
package edu.wustl.common.query.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import edu.common.dynamicextensions.domain.BooleanAttributeTypeInformation;
import edu.common.dynamicextensions.domaininterface.AssociationInterface;
import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.AttributeTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.BooleanTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.DateTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.DoubleTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.common.dynamicextensions.domaininterface.FileTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.IntegerTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.LongTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.StringTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.databaseproperties.ConstraintPropertiesInterface;
import edu.common.dynamicextensions.entitymanager.EntityManager;
import edu.common.dynamicextensions.util.global.Constants.InheritanceStrategy;
import edu.wustl.cab2b.server.category.CategoryOperations;
import edu.wustl.cab2b.server.queryengine.querybuilders.CategoryPreprocessor;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.query.queryobject.util.InheritanceUtils;
import edu.wustl.common.query.queryobject.util.QueryObjectProcessor;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.exceptions.SqlException;
import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.metadata.associations.IAssociation;
import edu.wustl.common.querysuite.metadata.associations.IIntraModelAssociation;
import edu.wustl.common.querysuite.metadata.category.Category;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IConnector;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IOutputTerm;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.IQueryEntity;
import edu.wustl.common.querysuite.queryobject.IRule;
import edu.wustl.common.querysuite.queryobject.LogicalOperator;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.querysuite.queryobject.TimeInterval;
import edu.wustl.common.querysuite.queryobject.impl.Expression;
import edu.wustl.common.querysuite.queryobject.impl.JoinGraph;
import edu.wustl.common.util.Utility;
import edu.wustl.common.util.global.Validator;
import edu.wustl.common.util.global.Variables;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.util.global.Constants;

/**
 * To generate SQL from the given Query Object.
 * 
 * @author prafull_kadam
 * 
 */
public class SqlGenerator extends QueryGenerator
{

	/**
	 * Default Constructor to instantiate SQL generator object.
	 */
	public SqlGenerator()
	{
	}

	/**
	 * Generates SQL for the given Query Object.
	 * 
	 * @param query The Reference to Query Object.
	 * @return the String representing SQL for the given Query object.
	 * @throws MultipleRootsException When there are multpile roots present in a
	 *             graph.
	 * @throws SqlException When there is error in the passed IQuery object.
	 * @see edu.wustl.common.querysuite.queryengine.ISqlGenerator#generateSQL(edu.wustl.common.querysuite.queryobject.IQuery)
	 */
	public String generateQuery(IQuery query) throws MultipleRootsException, SqlException,
			RuntimeException
	{
		Logger.out.debug("Srarted SqlGenerator.generateSQL().....");
		String sql = buildQuery(query);
		Logger.out.debug("Finished SqlGenerator.generateSQL()...SQL:" + sql);
		return sql;
	}

	/**
	 * Changes made for adding new rule in expression which do not have rule
	 * provided that expression contains Activity status attribute.
	 * 
	 * @param expressionId Root Expression Id
	 */
	private void addactivityStatusToEmptExpr(int expressionId)
	{
		Expression expression = (Expression) constraints.getExpression(expressionId);

		List<IExpression> operandList = joinGraph.getChildrenList(expression);
		for (IExpression subExpression : operandList)
		{
			// TODO check this code.
			// if (subExpression.isSubExpressionOperand()) {
			addactivityStatusToEmptExpr(subExpression.getExpressionId());
			// }
		}

		if (!expression.containsRule())
		{
			if (getActivityStatusAttribute(expression.getQueryEntity().getDynamicExtensionsEntity()) != null)
			{
				IRule rule = QueryObjectFactory.createRule();
				IConnector<LogicalOperator> logicalConnector = QueryObjectFactory
						.createLogicalConnector(LogicalOperator.And);
				expression.addOperand(0, rule, logicalConnector);
			}
		}
	}

