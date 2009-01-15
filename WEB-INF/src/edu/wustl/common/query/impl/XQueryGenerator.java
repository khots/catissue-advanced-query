
package edu.wustl.common.query.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
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
import edu.common.dynamicextensions.entitymanager.DataTypeFactory;
import edu.common.dynamicextensions.entitymanager.EntityManagerConstantsInterface;
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
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.LogicalOperator;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.querysuite.queryobject.impl.JoinGraph;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.metadata.util.DyExtnObjectCloner;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.querysuite.QueryCSMUtil;

public abstract class XQueryGenerator extends QueryGenerator
{

	protected int suffix = 0;
	/**
	 * the set of expressions whose entites have a separate XML file, where they are the root element
	 */
	protected Set<IExpression> mainExpressions;

	/**
	 * map of expressions for entities and the xpath used to reach them
	 * xpath could be a variable name or a path
	 */
	protected Map<IExpression, String> entityPaths;

	/**
	 * map of expressions which have one to many relationships with their parents 
	 * expressions and their respective target roles
	 */
	protected Map<IExpression, String> targetRoles;

	/**
	 * the map of exprssions for which a variable is created in the for clause and the variables 
	 */
	protected Map<IExpression, String> forVariables;

	/**
	 * the selected attributes (ie the ones going in SELECT part) and their aliases
	 */
	protected Map<IOutputAttribute, String> attributeAliases;

	
	//private static org.apache.log4j.Logger logger =Logger.getLogger(XQueryGenerator.class);
	
	/**
	 * Generates SQL for the given Query Object.
	 * 
	 * @param query 
	 * @return the String representing SQL for the given Query object.
	 * @throws MultipleRootsException 
	 * @throws SqlException 
	 * @see edu.wustl.common.querysuite.queryengine.ISqlGenerator#generateSQL(edu.wustl.common.querysuite.queryobject.IQuery)
	 */

