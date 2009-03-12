package edu.wustl.query.util.filter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import edu.wustl.common.util.global.Variables;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.util.global.Constants;


/**
 * Class to parse struts-config file of AdvanceQuery to get action names
 * @author vijay_pande
 *
 */
public class StrutsConfigReader
{

	private static Document document = null;
	private static Map<String, String> actionMap;
	private static org.apache.log4j.Logger logger = Logger
	.getLogger(StrutsConfigReader.class);
	
	public static void main(String[] args) throws Exception
	{
		Variables.applicationHome = "D:\\Washu-Eclipse\\eclipse\\workspace\\SVN_AdvancedQuery";
		StrutsConfigReader.init(Variables.applicationHome+File.separator+"WEB-INF"+File.separator+"advancequery-struts-config.xml");

		boolean propertyValue1 = StrutsConfigReader.isQueryAction("PrivacyNotice.do");
		System.out.println(""+propertyValue1);
	}

	/**
	 * @param path
	 * @throws Exception
	 */
	public static void init(String path) throws Exception
	{
		logger.debug("path.........................."+path);
		DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
		try
		{
			DocumentBuilder dbuilder = dbfactory.newDocumentBuilder();// throws
			dbuilder.setEntityResolver(new EntityResolver() {
                public InputSource resolveEntity(String publicId, String systemId)
                {
                	// local dtd file name that has to be used
					return new InputSource(Variables.applicationHome+File.separator+Constants.WEB_INF_FOLDER_NAME+File.separator+Constants.AQ_STRUTS_CONFIG_DTD_FILE_NAME);
                }
            });
			// ParserConfigurationException
			if (path != null)
			{
				document = dbuilder.parse(path);
			}
			populateActionMap();
		}
		catch (SAXException e)
		{
			logger.error(e.getMessage(),e);
			throw e;
		}
		catch (IOException e)
		{
			logger.error(e.getMessage(),e);
			throw e;
		}
		catch (ParserConfigurationException e)
		{
			logger.error("Could not locate a JAXP parser: "+e.getMessage(),e);
			throw e;
		}
	}

	/**
	 * Method to populate map of Struts actions
	 */
	private static void populateActionMap()
	{
		actionMap = new HashMap<String, String>();
		// it gives the rootNode of the xml file
		Element root = document.getDocumentElement();

		NodeList children = root.getElementsByTagName(Constants.STRUTS_NODE_ACTION);
		for (int i = 0; i < children.getLength(); i++)
		{
			Node child = children.item(i);
			if (child instanceof Element)
			{
				String value = ((Element)child).getAttribute(Constants.NODE_ACTION_ATTRIBUTE_PATH);
				actionMap.put(value.substring(1), value);
			}
		}
	}

	/**
	 * @param action
	 * @return
	 */
	public static boolean isQueryAction(String action)
	{
		boolean isQueryAction = false;
		String key = action;
		if(action.indexOf(".do")>-1)
		{
			key = action.substring(0,action.indexOf(".do"));
		}
		if(actionMap.keySet().contains(key))
		{
			isQueryAction = true;
		}
		return isQueryAction;
	}
	
}