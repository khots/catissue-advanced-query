package edu.wustl.query.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.hp.hpl.jena.sparql.algebra.op.OpReduced;

import edu.stanford.smi.protegex.owl.swrl.bridge.UnionOfRestrictionInfo;
import edu.wustl.common.actionForm.IValueObject;
import edu.wustl.common.domain.AbstractDomainObject;
import edu.wustl.common.exception.AssignDataException;
import edu.wustl.common.querysuite.queryobject.IAbstractQuery;
import edu.wustl.common.querysuite.queryobject.IOperation;
import edu.wustl.common.querysuite.queryobject.IWorkFlow;
import edu.wustl.common.querysuite.queryobject.impl.CompositeQuery;
import edu.wustl.common.querysuite.queryobject.impl.Intersection;
import edu.wustl.common.querysuite.queryobject.impl.Minus;
import edu.wustl.common.querysuite.queryobject.impl.Operation;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.Query;
import edu.wustl.common.querysuite.queryobject.impl.Union;
import edu.wustl.query.actionForm.WorkflowForm;
import edu.wustl.query.util.global.CompositeQueryOperations;
import edu.wustl.query.util.global.Constants;


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
		
		
		String[] operators = workFlowForm.getOperators();
		String[] operands = workFlowForm.getOperands();
		
		int numberOfRows = operators.length;
		for(int i=0; i<numberOfRows; i++)
		{
			IAbstractQuery query = getQuery(operators[i],operands[i]);
			WorkflowItem workflowItem = new WorkflowItem();
			
			workflowItem.setQuery(query);
			workflowItem.setPosition(i);
			this.workflowItemList.add(workflowItem);
		}
		
		this.setWorkflowItemList(workflowItemList);
	}
	
	/**
	 * Method to get label for the domain object
	 */
	public String getMessageLabel()
	{
		return this.getClass().toString();
	}
	
	private IAbstractQuery getQuery(String operators, String operands)
	{
		IAbstractQuery query = null;
		
		if(operators.equals(CompositeQueryOperations.NONE.getOperation()))
		{
			query = new ParameterizedQuery();
			query.setId(Long.parseLong(operands));
		}
		else
		{
			query = getCompositeQuery(operators, operands);
		}
		return query;
	}

	private IAbstractQuery getCompositeQuery(String operators, String operands)
	{
		String[] operation = operators.split("_");
		IAbstractQuery compositeQuery = null;
		
		
		if(operation.length>1)
		{
			IAbstractQuery operandTwo = new ParameterizedQuery();
			operandTwo.setId(Long.parseLong(operands.substring(operands.lastIndexOf('_'))));
			
			compositeQuery = getCompositeQuery(operators.substring(0,operands.lastIndexOf('_')), operators.substring(0,operators.lastIndexOf('_')));
			
		}
		else
		{
			String[] ids = operands.split("_");
			IAbstractQuery operandOne = new ParameterizedQuery();
			operandOne.setId(Long.parseLong(ids[0]));
			IAbstractQuery operandTwo = new ParameterizedQuery();
			operandTwo.setId(Long.parseLong(ids[1]));
			
			compositeQuery = new CompositeQuery();
			((CompositeQuery)compositeQuery).setOperation(getOperationForCompositeQuery(operation[0], operandOne, operandTwo));
			((CompositeQuery)compositeQuery).setName("Generated by Workflow");
		}
		return compositeQuery;
	}

	private IOperation getOperationForCompositeQuery(String operation, IAbstractQuery operandOne, IAbstractQuery operandTwo)
	{
		Operation operationObj = null;
		if(operation.equals(CompositeQueryOperations.UNION.getOperation()))
		{
			operationObj = new Union();
		}
		else if (operation.equals(CompositeQueryOperations.MINUS.getOperation()))
		{
			operationObj = new Intersection();
		}
		else if (operation.equals(CompositeQueryOperations.INTERSECTION.getOperation()))
		{
			operationObj = new Minus();
		}
				
		operationObj.setOperandOne(operandOne);
		operationObj.setOperandTwo(operandTwo);
		return operationObj;
	}	
}
