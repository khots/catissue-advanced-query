
package edu.wustl.common.query.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.common.dynamicextensions.domaininterface.AssociationInterface;
import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.AttributeTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.DateTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.common.dynamicextensions.domaininterface.IntegerTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.LongTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.StringTypeInformationInterface;
import edu.common.dynamicextensions.exception.DynamicExtensionsSystemException;
import edu.wustl.common.query.exeptions.SQLXMLException;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.exceptions.SqlException;
import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IExpressionOperand;
import edu.wustl.common.querysuite.queryobject.IOutputEntity;
import edu.wustl.common.querysuite.queryobject.IOutputTerm;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.IRule;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.querysuite.queryobject.impl.Expression;
import edu.wustl.common.querysuite.queryobject.impl.JoinGraph;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.queryengine.impl.IQueryGenerator;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.util.querysuite.QueryCSMUtil;

public class XQueryGenerator implements IQueryGenerator
{

	/**
	* This map holds integer value that will be appended to each table alias in
	* the sql.
	*/
	private Map<IExpression, Integer> aliasAppenderMap = new HashMap<IExpression, Integer>();

	/**
	 * reference to the joingraph object present in the query object.
	 */
	private JoinGraph joinGraph;

	/**
	 * reference to the constraints object present in the query object.
	 */
	private IConstraints constraints;

	public static final String COLUMN_NAME = "Column";

	/**
	 * This set will contain the expression ids of the empty expression. An
	 * expression is empty expression when it does not contain any Rule & its
	 * sub-expressions (also their subexpressions & so on) also does not contain
	 * any Rule
	 */
	private Set<IExpression> emptyExpressions;// Set of Empty Expressions.

	//Variables required for output tree.
	/**
	 * List of Roots of the output tree node.
	 */
	private List<OutputTreeDataNode> rootOutputTreeNodeList;

	/**
	 * This map is used in output tree creation logic. It is map of alias
	 * appender verses the output tree node. This map is used to ensure that no
	 * duplicate output tree node is created for the expressions having same
	 * alias appender.
	 */
	private Map<Integer, OutputTreeDataNode> outputTreeNodeMap;

	/**
	 * This map contains information about the tree node ids, attributes & their
	 * correspoiding column names in the generated SQL. - Inner most map Map<AttributeInterface,
	 * String> contains mapping of attribute interface verses the column name in
	 * SQL. - The outer map Map<Long, Map<AttributeInterface, String>>
	 * contains mapping of treenode Id verses the map in above step. This map
	 * contains mapping required for one output tree. - The List contains the
	 * mapping of all output trees that are formed by the query.
	 */
	// List<Map<Long, Map<AttributeInterface, String>>> columnMapList;
	private int treeNo; // this count represents number of output trees formed.
	private Map<AttributeInterface, String> attributeColumnNameMap = new HashMap<AttributeInterface, String>();
	/**
	 * Default Constructor to instantiate SQL generator object.
	 */
	private List<String> TableList;

	private Set<IExpression> pAndExpressions;

	private Map<String, IOutputTerm> outputTermsColumns;

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

