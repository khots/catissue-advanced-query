package edu.wustl.query.domain;

import java.io.Serializable;

import edu.wustl.common.actionForm.IValueObject;
import edu.wustl.common.domain.AbstractDomainObject;
import edu.wustl.common.exception.AssignDataException;
import edu.wustl.query.actionForm.WorkflowForm;

/**
 * Workflow is used to club together queries that may be required 
 * for a particular research purpose.
 * Used to batch execute get count queries or combine them using set operations
 * Also, used to execute patient data queries within the workflow.
 * 
 * @author ravindra_jain  creation date: 26th November,2008
 * @version 1.0
 */
public class Workflow // extends AbstractDomainObject implements Serializable
{

	/**
	 * Serial Version Identifier
	 */
	private static final long serialVersionUID = 1L;

	/**
     * System generated unique id.
     */
    protected Long id;
	
    /**
     * Name of the Workflow (unique) 
     */
    protected String name;
	
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
	
	public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
	
	/*@Override
	public void setAllValues(IValueObject abstractForm) throws AssignDataException
	{
		System.out.println("In SetAllValues :::::::>>>> Workflow.java");
		final WorkflowForm workflowForm = (WorkflowForm) abstractForm;
		
		this.name = workflowForm.getName();
	}*/

}
