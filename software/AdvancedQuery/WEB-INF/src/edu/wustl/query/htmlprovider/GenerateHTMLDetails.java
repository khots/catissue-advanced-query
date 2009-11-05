
package edu.wustl.query.htmlprovider;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;

/**
 * @author vijay_pande
 * Class to hold details related to Generate HTML logic
 */
public class GenerateHTMLDetails
{
	/**
	 * String to be searched.
	 */
	private String searchString;

	/**
	 *	String searched in attributes, if true.
	 */
	private boolean attributeChecked;

	/**
	 * String searched in permissible values, if true.
	 */
	private boolean permissibleValuesChecked;
	/**
	 *
	 */
	private String[] searchStrings;
	
	/**
	 * Method to set search string array.
	 * @param searchStrings search string array
	 */
	@Deprecated
	public void setSearchStrings(String[] searchStrings)
	{
		System.arraycopy(searchStrings, 0, this.searchString, 0, searchStrings.length);
//		this.searchStrings = searchStrings;
	}

	/**
	 * 
	 */
	private String queryId ;
	
	
	
	public String getQueryId() 
	{
		return queryId;
	}

	/**
	 * 
	 * @param queryID
	 */
	public void setQueryId(String queryId) 
	{
		this.queryId = queryId;
	}

	/**
	 * Method to get String value of searchString.
	 * @return searchString value of searchString
	 */
	public String getSearchString()
	{
		return searchString;
	}

	/**
	 * Method set value of searchString.
	 * @param searchString the searchString to set
	 */
	public void setSearchString(String searchString)
	{
		this.searchString = searchString;
		prepareSearchString(searchString);
	}

	/**
	 * Method to get value of attributeChecked.
	 * @return attributeChecked
	 */
	public boolean isAttributeChecked()
	{
		return attributeChecked;
	}

	/**
	 * Method to set value of attributeChecked.
	 * @param attributeChecked the attributeChecked to set
	 */
	public void setAttributeChecked(boolean attributeChecked)
	{
		this.attributeChecked = attributeChecked;
	}

	/**
	 * Method to get value of permissibleValuesChecked.
	 * @return permissibleValuesChecked
	 */
	public boolean isPermissibleValuesChecked()
	{
		return permissibleValuesChecked;
	}

	/**
	 * Method to set value of permissibleValuesChecked.
	 * @param permissibleValuesChecked the permissibleValuesChecked to set
	 */
	public void setPermissibleValuesChecked(boolean permissibleValuesChecked)
	{
		this.permissibleValuesChecked = permissibleValuesChecked;
	}

	/**
	 * Method to get String[]  of searchStrings.
	 * @return searchStrings
	 */
	public String[] getSearchStrings()
	{
		return this.searchStrings.clone();
	}

	/**
	 * Prepares a String to be sent to AdvancedSearch logic.
	 * @param searchString String
	 * @return String[] array of strings , taken from user.
	 */
	private void prepareSearchString(String searchString)
	{
		int counter = 0;
		StringTokenizer tokenizer = new StringTokenizer(searchString);
		this.searchStrings = new String[tokenizer.countTokens()];
		while (tokenizer.hasMoreTokens())
		{
			searchStrings[counter++] = tokenizer.nextToken().toLowerCase();
		}
	}

	/**
	 * enumratedAttributeMap for storing the AttributeInterface object in session which has Permissible Values
	 */
	private Map<String,QueryableAttributeInterface> enumratedAttributeMap= new HashMap<String,QueryableAttributeInterface>();
	
	public Map<String, QueryableAttributeInterface> getEnumratedAttributeMap() {
		return enumratedAttributeMap;
	}

	public void setEnumratedAttributeMap(Map<String,QueryableAttributeInterface> enumratedAttributeMap) {
		this.enumratedAttributeMap = enumratedAttributeMap;
	}
	
}