	/*
	 * The following function takes IQuery object as input for further processing
	 * @parameters : IQuery query= The query object
	 * @parameters : char QueryType = representing the kind of query whether aggregate or normal
	 */
	public String generateQuery(IQuery query) throws MultipleRootsException
	{
		String sqlxml = "";
		try
		{
			char QueryType = 'N';
			sqlxml = (String) buildQuery(query, QueryType);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (SQLXMLException e)
		{
			e.printStackTrace();
		}
		catch (RuntimeException e)
		{
			e.printStackTrace();
		}
		catch (SqlException e)
		{
			e.printStackTrace();
		}
		catch (DynamicExtensionsSystemException e)
		{
			e.printStackTrace();
		}
		return sqlxml;
	}

	public String buildQuery(IQuery query, char QueryType) throws MultipleRootsException,
			SQLException, RuntimeException, SQLXMLException, SqlException,
			DynamicExtensionsSystemException
	{

		String FormedQuery = "";
		IQuery queryClone = (IQuery) getObjectCopy(query);
		constraints = queryClone.getConstraints();

		this.joinGraph = (JoinGraph) constraints.getJoinGraph();
		IExpression rootExpression = constraints.getRootExpression();

		emptyExpressions = new HashSet<IExpression>();
		isEmptyExpression(rootExpression.getExpressionId());

		// Generating output tree.
		createTree();

		String select = getSelectPart(QueryType);
		String column = getColumn() + ")";
		//        if(QueryType == 'N')
		//        {
		FormedQuery = select + column;
		//        }
		//        else if(QueryType == 'C')
		//        {
		//        	FormedQuery = select + column + ')';
		//        }
		return FormedQuery;

	}

	/**
	 * Method to create deep copy of the object.
	 * @param obj The object to be copied.
	 * @return The Object reference representing deep copy of the given object.
	 */
	public static Object getObjectCopy(Object obj)
	{
		Object copy = null;
		try
		{
			// Write the object out to a byte array
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(obj);
			out.flush();
			out.close();
			// Make an input stream from the byte array and read
			// a copy of the object back in.
			ObjectInputStream in = new ObjectInputStream(
					new ByteArrayInputStream(bos.toByteArray()));
			copy = in.readObject();
		}
		catch (IOException e)
		{
			Logger.out.error(e.getMessage(), e);
			copy = null;
		}
		catch (ClassNotFoundException cnfe)
		{
			Logger.out.error(cnfe.getMessage(), cnfe);
			copy = null;
		}
		return copy;
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
	void createAliasAppenderMap() throws MultipleRootsException
	{
		for (IExpression expr : constraints)
		{
			aliasAppenderMap.put(expr, expr.getExpressionId());
		}
	}

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
	 * 
	 * @throws MultipleRootsException When there exists multiple roots in
	 *             joingraph.
	 */
	private void createTree() throws MultipleRootsException
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
	}

	/**
	 * TO create the output tree from the constraints.
	 * 
	 * @param expression The reference to Expression
	 * @param parentOutputTreeNode The reference to parent output tree node.
	 *            null if there is no parent.
	 */
	private void completeTree(IExpression expression, OutputTreeDataNode parentOutputTreeNode)
	{
		List<IExpression> children = joinGraph.getChildrenList(expression);
		for (IExpression childExp : children)
		{
			OutputTreeDataNode childNode = parentOutputTreeNode;
			/**
			 * Check whether chid node is in view or not. if it is in view then
			 * create output tree node for it. else look for their children node &
			 * create the output tree heirarchy if required.
			 */
			if (childExp.isInView())
			{
				IOutputEntity childOutputEntity = getOutputEntity(childExp);
				Integer childAliasAppender = aliasAppenderMap.get(childExp);

				/**
				 * Check whether output tree node for expression with the same
				 * alias already added or not. if its not added then need to add
				 * it alias in the outputTreeNodeMap
				 */
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
	}

	/**
	 * To get the Output Entity for the given Expression.
	 * 
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

	public Map<String, IOutputTerm> getOutputTermsColumns()
	{
		return outputTermsColumns;
	}

	public Map<AttributeInterface, String> getAttributeColumnNameMap()
	{
		return attributeColumnNameMap;
	}

	public List<OutputTreeDataNode> getRootOutputTreeNodeList()
	{
		return rootOutputTreeNodeList;
	}

	public String getSelectPart(char QueryType) throws SQLException, RuntimeException,
			MultipleRootsException, SQLXMLException, DynamicExtensionsSystemException
	{
		String xmlWhereClause = "";
		String xmlForClause = "";
		String xmlLetClause = "";
		String xmlreturnClause = "";
		StringBuffer xmlGetLetQueryPart = new StringBuffer();
		StringBuffer xmlGetForQueryPart = new StringBuffer(256);
		StringBuffer selectAttribute = new StringBuffer(256);
		List<AttributeInterface> attributes = new ArrayList<AttributeInterface>();
		List<String> EntityList = new ArrayList<String>();
		String actualPath = "";

		xmlGetForQueryPart.append("for ");
		xmlGetLetQueryPart.append(" let ");

		TableList = getTableNameList();
		selectAttribute.append("Select distinct " + getAtrributeList() + " from xmltable('");

		for (String tableName : TableList)
		{
			xmlGetForQueryPart.append("$" + tableName + " in db2-fn:xmlcolumn(\"CIDERWU." + tableName
					+ ".XMLDATA\") ,");
		}

		/*for (IExpression expressions : constraints)
		{
			String XPath = "";
			EntityInterface entity = expressions.getQueryEntity().getDynamicExtensionsEntity();
			List<AssociationInterface> associationList = QueryCSMUtil
					.getIncomingContainmentAssociations(entity);
			if (!associationList.isEmpty())
			{
				for (AssociationInterface associations : associationList)
				{
					int i = associations.getTargetRole().getMaximumCardinality().getValue();
					if (i > 1)
					{
						XPath = getAppropriatePath(entity, XPath, EntityList, actualPath);
						xmlGetForQueryPart.append("$" + associations.getTargetEntity().getName()
								+ " in " + XPath + "/" + associations.getTargetEntity().getName());
					}
				}
				//xmlGetForQueryPart.append("$" + 
			}

		}*/

		for (IExpression expressions : constraints)
		{
			String mainRoot = "";
			String Xpath = "";
			EntityInterface entity = expressions.getQueryEntity().getDynamicExtensionsEntity();
			String tableName = getTableName(entity, mainRoot);
			String Path = getXPath(entity, Xpath);
			attributes = getAttributes(expressions);
			for (AttributeInterface attribute : attributes)
			{
				if (attribute.getName().equalsIgnoreCase("id"))
				{
					xmlGetLetQueryPart.append("$" + entity.getName().toString() + ":= $"
							+ tableName + "/" + Path + "/" + attribute.getName().toString() + " ,");
				}
				else
				{
					xmlGetLetQueryPart.append("$" + attribute.getName().toString() + ":= $"
							+ tableName + "/" + Path + "/" + attribute.getName().toString() + " ,");
				}
			}
		}

		xmlForClause = removeLastComma(xmlGetForQueryPart.toString());
		xmlLetClause = removeLastComma(xmlGetLetQueryPart.toString());
		xmlWhereClause = " where " + removeLastAnd(getConditions());
		xmlreturnClause = getReturnClause();

		selectAttribute.append(xmlForClause + xmlLetClause + xmlWhereClause + xmlreturnClause);

		return selectAttribute.toString();
	}

	/**
	 * Adds an pseudo anded expression & all its child expressions to
	 * pAndExpressions set.
	 * 
	 * @param expression pAnd expression
	 */
	private void addpAndExpression(IExpression expression)
	{
		List<IExpression> childList = joinGraph.getChildrenList(expression);
		pAndExpressions.add(expression);

		for (IExpression newExp : childList)
		{
			addpAndExpression(newExp);
		}

	}

	private String getAppropriatePath(EntityInterface entity, String path, List<String> EntityList,
			String actualPath) throws DynamicExtensionsSystemException
	{
		String sourceEntity = "";
		String tableName = "";
		StringBuilder newActualPath = new StringBuilder(actualPath);
		String newPath = path;

		List<AssociationInterface> associationList = QueryCSMUtil
				.getIncomingContainmentAssociations(entity);
		if (!associationList.isEmpty())
		{
			for (AssociationInterface associations : associationList)
			{
				sourceEntity = associations.getEntity().getName();
				if (EntityList.contains(sourceEntity))
				{
					try
					{
						tableName = entity.getTableProperties().getName();
						if (tableName.substring(3).equalsIgnoreCase("DE_"))
						{
							newActualPath.append(sourceEntity);
						}
						else
						{
							newActualPath.insert(0, tableName);
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					newPath = "/" + sourceEntity;
					EntityInterface newEntity = associations.getEntity();
					newActualPath.append(newPath);
					newActualPath.insert(0, getAppropriatePath(newEntity, newPath, EntityList,
							newActualPath.toString()));
					
					
				}
			}
		}
		else
		{
			tableName = entity.getTableProperties().getName();
			newActualPath.insert(0, tableName);
		}

		return newActualPath.toString();
	}

	private String getForTree(EntityInterface entity, String rootVariable)
			throws DynamicExtensionsSystemException
	{
		String entityName = entity.getName();

		StringBuilder forTree = new StringBuilder();

		EntityInterface parentEntity = null;

		List<AssociationInterface> associationList = QueryCSMUtil
				.getIncomingContainmentAssociations(entity);
		if (!associationList.isEmpty())
		{
			for (AssociationInterface association : associationList)
			{
				parentEntity = association.getEntity();
				forTree.append(" for $");
				forTree.append(entityName);
				forTree.append(" in $");
				forTree.append(parentEntity.getName());
				forTree.append("/");
				forTree.append(entityName);
				forTree.insert(0, getForTree(parentEntity, rootVariable));

			}
		}
		else
		{
			forTree.append(" for $").append(entityName).append(" in ").append(rootVariable).append("/").append(entityName);
		}

		return forTree.toString();

	}

	private String getTableName(EntityInterface entity, String mainEntity)
	{
		String newMainEntity = mainEntity;

		try
		{
			List<AssociationInterface> associationList = QueryCSMUtil
					.getIncomingContainmentAssociations(entity);
			if (!associationList.isEmpty())
			{
				for (AssociationInterface assocoation : associationList)
				{
					newMainEntity = getTableName(assocoation.getEntity(), newMainEntity);
				}
			}
			else
			{
				newMainEntity = entity.getTableProperties().getName();
			}
		}
		catch (DynamicExtensionsSystemException deExeption)
		{
			deExeption.printStackTrace();
		}
		return newMainEntity;
	}

	private String getReturnClause()
	{
		StringBuffer buffer = new StringBuffer(128);
		buffer.append(" return <return>");
		for (IExpression expression : constraints)
		{
			if (expression.isInView())
			{
				EntityInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();
				List<AttributeInterface> attributes = (List<AttributeInterface>) entity
						.getAllAttributes();
				for (AttributeInterface attribute : attributes)
				{
					String attrName = attribute.getName();
					if (attrName.equalsIgnoreCase("id"))
					{
						attrName = entity.getName();
						buffer.append("<" + attrName + ">{$" + attrName + "}</" + attrName + ">");
					}
					else
					{
						buffer.append("<" + attrName + ">{$" + attrName + "}</" + attrName + ">");
					}
				}

			}
		}
		buffer.append("</return>'");
		return buffer.toString();
	}

	private String getConditions() throws SQLException, SQLXMLException
	{
		
		StringBuilder operandRule = new StringBuilder(processOperands());

		operandRule.append(" ");
		if(TableList.contains("DEMOGRAPHICS"))
		{
		operandRule.append(Variables.properties.getProperty("xquery.wherecondition.activeUpiFlag"));
		operandRule.append(" and ");
		operandRule
				.append(Variables.properties.getProperty("xquery.wherecondition.researchOptOut"));
		operandRule.append(" and ");
		}
		operandRule
				.append(Variables.properties.getProperty("xquery.wherecondition.startTimeStamp"));
		operandRule.append(" and ");
		operandRule.append(Variables.properties.getProperty("xquery.wherecondition.endTimeStamp"));
		operandRule.append(" ");

		return operandRule.toString();
	}

	private String processOperands() throws SQLException, SQLXMLException
	{
		StringBuffer operandQuery = new StringBuffer();

		for(IExpression expressions : constraints)
		{
		int noOfRules = expressions.numberOfOperands();
		for (int i = 0; i < noOfRules; i++)
		{
			IExpressionOperand operand = expressions.getOperand(i);
			if (operand instanceof IRule)
			{
				operandQuery.append(getQueryCondition((IRule) operand) + " and "); // Processing Rule.

			}
			else if (operand instanceof IExpression)
			{
				operandQuery.append("");
			}
		}
		}

		return operandQuery.toString();
	}

	private String removeLastAnd(String select)
	{
		String selectString = select;
		if (select.endsWith("and "))
		{
			selectString = selectString.substring(0, selectString.length() - 5);
		}
		return selectString;
	}

	/**
	* To get the SQL representation of the Rule.
	* 
	* @param rule The reference to Rule.
	* @return The SQL representation of the Rule.
	* @throws SqlException When there is error in the passed IQuery object.
	* @throws SQLException 
	* @throws SQLXMLException 
	*/
	private String getQueryCondition(IRule rule) throws SQLException, SQLXMLException
	{
		StringBuffer buffer = new StringBuffer("");

		int noOfConditions = rule.size();
		if (noOfConditions == 0)
		{
			throw new SQLXMLException("No conditions defined in the Rule!!!");
		}
		for (int i = 0; i < noOfConditions; i++)
		{
			String condition = getQueryCondition(rule.getCondition(i), rule
					.getContainingExpression());
			if (i != noOfConditions - 1)
			{
				buffer.append(condition + " and ");
			}
			else
			{
				buffer.append(condition);
			}
		}
		return buffer.toString();
	}

	/**
	 * 
	 * @param condition ICondition Object
	 * @param expression IExpression Object
	 * @return The set of conditions along with actual xpath
	 * @throws SQLException
	 */

	private String getQueryCondition(ICondition condition, IExpression expression)
			throws SQLException
	{
		String query = null;

		AttributeInterface attribute = condition.getAttribute();
		String attributeName = attribute.getName();

		if (attributeName.equalsIgnoreCase("id"))
		{
			EntityInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();
			attributeName = entity.getName();
		}

		query = processComparisionOperator(condition, attributeName);
		return query;
	}

	private String processComparisionOperator(ICondition condition, String attributeName)
	{

		RelationalOperator operator = condition.getRelationalOperator();
		List<String> values = condition.getValues();
		StringBuilder value = new StringBuilder();
		for (int i = 0; i < values.size(); ++i)
		{
			value.append(values.get(i)).append(" ,");
		}
		
		value = new StringBuilder(removeLastComma(value.toString()));

		value = new StringBuilder(getValueForType(condition, value.toString()));

		value.insert(0, "(").append(")");
		StringBuilder Operator = new StringBuilder(getActualValue(RelationalOperator.getSQL(operator), attributeName, value.toString()));
		
		if (Operator.toString().equalsIgnoreCase(""))
		{
			Operator.append("$").append(attributeName).append(" ").append(RelationalOperator.getSQL(operator)).append(" ").append(value);
		}
		
		return Operator.toString();
	}

	private String getActualValue(String operator, String attributeName, String value)
	{
		String newOperator = "";

		if (operator.equalsIgnoreCase("is NOT NULL"))
		{
			newOperator = "exists($" + attributeName + ")";
			
		}
		else if (operator.equalsIgnoreCase("Contains"))
		{
			newOperator = "contains($" + attributeName + ",\"" + value + "\")";
			
		}
		else if (operator.equalsIgnoreCase("StartsWith"))
		{
			newOperator = "starts-with($" + attributeName + ",\"" + value + "\")";
			
		}
		else if (operator.equalsIgnoreCase("EndsWith"))
		{
			newOperator = "ends-with($" + attributeName + ",\"" + value + "\")";
			
		}
		
		return newOperator;
	}

	private String getValueForType(ICondition condition, String value)
	{
		AttributeTypeInformationInterface dataType = condition.getAttribute()
				.getAttributeTypeInformation();
		StringBuilder actualValue = new StringBuilder();
		
		if (dataType instanceof StringTypeInformationInterface)
		{
			actualValue.append("\"").append(value).append("\"");
		}
		else if (dataType instanceof DateTypeInformationInterface)
		{
			String actualYear = value.substring(6);
        	String actualMonth = value.substring(0,2);
        	String actualDate = value.substring(3,5);
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

	private String getXPath(EntityInterface entity, String XPath)
	{
		StringBuilder newXPath = new StringBuilder(XPath);
		EntityInterface newEntity = entity;

		try
		{
			List<AssociationInterface> associationList = QueryCSMUtil
					.getIncomingContainmentAssociations(newEntity);
			if (!associationList.isEmpty())
			{
				for (AssociationInterface assocoation : associationList)
				{
					int maxCardinality = assocoation.getTargetRole().getMaximumCardinality().getValue();
					if(maxCardinality == 1)
					{
						newXPath.append("/").append(assocoation.getTargetRole().getName());
					}
					else
					{
						String firstChar = assocoation.getTargetEntity().getName().substring(0, 1);
						String originalTargetEntity = firstChar.toLowerCase();
						String newTargetEntity = assocoation.getTargetEntity().getName().replaceFirst(firstChar, originalTargetEntity);
						StringBuilder intermediatePart = new StringBuilder("/");
						intermediatePart.append(assocoation.getTargetRole().getName()).append("/").append(newTargetEntity);
						newXPath.insert(0, intermediatePart);
					}
					newEntity = assocoation.getEntity();
					newXPath = new StringBuilder(getXPath(newEntity,newXPath.toString()));
				}
			}
			else
			{
				newXPath.insert(0,newEntity.getName());
			}
		}
		catch (DynamicExtensionsSystemException deExeption)
		{
			deExeption.printStackTrace();
		}
		return newXPath.toString();
	}

	private List<AttributeInterface> getAttributes(IExpression expression)
	{
		List<AttributeInterface> attributes = new ArrayList<AttributeInterface>();
		EntityInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();
		attributes = (List<AttributeInterface>) entity.getAllAttributes();
		return attributes;
	}

	private List<String> getTableNameList() throws DynamicExtensionsSystemException
	{
		List<String> tableNameList = new ArrayList<String>();
		for (IExpression expression : constraints)
		{
			List<EntityInterface> mainEntityList = new ArrayList<EntityInterface>();
			EntityInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();
			List<EntityInterface> mainRoot = QueryCSMUtil
					.getAllMainEntities(entity, mainEntityList);
			String tableName = mainRoot.get(0).getTableProperties().getName();
			if (!tableNameList.contains(tableName))
			{
				tableNameList.add(tableName);
			}

		}
		return tableNameList;
	}

	private String getAtrributeList()
	{
		StringBuffer attributeName = new StringBuffer();
		for (IExpression allExpression : constraints)
		{
			if (allExpression.isInView())
			{
				EntityInterface entity = allExpression.getQueryEntity()
						.getDynamicExtensionsEntity();
				List<AttributeInterface> attributes = (List<AttributeInterface>) entity
						.getAllAttributes();
				for (AttributeInterface attribute : attributes)
				{
					if (attribute.getName().equalsIgnoreCase("id"))
					{
						attributeName.append(entity.getName() + " ,");
					}
					else
					{
						attributeName.append(attribute.getName() + " ,");
					}
				}
			}
		}
		return removeLastComma(attributeName.toString());
	}

	private String getColumn()
	{
		StringBuffer buffer = new StringBuffer(20);
		buffer.append(" columns ");
		for (IExpression expression : constraints)
		{
			if (expression.isInView())
			{
				EntityInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();
				List<AttributeInterface> attributes = (List<AttributeInterface>) entity
						.getAllAttributes();
				for (AttributeInterface attribute : attributes)
				{
					String attrName = attribute.getName();
					String dataType = getDataTypeInformation(attribute);
					if (attrName.equalsIgnoreCase("id"))
					{
						attrName = entity.getName();
						buffer
								.append(attrName + " " + dataType + " path '" + attrName + "'"
										+ " ,");
					}
					else
					{
						buffer
								.append(attrName + " " + dataType + " path '" + attrName + "'"
										+ " ,");
					}
				}

			}
		}
		return removeLastComma(buffer.toString());
	}

	private String removeLastComma(String select)
	{
		String newSelect = select;

		if (newSelect.endsWith(" ,"))
		{
			newSelect = newSelect.substring(0, newSelect.length() - 2);
		}
		return newSelect;
	}

	private String getDataTypeInformation(AttributeInterface attribute)
	{
		String returnValue = null;
		
		AttributeTypeInformationInterface dataType = attribute.getAttributeTypeInformation();
		if (dataType instanceof StringTypeInformationInterface)
		{
			returnValue =  "varchar(500)";
		}
		else if (dataType instanceof DateTypeInformationInterface)
		{
			returnValue =  "varchar(500)";
		}
		else if (dataType instanceof LongTypeInformationInterface)
		{
			returnValue = "varchar(500)";
		}
		else if (dataType instanceof IntegerTypeInformationInterface)
		{
			returnValue = "varchar(500)";
		}
		
		return returnValue;
	}

}
