package edu.wustl.query.generator;

import static edu.wustl.query.generator.SqlKeyWords.WHERE;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import edu.common.dynamicextensions.domain.BooleanAttributeTypeInformation;
import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.AttributeTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.BooleanTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.DateTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.DoubleTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.common.dynamicextensions.domaininterface.FileTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.FloatTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.IntegerTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.LongTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.StringTypeInformationInterface;
import edu.common.dynamicextensions.entitymanager.EntityManager;
import edu.wustl.cab2b.common.queryengine.querybuilders.CategoryPreprocessor;
import edu.wustl.cab2b.server.category.CategoryOperations;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.query.queryobject.impl.metadata.QueryOutputTreeAttributeMetadata;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.exceptions.SqlException;
import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.metadata.category.Category;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IExpressionAttribute;
import edu.wustl.common.querysuite.queryobject.IExpressionOperand;
import edu.wustl.common.querysuite.queryobject.IOutputEntity;
import edu.wustl.common.querysuite.queryobject.IOutputTerm;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.IQueryEntity;
import edu.wustl.common.querysuite.queryobject.IRule;
import edu.wustl.common.querysuite.queryobject.ITerm;
import edu.wustl.common.querysuite.queryobject.LogicalOperator;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.querysuite.queryobject.TimeInterval;
import edu.wustl.common.querysuite.queryobject.impl.Connector;
import edu.wustl.common.querysuite.queryobject.impl.Expression;
import edu.wustl.common.querysuite.queryobject.impl.JoinGraph;
import edu.wustl.common.querysuite.utils.CustomFormulaProcessor;
import edu.wustl.common.querysuite.utils.DatabaseSQLSettings;
import edu.wustl.common.querysuite.utils.DatabaseType;
import edu.wustl.common.querysuite.utils.TermProcessor;
import edu.wustl.common.querysuite.utils.TermProcessor.IAttributeAliasProvider;
import edu.wustl.common.util.Utility;
import edu.wustl.common.util.global.CommonServiceLocator;
import edu.wustl.common.util.global.Validator;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.dao.JDBCDAO;
import edu.wustl.dao.daofactory.DAOConfigFactory;
import edu.wustl.dao.daofactory.DAOFactory;
import edu.wustl.dao.daofactory.IDAOFactory;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.metadata.util.DyExtnObjectCloner;
import edu.wustl.query.util.global.AQConstants;

/**
 * To generate SQL from the given Query Object.
 * @author prafull_kadam
 */
public class SqlGenerator implements ISqlGenerator
{
	/**
	 * Select string.
	 */
	private static final String SELECT = "select ";

	/**
	 * Select Distinct.
	 */
	private static final String SELECT_DISTINCT = "select distinct ";

	/**
	 * strTodateFunction.
	 */
	private static String strTodateFunction = "";

	/**
	 * The date pattern.
	 */
	private static String datePattern = "";

	/**
	 * escape character.
	 */
	private static String escapeCharacter = "\\";

	/**
	 * escape character for oracle database.
	 */
	private static String escapeCharOracle = "!";

	/**
	 * escape sequence for oracle.
	 */
	private static String escapeSeqOracle = "escape '!'";

    /**
     * reference to the join graph object present in the query object.
     */
    private JoinGraph joinGraph;

    /**
     * reference to the constraints object present in the query object.
     */
    private IConstraints constraints;

    /**
     * Column name.
     */
    public static final String COLUMN_NAME = "Column";

    /**
     * Comma.
     */
    private static final String SELECT_COMMA = ", ";
    /**
     * This set will contain the expression id's of the empty expression. An
     * expression is empty expression when it does not contain any Rule & its
     * sub-expressions (also their subexpressions & so on) also does not contain
     * any Rule
     */
    private Set<IExpression> emptyExpressions;// Set of Empty Expressions.

    // Variables required for output tree.
    /**
     * List of Roots of the output tree node.
     */
    private List<OutputTreeDataNode> rootOpTreeNodLst;

    /**
     * Object of FromBuilder.
     */
    private FromBuilder fromBuilder;

    /**
     * This count represents number of output trees formed.
     */
    private int treeNo;

    /**
     * Attribute to column name mapping.
     */
    private Map<AttributeInterface, String> attrColNameMap =
    	new HashMap<AttributeInterface, String>();

    /**
     * To specify if the query contains column of CLOB type.
     */
    private boolean hasCLOBTypeCol = false;

    /**
     * JDBCDAO object.
     */
    private JDBCDAO dao = null;

    /**
     * Default Constructor to instantiate SQL generator object.
     */
    public SqlGenerator() {}

    /**
     * Generates SQL for the given Query Object.
     * @param query The Reference to Query Object.
     * @return the String representing SQL for the given Query object.
     * @throws MultipleRootsException When there are multiple roots present in a
     *             graph.
     * @throws SqlException When there is error in the passed IQuery object.
     * @throws RuntimeException RuntimeException
     * @throws DAOException DAOException
     * @see edu.wustl.common.querysuite.queryengine.ISqlGenerator#generateSQL
     * (edu.wustl.common.querysuite.queryobject.IQuery)
     */
    public String generateSQL(IQuery query) throws MultipleRootsException,
    SqlException, RuntimeException, DAOException
    {
        Logger.out.debug("Srarted SqlGenerator.generateSQL().....");

        String appName=CommonServiceLocator.getInstance().getAppName();
        IDAOFactory daofactory = DAOConfigFactory.getInstance().getDAOFactory(appName);
        dao = daofactory.getJDBCDAO();
        strTodateFunction = dao.getStrTodateFunction();
        datePattern = dao.getDatePattern();
        String sql = buildQuery(query);
        Logger.out.debug("Finished SqlGenerator.generateSQL()...SQL:" + sql);
        return sql;
    }

