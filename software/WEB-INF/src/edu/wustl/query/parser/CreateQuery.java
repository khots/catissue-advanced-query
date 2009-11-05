
package edu.wustl.query.parser;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.common.dynamicextensions.domain.validationrules.Rule;
import edu.common.dynamicextensions.exception.DynamicExtensionsApplicationException;
import edu.common.dynamicextensions.exception.DynamicExtensionsSystemException;
import edu.wustl.common.querysuite.exceptions.CyclicException;
import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.querableobject.QueryableEntityAttribute;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;
import edu.wustl.common.querysuite.queryobject.ArithmeticOperator;
import edu.wustl.common.querysuite.queryobject.IArithmeticOperand;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IConnector;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IDateLiteral;
import edu.wustl.common.querysuite.queryobject.IDateOffsetLiteral;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IExpressionAttribute;
import edu.wustl.common.querysuite.queryobject.INumericLiteral;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IOutputTerm;
import edu.wustl.common.querysuite.queryobject.IParameter;
import edu.wustl.common.querysuite.queryobject.IParameterizable;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IRule;
import edu.wustl.common.querysuite.queryobject.ITerm;
import edu.wustl.common.querysuite.queryobject.LogicalOperator;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.querysuite.queryobject.TimeInterval;
import edu.wustl.common.querysuite.queryobject.impl.Condition;
import edu.wustl.common.querysuite.queryobject.impl.Connector;
import edu.wustl.common.querysuite.queryobject.impl.Constraints;
import edu.wustl.common.querysuite.queryobject.impl.CustomFormula;
import edu.wustl.common.querysuite.queryobject.impl.DateLiteral;
import edu.wustl.common.querysuite.queryobject.impl.DateOffsetLiteral;
import edu.wustl.common.querysuite.queryobject.impl.Expression;
import edu.wustl.common.querysuite.queryobject.impl.ExpressionAttribute;
import edu.wustl.common.querysuite.queryobject.impl.NumericLiteral;
import edu.wustl.common.querysuite.queryobject.impl.OutputAttribute;
import edu.wustl.common.querysuite.queryobject.impl.OutputTerm;
import edu.wustl.common.querysuite.queryobject.impl.Parameter;
import edu.wustl.common.querysuite.queryobject.impl.QueryEntity;
import edu.wustl.common.querysuite.queryobject.impl.Term;
import edu.wustl.common.util.global.CommonServiceLocator;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.query.enums.QuerySharingStatus;
import edu.wustl.query.util.global.QueryBuilder;

/**Class to create query object from XML.
 * @author vijay_pande
 *
 */
public class CreateQuery
{

	/** logger object for the class. */
	private static final Logger LOGGER = LoggerConfig.getConfiguredLogger(CreateQuery.class);
	/** Document object to parse Query XML. */
	private transient final Document doc;
	/** expression versus subexpression-expressionId-list map. */
	private transient final Map<Integer, List<Integer>> exprSubExpressionId = new HashMap<Integer, List<Integer>>();
	/** old versus new expression Id map. */
	private transient final Map<Integer, Integer> oldNewExpressionId = new HashMap<Integer, Integer>();
	/** old id versus condition object map. */
	private transient final Map<String, ICondition> conditionIdMap = new HashMap<String, ICondition>();
	/** old id versus CustomFormula object map. */
	private transient final Map<String, ICustomFormula> customFormulaIdMap = new HashMap<String, ICustomFormula>();
	/** old id versus CustomFormula object map. */
	private transient final Map<String, ITerm> termIdMap = new HashMap<String, ITerm>();

	/**Default constructor.
	 * @throws QueryParserException Query Parser Exception
	 */
	public CreateQuery() throws QueryParserException
	{
		try
		{
			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
			DocumentBuilder parser = fact.newDocumentBuilder();
			File queryFile = new File("D:/query/TQ.xml");
			doc = parser.parse(queryFile);
		}
		catch (ParserConfigurationException e)
		{
			throw new QueryParserException(
					"Expetion while creating CreateQuery object, unable to parse XML file", e);
		}
		catch (SAXException e)
		{
			throw new QueryParserException(
					"Expetion while creating CreateQuery object, unable to parse XML file", e);
		}
		catch (IOException e)
		{
			throw new QueryParserException(
					"Expetion while creating CreateQuery object, unable to parse XML file", e);
		}

	}

