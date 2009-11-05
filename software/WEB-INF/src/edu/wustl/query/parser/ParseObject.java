
package edu.wustl.query.parser;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import edu.wustl.common.querysuite.queryobject.IArithmeticOperand;
import edu.wustl.common.querysuite.queryobject.IConnector;
import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IExpressionAttribute;
import edu.wustl.common.querysuite.queryobject.IOperand;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.ITerm;
import edu.wustl.common.querysuite.queryobject.TimeInterval;
import edu.wustl.common.querysuite.queryobject.impl.CustomFormula;
import edu.wustl.common.querysuite.queryobject.impl.Expression;
import edu.wustl.common.querysuite.queryobject.impl.ExpressionAttribute;
import edu.wustl.common.util.global.CommonServiceLocator;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.query.bizlogic.QueryBizLogic;
import edu.wustl.query.enums.QuerySharingStatus;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.QueryModuleException;
import edu.wustl.query.util.reflection.Field;
import edu.wustl.query.util.reflection.FieldType;
import edu.wustl.query.util.reflection.ReflectionException;
import edu.wustl.query.util.reflection.ReflectionUtilities;

/**Class to parse Query Domain object to create equivalent XML Document.
 * @author vijay_pande
 *
 */
public class ParseObject
{

	/**
	 * logger object for the class.
	 */
	private static final Logger logger = LoggerConfig.getConfiguredLogger(ParseObject.class);
	/**
	 * Document object which will be generated from the given input object.
	 */
	private transient Document doc;

	/**
	 * Default constructor which also initialize the Document object.
	 * @throws ParserConfigurationException  Parser Configuration Exception
	 */
	public ParseObject() throws ParserConfigurationException
	{
		super();
		try
		{
			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
			DocumentBuilder parser = fact.newDocumentBuilder();
			doc = parser.newDocument();
		}
		catch (ParserConfigurationException e)
		{
			logger.error("Exception while creating Document", e);
			throw e;
		}
	}

	/**The public method which will be called to create XML document for input object.
	 * @param object object for which XML to be created
	 * @return Document object
	 * @throws ReflectionException Reflection Exception
	 */
	public Document processObject(Object object) throws ReflectionException
	{
		doc.appendChild(createXML(object));
		if (object instanceof IParameterizedQuery)
		{
			IParameterizedQuery query = (IParameterizedQuery) object;
			QueryBizLogic queryBizLogic = new QueryBizLogic();
			Node sharingStatusNode = doc.createElement(getClassName(QuerySharingStatus.class
					.getName()));
			try
			{
				QuerySharingStatus sharingStatus = queryBizLogic.getSharingStatus(query, query
						.getCreatedBy());
				sharingStatusNode.appendChild(doc.createTextNode(sharingStatus.toString()));
			}
			catch (QueryModuleException e)
			{
				sharingStatusNode.appendChild(doc
						.createTextNode(QueryParserConstants.UNKNOWN_VALUE));
			}
		}
		return doc;
	}

	/**Method to create XML for given object.
	 * @param object object for which XML is to be created
	 * @return Node root node
	 * @throws ReflectionException Reflection Exception
	 */
	public Node createXML(Object object) throws ReflectionException
	{
		logger.debug("In createXML for object" + object.getClass().getName());
		Node root = doc.createElement(getClassName(object.getClass().getName()));
		if (getClassName(object.getClass().getName()).equals(
				getClassName(CustomFormula.class.getName())))
		{
			root = processCustomFormula(object);
		}
		else if (getClassName(object.getClass().getName()).equals(
				QueryParserConstants.CLASS_TIME_INTERVAL))
		{
			TimeInterval<?> timeInterval = (TimeInterval<?>) object;
			root.appendChild(doc.createTextNode(timeInterval.toString()));
		}
		else if (getClassName(object.getClass().getName()).equals(
				getClassName(ExpressionAttribute.class.getName())))
		{
			Node exprId = doc.createElement(QueryParserConstants.ATTR_EXPR_ID);
			String expressionId = String.valueOf(((IExpressionAttribute) object).getExpression()
					.getExpressionId());
			exprId.appendChild(doc.createTextNode(expressionId));
			root.appendChild(exprId);
			for (Field field : ReflectionUtilities.getFieldList(object))
			{
				root.appendChild(processField(field));
			}
		}
		else
		{
			for (Field field : ReflectionUtilities.getFieldList(object))
			{
				root.appendChild(processField(field));
			}
		}
		return root;
	}

