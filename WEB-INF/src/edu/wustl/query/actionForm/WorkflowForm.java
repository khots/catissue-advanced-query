
package edu.wustl.query.actionForm;

import java.util.LinkedList;
import java.util.List;
import edu.wustl.common.actionForm.AbstractActionForm;
import edu.wustl.common.domain.AbstractDomainObject;
import edu.wustl.common.querysuite.queryobject.IAbstractQuery;
import edu.wustl.common.querysuite.queryobject.IOperation;
import edu.wustl.common.querysuite.queryobject.impl.CompositeQuery;
import edu.wustl.common.querysuite.queryobject.impl.Intersection;
import edu.wustl.common.querysuite.queryobject.impl.Minus;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.Union;
import edu.wustl.common.util.Utility;
import edu.wustl.query.domain.Workflow;
import edu.wustl.query.domain.WorkflowItem;
import edu.wustl.query.util.global.CompositeQueryOperations;
import edu.wustl.query.util.global.Constants;

/**
 * 
 * @author ravindra_jain
 *	
 */
/**
 * @author chitra_garg
 *
 */
/**
 * @author chitra_garg
 *
 */
public class WorkflowForm extends AbstractActionForm
{

	/**
	 * @return that get count or composite query 
	 */
	public String[] getQueryTypeControl()
	{
		return queryTypeControl;
	}

	/**
	 * @param queryTypeControl=array containing get count or composite query 
	 */
	public void setQueryTypeControl(String[] queryTypeControl)
	{
		this.queryTypeControl = queryTypeControl;
	}

	/**
	 * @return  operands array . Each element of array is  underscore separated operands
	 * corresponding to each row of table displayed in UI 
	 */
	public String[] getOperands()
	{
		return operands;
	}

	/**
	 * @param operands=array of operand's id separated by underscore values
	 */
	public void setOperands(String[] operands)
	{
		this.operands = operands;
	}

	/**
	 * @return operators array . Each element of array is  underscore separated operators
	 * corresponding to each row of table displayed in UI 
	 */
	public String[] getOperators()
	{
		return operators;
	}

	/**
	 * @param operators= array of operators's id separated by underscore values
	 */
	public void setOperators(String[] operators)
	{
		this.operators = operators;
	}

	/**
	 * @return the displayQueryTitle array . Each element of array is  underscore separated displayQueryTitle
	 * corresponding to each row of table displayed in UI 
	 */
	public String[] getDisplayQueryTitle()
	{
		return displayQueryTitle;
	}

	/**
	 * @param displayQueryTitle title corresponding 
	 * to each row of table displayed in UI
	 */
	public void setDisplayQueryTitle(String[] displayQueryTitle)
	{
		this.displayQueryTitle = displayQueryTitle;
	}

	/**
	 * @return queryTypeControl=array containing get count or composite query 
	 */
	public String[] getDisplayQueryType()
	{
		return displayQueryType;
	}

	/**
	 * @param displayQueryType=array of query type
	 * each row of the array corresponds to one row  in work flow UI
	 */
	public void setDisplayQueryType(String[] displayQueryType)
	{
		this.displayQueryType = displayQueryType;
	}

	/**
	 * Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Query ID array corresponding 
	 * to each row in UI  
	 */
	protected String[] queryId;

	/**
	 *queryTitle of queries array 
	 * selected from the work flow pop up
	 */
	protected String[] queryTitle;

	/**
	 *Composite Query or 
	 * parameterized Query of query for each row in UI 
	 */
	protected String[] queryType;

	/**
	 * check box controls from UI 
	 */
	protected boolean[] chkbox;

	/**
	 * Name of the workFlow
	 */
	private String name;

	/**
	 * @return Query Id array 
	 */
	public String[] getQueryId()
	{
		return queryId;
	}

	/**
	 * @param queryId=set the value of Query ID 
	 * array corresponding to each row in UI  
	 */
	public void setQueryId(String[] queryId)
	{
		this.queryId = queryId;
	}

	/**
	 * @return queryTitle of queries array 
	 * selected from the work flow pop up
	 */
	public String[] getQueryTitle()
	{
		return queryTitle;
	}

	/**
	 * @param queryTitle 
	 */
	public void setQueryTitle(String[] queryTitle)
	{
		this.queryTitle = queryTitle;
	}

	/**
	 * @return queryType of queries array
	 *  selected from the work flow pop up
	 */
	public String[] getQueryType()
	{
		return queryType;
	}

	/**
	 * @param queryType=composite Query or 
	 * parameterized Query of query for each row in UI 
	 */
	public void setQueryType(String[] queryType)
	{
		this.queryType = queryType;
	}