	/*
	 * The following function takes IQuery object as input for further processing
	 * @parameters : IQuery query= The query object
	 * @parameters : char QueryType = representing the kind of query whether aggregate or normal
	 */
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
		log(formedQuery);
		return formedQuery;
	}

	private void prepare(IQuery query) throws MultipleRootsException
	{
		//populate selected attributes and their aliases
		attributeAliases = new LinkedHashMap<IOutputAttribute, String>();
		IQuery queryClone = new DyExtnObjectCloner().clone(query);
		for( IOutputAttribute selectedAttribute : ((ParameterizedQuery) queryClone).getOutputAttributeList())
		{
			String attributeAlias = getAliasFor(selectedAttribute);
			attributeAliases.put(selectedAttribute, attributeAlias);
		}
		
		// IQuery queryClone = query;
		constraints = queryClone.getConstraints();
		QueryObjectProcessor.replaceMultipleParents(constraints);

		this.joinGraph = (JoinGraph) constraints.getJoinGraph();
		aliasAppenderMap = new HashMap<IExpression, Integer>();
		createAliasAppenderMap();
		setMainExpressions();
		createTree();
		createEntityPaths(queryClone);

	}

	private String formQuery() throws SqlException, MultipleRootsException, SQLXMLException,
			DynamicExtensionsSystemException
	{
		StringBuilder formedQuery = new StringBuilder();

		formedQuery.append(buildSelectPart());
		String wherePart = buildWherePart(constraints.getRootExpression(), null);
		PredicateGenerator predicateGenerator = new PredicateGenerator(forVariables, wherePart);

		//isEmptyExpression(rootExpression.getExpressionId());

		formedQuery.append(buildFromPart(predicateGenerator));

		return formedQuery.toString();
	}
	
	
	
	private void log(String sql)
	{
		// TODO format.
		try
		{
			new SQLLogger().log(sql);
		}
		catch (IOException e)
		{
			Logger.out.error("Error while logging sql.\n" + e);
		}
	}

	private void createEntityPaths(IQuery queryClone)
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

	private void createEntityPaths(IExpression expression, String xpath)
	{
		for (IExpression childExpression : joinGraph.getChildrenList(expression))
		{
			//skip main expressions
			if (mainExpressions.contains(childExpression))
			{
				continue;
			}

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
				String childEntityName = deCapitalize(childExpression.getQueryEntity()
						.getDynamicExtensionsEntity().getName());
				newPath = new StringBuilder(xpath).append('/').append(childEntityName)
						.toString();
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
	 * To assign alias to each tablename in the Expression. It will generate
	 * alias that will be assigned to each entity in Expression.
	 * 
	 * @param expression the Root Expression of the Query.
	 * @param currentAliasCount The count from which it will start to assign
	 *            alias appender.
	 * @param aliasToSet The alias to set for the current expression.
	 * @param pathMap The map of path verses the ExpressionId. entry in this map
	 *            means, for such path, there is already alias assigned to some
	 *            Expression.
	 * @return The int representing the modified alias appender count that will
	 *         be used for further processing.
	 * @throws MultipleRootsException if there are multpile roots present in
	 *             join graph.
	 */
	/*void createAliasAppenderMap() throws MultipleRootsException
	{
		for (IExpression expr : constraints)
		{
			aliasAppenderMap.put(expr, expr.getExpressionId());
		}
	}*/

	/**
	 * To check if the Expression is empty or not. It will simultaneously add
	 * such empty expressions in the emptyExpressions set.
	 * 
	 * An expression is said to be empty when: - it contains no rule as operand. -
	 * and all of its children(i.e subExpressions & their subExpressions & so
	 * on) contains no rule
	 * 
	 * @param expressionId the reference to the expression id.
	 * @return true if the expression is empty.
	 */
	/*private boolean isEmptyExpression(int expressionId)
	{
		Expression expression = (Expression) constraints.getExpression(expressionId);
		List<IExpression> operandList = joinGraph.getChildrenList(expression);

		boolean isEmpty = true;
		if (!operandList.isEmpty()) // Check whether any of its children
		// contains rule.
		{
			for (IExpression subExpression : operandList)
			{
				if (!isEmptyExpression(subExpression.getExpressionId()))
				{
					isEmpty = false;
				}
			}
		}

		isEmpty = isEmpty && !expression.containsRule();// check if there are
		// rule present as
		// subexpression.
		// SRINATH
		isEmpty = isEmpty && !expression.containsCustomFormula();
		if (isEmpty)
		{
			emptyExpressions.add(expression); // Expression is empty.
		}

		return isEmpty;
	}*/

	/**
	 * To create output tree for the given expression graph.
	 * 
	 * @throws MultipleRootsException When there exists multiple roots in
	 *             joingraph.
	 */
	/*private void createTree() throws MultipleRootsException
	{
		IExpression rootExpression = joinGraph.getRoot();
		rootOutputTreeNodeList = new ArrayList<OutputTreeDataNode>();
		outputTreeNodeMap = new HashMap<Integer, OutputTreeDataNode>();
		OutputTreeDataNode rootOutputTreeNode = null;
		treeNo = 0;
		if (rootExpression.isInView())
		{
			IOutputEntity rootOutputEntity = getOutputEntity(rootExpression);
			rootOutputTreeNode = new OutputTreeDataNode(rootOutputEntity, rootExpression
					.getExpressionId(), treeNo++);
			rootOutputTreeNodeList.add(rootOutputTreeNode);
			outputTreeNodeMap.put(aliasAppenderMap.get(rootExpression), rootOutputTreeNode);
		}
		completeTree(rootExpression, rootOutputTreeNode);
	}*/

	/**
	 * TO create the output tree from the constraints.
	 * 
	 * @param expression The reference to Expression
	 * @param parentOutputTreeNode The reference to parent output tree node.
	 *            null if there is no parent.
	 */
	/*private void completeTree(IExpression expression, OutputTreeDataNode parentOutputTreeNode)
	{
		List<IExpression> children = joinGraph.getChildrenList(expression);
		for (IExpression childExp : children)
		{
			OutputTreeDataNode childNode = parentOutputTreeNode;
			/**
			 * Check whether chid node is in view or not. if it is in view then
			 * create output tree node for it. else look for their children node &
			 * create the output tree heirarchy if required.
			 
			if (childExp.isInView())
			{
				IOutputEntity childOutputEntity = getOutputEntity(childExp);
				Integer childAliasAppender = aliasAppenderMap.get(childExp);

				/**
				 * Check whether output tree node for expression with the same
				 * alias already added or not. if its not added then need to add
				 * it alias in the outputTreeNodeMap
				 
				childNode = outputTreeNodeMap.get(childAliasAppender);
				if (childNode == null)
				{
					if (parentOutputTreeNode == null)
					{
						// New root node for output tree found, so create root
						// node & add it in the rootOutputTreeNodeList.
						childNode = new OutputTreeDataNode(childOutputEntity, childExp
								.getExpressionId(), treeNo++);
						rootOutputTreeNodeList.add(childNode);
					}
					else
					{
						childNode = parentOutputTreeNode.addChild(childOutputEntity, childExp
								.getExpressionId());
					}
					outputTreeNodeMap.put(childAliasAppender, childNode);
				}
			}
			completeTree(childExp, childNode);
		}
	}*/

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

	
	private void addToTreeNode(Entry<IOutputAttribute, String> entry, String columnAliasName)
	{
		// code to get displayname. & pass it to the Constructor along with
		// treeNode.
		
		OutputTreeDataNode treeNode = null;
		
		//find the right tree node to add the attribute to
		for(OutputTreeDataNode node : rootOutputTreeNodeList)
		{
			if(node.getExpressionId() == entry.getKey().getExpression().getExpressionId())
			{
				treeNode = node;
				break;
			}
		}
		
		String displayNameForColumn = Utility.getDisplayNameForColumn(entry.getKey().getAttribute());
		treeNode.addAttribute(new QueryOutputTreeAttributeMetadata(entry.getKey().getAttribute(), columnAliasName,
				treeNode, displayNameForColumn));
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
	
	
	protected abstract String buildXQueryLetClause(PredicateGenerator predicateGenerator);
	
	

	/**
	 * 
	 * @return the Return Clause of SQLXML
	 */
	protected String buildXQueryReturnClause()
	{
		StringBuilder xqueryReturnClause = new StringBuilder(Constants.QUERY_RETURN);
		xqueryReturnClause.append("<return>");

		for (String forVariable : forVariables.values())
		{
			String tagName = forVariable.substring(1);
			xqueryReturnClause.append('<').append(tagName).append('>');
			xqueryReturnClause.append('{').append(forVariable).append('}');
			xqueryReturnClause.append("</").append(tagName).append('>');
		}

		xqueryReturnClause.append("</return>");
		return xqueryReturnClause.toString();
	}

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
	 * 
	 * @return The additional condition for Activity status,Research Opted, 
	 * effective Start timestamp and effective End timestamp
	 * @throws SQLXMLException
	 */
	private String getApplicationConditions() throws SQLXMLException
	{
		return "";
		/*
		StringBuilder operandRule = new StringBuilder();

		operandRule.append(" ");
			operandRule.append(Variables.properties
					.getProperty("xquery.wherecondition.activeUpiFlag"));
			operandRule.append(Constants.QUERY_AND);
			operandRule.append(Variables.properties
					.getProperty("xquery.wherecondition.researchOptOut"));
			operandRule.append(Constants.QUERY_AND);
		
			operandRule.append(Variables.properties
					.getProperty("xquery.wherecondition.person.startTimeStamp"));
			operandRule.append(Constants.QUERY_AND);
			operandRule.append(Variables.properties
					.getProperty("xquery.wherecondition.person.endTimeStamp"));
			operandRule.append(" ");
		
		return operandRule.toString(); */
	}

	/*
	private String getActualValue(String operator, String attributeName, String value)
	{
		StringBuilder newOperator = new StringBuilder();

		if (operator.equalsIgnoreCase("is NOT NULL"))
		{
			newOperator.append("exists").append(Constants.QUERY_OPENING_PARENTHESIS).append(
					Constants.QUERY_DOLLAR).append(attributeName).append(
					Constants.QUERY_CLOSING_PARENTHESIS);

		}
		else if (operator.equalsIgnoreCase("Contains"))
		{
			newOperator.append("contains").append(Constants.QUERY_OPENING_PARENTHESIS).append(
					Constants.QUERY_DOLLAR).append(attributeName).append(Constants.QUERY_COMMA)
					.append("\"").append(value).append("\"").append(
							Constants.QUERY_CLOSING_PARENTHESIS);

		}
		else if (operator.equalsIgnoreCase("StartsWith"))
		{
			newOperator.append("starts-with").append(Constants.QUERY_OPENING_PARENTHESIS).append(
					Constants.QUERY_DOLLAR).append(attributeName).append(Constants.QUERY_COMMA)
					.append("\"").append(value).append("\"").append(
							Constants.QUERY_CLOSING_PARENTHESIS);

		}
		else if (operator.equalsIgnoreCase("EndsWith"))
		{
			newOperator.append("ends-with").append(Constants.QUERY_OPENING_PARENTHESIS).append(
					Constants.QUERY_DOLLAR).append(attributeName).append(Constants.QUERY_COMMA)
					.append("\"").append(value).append("\"").append(
							Constants.QUERY_CLOSING_PARENTHESIS);

		}

		return newOperator.toString();
	}
	
	*/

	/**
	 * @return Will modify the DataType depending on input
	 * according to the database
	 */
	protected String modifyValueForDataType(String value, AttributeTypeInformationInterface dataType)
	{
		StringBuilder actualValue = new StringBuilder();

		if (dataType instanceof StringTypeInformationInterface)
		{
			actualValue.append("\"").append(value).append("\"");
		}
		else if (dataType instanceof DateTypeInformationInterface)
		{
			String actualYear = value.substring(6);
			String actualMonth = value.substring(0, 2);
			String actualDate = value.substring(3, 5);
			StringBuilder newValue = new StringBuilder(actualYear);
			newValue.append("-");
			newValue.append(actualMonth);
			newValue.append("-");
			newValue.append(actualDate);
			actualValue.append("xs:dateTime(\"").append(newValue.toString()).append("T23:59:59\")");
		}
		else
		{
			actualValue.append(value);
		}
		return actualValue.toString();
	}

	/**
	 * @return Create a set of expressions corresponding to the root Element
	 * of each XML
	 */
	private void setMainExpressions()
	{
		mainExpressions = new HashSet<IExpression>();

		for (IExpression expression : constraints)
		{
			List<EntityInterface> mainEntityList = new ArrayList<EntityInterface>();
			EntityInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();

			List<EntityInterface> mainEntities = QueryCSMUtil.getAllMainEntities(entity,
					mainEntityList);

			if (mainEntities.contains(expression.getQueryEntity().getDynamicExtensionsEntity()))
			{
				mainExpressions.add(expression);
			}
		}

	}

	
	/**
	 * 
	 * @return the 'passing' part of SQLXML
	 */
	protected abstract String buildPassingPart();
	

	/**
	 * 
	 * @return Columns part of SQLXML
	 * @throws DataTypeFactoryInitializationException
	 */
	protected abstract String buildColumnsPart() throws DataTypeFactoryInitializationException;
	
	
	protected String getDataTypeInformation(AttributeInterface attribute)
			throws DataTypeFactoryInitializationException
	{
		String returnValue = null;

		DataTypeFactory type = DataTypeFactory.getInstance();
		AttributeTypeInformationInterface dataType = attribute.getAttributeTypeInformation();

		if (dataType instanceof StringTypeInformationInterface)
		{
			returnValue = type
					.getDatabaseDataType(EntityManagerConstantsInterface.STRING_ATTRIBUTE_TYPE);
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

	@Override
	protected String getConditionAttributeName(AttributeInterface attribute, IExpression expression)
	{
		return entityPaths.get(expression) + '/' + attribute.getName();
	}

	protected String getAliasFor(IOutputAttribute attribute)
	{
		return attribute.getAttribute().getName() + "_" + attribute.getExpression().getExpressionId();
	}

	@Override
	protected String getDescriminatorCondition(EntityInterface entity, String aliasFor)
	{
		//for the time being null is sufficient 
		return null;
	}

	protected boolean shouldAddNodeFor(IExpression expression)
	{
		return super.shouldAddNodeFor(expression) ;
	}
	
	protected boolean isContainedExpresion(int expressionId)
	{ 
		boolean isMainExpression = false;
		for (IExpression  exp: mainExpressions)
		{
			if(exp.getExpressionId()==expressionId)
			{
				isMainExpression = true;
				break;
			}
		}
		return !(isMainExpression);
	}

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

		builder.append(attributeName);
		builder.append("[. " + RelationalOperator.getSQL(RelationalOperator.GreaterThanOrEquals)
				+ firstValue);

		builder.append(" " + LogicalOperator.And.toString().toLowerCase() + " . "
				+ RelationalOperator.getSQL(RelationalOperator.LessThanOrEquals) + secondValue
				+ "]");

		return builder.toString();

	}

	protected String processInOperator(ICondition condition, String attributeName)
			throws SqlException
	{
		StringBuilder builder = new StringBuilder(attributeName).append(' ').append(" = ").append(
				' ').append(Constants.QUERY_OPENING_PARENTHESIS);

		for (String value : condition.getValues())
		{
			AttributeTypeInformationInterface dataType = condition.getAttribute()
			.getAttributeTypeInformation();
			if(dataType instanceof StringTypeInformationInterface)
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

}