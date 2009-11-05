
package edu.wustl.common.query.queryobject.impl.metadata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.wustl.common.beans.NameValueBean;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;

/**
 * 
 * @author deepti_shelar
 *
 */
public class SelectedColumnsMetadata implements Serializable
{

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Selected object by user at the time of defining columns
	 */
	private OutputTreeDataNode currentSelectedObject;
	/**
	 * List of attributes user has selected 
	 */
	private List<QueryOutputTreeAttributeMetadata> selectedAttributeMetaDataList;
	/**
	 * List of name value bean object for selected columns
	 */
	private List<NameValueBean> selectedColumnNVBList;
	/**
	 * Returns true/false Whether view is defined.
	 */
	private boolean isdefinedview;
	/**
	 * List of output attribute objects.
	 */
	private List<IOutputAttribute> selectedOutputAttributeList;

	/**
	 * 
	 * @return
	 */
	public boolean isDefinedView()
	{
		return isdefinedview;
	}

	/**
	 * 
	 * @param isDefinedView
	 */
	public void setDefinedView(boolean isdefinedview)
	{
		this.isdefinedview = isdefinedview;
	}

	public List<QueryableAttributeInterface> getAttributeList()
	{
		List<QueryableAttributeInterface> attributeList = new ArrayList<QueryableAttributeInterface>();
		if (selectedAttributeMetaDataList != null)
		{
			for (QueryOutputTreeAttributeMetadata metadata : selectedAttributeMetaDataList)
			{
				attributeList.add(metadata.getAttribute());
			}
		}
		return attributeList;
	}

	//	public void setAttributeList(List<AttributeInterface> attributeList)
	//	{
	//		this.attributeList = attributeList;
	//	}
	public OutputTreeDataNode getCurrentSelectedObject()
	{
		return currentSelectedObject;
	}

	/**
	 * @param currentSelectedObject the currentSelectedObject to set
	 */
	public void setCurrentSelectedObject(OutputTreeDataNode currentSelectedObject)
	{
		this.currentSelectedObject = currentSelectedObject;
	}

	/**
	 * @return the selectedColumnNameValueBeanList
	 */
	public List<NameValueBean> getSelectedColumnNameValueBeanList()
	{
		return selectedColumnNVBList;
	}

	/**
	 * @param selectedColumnNameValueBeanList the selectedColumnNameValueBeanList to set
	 */
	public void setSelectedColumnNameValueBeanList(
			List<NameValueBean> selectedColumnNVBList)
	{
		this.selectedColumnNVBList = selectedColumnNVBList;
	}

	/**
	 * @return the selectedAttributeMetaDataList
	 */
	public List<QueryOutputTreeAttributeMetadata> getSelectedAttributeMetaDataList()
	{
		return selectedAttributeMetaDataList;
	}

	/**
	 * @param selectedAttributeMetaDataList the selectedAttributeMetaDataList to set
	 */
	public void setSelectedAttributeMetaDataList(
			List<QueryOutputTreeAttributeMetadata> selectedAttributeMetaDataList)
	{
		this.selectedAttributeMetaDataList = selectedAttributeMetaDataList;
	}

	/**
	 * @return the selectedOutputAttributeList
	 */
	public List<IOutputAttribute> getSelectedOutputAttributeList()
	{
		return selectedOutputAttributeList;
	}

	/**
	 * @param selectedOutputAttributeList the selectedOutputAttributeList to set
	 */
	public void setSelectedOutputAttributeList(List<IOutputAttribute> selectedOutputAttributeList)
	{
		this.selectedOutputAttributeList = selectedOutputAttributeList;
	}
}

