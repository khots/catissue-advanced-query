
package edu.wustl.common.query.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import edu.common.dynamicextensions.domaininterface.AssociationInterface;
import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.AttributeTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.BooleanTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.DateTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.DoubleTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.common.dynamicextensions.domaininterface.IntegerTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.LongTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.StringTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.databaseproperties.ConstraintKeyPropertiesInterface;
import edu.common.dynamicextensions.domaininterface.databaseproperties.ConstraintPropertiesInterface;
import edu.common.dynamicextensions.entitymanager.DataTypeFactory;
import edu.common.dynamicextensions.entitymanager.EntityManager;
import edu.common.dynamicextensions.entitymanager.EntityManagerConstantsInterface;
import edu.common.dynamicextensions.entitymanager.EntityManagerInterface;
import edu.common.dynamicextensions.exception.DataTypeFactoryInitializationException;
import edu.common.dynamicextensions.exception.DynamicExtensionsSystemException;
import edu.wustl.common.query.exeptions.SQLXMLException;
import edu.wustl.common.query.impl.predicate.PredicateGenerator;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.query.queryobject.impl.metadata.QueryOutputTreeAttributeMetadata;
import edu.wustl.common.query.queryobject.util.QueryObjectProcessor;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.exceptions.SqlException;
import edu.wustl.common.querysuite.metadata.associations.IAssociation;
import edu.wustl.common.querysuite.metadata.associations.IIntraModelAssociation;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IParameter;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.querysuite.queryobject.impl.JoinGraph;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.querysuite.utils.QueryUtility;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.metadata.util.DyExtnObjectCloner;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.util.querysuite.QueryCSMUtil;
import edu.wustl.query.xquerydatatypes.XQueryAttributeType;
import edu.wustl.query.xquerydatatypes.XQueryDataTypeInitializationException;

/**
 * 
 * @author juberahamad_patel
 * 
 * abstract class from which the xquery generator classes extend
 *
 */
public abstract class AbstractXQueryGenerator extends QueryGenerator
{

	/**
	 * the suffix used to generate sql column names on the fly, like column0, column1 etc.
	 */
	protected int suffix = 0;

	/**
	 * the set of expressions whose entites have a separate XML file, where they are the root element
	 */
	private Set<IExpression> mainExpressions;

	/**
	 * map of expressions for entities and the xpath used to reach them
	 * xpath could be a variable name or a path
	 */
	private Map<IExpression, String> entityPaths;

	/**
	 * map of expressions which have one to many relationships with their parents 
	 * expressions and their respective target roles
	 */
	private Map<IExpression, String> targetRoles;

	/**
	 * the map of exprssions for which a variable is created in the for clause and the variables 
	 */
	private Map<IExpression, String> forVariables;

	/**
	 * the selected attributes (ie the ones going in SELECT part) and their aliases
	 */
	private Map<IOutputAttribute, String> attributeAliases;

	/**
	 * map of IParameter and the expressions corresponding to their attributes 
	 */
	private Map<IParameter<ICondition>, IExpression> parameters;

	/*
	 * A List containing all the main Entitiees
	 */
	protected List<EntityInterface> allMainEntityList = new ArrayList<EntityInterface>();
	//private static org.apache.log4j.Logger logger =Logger.getLogger(XQueryGenerator.class);

	/**
	 * Generates SQL for the given Query Object.
	 * 
	 * @param query 
	 * @return the String representing SQL for the given Query object.
	 * @throws MultipleRootsException 
	 * @throws SqlException 
	 * @throws XQueryDataTypeInitializationException 
	 * @see edu.wustl.common.querysuite.queryengine.ISqlGenerator#generateSQL(edu.wustl.common.querysuite.queryobject.IQuery)
	 */

