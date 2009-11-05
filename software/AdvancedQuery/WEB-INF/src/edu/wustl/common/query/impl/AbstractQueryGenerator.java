/**
 * 
 */

package edu.wustl.common.query.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.common.dynamicextensions.domain.BooleanAttributeTypeInformation;
import edu.common.dynamicextensions.domaininterface.AttributeTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.DateTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.DoubleTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.FileTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.IntegerTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.LongTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.StringTypeInformationInterface;
import edu.common.dynamicextensions.util.global.DEConstants.InheritanceStrategy;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.query.queryobject.impl.metadata.QueryOutputTreeAttributeMetadata;
import edu.wustl.common.querysuite.exceptions.SqlException;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableObjectInterface;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IExpressionAttribute;
import edu.wustl.common.querysuite.queryobject.IExpressionOperand;
import edu.wustl.common.querysuite.queryobject.IOutputEntity;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.IRule;
import edu.wustl.common.querysuite.queryobject.ITerm;
import edu.wustl.common.querysuite.queryobject.LogicalOperator;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.querysuite.queryobject.TermType;
import edu.wustl.common.querysuite.queryobject.impl.Connector;
import edu.wustl.common.querysuite.queryobject.impl.Expression;
import edu.wustl.common.querysuite.queryobject.impl.JoinGraph;
import edu.wustl.common.querysuite.utils.CustomFormulaProcessor;
import edu.wustl.common.querysuite.utils.DatabaseSQLSettings;
import edu.wustl.common.querysuite.utils.DatabaseType;
import edu.wustl.common.querysuite.utils.TermProcessor;
import edu.wustl.common.querysuite.utils.TermProcessor.IAttributeAliasProvider;
import edu.wustl.common.querysuite.utils.TermProcessor.TermString;
import edu.wustl.dao.daofactory.DAOConfigFactory;
import edu.wustl.query.queryengine.impl.IQueryGenerator;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.util.querysuite.AdvanceQueryDAO;
import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * @author juberahamad_patel
 *
 */
public abstract class AbstractQueryGenerator implements IQueryGenerator
{

	/**
	 * This map holds integer value that will be appended to each table alias in
	 * the query.
	 */
	protected Map<IExpression, Integer> aliasAppenderMap = new HashMap<IExpression, Integer>();

	/**
	 * This map holds the alias name generated for each fully Qualified
	 * className, where className id key & value is the aliasName generated for
	 * that className.
	 */
	protected Map<String, String> aliasNameMap = new HashMap<String, String>();

	/**
	 * reference to the joingraph object present in the query object.
	 */
	protected JoinGraph joinGraph;

	//PAND is not used anymore - juber
	//protected Set<IExpression> pAndExpressions;

	/**
	 * reference to the constraints object present in the query object.
	 */
	protected IConstraints constraints;

	// Variables required for output tree.
	/**
	 * List of Roots of the output tree node.
	 */

	// Variables required for output tree.
	/**
	 * List of Roots of the output tree node.
	 */



	/**
	 * This map contains information about the tree node ids, attributes & their
	 * correspoiding column names in the generated query. - Inner most map Map<AttributeInterface,
	 * String> contains mapping of attribute interface verses the column name in
	 * query. - The outer map Map<Long, Map<AttributeInterface, String>>
	 * contains mapping of treenode Id verses the map in above step. This map
	 * contains mapping required for one output tree. - The List contains the
	 * mapping of all output trees that are formed by the query.
	 */
	// List<Map<Long, Map<AttributeInterface, String>>> columnMapList;

	protected Map<QueryableAttributeInterface, String> attributeColumnNameMap = new HashMap<QueryableAttributeInterface, String>();
	protected boolean containsCLOBTypeColumn = false;

	protected int selectIndex;

	/**
	 * This set will contain the expression ids of the empty expression. An
	 * expression is empty expression when it does not contain any Rule & its
	 * sub-expressions (also their subexpressions & so on) also does not contain
	 * any Rule
	 */
	protected Set<IExpression> emptyExpressions;

	private static final int ALIAS_NAME_LENGTH = 25;

	/**
	 * Constructor
	 */
	public AbstractQueryGenerator()
	{
		aliasAppenderMap = new HashMap<IExpression, Integer>();
		emptyExpressions = new HashSet<IExpression>();

	}

	
	/**
	 *  Getter Method
	 *  @return attributeColumnNameMap
	 */
	public Map<QueryableAttributeInterface, String> getAttributeColumnNameMap()
	{
		return attributeColumnNameMap;
	}