	/**Parameterized constructor.
	 * @param filePath String file path to XML query
	 * @throws QueryParserException Query Parser Exception
	 */
	public CreateQuery(String filePath) throws QueryParserException
	{
		try
		{
			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
			DocumentBuilder parser = fact.newDocumentBuilder();
			File queryFile = new File(filePath);
			doc = parser.parse(queryFile);
		}
		catch (ParserConfigurationException e)
		{
			throw new QueryParserException(
					"Expetion while creating CreateQuery object, unable to parse XML file:"
							+ e.getMessage(), e);
		}
		catch (SAXException e)
		{
			throw new QueryParserException(
					"Expetion while creating CreateQuery object, unable to parse XML file:"
							+ e.getMessage(), e);
		}
		catch (IOException e)
		{
			throw new QueryParserException(
					"Expetion while creating CreateQuery object, unable to parse XML file:"
							+ e.getMessage(), e);
		}
	}

	/**main method of class.
	 * @param args arguments
	 * @throws Exception Exception
	 */
	public static void main(String[] args) throws Exception
	{
		CreateQuery test = new CreateQuery();
		IParameterizedQuery query = test.createQuery();
		ParseObject parseObject = new ParseObject();
		parseObject.format(parseObject.processObject(query));
	}

	/**Entry point to class to create query from XML.
	 * @return query object of type IParameterizedQuery
	 * @throws QueryParserException Query Parser Exception
	 */
	public IParameterizedQuery createQuery() throws QueryParserException
	{
		Node root = doc.getDocumentElement();
		IParameterizedQuery query = QueryObjectFactory.createParameterizedQuery();

		IConstraints constraints = getConstraints(CreateQueryUtil.getElement(root,
				Constraints.class));
		query.setConstraints(constraints);
		List<IParameter<?>> parameters = getParameters(CreateQueryUtil.getElement(root,
				QueryParserConstants.NODE_PARAMETERS));
		query.getParameters().addAll(parameters);

		List<IOutputAttribute> outputAttributeList = getOutputAttributeList(CreateQueryUtil
				.getElement(root, QueryParserConstants.NODE_OUTPUT_ATTR_LIST), query
				.getConstraints());
		query.setOutputAttributeList(outputAttributeList);

		List<IOutputTerm> outputTerms = getOutputTerms(CreateQueryUtil.getElement(root,
				QueryParserConstants.NODE_OUTPUT_TERMS));
		query.getOutputTerms().addAll(outputTerms);

		setQueryDetails(root, query);
		//		System.out.println(query.getConstraints().getRootExpression());
		return query;
	}

	/**Method to set query details to query object.
	 * @param root root node from XML
	 * @param query Query object
	 */
	private void setQueryDetails(Node root, IParameterizedQuery query)
	{
		String name = CreateQueryUtil.getValueForTag(root, QueryParserConstants.ATTR_NAME);
		String createdByString = CreateQueryUtil.getValueForTag(root,
				QueryParserConstants.ATTR_CREATED_BY);
		if (createdByString != null)
		{
			query.setCreatedBy(Long.valueOf(createdByString));

		}
		String description = CreateQueryUtil.getValueForTag(root,
				QueryParserConstants.ATTR_DESCRIPTION);
		String type = CreateQueryUtil.getValueForTag(root, QueryParserConstants.ATTR_TYPE);

		String createDateString = CreateQueryUtil.getValueForTag(root,
				QueryParserConstants.ATTR_CREATED_DATE);
		DateFormat formatter = new SimpleDateFormat(QueryParserConstants.DATE_TIME_PATTERN,
				CommonServiceLocator.getInstance().getDefaultLocale());
		Date date = null;
		try
		{
			date = formatter.parse(createDateString);
		}
		catch (ParseException e)
		{
			LOGGER.error(e.getMessage(), e);
		}

		query.setName(name);
		query.setCreatedDate(date);
		query.setDescription(description);
		query.setType(type);
		query.setUpdationDate(new Date());
		query.setUpdatedBy(query.getCreatedBy());
	}

