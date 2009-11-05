
package edu.wustl.query.parser;

/**Bean class to get name and isCategory for QueryableEntity/QueryableAttribute.
 * @author vijay_pande
 *
 */
public class QueryableObjectBean
{

	private String name;
	private boolean isCategory;

	/**Method to get name.
	 * @return name
	 */
	public String getName()
	{
		return name;
	}

	/**Method to set name.
	 * @param objectName name of object
	 */
	public void setName(String objectName)
	{
		name = objectName;
	}

	/**Method to get is category.
	 * @return isCategory
	 */
	public boolean isCategory()
	{
		return isCategory;
	}

	/**Method to set isCategory.
	 * @param isCategoryObject isCategory for object
	 */
	public void setCategory(boolean isCategoryObject)
	{
		isCategory = isCategoryObject;
	}
}