	/**
	 * build the where clause of the query
	 * @param expression - the Expression for which query is to be generated.
	 * @param parentExpression - The Parent Expression.
	 * @return The query string
	 * @throws QueryModuleException - Exception
	 */
	protected String buildWherePart(IExpression expression, IExpression parentExpression)
			throws QueryModuleException
	{
		StringBuffer buffer = new StringBuffer();

		QueryableObjectInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();
		try
		{
			buffer.append(processOperands(expression));
		}
		catch (SqlException e)
		{
			throw new QueryModuleException(Constants.QUERY_ERRORMSG + e.getMessage(), e,
					QueryModuleError.SQL_EXCEPTION);
		}
		/*
		 * If the Query has only one Expression, which referes to an entity
		 * having inheritance strategy as TABLE_PER_HEIRARCHY, then the
		 * Descriminator column condition needs to be added in the WHERE part of
		 * SQL as it can not be added in the FROM part of the query. This can be
		 * identified by following checks 1. parentExpression is null & 2.
		 * expression have no child expression.
		 * If this expression is PseudoAnded then the same check should be made
		 * which will be add descriminatorCondition in the innersql of pseudoAnd
		 * query.
		 */
		if (parentExpression == null /*|| isPAND*/) // This will be true only for
		// root Expression of the Query.
		{
			List<IExpression> childrenList = joinGraph.getChildrenList(expression);
			if (childrenList == null || childrenList.isEmpty())
			{
				/*
				 * No Child Expressions present for the root node, so this is
				 * only Expression in the Query. So check for the Inheritance
				 * strategy. If its derived entity with inheritance strategy as
				 * TABLE_PER_HEIRARCHY, then append the descriminator condition
				 * SQL in buffer.
				 */
				buffer.append(addDescriminatorCondition(entity, expression));
			}
		}
		return buffer.toString();
	}

	/**
	 * @param entity - QueryableObjectInterface
	 * @param expression - IExpression
	 * @return adding descriminator condition and returning String
	 */
	private String addDescriminatorCondition(QueryableObjectInterface entity, IExpression expression)
	{
		StringBuffer buffer = new StringBuffer();
		if (entity.getParentEntity() != null
				&& InheritanceStrategy.TABLE_PER_HEIRARCHY.equals(entity.getInheritanceStrategy()))
		{
			String descriminatorCondition = getDescriminatorCondition(entity, getAliasFor(
					expression, entity));
			buffer.insert(0, Constants.QUERY_OPENING_PARENTHESIS);
			buffer.append(Constants.QUERY_CLOSING_PARENTHESIS);
			buffer.append(descriminatorCondition);
		}
		return buffer.toString();
	}

	//	/**
	//	 * @param eavAssociation
	//	 * @return If eavAssocaiation is Inherited return actual eav association
	//	 */
	//	private AssociationInterface getEavAssociation(AssociationInterface eavAssociation)
	//	{
	//		AssociationInterface newEavAssociation = eavAssociation;
	//		if (InheritanceUtils.getInstance().isInherited(eavAssociation))
	//		{
	//			newEavAssociation = InheritanceUtils.getInstance()
	//					.getActualAassociation(eavAssociation);
	//		}
	//		return newEavAssociation;
	//	}

	/**
	 * To process all child operands of the expression.
	 * @param expression - the reference to Expression.
	 * @return the query for the child operands.
	 * @throws SqlException When there is error in the passed IQuery object.
	 * @throws RuntimeException - Exception
	 * @throws QueryModuleException - Exception
	 */
	protected String processOperands(IExpression expression) throws SqlException, RuntimeException,
			QueryModuleException
	{
		StringBuffer buffer = new StringBuffer();
		int currentNestingCounter = 0;// holds current nesting number count
		// i.e. no of opening Braces that needs
		// to be closed.

		int noOfRules = expression.numberOfOperands();
		for (int forCounter = 0; forCounter < noOfRules; forCounter++)
		{
			IExpressionOperand operand = expression.getOperand(forCounter);
			StringBuffer operandquery = new StringBuffer(getOperand(operand, expression));
			if (operandquery.length() == 0)
			{
				continue;
			}

			if (operandquery.length() != 0 && noOfRules != 1)
			{
				operandquery.insert(0, Constants.QUERY_OPENING_PARENTHESIS);
				operandquery.append(Constants.QUERY_CLOSING_PARENTHESIS);
				// putting the Rule's query in
				// Braces so that it
				// will not get mixed
				// with other Rules.
			}

			if (forCounter != noOfRules - 1)
			{
				Connector connector = (Connector) expression.getConnector(forCounter,
						forCounter + 1);
				int nestingNumber = connector.getNestingNumber();

				int nextIndex = forCounter + 1;
				IExpressionOperand nextOperand = expression.getOperand(nextIndex);
				if (nextOperand instanceof IExpression && emptyExpressions.contains(nextOperand))
				{
					buffer.append(getConnectorForEmptyExpression(nextIndex, nextOperand,
							expression, noOfRules, operandquery, currentNestingCounter));
					forCounter = nextIndex - 1;
				}
				else
				{
					if (operandquery.length() == 0)
					{
						continue;
					}
					currentNestingCounter = attachOperandquery(buffer, currentNestingCounter,
							operandquery.toString(), nestingNumber);
					buffer.append(Constants.SPACE).append(
							connector.getOperator().toString().toLowerCase());
				}
			}
			else
			{
				buffer.append(operandquery);
				buffer.append(' ');
				buffer = new StringBuffer(Utility.removeLastAnd(buffer.toString()));
				buffer.append(getParenthesis(currentNestingCounter,
						Constants.QUERY_CLOSING_PARENTHESIS));
				// Finishing
				// query
				// by
				// adding
				// closing
				// parenthesis
				// if
				// any.
				currentNestingCounter = 0;
			}
		}
		return buffer.toString();
	}

	/**
	 * @param nextIndex - Counter
	 * @param nextOperand - IOperand
	 * @param expression - IExpression
	 * @param noOfRules - Number of Rules
	 * @param operandquery - String
	 * @param currentNestingCounter - Number of opening braces
	 * @return String representation of operandquery
	 */

