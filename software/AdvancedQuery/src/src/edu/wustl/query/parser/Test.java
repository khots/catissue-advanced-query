
package edu.wustl.query.parser;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;

import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.query.util.global.QueryBuilder;

/**Test class for query parser utility.
 * @author vijay_pande
 *
 */
public class Test
{

	public static void main(String[] av) throws Exception
	{
		new Test();
		/**
				IQuery query = getQuery();
				ParseObject parseObject = new ParseObject();
				//		dw.printDocument(parseObject.processObject((IAbstractQuery) query));
		String xml = parseObject.format(parseObject.processObject(query));
				//		System.out.println(dw.format(parseObject.processObject((IAbstractQuery) query)));
				 
				 */
	}

	private static IQuery getQuery() throws Exception
	{
		IParameterizedQuery query = QueryObjectFactory.createParameterizedQuery();
		IConstraints constraints = QueryObjectFactory.createConstraints();
		query.setConstraints(constraints);
		query.setName("FirstQuery");
		query.setCreatedBy(1L);
		query.setId(1L);

		IExpression personExpr = QueryBuilder.createExpression(constraints, null, "Person", false);
		query.setType("Data");

		IExpression demoExpr = QueryBuilder.createExpression(constraints, personExpr,
				"Demographics", false);
		query.setType("Data");

		QueryBuilder.addParametrizedCondition(query, personExpr, "personUpi",
				RelationalOperator.Equals);
		String[] values = new String[]{};
		QueryBuilder.addCondition(demoExpr, "socialSecurityNumber", RelationalOperator.IsNotNull,
				values);
		QueryBuilder.addCondition(demoExpr, "dateOfBirth", RelationalOperator.IsNotNull, values);

		QueryBuilder.addOutputAttribute(query.getOutputAttributeList(), personExpr, "personUpi");

		return query;
	}

	public String format(Document document)
	{
		try
		{
			//			final Document document = parseXmlFile(unformattedXml);

			OutputFormat format = new OutputFormat(document);
			format.setLineWidth(65);
			format.setIndenting(true);
			format.setIndent(5);
			Writer out = new StringWriter();
			XMLSerializer serializer = new XMLSerializer(out, format);
			serializer.serialize(document);

			return out.toString();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	//	/**			DOMBuilder builder = new DOMBuilder();
	//	org.jdom.Document jdomDocument = builder.build(document);
	//	//	XMLOutputter outputet = new XMLOutputter(Format.getPrettyFormat());
	//	//	BufferedWriter writer = new BufferedWriter(new FileWriter("temp.txt"));
	//	//	outputet.output(jdomDocument, writer);*/
	//	protected void printDocument(Document document)
	//	{
	//		try
	//		{
	//
	//			DOMSource domSource = new DOMSource(document);
	//			StringWriter writer6 = new StringWriter();
	//			StreamResult result = new StreamResult(writer6);
	//			TransformerFactory tf = TransformerFactory.newInstance();
	//			Transformer transformer = tf.newTransformer();
	//			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	//			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	//
	//			transformer.transform(domSource, result);
	//			//			System.out.println(writer6.toString());
	//
	//			System.out.println(format(document));
	//		}
	//		catch (Exception ex)
	//		{
	//			ex.printStackTrace();
	//			return;
	//		}
	//	}

	//	/** Generate the XML document */
	//	protected Document makeDoc()
	//	{
	//		try
	//		{
	//			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
	//			DocumentBuilder parser = fact.newDocumentBuilder();
	//			Document doc = parser.newDocument();
	//			Node root = doc.createElement("Poem");
	//			doc.appendChild(root);
	//
	//			Node stanza = doc.createElement("Stanza");
	//			root.appendChild(stanza);
	//
	//			Node line = doc.createElement("Line");
	//			stanza.appendChild(line);
	//			line.appendChild(doc.createTextNode("Once, upon a midnight dreary"));
	//			line = doc.createElement("Line");
	//			stanza.appendChild(line);
	//			line.appendChild(doc.createTextNode("While I pondered, weak and weary"));
	//
	//			return doc;
	//
	//		}
	//		catch (Exception ex)
	//		{
	//			System.err.println("+============================+");
	//			System.err.println("|        XML Error           |");
	//			System.err.println("+============================+");
	//			System.err.println(ex.getClass());
	//			System.err.println(ex.getMessage());
	//			System.err.println("+============================+");
	//			return null;
	//		}
	//	}
}
// demo xml file
/*
<?xml version="1.0"?>
<people>
<person>
  <name>Ian Darwin</name>
  <email>http://www.darwinsys.com/</email>
  <country>Canada</country>
</person>
<person>
  <name>Another Darwin</name>
  <email type="intranet">afd@node1</email>
  <country>Canada</country>
</person>
</people>


*/