	/**
	 * To initialize map the variables. & build the SQL for the Given Query
	 * Object.
	 * 
	 * @param query the IQuery reference.
	 * @return The Root Expetssion of the IQuery.
	 * @throws MultipleRootsException When there exists multiple roots in
	 *             joingraph.
	 * @throws SqlException When there is error in the passed IQuery object.
	 */
	String buildQuery(IQuery query) throws MultipleRootsException, SqlException, RuntimeException
	{
		IQuery queryClone = new DyExtnObjectCloner().clone(query);
		// IQuery queryClone = query;
		constraints = queryClone.getConstraints();

		QueryObjectProcessor.replaceMultipleParents(constraints);

		processExpressionsWithCategories(queryClone);

		this.joinGraph = (JoinGraph) constraints.getJoinGraph();
		IExpression rootExpression = constraints.getRootExpression();

		// Initializin map variables.
		
		aliasNameMap = new HashMap<String, String>();
		createAliasAppenderMap();

		addactivityStatusToEmptExpr(rootExpression.getExpressionId());
		// Identifying empty Expressions.
		emptyExpressions = new HashSet<IExpression>();
		checkForEmptyExpression(rootExpression.getExpressionId());

		//pAndExpressions = new HashSet<IExpression>();

		// Generating output tree.
		createTree();

		// Creating SQL.
		String wherePart = getCompleteWherePart(rootExpression);

		String fromPart = getFromPartSQL(rootExpression, null, new HashSet<Integer>());
		StringBuilder selectPart = new StringBuilder(getSelectPart());
		// SRINATH
		if (selectPart.length() > 0)
		{
			selectPart.append(Constants.QUERY_COMMA);
		}
		selectPart.append(getSelectForOutputTerms(queryClone.getOutputTerms()));
		removeLastComma(selectPart);

		String sql = selectPart + " " + fromPart + " " + wherePart;

		log(sql);
		return sql;
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

	/**
	 * Returns complete where part  //including PAND conditions.
	 * 
	 * @param rootExpression
	 * @return
	 * @throws SqlException
	 */
	private String getCompleteWherePart(IExpression rootExpression) throws SqlException,
			RuntimeException
	{

		String wherePart = buildWherePart(rootExpression, null);

		// Adding extra where condition for PAND to check activity status value
		// as disabled or null
		StringBuffer extraWherePAnd = new StringBuffer();

		Set<Integer> expressionIDs = new HashSet<Integer>(); // set to hold
		// values of
		// aliasAppender,
		// so that
		// duplicate
		// condition
		// should not
		// get added in
		// Query.
		/*for (IExpression expression : pAndExpressions)
		{
			if (expressionIDs.add(aliasAppenderMap.get(expression)))
			{
				AttributeInterface attributeObj = getActivityStatusAttribute(expression
						.getQueryEntity().getDynamicExtensionsEntity());
				if (attributeObj != null)
				{
					// creating activityStatus is null condition, this is
					// required in case of Pseudo-Anded expressions.
					ICondition condition = QueryObjectFactory.createCondition(attributeObj,
							RelationalOperator.IsNull, null);
					extraWherePAnd.append('(').append(processOperator(condition, expression)).append(" OR ");

					// creating activityStatus != disabled condition.
					condition = createActivityStatusCondition(attributeObj);
					extraWherePAnd.append(processOperator(condition, expression));
					extraWherePAnd.append(')').append(LogicalOperator.And).append(' ');
				}
			}
			// expression.getQueryEntity()
		}*/
		wherePart = Constants.WHERE + extraWherePAnd.toString() + wherePart;
		return wherePart;
	}

	/**
	 * Creates condition ActivitiStatus!='disabled'
	 * 
	 * @param attributeObj
	 * @return
	 */
	private ICondition createActivityStatusCondition(AttributeInterface attributeObj)
	{
		List<String> values = new ArrayList<String>();
		values.add(Constants.ACTIVITY_STATUS_DISABLED);
		ICondition condition = QueryObjectFactory.createCondition(attributeObj,
				RelationalOperator.NotEquals, values);
		return condition;
	}

	/**
	 * To handle Expressions constrained on Categories. If Query contains an
	 * Expression having Constraint Entity as Category, then that Expression is
	 * expanded in such a way that it will look as if it is constrained on
	 * Classes without changing Query criteria.
	 * 
	 * @throws SqlException if there is any error in processing category.
	 */
	private void processExpressionsWithCategories(IQuery query) throws SqlException
	{
		if (containsCategrory(constraints))
		{
			Connection connection = null;
			try
			{
				EntityInterface rootEntity = null;
				EntityInterface rootDEEntity = constraints.getRootExpression().getQueryEntity()
						.getDynamicExtensionsEntity();
				boolean isCategory = edu.wustl.cab2b.common.util.Utility.isCategory(rootDEEntity);

				// This is temporary work around, This connection parameter will
				// be reomoved in future.
				InitialContext context = new InitialContext();
				DataSource dataSource = (DataSource) context.lookup("java:/catissuecore");
				connection = dataSource.getConnection();

				/**
				 * if the root entity itself is category, then get the root
				 * entity of the category & pass it to the processCategory()
				 * method.
				 */
				if (isCategory)
				{
					Category category = new CategoryOperations().getCategoryByEntityId(rootDEEntity
							.getId(), connection);
					rootEntity = EntityManager.getInstance().getEntityByIdentifier(
							category.getRootClass().getDeEntityId());
				}
				else
				{
					rootEntity = rootDEEntity;
				}
				new CategoryPreprocessor().processCategories(query);
			}
			catch (Exception e)
			{
				Logger.out.error(e.getMessage(), e);
				throw new SqlException("Error in preprocessing category!!!!", e);
			}
			finally
			{
				if (connection != null) // Closing connection.
				{
					try
					{
						connection.close();
					}
					catch (SQLException e)
					{
						Logger.out.error(e.getMessage(), e);
						throw new SqlException(
								"Error in closing connection while preprocessing category!!!!", e);
					}
				}
			}
		}
	}

	/**
	 * To check whether there is any Expression having Constraint Entity as
	 * category or not.
	 * 
	 * @param theConstraints reference to IConstraints of the Query object.
	 * @return true if there is any constraint put on category.
	 */
	private boolean containsCategrory(IConstraints theConstraints)
	{
		Set<IQueryEntity> constraintEntities = constraints.getQueryEntities();
		for (IQueryEntity entity : constraintEntities)
		{
			boolean isCategory = edu.wustl.cab2b.common.util.Utility.isCategory(entity
					.getDynamicExtensionsEntity());
			if (isCategory)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * To get the select part of the SQL.
	 * 
	 * @return The SQL for the select part of the query.
	 */
	private String getSelectPart()
	{
		// columnMapList = new
		// ArrayList<Map<Long,Map<AttributeInterface,String>>>();
		StringBuilder selectAttribute = new StringBuilder();
		for (OutputTreeDataNode rootOutputTreeNode : rootOutputTreeNodeList)
		{
			selectAttribute.append(getSelectAttributes(rootOutputTreeNode));
		}
		//Deepti : added quick fix for bug 6950. Add distinct only when columns do not include CLOB type.
		if (containsCLOBTypeColumn)
		{
			selectAttribute.insert(0, Constants.SELECT);
		}
		else
		{
			selectAttribute.insert(0, Constants.SELECT_DISTINCT);
		}
		
		removeLastComma(selectAttribute);
		return selectAttribute.toString();
	}


	/**
	 * To get the From clause of the Query.
	 * 
	 * @param expression The Root Expression.
	 * @param leftAlias the String representing alias of left table. This will
	 *            be alias of table represented by Parent Expression. Will be
	 *            null for the Root Expression.
	 * @param processedAlias The set of aliases processed.
	 * @return the From clause of the SQL.
	 * @throws SqlException When there is problem in creating from part. problem
	 *             can be like: no primary key found in entity for join.
	 */
	private String getFromPartSQL(IExpression expression, String leftAlias, Set<Integer> processedAlias)
			throws SqlException
	{
		StringBuffer buffer = new StringBuffer();

		if (processedAlias.isEmpty()) // this will be true only for root node.
		{
			EntityInterface leftEntity = expression.getQueryEntity().getDynamicExtensionsEntity();
			leftAlias = getAliasName(expression);
			buffer.append(Constants.FROM + leftEntity.getTableProperties().getName() + " " + leftAlias);

			createFromPartForDerivedEntity(expression, buffer);
		}

		Integer parentExpressionAliasAppender = aliasAppenderMap.get(expression);
		processedAlias.add(parentExpressionAliasAppender);

		// Processing children
		buffer.append(processChildExpressions(leftAlias, processedAlias, expression));
		return buffer.toString();
	}

	/**
	 * To create From path for the deirved entity.
	 * 
	 * @param expression the reference to expression.
	 * @param buffer The buffer to which the Output will be appended.
	 * @throws SqlException
	 */
	private void createFromPartForDerivedEntity(IExpression expression, StringBuffer buffer)
			throws SqlException
	{
		EntityInterface leftEntity = expression.getQueryEntity().getDynamicExtensionsEntity();
		EntityInterface superClassEntity = leftEntity.getParentEntity();
		// processing Parent class heirarchy.
		if (superClassEntity != null)
		{
			EntityInterface theLeftEntity = leftEntity;
			while (superClassEntity != null)
			{
				InheritanceStrategy inheritanceType = theLeftEntity.getInheritanceStrategy();
				if (InheritanceStrategy.TABLE_PER_SUB_CLASS.equals(inheritanceType)) // only
				// need
				// to
				// handle
				// this
				// type
				// of
				// inheritance
				// here.
				{
					AttributeInterface primaryKey = getPrimaryKey(theLeftEntity);
					String primaryKeyColumnName = primaryKey.getColumnProperties().getName();
					String subClassAlias = getAliasFor(expression, theLeftEntity);
					String superClassAlias = getAliasFor(expression, superClassEntity);
					buffer.append(Constants.LEFT_JOIN + superClassEntity.getTableProperties().getName()
							+ ' ' + superClassAlias + Constants.ON);
					String leftAttribute = subClassAlias + Constants.QUERY_DOT + primaryKeyColumnName;
					String rightAttribute = superClassAlias + Constants.QUERY_DOT + primaryKeyColumnName;
					buffer.append(Constants.QUERY_DOT + leftAttribute + Constants.QUERY_EQUALS + rightAttribute + Constants.QUERY_CLOSING_PARENTHESIS);
				}
				theLeftEntity = superClassEntity;
				superClassEntity = superClassEntity.getParentEntity();
			}
		}
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
	 */
	private String processChildExpressions(String leftAlias, Set<Integer> processedAlias,
			IExpression parentExpression) throws SqlException
	{
		StringBuffer buffer = new StringBuffer();

		List<IExpression> children = joinGraph.getChildrenList(parentExpression);
		if (!children.isEmpty())
		{
			// processing all outgoing edges/nodes from the current node in the
			// joingraph.
			for (IExpression childExpression : children)
			{
				// IExpression childExpression =
				// constraints.getExpression(childExpressionId);

				IAssociation association = joinGraph.getAssociation(parentExpression,
						childExpression);

				AssociationInterface actualEavAssociation = ((IIntraModelAssociation) association)
						.getDynamicExtensionsAssociation();
				AssociationInterface eavAssociation = actualEavAssociation;
				EntityInterface rightEntity = eavAssociation.getTargetEntity();
				String actualRightAlias = getAliasFor(childExpression, rightEntity);
				String rightAlias = actualRightAlias;
				if (!processedAlias.contains(aliasAppenderMap.get(childExpression)))
				{
					if (InheritanceUtils.getInstance().isInherited(eavAssociation))
					{
						eavAssociation = InheritanceUtils.getInstance().getActualAassociation(
								eavAssociation);
						rightEntity = eavAssociation.getTargetEntity();

						leftAlias = getAliasFor(parentExpression, eavAssociation.getEntity());
						rightAlias = getAliasFor(childExpression, eavAssociation.getTargetEntity());
					}
					else
					{
						leftAlias = getAliasFor(parentExpression, eavAssociation.getEntity());
					}

					EntityInterface childEntity = childExpression.getQueryEntity()
							.getDynamicExtensionsEntity();

					EntityInterface leftEntity = eavAssociation.getEntity();

					ConstraintPropertiesInterface constraintProperties = eavAssociation
							.getConstraintProperties();
					if (constraintProperties.getSourceEntityKey() != null
							&& constraintProperties.getTargetEntityKey() != null)// Many
					// to
					// Many
					// Case
					{

						String leftAttribute = null;
						String rightAttribute = null;

						String middleTableName = constraintProperties.getName();
						String middleTableAlias = getAliasForMiddleTable(childExpression,
								middleTableName);

						AttributeInterface primaryKey = getPrimaryKey(leftEntity);
						leftAttribute = leftAlias + Constants.QUERY_DOT
								+ primaryKey.getColumnProperties().getName();

						rightAttribute = middleTableAlias + Constants.QUERY_DOT
								+ constraintProperties.getSourceEntityKey();
						// Forming joing with middle table.
						buffer.append(Constants.LEFT_JOIN + middleTableName + " " + middleTableAlias
								+ Constants.ON);
						buffer.append(Constants.QUERY_OPENING_PARENTHESIS + leftAttribute + Constants.QUERY_EQUALS + rightAttribute);

						/*
						 * Adding descriminator column condition for the 1st
						 * parent node while forming FROM part left joins. This
						 * will be executed only once i.e. when only one node is
						 * processed.
						 */
						if (processedAlias.size() == 1)
						{
							buffer.append(getDescriminatorCondition(actualEavAssociation
									.getEntity(), leftAlias));
						}
						buffer.append(Constants.QUERY_CLOSING_PARENTHESIS);

						// Forming join with child table.
						leftAttribute = middleTableAlias + Constants.QUERY_DOT
								+ constraintProperties.getTargetEntityKey();
						primaryKey = getPrimaryKey(rightEntity);
						rightAttribute = rightAlias + Constants.QUERY_DOT
								+ primaryKey.getColumnProperties().getName();

						buffer.append(Constants.LEFT_JOIN + rightEntity.getTableProperties().getName()
								+ " " + rightAlias + Constants.ON);
						buffer.append(Constants.QUERY_OPENING_PARENTHESIS + leftAttribute + Constants.QUERY_EQUALS + rightAttribute);

						/*
						 * Adding descriminator column condition for the child
						 * node while forming FROM part left joins.
						 */
						buffer.append(getDescriminatorCondition(actualEavAssociation
								.getTargetEntity(), rightAlias)
								+ Constants.QUERY_CLOSING_PARENTHESIS);
					}
					else
					{
						String leftAttribute = null;
						String rightAttribute = null;
						if (constraintProperties.getSourceEntityKey() != null)// Many
						// Side
						{
							leftAttribute = leftAlias + Constants.QUERY_DOT
									+ constraintProperties.getSourceEntityKey();
							AttributeInterface primaryKey = getPrimaryKey(rightEntity);
							rightAttribute = rightAlias + Constants.QUERY_DOT
									+ primaryKey.getColumnProperties().getName();
						}
						else
						// One Side
						{
							AttributeInterface primaryKey = getPrimaryKey(leftEntity);
							leftAttribute = leftAlias + Constants.QUERY_DOT
									+ primaryKey.getColumnProperties().getName();
							rightAttribute = rightAlias + Constants.QUERY_DOT
									+ constraintProperties.getTargetEntityKey();
						}
						buffer.append(Constants.LEFT_JOIN + rightEntity.getTableProperties().getName()
								+ " " + rightAlias + Constants.ON);
						buffer.append(Constants.QUERY_OPENING_PARENTHESIS + leftAttribute + Constants.QUERY_EQUALS + rightAttribute);

						/*
						 * Adding descriminator column condition for the 1st
						 * parent node while forming FROM part left joins. This
						 * will be executed only once i.e. when only one node is
						 * processed.
						 */
						if (processedAlias.size() == 1)
						{
							buffer.append(getDescriminatorCondition(actualEavAssociation
									.getEntity(), leftAlias));
						}
						/*
						 * Adding descriminator column condition for the child
						 * node while forming FROM part left joins.
						 */
						buffer.append(getDescriminatorCondition(actualEavAssociation
								.getTargetEntity(), rightAlias)
								+ Constants.QUERY_CLOSING_PARENTHESIS);
					}

					buffer.append(getParentHeirarchy(childExpression, childEntity, rightEntity));
				}
				// append from part SQL for the next Expressions.
				buffer.append(getFromPartSQL(childExpression, actualRightAlias, processedAlias));
			}
		}
		return buffer.toString();
	}

	/**
	 * To get the SQL for the descriminator column condition for the given
	 * entity. It will return SQL for condition in format: " AND
	 * <DescriminatorColumnName> = '<DescriminatorColumnValue>'"
	 * 
	 * @param entity The reference to the entity.
	 * @param aliasName The alias Name assigned to that entity table in the SQL.
	 * @return The String representing SQL for the descriminator column
	 *         condition for the given entity, if inheritance strategy is
	 *         TABLE_PER_HEIRARCHY. Returns empty String if there is no
	 *         Descriminator column condition present for the Entity. i.e. when
	 *         either of following is true: 1. when entity is not derived
	 *         entity.(Parent entity is null) 2. Inheritance strategy is not
	 *         TABLE_PER_HEIRARCHY.
	 */
	protected String getDescriminatorCondition(EntityInterface entity, String aliasName)
	{
		String sql = null;
		EntityInterface parentEntity = entity.getParentEntity();
		// Checking whether the entity is derived or not.
		if (parentEntity != null)
		{
			InheritanceStrategy inheritanceType = entity.getInheritanceStrategy();
			if (inheritanceType.equals(InheritanceStrategy.TABLE_PER_HEIRARCHY))
			{
				String columnName = entity.getDiscriminatorColumn();
				String columnValue = entity.getDiscriminatorValue();
				// Assuming Discrimanator is of type String.
				String condition = aliasName + Constants.QUERY_DOT + columnName + Constants.QUERY_EQUALS + "'" + columnValue + "'";
				sql = " " + LogicalOperator.And + " " + condition;
			}
		}
		return sql;
	}

	/**
	 * To get the alias name for the Many to Many table.
	 * 
	 * @param childExpression The child Expression of the association.
	 * @param middleTableName The Many to Mant table name.
	 * @return The String representing aliasName for the Many to Many table.
	 */
	private String getAliasForMiddleTable(IExpression childExpression, String middleTableName)
	{
		return getAliasForClassName(Constants.QUERY_DOT + middleTableName) + Constants.QUERY_UNDERSCORE
				+ aliasAppenderMap.get(childExpression);
	}

	/**
	 * To add the Parent Heirarchy to the join part.
	 * 
	 * @param childExpression The Expression to which the entity belongs.
	 * @param childEntity The entity whose parent heirarchy to be joined.
	 * @param alreadyAddedEntity The entity already added in join part.
	 * @return left join sql for childEntity.
	 * @throws SqlException when there is error in the passed IQuery object.
	 */
	private String getParentHeirarchy(IExpression childExpression, EntityInterface childEntity,
			EntityInterface alreadyAddedEntity) throws SqlException
	{
		StringBuffer combinedJoinPart = new StringBuffer();
		if (childEntity.getParentEntity() != null) // Joining Parent & child
		// classes of the entity.
		{
			EntityInterface entity = childEntity;
			EntityInterface parent = childEntity.getParentEntity();
			boolean isReverse = false;
			List<String> joinSqlList = new ArrayList<String>();
			while (parent != null)
			{
				if (entity.equals(alreadyAddedEntity))
				{
					isReverse = true;
				}

				if (entity.getInheritanceStrategy().equals(InheritanceStrategy.TABLE_PER_SUB_CLASS))
				{
					String leftEntityalias = getAliasFor(childExpression, entity);
					String rightEntityalias = getAliasFor(childExpression, parent);
					AttributeInterface primaryKey = getPrimaryKey(entity);
					String primaryKeyColumnName = primaryKey.getColumnProperties().getName();

					String leftAttributeColumn = leftEntityalias + Constants.QUERY_DOT + primaryKeyColumnName;
					String rightAttributeColumn = rightEntityalias + Constants.QUERY_DOT + primaryKeyColumnName;
					String sql = null;
					if (isReverse)
					{
						sql = Constants.INNER_JOIN + parent.getTableProperties().getName() + " "
								+ rightEntityalias + Constants.ON;
						sql += Constants.QUERY_OPENING_PARENTHESIS + leftAttributeColumn + Constants.QUERY_EQUALS + rightAttributeColumn + Constants.QUERY_CLOSING_PARENTHESIS;
					}
					else
					{
						sql = Constants.INNER_JOIN + entity.getTableProperties().getName() + " "
								+ leftEntityalias + Constants.ON;
						sql += Constants.QUERY_OPENING_PARENTHESIS + rightAttributeColumn + Constants.QUERY_EQUALS + leftAttributeColumn + Constants.QUERY_CLOSING_PARENTHESIS;
					}
					// joinSqlList.add(0, sql);
					joinSqlList.add(sql);

				}
				entity = parent;
				parent = parent.getParentEntity();
			}

			if (isReverse)
			{
				for (String joinSql : joinSqlList)
				{
					combinedJoinPart.append(joinSql);
				}
			}
			else
			{
				for (String joinSql : joinSqlList)
				{
					combinedJoinPart.insert(0, joinSql);
				}
			}

		}
		return combinedJoinPart.toString();
	}

		
	
	/**
	 * To form the Pseudo-And condition for the expression.
	 * 
	 * @param expression The child Expression reference.
	 * @param parentExpression The parent Expression.
	 * @param eavAssociation The association between parent & child expression.
	 * @return The Pseudo-And SQL condition.
	 * @throws SqlException When there is problem in creating from part. problem
	 *             can be like: no primary key found in entity for join.
	 */
	protected String createPseudoAndCondition(IExpression expression, IExpression parentExpression,
			AssociationInterface eavAssociation) throws SqlException
	{
		String pseudoAndSQL;
		EntityInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();
		String tableName = entity.getTableProperties().getName() + ' ';
		String leftAlias = getAliasName(expression);
		String selectAttribute = leftAlias + '.';

		ConstraintPropertiesInterface constraintProperties = eavAssociation
				.getConstraintProperties();
		if (constraintProperties.getSourceEntityKey() != null
				&& constraintProperties.getTargetEntityKey() != null)// Many
		// to
		// many
		// case.
		{
			// This will start FROM part of SQL from the parent table.
			selectAttribute = getAliasName(parentExpression)
					+ '.'
					+ getPrimaryKey(parentExpression.getQueryEntity().getDynamicExtensionsEntity())
							.getColumnProperties().getName();
			pseudoAndSQL = Constants.SELECT + selectAttribute;
			Set<Integer> processedAlias = new HashSet<Integer>();
			String fromPart = getFromPartSQL(parentExpression, leftAlias, processedAlias);
			pseudoAndSQL += ' ' + fromPart + Constants.WHERE;
		}
		else
		{
			if (constraintProperties.getTargetEntityKey() == null)
			{
				selectAttribute += getPrimaryKey(entity).getColumnProperties().getName();
			}
			else
			{
				selectAttribute += constraintProperties.getTargetEntityKey();
			}
			pseudoAndSQL = Constants.SELECT + selectAttribute;
			Set<Integer> processedAlias = new HashSet<Integer>();
			processedAlias.add(aliasAppenderMap.get(expression));
			String fromPart = getFromPartSQL(expression, leftAlias, processedAlias);
			StringBuffer buffer = new StringBuffer();
			buffer.append(Constants.FROM).append(tableName).append(' ').append(leftAlias);
			createFromPartForDerivedEntity(expression, buffer);
			buffer.append(fromPart).append(Constants.WHERE);
			pseudoAndSQL += buffer.toString();
		}
		return pseudoAndSQL;
	}



	public void addActivityStatusCondition(IRule rule)
	{
		IExpression expression = rule.getContainingExpression();
		AttributeInterface attributeObj = getActivityStatusAttribute(expression.getQueryEntity()
				.getDynamicExtensionsEntity());

		if (attributeObj != null)
		{
			ICondition condition = createActivityStatusCondition(attributeObj);
			rule.addCondition(condition);
		}
	}
	
	/**
	 * Check for activity status present in entity.
	 * 
	 * @param entityInterfaceObj The Entity for which we required to check if
	 *            activity status present.
	 * @return Reference to the AttributeInterface if activityStatus attribute
	 *         present in the entity, else null.
	 */
	private AttributeInterface getActivityStatusAttribute(EntityInterface entityInterfaceObj)
	{
		Collection<AttributeInterface> attributes = entityInterfaceObj
				.getEntityAttributesForQuery();

		for (AttributeInterface attribute : attributes)
		{
			if (attribute.getName().equals(Constants.ACTIVITY_STATUS))
			{
				return attribute;
			}
		}
		return null;
	}

	/**
	 * Get the query specific representation for Attribute ie the LHS of a condition.
	 * 
	 * @param attribute The reference to AttributeInterface
	 * @param expression The reference to Expression to which this attribute
	 *            belongs.
	 * @return The query specific representation for Attribute.
	 */
	protected String getConditionAttributeName(AttributeInterface attribute, IExpression expression)
	{

		AttributeInterface actualAttribute = attribute;

		if (InheritanceUtils.getInstance().isInherited(attribute))
		{
			actualAttribute = InheritanceUtils.getInstance().getActualAttribute(attribute);
		}
		EntityInterface attributeEntity = actualAttribute.getEntity();
		String aliasName = getAliasFor(expression, attributeEntity);

		String attributeName = aliasName + Constants.QUERY_DOT
				+ actualAttribute.getColumnProperties().getName();

		return attributeName;
	}

	
	/**
	 * To Modify value as per the Data type. 1. In case of String datatype,
	 * replace occurence of single quote by singlequote twice. 2. Enclose the
	 * Given values by single Quotes for String & Date Data type. 3. For Boolean
	 * DataType it will change value to 1 if its TRUE, else 0.
	 * 
	 * @param value the Modified value.
	 * @param dataType The DataType of the passed value.
	 * @return The String representing encoded value for the given value &
	 *         datatype.
	 * @throws SqlException when there is problem with the values, for Ex.
	 *             unable to parse date/integer/double etc.
	 */
	protected String modifyValueForDataType(String value, AttributeTypeInformationInterface dataType)
			throws SqlException
	{

		if (dataType instanceof StringTypeInformationInterface)// for data type
		// String it will be enclosed in single quote.
		{
			value = value.replaceAll("'", "''");
			value = "'" + value + "'";
		}
		else if (dataType instanceof DateTypeInformationInterface) // for
		// data type date it will be enclosed in single quote.
		{
			try
			{
				Date date = new Date();
				date = Utility.parseDate(value);

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				value = (calendar.get(Calendar.MONTH) + 1) + "-"
						+ calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.YEAR);

				String strToDateFunction = Variables.strTodateFunction;
				if (strToDateFunction == null || strToDateFunction.trim().equals(""))
				{
					strToDateFunction = Constants.STR_TO_DATE; // using MySQL function
					// if the Value is not
					// defined.
				}

				String datePattern = Variables.datePattern;
				if (datePattern == null || datePattern.trim().equals(""))
				{
					datePattern = "%m-%d-%Y"; // using MySQL function if the
					// Value is not defined.
				}
				value = strToDateFunction + "('" + value + "','" + datePattern + "')";
			}
			catch (ParseException parseExp)
			{
				Logger.out.error(parseExp.getMessage(), parseExp);
				throw new SqlException(parseExp.getMessage(), parseExp);
			}

		}
		else if (dataType instanceof BooleanTypeInformationInterface) // defining
		// value
		// for
		// boolean
		// datatype.
		{
			if (value == null
					|| !(value.equalsIgnoreCase(Constants.TRUE) || value
							.equalsIgnoreCase(Constants.FALSE)))
			{
				throw new SqlException(
						"Incorrect value found in value part for boolean operator!!!");
			}
			if (value.equalsIgnoreCase(Constants.TRUE))
			{
				value = "1";
			}
			else
			{
				value = "0";
			}
		}
		else if (dataType instanceof IntegerTypeInformationInterface)
		{
			if (!new Validator().isNumeric(value))
			{
				throw new SqlException("Non numeric value found in value part!!!");
			}
		}
		else if (dataType instanceof DoubleTypeInformationInterface)
		{
			if (!new Validator().isDouble(value))
			{
				throw new SqlException("Non numeric value found in value part!!!");
			}
		}
		return value;
	}

	
	
	
	
	private String getSelectForOutputTerms(List<IOutputTerm> terms)
	{
		outputTermsColumns = new HashMap<String, IOutputTerm>();
		StringBuilder s = new StringBuilder();
		for (IOutputTerm term : terms)
		{
			String termString = Constants.QUERY_OPENING_PARENTHESIS + getTermString(term.getTerm()) + Constants.QUERY_CLOSING_PARENTHESIS;
			termString = modifyForTimeInterval(termString, term.getTimeInterval());
			String columnName = Constants.COLUMN_NAME + selectIndex++;
			s.append(termString + " " + columnName + Constants.QUERY_COMMA);
			outputTermsColumns.put(columnName, term);
		}
		
		removeLastComma(s);
		return s.toString();
		
		
	}

	private String modifyForTimeInterval(String termString, TimeInterval<?> timeInterval)
	{
		if (timeInterval == null)
		{
			return termString;
		}
		termString = termString + "/" + timeInterval.numSeconds();
		termString = "ROUND" + Constants.QUERY_OPENING_PARENTHESIS + termString + Constants.QUERY_CLOSING_PARENTHESIS;
		return termString;
	}

}