	private String getConnectorForEmptyExpression(int nextIndex, IExpressionOperand nextOperand,
			IExpression expression, int noOfRules, StringBuffer operandquery,
			int currentNestingCounter)
	{
		IExpressionOperand operand = nextOperand;
		int nestingCounter = currentNestingCounter;
		int index = nextIndex;
		StringBuffer buffer = new StringBuffer();
		for (; index < noOfRules; index++)
		{
			operand = expression.getOperand(index);
			if (!(operand instanceof IExpression && emptyExpressions.contains(operand)))
			{
				break;
			}
		}
		// Expression over add closing
		// parenthesis.
		if (index == noOfRules)
		{
			buffer.append(operandquery);
			buffer
					.append(getParenthesis(currentNestingCounter,
							Constants.QUERY_CLOSING_PARENTHESIS));
			nestingCounter = 0;
		}
		else
		{
			Connector newConnector = (Connector) expression.getConnector(index - 1, index);
			int newNestingNumber = newConnector.getNestingNumber();
			nestingCounter = attachOperandquery(buffer, nestingCounter, operandquery.toString(),
					newNestingNumber);
			buffer.append(Constants.SPACE).append(
					newConnector.getOperator().toString().toLowerCase());
		}
		return buffer.toString();
	}

	/**
	 * @param operand - IOperand
	 * @param expression - IExpression
	 * @return operand string
	 * @throws SqlException - Exception
	 * @throws QueryModuleException - Exception
	 */
	private String getOperand(IExpressionOperand operand, IExpression expression)
			throws SqlException, QueryModuleException
	{
		StringBuffer operandquery = new StringBuffer();
		StringBuffer newOperandquery = new StringBuffer();
		boolean isEmptyExppression = false;
		if (operand instanceof IRule)
		{
			if (((IRule) operand).size() > 0)
			{
				operandquery.append(getQuery((IRule) operand)); // Processing Rule.
			}

		}
		else if (operand instanceof IExpression)
		// Processing sub Expression.
		{
			isEmptyExppression = emptyExpressions.contains(operand);
			if (!isEmptyExppression)
			{
				operandquery.append(buildWherePart((IExpression) operand, expression));
			}
		}
		else
		{
			newOperandquery.append(getCustomFormulaString((ICustomFormula) operand));
			operandquery.append(getTemporalCondition(newOperandquery.toString()));
		}
		return operandquery.toString();
	}

	/**
	 * @param operandquery - String representation
	 * @return Get the modified Temporal Condition according to SQL and XQuery Implementation
	 */
	protected abstract String getTemporalCondition(String operandquery);

	/**
	 * @param entity - IEntity
	 * @param aliasFor - String
	 * @return Entity Alias
	 */
	protected abstract String getDescriminatorCondition(QueryableObjectInterface entity,
			String aliasFor);

	/**
	 * To check if the Expression is empty or not. It will simultaneously add
	 * such empty expressions in the emptyExpressions set.
	 * An expression is said to be empty when: - it contains no rule as operand. -
	 * and all of its children(i.e subExpressions & their subExpressions & so
	 * on) contains no rule
	 * @param expressionId the reference to the expression id.
	 * @return true if the expression is empty.
	 */
	protected boolean checkForEmptyExpression(int expressionId)
	{
		Expression expression = (Expression) constraints.getExpression(expressionId);
		List<IExpression> operandList = joinGraph.getChildrenList(expression);

		boolean isEmpty = true;
		if (!operandList.isEmpty()) // Check whether any of its children
		// contains rule.
		{
			for (IExpression subExpression : operandList)
			{
				if (!checkForEmptyExpression(subExpression.getExpressionId()))
				{
					isEmpty = false;
				}
			}
		}

		isEmpty = isEmpty && !containsCondition(expression);// check if there are
		// rule present as
		// subexpression.
		// SRINATH
		isEmpty = isEmpty && !expression.containsCustomFormula();
		if (isEmpty)
		{
			emptyExpressions.add(expression); // Expression is empty.
		}

		return isEmpty;
	}

	/**
	 * @param expression - IExpression
	 * @return - Check if the expression contains any rules
	 */
	private boolean containsCondition(Expression expression)
	{
		boolean result;
		if (expression.containsRule())
		{
			IRule rule = (IRule) expression.getOperand(0);
			if (rule.size() == 0)
			{
				result = false;
			}
			else
			{
				result = true;
			}
		}
		else
		{
			result = false;
		}

		return result;
	}

	/**
	 * To get the query for the Rule
	 * @param rule The reference to Rule.
	 * @return The query for the Rule.
	 * @throws SqlException When there is error in the passed IQuery object.
	 */
	private String getQuery(IRule rule) throws SqlException
	{
		StringBuffer buffer = new StringBuffer();

		//IExpression expression = rule.getContainingExpression();
		//		addActivityStausCondition();

		int noOfConditions = rule.size();
		if (noOfConditions == 0)
		{
			throw new SqlException("No conditions defined in the Rule!!!");
		}
		for (int i = 0; i < noOfConditions; i++) // Processing all conditions
		// in Rule combining them
		// with AND operator.
		{
			String condition = processOperator(rule.getCondition(i), rule.getContainingExpression());

			if (i != noOfConditions - 1) // Intermediate Condition.
			{
				if (!"".equals(condition))
				{
					buffer.append(condition + " " + LogicalOperator.And.toString().toLowerCase()
							+ " ");
				}

			}
			else
			{
				// Last Condition, this will not followed by And logical
				// operator.
				buffer.append(condition);
			}
		}
		return Utility.removeLastAnd(buffer.toString());
	}

