
package edu.wustl.query.actionforms;

import java.util.List;

import org.apache.struts.action.ActionForm;

import edu.wustl.common.beans.NameValueBean;

/**
 * FormBean representing the QueryModule's properties.
 * @author Mandar Shidhore
 * @author deepti_shelar
 * @version 1.0
 * @created 06-Nov-2006 10.40.04 AM
 */

public class CategorySearchForm extends ActionForm
{

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * String to store the text field value.
	 */
	private String textField = null;
	/**
	 * String to store the classChecked checkbox's  value.
	 */
	private String classChecked = null;
	/**
	 * String to store the attributeChecked checkbox's value.
	 */
	private String attributeChecked = null;
	/**
	 * String to store the permissibleValuesChecked checkbox's value.
	 */
	private String permissibleValuesChecked = null;
	/**
	 * String to store the the IncludeDescription checkbox's value.
	 */
	private String includeDescriptionChecked = null;
	/**
	 * String to store the radio button selected value.
	 */
	private String selected = null;
	/**
	 * String to store the radio button selected value.
	 */
	private String options = null;
	/**
	 * String to store the entityName value.
	 */
	private String entityName = null;
	/**
	 * String to store the string used To Create Query Object.
	 */
	private String stringToCreateQueryObject = null;
	/**
	 * String to store the errors value.
	 */
	private List errors = null;
	/**
	 * String to store the searchButton value.
	 */
	private String searchButton = null;
	/**
	 * String to store the nextOperation value.
	 */
	private String nextOperation = null;
	/**
	 * String to store the text field value.
	 */
	private String nodeId = null;
	/**
	 * String to store the text field value.
	 */
	private String currentPage = null;
	/**
	 *
	 */
	private String booleanAttribute = null;
	/** String array for selected column names.*/
	private String[] selectedColumnNames;
	/** String array for column names.*/
	private String[] columnNames;
	/** List of selected columns in the form of name value bean.*/
	private List<NameValueBean> selectedColumnNVBList;
	/**
	 * Name-ValueBean list to store Projects available.
	 */
	private List<NameValueBean> projectsNameValueBeanList;
	/**
	 * String to store currentSelectedProject.
	 */
	private String currentSelectedProject = null;
	/**
	 * String to store currentSelectedObject.
	 */
	private String currentSelectedObject = null;
	/**
	* String to store currentSelectedObject.
	*/
	private String currentSelectedNodeInTree = null;
	/**
	 * String array to store selected main entities.
	 */
	private String[] selectedMainEntities = null;
	/**
	 * title for defined queries.
	 */
	private String queryTitle;
	/**
	 * Name for WorkFlow.
	 */
	private String workflowName;

	/**Method to get workflow name.
	 * @return workflowName
	 */
	public String getWorkflowName()
	{
		return workflowName;
	}

	/**Method to set workflow name.
	 * @param workflowName new workflow Name
	 */
	public void setWorkflowName(String workflowName)
	{
		this.workflowName = workflowName;
	}

	/**Method to get project list in the form of Name value bean.
	 * @return the projectsNameValueBeanList
	 */
	public List<NameValueBean> getProjectsNameValueBeanList()
	{
		return projectsNameValueBeanList;
	}

	/**Method to set project list in the form of Name value bean.
	 * @param projectsNameValueBeanList the projectsNameValueBeanList to set
	 */
	public void setProjectsNameValueBeanList(List<NameValueBean> projectsNameValueBeanList)
	{
		this.projectsNameValueBeanList = projectsNameValueBeanList;
	}

	/**Method to get selected project.
	 * @return the currentSelectedProject
	 */
	public String getCurrentSelectedProject()
	{
		return currentSelectedProject;
	}

	/**Method to get selected project.
	 * @param currentSelectedProject the currentSelectedProject to set
	 */
	public void setCurrentSelectedProject(String currentSelectedProject)
	{
		this.currentSelectedProject = currentSelectedProject;
	}

	/**Method to get selected main entities.
	 * @return the selectedMainEntities
	 */
	public String[] getSelectedMainEntities()
	{
		String[] temp = selectedMainEntities;
		return temp;
	}

	/**Method to set selected main entities.
	 * @param selectedMainEntities the selectedMainEntities to set
	 */
	public void setSelectedMainEntities(String[] selectedMainEntities)
	{
		String[] temp = new String[selectedMainEntities.length];
		System.arraycopy(selectedMainEntities, 0, temp, 0, selectedMainEntities.length);

		this.selectedMainEntities = temp;
	}

	/**Method to get value for current page.
	 * @return the currentPage
	 */
	public String getCurrentPage()
	{
		return currentPage;
	}

	/**Method to set value for current page.
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(String currentPage)
	{
		this.currentPage = currentPage;
	}

	/**Method to get next operation.
	 * @return the nextOperation
	 */
	public String getNextOperation()
	{
		return nextOperation;
	}

	/**Method to set next operation.
	 * @param nextOperation the nextOperation to set
	 */
	public void setNextOperation(String nextOperation)
	{
		this.nextOperation = nextOperation;
	}

	/**
	 * @return the searchButton
	 */
	public String getSearchButton()
	{
		return searchButton;
	}

	/**
	 * @param searchButton the searchButton to set
	 */
	public void setSearchButton(String searchButton)
	{
		this.searchButton = searchButton;
	}

	/**
	 * @return the errors
	 */
	public List getErrors()
	{
		return errors;
	}

	/**
	 * @param errors the errors to set
	 */
	public void setErrors(List errors)
	{
		this.errors = errors;
	}

	/**
	 * @return the stringToCreateQueryObject
	 */
	public String getStringToCreateQueryObject()
	{
		return stringToCreateQueryObject;
	}