	/**Method to get values for condition.
	 * @param valuesNode values Node from XML
	 * @return values of type List
	 */
	private List<String> getValuesForConditon(Node valuesNode)
	{
		List<String> values = new ArrayList<String>();
		String value;
		NodeList childNodes = valuesNode.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++)
		{
			if (childNodes.item(i).getNodeName().equals(QueryParserConstants.NODE_STRING))
			{
				if (childNodes.item(i).getChildNodes().item(0) == null)
				{
					value = "";
				}
				else
				{
					value = childNodes.item(i).getChildNodes().item(0).getNodeValue();
				}
				values.add(value);
			}
		}
		return values;
	}

	/**Method to create constraints from XML.
	 * @param constraintsNode input constraints node
	 * @return constraints object of type IConstraints
	 * @throws QueryParserException Query Parser Exception
	 */
	private IConstraints getConstraints(Node constraintsNode) throws QueryParserException
	{
		IConstraints constraints = QueryObjectFactory.createConstraints();
		NodeList childNodes = constraintsNode.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++)
		{
			if (childNodes.item(i).getNodeName().equals(
					CreateQueryUtil.getClassName(Expression.class.getName())))
			{
				addExpression(constraints, childNodes.item(i));
			}
		}
		processSubExpression(constraints);
		processCustomFormulas(constraints, constraintsNode);
		return constraints;
	}

	/**Method to process subexpressions.
	 * First expressions are created and a map is maintained for subexpression list.
	 * When all the subexpressions are created their subexpressions are created with the help of map.
	 * @param constraints query constraints
	 * @throws QueryParserException Query Parser Exception
	 */
	private void processSubExpression(IConstraints constraints) throws QueryParserException
	{
		try
		{
			for (Map.Entry<Integer, Integer> entry : oldNewExpressionId.entrySet())
			{
				IExpression expression = constraints.getExpression(entry.getValue());
				for (Integer subExprId : exprSubExpressionId.get(entry.getKey()))
				{
					IExpression subExpression = constraints.getExpression(oldNewExpressionId
							.get(subExprId));
					//	expression.addOperand(subExpression);
					QueryBuilder.connectExpressions(expression, subExpression, LogicalOperator.And,
							constraints.getJoinGraph());
				}
			}
		}
		catch (Exception e)
		{
			throw new QueryParserException("exception in processSubExpression:" + e.getMessage(), e);
		}
	}

	/**Method to add expression to constraint.
	 * @param constraints object of type IConstraints
	 * @param expressionNode expression Node from XML
	 * @throws QueryParserException Query Parser Exception
	 */
	private void addExpression(IConstraints constraints, Node expressionNode)
			throws QueryParserException
	{
		String errMsg = "exception in addExpression:";
		try
		{
			NodeList childNodes = expressionNode.getChildNodes();
			Node queryEntityNode = CreateQueryUtil.getElement(expressionNode, QueryEntity.class);
			QueryableObjectBean queryableObjectBean = CreateQueryUtil
					.getQueryEntity(queryEntityNode);
			int oldExprId = Integer.parseInt(CreateQueryUtil.getValueForTag(expressionNode,
					QueryParserConstants.ATTR_EXPR_ID));

			IExpression expression = QueryBuilder.createExpression(constraints, null,
					queryableObjectBean.getName(), queryableObjectBean.isCategory());
			oldNewExpressionId.put(oldExprId, expression.getExpressionId());
			processExpression(childNodes, oldExprId, expression);
		}
		catch (NumberFormatException e)
		{
			throw new QueryParserException(errMsg + e.getMessage(), e);
		}
		catch (DynamicExtensionsSystemException e)
		{
			throw new QueryParserException(errMsg + e.getMessage(), e);
		}
		catch (DynamicExtensionsApplicationException e)
		{
			throw new QueryParserException(errMsg + e.getMessage(), e);
		}
		catch (CyclicException e)
		{
			throw new QueryParserException(errMsg + e.getMessage(), e);
		}
	}

	/**Method to process expression.
	 * @param childNodes childNodes list of expression node.
	 * @param oldExprId old subexpression id of current expression
	 * @param expression current expression
	 */
	private void processExpression(NodeList childNodes, int oldExprId, IExpression expression)
	{
		List<Integer> subExprIdList = new ArrayList<Integer>();
		for (int i = 0; i < childNodes.getLength(); i++)
		{
			if (childNodes.item(i).getNodeName().equals(
					CreateQueryUtil.getClassName(Rule.class.getName())))
			{
				addConditions(childNodes.item(i), expression);
			}
			if (childNodes.item(i).getNodeName().equals(QueryParserConstants.CLASS_SUBEXPRESSION))
			{
				String subExprId = CreateQueryUtil.getValueForTag(childNodes.item(i),
						QueryParserConstants.ATTR_EXPR_ID);
				if (subExprId != null)
				{
					subExprIdList.add(Integer.parseInt(subExprId));
				}
			}
		}
		exprSubExpressionId.put(oldExprId, subExprIdList);
	}

	/**Method to process custom formulas of query.
	 * @param constraints object of type IConstraints
	 * @param constraintsNode  constraints Node from XML
	 * @throws QueryParserException Query Parser Exception
	 */
	private void processCustomFormulas(IConstraints constraints, Node constraintsNode)
			throws QueryParserException
	{
		NodeList childNodes = constraintsNode.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++)
		{
			if (childNodes.item(i).getNodeName().equals(
					CreateQueryUtil.getClassName(Expression.class.getName())))
			{
				NodeList exprNodes = childNodes.item(i).getChildNodes();
				for (int j = 0; j < exprNodes.getLength(); j++)
				{
					if (exprNodes.item(j).getNodeName().equals(
							CreateQueryUtil.getClassName(CustomFormula.class.getName())))
					{
						String exprId = CreateQueryUtil.getValueForTag(childNodes.item(i),
								QueryParserConstants.ATTR_EXPR_ID);
						IExpression expression = constraints.getExpression(oldNewExpressionId
								.get(Integer.valueOf(exprId)));
						addCustomFormula(exprNodes.item(j), expression, constraints);
					}
				}
			}
		}
	}

	/**Method to add custom formula to query.
	 * @param customFormulaNode customFormula Node from XML
	 * @param expression object of type IExpression to which custom formula belongs
	 * @param constraints object of type IConstraints
	 * @throws QueryParserException Query Parser Exception
	 */
	private void addCustomFormula(Node customFormulaNode, IExpression expression,
			IConstraints constraints) throws QueryParserException
	{
		Node lhsNode = CreateQueryUtil.getElement(customFormulaNode, QueryParserConstants.NODE_LHS);
		Node rhsNode = CreateQueryUtil.getElement(customFormulaNode, QueryParserConstants.NODE_RHS);
		String customFormulaId = CreateQueryUtil.getValueForTag(customFormulaNode,
				QueryParserConstants.ATTR_IDENTIFIER);
		Node relationalOpNode = CreateQueryUtil.getElement(customFormulaNode,
				RelationalOperator.class);
		String relOperator = CreateQueryUtil.getValueForTag(relationalOpNode,
				QueryParserConstants.NODE_STRING_REPRESENTATION);
		RelationalOperator relationalOperator = RelationalOperator
				.getOperatorForStringRepresentation(relOperator);

		ITerm lhs = getLHS(lhsNode, constraints);
		ICustomFormula customFormula = QueryObjectFactory.createCustomFormula();
		customFormula.setLhs(lhs);

		NodeList childNodes = rhsNode.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++)
		{
			if (childNodes.item(i).getNodeName().equals(
					CreateQueryUtil.getClassName(Term.class.getName())))
			{
				ITerm rhsOperand = getRHSOperand(childNodes.item(i));
				customFormula.addRhs(rhsOperand);
			}
		}
		customFormula.setOperator(relationalOperator);
		expression.addOperand(customFormula);
		customFormulaIdMap.put(customFormulaId, customFormula);
	}

	/**Method to create RHS operand of TQ.
	 * @param rhsTermNode rhsTerm Node from XML
	 * @return rhs lhs term
	 * @throws QueryParserException Query Parser Exception
	 */
	private ITerm getRHSOperand(Node rhsTermNode) throws QueryParserException
	{
		ITerm rhs = QueryObjectFactory.createTerm();
		NodeList childNodes = rhsTermNode.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++)
		{
			if (childNodes.item(i).getNodeName().equals(
					CreateQueryUtil.getClassName(DateOffsetLiteral.class.getName())))
			{
				//String intervalType = getValueForTag(childNodes.item(i), "termType");
				String offsetValue = CreateQueryUtil.getValueForTag(childNodes.item(i),
						QueryParserConstants.NODE_OFFSET);
				String timeInterval = CreateQueryUtil.getValueForTag(childNodes.item(i),
						CreateQueryUtil.getClassName(TimeInterval.class.getName()));
				TimeInterval<?> timeIntervalEnum = TimeInterval.valueOf(timeInterval);

				IDateOffsetLiteral dateOffsetLiteral = QueryObjectFactory.createDateOffsetLiteral(
						offsetValue, timeIntervalEnum);
				rhs.addOperand(dateOffsetLiteral);
				break;
			}
			if (childNodes.item(i).getNodeName().equals(
					CreateQueryUtil.getClassName(DateLiteral.class.getName())))
			{
				IDateLiteral dateLiteral = createDateLiteral(childNodes.item(i));
				rhs.addOperand(dateLiteral);
				break;
			}
			if (childNodes.item(i).getNodeName().equals(
					CreateQueryUtil.getClassName(NumericLiteral.class.getName())))
			{
				String number = CreateQueryUtil.getValueForTag(childNodes.item(i),
						QueryParserConstants.NODE_NUMBER);
				INumericLiteral numericLiteral = QueryObjectFactory.createNumericLiteral(number);
				rhs.addOperand(numericLiteral);
				break;
			}
		}
		return rhs;
	}

	/**Method to create date literal object.
	 * @param dateLiteralNode dateLiteral Node from XML
	 * @return dateLiteral of type IDateLiteral
	 * @throws QueryParserException Query Parser Exception
	 */
	private IDateLiteral createDateLiteral(Node dateLiteralNode) throws QueryParserException
	{
		String dateValue = CreateQueryUtil.getValueForTag(dateLiteralNode,
				QueryParserConstants.NODE_DATE);
		java.sql.Date date = null;
		try
		{
			date = new java.sql.Date(edu.wustl.common.util.Utility.parseDate(dateValue,
					edu.wustl.common.util.Utility.datePattern(dateValue)).getTime());
		}
		catch (ParseException e)
		{
			throw new QueryParserException("exception in createDateLiteral:" + e.getMessage(), e);
		}
		return QueryObjectFactory.createDateLiteral(date);
	}

	/**Method to get LHS operand of TQ.
	 * @param lhsNode lhs Node of XML
	 * @param constraints object of type IConstraints
	 * @return lhs lhs term
	 * @throws QueryParserException Query Parser Exception
	 */
	private ITerm getLHS(Node lhsNode, IConstraints constraints) throws QueryParserException
	{
		NodeList childNodes = lhsNode.getChildNodes();
		ITerm lhs = null;
		for (int i = 0; i < childNodes.getLength(); i++)
		{
			if (childNodes.item(i).getNodeName().equals(
					CreateQueryUtil.getClassName(Term.class.getName())))
			{
				String termId = CreateQueryUtil.getValueForTag(childNodes.item(i),
						QueryParserConstants.ATTR_IDENTIFIER);
				lhs = createLHS(childNodes.item(i), constraints);
				termIdMap.put(termId, lhs);
			}
		}
		return lhs;
	}

	/**Method to create LHS from XML.
	 * @param termNode lhs term node from XML
	 * @param constraints object of type IConstraints
	 * @return lhs lhs term
	 * @throws QueryParserException Query Parser Exception
	 */
	private ITerm createLHS(Node termNode, IConstraints constraints) throws QueryParserException
	{

		NodeList childNodes = termNode.getChildNodes();
		IArithmeticOperand operand1 = null;
		IArithmeticOperand operand2 = null;
		IConnector connector = null;
		for (int i = 0; i < childNodes.getLength(); i++)
		{
			if (childNodes.item(i).getNodeName().equals(
					CreateQueryUtil.getClassName(ExpressionAttribute.class.getName())))
			{

				IExpressionAttribute expressionAttribute = createExpressionAttribute(constraints,
						childNodes.item(i));
				if (operand1 == null)
				{
					operand1 = expressionAttribute;
				}
				else
				{
					operand2 = expressionAttribute;
				}

			}
			else if (childNodes.item(i).getNodeName().equals(
					CreateQueryUtil.getClassName(DateLiteral.class.getName())))
			{
				IDateLiteral dateLiteral = createDateLiteral(childNodes.item(i));
				if (operand1 == null)
				{
					operand1 = dateLiteral;
				}
				else
				{
					operand2 = dateLiteral;
				}

			}
			else if (childNodes.item(i).getNodeName().equals(
					CreateQueryUtil.getClassName(Connector.class.getName())))
			{
				connector = createConnector(childNodes.item(i));
			}
		}
		return setOperandsToLHS(operand1, operand2, connector);

	}

	/**Method to create connector object from XML.
	 * @param connectorNode connector Node from XML
	 * @return connector object of type IConnector
	 */
	private IConnector createConnector(Node connectorNode)
	{
		IConnector connector;
		String nestingNumber = CreateQueryUtil.getValueForTag(connectorNode,
				QueryParserConstants.NODE_NESTING_NUMBER);
		String arithmeticOperator = CreateQueryUtil.getValueForTag(connectorNode, CreateQueryUtil
				.getClassName(ArithmeticOperator.class.getName()));
		ArithmeticOperator operator = ArithmeticOperator.valueOf(arithmeticOperator);
		connector = QueryObjectFactory.createArithmeticConnector(operator, Integer
				.valueOf(nestingNumber));
		return connector;
	}

	/**Method to create expression attribute object.
	 * @param constraints object of type IConstraints
	 * @param expressionAttributeNode expressionAttribute Node from XML
	 * @return expressionAttribute of type IExpressionAttribute
	 */
	private IExpressionAttribute createExpressionAttribute(IConstraints constraints,
			Node expressionAttributeNode)
	{
		String exprId = CreateQueryUtil.getValueForTag(expressionAttributeNode,
				QueryParserConstants.ATTR_EXPR_ID);
		Node queryableAttrNode = CreateQueryUtil.getElement(expressionAttributeNode,
				QueryableEntityAttribute.class);
		String attributeName = CreateQueryUtil.getValueForTag(queryableAttrNode,
				QueryParserConstants.ATTR_NAME);

		IExpression expression = constraints.getExpression(oldNewExpressionId.get(Integer
				.valueOf(exprId)));

		QueryableAttributeInterface attribute = QueryBuilder.findAttribute(expression
				.getQueryEntity().getDynamicExtensionsEntity(), attributeName);
		IExpressionAttribute expressionAttribute = QueryObjectFactory.createExpressionAttribute(
				expression, attribute);
		return expressionAttribute;
	}

	/** Method to set operands and connectors to LHS.
	 * @param operand1 first operand
	 * @param operand2 second operand
	 * @param connector connector object
	 * @return lhs of type ITerm
	 */
	private ITerm setOperandsToLHS(IArithmeticOperand operand1, IArithmeticOperand operand2,
			IConnector connector)
	{
		ITerm lhs = QueryObjectFactory.createTerm();
		if (operand1 != null)
		{
			lhs.addOperand(operand1);
			if (operand2 != null && connector != null)
			{
				//Assumed that if connector is present means its two node TQ.
				lhs.addOperand(connector, operand2);
			}
		}
		return lhs;
	}

	/**Method to add conditions to Expression.
	 * @param ruleNode rule Node from XML
	 * @param expression object of type IExpression to which conditions belongs
	 */
	private void addConditions(Node ruleNode, IExpression expression)
	{
		NodeList childNodes = ruleNode.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++)
		{
			if (childNodes.item(i).getNodeName().equals(
					CreateQueryUtil.getClassName(Condition.class.getName())))
			{
				addCondition(childNodes.item(i), expression);
			}
		}
	}

	/**Method to create and add a condition to given expression.
	 * @param conditionNode condition node from XML
	 * @param expression expression object of type IExpression to which condition belongs
	 */
	private void addCondition(Node conditionNode, IExpression expression)
	{

		String relOperator = CreateQueryUtil.getValueForTag(conditionNode,
				QueryParserConstants.NODE_RELATIONAL_OPERATOR);
		QueryableObjectBean queryableObjectBean = CreateQueryUtil.getQueryAttribute(conditionNode);
		String attributeName = queryableObjectBean.getName();
		//		Long id = Long.parseLong(getValueForTag(queryableAttribute, "id"));
		//		System.out.println(attributeName + ":::" + id);
		QueryableAttributeInterface attribute = QueryBuilder.findAttribute(expression
				.getQueryEntity().getDynamicExtensionsEntity(), attributeName);
		RelationalOperator relationalOperator = RelationalOperator
				.getOperatorForStringRepresentation(relOperator);
		List<String> values = getValuesForConditon(CreateQueryUtil.getElement(conditionNode,
				QueryParserConstants.NODE_VALUES));
		ICondition condition = QueryObjectFactory.createCondition(attribute, relationalOperator,
				values);
		((IRule) expression.getOperand(0)).addCondition(condition);
		String conditionId = CreateQueryUtil.getValueForTag(conditionNode,
				QueryParserConstants.ATTR_IDENTIFIER);
		conditionIdMap.put(conditionId, condition);

	}

	/**Method to create parameters list.
	 * @param parametersNode parameters Node from XML
	 * @return parameters of type List
	 */
	private List<IParameter<?>> getParameters(Node parametersNode)
	{
		List<IParameter<?>> parameters = new ArrayList<IParameter<?>>();
		NodeList childNodes = parametersNode.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++)
		{
			if (childNodes.item(i).getNodeName().equals(
					CreateQueryUtil.getClassName(Parameter.class.getName())))
			{
				IParameter<?> parameter = getParameter(childNodes.item(i));
				if (parameter != null)
				{
					parameters.add(parameter);
				}
			}
		}

		return parameters;
	}

	/**Method to create parameter from XML.
	 * @param parameterNode parameter Node from XML
	 * @return parameter of type IParameter
	 */
	private IParameter<?> getParameter(Node parameterNode)
	{
		Node conditionNode = CreateQueryUtil.getElement(parameterNode, Condition.class);
		Node customFormulaNode = CreateQueryUtil.getElement(parameterNode, CustomFormula.class);
		IParameterizable parameterizedObject = null;
		IParameter<?> parameter = null;
		String parameterName = CreateQueryUtil.getValueForTag(parameterNode,
				QueryParserConstants.ATTR_NAME);
		if (conditionNode == null)
		{
			if (customFormulaNode != null)
			{
				String customFormulaId = CreateQueryUtil.getValueForTag(customFormulaNode,
						QueryParserConstants.ATTR_IDENTIFIER);
				parameterizedObject = customFormulaIdMap.get(customFormulaId);
			}
		}
		else
		{
			String conditionId = CreateQueryUtil.getValueForTag(conditionNode,
					QueryParserConstants.ATTR_IDENTIFIER);
			parameterizedObject = conditionIdMap.get(conditionId);
		}
		if (parameterizedObject != null)
		{
			parameter = QueryObjectFactory.createParameter(parameterizedObject, parameterName);
		}
		return parameter;
	}

	/**Method to create output attributes list.
	 * @param outputAttrNode outputAttributeList Node from XML
	 * @param constraints object of type IConstraints
	 * @return outputAttributeList of type List
	 */
	private List<IOutputAttribute> getOutputAttributeList(Node outputAttrNode,
			IConstraints constraints)
	{
		List<IOutputAttribute> outputAttributeList = new ArrayList<IOutputAttribute>();
		NodeList childNodes = outputAttrNode.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++)
		{
			if (childNodes.item(i).getNodeName().equals(
					CreateQueryUtil.getClassName(OutputAttribute.class.getName())))
			{
				IOutputAttribute outputAttribute = getOputputAttribute(childNodes.item(i),
						constraints);
				if (outputAttribute != null)
				{
					outputAttributeList.add(outputAttribute);
				}
			}
		}

		return outputAttributeList;
	}

	/**Method to create output attribute.
	 * @param outputAttrNode outputAttribute node from XML
	 * @param constraints object of type IConstraints
	 * @return outputAttribute of type IOutputAttribute
	 */
	private IOutputAttribute getOputputAttribute(Node outputAttrNode, IConstraints constraints)
	{
		Node expressionNode = CreateQueryUtil.getElement(outputAttrNode, Expression.class);
		String oldId = CreateQueryUtil.getValueForTag(expressionNode,
				QueryParserConstants.ATTR_EXPR_ID);
		IExpression expression = constraints.getExpression(oldNewExpressionId.get(Integer
				.parseInt(oldId)));

		QueryableObjectBean queryableObjectBean = CreateQueryUtil.getQueryAttribute(outputAttrNode);
		String attributeName = queryableObjectBean.getName();

		QueryableAttributeInterface attribute = QueryBuilder.findAttribute(expression
				.getQueryEntity().getDynamicExtensionsEntity(), attributeName);

		IOutputAttribute outputAttribute = QueryObjectFactory.createOutputAttribute(expression,
				attribute);
		return outputAttribute;
	}

	/**Method to create output terms list.
	 * @param outputTermsNode outputTerms Node from XML
	 * @return outputTermsList of type List
	 */
	private List<IOutputTerm> getOutputTerms(Node outputTermsNode)
	{
		List<IOutputTerm> outputTermsList = new ArrayList<IOutputTerm>();
		NodeList childNodes = outputTermsNode.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++)
		{
			if (childNodes.item(i).getNodeName().equals(
					CreateQueryUtil.getClassName(OutputTerm.class.getName())))
			{
				String name = CreateQueryUtil.getValueForTag(childNodes.item(i),
						QueryParserConstants.ATTR_NAME);
				NodeList outputTermChild = childNodes.item(i).getChildNodes();
				for (int j = 0; j < outputTermChild.getLength(); j++)
				{
					if (outputTermChild.item(j).getNodeName().equals(
							CreateQueryUtil.getClassName(Term.class.getName())))
					{
						String termId = CreateQueryUtil.getValueForTag(outputTermChild.item(j),
								QueryParserConstants.ATTR_IDENTIFIER);
						ITerm term = termIdMap.get(termId);
						IOutputTerm outputTerm = QueryObjectFactory.createOutputTerm(term, name);
						outputTermsList.add(outputTerm);
						break;
					}
				}
			}
		}
		return outputTermsList;
	}

	/**Method to get the sharing status of the current query.
	 * @return String value indicating sharing status of the query
	 */
	public String getSharingStatus()
	{
		return CreateQueryUtil.getValueForTag(doc.getDocumentElement(), CreateQueryUtil
				.getClassName(QuerySharingStatus.class.getName()));
	}

}