	//	private void addActivityStausCondition()
	//	{
	//	}

	/**
	 * @param formula - ICustomFormula
	 * @return ICustomFormula
	 */
	protected String getCustomFormulaString(ICustomFormula formula)
	{
		ICustomFormula newCustomFormula = getNewCustomformula(formula);
		return getCustomFormulaProcessor().asString(newCustomFormula);
	}

	/**
	 * @param formula - ICustomFormula
	 * @return - new ICustomFormula
	 */
	protected abstract ICustomFormula getNewCustomformula(ICustomFormula formula);

	/**
	 * @return Custom Formula
	 */
	protected CustomFormulaProcessor getCustomFormulaProcessor()
	{
		return new CustomFormulaProcessor(getAliasProvider(), getDatabaseSQLSettings(),
				Variables.properties.getProperty("queryType"));
	}

	/**
	 * @return String representation based on type of database
	 */
	private DatabaseSQLSettings getDatabaseSQLSettings()
	{
		DatabaseType databaseType;
		String appname = AdvanceQueryDAO.getInstance().getAppName();
		String databaseName = DAOConfigFactory.getInstance().getDAOFactory(appname)
				.getDataBaseType();
		if (Constants.MYSQL_DATABASE.equals(databaseName))
		{
			databaseType = DatabaseType.MySQL;
		}
		else if (Constants.ORACLE_DATABASE.equals(databaseName))
		{
			databaseType = DatabaseType.Oracle;
		}
		else if (Constants.DB2_DATABASE.equals(databaseName))
		{
			databaseType = DatabaseType.DB2;
		}
		else
		{
			throw new UnsupportedOperationException("Custom formulas on " + databaseName
					+ " are not supported.");
		}

		return new DatabaseSQLSettings(databaseType);
	}

	/**
	 * @return Attribute Name used in Temporal Query
	 */
	protected IAttributeAliasProvider getAliasProvider()
	{
		return new IAttributeAliasProvider()
		{

			public String getAliasFor(IExpressionAttribute exprAttr)
			{
				return getTemporalAttributeName(exprAttr.getAttribute(), exprAttr.getExpression());
			}

		};
	}

	/**
	 * @param attribute - QueryableAttributeInterface
	 * @param expression - IExpression
	 * @return - Attribute used in Temporal Condition
	 */
	protected abstract String getTemporalAttributeName(QueryableAttributeInterface attribute,
			IExpression expression);

	// output terms

	/**
	 * @return TermProcessor
	 */
	private TermProcessor getTermProcessor()
	{
		return new TermProcessor(getAliasProvider(), getDatabaseSQLSettings(), Variables.properties
				.getProperty("queryType"));
	}

	/**
	 * @param term - ITerm Object
	 * @return Term String representation based on type of database
	 * @throws SqlException - Exception
	 */
	protected String getTermString(ITerm term) throws SqlException
	{
		// TODO this is a tad ugly now; if/when sqlGen moves to query project, it won't be.
		TermString termString = getTermProcessor().convertTerm(term);
		StringBuffer buffer = new StringBuffer(termString.getString());

		if (termString.getTermType() == TermType.DSInterval)
		{
			switch (getDatabaseSQLSettings().getDatabaseType())
			{
				case MySQL :
					buffer.toString();
					break;

				case Oracle :
					Constants.getOracleTermString(buffer.toString());
					break;
				case DB2 :
					Constants.getDB2TermString(buffer.toString());
					break;
				default :
					throw new SqlException("wont occur");
			}
			buffer.insert(0, Constants.QUERY_OPENING_PARENTHESIS);
			buffer.append(Constants.QUERY_CLOSING_PARENTHESIS);
		}
		return buffer.toString();
	}

	/**
	 * To append the operand query to the query buffer, with required number of
	 * parenthesis.
	 * @param buffer The reference to the String buffer containing query for query
	 *            of operands of an expression.
	 * @param currentNestingCounter The current nesting count.
	 * @param operandquery The query of the operand to be appended to buffer
	 * @param nestingNumber The nesting number for the current operand's
	 *            operator.
	 * @return The updated current nesting count.
	 */
	private int attachOperandquery(StringBuffer buffer, int currentNestingCounter,
			String operandquery, int nestingNumber)
	{
		int counter = currentNestingCounter;
		if (counter < nestingNumber)
		{
			buffer.append(getParenthesis(nestingNumber - counter,
					Constants.QUERY_CLOSING_PARENTHESIS));
			counter = nestingNumber;
			buffer.append(operandquery);
		}
		else if (counter > nestingNumber)
		{
			buffer.append(operandquery);
			buffer.append(getParenthesis(counter - nestingNumber,
					Constants.QUERY_CLOSING_PARENTHESIS));
			counter = nestingNumber;
		}
		else
		{
			buffer.append(operandquery);
		}
		return counter;
	}

	/**
	 * To get newCounter number of parenthesis.
	 * @param newCounter The positive integer value
	 * @param parenthesis either Opening parenthesis or closing parenthesis.
	 * @return The newCounter number of parenthesis.
	 */
	public String getParenthesis(int newCounter, String parenthesis)
	{
		StringBuilder string = new StringBuilder();
		for (int counter = 0; counter < newCounter; counter++)
		{
			string.append(parenthesis);
		}

		return string.toString();
	}

	
	/**
	 * Get the query specific representation for Attribute ie LHS of a condition.
	 * @param attribute The reference to AttributeInterface
	 * @param expression The reference to Expression to which this attribute
	 *            belongs.
	 * @return The query specific representation for Attribute.
	 */
	protected abstract String getConditionAttributeName(QueryableAttributeInterface attribute,
			IExpression expression);