	/**
	 * @return hidden control that contains the 
	 * value of Query title 
	 */
	public String[] getQueryTitleControl()
	{
		return queryTitleControl;
	}

	/**
	 * @param queryTitleControl hidden control that contains the 
	 * value of Query title 
	 * This function sets the  value of  this hidden control
	 */
	public void setQueryTitleControl(String[] queryTitleControl)
	{
		this.queryTitleControl = queryTitleControl;
	}

	/**
	 * @return QueryIdControlarray
	 */
	public String[] getQueryIdControl()
	{
		return queryIdControl;
	}

	/**
	 * @param queryIdControl hidden field in UI 
	 * for the Query id
	 */
	public void setQueryIdControl(String[] queryIdControl)
	{
		this.queryIdControl = queryIdControl;
	}

	/**
	 * @return operands array.underscore separated for each row of table 
	 */
	public String[] getSelectedqueryId()
	{
		return selectedqueryId;
	}

	/**
	 * @param operands array.underscore separated for each row of table 
	 */
	public void setSelectedqueryId(String[] selectedqueryId)
	{
		this.selectedqueryId = selectedqueryId;
	}

	/**
	 * @return number of check boxes created
	 */
	public boolean[] getChkbox()
	{
		return chkbox;
	}

	/**
	 * @param chkbox=check box controls from UI 
	 */
	public void setChkbox(boolean[] chkbox)
	{
		this.chkbox = chkbox;
	}

	/**
	 * hidden control in UI that contains the 
	 * value of Query title 
	 */
	protected String[] queryTitleControl;

	/**
	 *hidden field in UI 
	 * for the Query id
	 */
	protected String[] queryIdControl;

	/**
	 * array containing get count or composite query
	 */
	protected String[] queryTypeControl;
	
	protected Long[] identifier;

	/**
	 * array containing operand's ids
	 */
	protected String[] operands;
	/**
	 * array containing operators
	 */
	protected String[] operators;
	/**
	 * 
	 */
	protected String[] displayQueryTitle;
	/**
	 * 
	 */
	protected String[] displayQueryType;
	/**
	 * 
	 */
	protected String[] selectedqueryId;

