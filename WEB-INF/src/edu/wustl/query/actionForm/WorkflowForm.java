package edu.wustl.query.actionForm;

import edu.wustl.common.actionForm.AbstractActionForm;
import edu.wustl.common.domain.AbstractDomainObject;
import edu.wustl.common.util.Utility;
import edu.wustl.query.domain.Workflow;
import edu.wustl.query.util.global.Constants;

/**
 * 
 * @author ravindra_jain
 *	
 */
public class WorkflowForm extends AbstractActionForm
{
	
	
	public String[] getQueryTypeControl()
	{
		return queryTypeControl;
	}



	
	public void setQueryTypeControl(String[] queryTypeControl)
	{
		this.queryTypeControl = queryTypeControl;
	}



	
	public String[] getOperands()
	{
		return operands;
	}



	
	public void setOperands(String[] operands)
	{
		this.operands = operands;
	}



	
	public String[] getOperators()
	{
		return operators;
	}



	
	public void setOperators(String[] operators)
	{
		this.operators = operators;
	}



	
	public String[] getDisplayQueryTitle()
	{
		return displayQueryTitle;
	}



	
	public void setDisplayQueryTitle(String[] displayQueryTitle)
	{
		this.displayQueryTitle = displayQueryTitle;
	}



	
	public String[] getDisplayQueryType()
	{
		return displayQueryType;
	}



	
	public void setDisplayQueryType(String[] displayQueryType)
	{
		this.displayQueryType = displayQueryType;
	}

	/**
	 * Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	protected String[] queryId;
	
	protected String[] queryTitle;
	
	protected String[] queryType;
	
	protected boolean[] chkbox;
	
	/**
	 * Name of the workFlow
	 */
	private String name;
	
	
	public String[] getQueryId()
	{
		return queryId;
	}


	
	public void setQueryId(String[] queryId)
	{
		this.queryId = queryId;
	}


	
	public String[] getQueryTitle()
	{
		return queryTitle;
	}


	
	public void setQueryTitle(String[] queryTitle)
	{
		this.queryTitle = queryTitle;
	}


	
	public String[] getQueryType()
	{
		return queryType;
	}


	
	public void setQueryType(String[] queryType)
	{
		this.queryType = queryType;
	}


	
	public String[] getQueryTitleControl()
	{
		return queryTitleControl;
	}


	
	public void setQueryTitleControl(String[] queryTitleControl)
	{
		this.queryTitleControl = queryTitleControl;
	}


	
	public String[] getQueryIdControl()
	{
		return queryIdControl;
	}


	
	public void setQueryIdControl(String[] queryIdControl)
	{
		this.queryIdControl = queryIdControl;
	}


	
	public String[] getSelectedqueryId()
	{
		return selectedqueryId;
	}


	
	public void setSelectedqueryId(String[] selectedqueryId)
	{
		this.selectedqueryId = selectedqueryId;
	}


	
	public boolean[] getChkbox()
	{
		return chkbox;
	}

	
	public void setChkbox(boolean[] chkbox)
	{
		this.chkbox = chkbox;
	}

	protected String[] queryTitleControl;
	
	protected String[] queryIdControl;
	
	protected String[] queryTypeControl;
	
	protected String[] operands;
	
	protected String[] operators;
	
	protected String[] displayQueryTitle;
	
	protected String[] displayQueryType;
	
	protected String[] selectedqueryId;

	/**
	 * Method to get name of the workflow
	 * @return name of type String
	 */
	public String getName()
	{
		return (this.name);
	}

	/**
	 * Method to set name of the workflow
	 * @param name of type String
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	
	
	/**
	 * Method to get form id
	 * @return name of type String
	 */
	public int getFormId()
	{
		return Constants.WORKFLOW_FORM_ID;
	}

	/**
	 * reset method to set default values
	 */
	protected void reset()
	{
		
	}

	/**
	 * Method to populate formBean from domain object
	 */
	public void setAllValues(AbstractDomainObject abstractDomain)
	{
		Workflow workflow = (Workflow) abstractDomain;
		
		this.id = workflow.getId();
		this.name = Utility.toString(workflow.getName());
		this.name = "Workflow1";
	}

}
