package edu.wustl.query.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.wustl.common.actionForm.IValueObject;
import edu.wustl.common.domain.AbstractDomainObject;
import edu.wustl.common.exception.AssignDataException;
import edu.wustl.common.querysuite.queryobject.IAbstractQuery;
import edu.wustl.common.querysuite.queryobject.impl.CompositeQuery;
import edu.wustl.common.querysuite.queryobject.impl.Operation;
import edu.wustl.common.querysuite.queryobject.impl.Query;
import edu.wustl.common.querysuite.queryobject.impl.Union;
import edu.wustl.query.actionForm.WorkflowForm;


/**
 * @author vijay_pande
 * Class for Workflow domain object
 */
public class Workflow extends AbstractDomainObject
{

	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * List of items in workflow
	 */
	private List<WorkflowItem> workflowItemList;
	
	/**
	 * identifier for workflow
	 */
	private Long id;
	
	/**
	 * Name of workflow
	 */
	private String name;
	
	/**
	 * Workflow creation date
	 */
	private Date createdOn;
//	private User createdBy;

	/**
	 * Default constructor for workflow
	 */
	public Workflow()
	{
		super();
	}
	
	/**
	 * Constructor for workflow
	 * @param object object of type IValueObject (formbean)
	 * @throws AssignDataException
	 */
	public Workflow(final IValueObject object) throws AssignDataException
	{
		this();
		setAllValues(object);
	}
	
	/**
	 * Method to get name of the workflow
	 * @return name of type String
	 */
	public String getName()
	{
		return name;
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
	 * Method to get id of the workflow
	 * @return id of type Long
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * Method to set id of the workflow
	 * @param id of type Long
	 */
	public Long getId()
	{
		return this.id;
	}
	
	/**
	 * Method to set workflowItemList of the workflow
	 * @param workflowItemList List of object of type WorkflowItem
	 */
	public void setWorkflowItemList(List<WorkflowItem> workflowItemList)
	{
		this.workflowItemList = workflowItemList;
	}

	/**
	 * Method to get workflowItemList of the workflow
	 * @return workflowItemList List of object of type WorkflowItem
	 */
	public List<WorkflowItem> getWorkflowItemList()
	{
		return this.workflowItemList;
	}	
	
	/**
	 * Method to get workflow creation date
	 * @return createdOn of type Date
	 */
	public Date getCreatedOn()
	{
		return createdOn;
	}

	/**
	 * Method to set workflow creation date
	 * @param createdOn of type Date
	 */
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

	
	/**
	 * Method to populate domain object from formBean
	 */
	public void setAllValues(IValueObject form) throws AssignDataException
	{
		WorkflowForm workFlowForm = (WorkflowForm)form;
		this.workflowItemList = new ArrayList<WorkflowItem>();

		
		this.setName(workFlowForm.getName());
		this.setName("Workflow1");
		this.setCreatedOn(new Date());
		
		WorkflowItem workflowItem =new WorkflowItem();
		workflowItem.setPosition(1);
		IAbstractQuery operandOne = new Query();
		operandOne.setId(Long.valueOf(1));
		workflowItem.setQuery(operandOne);
		this.workflowItemList.add(workflowItem);
		
		workflowItem =new WorkflowItem();
		workflowItem.setPosition(2);
		IAbstractQuery operandTwo = new Query();
		operandTwo.setId(Long.valueOf(2));
		workflowItem.setQuery(operandTwo);
		this.workflowItemList.add(workflowItem);
		
		workflowItem =new WorkflowItem();
		workflowItem.setPosition(3);
		IAbstractQuery query = new Query();
		query.setId(Long.valueOf(3));
		workflowItem.setQuery(query);
		this.workflowItemList.add(workflowItem);
		
		
		workflowItem =new WorkflowItem();
		workflowItem.setPosition(4);
		query = new CompositeQuery();
		Operation operation = new Union();
		operation.setOperandOne(operandOne);
		operation.setOperandTwo(operandTwo);
		
		
		((CompositeQuery)query).setOperation(operation);
		query.setId(Long.valueOf(6));
		workflowItem.setQuery(query);
		this.workflowItemList.add(workflowItem);
  	
		
		this.setWorkflowItemList(workflowItemList);
	}
	
	/**
	 * Method to get label for the domain object
	 */
	public String getMessageLabel()
	{
		return this.getClass().toString();
	}
}