	/**
	 * @param stringToCreateQueryObject the stringToCreateQueryObject to set
	 */
	public void setStringToCreateQueryObject(String stringToCreateQueryObject)
	{
		this.stringToCreateQueryObject = stringToCreateQueryObject;
	}

	/**
	 * @return the selected
	 */
	public String getSelected()
	{
		return selected;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(String selected)
	{
		this.selected = selected;
	}

	/**
	 * @return the textField
	 */
	public String getTextField()
	{
		return textField;
	}

	/**
	 * @param textField the textField to set
	 */
	public void setTextField(String textField)
	{
		this.textField = textField;
	}

	/**
	 * @return the attributeChecked
	 */
	public String getAttributeChecked()
	{
		return attributeChecked;
	}

	/**
	 * @param attributeChecked the attributeChecked to set
	 */
	public void setAttributeChecked(String attributeChecked)
	{
		this.attributeChecked = attributeChecked;
	}

	/**
	 * @return the classChecked
	 */
	public String getClassChecked()
	{
		return classChecked;
	}

	/**
	 * @param classChecked the classChecked to set
	 */
	public void setClassChecked(String classChecked)
	{
		this.classChecked = classChecked;
	}

	/**
	 * @return the permissibleValuesChecked
	 */
	public String getPermissibleValuesChecked()
	{
		return permissibleValuesChecked;
	}

	/**
	 * @param permissibleValuesChecked the permissibleValuesChecked to set
	 */
	public void setPermissibleValuesChecked(String permissibleValuesChecked)
	{
		this.permissibleValuesChecked = permissibleValuesChecked;
	}

	/**
	 * @return the entityName
	 */
	public String getEntityName()
	{
		return entityName;
	}

	/**
	 * @param entityName the entityName to set
	 */
	public void setEntityName(String entityName)
	{
		this.entityName = entityName;
	}

	/**
	 * @return the nodeId
	 */
	public String getNodeId()
	{
		return nodeId;
	}

	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(String nodeId)
	{
		this.nodeId = nodeId;
	}

	/**
	 * @return the booleanAttribute
	 */
	public String getBooleanAttribute()
	{
		return booleanAttribute;
	}

	/**
	 * @param booleanAttribute the booleanAttribute to set
	 */
	public void setBooleanAttribute(String booleanAttribute)
	{
		this.booleanAttribute = booleanAttribute;
	}

	/**Method to get value for includeDescriptionChecked.
	 * @return includeDescriptionChecked
	 */
	public String getIncludeDescriptionChecked()
	{
		return includeDescriptionChecked;
	}

	/**
	 * Method to set value for includeDescriptionChecked.
	 * @param includeDescriptionChecked new value for includeDescriptionChecked
	 */
	public void setIncludeDescriptionChecked(String includeDescriptionChecked)
	{
		this.includeDescriptionChecked = includeDescriptionChecked;
	}

	/**
	 * @return the columnNames
	 */
	public String[] getColumnNames()
	{
		String[] temp = columnNames;
		return temp;
	}

	/**
	 * @param columnNames the columnNames to set
	 */
	public void setColumnNames(String[] columnNames)
	{
		String[] temp = new String[columnNames.length];
		System.arraycopy(columnNames, 0, temp, 0, columnNames.length);

		this.columnNames = temp;
	}

	/**
	 * @return the selectedColumnNames
	 */
	public String[] getSelectedColumnNames()
	{
		String[] temp = selectedColumnNames;
		return temp;
	}

	/**
	 * @param selectedColumnNames the selectedColumnNames to set
	 */
	public void setSelectedColumnNames(String[] selectedColumnNames)
	{
		String[] temp = new String[selectedColumnNames.length];
		System.arraycopy(selectedColumnNames, 0, temp, 0, selectedColumnNames.length);
		this.selectedColumnNames = temp;
	}

	/**
	 * @return the currentSelectedObject
	 */
	public String getCurrentSelectedObject()
	{
		return currentSelectedObject;
	}

	/**
	 * @param currentSelectedObject the currentSelectedObject to set
	 */
	public void setCurrentSelectedObject(String currentSelectedObject)
	{
		this.currentSelectedObject = currentSelectedObject;
	}

	/**
	 * @return the currentSelectedNodeInTree
	 */
	public String getCurrentSelectedNodeInTree()
	{
		return currentSelectedNodeInTree;
	}

	/**
	 * @param currentSelectedNodeInTree the currentSelectedNodeInTree to set
	 */
	public void setCurrentSelectedNodeInTree(String currentSelectedNodeInTree)
	{
		this.currentSelectedNodeInTree = currentSelectedNodeInTree;
	}

	/**
	 * @return the selectedColumnNVBList
	 */
	public List<NameValueBean> getSelectedColumnNameValueBeanList()
	{
		return selectedColumnNVBList;
	}

	/**
	 * @param selectedColumnNVBList the selected Column Name Value Bean List to set
	 */
	public void setSelectedColumnNameValueBeanList(List<NameValueBean> selectedColumnNVBList)
	{
		this.selectedColumnNVBList = selectedColumnNVBList;
	}

	/**
	 * @return the options
	 */
	public String getOptions()
	{
		return options;
	}

	/**
	 * @param options the options to set
	 */
	public void setOptions(String options)
	{
		this.options = options;
	}

	/**
	 * @return title for defined queries
	 */
	public String getQueryTitle()
	{
		return queryTitle;
	}

	/**Sets title for defined queries.
	 * @param queryTitle query title to set
	 *
	 */

	public void setQueryTitle(String queryTitle)
	{
		this.queryTitle = queryTitle;
	}
}