	/**
	 * It will return the select part attributes for this node along with its
	 * child nodes.
	 * @param treeNode the output tree node.
	 * @return The select part attributes for this node along with its child
	 *         nodes.
	 */
	protected String getSelectAttributes(OutputTreeDataNode treeNode)
	{
		StringBuffer selectPart = new StringBuffer();
		IExpression expression = constraints.getExpression(treeNode.getExpressionId());

		IOutputEntity outputEntity = treeNode.getOutputEntity();
		List<QueryableAttributeInterface> attributes = outputEntity.getSelectedAttributes();

		for (QueryableAttributeInterface attribute : attributes)
		{
			selectPart.append(getConditionAttributeName(attribute, expression));
			String columnAliasName = Constants.QUERY_COLUMN_NAME + selectIndex;
			selectPart.append(Constants.SPACE).append(columnAliasName)
					.append(Constants.QUERY_COMMA);
			// code to get display name. & pass it to the Constructor along with
			// treeNode.
			//String displayNameForColumn = Utility.getDisplayNameForColumn(attribute);
			String displayNameForColumn = Utility.getDisplayNameForColumn(attribute);
			treeNode.addAttribute(new QueryOutputTreeAttributeMetadata(attribute, columnAliasName,
					treeNode, displayNameForColumn));
			attributeColumnNameMap.put(attribute, columnAliasName);
			selectIndex++;
			if (Constants.QUERY_FILE.equalsIgnoreCase(attribute.getDataType()))
			{
				containsCLOBTypeColumn = true;
			}
		}
		List<OutputTreeDataNode> children = treeNode.getChildren();
		for (OutputTreeDataNode childTreeNode : children)
		{
			selectPart.append(getSelectAttributes(childTreeNode));
		}
		return selectPart.toString();
	}

	/**
	 * Adds an pseudo anded expression & all its child expressions to
	 * pAndExpressions set.
	 * @param expression pAnd expression
	 */
	/*protected void addpAndExpression(IExpression expression)
	{
		List<IExpression> childList = joinGraph.getChildrenList(expression);
		pAndExpressions.add(expression);

		for (IExpression newExp : childList)
		{
			addpAndExpression(newExp);
		}

	}*/

	/**
	 * It will create the aliasAppenderMap
	 */
	protected void createAliasAppenderMap() //throws MultipleRootsException
	{
		for (IExpression expr : constraints)
		{
			aliasAppenderMap.put(expr, expr.getExpressionId());
		}
	}

	/**
	 * To get the primary key attribute of the given entity.
	 * @param entity the DE entity.
	 * @return The Primary key attribute of the given entity.
	 * @throws SqlException If there is no such attribute present in the
	 *             attribute list of the entity.
	 */
	protected QueryableAttributeInterface getPrimaryKey(QueryableObjectInterface entity)
			throws SqlException
	{
		Collection<QueryableAttributeInterface> attributes = entity.getEntityAttributesForQuery();
		QueryableAttributeInterface mainAttribute = getAttribute(attributes);

		if (mainAttribute == null)
		{
			QueryableObjectInterface parentEntity = entity.getParentEntity();
			if (parentEntity != null)// &&
			// entity.getInheritanceStrategy().equals(InheritanceStrategy.TABLE_PER_SUB_CLASS))
			{
				mainAttribute = getPrimaryKey(parentEntity);
			}
		}
		return mainAttribute;
	}

	/**
	 * @param attributes - List of QueryableAttributeInterface
	 * @return the primary Key attribute
	 */
	private QueryableAttributeInterface getAttribute(
			Collection<QueryableAttributeInterface> attributes)
	{
		QueryableAttributeInterface newAttribute = null;
		for (QueryableAttributeInterface attribute : attributes)
		{
			if (attribute.getIsPrimaryKey()
					|| attribute.getName().equals(
							edu.wustl.common.util.global.Constants.SYSTEM_IDENTIFIER))
			{
				newAttribute = attribute;
			}
		}
		return newAttribute;
	}

	

	/**
	 * To get the Alias Name for the given IExpression. It will return alias
	 * name for the DE entity associated with constraint entity.
	 * @param expression The reference to IExpression.
	 * @return The Alias Name for the given Entity.
	 */
	protected String getAliasName(IExpression expression)
	{
		QueryableObjectInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();
		return getAliasFor(expression, entity);
	}

	/**
	 * To get the aliasName for the given entity present which is associated
	 * with Expression.
	 * @param expression The reference to IExpression.
	 * @param attributeEntity The reference to the Entity for which the alias to
	 *            be searched.
	 * @return The Alias Name for the given Entity.
	 */
	protected String getAliasFor(IExpression expression, QueryableObjectInterface attributeEntity)
	{
		StringBuffer newAliasName = new StringBuffer();
		QueryableObjectInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();
		QueryableObjectInterface aliasEntity = entity;

		QueryableObjectInterface parentEntity = entity.getParentEntity();
		aliasEntity = getAliasEntity(entity, parentEntity, attributeEntity);

		// Need an extra check for the TABLE_PER_HEIRARCHY case.
		// Because even if attribute belongs to this aliasEntity, but if its
		// association with parent is of TABLE_PER_HEIRARCHY type, its alias
		// will be one of its parent heirarchy.
		parentEntity = aliasEntity.getParentEntity();
		InheritanceStrategy type = aliasEntity.getInheritanceStrategy();
		while (parentEntity != null && type.equals(InheritanceStrategy.TABLE_PER_HEIRARCHY))
		{
			aliasEntity = parentEntity;
			type = aliasEntity.getInheritanceStrategy();
			parentEntity = parentEntity.getParentEntity();
		}

		String aliasName = getAliasForClassName(aliasEntity.getName());
		Integer aliasAppender = aliasAppenderMap.get(expression);
		if (aliasAppender == null)// for Junits
		{
			aliasAppender = 0;
		}
		newAliasName.append(aliasName).append(Constants.QUERY_UNDERSCORE).append(aliasAppender);
		return newAliasName.toString();
	}

