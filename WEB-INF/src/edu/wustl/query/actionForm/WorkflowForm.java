package edu.wustl.query.actionForm;

import edu.wustl.common.actionForm.AbstractActionForm;
import edu.wustl.common.domain.AbstractDomainObject;
import edu.wustl.query.domain.Workflow;

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
	 * Name of the workflow
	 * */
	private String name;

	public String getName()
	{
		return (this.name);
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	
	
	@Override
	public int getFormId()
	{
		return 0;
	}

	@Override
	protected void reset()
	{
		
	}

	public void setAllValues(AbstractDomainObject abstractDomain)
	{
		/*Workflow workflow = (Workflow) abstractDomain;
		
		this.id = workflow.getId();
		this.name = workflow.getName();*/
	}

}