	/*
	 * The following function takes IQuery object as input for further processing
	 * @parameters : IQuery query= The query object
	 * @parameters : char QueryType = representing the kind of query whether aggregate or normal
	 */
	@Override
	public String generateQuery(IQuery query) throws MultipleRootsException, SqlException
	{
		String formedQuery = null;
		try
		{
			prepare(query);
			formedQuery = formQuery();

		}
		catch (SQLXMLException e)
		{
			throw new SqlException("problem while trying to build xquery", e);
		}
		catch (DynamicExtensionsSystemException e)
		{
			throw new SqlException("problem while trying to build xquery", e);
		}
		catch (XQueryDataTypeInitializationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (!Variables.isExecutingTestCase)
		{
			log(formedQuery);
		}
		return formedQuery;
	}

	/**
	 * prepare the data structures and information required to generate xquery
	 * This is step I of xquery generation 
	 * @param query
	 * @throws MultipleRootsException
	 */
	private void prepare(IQuery query) throws MultipleRootsException
	{
		ParameterizedQuery queryClone = (ParameterizedQuery) new DyExtnObjectCloner().clone(query);
		setSelectedAttributes(queryClone);
		setParameterizedAttributes(queryClone);

		// IQuery queryClone = query;
		constraints = queryClone.getConstraints();
		QueryObjectProcessor.replaceMultipleParents(constraints);

		this.joinGraph = (JoinGraph) constraints.getJoinGraph();
		aliasAppenderMap = new HashMap<IExpression, Integer>();
		createAliasAppenderMap();

		mainExpressions = new LinkedHashSet<IExpression>();
		setMainExpressions(joinGraph.getRoot());
		createTree();
		createEntityPaths();
		checkForEmptyExpression(joinGraph.getRoot().getExpressionId());
	}

	/**
	 * set the parameters and their expressions
	 * @param query
	 */
	private void setParameterizedAttributes(ParameterizedQuery query)
	{
		parameters = new HashMap<IParameter<ICondition>, IExpression>();

		for (IParameter<?> parameter : query.getParameters())
		{
			IExpression expression = QueryUtility.getExpression((IParameter<ICondition>) parameter,
					query);

			parameters.put((IParameter<ICondition>) parameter, expression);
		}
	}

	/**
	 * @param query
	 */
	private void setSelectedAttributes(ParameterizedQuery query)
	{
		//populate selected attributes and their aliases
		attributeAliases = new LinkedHashMap<IOutputAttribute, String>();

		for (IOutputAttribute selectedAttribute : query.getOutputAttributeList())
		{
			String attributeAlias = Utility.getAliasFor(selectedAttribute.getAttribute(),
					selectedAttribute.getExpression());
			attributeAliases.put(selectedAttribute, attributeAlias);
		}
	}

	/**
	 * form xquery using the data structures prepared in step I.
	 * This is step II of xquery generation.
	 *  
	 * @return the formed xquery
	 * @throws SqlException
	 * @throws MultipleRootsException
	 * @throws SQLXMLException
	 * @throws DynamicExtensionsSystemException
	 * @throws XQueryDataTypeInitializationException 
	 */
	private String formQuery() throws SqlException, MultipleRootsException, SQLXMLException,
			DynamicExtensionsSystemException, XQueryDataTypeInitializationException
	{
		StringBuilder formedQuery = new StringBuilder();

		formedQuery.append(buildSelectPart());
		String wherePart = buildWherePart(constraints.getRootExpression(), null);
		wherePart = addJoiningTableCondition(wherePart);
		PredicateGenerator predicateGenerator = new PredicateGenerator(forVariables, wherePart);

		//isEmptyExpression(rootExpression.getExpressionId());

		formedQuery.append(buildFromPart(predicateGenerator));

		return formedQuery.toString();
	}

	/**
	 * add the join condition for 2 tables to the "where" part
	 * 
	 * @param wherePart
	 * @return "where" part that has the join condition in it
	 * @throws MultipleRootsException
	 * @throws SqlException
	 * @throws XQueryDataTypeInitializationException 
	 * @throws DynamicExtensionsSystemException 
	 */
	private String addJoiningTableCondition(String wherePart) throws MultipleRootsException,
			SqlException, XQueryDataTypeInitializationException, DynamicExtensionsSystemException
	{
		StringBuilder completeWherePart = new StringBuilder(wherePart);
		Set<Integer> processedAlias = new HashSet<Integer>();
		IExpression parentExpression = joinGraph.getRoot();
		String leftAlias = getAliasName(parentExpression);
		completeWherePart.append(Constants.QUERY_AND);
		completeWherePart.append(processChildExpressions(leftAlias, processedAlias,
				parentExpression));
		return Utility.removeLastAnd(completeWherePart.toString());
	}

	/**
	 * To process all child expression of the given Expression & get their SQL
	 * representation for where part.
	 * 
	 * @param leftAlias left table alias in join.
	 * @param processedAlias The list of precessed alias.
	 * @param parentExpressionId The reference to expression whose children to
	 *            be processed.
	 * @return the left join sql for children expression.
	 * @throws SqlException when there is error in the passed IQuery object.
	 * @throws XQueryDataTypeInitializationException 
	 * @throws DynamicExtensionsSystemException 
	 */
	private String processChildExpressions(String leftAlias, Set<Integer> processedAlias,
			IExpression parentExpression) throws SqlException, XQueryDataTypeInitializationException, DynamicExtensionsSystemException
	{
		StringBuffer buffer = new StringBuffer();
		List<IExpression> children = joinGraph.getChildrenList(parentExpression);
		if (!children.isEmpty())
		{
			// processing all outgoing edges/nodes from the current node in the
			// joingraph.
			for (IExpression childExpression : children)
			{
				if (mainExpressions.contains(childExpression))
				{
					IAssociation association = joinGraph.getAssociation(parentExpression,
							childExpression);
					AssociationInterface actualEavAssociation = ((IIntraModelAssociation) association)
							.getDynamicExtensionsAssociation();
					AssociationInterface eavAssociation = actualEavAssociation;
					EntityInterface rightEntity = eavAssociation.getTargetEntity();
					String actualRightAlias = getAliasFor(childExpression, rightEntity);
					if (!processedAlias.contains(aliasAppenderMap.get(childExpression)))
					{
						ConstraintPropertiesInterface constraintProperties = eavAssociation
								.getConstraintProperties();
						Collection<ConstraintKeyPropertiesInterface> srcCnstrKeyPropColl = constraintProperties
								.getSrcEntityConstraintKeyPropertiesCollection();
						Collection<ConstraintKeyPropertiesInterface> tgtCnstrKeyPropColl = constraintProperties
								.getTgtEntityConstraintKeyPropertiesCollection();
						
						String leftAttribute = null;
						String rightAttribute = null;
						
						//many sides
						for (ConstraintKeyPropertiesInterface cnstrKeyProp : srcCnstrKeyPropColl)
						{
							AttributeInterface primaryKey = cnstrKeyProp.getSrcPrimaryKeyAttribute();
							String xQueryDataType = getXQuerydataType(primaryKey);
							leftAttribute = "$" + getAliasName(parentExpression) + "/"
									+ cnstrKeyProp.getTgtForiegnKeyColumnProperties().getName();
							EntityInterface entity = cnstrKeyProp.getSrcPrimaryKeyAttribute().getEntity();
							String entityPath = getEntityPath(entity,childExpression);
							String primaryKeyName = cnstrKeyProp.getSrcPrimaryKeyAttribute()
									.getName();
							rightAttribute = "$" + getAliasName(childExpression) + entityPath + "/"
									+ primaryKeyName  +  "/" + xQueryDataType;
							buffer.append(Constants.QUERY_OPENING_PARENTHESIS + rightAttribute + "=" + leftAttribute);
							buffer.append(Constants.QUERY_CLOSING_PARENTHESIS);
							buffer.append(Constants.QUERY_AND);
						}
						// One Side
						for (ConstraintKeyPropertiesInterface cnstrKeyProp : tgtCnstrKeyPropColl)
						{
							AttributeInterface primaryKey = cnstrKeyProp.getSrcPrimaryKeyAttribute();
							String xQueryDataType = getXQuerydataType(primaryKey);
							
							EntityInterface entity = cnstrKeyProp.getSrcPrimaryKeyAttribute().getEntity();
							String entityPath = getEntityPath(entity, parentExpression);
							
							String primaryKeyName = cnstrKeyProp.getSrcPrimaryKeyAttribute()
									.getName();
							leftAttribute = "$" + getAliasName(parentExpression) + entityPath + "/"
									+ primaryKeyName ;
							rightAttribute = "$" + getAliasName(childExpression) + "/"
									+ cnstrKeyProp.getTgtForiegnKeyColumnProperties().getName()+ "/" + xQueryDataType;
							buffer.append(Constants.QUERY_OPENING_PARENTHESIS + rightAttribute + "=" + leftAttribute);
							buffer.append(Constants.QUERY_CLOSING_PARENTHESIS);
							buffer.append(Constants.QUERY_AND);
						}

					}


					// append from part SQLXML for the next Expressions.
					buffer.append(processChildExpressions(actualRightAlias, processedAlias,
							childExpression));
				}
				else
				{
					continue;
				}
			}
		}
	
		return buffer.toString();
	}

/**
 * 
 * @param entity - the entity whose path is been calculated
 * @param intermediatePath - String buffer that stores the path and sends at as input recu 
 * @param expression - expression for which the path is to be calculated
 * @return
 * @throws DynamicExtensionsSystemException
 */
	private String getEntityPath(EntityInterface entity, IExpression expression) throws DynamicExtensionsSystemException
	{
		StringBuffer intermediatePath = new StringBuffer();
		if(!allMainEntityList.contains(entity))
		{	
			EntityManagerInterface entityMgr = EntityManager.getInstance();
			Collection<AssociationInterface> associationList = entityMgr.getIncomingAssociations(entity);
			for(AssociationInterface association : associationList)
			{
				EntityInterface associatedEntity = association.getEntity();
				if (associatedEntity.equals(expression.getQueryEntity().getDynamicExtensionsEntity()))
						{
							intermediatePath.append("/").append(association.getTargetRole().getName());
						}
			}		
		}
		return intermediatePath.toString();
	}

	
	/**
	 * The method returns the XQuery function based on the data type
	 * @param attributeType - the data type of the attribute
	 * @return String representing XQuery function
	 * @throws XQueryDataTypeInitializationException
	 */
	private String getXQuerydataType(AttributeInterface attributeType)
			throws XQueryDataTypeInitializationException
	{
		String returnValue = null;
		AttributeTypeInformationInterface dataType = attributeType.getAttributeTypeInformation();
		XQueryAttributeType xQueryAttributeType = XQueryAttributeType.getInstance();

		if (dataType instanceof StringTypeInformationInterface)
		{
			returnValue = xQueryAttributeType
					.getDataType(EntityManagerConstantsInterface.STRING_ATTRIBUTE_TYPE);
		}
		else if (dataType instanceof DateTypeInformationInterface)
		{
			returnValue = xQueryAttributeType
					.getDataType(EntityManagerConstantsInterface.DATE_TIME_ATTRIBUTE_TYPE);
		}
		else if (dataType instanceof LongTypeInformationInterface)
		{
			returnValue = xQueryAttributeType
					.getDataType(EntityManagerConstantsInterface.LONG_ATTRIBUTE_TYPE);
		}
		else if (dataType instanceof DoubleTypeInformationInterface)
		{
			returnValue = xQueryAttributeType
					.getDataType(EntityManagerConstantsInterface.DOUBLE_ATTRIBUTE_TYPE);
		}
		else if (dataType instanceof IntegerTypeInformationInterface)
		{
			returnValue = xQueryAttributeType
					.getDataType(EntityManagerConstantsInterface.INTEGER_ATTRIBUTE_TYPE);
		}
		else if (dataType instanceof BooleanTypeInformationInterface)
		{
			returnValue = xQueryAttributeType
					.getDataType(EntityManagerConstantsInterface.BOOLEAN_ATTRIBUTE_TYPE);
		}

		return returnValue;
	}

	/**
	 * log the sql
	 * @param sql
	 */
	private void log(String sql)
	{
		try
		{
			new SQLLogger().log(sql);
		}
		catch (IOException e)
		{
			Logger.out.error("Error while logging sql.\n" + e);
		}
	}

	/**
	 * populate entityPaths, forVariables and targetRoles 
	 * by processing the expression hierarchy and deciding which relationships are one-many. 
	 */
	private void createEntityPaths()
	{
		entityPaths = new LinkedHashMap<IExpression, String>();
		forVariables = new LinkedHashMap<IExpression, String>();
		targetRoles = new HashMap<IExpression, String>();

		for (IExpression mainExpression : mainExpressions)
		{
			String mainVariable = new StringBuilder().append(Constants.QUERY_DOLLAR).append(
					getAliasName(mainExpression)).toString();

			entityPaths.put(mainExpression, mainVariable);
			forVariables.put(mainExpression, mainVariable);
			createEntityPaths(mainExpression, mainVariable);
		}
	}

	/**
	 * the recursive method to traverse down the expression hierachy 
	 * and decide paths to reach each entity 
	 * @param expression
	 * @param xpath the path built so far to reach this point
	 */
	private void createEntityPaths(IExpression expression, String xpath)
	{
		for (IExpression childExpression : getNonMainChildren(expression))
		{
			String newPath = null;
			IAssociation association = joinGraph.getAssociation(expression, childExpression);
			AssociationInterface eavAssociation = ((IIntraModelAssociation) association)
					.getDynamicExtensionsAssociation();

			int cardinality = eavAssociation.getTargetRole().getMaximumCardinality().getValue();
			if (cardinality > 1)
			{
				newPath = new StringBuilder().append(Constants.QUERY_DOLLAR).append(
						getAliasName(childExpression)).toString();
				entityPaths.put(childExpression, newPath);
				forVariables.put(childExpression, newPath);
				targetRoles.put(childExpression, eavAssociation.getTargetRole().getName());
			}
			else
			{
				String childEntityName = eavAssociation.getTargetRole().getName();
				newPath = new StringBuilder(xpath).append('/').append(childEntityName).toString();
				entityPaths.put(childExpression, newPath);
			}

			createEntityPaths(childExpression, newPath);
		}
	}

	/**
	 * 
	 * @param predicateGenerator 
	 * @return the From part of SQLXML
	 * @throws SQLXMLException - Will be thrown when there is some SQLXML Exception
	 * @throws DynamicExtensionsSystemException - Exception thrown by DynamicExtensions
	 * @throws MultipleRootsException - thrown when there is more then one root element
	 * @throws SqlException - Thrown when there is some SQL Exception
	 */
	private String buildFromPart(PredicateGenerator predicateGenerator) throws SQLXMLException,
			DynamicExtensionsSystemException, MultipleRootsException, SqlException
	{
		StringBuilder fromPart = new StringBuilder();

		fromPart.append(Constants.QUERY_FROM_XMLTABLE + Constants.QUERY_OPENING_PARENTHESIS + "'");
		fromPart.append(buildXQuery(predicateGenerator));
		fromPart.append("'");
		fromPart.append(buildPassingPart());
		fromPart.append(buildColumnsPart());
		fromPart.append(Constants.QUERY_CLOSING_PARENTHESIS);

		return fromPart.toString();
	}

	/**
	 * return the Select portion of SQLXML Query
	 * throws
	 * SQLXMLException
	 */
	private String buildSelectPart() throws SQLXMLException
	{

		StringBuilder selectClause = new StringBuilder(256);
		selectClause.append(Constants.SELECT);

		for (Entry<IOutputAttribute, String> entry : attributeAliases.entrySet())
		{
			selectClause.append(entry.getValue());
			String columnAliasName = Constants.QUERY_COLUMN_NAME + suffix;
			selectClause.append(" " + columnAliasName + Constants.QUERY_COMMA);
			addToTreeNode(entry, columnAliasName);
			attributeColumnNameMap.put(entry.getKey().getAttribute(), columnAliasName);
			suffix++;
		}

		Utility.removeLastComma(selectClause);
		return selectClause.toString();

	}

	/**
	 * add the attribute to the appropriate node of rootOutputTreeNodeList
	 * @param entry
	 * @param columnAliasName
	 */
	private void addToTreeNode(Entry<IOutputAttribute, String> entry, String columnAliasName)
	{
		// code to get displayname. & pass it to the Constructor along with
		// treeNode.

		OutputTreeDataNode treeNode = null;

		//find the right tree node to add the attribute to
		for (OutputTreeDataNode node : attributeOutputTreeNodeList)
		{
			if (node.getExpressionId() == entry.getKey().getExpression().getExpressionId())
			{
				treeNode = node;
				break;
			}
		}

		String displayNameForColumn = Utility
				.getDisplayNameForColumn(entry.getKey().getAttribute());
		treeNode.addAttribute(new QueryOutputTreeAttributeMetadata(entry.getKey().getAttribute(),
				columnAliasName, treeNode, displayNameForColumn));
	}

	/**
	 * 
	 * @param predicateGenerator 
	 * @return returns the XQuery formed from IQuery object
	 * @throws SQLXMLException
	 * @throws DynamicExtensionsSystemException
	 * @throws MultipleRootsException
	 * @throws SqlException
	 */
	private String buildXQuery(PredicateGenerator predicateGenerator) throws SQLXMLException,
			DynamicExtensionsSystemException, MultipleRootsException, SqlException

	{
		StringBuffer xQuery = new StringBuffer(1024);

		xQuery.append(buildXQueryForClause(predicateGenerator));
		xQuery.append(buildXQueryLetClause(predicateGenerator));
		//xQuery.append(buildXQueryWhereClause());
		xQuery.append(buildXQueryReturnClause());

		return xQuery.toString();

	}

	/**
	 * 
	 * @param predicateGenerator 
	 * @return the For Clause of XQuery
	 * @throws MultipleRootsException
	 * @throws DynamicExtensionsSystemException
	 */
	protected abstract String buildXQueryForClause(PredicateGenerator predicateGenerator)
			throws MultipleRootsException, DynamicExtensionsSystemException;

	/**
	 * 
	 *  @return the Return Clause of SQLXML
	 */
	protected abstract String buildXQueryLetClause(PredicateGenerator predicateGenerator);

	/**
	 * 
	 * @return the Return Clause of SQLXML
	 */
	protected abstract String buildXQueryReturnClause();

	/**
	 * Adds an pseudo anded expression & all its child expressions to
	 * pAndExpressions set.
	 * 
	 * @param expression pAnd expression
	 */
	/*private void addpAndExpression(IExpression expression)
	{
		List<IExpression> childList = joinGraph.getChildrenList(expression);
		pAndExpressions.add(expression);

		for (IExpression newExp : childList)
		{
			addpAndExpression(newExp);
		}

	}*/

	/**
	 * change the first letter of the Entity Name 
	 * to lower case
	 */
	protected String deCapitalize(String name)
	{
		StringBuilder builder = new StringBuilder(name);
		String firstLetter = name.substring(0, 1).toLowerCase();
		builder.replace(0, 1, firstLetter);
		return builder.toString();
	}

	/**
	 * @return Will modify the DataType depending on input
	 * according to the database
	 */
	@Override
	protected String modifyValueForDataType(String value, AttributeTypeInformationInterface dataType)
	{
		StringBuilder actualValue = new StringBuilder();

		if (dataType instanceof StringTypeInformationInterface)
		{
			actualValue.append("\"").append(value).append("\"");
		}
		else if (dataType instanceof DateTypeInformationInterface)
		{
			String actualYear = value.substring(6, 10);
			String actualMonth = value.substring(0, 2);
			String actualDate = value.substring(3, 5);
			String actualTime = "";
			try
			{
				actualTime = value.substring(11);
			}
			catch (Exception e)
			{
				actualTime = "";
			}
			if (actualTime.equals(""))
			{
				StringBuilder newValue = new StringBuilder(actualYear);
				newValue.append("-");
				newValue.append(actualMonth);
				newValue.append("-");
				newValue.append(actualDate);
				actualValue.append("xs:dateTime(\"").append(newValue.toString()).append(
						"T00:00:00\")");
			}
			else
			{
				StringBuilder newValue = new StringBuilder(actualYear);
				newValue.append("-");
				newValue.append(actualMonth);
				newValue.append("-");
				newValue.append(actualDate);
				newValue.append("T" + actualTime);
				actualValue.append("xs:dateTime(\"").append(newValue.toString()).append("\")");
			}
		}
		else
		{
			actualValue.append(value);
		}
		return actualValue.toString();
	}

	/**
	 * populate the set of main expressions by
	 * traversing expression tree recursively
	 */
	private void setMainExpressions(IExpression expression)
	{
		List<EntityInterface> mainEntityList = new ArrayList<EntityInterface>();
		EntityInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();

		List<EntityInterface> mainEntities = QueryCSMUtil
				.getAllMainEntities(entity, mainEntityList);

		if (mainEntities.contains(expression.getQueryEntity().getDynamicExtensionsEntity()))
		{
			mainExpressions.add(expression);
		}

		for (IExpression child : joinGraph.getChildrenList(expression))
		{
			setMainExpressions(child);
		}
	}

	/**
	 * 
	 * @return the 'passing' part of SQLXML
	 * @throws DataTypeFactoryInitializationException 
	 */
	protected abstract String buildPassingPart() throws DataTypeFactoryInitializationException;

	/**
	 * 
	 * @return Columns part of SQLXML
	 * @throws DataTypeFactoryInitializationException
	 */
	protected abstract String buildColumnsPart() throws DataTypeFactoryInitializationException;

	/**
	 * get the database specific data type for given attribute
	 * 
	 * @param attribute
	 * @return
	 * @throws DataTypeFactoryInitializationException
	 */
	protected String getDataTypeInformation(AttributeInterface attribute)
			throws DataTypeFactoryInitializationException
	{
		String returnValue = null;

		DataTypeFactory type = DataTypeFactory.getInstance();
		AttributeTypeInformationInterface dataType = attribute.getAttributeTypeInformation();

		if (dataType instanceof StringTypeInformationInterface)
		{
			returnValue = type
					.getDatabaseDataType(EntityManagerConstantsInterface.STRING_ATTRIBUTE_TYPE)
					+ Constants.QUERY_OPENING_PARENTHESIS
					+ ((StringTypeInformationInterface) dataType).getSize()
					+ Constants.QUERY_CLOSING_PARENTHESIS;
		}
		else if (dataType instanceof DateTypeInformationInterface)
		{
			returnValue = type
					.getDatabaseDataType(EntityManagerConstantsInterface.DATE_TIME_ATTRIBUTE_TYPE);
		}
		else if (dataType instanceof LongTypeInformationInterface)
		{
			returnValue = type
					.getDatabaseDataType(EntityManagerConstantsInterface.LONG_ATTRIBUTE_TYPE);
		}
		else if (dataType instanceof DoubleTypeInformationInterface)
		{
			returnValue = type
					.getDatabaseDataType(EntityManagerConstantsInterface.DOUBLE_ATTRIBUTE_TYPE);
		}
		else if (dataType instanceof IntegerTypeInformationInterface)
		{
			returnValue = type
					.getDatabaseDataType(EntityManagerConstantsInterface.INTEGER_ATTRIBUTE_TYPE);
		}
		else if (dataType instanceof BooleanTypeInformationInterface)
		{
			returnValue = type
					.getDatabaseDataType(EntityManagerConstantsInterface.BOOLEAN_ATTRIBUTE_TYPE);
		}

		return returnValue;
	}

	/**
	 * get the complete name for given attribute
	 */
	@Override
	protected String getConditionAttributeName(AttributeInterface attribute, IExpression expression)
	{
		return entityPaths.get(expression) + '/' + attribute.getName();
	}

	@Override
	protected String getDescriminatorCondition(EntityInterface entity, String aliasFor)
	{
		//for the time being null is sufficient 
		return null;
	}

	@Override
	protected boolean shouldAddNodeFor(IExpression expression)
	{
		return super.shouldAddNodeFor(expression);
	}

	@Override
	protected boolean isContainedExpresion(int expressionId)
	{
		boolean isMainExpression = false;
		for (IExpression exp : mainExpressions)
		{
			if (exp.getExpressionId() == expressionId)
			{
				isMainExpression = true;
				break;
			}
		}
		return !(isMainExpression);
	}

	/**
	 * create xquery fragment to represent "between" operator 
	 */
	@Override
	protected String processBetweenOperator(ICondition condition, String attributeName)
			throws SqlException
	{
		StringBuilder builder = new StringBuilder();
		List<String> values = condition.getValues();

		if (values.size() != 2)
		{
			throw new SqlException("Incorrect number of operand for Between oparator in condition:"
					+ condition);
		}

		AttributeTypeInformationInterface dataType = condition.getAttribute()
				.getAttributeTypeInformation();

		if (!(dataType instanceof DateTypeInformationInterface
				|| dataType instanceof IntegerTypeInformationInterface
				|| dataType instanceof LongTypeInformationInterface || dataType instanceof DoubleTypeInformationInterface))
		{
			throw new SqlException(
					"Incorrect Data type of operand for Between oparator in condition:" + condition);
		}

		String firstValue = modifyValueForDataType(values.get(0), dataType);
		String secondValue = modifyValueForDataType(values.get(1), dataType);

		builder.append(attributeName).append(
				RelationalOperator.getSQL(RelationalOperator.GreaterThanOrEquals)).append(
				firstValue);
		builder.append(Constants.QUERY_AND);
		builder.append(attributeName).append(
				RelationalOperator.getSQL(RelationalOperator.LessThanOrEquals)).append(secondValue);

		return builder.toString();

	}

	/**
	 * create xquery fragment to represent "in" operator 
	 */
	@Override
	protected String processInOperator(ICondition condition, String attributeName)
			throws SqlException
	{
		StringBuilder builder = new StringBuilder(attributeName).append(' ').append(" = ").append(
				' ').append(Constants.QUERY_OPENING_PARENTHESIS);

		for (String value : condition.getValues())
		{
			AttributeTypeInformationInterface dataType = condition.getAttribute()
					.getAttributeTypeInformation();
			if (dataType instanceof StringTypeInformationInterface)
			{
				builder.append("\"").append(value).append("\"").append(Constants.QUERY_COMMA);
			}
			else
			{
				builder.append(value).append(Constants.QUERY_COMMA);
			}
		}

		Utility.removeLastComma(builder);
		builder.append(Constants.QUERY_CLOSING_PARENTHESIS);

		if (condition.getRelationalOperator().equals(RelationalOperator.NotIn))
		{
			builder.insert(0, Constants.QUERY_OPENING_PARENTHESIS).insert(0, "not").append(
					Constants.QUERY_CLOSING_PARENTHESIS);
		}

		return builder.toString();

	}

	/**
	 * create xquery fragment to represent "exists" and "empty" operators 
	 */
	@Override
	protected String processNullCheckOperators(ICondition condition, String attributeName)
			throws SqlException
	{
		RelationalOperator operator = condition.getRelationalOperator();
		StringBuilder builder = new StringBuilder();

		if (operator.equals(RelationalOperator.IsNotNull))
		{
			builder.append("exists");
		}
		else if (operator.equals(RelationalOperator.IsNull))
		{
			builder.append("empty");
		}

		builder.append(Constants.QUERY_OPENING_PARENTHESIS).append(attributeName).append(
				Constants.QUERY_CLOSING_PARENTHESIS);

		return builder.toString();
	}

	/**
	 * create xquery fragment to represent "contains", "starts-with" and 
	 * "ends-with" operators 
	 */
	@Override
	protected String processLikeOperators(ICondition condition, String attributeName)
			throws SqlException
	{
		RelationalOperator operator = condition.getRelationalOperator();
		String newOperator = null;
		String value = condition.getValue();

		if (operator.equals(RelationalOperator.Contains))
		{
			newOperator = "contains(string(" + attributeName + "),\"" + value + "\")";
		}
		else if (operator.equals(RelationalOperator.StartsWith))
		{
			newOperator = "starts-with(string(" + attributeName + "),\"" + value + "\")";
		}
		else if (operator.equals(RelationalOperator.EndsWith))
		{
			newOperator = "ends-with(string(" + attributeName + "),\"" + value + "\")";
		}

		return newOperator;

	}

	/**
	 * get the list of children of given expression which are not main expressions
	 *   
	 * @param expression
	 * @return
	 */
	protected List<IExpression> getNonMainChildren(IExpression expression)
	{
		List<IExpression> nonMainChildren = new ArrayList<IExpression>(joinGraph
				.getChildrenList(expression));
		nonMainChildren.removeAll(mainExpressions);
		return nonMainChildren;
	}

	protected List<IExpression> getNonMainNonEmptyChildren(IExpression expression)
	{
		List<IExpression> children = getNonMainChildren(expression);
		children.removeAll(emptyExpressions);
		return children;

	}

	/**
	 * @return the mainExpressions
	 */
	protected Set<IExpression> getMainExpressions()
	{
		return mainExpressions;
	}

	/**
	 * @return the entityPaths
	 */
	protected Map<IExpression, String> getEntityPaths()
	{
		return entityPaths;
	}

	/**
	 * @return the targetRoles
	 */
	protected Map<IExpression, String> getTargetRoles()
	{
		return targetRoles;
	}

	/**
	 * @return the forVariables
	 */
	protected Map<IExpression, String> getForVariables()
	{
		return forVariables;
	}

	/**
	 * @return the attributeAliases
	 */
	protected Map<IOutputAttribute, String> getAttributeAliases()
	{
		return attributeAliases;
	}

	/**
	 * @return the parameters
	 */
	protected Map<IParameter<ICondition>, IExpression> getParameters()
	{
		return parameters;
	}
	
	/**
	 * 
	 * @param operandquery
	 * @return Added a method so that the Parser can identify the temporal query and act accordingly
	 */
	protected String getTemporalCondition(String operandquery) 
	{
		return Constants.QUERY_TEMPORAL_CONDITION + "(" + operandquery + ")";
	}

}