	/**
	 * @param entity - QueryableObjectInterface
	 * @param parentEntity - QueryableObjectInterface represents Parent entity
	 * @param attributeEntity - QueryableObjectInterface represents
	 * @return aliasEntity - QueryableObjectInterface
	 */
	private QueryableObjectInterface getAliasEntity(QueryableObjectInterface entity,
			QueryableObjectInterface parentEntity, QueryableObjectInterface attributeEntity)
	{
		QueryableObjectInterface aliasEntity = entity;
		//		QueryableObjectInterface baseEntity = entity;
		//		QueryableObjectInterface newParentEntity = parentEntity;
		while (parentEntity != null && !attributeEntity.equals(entity))
		{
			InheritanceStrategy type = entity.getInheritanceStrategy();

			if (type.equals(InheritanceStrategy.TABLE_PER_CONCRETE_CLASS))
			{
				aliasEntity = entity;
				break;
			}
			else if (type.equals(InheritanceStrategy.TABLE_PER_SUB_CLASS))
			{
				//				baseEntity = parentEntity;
				aliasEntity = parentEntity;
				//				newParentEntity = parentEntity.getParentEntity();
			}
			else if (type.equals(InheritanceStrategy.TABLE_PER_HEIRARCHY))
			{
				while (parentEntity != null && type.equals(InheritanceStrategy.TABLE_PER_HEIRARCHY))
				{
					//					baseEntity = parentEntity;
					if (attributeEntity.equals(entity))
					{
						break;
					}
					type = entity.getInheritanceStrategy();
					//newParentEntity = parentEntity.getParentEntity();
				}
				aliasEntity = entity;
			}
		}
		return aliasEntity;
	}

	/**
	 * To get the alias for the given Class Name.
	 * @param className The follyQualified class Name.
	 * @return The alias name for the given class Name.
	 */
	protected String getAliasForClassName(String className)
	{
		String aliasName = aliasNameMap.get(className);
		StringBuffer alias = new StringBuffer();
		if (aliasName == null)
		{
			aliasName = className.substring(className.lastIndexOf('.') + 1, className.length());
			if (aliasName.length() > ALIAS_NAME_LENGTH)
			{
				aliasName = aliasName.substring(0, ALIAS_NAME_LENGTH);
			}

			// aliasName = aliasName.replaceAll(Constants.REGEX_EXPRESION,
			// Constants.REPLACEMENT);
			alias
					.append(edu.wustl.common.util.Utility
							.removeSpecialCharactersFromString(aliasName));
			// get unique aliasName for the given class.
			int count = 1;
			String theAliasName = alias.toString();
			Collection<String> allAssignedAliases = aliasNameMap.values();
			while (allAssignedAliases.contains(theAliasName))
			{
				theAliasName = aliasName + count++;
			}
			aliasName = theAliasName;
			aliasNameMap.put(className, aliasName);
		}
		return aliasName;
	}

	/**
	 * @param condition - ICondition
	 * @param expression - IExpression
	 * @return - the String value obtained after processing the value based on operator
	 * @throws SqlException - Exception
	 */
	private String processOperator(ICondition condition, IExpression expression)
			throws SqlException
	{
		String sql = null;
		QueryableAttributeInterface attribute = condition.getAttribute();
		if (attribute.isTagPresent(Constants.VI_IGNORE_PREDICATE)
				|| isParametrizedAttribute(attribute, expression))
		{
			sql = "";
		}
		else
		{
			String attributeName = getConditionAttributeName(attribute, expression);
			RelationalOperator operator = condition.getRelationalOperator();
			sql = processOperators(operator, condition, attributeName);
		}
		return sql;
	}

	/**
	 * @param operator - RelationalOperator
	 * @param condition - ICondition
	 * @param attributeName - Name of the attribute
	 * @return - making appropriate calls based on the type of operator
	 * and returning String representation of the value
	 * @throws SqlException - Exception
	 */
	private String processOperators(RelationalOperator operator, ICondition condition,
			String attributeName) throws SqlException
	{
		String sql = "";
		// Processing Between Operator, it will be
		// treated as (op>=val1 and op<=val2)
		switch (operator)
		{
			case Between :
				sql = processBetweenOperator(condition, attributeName);
				break;
			case In :
				sql = processInOperator(condition, attributeName);
				break;
			case NotIn :
				sql = processInOperator(condition, attributeName);
				break;
			case IsNotNull :
				sql = processNullCheckOperators(condition, attributeName);
				break;
			case IsNull :
				sql = processNullCheckOperators(condition, attributeName);
				break;
			case Contains :
				sql = processLikeOperators(condition, attributeName);
				break;
			case StartsWith :
				sql = processLikeOperators(condition, attributeName);
				break;
			case EndsWith :
				sql = processLikeOperators(condition, attributeName);
				break;
			default :
				sql = processComparisionOperator(condition, attributeName);
		}
		return sql;
	}