	/**
	 * Method to get name of the workflow
	 * @return name of type String
	 */
	public String getName()
	{
		return this.name;
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

	/* (non-Javadoc)
	 * @see edu.wustl.common.actionForm.AbstractActionForm#reset()
	 * reset method to set default values
	 */
	protected void reset()
	{

	}

	/**
	 * @param domain object from which form object will generate
	 * Method to populate formBean from domain object
	 */
	public void setAllValues(AbstractDomainObject abstractDomain)
	{
		Workflow workflow = (Workflow) abstractDomain;
		LinkedList<String> opretionList = new LinkedList<String>();
		LinkedList<String> operandList = new LinkedList<String>();

		this.id = workflow.getId();
		this.name = Utility.toString(workflow.getName());
		List<WorkflowItem> workflowItemList = workflow.getWorkflowItemList();

		//  display title
		String[] displayQueryTitle = new String[workflowItemList.size()];
		Long[] identifier = new Long[workflowItemList.size()];
		for (int i = 0; i < workflowItemList.size(); i++)
		{
			WorkflowItem workflowItem = workflowItemList.get(i);
			//to initalize identifier array
			//this.identifier[i]=workflowItem.getQuery().getId();
			LinkedList<String> operatorsList = new LinkedList<String>();
			LinkedList<Long> operandsList = new LinkedList<Long>();
			IAbstractQuery abstractQuery = workflowItem.getQuery();


			identifier[i]=abstractQuery.getId();
			generateOperatorAndOperandList(operatorsList, operandsList, abstractQuery);
			setoperandList(operandList, operandsList);
			setOperatorList(opretionList, operatorsList);
		}

		String[] operands = new String[operandList.size()];//oprands array
		String[] operators = new String[opretionList.size()];//operator array 
		//initialize operator and operand array 
		opretionList.toArray(operators);
		operandList.toArray(operands);
		genetrateDisplayQueryTitle(operators, operands, displayQueryTitle);

		//starts TO DO query type value retrieve .....currently hard coded
		// Commented as not needed
		/*String[] queryTypeControl=new String[workflowItemList.size()];
		String[] displayQueryType=new String[workflowItemList.size()];*/
		createQueryTitle(workflowItemList.size());//, queryTypeControl, displayQueryType);
		//ends
		// starts selectedqueryId same as operandsArray 
		String[] selectedqueryId = operands;
		this.selectedqueryId = selectedqueryId;
		this.displayQueryTitle = displayQueryTitle;
		//this.queryTypeControl=queryTypeControl;
		//this.displayQueryType=displayQueryType;
		this.operands = operands;
		this.operators = operators;
		boolean[] chkbox = new boolean[workflowItemList.size()];
		this.chkbox = chkbox;
		this.identifier=identifier;

	}

	private void setOperatorList(LinkedList<String> opretionList, LinkedList<String> operatorsList)
	{
		if (operatorsList != null)
		{
			String operationString = "";

			for (String operation : operatorsList)
			{
				operationString = operationString + operation + "_";
			}
			if (operationString != null && !(operationString.equals("")))
			{
				opretionList.add(operationString.substring(0, operationString.lastIndexOf('_')));
			}
		}
	}

	private void setoperandList(LinkedList<String> operandList, LinkedList<Long> operandsList)
	{
		if (operandsList != null)
		{
			String operandString = "";
			for (Long operand : operandsList)
			{
				operandString = operandString + operand + "_";
			}
			if (operandString != null)
			{
				operandList.add(operandString.substring(0, operandString.lastIndexOf('_')));
			}
		}
	}

	/*
	 * */
	private void createQueryTitle(int size)//, String[] queryTypeControl,String[] displayQueryType)

	{

		String[] queryTypeControl = new String[size];
		String[] displayQueryType = new String[size];
		for (int i = 0; i < size; i++)
		{
			queryTypeControl[i] = "Get Count";
			displayQueryType[i] = "Get Count";
		}
		this.queryTypeControl = queryTypeControl;
		this.displayQueryType = displayQueryType;
	}

	/**
	 * 
	 * @param operators
	 * @param operands
	 * @param displayQueryTitle
	 * generates the List of  displayQueryTitle
	 */
	private void genetrateDisplayQueryTitle(String[] operators, String[] operands,
			String[] displayQueryTitle)
	{
		for (int i = 0; i < operators.length; i++)
		{
			String[] operatorsInRow = operators[i].split("_");
			String[] operandsInRow = operands[i].split("_");
			if (operandsInRow.length == 1)//PQ
			{
				displayQueryTitle[i] = operandsInRow[0];
			}
			else
			{
				String queryConst = "[Query";
				String queryTitle = "";
				int j = 0;
				for (; j < operatorsInRow.length; j++)
				{
					queryTitle = queryTitle + queryConst + " : " + operandsInRow[j] + "]"
							+ operatorsInRow[j];
				}
				displayQueryTitle[i] = queryTitle + queryConst + " : " + operandsInRow[j] + "]";
			}
		}
	}

	private void generateOperatorAndOperandList(LinkedList<String> operatorsList,
			LinkedList<Long> operandsList, IAbstractQuery abstractQuery)
	{
		if (abstractQuery instanceof ParameterizedQuery)
		{
			operatorsList.add(CompositeQueryOperations.NONE.getOperation());
			operandsList.add(abstractQuery.getId());
		}
		else
		{
			generateOperatorAndOperandListForCompositeQuery(operatorsList, operandsList,
					abstractQuery);
		}
	}

	private void generateOperatorAndOperandListForCompositeQuery(LinkedList<String> operatorsList,
			LinkedList<Long> operandsList, IAbstractQuery abstractQuery)
	{
		if (abstractQuery instanceof CompositeQuery)
		{
			CompositeQuery compositeQuery = (CompositeQuery) abstractQuery;
			IOperation operation = compositeQuery.getOperation();
			String operationName = setOperationForCompositeQuery(operation);
			generateOperatorAndOperandListForCompositeQuery(operatorsList, operandsList, operation
					.getOperandOne());
			generateOperatorAndOperandListForCompositeQuery(operatorsList, operandsList, operation
					.getOperandTwo());
			operatorsList.add(operationName);
		}
		else
		{
			operandsList.add(abstractQuery.getId());
		}
	}

	/**
	 * @param operation = operation on the CQ
	 * @return operation object according to the String of operation to perform
	 * 
	 * This method re
	 */
	private String setOperationForCompositeQuery(IOperation operation)
	{
		String operationName = null;
		if (operation instanceof Union)
		{
			operationName = "Union";
		}
		else if (operation instanceof Intersection)
		{
			operationName = "Intersection";
		}
		else if (operation instanceof Minus)
		{
			operationName = "Minus";
		}
		return operationName;
	}

	
	public Long[] getIdentifier()
	{
		return identifier;
	}

	
	public void setIdentifier(Long[] identifier)
	{
		this.identifier = identifier;
	}

}