	/**Method to process CustomFormula class.
	 * Since it wasn't possible to process attributes of the class through normal reflection utilities.
	 * @param object CustomFormula object
	 * @return Node
	 * @throws ReflectionException Reflection Exception
	 */
	private Node processCustomFormula(Object object) throws ReflectionException
	{
		Node root = doc.createElement(getClassName(object.getClass().getName()));
		ICustomFormula customFormula = (CustomFormula) object;
		Node rhsColl = doc.createElement(QueryParserConstants.NODE_RHS);
		for (ITerm term : customFormula.getAllRhs())
		{
			Node rhsNode = doc.createElement(getClassName(term.getClass().getName()));
			IArithmeticOperand operand = term.getOperand(0);
			rhsNode.appendChild(createXML(operand));
			rhsColl.appendChild(rhsNode);
		}
		root.appendChild(rhsColl);
		root.appendChild(processLHS(customFormula.getLhs()));
		root.appendChild(createXML(customFormula.getOperator()));
		Node idElem = doc.createElement(QueryParserConstants.ATTR_IDENTIFIER);
		idElem.appendChild(doc.createTextNode(customFormula.getId().toString()));
		root.appendChild(idElem);
		return root;
	}

	/**Method to process LHS of CustomFormula.
	 * @param term LHS term
	 * @return Node
	 * @throws ReflectionException Reflection Exception
	 */
	private Node processLHS(ITerm term) throws ReflectionException
	{
		Node lhsNode = doc.createElement(QueryParserConstants.NODE_LHS);
		Node rootNode = createXML(term);
		for (int i = 0; i < term.numberOfOperands(); i++)
		{
			IOperand operand = term.getOperand(i);
			rootNode.appendChild(createXML(operand));
		}
		if (term.numberOfOperands() == Constants.TWO)
		{
			IConnector<?> connector = term.getConnector(Constants.ZERO, Constants.ONE);
			if (connector != null)
			{
				Node node = createXML(connector);
				Node operator = doc.createElement(getClassName(connector.getOperator().getClass()
						.getName()));
				operator.appendChild(doc.createTextNode(connector.getOperator().toString()));
				node.appendChild(operator);
				rootNode.appendChild(node);
			}
		}
		lhsNode.appendChild(rootNode);
		return lhsNode;
	}

	/**Method to process attribute.
	 * @param field attribute to be processed
	 * @return root node for the generated partial XML
	 * @throws ReflectionException Reflection Exception
	 */
	private Node processField(Field field) throws ReflectionException
	{
		logger.debug("Processing " + field);
		Node node = null;
		switch (field.getFieldType())
		{
			case COLLECTION :
				node = processCollection(field);
				break;
			case PRIMITIVE :
				node = processPrimitive(field);
				break;
			case ITERABLE :
				node = processIterable(field);
				break;
			case DOMAIN_OBJECT :
				node = processDomainObject(field);
				break;
			case ENUM :
				node = processEnum(field);
				break;
			default :
				logger.error("Not processed" + field);
				break;
		}
		return node;
	}

	/**Method to process primitive values.
	 * @param field attribute to process
	 * @return node node for attribute
	 */
	private Node processPrimitive(Field field)
	{
		Node node;
		node = doc.createElement(field.getFieldName());
		if (field.getFieldValue() instanceof Date)
		{
			Date date = (Date) field.getFieldValue();
			DateFormat formatter = new SimpleDateFormat(QueryParserConstants.DATE_TIME_PATTERN,
					CommonServiceLocator.getInstance().getDefaultLocale());
			String stringDate = formatter.format(date);
			node.appendChild(doc.createTextNode(stringDate));
		}
		else
		{
			node.appendChild(doc.createTextNode(field.getFieldValue().toString()));
		}
		return node;
	}

	/**Method to process enum types.
	 * @param field attribute to process
	 * @return elem
	 */
	private Node processEnum(Field field)
	{
		Node elem = doc.createElement(field.getFieldName());
		elem.appendChild(doc.createTextNode(field.getFieldValue().toString()));
		return elem;
	}

	/**Method to process domain object.
	 * @param field attribute under process
	 * @return Node rootNode for generated partial XML
	 * @throws ReflectionException Reflection Exception
	 */
	private Node processDomainObject(Field field) throws ReflectionException
	{
		Node elem = null;
		if (isQueryable(field.getFieldValue().getClass().getName()))
		{
			elem = processQueryable(field.getFieldValue());
		}
		else if (isClassToProcess(field.getFieldValue().getClass().getName()))
		{
			elem = createXML(field.getFieldValue());
		}
		return elem;
	}

	/**Method to process Queryable query object.
	 * @param object object under process
	 * @return Node rootNode for generated partial XML
	 * @throws ReflectionException Reflection Exception
	 */
	private Node processQueryable(Object object) throws ReflectionException
	{
		Node elem = doc.createElement(getClassName(object.getClass().getName()));

		String nameOfObject = ReflectionUtilities.getName(object);
		Long idOfObject = ReflectionUtilities.getId(object);

		Node name = doc.createElement(QueryParserConstants.ATTR_NAME);
		name.appendChild(doc.createTextNode(nameOfObject));
		elem.appendChild(name);
		Node idNode = doc.createElement(QueryParserConstants.ATTR_IDENTIFIER);
		idNode.appendChild(doc.createTextNode(idOfObject.toString()));
		elem.appendChild(idNode);
		return elem;
	}