	/**
	 * @param attribute - QueryableAttributeInterface
	 * @param expression - IExpression
	 * @return a boolean value representing whether he attribute
	 * is parametrized or not
	 */
	protected abstract boolean isParametrizedAttribute(QueryableAttributeInterface attribute,
			IExpression expression);

	/**
	* Processing operators like =, !=, <, > , <=, >= etc.
	* @param condition the condition.
	* @param attributeName the Name of the attribute to returned in SQL.
	* @return SQL representation for given condition.
	* @throws SqlException when: 1. value list contains more/less than 1 value.
	*             2. other than = ,!= operator present for String data type.
	*/
	protected String processComparisionOperator(ICondition condition, String attributeName)
			throws SqlException
	{
		AttributeTypeInformationInterface dataType = condition.getAttribute()
				.getAttributeTypeInformation();
		RelationalOperator operator = condition.getRelationalOperator();
		List<String> values = condition.getValues();
		validateSizeCondition(values, operator, condition);
		validateDataTypeCondition(dataType, operator, condition);
		StringBuffer value = new StringBuffer(modifyValueForDataType(values.get(0), dataType,
				condition.getAttribute()));
		String sql = attributeName + RelationalOperator.getSQL(operator) + value.toString();
		return sql;
	}

	/**
	 * @param dataType - data type of the attribute
	 * @param operator - operator
	 * @param condition - ICondition
	 * @throws SqlException - SQL Exception
	 */
	private void validateDataTypeCondition(AttributeTypeInformationInterface dataType,
			RelationalOperator operator, ICondition condition) throws SqlException
	{
		boolean stringType = dataType instanceof StringTypeInformationInterface;
		boolean booleanType = dataType instanceof BooleanAttributeTypeInformation;
		boolean firstSet = stringType || booleanType;
		boolean equalsOperator = operator.equals(RelationalOperator.Equals);
		boolean notEqualsOperator = operator.equals(RelationalOperator.NotEquals);
		boolean secondSet = equalsOperator || notEqualsOperator;

		if (firstSet && !secondSet)
		{
			throw new SqlException(
					"Incorrect operator found for String or Boolean datatype for condition:"
							+ condition);
		}
	}

	/**
	 * @param values - List of values
	 * @param operator - operator
	 * @param condition - ICondition
	 * @throws SqlException - SQL Exception
	 */
	private void validateSizeCondition(List<String> values, RelationalOperator operator,
			ICondition condition) throws SqlException
	{
		if (operator.equals(RelationalOperator.Between) && values.size() != 2)
		{
			throw new SqlException("Incorrect number of values found for Operator '" + operator
					+ "' for condition:" + condition);
		}
		else if (values.size() != 1)
		{
			throw new SqlException("Incorrect number of values found for Operator '" + operator
					+ "' for condition:" + condition);
		}

	}

	/**
	* To process String operators. for Ex. starts with, contains etc.
	* @param condition the condition.
	* @param attributeName the Name of the attribute to returned in SQL.
	* @return SQL representation for given condition.
	* @throws SqlException when 1. The datatype of attribute is not String. 2.
	*             The value list empty or more than 1 value.
	*/
	protected String processLikeOperators(ICondition condition, String attributeName)
			throws SqlException
	{
		RelationalOperator operator = condition.getRelationalOperator();
		AttributeTypeInformationInterface dateType = condition.getAttribute()
				.getAttributeTypeInformation();
		boolean firstSet = dateType instanceof StringTypeInformationInterface;
		boolean secondSet = dateType instanceof FileTypeInformationInterface;
		if (!(firstSet || secondSet))
		{
			throw new SqlException("Incorrect data type found " + "for Operator '" + operator
					+ "' for condition:" + condition);
		}

		List<String> values = condition.getValues();
		validateSizeCondition(values, operator, condition);
		StringBuffer value = new StringBuffer(getValue(values, operator));
		String str = getDatabaseDetails(attributeName, value.toString());

		return str;
	}

	/**
	 * @param values - List of Values
	 * @param operator - Relational Operator
	 * @return value
	 */
	private String getValue(List<String> values, RelationalOperator operator)
	{
		StringBuffer value = new StringBuffer(values.get(0));
		if (operator.equals(RelationalOperator.Contains))
		{
			value.insert(0, "'%");
			value.append("%'");
		}
		else if (operator.equals(RelationalOperator.StartsWith))
		{
			value.insert(0, "'");
			value.append("%'");
		}
		else if (operator.equals(RelationalOperator.EndsWith))
		{
			value.insert(0, "'%");
			value.append('\'');
		}
		return value.toString();
	}

	/**
	 * @param attributeName - name of the attribute
	 * @param value - value defined
	 * @return - the String representation of the condition
	 */
	private String getDatabaseDetails(String attributeName, String value)
	{
		String str;
		switch (getDatabaseSQLSettings().getDatabaseType())
		{
			case MySQL :
				str = attributeName + Constants.LIKE + value;
				break;
			case Oracle :
				str = "lower(" + attributeName + ") like lower(" + value
						+ Constants.QUERY_CLOSING_PARENTHESIS;
				break;
			default :
				str = "";
				break;
		}
		return str;
	}

