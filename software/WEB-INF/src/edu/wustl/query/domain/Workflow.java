
package edu.wustl.query.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Map.Entry;

import org.apache.commons.lang.math.NumberUtils;

import edu.wustl.common.actionForm.IValueObject;
import edu.wustl.common.domain.AbstractDomainObject;
import edu.wustl.common.exception.AssignDataException;
import edu.wustl.common.querysuite.queryobject.IAbstractQuery;
import edu.wustl.common.querysuite.queryobject.IOperation;
import edu.wustl.common.querysuite.queryobject.impl.CompositeQuery;
import edu.wustl.common.querysuite.queryobject.impl.Intersection;
import edu.wustl.common.querysuite.queryobject.impl.Minus;
import edu.wustl.common.querysuite.queryobject.impl.Operation;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.Union;
import edu.wustl.query.actionforms.WorkflowForm;
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

	private Long createdBy;

	/**
	 * @return for user id
	 */
	public Long getCreatedBy()
	{
		Long createdBy = this.createdBy;
		return createdBy;
	}

	/**
	 * @param createdBy  for  setting the User id
	 */
	public void setCreatedBy(Long createdBy)
	{
		this.createdBy = createdBy;
	}

	/**
	 * For Work-flow description
	 */
	private String description; 


	/**
	 * For Work-flow description
	 */
	public String getDescription()
	{
		return description;
	}


	/**
	 * @param description workflow description
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	private final Map<String,IAbstractQuery> queryIdMap = new HashMap<String, IAbstractQuery>();

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
	 * Method to set id of the workflow
	 *
	 * @param id of type Long
	 */
	@Override
    public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * Method to set id of the workflow
	 * @return id of type Long
	 */
	@Override
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
		//return createdOn;
		Date temp = this.createdOn;
		return temp;
	}

	/**
	 * Method to set workflow creation date
	 * @param createdOn of type Date
	 */
	public void setCreatedOn(Date createdOn)
	{
		Date temp=createdOn;
		this.createdOn = temp;
	}


	/**
	 * @param form workflow form
	 * @throws AssignDataException
	 * Method to populate domain object from formBean
	 */
	@Override
    public void setAllValues(IValueObject form) throws AssignDataException
	{
		WorkflowForm workFlowForm = (WorkflowForm) form;
		String[] displayQueryTitle =workFlowForm.getDisplayQueryTitle();
		this.workflowItemList = new ArrayList<WorkflowItem>();
		this.setName(workFlowForm.getName());
		this.setCreatedOn(new Date());
		String[] identifier=workFlowForm.getQueryIdForRow();
		String[] expressions = workFlowForm.getExpression();
		//Long[] workflowItemId=workFlowForm.getWorkflowItemId();
//		String[] expressions = new String[] {""};
		int numberOfRows = 0;
		if (expressions != null)
		{
			numberOfRows = expressions.length;
		}
		for (int i = 0; i < numberOfRows; i++)
		{
//			IAbstractQuery query = getQuery(operators[i], operands[i],displayQueryTitle[i]);
            IAbstractQuery query = getQuery(expressions[i]);
            query.setName(displayQueryTitle[i]);

			WorkflowItem workflowItem = new WorkflowItem();
			//workflowItem.setId(workflowItemId[i]);
			//
			if(!identifier[i].contains("_"))
			{
				query.setId(Long.parseLong(identifier[i]));
			}
			workflowItem.setQuery(query);
			workflowItem.setPosition(i);
			this.workflowItemList.add(workflowItem);
		}

		this.setWorkflowItemList(workflowItemList);
		this.description=workFlowForm.getWfDescription();
		this.id=workFlowForm.getId();
	}

	/**
	 * Method to get label for the domain object
	 * @return label of domain object
	 */

	@Override
    public String getMessageLabel()
	{
		return this.getClass().toString();
	}

	/**
	 * @param operators String of operators
	 * @param operands String of operands
	 * @param queryTitle String of query Title
	 * @return IAbstractQuery
	 */
	public IAbstractQuery getQuery(String operators, String operands,String queryTitle)
	{
		IAbstractQuery query = null;

		if (operators.equals(CompositeQueryOperations.NONE.getOperation()))
		{
			query = new ParameterizedQuery();
			//query.setId(Long.parseLong(operands));
			((ParameterizedQuery)query).setName(queryTitle);
		}
		else
		{
			query = getCompositeQuery(operators, operands);
			((CompositeQuery)query).setName(queryTitle);
		}
		return query;
	}


	/**
	 * @param operators String of operators
	 * @param operands String of operands
	 * @return
	 */
	public IAbstractQuery getCompositeQuery(String operators, String operands)
	{
		String[] operand = operands.split("_");
		IAbstractQuery compositeQuery = new CompositeQuery();

		if (operand.length > 2)
		{
			IAbstractQuery operandTwo = new ParameterizedQuery();
			operandTwo.setId(Long.parseLong(operands.substring(operands.lastIndexOf('_') + 1)));

			String tempOperation = operators.substring(operators.lastIndexOf('_') + 1);
			IAbstractQuery tempCompositeQuery = getCompositeQuery(operators.substring(0, operators
					.lastIndexOf('_')), operands.substring(0, operands.lastIndexOf('_')));
			((CompositeQuery) compositeQuery).setOperation(getOperationForCompositeQuery(
					tempOperation, tempCompositeQuery, operandTwo));
		}
		else
		{
			String[] ids = operands.split("_");
			String[] operator = operators.split("_");
			IAbstractQuery operandOne = new ParameterizedQuery();
			operandOne.setId(Long.parseLong(ids[0]));
			IAbstractQuery operandTwo = new ParameterizedQuery();
			operandTwo.setId(Long.parseLong(ids[1]));

			compositeQuery = new CompositeQuery();
			((CompositeQuery) compositeQuery).setOperation(getOperationForCompositeQuery(
					operator[0], operandOne, operandTwo));
						String name = "CompositeQuery_"+ new Date().getTime();
						((CompositeQuery)compositeQuery).setName(name);
						((CompositeQuery)compositeQuery).setType("operation");
		}
		return compositeQuery;
	}

	/**
	 * @param operation String of operation
	 * @param operandOne IAbstractQuery
	 * @param operandTwo IAbstractQuery
	 * @return IOperation
	 */
	public IOperation getOperationForCompositeQuery(String operation, IAbstractQuery operandOne,
			IAbstractQuery operandTwo)
	{
		Operation operationObj = null;
		operationObj = getOperation(operation, operationObj);

		if(operationObj!=null)
		{
			operationObj.setOperandOne(operandOne);
			operationObj.setOperandTwo(operandTwo);
		}
		return operationObj;
	}

	/**
	 * @param operation String operation
	 * @param operationObj Operation
	 * @return Operation
	 */
	private Operation getOperation(String operation, Operation operationObj)
	{
		if (operation.equals(CompositeQueryOperations.UNION.getOperation()))
		{
			operationObj = new Union();
		}
		else if (operation.equals(CompositeQueryOperations.MINUS.getOperation()))
		{
			operationObj = new Minus();
		}
		else if (operation.equals(CompositeQueryOperations.INTERSECTION.getOperation()))
		{
			operationObj = new Intersection();
		}
		return operationObj;
	}

    /**
     * Forms the IAbstract Query object based on the post fix expression.
     * @param expression The post fix expression
     * @return The corresponding IAbstractQuery object.
     */
    public IAbstractQuery getQuery(String expression)
    {
        Stack<IAbstractQuery> stack = new Stack<IAbstractQuery>();

        String[] exprStack = expression.split("_");
        for (String var : exprStack)
        {
            var = var.trim();
            if (NumberUtils.isNumber(var))
            {
                // Its a queryId, so skip
                IAbstractQuery queryVar = null;
                if (queryIdMap.containsKey(var))
                {
                    queryVar = queryIdMap.get(var);
                }
                else
                {
                    queryVar = new ParameterizedQuery();
                    queryIdMap.put(var, queryVar);
                }
                stack.push(queryVar);
            }
            else
            {
                // Its an operator, so pop last 2 from stack, create a new CQ
                // with current operator and the popped operands and then push
                // the new node.
                IAbstractQuery op1 = stack.pop();
                IAbstractQuery op2 = stack.pop();
                IAbstractQuery op3 = null;

                String key = getKey(op2) + "_" + getKey(op1) + "_" + var; 
                if (queryIdMap.containsKey(key))
                {
                    op3 = queryIdMap.get(key);
                }
                else
                {
                    op3 = new CompositeQuery();

                    Operation operationObj = null;
                    CompositeQueryOperations opr = CompositeQueryOperations.get(var);
                    if (opr.equals(CompositeQueryOperations.UNION))
                    {
                        operationObj = new Union();
                    }
                    else if (opr.equals(CompositeQueryOperations.MINUS))
                    {
                        operationObj = new Minus();
                    }
                    else if (opr.equals(CompositeQueryOperations.INTERSECTION))
                    {
                        operationObj = new Intersection();
                    }
                    operationObj.setOperandOne(op1);
                    operationObj.setOperandTwo(op2);
                    ((CompositeQuery) op3).setOperation(operationObj);
                    ((CompositeQuery) op3).setType(Constants.QUERY_TYPE_GET_COUNT);

                    queryIdMap.put(key, op3);
                    Set<Entry<String, IAbstractQuery>> entrySet = queryIdMap.entrySet();
                    int s = entrySet.size();
                }

                stack.push(op3);
            }
        }
        return stack.pop();
    }

	/**
	 * This method returns the true 'key' of the operand that is passed.
	 * <ol>
	 * <li>If the operand is a Parameterized Query then the key is the same as
	 * the Query Id.</li>
	 * <li>If the operand is a saved Composite query, then the key is the same
	 * as the query Id.</li>If the operand is an unsaved Composite Query then
	 * the key is the "postfix" expression formed for that operand.
	 * <li></li>
	 * </ol>
	 *
	 * @param operand
	 *            IAbstractQuery.
	 * @return Key corresponding to the given operand.
	 */
	private String getKey(IAbstractQuery operand)
	{
		String keyOfOperand = null;
		// start method - private String getKey(IAbstractQuery op)
		if (operand.getId() == null)
		{
			// get Key for Op2
			Set<Entry<String, IAbstractQuery>> operandEntrySet = queryIdMap
					.entrySet();
			for (Entry<String, IAbstractQuery> entry : operandEntrySet)
			{
				if (operand.equals(entry.getValue()))
				{
					keyOfOperand = entry.getKey();
					break;
				}
			}
		}
		else
		{
			keyOfOperand = operand.getId().toString();
		}
		return keyOfOperand;
	}
}