	/**Method to check whether class name is instance of QueryableEntity or QueryableAttribute or not.
	 * @param name Class name to verify
	 * @return boolean value
	 */
	private boolean isQueryable(String name)
	{
		boolean isQueryableClass = false;
		if (name.contains("Queryable"))//|| name.contains("querysuite"))
		{
			logger.debug("inside isQueryable, skipping class:" + name);
			isQueryableClass = true;
		}
		return isQueryableClass;
	}

	/**Method to process attributes of type Iterable.
	 * @param field attribute to be processed
	 * @return Node rootNode for partial generated XML
	 * @throws ReflectionException Reflection Exception
	 */
	private Node processIterable(Field field) throws ReflectionException
	{
		logger.debug("Type of iterator:" + field.getFieldName() + " "
				+ field.getFieldValue().getClass().getName());
		Node elemColl = createXML(field.getFieldValue());
		//		Element elemIter = doc.createElement(field.getFieldName());
		int exprOperandCounter = 0;
		for (Object tempObject : (Iterable<?>) field.getFieldValue())
		{
			Node elem;
			if (Iterable.class.isInstance(tempObject))
			{
				Field myField = createFieldForTempObject(tempObject);
				elem = processIterable(myField);
			}
			else if (getClassName(field.getFieldValue().getClass().getName()).equals(
					getClassName(Expression.class.getName()))
					&& getClassName(tempObject.getClass().getName()).equals(
							QueryParserConstants.CLASS_SUBEXPRESSION))
			{
				elem = doc.createElement(QueryParserConstants.CLASS_SUBEXPRESSION);
				IExpression expression = (IExpression) field.getFieldValue();
				if (expression.getOperand(exprOperandCounter) instanceof IExpression)
				{
					IExpression subExpression = (IExpression) expression
							.getOperand(exprOperandCounter);
					Node expressionId = doc.createElement(QueryParserConstants.ATTR_EXPR_ID);
					expressionId.appendChild(doc.createTextNode(String.valueOf(subExpression
							.getExpressionId())));
					elem.appendChild(expressionId);
				}
			}
			else
			{
				elem = createXML(tempObject);
			}
			elemColl.appendChild(elem);
			exprOperandCounter++;
		}
		return elemColl;
	}

	/**Method to create field object from raw object.
	 * @param tempObject raw object for which we are creating Field object on the fly
	 * @return myField object of type Field
	 */
	private Field createFieldForTempObject(Object tempObject)
	{
		Field myField = new Field();
		myField.setFieldName(getClassName(tempObject.getClass().getName()));
		myField.setFieldType(FieldType.ITERABLE);
		myField.setFieldValue(tempObject);
		return myField;
	}

	/**Method to process attributes of type Collection.
	 * @param field attribute to be processed
	 * @return Node rootNode for partial generated XML
	 * @throws ReflectionException Reflection Exception
	 */
	private Node processCollection(Field field) throws ReflectionException
	{
		Node elemCollection = doc.createElement(field.getFieldName());
		for (Object tempObject : (Collection<?>) field.getFieldValue())
		{
			Node elem;
			if (isQueryable(tempObject.getClass().getName()))
			{
				elem = processQueryable(tempObject);
			}
			if (ReflectionUtilities.isPrimitive(tempObject.getClass().getName()))
			{
				elem = doc.createElement(getClassName(tempObject.getClass().getName()));
				elem.appendChild(doc.createTextNode(tempObject.toString()));
			}
			else
			{
				elem = createXML(tempObject);

			}
			elemCollection.appendChild(elem);
		}
		return elemCollection;
	}

	/**Method to check whether class should be processed or skipped.
	 * Skipping DynamicExtension related classes.
	 * @param name class name
	 * @return boolean value
	 */
	private boolean isClassToProcess(String name)
	{
		boolean isSkip = true;
		if (name.contains("dynamic"))
		{
			logger.debug("inside isClassToProcess, skipping class:" + name);
			isSkip = false;
		}
		return isSkip;
	}

	/**Method to get only class name from fully qualified name.
	 * @param name fully qualified name
	 * @return name only class name
	 */
	private String getClassName(String name)
	{
		String tempName = name;
		if (name.lastIndexOf('.') != Constants.MINU_ONE)
		{
			tempName = name.substring(name.lastIndexOf('.')
					+ edu.wustl.query.util.global.Constants.ONE);
		}
		if (tempName.indexOf('$') != Constants.MINU_ONE)
		{
			tempName = tempName.substring(tempName.indexOf('$') + Constants.ONE);
		}
		return tempName;
	}

	/**Method to convert the Document object into XML string with Indentation.
	 * @param document Document object which is to be converted into XML string
	 * @return String equivalent of Document object
	 * @throws IOException IO Exception
	 */
	public String format(Document document) throws IOException
	{
		OutputFormat format = new OutputFormat(document);
		format.setLineWidth(Constants.HUNDRED);
		format.setIndenting(true);
		format.setIndent(Constants.FIVE);
		Writer out = new StringWriter();
		XMLSerializer serializer = new XMLSerializer(out, format);
		serializer.serialize(document);

		return out.toString();
	}
}