	/**
	* To process 'Is Null' & 'Is Not Null' operator.
	* @param condition the condition.
	* @param attributeName the Name of the attribute to returned in SQL.
	* @return SQL representation for given condition.
	* @throws SqlException when the value list is not empty.
	*/
	protected String processNullCheckOperators(ICondition condition, String attributeName)
			throws SqlException
	{
		String operatorStr = RelationalOperator.getSQL(condition.getRelationalOperator());
		if (condition.getValues().size() > 0)
		{
			throw new SqlException("No value expected in value part for '" + operatorStr
					+ "' operator !!!");
		}

		return attributeName + " " + operatorStr;

	}

	/**
	* To process 'In' & 'Not In' operator.
	* @param condition the condition.
	* @param attributeName the Name of the attribute to returned in SQL.
	* @return SQL representation for given condition.
	* @throws SqlException when the value list is empty or problem in parsing
	*             any of the value.
	*/
	protected String processInOperator(ICondition condition, String attributeName)
			throws SqlException
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append(attributeName + " "
				+ RelationalOperator.getSQL(condition.getRelationalOperator()) + " "
				+ Constants.QUERY_OPENING_PARENTHESIS);
		List<String> valueList = condition.getValues();
		AttributeTypeInformationInterface dataType = condition.getAttribute()
				.getAttributeTypeInformation();

		if (valueList.isEmpty())
		{
			throw new SqlException("atleast one value " + "required for 'In' "
					+ "operand list for condition:" + condition);
		}

		if (dataType instanceof BooleanAttributeTypeInformation)
		{
			throw new SqlException("Incorrect operator found for Boolean datatype for condition:"
					+ condition);
		}
		for (int i = 0; i < valueList.size(); i++)
		{

			String value = modifyValueForDataType(valueList.get(i), dataType, condition
					.getAttribute());

			if (i == valueList.size() - 1)
			{
				buffer.append(value).append(Constants.QUERY_CLOSING_PARENTHESIS);
			}
			else
			{
				buffer.append(value).append(Constants.QUERY_COMMA);
			}
		}
		return buffer.toString();
	}

	/**
	* To get the SQL for the given condition with Between operator. It will be
	* treated as (op>=val1 and op<=val2)
	* @param condition The condition.
	* @param attributeName the Name of the attribute to returned in SQL.
	* @return SQL representation for given condition.
	* @throws SqlException when: 1. value list does not have 2 values 2.
	*             Datatype is not date 3. problem in parsing date.
	*/
	protected String processBetweenOperator(ICondition condition, String attributeName)
			throws SqlException
	{
		StringBuffer buffer = new StringBuffer();
		List<String> values = condition.getValues();
		RelationalOperator operator = condition.getRelationalOperator();
		validateSizeCondition(values, operator, condition);
		AttributeTypeInformationInterface dataType = condition.getAttribute()
				.getAttributeTypeInformation();

		boolean dateType = dataType instanceof DateTypeInformationInterface;
		boolean integerType = dataType instanceof IntegerTypeInformationInterface;
		boolean longType = dataType instanceof LongTypeInformationInterface;
		boolean doubleType = dataType instanceof DoubleTypeInformationInterface;
		boolean excCondition = dateType || integerType || longType || doubleType;

		if (!(excCondition))
		{
			throw new SqlException("Incorrect Data type of operand "
					+ "for Between oparator in condition:" + condition);
		}

		String firstValue = modifyValueForDataType(values.get(0), dataType, condition
				.getAttribute());
		String secondValue = modifyValueForDataType(values.get(1), dataType, condition
				.getAttribute());

		buffer.append(Constants.QUERY_OPENING_PARENTHESIS).append(attributeName);
		buffer.append(RelationalOperator.getSQL(RelationalOperator.GreaterThanOrEquals)).append(
				firstValue);
		buffer.append(Constants.SPACE).append(LogicalOperator.And).append(Constants.SPACE).append(
				attributeName).append(
				RelationalOperator.getSQL(RelationalOperator.LessThanOrEquals)).append(secondValue)
				.append(Constants.QUERY_CLOSING_PARENTHESIS);

		return buffer.toString();
	}

	/**
	 * This method will be used by Query Mock to set the join Graph externally.
	 * @param joinGraph - the reference to joinGraph.
	 */
	protected void setJoinGraph(JoinGraph joinGraph)
	{
		this.joinGraph = joinGraph;
	}

	/* (non-Javadoc)
	 * @see edu.wustl.query.queryengine.impl.IQueryGenerator#generateQuery
	 * (edu.wustl.common.querysuite.queryobject.IQuery)
	 */
	/**
	 * @param query - IQuery
	 * @return String representation  of query
	 * @throws QueryModuleException - Exception
	 */
	public abstract String generateQuery(IQuery query) throws QueryModuleException;

	/* (non-Javadoc)
	 * @see edu.wustl.query.queryengine.impl.IQueryGenerator#getAttributeColumnNameMap()
	 */

	/**
	 * modify the values of the data types to suit the database environment
	 * @param value - String representation of value
	 * @param dataType - Type of the data
	 * @param attribute - QueryableAttributeInterface
	 * @return String representation based on data type
	 * @throws SqlException - Exception
	 */
	protected abstract String modifyValueForDataType(String value,
			AttributeTypeInformationInterface dataType, QueryableAttributeInterface attribute)
			throws SqlException;

}
