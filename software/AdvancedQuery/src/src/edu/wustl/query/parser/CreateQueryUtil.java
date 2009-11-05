
package edu.wustl.query.parser;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.wustl.common.querysuite.querableobject.QueryableCategory;
import edu.wustl.common.querysuite.querableobject.QueryableCategoryAttribute;
import edu.wustl.common.querysuite.querableobject.QueryableEntity;
import edu.wustl.common.querysuite.querableobject.QueryableEntityAttribute;
import edu.wustl.query.util.global.Constants;

/**Helper class for CreateQuery.
 * @author vijay_pande
 *
 */
public class CreateQueryUtil
{

	/**Method to get details of QueryableAttribute Entity.
	 * @param queryEntityNode rootNode under which QueryableEntity is present
	 * @return queryableObjectBean object of type QueryableObjectBean
	 */
	public static QueryableObjectBean getQueryEntity(Node queryEntityNode)
	{
		QueryableObjectBean queryableObjectBean = new QueryableObjectBean();
		Node queryableEntity = getElement(queryEntityNode, getClassName(QueryableEntity.class
				.getName()));
		if (queryableEntity == null)
		{
			Node queryableCategory = getElement(queryEntityNode,
					getClassName(QueryableCategory.class.getName()));
			queryableObjectBean.setName(getValueForTag(queryableCategory,
					QueryParserConstants.ATTR_NAME));
			queryableObjectBean.setCategory(true);
		}
		else
		{
			queryableObjectBean.setName(getValueForTag(queryableEntity,
					QueryParserConstants.ATTR_NAME));
			queryableObjectBean.setCategory(false);
		}
		return queryableObjectBean;
	}

	/**Method to get details of QueryableAttribute.
	 * @param rootNode rootNode under which QueryableAttribute is present
	 * @return queryableObjectBean object of type QueryableObjectBean
	 */
	public static QueryableObjectBean getQueryAttribute(Node rootNode)
	{
		QueryableObjectBean queryableObjectBean = new QueryableObjectBean();
		Node entityAttribute = getElement(rootNode, getClassName(QueryableEntityAttribute.class
				.getName()));
		if (entityAttribute == null)
		{
			Node categoryAttribute = getElement(rootNode,
					getClassName(QueryableCategoryAttribute.class.getName()));
			queryableObjectBean.setName(getValueForTag(categoryAttribute,
					QueryParserConstants.ATTR_NAME));
			queryableObjectBean.setCategory(true);
		}
		else
		{
			queryableObjectBean.setName(getValueForTag(entityAttribute,
					QueryParserConstants.ATTR_NAME));
			queryableObjectBean.setCategory(false);
		}
		return queryableObjectBean;
	}

	/**Method to get only class name from fully qualified name.
	 * @param name fully qualified name
	 * @return name only class name
	 */
	public static String getClassName(String name)
	{
		String tempName = name;
		if (name.lastIndexOf('.') != Constants.MINUS_ONE)
		{
			tempName = name.substring(name.lastIndexOf('.') + Constants.ONE);
		}
		if (tempName.indexOf('$') != Constants.MINUS_ONE)
		{
			tempName = tempName.substring(tempName.indexOf('$') + Constants.ONE);
		}
		return tempName;
	}

	/**Method to get element which is child element of the input element.
	 * @param root input element
	 * @param nodeName name of required
	 * @return node required element
	 */
	public static Node getElement(Node root, String nodeName)
	{
		NodeList childNodes = root.getChildNodes();
		Node node = null;
		for (int i = 0; i < childNodes.getLength(); i++)
		{
			if (childNodes.item(i).getNodeName().equals(nodeName))
			{
				node = childNodes.item(i);
				break;
			}
		}
		return node;
	}

	/**Method to get value of given element.
	 * @param root input element
	 * @param nodeName name of element whose value is required.
	 * @return value as Sting object
	 */
	public static String getValueForTag(Node root, String nodeName)
	{
		String value = null;
		NodeList childNodes = root.getChildNodes();
		Node node;
		for (int i = 0; i < childNodes.getLength(); i++)
		{
			node = childNodes.item(i);
			if (node.getNodeName().equals(nodeName))
			{
				if (node.getChildNodes().item(0) == null)
				{
					value = "";
				}
				else
				{
					value = node.getChildNodes().item(0).getNodeValue();
				}
				break;
			}
		}
		return value;
	}

	/**Method to get element which is child element of the input element based on class.
	 * @param root input element
	 * @param clazz Class which is the child element of root
	 * @return node required element
	 */
	public static Node getElement(Node root, Class<?> clazz)
	{
		return getElement(root, getClassName(clazz.getName()));
	}
}
