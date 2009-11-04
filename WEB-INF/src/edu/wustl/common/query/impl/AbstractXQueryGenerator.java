
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
import edu.common.dynamicextensions.domaininterface.TaggedValueInterface;
import edu.common.dynamicextensions.domaininterface.databaseproperties.ConstraintKeyPropertiesInterface;
import edu.common.dynamicextensions.domaininterface.databaseproperties.ConstraintPropertiesInterface;
import edu.common.dynamicextensions.entitymanager.DataTypeFactory;
import edu.common.dynamicextensions.entitymanager.EntityManager;
import edu.common.dynamicextensions.entitymanager.EntityManagerConstantsInterface;
import edu.common.dynamicextensions.entitymanager.EntityManagerInterface;
import edu.common.dynamicextensions.exception.DataTypeFactoryInitializationException;
import edu.common.dynamicextensions.exception.DynamicExtensionsSystemException;
import edu.common.dynamicextensions.processor.ProcessorConstants;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.common.query.exeptions.SQLXMLException;
import edu.wustl.common.query.impl.predicate.PredicateGenerator;
import edu.wustl.common.query.queryobject.util.QueryObjectProcessor;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.exceptions.SqlException;
import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.metadata.associations.IAssociation;
import edu.wustl.common.querysuite.metadata.associations.IIntraModelAssociation;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableObjectInterface;
import edu.wustl.common.querysuite.queryobject.ArithmeticOperator;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IConnector;
import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IExpressionAttribute;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IParameter;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.ITerm;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.querysuite.queryobject.impl.JoinGraph;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.querysuite.utils.QueryUtility;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.metadata.util.DyExtnObjectCloner;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.util.querysuite.IQueryUpdationUtil;
import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * @author juberahamad_patel
 * abstract class from which the xquery generator classes extend
 */
public abstract class AbstractXQueryGenerator extends AbstractQueryGenerator
{

	/**
	 * the suffix used to generate sql column names on the fly, like column0, column1 etc.
	 */
	private int suffix = 0;

	/**
	 * the set of expressions whose entities have a separate XML file, where they are the root element.
	 */
	protected Set<IExpression> mainExpressions;

	/**
	 * the set of expressions whose entities have a separate XML file, where they are the root element.
	 */
	private Set<IExpression> expressionList;

	/**
	 * map of expressions for entities and the xpath used to reach them
	 * xpath could be a variable name or a path.
	 */
	private Map<IExpression, String> entityPaths;

	/**
	 * map of expressions which have one to many relationships with their parents
	 * expressions and their respective target roles.
	 */
	private Map<IExpression, String> targetRoles;

	/**
	 * the map of expressions for which a variable is created in the for clause and the variables.
	 */
	protected Map<IExpression, String> forVariables;

	/**
	 * the selected attributes (ie the ones going in SELECT part) and their aliases.
	 */
	protected Map<IOutputAttribute, String> attributeAliases;

	/**
	 * map of IParameter and the expressions corresponding to their attributes.
	 */
	protected Map<IExpression, HashSet<IParameter<ICondition>>> parameters;

	/**
	 * A boolean value to indicate whether to use a select clause or select distinct.
	 */
	private boolean selectDistinct;

	/**
	 * A List containing all the main Entities.
	 */
	protected List<EntityInterface> allMainEntityList = new ArrayList<EntityInterface>();

	/**
	 * A Map that stores the attributes related to an expression.
	 */
	protected Map<String, HashSet<IOutputAttribute>> attributeExpressionMap = new HashMap<String, HashSet<IOutputAttribute>>();

	//private static org.apache.log4j.Logger logger =Logger.getLogger(XQueryGenerator.class);

	/**
	 * Generates SQL for the given Query Object.
	 * @param query - IQuery
	 * @return the String representing SQL for the given Query object.
	 * @throws MultipleRootsException - in case there are more than 1 root expression
	 * @throws SqlException - SQl Exception
	 * @see edu.wustl.common.querysuite.queryengine.ISqlGenerator#generateSQL
	 * (edu.wustl.common.querysuite.queryobject.IQuery)
	 */

	/*
	 * 	 Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah  02-Jun-2009       Siddharth Shah                 Initial
	 */
	/*
	 * The following function takes IQuery object as input for further processing
	 * @parameters : IQuery query= The query object
	 * @parameters : char QueryType = representing the kind of query whether aggregate or normal
	 *
	 */