    /**
     * To initialize map the variables. & build the SQL for the Given Query
     * Object.
     * @param query the IQuery reference.
     * @return The Root Expression of the IQuery.
     * @throws MultipleRootsException When there exists multiple roots in
     *             join graph.
     * @throws SqlException When there is error in the passed IQuery object.
     * @throws RuntimeException RuntimeException
     */
    String buildQuery(IQuery query) throws MultipleRootsException, SqlException, RuntimeException
    {
        IQuery queryClone = cloneQuery(query);
        constraints = queryClone.getConstraints();

        processExpressionsWithCategories(queryClone);

        this.joinGraph = (JoinGraph) constraints.getJoinGraph();
        IExpression rootExpression = constraints.getRootExpression();

        // Identifying empty Expressions.
        emptyExpressions = new HashSet<IExpression>();
        isEmptyExpression(rootExpression.getExpressionId());

        // Generating output tree.
        createTree();

        // Creating SQL.
        fromBuilder = new FromBuilder(joinGraph);
        // String fromPart = fromBuilder.getFromPartSQL(rootExpression, null,
        // new HashSet<IExpression>());
        String fromPart = fromBuilder.fromClause();
        String wherePart = getCompleteWherePart(rootExpression);
        String selectPart = getSelectPart();

        selectPart += getSelectForOutputTerms(queryClone.getOutputTerms());
        selectPart = removeLastComma(selectPart);

        String sql = selectPart + " " + fromPart + " " + wherePart;

        log(sql);
        return sql;
    }

    /**
     * Returns the clone of the passed query.
     * @param query query
     * @return clone of the query
     */
    IQuery cloneQuery(IQuery query)
    {
        return new DyExtnObjectCloner().clone(query);
    }

    /**
     * @param sql the query
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
     * Returns complete where part including PAND conditions.
     * @param rootExpression The root expression
     * @return wherePart The where part
     * @throws SqlException SqlException
     * @throws RuntimeException RuntimeException
     */
    private String getCompleteWherePart(IExpression rootExpression) throws SqlException, RuntimeException
    {
        String wherePart = getWherePartSQL(rootExpression);
        wherePart = WHERE + wherePart;
        return wherePart;
    }

