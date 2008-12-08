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
	
	/**
	 * Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Name of the workFlow
	 */
	private String name;

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
