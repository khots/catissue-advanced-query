package edu.wustl.query.domain;

import java.util.Date;
import java.util.List;

import edu.wustl.common.actionForm.IValueObject;
import edu.wustl.common.domain.AbstractDomainObject;
import edu.wustl.common.exception.AssignDataException;
import edu.wustl.query.actionForm.WorkflowForm;


public class Workflow extends AbstractDomainObject
{

	private static final long serialVersionUID = 1L;
	
	private List<WorkflowItem> workflowItemList;
	private Long id;
	private String name;
	private Date createdOn;
//	private User createdBy;

	public Workflow()
	{
		super();
	}
	
	public Workflow(final IValueObject object) throws AssignDataException
	{
		this();
		setAllValues(object);
	}
	
	public String getName()
	{
		return name;
	}

	
	public void setName(String name)
	{
		this.name = name;
	}

	public void setId(Long id)
	{
		this.id = id;
	}


	public Long getId()
	{
		return this.id;
	}
	
	public void setWorkflowItemList(List<WorkflowItem> workflowItemList)
	{
		this.workflowItemList = workflowItemList;
	}

	public List<WorkflowItem> getWorkflowItemList()
	{
		return this.workflowItemList;
	}	
	
	public Date getCreatedOn()
	{
		return createdOn;
	}

	public void setCreatedOn(Date createdOn)
	{
		this.createdOn = createdOn;
	}

//	public User getCreatedBy()
//	{
//		return createdBy;
//	}
//
//	public void setCreatedBy(User createdBy)
//	{
//		this.createdBy = createdBy;
//	}

	
	@Override
	public void setAllValues(IValueObject form) throws AssignDataException
	{
		WorkflowForm workFlowForm = (WorkflowForm)form;
		this.setName(workFlowForm.getName());
		this.setName("Workflow1");
	}
	
	 public String getMessageLabel()
	{
		return this.getClass().toString();
	}
}