    /**
     * To handle Expressions constrained on Categories. If Query contains an
     * Expression having Constraint Entity as Category, then that Expression is
     * expanded in such a way that it will look as if it is constrained on
     * Classes without changing Query criteria.
     * @param query query
     * @throws SqlException if there is any error in processing category.
     */
    private void processExpressionsWithCategories(IQuery query) throws SqlException
    {
        if (containsCategrory())
        {
            Connection connection = null;
            try
            {
                EntityInterface rootEntity = null;
                EntityInterface rootDEEntity = constraints.getRootExpression().getQueryEntity()
                        .getDynamicExtensionsEntity();
                boolean isCategory = edu.wustl.cab2b.common.util.Utility.isCategory(rootDEEntity);
                // This is temporary work around, This connection parameter will be removed in future.
                InitialContext context = new InitialContext();
                DataSource dataSource = (DataSource) context.lookup("java:/catissuecore");
                connection = dataSource.getConnection();
                 // if the root entity itself is category, then get the root
                 //entity of the category & pass it to the processCategory() method.
                if (isCategory)
                {
                    Category category = new CategoryOperations().getCategoryByEntityId(rootDEEntity.getId());
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
                        throw new SqlException("Error in closing connection while preprocessing category!!!!", e);
                    }
                }
            }
        }
    }

    /**
     * To check whether there is any Expression having Constraint Entity as category.
     * @return true if there is any constraint put on category.
     */
    private boolean containsCategrory()
    {
    	boolean containsCategory = false;
        Set<IQueryEntity> queryEntities = constraints.getQueryEntities();
        for (IQueryEntity entity : queryEntities)
        {
            boolean isCategory = edu.wustl.cab2b.common.util.Utility.isCategory
            (entity.getDynamicExtensionsEntity());
            if (isCategory)
            {
              containsCategory = true;
              break;
            }
        }
        return containsCategory;
    }

    /**
     * To get the select part of the SQL.
     * @return The SQL for the select part of the query.
     */
    private String getSelectPart()
    {
        selectIndex = 0;
        String selectAttribute = "";
        for (OutputTreeDataNode rootOutputTreeNode : rootOpTreeNodLst)
        {
            selectAttribute += getSelectAttributes(rootOutputTreeNode);
        }
        // Deepti : added quick fix for bug 6950. Add distinct only when columns
        // do not include CLOB type.
        if (hasCLOBTypeCol)
        {
            selectAttribute = SELECT + selectAttribute;
        }
        else
        {
            selectAttribute = SELECT_DISTINCT + selectAttribute;
        }
        return selectAttribute;
    }

    /**
     * Removes the last comma from the given string.
     * @param select select clause
     * @return select clause with last comma removed
     */
    private String removeLastComma(String select)
    {
        if (select.endsWith(SELECT_COMMA))
        {
            select = select.substring(0, select.length() - SELECT_COMMA.length());
        }
        return select;
    }

    /**
     * It will return the select part attributes for this node along with its
     * child nodes.
     * @param treeNode the output tree node.
     * @return The select part attributes for this node along with its child
     *         nodes.
     */
    private String getSelectAttributes(OutputTreeDataNode treeNode)
    {
        StringBuffer selectPart = new StringBuffer("");
        IExpression expression = constraints.getExpression(treeNode.getExpressionId());

        IOutputEntity outputEntity = treeNode.getOutputEntity();
        List<AttributeInterface> attributes = outputEntity.getSelectedAttributes();
        EntityInterface deEntity = outputEntity.getDynamicExtensionsEntity();

        for (AttributeInterface attribute : attributes)
        {
            selectPart.append(fromBuilder.aliasOf(attribute, expression));
            String columnAliasName = COLUMN_NAME + selectIndex;
            selectPart.append(" " + columnAliasName + SELECT_COMMA);
            // code to get displayname. & pass it to the Constructor along with
            // treeNode.
            String className = edu.wustl.query.util.global.Utility.parseClassName(deEntity.getName());
            String attributeLabel = edu.wustl.common.util.Utility.getDisplayLabel(attribute.getName());
            String displayNmForCol = className+" : "+attributeLabel;
            treeNode.addAttribute(new QueryOutputTreeAttributeMetadata(attribute, columnAliasName, treeNode,
                    displayNmForCol));
            attrColNameMap.put(attribute, columnAliasName);
            selectIndex++;
            if ("file".equalsIgnoreCase(attribute.getDataType()))
            {
                hasCLOBTypeCol = true;
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
     * Select index.
     */
    private int selectIndex;

    /**
     * To compile the SQL & get the SQL representation of the Expression.
     * @param expression the Expression whose SQL to be generated.
     * @return The SQL representation of the Expression.
     * @throws SqlException When there is error in the passed IQuery object.
     * @throws RuntimeException RuntimeException
     */
    String getWherePartSQL(IExpression expression) throws SqlException, RuntimeException
    {
        StringBuffer buffer = new StringBuffer("");
        int currentNestingCounter = 0;
        // holds current nesting number count i.e. no of opening Braces that needs to be closed.
        int noOfRules = expression.numberOfOperands();
        for (int i = 0; i < noOfRules; i++)
        {
            IExpressionOperand operand = expression.getOperand(i);
            String operandSQL = "";
            boolean emptyExppression = false;
            if (operand instanceof IRule)
            {
                // Processing Rule.
                operandSQL = getSQL((IRule) operand);
            }
            else if (operand instanceof IExpression)
            {
                // Processing sub Expression.
                emptyExppression = emptyExpressions.contains(operand);
                if (!emptyExppression)
                {
                    operandSQL = getWherePartSQL((IExpression) operand);
                }
            }
            else
            {
                operandSQL = getCustomFormulaString((ICustomFormula) operand);
            }
            if (!"".equals(operandSQL) && noOfRules != 1)
            {
                operandSQL = "(" + operandSQL + ")";
                /*
                 * putting RuleSQL in Braces so that it will not get mixed with
                 * other Rules.
                 */
            }
            if (i != noOfRules - 1)
            {
                Connector connector = (Connector) expression.getConnector(i, i + 1);
                int nestingNumber = connector.getNestingNumber();

                int nextIndex = i + 1;
                IExpressionOperand nextOperand = expression.getOperand(nextIndex);
                if (nextOperand instanceof IExpression && emptyExpressions.contains(nextOperand))
                {
                    for (; nextIndex < noOfRules; nextIndex++)
                    {
                        nextOperand = expression.getOperand(nextIndex);
                        if (!(nextOperand instanceof IExpression && emptyExpressions.contains(nextOperand)))
                        {
                            break;
                        }
                    }
                    if (nextIndex == noOfRules)
                    // Expression over add closing parenthesis.
                    {
                        buffer.append(operandSQL);
                        buffer.append(getParenthesis(currentNestingCounter, ")"));
                        currentNestingCounter = 0;
                    }
                    else
                    {
                        Connector newConnector = (Connector) expression.getConnector(nextIndex - 1, nextIndex);
                        int newNestingNumber = newConnector.getNestingNumber();
                        currentNestingCounter = attachOperandSQL(buffer, currentNestingCounter, operandSQL,
                                newNestingNumber);
                        if(buffer.length() > 0)
                        {
                            buffer.append(' ').append(newConnector.getOperator());
                        }
                    }
                    i = nextIndex - 1;
                }
                else
                {
                    currentNestingCounter = attachOperandSQL(buffer,
                    		currentNestingCounter, operandSQL, nestingNumber);
                    if(buffer.length() > 0)
                    {
                        buffer.append(' ').append(connector.getOperator());
                    }
                }
            }
            else
            {
                buffer.append(operandSQL);
                // Finishing SQL by adding closing parenthesis if any.
                buffer.append(getParenthesis(currentNestingCounter, ")"));
                currentNestingCounter = 0;
            }
        }
        return buffer.toString();
    }

    /**
     * To append the operand SQL to the SQL buffer, with required number of
     * parenthesis.
     * @param buffer The reference to the String buffer containing SQL for SQL
     *            of operands of an expression.
     * @param currentNestingCounter The current nesting count.
     * @param operandSQL The SQL of the operand to be appended to buffer
     * @param nestingNumber The nesting number for the current operand's
     *            operator.
     * @return The updated current nesting count.
     */
    private int attachOperandSQL(StringBuffer buffer, int currentNestingCounter,
    		String operandSQL, int nestingNumber)
    {
        if (currentNestingCounter < nestingNumber)
        {
            buffer.append(getParenthesis(nestingNumber - currentNestingCounter, "("));
            currentNestingCounter = nestingNumber;
            buffer.append(operandSQL);
        }
        else if (currentNestingCounter > nestingNumber)
        {
            buffer.append(operandSQL);
            buffer.append(getParenthesis(currentNestingCounter - nestingNumber, ")"));
            currentNestingCounter = nestingNumber;
        }
        else
        {
            buffer.append(operandSQL);
        }
        return currentNestingCounter;
    }

    /**
     * To get n number of parenthesis.
     * @param number The positive integer value
     * @param parenthesis either Opening parenthesis or closing parenthesis.
     * @return The n number of parenthesis.
     */
    String getParenthesis(int number, String parenthesis)
    {
        String string = "";
        for (int i = 0; i < number; i++)
        {
            string += parenthesis;
        }
        return string;
    }

    /**
     * To get the SQL representation of the Rule.
     * @param rule The reference to Rule.
     * @return The SQL representation of the Rule.
     * @throws SqlException When there is error in the passed IQuery object.
     */
    String getSQL(IRule rule) throws SqlException
    {
        StringBuffer buffer = new StringBuffer("");

        int noOfConditions = rule.size();
        if (noOfConditions == 0)
        {
            throw new SqlException("No conditions defined in the Rule!!!");
        }
        for (int i = 0; i < noOfConditions; i++)
        // Processing all conditions in Rule and combine them with AND operator.
        {
            String condition = getSQL(rule.getCondition(i), rule.getContainingExpression());

            if (i == noOfConditions - 1) // Intermediate Condition.
            {
            	// Last Condition, this will not followed by And logical
                // operator.
                buffer.append(condition);
            }
            else
            {
            	buffer.append(condition + " " + LogicalOperator.And + " ");
            }
        }
        return buffer.toString();
    }

    /**
     * To get the SQL Representation of the Condition.
     * @param condition The reference to condition.
     * @param expression The reference to Expression to which this condition
     *            belongs.
     * @return The SQL Representation of the Condition.
     * @throws SqlException When there is error in the passed IQuery object.
     */
    String getSQL(ICondition condition, IExpression expression) throws SqlException
    {
        String sql = null;
        AttributeInterface attribute = condition.getAttribute();
        if(fromBuilder == null)
        {
        	fromBuilder = new FromBuilder(joinGraph);
        	if(getDatabaseSQLSettings().getDatabaseType().equals(DatabaseType.Oracle))
        	{
        		strTodateFunction = "TO_DATE";
        		datePattern = "mm-dd-yyyy";
        	}
        	else if(getDatabaseSQLSettings().getDatabaseType().equals(DatabaseType.MySQL))
        	{
        		strTodateFunction = "STR_TO_DATE";
        		datePattern = "%m-%d-%Y";
        	}
        }
        String attributeName = fromBuilder.aliasOf(attribute, expression);

        RelationalOperator operator = condition.getRelationalOperator();

        if (operator.equals(RelationalOperator.Between))
        {
            // Processing Between Operator, it will be treated as
            // (op>=val1 and op<=val2)
            sql = processBetweenOperator(condition, attributeName);
        }
        else if (operator.equals(RelationalOperator.In) || operator.equals(RelationalOperator.NotIn))
        {
            sql = processInOperator(condition, attributeName);
        }
        else if (operator.equals(RelationalOperator.IsNotNull) || operator.equals(RelationalOperator.IsNull))
        {
            sql = processNullCheckOperators(condition, attributeName);
        }
        else if (operator.equals(RelationalOperator.Contains) || operator.equals(RelationalOperator.StartsWith)
                || operator.equals(RelationalOperator.EndsWith))
        {
            sql = processLikeOperators(condition, attributeName);
        }
        else
        {
            // Processing rest operators like =, !=, <, > , <=, >= etc.
            sql = processComparisonOperator(condition, attributeName);
        }

        return sql;
    }

    /**
     * Processing operators like =, !=, <, > , <=, >= etc.
     * @param condition the condition.
     * @param attributeName the Name of the attribute to returned in SQL.
     * @return SQL representation for given condition.
     * @throws SqlException when: 1. value list contains more/less than 1 value.
     * 2. other than = ,!= operator present for String data type.
     */
    private String processComparisonOperator(ICondition condition, String attributeName) throws SqlException
    {
        AttributeTypeInformationInterface dataType = condition.getAttribute().getAttributeTypeInformation();
        RelationalOperator operator = condition.getRelationalOperator();
        List<String> values = condition.getValues();
        if (values.size() != 1)
        {
            throw new SqlException("Incorrect number of values found for Operator '"
            		+ operator + "' for condition:"+ condition);
        }
        String value = values.get(0);
        if (dataType instanceof StringTypeInformationInterface)
        {
            if (!(operator.equals(RelationalOperator.Equals) || operator.equals(RelationalOperator.NotEquals)))
            {
                throw new SqlException("Incorrect operator found for String datatype for condition:" + condition);
            }
        }

        if (dataType instanceof BooleanAttributeTypeInformation)
        {
            if (!(operator.equals(RelationalOperator.Equals) || operator.equals(RelationalOperator.NotEquals)))
            {
                throw new SqlException("Incorrect operator found for Boolean datatype for condition:" + condition);
            }
        }
        String conditionStr = "";
        if (dataType instanceof DateTypeInformationInterface)
        {
        	if(getDatabaseSQLSettings().getDatabaseType().equals(DatabaseType.MySQL))
        	{
        		attributeName =  strTodateFunction+"(DATE_FORMAT(DATE("+attributeName+"),'" +
        		datePattern + "'),'" + datePattern + "')";
        	}
        	else if(getDatabaseSQLSettings().getDatabaseType().equals(DatabaseType.Oracle))
        	{
        		attributeName = strTodateFunction+"(TO_CHAR(TRUNC("+attributeName+"),'" +
        		datePattern + "'),'" + datePattern + "')";
        	}
        }
        value = modifyValueforDataType(value, dataType);
        conditionStr = attributeName + RelationalOperator.getSQL(operator) + value;

        if(getDatabaseSQLSettings().getDatabaseType().equals(DatabaseType.Oracle)
        		&& dataType instanceof StringTypeInformationInterface)
        {
        	conditionStr = "lower(" + attributeName + ") "+RelationalOperator.getSQL(operator)+
        	" lower(" + value + ")";
        }
        return conditionStr;
    }

    /**
     * To process String operators. for e.g. starts with, contains etc.
     * @param condition the condition.
     * @param attributeName the Name of the attribute to returned in SQL.
     * @return SQL representation for given condition.
     * @throws SqlException when 1. The data type of attribute is not String. 2.
     *             The value list empty or more than 1 value.
     */
    private String processLikeOperators(ICondition condition, String attributeName) throws SqlException
    {
        RelationalOperator operator = condition.getRelationalOperator();

        if (!(condition.getAttribute().getAttributeTypeInformation() instanceof StringTypeInformationInterface
        	|| condition.getAttribute().getAttributeTypeInformation() instanceof FileTypeInformationInterface))
        {
            throw new SqlException("Incorrect data type found for Operator '" + operator + "' for condition:"
                    + condition);
        }

        List<String> values = condition.getValues();
        if (values.size() != 1)
        {
            throw new SqlException("Incorrect number of values found for Operator '"
            		+ operator + "' for condition:"+ condition);
        }
        String value = values.get(0);

        value = replaceSpecialChars(value,operator);
        String appName = CommonServiceLocator.getInstance().getAppName();
        DAOFactory daoFactory = (DAOFactory) DAOConfigFactory.getInstance().getDAOFactory(appName);
        String str = "";
        if("Oracle".equalsIgnoreCase(daoFactory.getDataBaseType()))
        {
        	str = "lower(" + attributeName + ") like lower(" + value + ")";
        }
        else
        {
        	 str = attributeName + " like " + value;
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
    private String processNullCheckOperators(ICondition condition, String attributeName) throws SqlException
    {
        String operatorStr = RelationalOperator.getSQL(condition.getRelationalOperator());
        if (condition.getValues().size() > 0)
        {
            throw new SqlException("No value expected in value part for '" + operatorStr + "' operator !!!");
        }
        return attributeName + " " + operatorStr;
    }

    /**
     * To process 'In' & 'Not In' operator.
     * @param condition the condition.
     * @param attributeName the Name of the attribute to returned in SQL.
     * @return SQL representation for given condition.
     * @throws SqlException when the value list is empty or problem in parsing
     *         any of the value.
     */
    private String processInOperator(ICondition condition, String attributeName) throws SqlException
    {
        StringBuffer buffer = new StringBuffer("");
        buffer.append(attributeName + " " + RelationalOperator.getSQL(condition.getRelationalOperator()) + " (");
        List<String> valueList = condition.getValues();
        AttributeTypeInformationInterface dataType = condition.getAttribute().getAttributeTypeInformation();
        StringBuffer valueStr = new StringBuffer();

        if (valueList.isEmpty())
        {
            throw new SqlException("at least one value required for 'In' operand list for condition:" + condition);
        }

        if (dataType instanceof BooleanAttributeTypeInformation)
        {
            throw new SqlException("Incorrect operator found for Boolean datatype for condition:" + condition);
        }
        for (int i = 0; i < valueList.size(); i++)
        {
            String value = modifyValueforDataType(valueList.get(i), dataType);

            if (i == valueList.size() - 1)
            {
            	valueStr.append("lower(").append(value).append("))");
                buffer.append(value).append(')');
            }
            else
            {
            	valueStr.append("lower(").append(value).append("),");
                buffer.append(value).append(',');
            }
        }
        if(getDatabaseSQLSettings().getDatabaseType().equals(DatabaseType.Oracle)
        		&& dataType instanceof StringTypeInformationInterface)
        {
        	buffer = new StringBuffer("lower(" + attributeName + ") "+
        	RelationalOperator.getSQL(condition.getRelationalOperator()) +" ("+ valueStr);
        }
        return buffer.toString();
    }

    /**
     * To get the SQL for the given condition with Between operator. It will be
     * treated as (operator>=val1 and operator<=val2)
     * @param condition The condition.
     * @param attributeName the Name of the attribute to returned in SQL.
     * @return SQL representation for given condition.
     * @throws SqlException when: 1. value list does not have 2 values 2.
     *             Data type is not date 3. problem in parsing date.
     */
    private String processBetweenOperator(ICondition condition, String attributeName) throws SqlException
    {
        StringBuffer buffer = new StringBuffer();
        List<String> values = condition.getValues();
        if (values.size() != AQConstants.TWO)
        {
            throw new SqlException("Incorrect number of operand for Between operator in condition:" + condition);
        }

        AttributeTypeInformationInterface dataType = condition.getAttribute().getAttributeTypeInformation();
        if (!(dataType instanceof DateTypeInformationInterface ||
        		dataType instanceof IntegerTypeInformationInterface
                || dataType instanceof LongTypeInformationInterface ||
                dataType instanceof DoubleTypeInformationInterface ||
                dataType instanceof FloatTypeInformationInterface))
        {
            throw new SqlException("Incorrect Data type of operand for Between operator in condition:"
            		+ condition);
        }

        String firstValue = modifyValueforDataType(values.get(0), dataType);
        String secondValue = modifyValueforDataType(values.get(1), dataType);

        buffer.append('(').append(attributeName);
        buffer.append(RelationalOperator.getSQL(RelationalOperator.GreaterThanOrEquals)).append(firstValue);
        buffer.append(' ').append(LogicalOperator.And).append(' ').append(attributeName)
        .append(RelationalOperator.getSQL(RelationalOperator.LessThanOrEquals)).append(secondValue).append(')');

        return buffer.toString();
    }

    /**
     * To Modify value as per the Data type. 1. In case of String data type,
     * replace occurrence of single quote by single quote twice. 2. Enclose the
     * Given values by single Quotes for String Data type.
     * @param value the Modified value.
     * @param dataType The DataType of the passed value.
     * @return The String representing encoded value for the given value &
     *         data type.
     * @throws SqlException when there is problem with the values, for Ex.
     *             unable to parse date/integer/double etc.
     */
    String modifyValueforDataType(String value, AttributeTypeInformationInterface dataType) throws SqlException
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
            value = modifyValueForDate(value);
        }
        else if (dataType instanceof BooleanTypeInformationInterface) // defining
        // value for boolean  data type.
        {
            value = modifyValueForBoolean(value);
        }
        else if (dataType instanceof IntegerTypeInformationInterface)
        {
            if (!new Validator().isNumeric(value))
            {
                throw new SqlException("Non numeric value found in value part!!!");
            }
        }
        else if (dataType instanceof DoubleTypeInformationInterface && !new Validator().isDouble(value))
        {
             throw new SqlException("Non numeric value found in value part!!!");
        }
        return value;
    }

    /**
     * To Modify value for Boolean data type.
     * For Boolean dataType it will change value to 1 if its TRUE, else 0.
     * @param value the Modified value.
     * @return The String representing encoded value for the given value &
     * data type.
     * @throws SqlException when there is problem with the values, for e.g.
     *             unable to parse date/integer/double etc.
     */
	private String modifyValueForBoolean(String value) throws SqlException
	{
		if (value == null || !(value.equalsIgnoreCase(edu.wustl.query.util.global.AQConstants.TRUE)
				|| value.equalsIgnoreCase(edu.wustl.query.util.global.AQConstants.FALSE)))
		{
		    throw new SqlException("Incorrect value found in value part for boolean operator!!!");
		}
		if (value.equalsIgnoreCase(edu.wustl.query.util.global.AQConstants.TRUE))
		{
		    value = "1";
		}
		else
		{
		    value = "0";
		}
		return value;
	}

    /**
     * To Modify value for Date data type.
     * Enclose the given values by single Quotes for Date Data type.
     * @param value the Modified value.
     * @return The String representing encoded value for the given value &
     * data type.
     * @throws SqlException when there is problem with the values, for e.g.
     *             unable to parse date/integer/double etc.
     */
	private String modifyValueForDate(String value) throws SqlException
	{
		try
		{
		    Date date = new Date();
		    date = Utility.parseDate(value);

		    Calendar calendar = Calendar.getInstance();
		    calendar.setTime(date);
		    value = (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + "-"
		            + calendar.get(Calendar.YEAR);
		  //  CommonServiceLocator.getInstance().
		    String strToDateFunction = strTodateFunction;
		    if (strToDateFunction == null || strToDateFunction.trim().equals(""))
		    {
		        strToDateFunction = "STR_TO_DATE"; // using MySQL function
		        // if the Value is not
		        // defined.
		    }
		    if (datePattern == null || datePattern.trim().equals(""))
		    {
		        datePattern = "%m-%d-%Y"; // using MySQL function if the
		        // Value is not defined.
		    }
		    String appName = CommonServiceLocator.getInstance().getAppName();
		    if("MSSQLSERVER".equals(DAOConfigFactory.getInstance().getDAOFactory(appName).
		    		getDataBaseType()))
		   	{
		    	value="CONVERT(datetime, '"+value+"', 110)";
		   	}
		    else
		    {
		    	value = strToDateFunction + "('" + value + "','" + datePattern + "')";
		    }
		}
		catch (ParseException parseExp)
		{
		    Logger.out.error(parseExp.getMessage(), parseExp);
		    throw new SqlException(parseExp.getMessage(), parseExp);
		}
		return value;
	}

    /**
     * This method will be used by Query Mock to set the join Graph externally.
     * @param joinGraph the reference to joinGraph.
     */
    void setJoinGraph(JoinGraph joinGraph)
    {
        this.joinGraph = joinGraph;
    }

    /**
     * To check if the Expression is empty or not. It will add
     * such empty expressions in the emptyExpressions set.
     * An expression is said to be empty when: - it contains no rule as operand. -
     * and all of its children(i.e subExpressions & their subExpressions & so
     * on) contains no rule
     * @param expressionId the reference to the expression id.
     * @return true if the expression is empty.
     */
    private boolean isEmptyExpression(int expressionId)
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
    }

    /**
     * To create output tree for the given expression graph.
     * @throws MultipleRootsException When there exists multiple roots in
     * join graph.
     */
    private void createTree() throws MultipleRootsException
    {
        IExpression rootExpression = joinGraph.getRoot();
        rootOpTreeNodLst = new ArrayList<OutputTreeDataNode>();

        OutputTreeDataNode rootOutputTreeNode = null;
        treeNo = 0;
        if (rootExpression.isInView())
        {
            IOutputEntity rootOutputEntity = getOutputEntity(rootExpression);
            rootOutputTreeNode = new OutputTreeDataNode(rootOutputEntity,
            		rootExpression.getExpressionId(), treeNo++);
            rootOpTreeNodLst.add(rootOutputTreeNode);
        }
        completeTree(rootExpression, rootOutputTreeNode);
    }

    /**
     * To create the output tree from the constraints.
     * @param expression The reference to Expression
     * @param parentOutputTreeNode The reference to parent output tree node.
     * null if there is no parent.
     */
    private void completeTree(IExpression expression, OutputTreeDataNode parentOutputTreeNode)
    {
        List<IExpression> children = joinGraph.getChildrenList(expression);
        for (IExpression childExp : children)
        {
            OutputTreeDataNode childNode = parentOutputTreeNode;
            /**
             * Check whether child node is in view or not. if it is in view then
             * create output tree node for it. else look for their children node &
             * create the output tree hierarchy if required.
             */
            if (childExp.isInView())
            {
                IOutputEntity childOutputEntity = getOutputEntity(childExp);
                if (parentOutputTreeNode == null)
                {
                    // New root node for output tree found, so create root
                    // node & add it in the rootOutputTreeNodeList.
                    childNode = new OutputTreeDataNode(childOutputEntity, childExp.getExpressionId(), treeNo++);
                    rootOpTreeNodLst.add(childNode);
                }
                else
                {
                    childNode = parentOutputTreeNode.addChild(childOutputEntity, childExp.getExpressionId());
                }
            }
            completeTree(childExp, childNode);
        }
    }

    /**
     * To get the Output Entity for the given Expression.
     * @param expression The reference to the Expression.
     * @return The output entity for the Expression.
     */
    private IOutputEntity getOutputEntity(IExpression expression)
    {
        EntityInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();
        IOutputEntity outputEntity = QueryObjectFactory.createOutputEntity(entity);
        outputEntity.getSelectedAttributes().addAll(entity.getEntityAttributesForQuery());
        return outputEntity;
    }

    /**
     * @return the rootOutputTreeNodeList
     */
    public List<OutputTreeDataNode> getRootOutputTreeNodeList()
    {
        return rootOpTreeNodLst;
    }

    // //////// CUSTOM FORMULA

    /**
     * Returns the CustomFormulaProcessor.
     * @return CustomFormulaProcessor object
     */
    private CustomFormulaProcessor getCustomFormulaProcessor()
    {
        return new CustomFormulaProcessor(getAliasProvider(),getDatabaseSQLSettings());
    }

    /**
     * Get the database settings.
     * @return Object of DatabaseSQLSettings
     */
    private DatabaseSQLSettings getDatabaseSQLSettings()
    {
        String appName = CommonServiceLocator.getInstance().getAppName();
        String databaseName = DAOConfigFactory.getInstance().getDAOFactory(appName).getDataBaseType();
        DatabaseType databaseType;
        if (databaseName.equalsIgnoreCase(AQConstants.MYSQL_DATABASE))
        {
            databaseType = DatabaseType.MySQL;
        }
        else if (databaseName.equals(AQConstants.ORACLE_DATABASE))
        {
            databaseType = DatabaseType.Oracle;
        }
        else if ("MSSQLSERVER".equals(DAOConfigFactory.getInstance().getDAOFactory(appName).getDataBaseType()))
        {
            databaseType = DatabaseType.MsSqlServer;
        }
        else
        {
            throw new UnsupportedOperationException("Custom formulas on " + databaseName
                    + " are not supported.");
        }
        return new DatabaseSQLSettings(databaseType);
    }

    /**
     * Returns the alias provider.
     * @return alias provider
     */
    private IAttributeAliasProvider getAliasProvider()
    {
        return new IAttributeAliasProvider() {

            public String getAliasFor(IExpressionAttribute exprAttr)
            {
                return fromBuilder.aliasOf(exprAttr.getAttribute(), exprAttr.getExpression());
            }
        };
    }

    /**
     * Get custom formula in the form of string.
     * @param formula The custom formula
     * @return string representation of formula
     */
    protected String getCustomFormulaString(ICustomFormula formula)
    {
        return getCustomFormulaProcessor().asString(formula);
    }

    // output terms

    /**
     * Get the term processor.
     * @return the Object of TermProcessor
     */
    private TermProcessor getTermProcessor()
    {
        return new TermProcessor(getAliasProvider(), getDatabaseSQLSettings());
    }

    /**
     * TODO need to re factor Remove DB type check;
     * @return
     */
/*    private Object getPrimitiveOperationProcessor()
    {

    	Object oprProcessor;
    	String appName = CommonServiceLocator.getInstance().getAppName();
        DAOFactory daoFactory = (DAOFactory) DAOConfigFactory.getInstance().getDAOFactory(appName);
    	if("MYSQL".equals(daoFactory.getDataBaseType()))
        {
    		oprProcessor = new MySQLPrimitiveOperationProcessor();
        }
        else
        {
        	oprProcessor =new OraclePrimitiveOperationProcessor();
        }
        return oprProcessor;
    }*/

    /**
     * Returns the term string.
     * @param term term
     * @return The term string
     */
    private String getTermString(ITerm term)
    {
    	return getTermProcessor().convertTerm(term).getString();
       /* TermString termString = getTermProcessor().convertTerm(term);
        String termStr = termString.getString();
        return termStr;*/
    }

    /**
     * @param terms List of output terms.
     * @return select clause for output terms
     */
    private String getSelectForOutputTerms(List<IOutputTerm> terms)
    {
        outputTermsColumns = new HashMap<String, IOutputTerm>();
        StringBuffer selectClause = new StringBuffer();
        for (IOutputTerm term : terms)
        {
            String termString = "(" + getTermString(term.getTerm()) + ")";
            termString = modifyForTimeInterval(termString, term.getTimeInterval());
            String columnName = COLUMN_NAME + selectIndex++;
            selectClause.append(termString + " " + columnName + SELECT_COMMA);
            outputTermsColumns.put(columnName, term);
        }
        return removeLastComma(selectClause.toString());
    }

    /**
     * @param termString termString
     * @param timeInterval timeInterval
     * @return termString
     */
    private String modifyForTimeInterval(String termString, TimeInterval<?> timeInterval)
    {
        if (timeInterval != null)
        {
	        termString = termString + "/" + timeInterval.numSeconds();
	        String appName = CommonServiceLocator.getInstance().getAppName();
	        if("MSSQLSERVER".equals(DAOConfigFactory.getInstance().getDAOFactory(appName).getDataBaseType()))
	        {
	        	termString = "cast(ROUND(" + termString + ",0)as bigint)";
	        }
	        else
	        {
	        	termString = "ROUND(" + termString + ")";
	        }
        }
        return termString;
    }

    /**
     * outputTermsColumns Used to store the temporal query details.
     */
    private Map<String, IOutputTerm> outputTermsColumns;

    /**
     * Returns the output term columns.
     * @return the outputTermsColumns
     */
    public Map<String, IOutputTerm> getOutputTermsColumns()
    {
        return outputTermsColumns;
    }

    /**
     * Returns the AttributeColumnNameMap.
     * @return the attributeColumnNameMap
     */
    public Map<AttributeInterface, String> getAttributeColumnNameMap()
    {
        return attrColNameMap;
    }

    /**
     * Checks if the query contains entities from multiple groups.
     * @return <CODE>true</CODE> query contains entities from multiple groups,
	 * <CODE>false</CODE> otherwise
     */
    public boolean isQueryOnMultipleEntityGroups()
    {
        boolean isQueryOnMultipleEntityGroups = false;

        List<String> entityGroupList = new ArrayList<String>();
        Set<IQueryEntity> queryEntityList = constraints.getQueryEntities();
        for(IQueryEntity queryEntity : queryEntityList)
        {
            EntityInterface entityObject = queryEntity.getDynamicExtensionsEntity();
            String entityGroupName = entityObject.getEntityGroup().getShortName();
            if(entityGroupList.isEmpty())
            {
            	entityGroupList.add(entityGroupName);
            }
            else
            {
            	if(!entityGroupList.contains(entityGroupName))
                {
                    isQueryOnMultipleEntityGroups = true;
                    //entityGroupList.add(entityGroupName);
                    break;
                }
            }
        }
        return isQueryOnMultipleEntityGroups;
    }
    /**
     * This method replaces special characters with the escape sequences from the entered value.
     * @param value entered by user.
     * @param operator Relational operator
     * @return special characters replaced by escape sequences.
     */
    private String replaceSpecialChars(String value, RelationalOperator operator)
    {
    	String appName = CommonServiceLocator.getInstance().getAppName();
    	DAOFactory daoFactory = (DAOFactory) DAOConfigFactory.getInstance().getDAOFactory(appName);
    	if(AQConstants.ORACLE_DATABASE.equals(daoFactory.getDataBaseType()))
    	{
    		if(value.contains("'"))
    		{
    			String character = "'";
    			value = value.replaceAll(character, character+character);
    		}
    		if(value.contains("%"))
    		{
    			String character = "%";
    			value = value.replaceAll(character, escapeCharOracle+character);
    		}
    		String finalValue = getOperatorSpecificValue(value, operator);
    		if(value.contains("%"))
    		{
    			finalValue = finalValue + " "+escapeSeqOracle;
    		}
    		value = finalValue;
    	}
    	else if(AQConstants.MYSQL_DATABASE.equals(daoFactory.getDataBaseType()))
    	{
    		String character = "";
    		if(value.contains("%") )
    		{
    			character = "%";
    			String escapedValue = escapeCharacter + escapeCharacter + character;
    			value = value.replaceAll(character, escapedValue);
    		}
    		if(value.contains("'"))
    		{
    			character = "'";
    			String escapedValue = escapeCharacter + escapeCharacter + character;
    			value = value.replaceAll(character, escapedValue);
    		}
    		value = getOperatorSpecificValue(value, operator);
    	}
    	return value;
    }
    /**
     * This method changes the value as per RelationalOperator's value.
     * @param value entered by user.
     * @param operator RelationalOperator.
     * @return value as per RelationalOperators
     */
    private String getOperatorSpecificValue(String value,
			RelationalOperator operator)
    {
		if (operator.equals(RelationalOperator.Contains))
		{
			value = "'%" + value + "%'";
		}
		else if (operator.equals(RelationalOperator.StartsWith))
		{
			value = "'" + value + "%'";
		}
		else if (operator.equals(RelationalOperator.EndsWith))
		{
			value = "'%" + value + "'";
		}
		return value;
	}
  }