	/**
	 * @param query -IQuery
	 * @return XQuery String
	 * @throws QueryModuleException - Exception
	 */
	@Override
	public String generateQuery(IQuery query) throws QueryModuleException
	{
		String formedQuery = null;
		try
		{
			prepare(query);
			formedQuery = formQuery();
		}
		catch (NumberFormatException e)
		{
			throw new QueryModuleException(Constants.QUERY_ERRORMSG + e.getMessage(), e,
					QueryModuleError.GENERIC_EXCEPTION);
		}

		if (!Variables.isExecutingTestCase)
		{
			log(formedQuery);
		}
		return formedQuery;
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah  02-Jun-2009       Siddharth Shah                 Initial
	 */
	/**
	 * prepare the data structures and information required to generate xquery.
	 * This is step I of xquery generation
	 * @param query - IQuery
	 * @throws QueryModuleException - Exception
	 */

	private void prepare(IQuery query) throws QueryModuleException
	{
		// creating the clone of a query as the query might get changed
		// during the processing
		ParameterizedQuery queryClone = (ParameterizedQuery) new DyExtnObjectCloner().clone(query);
		constraints = queryClone.getConstraints();
		this.joinGraph = (JoinGraph) constraints.getJoinGraph();

		QueryObjectProcessor.replaceMultipleParents(constraints);

		aliasAppenderMap = new HashMap<IExpression, Integer>();
		mainExpressions = new LinkedHashSet<IExpression>();
		expressionList = new LinkedHashSet<IExpression>();
		try
		{
			setMainExpressions(joinGraph.getRoot());
			createAliasAppenderMap();

			setSelectedAttributes(queryClone);
			setParameterizedAttributes(queryClone);
			//Populating expresionAttributeMap
			setAttributeExpressionMap(queryClone);

			createEntityPaths();
			checkForEmptyExpression(joinGraph.getRoot().getExpressionId());
		}
		catch (MultipleRootsException e)
		{
			throw new QueryModuleException(Constants.QUERY_ERRORMSG + e.getMessage(), e,
					QueryModuleError.MULTIPLE_ROOT);
		}
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah  02-Jun-2009       Siddharth Shah                 Initial
	 */
	/**
	 * To populate expressionAttribute Map.
	 * @param queryClone - an input to get all the expressions and identifying
	 * 						the attributes selected
	 * @throws MultipleRootsException - when there are more then 1 root expression
	 */

	private void setAttributeExpressionMap(ParameterizedQuery queryClone)
			throws MultipleRootsException
	{

		IExpression rootExpression = joinGraph.getRoot();
		Set<IExpression> expressions = getExpressionList(rootExpression);
		List<IOutputAttribute> attributes = queryClone.getOutputAttributeList();
		for (IExpression expression : expressions)
		{
			HashSet<IOutputAttribute> attributeSet = new HashSet<IOutputAttribute>();
			String expressionAlias = getAliasName(expression);
			for (IOutputAttribute attribute : attributes)
			{
				IExpression atributeExpression = attribute.getExpression();
				String attributeExpressionAlias = getAliasName(atributeExpression);
				if (attributeExpressionAlias.equals(expressionAlias))
				{
					attributeSet.add(attribute);
				}
			}

			attributeExpressionMap.put(expressionAlias, attributeSet);
		}

	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah  02-Jun-2009       Siddharth Shah                 Initial
	 */
	/**
	 * @param expression - traverse the tree and get all the expressions
	 * @return - List of IExpression
	 * @throws MultipleRootsException - when there are more then 1 root expression
	 */

	private Set<IExpression> getExpressionList(IExpression expression)
			throws MultipleRootsException
	{
		expressionList.add(expression);
		for (IExpression expressions : joinGraph.getChildrenList(expression))
		{
			getExpressionList(expressions);
		}
		return expressionList;
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah  02-Jun-2009       Siddharth Shah                 Initial
	 */

	/**
	 * set the parameters and their expressions.
	 * @param query - IQuery
	 */

	private void setParameterizedAttributes(ParameterizedQuery query)
	{
		parameters = new HashMap<IExpression, HashSet<IParameter<ICondition>>>();

		for (IParameter<?> parameter : query.getParameters())
		{
			if (parameter.getParameterizedObject() instanceof ICustomFormula)
			{
				continue;
			}

			if (parameter.getParameterizedObject() instanceof ICondition)
			{
				IExpression expression = QueryUtility.getExpression(
						(IParameter<ICondition>) parameter, query);
				HashSet<IParameter<ICondition>> parameterSet = new HashSet<IParameter<ICondition>>();
				if (parameters.containsKey(expression))
				{
					parameterSet = parameters.get(expression);
					parameterSet.add((IParameter<ICondition>) parameter);
					parameters.remove(expression);
					parameters.put(expression, parameterSet);
				}
				else
				{
					parameterSet.add((IParameter<ICondition>) parameter);
					parameters.put(expression, parameterSet);
				}

			}
		}
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah  02-Jun-2009       Siddharth Shah                 Initial
	 */

	/**
	 * This will set up the select clause of XQuery.
	 * @param query - IQuery
	 */
	private void setSelectedAttributes(ParameterizedQuery query)
	{
		//populate selected attributes and their aliases
		attributeAliases = new LinkedHashMap<IOutputAttribute, String>();
		for (IOutputAttribute selectedAttribute : query.getOutputAttributeList())
		{
			IExpression expression = selectedAttribute.getExpression();
			String expressionAlias = getAttributeExpression(expression);
			StringBuffer attributeAlias = new StringBuffer(Utility.getAliasFor(selectedAttribute
					.getAttribute(), selectedAttribute.getExpression()));
			attributeAlias.insert(0, Constants.QUERY_DOT);
			attributeAlias.insert(0, Constants.QUERY_XML);
			attributeAlias.insert(0, expressionAlias);
			attributeAliases.put(selectedAttribute, attributeAlias.toString());

		}
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah  02-Jun-2009       Siddharth Shah                 Initial
	 */

	/**
	 * get the expression for the attribute.
	 * @param expression - IExpresssion
	 * @return expression corresponding to the attribute
	 */
	private String getAttributeExpression(IExpression expression)
	{
		String alias = "";
		if (mainExpressions.contains(expression))
		{
			alias = getAliasName(expression);
			//			return getAliasName(expression);
		}
		else
		{
			IExpression parentExpression = joinGraph.getParentList(expression).get(0);
			alias = getAttributeExpression(parentExpression);
		}
		return alias;
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    JuberAhamadPatel  20-Dec-2008       Siddharth Shah                 Initial
	 *    Siddharth Shah    20-Mar-2009       Siddharth Shah            Added the condition
	 *    																of adding AND to wherePart in build
	 *      													        WherePart does not return empty string
	 *    Siddharth Shah    2-June-2009       Siddharth Shah            Added Where Part logic for new Structure of XQuery
	 */
	/**
	 * form xquery using the data structures prepared in step I.
	 * This is step II of xquery generation.
	 * @return the formed xquery
	 * @throws QueryModuleException - Exception
	 */

	private String formQuery() throws QueryModuleException
	{
		StringBuilder formedQuery = new StringBuilder();
		formedQuery.append(buildSelectPart());
		try
		{
			String wherePart = buildWherePart(constraints.getRootExpression(), null);
			PredicateGenerator predicateGenerator;
			predicateGenerator = new PredicateGenerator(forVariables, wherePart);
			formedQuery.append(buildFromPart(predicateGenerator));
			formedQuery.append(buildWherePart(predicateGenerator));
		}
		catch (SQLXMLException e)
		{
			throw new QueryModuleException(Constants.QUERY_ERRORMSG + e.getMessage(), e,
					QueryModuleError.SQL_EXCEPTION);
		}
		catch (DynamicExtensionsSystemException e)
		{
			throw new QueryModuleException(Constants.QUERY_ERRORMSG + e.getMessage(), e,
					QueryModuleError.GENERIC_EXCEPTION);
		}
		catch (MultipleRootsException e)
		{
			throw new QueryModuleException(Constants.QUERY_ERRORMSG + e.getMessage(), e,
					QueryModuleError.MULTIPLE_ROOT);
		}
		catch (SqlException e)
		{
			throw new QueryModuleException(Constants.QUERY_ERRORMSG + e.getMessage(), e,
					QueryModuleError.SQL_EXCEPTION);
		}
		return Utility.removeLastAnd(formedQuery.toString());
	}

	/**
	 * @return String representing parametrized condition
	 */
	protected abstract String getParametrizedCondition();

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    JuberAhamadPatel  21-Dec-2008       Siddharth Shah                 Initial
	 */
	/**
	 *  to get the joining conditions between 2 tables.
	 *  @param wherePart - String representation of where clause
	 *  @return Joining conditions
	 *  @throws MultipleRootsException - Exception
	 *  @throws SqlException - Exception
	 *  @throws DynamicExtensionsSystemException - Exception
	 */
	protected String addJoiningTableCondition(String wherePart) throws MultipleRootsException,
			SqlException, DynamicExtensionsSystemException
	{
		StringBuilder completeWherePart = new StringBuilder(wherePart);
		Set<Integer> processedAlias = new HashSet<Integer>();
		IExpression parentExpression = joinGraph.getRoot();
		completeWherePart.append(processChildExpressions(processedAlias, parentExpression));
		return Utility.removeLastAnd(completeWherePart.toString());
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah  02-Jun-2009       Siddharth Shah                 Initial
	 */
	/**
	 * Build the where part for temporal query.
	 * @param predicateGenerator - Predicate Generator Object
	 * @return where part string
	 * @throws SqlException  - SQl Exception
	 * @throws MultipleRootsException - when there are more then 1 root expression
	 * @throws DynamicExtensionsSystemException - DE Exception
	 */

	protected String buildWherePart(PredicateGenerator predicateGenerator)
			throws DynamicExtensionsSystemException, MultipleRootsException, SqlException
	{
		StringBuilder wherePart = new StringBuilder();
		wherePart.append(getParametrizedCondition());
		wherePart.append(setJoiningTableCondition());
		String temporalConditions = predicateGenerator.getXQueryWherePart();

		if (!(wherePart.toString().trim().equals("") && temporalConditions == null))
		{
			wherePart.insert(0, Constants.QUERY_WHERE);
			if (temporalConditions == null)
			{
				wherePart.append("");
			}
			else
			{
				wherePart.append(temporalConditions);
			}
		}

		return Utility.removeLastAnd(wherePart.toString());
	}

	/**
	 * To process all child expression of the given Expression & get their SQL
	 * representation for where part.
	 * @param leftAlias left table alias in join.
	 * @param processedAlias The list of precessed alias.
	 * @param parentExpressionId The reference to expression whose children to
	 *            be processed.
	 * @return the left join sql for children expression.
	 * @throws SqlException when there is error in the passed IQuery object.
	 * @throws DynamicExtensionsSystemException - DE Exception
	 */

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah  02-Jun-2009       Siddharth Shah                 Initial
	 */

	/**
	 * @param entity - the entity whose path is been calculated
	 * @param expression - expression for which the path is to be calculated
	 * @return entity path
	 * @throws DynamicExtensionsSystemException - Exception
	 */
	protected String getEntityPath(EntityInterface entity, IExpression expression)
			throws DynamicExtensionsSystemException
	{
		StringBuffer intermediatePath = new StringBuffer();
		if (!allMainEntityList.contains(entity))
		{
			EntityManagerInterface entityManager = EntityManager.getInstance();
			ArrayList<Long> allIds = (ArrayList<Long>) entityManager
					.getIncomingAssociationIds(entity);
			EntityCache cache = EntityCache.getInstance();
			for (Long id : allIds)
			{
				AssociationInterface associationById = cache.getAssociationById(id);
				EntityInterface associatedEntity = associationById.getEntity();
				if (associatedEntity.equals(expression.getQueryEntity()
						.getDynamicExtensionsEntity()))
				{
					intermediatePath.append('/').append(associationById.getTargetRole().getName());
				}
			}
		}
		return intermediatePath.toString();
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah  02-Jun-2009       Siddharth Shah                 Initial
	 */

	/**
	 * log the sql.
	 * @param sql - String representation of SQLXML
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

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah  02-Jun-2009       Siddharth Shah                 Initial
	 */

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

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah  02-Jun-2009       Siddharth Shah                 Initial
	 */
	/**
	 * the recursive method to traverse down the expression hierachy
	 * and decide paths to reach each entity.
	 * @param expression - IExpression
	 * @param xpath - the path built so far to reach this point
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

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah  02-Jun-2009       Siddharth Shah                 Initial
	 */
	/**
	 * @param predicateGenerator - PredicateGenerator Object
	 * @return From part of SQLXML
	 * @throws MultipleRootsException - when there are more then 1 root expression
	 * @throws SqlException - SQL Exception
	 * @throws QueryModuleException - Exception
	 */

	protected String buildFromPart(PredicateGenerator predicateGenerator)
			throws MultipleRootsException, SqlException, QueryModuleException
	{
		StringBuilder fromPart = new StringBuilder();
		fromPart.append(Constants.QUERY_FROM);

		for (IExpression expression : getMainExpressions())
		{
			StringBuilder columnsPart = new StringBuilder();

			String tableName = expression.getQueryEntity().getDynamicExtensionsEntity()
					.getTableProperties().getName();
			fromPart.append(tableName).append(" ").append(getAliasName(expression)).append(
					Constants.QUERY_RELATIONAL);
			fromPart.append(",").append(Constants.QUERY_XMLTABLE);
			fromPart.append(Constants.QUERY_OPENING_PARENTHESIS + "'");
			try
			{
				fromPart.append(buildXQuery(predicateGenerator, expression));
				fromPart.append("'");
				fromPart.append(buildPassingPart(expression));
				StringBuilder columnPart = new StringBuilder(buildColumnsPart(expression,
						columnsPart));
				if (!(columnPart.toString().equals("")))
				{
					Utility.removeLastComma(columnPart);
					fromPart.append(Constants.QUERY_COLUMN_CLAUSE);
					fromPart.append(columnPart);
				}
			}
			catch (DynamicExtensionsSystemException e)
			{
				throw new QueryModuleException(Constants.QUERY_ERRORMSG + e.getMessage(), e,
						QueryModuleError.GENERIC_EXCEPTION);
			}
			catch (SQLXMLException e)
			{
				throw new QueryModuleException(Constants.QUERY_ERRORMSG + e.getMessage(), e,
						QueryModuleError.SQL_EXCEPTION);
			}
			fromPart.append(Constants.QUERY_CLOSING_PARENTHESIS);
			fromPart.append(" ").append(getAliasName(expression)).append(Constants.QUERY_XML);
			fromPart.append(Constants.QUERY_COMMA);
		}
		Utility.removeLastComma(fromPart);
		return fromPart.toString();
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @param expression - IExpression
	 * @param columnsPart - String represenation of columns clause
	 * @return Columns part of SQLXML
	 * @throws DataTypeFactoryInitializationException - Exception
	 */

	private String buildColumnsPart(IExpression expression, StringBuilder columnsPart)
			throws DataTypeFactoryInitializationException
	{

		String expressionAlias = getAliasName(expression);
		HashSet<IOutputAttribute> attributeList = attributeExpressionMap.get(expressionAlias);
		for (IOutputAttribute attribute : attributeList)
		{
			columnsPart.append(getColumnsClause(attribute, expression));
		}

		getChildColumns(expression, columnsPart);

		//				Utility.removeLastComma(columnsPart);
		return columnsPart.toString();
	}

	/**
	 * @param attribute - IOutputAttribute
	 * @param expression - IExpression
	 * @return Columns Clause
	 * @throws DataTypeFactoryInitializationException - Exception
	 */
	protected abstract String getColumnsClause(IOutputAttribute attribute, IExpression expression)
			throws DataTypeFactoryInitializationException;

	/**
	 * @param predicateGenerator - PredicateGenerator
	 * @param expression - IExpression
	 * @return XQuery
	 * @throws SQLXMLException - Exception
	 * @throws DynamicExtensionsSystemException - Exception
	 * @throws MultipleRootsException - Exception
	 * @throws SqlException - Exception
	 */
	protected abstract String buildXQuery(PredicateGenerator predicateGenerator,
			IExpression expression) throws SQLXMLException, DynamicExtensionsSystemException,
			MultipleRootsException, SqlException;

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * to get the column names for child expression for columns Clause.
	 * @param expression - IExpression
	 * @param columnsPart - columns part of XQuery
	 * @throws DataTypeFactoryInitializationException - Exception
	 */

	private void getChildColumns(IExpression expression, StringBuilder columnsPart)
			throws DataTypeFactoryInitializationException
	{

		List<IExpression> expressions = joinGraph.getChildrenList(expression);
		for (IExpression childExpression : expressions)
		{
			if (!mainExpressions.contains(childExpression))
			{
				buildColumnsPart(childExpression, columnsPart);

			}
		}

	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * build the Passing clause.
	 * @param expression - IExpression
	 * @return Passing Part
	 * @throws DataTypeFactoryInitializationException - Exception
	 */

	private String buildPassingPart(IExpression expression)
			throws DataTypeFactoryInitializationException
	{
		String expressionAlias = getAliasName(expression) + Constants.QUERY_RELATIONAL;
		StringBuilder passingPart = new StringBuilder().append(Constants.QUERY_PASSING).append(" ")
				.append(expressionAlias).append(Constants.QUERY_DOT).append("\"").append(
						Constants.QUERY_XMLDATA).append("\"").append(Constants.AS).append("\"")
				.append(expressionAlias).append("\"").append(Constants.QUERY_COMMA);

		//		passingPart.append(processParametrizedCondition(expression));

		Utility.removeLastComma(passingPart);

		return passingPart.toString();
	}

	//	protected abstract String processParametrizedCondition(IExpression expression)
	//throws DataTypeFactoryInitializationException;

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * return the Select portion of SQLXML Query.
	 * @return Select Part
	 */
	protected String buildSelectPart()
	{

		StringBuilder selectClause = new StringBuilder(256);

		if (selectDistinct)
		{
			selectClause.append(Constants.SELECT_DISTINCT);
		}
		else
		{
			selectClause.append(Constants.SELECT);
		}

		for (Entry<IOutputAttribute, String> entry : attributeAliases.entrySet())
		{
			selectClause.append(entry.getValue());
			String columnAliasName = Constants.QUERY_COLUMN_NAME + suffix;
			selectClause.append(" " + columnAliasName + Constants.QUERY_COMMA);
			attributeColumnNameMap.put(entry.getKey().getAttribute(), columnAliasName);
			suffix++;
		}
		Utility.removeLastComma(selectClause);
		return selectClause.toString();

	}

	

	/**
	 * change the first letter of the Entity Name.
	 * to lower case
	 * @param name - String value for entity
	 * @return - String value for entity
	 */
	protected String deCapitalize(String name)
	{
		StringBuilder builder = new StringBuilder(name);
		String firstLetter = name.substring(0, 1).toLowerCase();
		builder.replace(0, 1, firstLetter);
		return builder.toString();
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @param value - String
	 * @param dataType - AttributeTypeInformationInterface
	 * @param attribute - queryableAttributeInterface
	 * @return Will modify the DataType depending on input
	 * according to the database
	 */
	protected String modifyValueForDataType(String value,
			AttributeTypeInformationInterface dataType, QueryableAttributeInterface attribute)
	{
		StringBuilder actualValue = new StringBuilder();
		// for string type data
		if (dataType instanceof StringTypeInformationInterface)
		{
			actualValue.append("\"").append(getCaseInsensitiveString(value, attribute))
					.append("\"");
		}
		// for Date type data
		else if (dataType instanceof DateTypeInformationInterface)
		{
			// to add time if time stamp is not defined
			actualValue.append(getTimeStamp(value));
		}
		else
		{
			actualValue.append(value);
		}
		return actualValue.toString();
	}

	/**
	 * @param value - String
	 * @param attribute - QueryableAttributeInterface
	 * @return To check the case of String and modify appropriately
	 * based on the attribute
	 */
	private String getCaseInsensitiveString(String value, QueryableAttributeInterface attribute)
	{
		String newValue = value;
		if (attribute.isTagPresent(Constants.TAGGED_VALUE_UPPERCASE))
		{
			newValue = newValue.toUpperCase();
		}
		return newValue;
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @param value - the value of timestamp
	 * @return the timestamp format supported by XQuery
	 */
	private String getTimeStamp(String value)
	{
		// get Months, Years and Days
		String actualYear = value.substring(6, 10);
		String actualMonth = value.substring(0, 2);
		String actualDate = value.substring(3, 5);
		StringBuilder actualValue = new StringBuilder();
		String actualTime = "";
		// to check whether time is defined
		try
		{
			actualTime = value.substring(11);
		}
		catch (Exception e)
		{
			actualTime = "";
		}
		// if time is not defined append 00:00:00
		if (("").equals(actualTime))
		{
			StringBuilder newValue = new StringBuilder(actualYear);
			newValue.append('-');
			newValue.append(actualMonth);
			newValue.append('-');
			newValue.append(actualDate);
			actualValue.append("xs:dateTime(\"").append(newValue.toString()).append("T00:00:00\")");
		}
		// if time is defined append the time
		else
		{
			StringBuilder newValue = new StringBuilder(actualYear);
			newValue.append('-');
			newValue.append(actualMonth);
			newValue.append('-');
			newValue.append(actualDate);
			newValue.append('T' + actualTime);
			actualValue.append("xs:dateTime").append(Constants.QUERY_OPENING_PARENTHESIS).append(
					'"').append(newValue.toString()).append('"').append(
					Constants.QUERY_CLOSING_PARENTHESIS);
		}
		return actualValue.toString();
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * populate the set of main expressions by.
	 * traversing expression tree recursively
	 * @param expression - IExpression
	 */
	private void setMainExpressions(IExpression expression)
	{
		List<QueryableObjectInterface> mainEntityList = new ArrayList<QueryableObjectInterface>();
		QueryableObjectInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();

		List<QueryableObjectInterface> mainEntities = IQueryUpdationUtil.getAllMainEntities(entity,
				mainEntityList);

		if (mainEntities.contains(expression.getQueryEntity().getDynamicExtensionsEntity()))
		{
			mainExpressions.add(expression);
		}

		for (IExpression child : joinGraph.getChildrenList(expression))
		{
			setMainExpressions(child);
		}
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * get the database specific data type for given attribute.
	 * @param attribute - QueryableAttributeInterface
	 * @return Datatype String
	 * @throws DataTypeFactoryInitializationException - Exception
	 */
	protected String getDataTypeInformation(QueryableAttributeInterface attribute)
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
			String format = ((DateTypeInformationInterface)dataType).getFormat();
			if(format.equals(ProcessorConstants.DATE_TIME_FORMAT))
			{
				returnValue = type
					.getDatabaseDataType(EntityManagerConstantsInterface.DATE_TIME_ATTRIBUTE_TYPE);
			}
			else
			{
				returnValue = type
				.getDatabaseDataType(EntityManagerConstantsInterface.DATE_ATTRIBUTE_TYPE);
			}
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

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * get the complete name for given attribute.
	 * @param attribute - QueryableAttributeInterface
	 * @param expression - IExpression
	 * @return path of the attribute
	 */
	@Override
	protected String getConditionAttributeName(QueryableAttributeInterface attribute,
			IExpression expression)
	{
		return entityPaths.get(expression) + '/' + attribute.getName();
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @param attribute - QueryableAttributeInterface
	 * @param expression - IExpression
	 * @return attribute of temporal condition
	 */
	@Override
	protected String getTemporalAttributeName(QueryableAttributeInterface attribute,
			IExpression expression)
	{
		IExpression parentExpression = expression;
		if (!mainExpressions.contains(parentExpression))
		{
			parentExpression = getParentExpression(attribute, parentExpression);

		}

		return getAliasName(parentExpression) + "x." + attribute.getName() + "_"
				+ expression.getExpressionId();
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @param attribute - QueryableAttributeInterface
	 * @param childExpression - IExpression
	 * @return - parent Expression
	 */
	private IExpression getParentExpression(QueryableAttributeInterface attribute,
			IExpression childExpression)
	{
		IExpression parentExpression = joinGraph.getParentList(childExpression).get(0);
		if (!mainExpressions.contains(parentExpression))
		{
			parentExpression = getParentExpression(attribute, parentExpression);
		}
		return parentExpression;
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @param entity - QueryableObjectInterface
	 * @param aliasFor - String for For clause
	 * @return Descriminator String
	 */
	@Override
	protected String getDescriminatorCondition(QueryableObjectInterface entity, String aliasFor)
	{
		String descriminator = null;
		//for the time being null is sufficient
		return descriminator;
	}

	

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * create xquery fragment to represent "between" operator
	 */
	/**
	 * @param condition - ICondition
	 * @param attributeName - Name of Attribute
	 * @return between string
	 * @throws SqlException - Exception
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
		boolean dateType = dataType instanceof DateTypeInformationInterface;
		boolean integerType = dataType instanceof IntegerTypeInformationInterface;
		boolean longType = dataType instanceof LongTypeInformationInterface;
		boolean doubleType = dataType instanceof DoubleTypeInformationInterface;
		boolean typeInformation = dateType || integerType || longType || doubleType;

		if (!(typeInformation))
		{
			throw new SqlException(
					"Incorrect Data type of operand for Between oparator in condition:" + condition);
		}

		String firstValue = modifyValueForDataType(values.get(0), dataType, condition
				.getAttribute());
		String secondValue = modifyValueForDataType(values.get(1), dataType, condition
				.getAttribute());

		builder.append(attributeName).append("[.").append(
				RelationalOperator.getSQL(RelationalOperator.GreaterThanOrEquals)).append(
				firstValue);
		builder.append(Constants.QUERY_AND);
		builder.append('.').append(RelationalOperator.getSQL(RelationalOperator.LessThanOrEquals))
				.append(secondValue).append(']');

		return builder.toString();

	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * create xquery fragment to represent "in" operator.
	 * @param condition - ICondition
	 * @param attributeName - name of attribute
	 * @return IN string
	 * @throws SqlException - Exception
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
				builder.append("\"").append(
						getCaseInsensitiveString(value, condition.getAttribute())).append("\"")
						.append(Constants.QUERY_COMMA);
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

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * create xquery fragment to represent "exists" and "empty" operators.
	 * @param condition - ICondition
	 * @param attributeName - name of attribute
	 * @return NULL CHECK string
	 * @throws SqlException - Exception
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

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * create xquery fragment to represent "contains", "starts-with" and
	 * "ends-with" operators.
	 * @param condition - ICondition
	 * @param attributeName - name of attribute
	 * @return LIKE string
	 * @throws SqlException - Exception
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
			newOperator = "contains(string(" + attributeName + "),\""
					+ getCaseInsensitiveString(value, condition.getAttribute()) + "\")";
		}
		else if (operator.equals(RelationalOperator.StartsWith))
		{
			newOperator = "starts-with(string(" + attributeName + "),\""
					+ getCaseInsensitiveString(value, condition.getAttribute()) + "\")";
		}
		else if (operator.equals(RelationalOperator.EndsWith))
		{
			newOperator = "ends-with(string(" + attributeName + "),\""
					+ getCaseInsensitiveString(value, condition.getAttribute()) + "\")";
		}

		return newOperator;

	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * get the list of children of given expression which are not main expressions.
	 * @param expression - IExpression
	 * @return Non main children
	 */
	protected List<IExpression> getNonMainChildren(IExpression expression)
	{
		List<IExpression> nonMainChildren = new ArrayList<IExpression>(joinGraph
				.getChildrenList(expression));
		nonMainChildren.removeAll(mainExpressions);
		return nonMainChildren;
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @param expression - IExpression
	 * @return - non main children in DAG
	 */
	protected List<IExpression> getNonMainNonEmptyChildren(IExpression expression)
	{
		List<IExpression> children = getNonMainChildren(expression);
		children.removeAll(emptyExpressions);
		return children;

	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @return the mainExpressions
	 */
	protected Set<IExpression> getMainExpressions()
	{
		return mainExpressions;
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @return the entityPaths
	 */
	protected Map<IExpression, String> getEntityPaths()
	{
		return entityPaths;
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @return the targetRoles
	 */
	protected Map<IExpression, String> getTargetRoles()
	{
		return targetRoles;
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @return the forVariables
	 */
	protected Map<IExpression, String> getForVariables()
	{
		return forVariables;
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @return the attributeAliases
	 */
	protected Map<IOutputAttribute, String> getAttributeAliases()
	{
		return attributeAliases;
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @return the parameters
	 */
	protected Map<IExpression, HashSet<IParameter<ICondition>>> getParameters()
	{
		return parameters;
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @param operandquery - String
	 * @return Added a method so that the Parser can identify the temporal query and act accordingly
	 */
	@Override
	protected String getTemporalCondition(String operandquery)
	{
		return "<" + Constants.QUERY_TEMPORAL_CONDITION + ">" + operandquery + "</"
				+ Constants.QUERY_TEMPORAL_CONDITION + ">";
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @param selectDistinct -  the selectDistinct to set
	 */
	protected void setSelectDistinct(boolean selectDistinct)
	{
		this.selectDistinct = selectDistinct;
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @return the selectDistinct
	 */
	protected boolean isSelectDistinct()
	{
		return selectDistinct;
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    01-Apr-2008       Abhijeet Ranadive              Initial
	 */
	/**
	 * This method will modify the Custom Formula provided by the UI.
	 * @param formula - Original ICustomFormula
	 * @return - modified ICustomFormula
	 */

	protected ICustomFormula getNewCustomformula(ICustomFormula formula)
	{
		ICustomFormula newFormula = QueryObjectFactory.createCustomFormula();
		ITerm newRhs1 = QueryObjectFactory.createTerm();
		ITerm newRhs2 = QueryObjectFactory.createTerm();
		ITerm rhs = QueryObjectFactory.createTerm();
		ITerm rhs1 = QueryObjectFactory.createTerm();

		newFormula.setLhs(getFormulaLhs(formula));

		//Get RHS from Custom Formula and add it to the RHS of new Custom Formula
		rhs = formula.getAllRhs().get(0);

		//Get Relational Operator from the formula
		RelationalOperator connector = formula.getOperator();
		newFormula.setOperator(connector);

		// In case the Relational Operator is between we will have 2 attributes in RHS
		if (connector.equals(RelationalOperator.Between))
		{
			rhs1 = formula.getAllRhs().get(1);
		}

		////Get Arithmetic Operator from the formula
		IConnector<ArithmeticOperator> operator = formula.getLhs().getConnector(0, 1);
		if (operator.getOperator().equals(ArithmeticOperator.Minus))
		{

			IConnector<ArithmeticOperator> newOperator = QueryObjectFactory
					.createArithmeticConnector(ArithmeticOperator.Plus);
			if (connector.equals(RelationalOperator.Between))
			{
				newRhs1.addOperand(rhs.getOperand(0));
				newRhs1.addOperand(newOperator, formula.getLhs().getOperand(1));
				newRhs2.addOperand(rhs1.getOperand(0));
				newRhs2.addOperand(newOperator, formula.getLhs().getOperand(1));

			}
			else
			{
				newRhs1.addOperand(rhs.getOperand(0));
				newRhs1.addOperand(newOperator, formula.getLhs().getOperand(1));
			}
			// Add expression to columns Map
			populateAttributeExpressionMap((IExpressionAttribute) formula.getLhs().getOperand(1));

		}
		else if (operator.getOperator().equals(ArithmeticOperator.Plus))
		{
			IConnector<ArithmeticOperator> newOperator = QueryObjectFactory
					.createArithmeticConnector(ArithmeticOperator.Minus);
			if (connector.equals(RelationalOperator.Between))
			{
				newRhs1.addOperand(rhs.getOperand(0));
				newRhs1.addOperand(newOperator, formula.getLhs().getOperand(1));
				newRhs2.addOperand(rhs1.getOperand(0));
				newRhs2.addOperand(newOperator, formula.getLhs().getOperand(1));

			}
			else
			{
				newRhs1.addOperand(rhs.getOperand(0));
				newRhs1.addOperand(newOperator, formula.getLhs().getOperand(1));
			}
			// Add expression to columns Map
			populateAttributeExpressionMap((IExpressionAttribute) formula.getLhs().getOperand(1));
		}
		// Add RHS created to new Formula
		newFormula.addRhs(newRhs1);
		if (connector.equals(RelationalOperator.Between))
		{
			newFormula.addRhs(newRhs2);
		}

		return newFormula;

	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    01-Apr-2008       Abhijeet Ranadive              Initial
	 */
	/**
	 * @param formula - Custome Formula
	 * @return Modify the Custom Formula to Set LHS
	 */
	private ITerm getFormulaLhs(ICustomFormula formula)
	{
		ITerm newLhs = QueryObjectFactory.createTerm();
		//Get LHS from the Custom Formula
		newLhs.addOperand(formula.getLhs().getOperand(0));

		if (newLhs.getOperand(0) instanceof IExpressionAttribute)
		{
			populateAttributeExpressionMap((IExpressionAttribute) formula.getLhs().getOperand(0));
		}
		return newLhs;
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * populating the attributeExpression Map in case of Temporal queries.
	 * as to create columns clause for that expression
	 * @param operand  - IExpressionAttribute
	 */

	private void populateAttributeExpressionMap(IExpressionAttribute operand)
	{
		HashSet<IOutputAttribute> attributeList = new HashSet<IOutputAttribute>();
		QueryableAttributeInterface attribute = operand.getAttribute();
		IExpression expression = operand.getExpression();
		String expressionAlias = getAliasName(expression);

		IOutputAttribute modifiedAttribute = QueryObjectFactory.createOutputAttribute(expression,
				attribute);

		if (attributeExpressionMap.containsKey(expressionAlias))
		{
			attributeList = attributeExpressionMap.get(expressionAlias);
			attributeList.add(modifiedAttribute);
			attributeExpressionMap.remove(expressionAlias);
			attributeExpressionMap.put(expressionAlias, attributeList);
		}
		else
		{
			attributeList.add(modifiedAttribute);
			attributeExpressionMap.put(expressionAlias, attributeList);
		}
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @param processedAlias - alias
	 * @param parentExpression - IExpression
	 * @return joining conditions
	 * @throws SqlException - SQL Exception
	 * @throws DynamicExtensionsSystemException - DE Excpetion
	 */
	private String processChildExpressions(Set<Integer> processedAlias, IExpression parentExpression)
			throws SqlException, DynamicExtensionsSystemException
	{
		StringBuffer buffer = new StringBuffer();
		List<IExpression> children = joinGraph.getChildrenList(parentExpression);

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
				if (!processedAlias.contains(aliasAppenderMap.get(childExpression)))
				{
					ConstraintPropertiesInterface constraintProperties = eavAssociation
							.getConstraintProperties();
					Collection<ConstraintKeyPropertiesInterface> srcCnstrKeyPropColl = constraintProperties
							.getSrcEntityConstraintKeyPropertiesCollection();
					Collection<ConstraintKeyPropertiesInterface> tgtCnstrKeyPropColl = constraintProperties
							.getTgtEntityConstraintKeyPropertiesCollection();

					buffer.append(getJoiningCondition(parentExpression, childExpression,
							srcCnstrKeyPropColl, tgtCnstrKeyPropColl));

				}

				// append from part SQLXML for the next Expressions.
				buffer.append(processChildExpressions(processedAlias, childExpression));
			}
			else
			{
				continue;
			}
		}

		return buffer.toString();
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @param parentExpression - The parent Expression
	 * @param childExpression - The main child expression which is the child expression in DAG
	 * @param srcCnstrKeyPropColl - Source Constraint Key Collection
	 * @param tgtCnstrKeyPropColl - Target Constraint Key Collection
	 * @return joining conditions
	 */

	private String getJoiningCondition(IExpression parentExpression, IExpression childExpression,
			Collection<ConstraintKeyPropertiesInterface> srcCnstrKeyPropColl,
			Collection<ConstraintKeyPropertiesInterface> tgtCnstrKeyPropColl)
	{
		String leftAttribute = null;
		String rightAttribute = null;
		StringBuffer buffer = new StringBuffer();
		//many sides
		for (ConstraintKeyPropertiesInterface cnstrKeyProp : srcCnstrKeyPropColl)
		{
			AttributeInterface primaryKey = cnstrKeyProp.getSrcPrimaryKeyAttribute();
			String leftAliasName = getAliasName(parentExpression) + Constants.QUERY_RELATIONAL;
			String rightAliasName = getAliasName(childExpression) + Constants.QUERY_RELATIONAL;
			leftAttribute = leftAliasName + "."
					+ cnstrKeyProp.getTgtForiegnKeyColumnProperties().getName();
			String tagValue = getPrimaryKeyTaggedValue(primaryKey);

			rightAttribute = rightAliasName + "." + tagValue;
			buffer.append(rightAttribute).append(Constants.QUERY_EQUALS).append(leftAttribute);
			buffer.append(Constants.QUERY_AND);

		}

		// One Side
		for (ConstraintKeyPropertiesInterface cnstrKeyProp : tgtCnstrKeyPropColl)
		{
			AttributeInterface primaryKey = cnstrKeyProp.getSrcPrimaryKeyAttribute();

			String leftAliasName = getAliasName(parentExpression) + Constants.QUERY_RELATIONAL;
			String rightAliasName = getAliasName(childExpression) + Constants.QUERY_RELATIONAL;
			leftAttribute = leftAliasName + "."
					+ cnstrKeyProp.getTgtForiegnKeyColumnProperties().getName();
			String tagValue = getPrimaryKeyTaggedValue(primaryKey);
			leftAttribute = leftAliasName + "." + tagValue;
			rightAttribute = rightAliasName + "."
					+ cnstrKeyProp.getTgtForiegnKeyColumnProperties().getName();
			buffer.append(rightAttribute).append(Constants.QUERY_EQUALS).append(leftAttribute);
			buffer.append(Constants.QUERY_AND);
		}

		return buffer.toString();
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @param primaryKey - AttributeInterface
	 * @return - To return the primary key name in case it has TAGGED_VALUE_PRIMARY_KEY_VALUE tag
	 * 			defined on it
	 */
	private String getPrimaryKeyTaggedValue(AttributeInterface primaryKey)
	{
		Collection<TaggedValueInterface> taggedValues = primaryKey.getTaggedValueCollection();
		String primaryKeyName = "";
		for (TaggedValueInterface value : taggedValues)
		{
			if (value.getKey().equals(Constants.TAGGED_VALUE_PRIMARY_KEY_VALUE))
			{
				primaryKeyName = value.getValue();
				break;
			}
		}

		return primaryKeyName;
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    JuberAhamadPatel  21-Dec-2008       Siddharth Shah                 Initial
	 *    Siddharth Shah    02-Jun-2009       Siddharth Shah                Changes done to create new XQuery Structure
	 */
	/**
	 * add the join condition for 2 tables to the "where" part.
	 * @param wherePart
	 * @return "where" part that has the join condition in it
	 * @throws MultipleRootsException - Exception
	 * @throws SqlException - Exception
	 * @throws DynamicExtensionsSystemException - Exception
	 */

	private String setJoiningTableCondition() throws MultipleRootsException, SqlException,
			DynamicExtensionsSystemException
	{
		StringBuilder completeWherePart = new StringBuilder();
		Set<Integer> processedAlias = new HashSet<Integer>();
		IExpression parentExpression = joinGraph.getRoot();
		completeWherePart.append(processChildExpressions(processedAlias, parentExpression));
		return completeWherePart.toString();
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * Abstract class to identisy whether the attribute is parametrized or not
	 * @param attribute - QueryableAttributeInterface
	 * @param expression - IExpression
	 * @return boolean
	 */
	protected abstract boolean isParametrizedAttribute(QueryableAttributeInterface attribute,
			IExpression expression);

}
