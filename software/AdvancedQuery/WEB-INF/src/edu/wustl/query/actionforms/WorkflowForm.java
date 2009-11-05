
package edu.wustl.query.actionforms;

import java.util.LinkedList;
import java.util.List;

import edu.wustl.common.actionForm.AbstractActionForm;
import edu.wustl.common.domain.AbstractDomainObject;
import edu.wustl.common.querysuite.queryobject.IAbstractQuery;
import edu.wustl.common.querysuite.queryobject.IOperation;
import edu.wustl.common.querysuite.queryobject.impl.AbstractQuery;
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

/**Class for WorkflowForm.
 * @author chitra_garg
 *
 */
public class WorkflowForm extends AbstractActionForm
{

	/**Method to get queryTypeControl.
	 * @return that get count or composite query
	 */
	public String[] getQueryTypeControl()
	{
		String[] queryTypeControl = this.queryTypeControl;
		return queryTypeControl;
	}

	/**array containing get count or composite query.
	 * @param queryTypeControl
	 * 
	 */
	public void setQueryTypeControl(String[] queryTypeControl)
	{
		String[] temp = new String[queryTypeControl.length];
		System.arraycopy(queryTypeControl, 0, temp, 0, queryTypeControl.length);
		this.queryTypeControl = temp;
	}

	/**corresponding to each row of table displayed in UI.
	 * @return  operands array . Each element of array is  underscore separated operands
	 * 
	 */
	public String[] getOperands()
	{

		String[] operands = this.operands;
		return operands;
	}

	/**id separated by underscore values.
	 * @param operands array of operand's
	 * 
	 */
	public void setOperands(String[] operands)
	{
		String[] temp = new String[operands.length];
		System.arraycopy(operands, 0, temp, 0, operands.length);

		this.operands = temp;
	}

	/**corresponding to each row of table displayed in UI.
	 * @return operators array . Each element of array is  underscore separated operators
	 * 
	 */
	public String[] getOperators()
	{
		String[] operators = this.operators;
		return operators;

	}

	/**operators's id separated by underscore values.
	 * @param operators array of
	 * 
	 */
	public void setOperators(String[] operators)
	{

		String[] temp = new String[operators.length];
		System.arraycopy(operators, 0, temp, 0, operators.length);
		this.operators = temp;
	}

	/**corresponding to each row of table displayed in UI.
	 * @return the displayQueryTitle array . Each element of array is  underscore separated displayQueryTitle
	 * 
	 */
	public String[] getDisplayQueryTitle()
	{
		String[] displayQueryTitle = this.displayQueryTitle;
		return displayQueryTitle;

	}

	/**to each row of table displayed in UI.
	 * @param displayQueryTitle title corresponding
	 * 
	 */
	public void setDisplayQueryTitle(String[] displayQueryTitle)
	{
		String[] temp = new String[displayQueryTitle.length];
		System.arraycopy(displayQueryTitle, 0, temp, 0, displayQueryTitle.length);
		this.displayQueryTitle = temp;
	}

	/**method to get displayQueryType.
	 * @return queryTypeControl=array containing get count or composite query
	 */
	public String[] getDisplayQueryType()
	{
		String[] displayQueryType = this.displayQueryType;
		return displayQueryType;
	}

	/**each row of the array corresponds to one row  in work flow UI.
	 * @param displayQueryType array of query type.
	 * 
	 */
	public void setDisplayQueryType(String[] displayQueryType)
	{
		String[] temp = new String[displayQueryType.length];
		System.arraycopy(displayQueryType, 0, temp, 0, displayQueryType.length);
		this.displayQueryType = temp;
	}

	/**
	 * Default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Query ID array corresponding to each row in UI.
	 */
	protected String[] queryId;

	/**
	 *queryTitle of queries array selected from the work flow pop up.
	 */
	protected String[] queryTitle;

	/**
	 *Composite Query or parameterized Query of query for each row in UI.
	 */
	protected String[] queryType;

	/**
	 * check box controls from UI.
	 */
	protected boolean[] chkbox;

	/**
	 * Name of the workFlow.
	 */
	private String name;

	/**Method to get queryId.
	 * @return Query Id array.
	 */
	public String[] getQueryId()
	{
		String[] queryId = this.queryId;
		return queryId;
	}

	/**array corresponding to each row in UI.
	 * @param queryId set the value of Query ID
	 */
	public void setQueryId(String[] queryId)
	{
		String[] temp = new String[queryId.length];
		System.arraycopy(queryId, 0, temp, 0, queryId.length);
		this.queryId = temp;
	}

	/**selected from the work flow pop up.
	 * @return queryTitle of queries array
	 */
	public String[] getQueryTitle()
	{
		String[] queryTitle = this.queryTitle;
		return queryTitle;
	}

	/**Method to set query title.
	 * @param queryTitle query title
	 */
	public void setQueryTitle(String[] queryTitle)
	{
		String[] temp = new String[queryTitle.length];
		System.arraycopy(queryTitle, 0, temp, 0, queryTitle.length);
		this.queryTitle = temp;
	}

	/**selected from the work flow pop up.
	 * @return queryType of queries array
	 */
	public String[] getQueryType()
	{
		String[] queryType = this.queryType;
		return queryType;
	}

	/**parameterized Query of query for each row in UI.
	 * @param queryType composite Query or
	 */
	public void setQueryType(String[] queryType)
	{
		String[] temp = new String[queryType.length];
		System.arraycopy(queryType, 0, temp, 0, queryType.length);
		this.queryType = temp;
	}

	/** value of Query title.
	 * @return hidden control that contains the
	 */
	public String[] getQueryTitleControl()
	{
		String[] queryTitleControl = this.queryTitleControl;
		return queryTitleControl;
	}

	/**This function sets the  value of  this hidden control.
	 * @param queryTitleControl hidden control that contains the value of Query title
	 */
	public void setQueryTitleControl(String[] queryTitleControl)
	{
		String[] temp = new String[queryTitleControl.length];
		System.arraycopy(queryTitleControl, 0, temp, 0, queryTitleControl.length);
		this.queryTitleControl = temp;
	}

	/** Method to get queryIdControl array.
	 * @return QueryIdControlarray
	 */
	public String[] getQueryIdControl()
	{
		String[] queryIdControl = this.queryIdControl;
		return queryIdControl;
	}

	/**Method to set queryIdControl.
	 * @param queryIdControl hidden field in UI for the Query id
	 */
	public void setQueryIdControl(String[] queryIdControl)
	{
		String[] temp = new String[queryIdControl.length];
		System.arraycopy(queryIdControl, 0, temp, 0, queryIdControl.length);
		this.queryIdControl = temp;
	}

	/**Method to get selectedqueryId array.
	 * @return operands array.underscore separated for each row of table
	 */
	public String[] getSelectedqueryId()
	{
		String[] selectedqueryId = this.selectedqueryId;
		return selectedqueryId;
	}

	/**Method to set selectedqueryId array.
	 * @param selectedqueryId array.underscore separated for each row of table
	 */
	public void setSelectedqueryId(String[] selectedqueryId)
	{
		String[] temp = new String[selectedqueryId.length];
		System.arraycopy(selectedqueryId, 0, temp, 0, selectedqueryId.length);
		this.selectedqueryId = temp;
	}

	/** Method to get check box array.
	 * @return number of check boxes created
	 */
	public boolean[] getChkbox()
	{
		boolean[] chkbox = this.chkbox;
		return chkbox;
	}

	/**Method to set check box array.
	 * @param chkbox check box controls from UI.
	 */
	public void setChkbox(boolean[] chkbox)
	{
		boolean[] temp = new boolean[chkbox.length];
		System.arraycopy(chkbox, 0, temp, 0, chkbox.length);
		this.chkbox = chkbox;
	}

	/**
	 * hidden control in UI that contains the value of Query title.
	 */
	protected String[] queryTitleControl;

	/**
	 *hidden field in UI for the Query id.
	 */
	protected String[] queryIdControl;

	/**
	 * array containing get count or composite query.
	 */
	protected String[] queryTypeControl;

	protected String[] identifier;

	/**
	 * array containing operand's ids.
	 */
	protected String[] operands;
	/**
	 * array containing operators.
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
	 * Method to get name of the workflow.
	 * @return name of type String
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Method to set name of the workflow.
	 * @param name of type String
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Method to get form id.
	 * @return name of type String
	 */
	@Override
	public int getFormId()
	{
		return Constants.WORKFLOW_FORM_ID;
	}

	/**
	 * Contains the query execution ID.
	 */
	private Long[] queryExecId;

	/**
	 * @return query Execution id
	 */
	public Long[] getQueryExecId()
	{
		Long[] queryExecId = this.queryExecId;
		return queryExecId;
	}

	/**
	 * @param queryExeId query Execution Id
	 */
	public void setQueryExecId(Long[] queryExeId)
	{
		Long[] temp = new Long[queryExeId.length];
		System.arraycopy(queryExeId, 0, temp, 0, queryExeId.length);
		queryExecId = temp;
	}

	/**
	 * Contains the query execution ID.
	 */
	private Long[] workflowItemId;

	/**
	 * @return query Execution id
	 */
	public Long[] getWorkflowItemId()
	{
		Long[] workflowItemId = this.workflowItemId;
		return workflowItemId;
	}

	/**
	 * @param workflowItemId workflow Item Id
	 */
	public void setWorkflowItemId(Long[] workflowItemId)
	{
		Long[] temp = new Long[workflowItemId.length];
		System.arraycopy(workflowItemId, 0, temp, 0, workflowItemId.length);
		this.workflowItemId = temp;
	}

	/* (non-Javadoc)
	 * @see edu.wustl.common.actionForm.AbstractActionForm#reset()
	 * reset method to set default values
	 */
	@Override
	protected void reset()
	{

	}

	/**
	 * for queryIds.
	 */
	private String[] queryIdForRow;

	public String[] getQueryIdForRow()
	{
		String[] queryIdForRow = this.queryIdForRow;
		return queryIdForRow;
	}

	public void setQueryIdForRow(String[] queryIdForRow)
	{
		String[] temp = new String[queryIdForRow.length];
		System.arraycopy(queryIdForRow, 0, temp, 0, queryIdForRow.length);
		this.queryIdForRow = temp;
	}

	/**
	 * for postfix expression .
	 */
	private String[] expression;

	/**
	 * @return postfix expression.
	 */
	public String[] getExpression()
	{
		String[] expression = this.expression;
		return expression;
	}

	/**
	 * @param expression post-fix expression.
	 */
	public void setExpression(String[] expression)
	{

		String[] temp = new String[expression.length];
		System.arraycopy(expression, 0, temp, 0, expression.length);
		this.expression = temp;
		//this.expression = expression;
	}

	/**
	 * for post-fix expression
	 */
	private String[] queryIdList;

	/**
	 * @return queryIdList for post fix expression.
	 */
	public String[] getQueryIdList()
	{
		String[] queryIdList = this.queryIdList;
		return queryIdList;
	}

	/**
	 * @param queryIdList queryIdList for post fix expression
	 */
	public void setQueryIdList(String[] queryIdList)
	{
		String[] temp = new String[queryIdList.length];
		System.arraycopy(queryIdList, 0, temp, 0, queryIdList.length);

		this.queryIdList = temp;
	}

	//	private Long createdBy;
	//
	//	/**
	//	 * @return for user id
	//	 */
	//	public Long getCreatedBy() {
	//		return createdBy;
	//	}
	//
	//	/**
	//	 * @param createdBy  for  setting the User id
	//	 */
	//	public void setCreatedBy(Long createdBy) {
	//		this.createdBy = createdBy;
	//	}

	/**
	 * added for get Count - latest project Id
	 */
	private Long executedForProject;

	/**
	 * @return latest project Id.
	 */
	public Long getExecutedForProject()
	{
		return executedForProject;
	}

	/**
	 * set value of latest project Id.
	 * @param executedForProject latest project Id.
	 */
	public void setExecutedForProject(Long executedForProject)
	{
		this.executedForProject = executedForProject;
	}

	/**
	 * For Work-flow description.
	 */
	private String wfDescription;

	/**
	 * @return Work-flow description
	 */
	public String getWfDescription()
	{
		return wfDescription;
	}

	/**
	 * @param wfDescription  Work-flow description.
	 */
	public void setWfDescription(String wfDescription)
	{
		this.wfDescription = wfDescription;
	}

	/**Method to populate formBean from domain object.
	 * @param abstractDomain object from which form object will generate
	 */
	public void setAllValues(AbstractDomainObject abstractDomain)
	{
		Workflow workflow = (Workflow) abstractDomain;
		setId(workflow.getId());
		populateWorklfow(workflow);
		//this.workflowItemId=workflowItemsId;

	}

	/**
	 * this method populates the workflow.
	 * @param workflow Workflow
	 */
	public void populateWorklfow(Workflow workflow)
	{
		LinkedList<String> opretionList = new LinkedList<String>();
		LinkedList<String> operandList = new LinkedList<String>();
		wfDescription = workflow.getDescription();
		name = Utility.toString(workflow.getName());
		List<WorkflowItem> workflowItemList = workflow.getWorkflowItemList();
		int size = workflowItemList.size();
		String[] displayQueryTitle = new String[size];
		String[] displayQueryType = new String[size];
		String[] identifier = new String[workflowItemList.size()];
		String[] expression = new String[workflowItemList.size()];
		for (int i = 0; i < workflowItemList.size(); i++)
		{
			WorkflowItem workflowItem = workflowItemList.get(i);
			//workflowItemsId[i]=workflowItem.getId();
			//to initalize identifier array
			//this.identifier[i]=workflowItem.getQuery().getId();
			LinkedList<String> operatorsList = new LinkedList<String>();
			LinkedList<Long> operandsList = new LinkedList<Long>();
			IAbstractQuery abstractQuery = workflowItem.getQuery();
			identifier[i] = String.valueOf(abstractQuery.getId());
			generateOperatorAndOperandList(operatorsList, operandsList, abstractQuery);
			// empty expression is passed for the first call
			expression[i] = generatePostfixExpression(abstractQuery, "");
			setoperandList(operandList, operandsList);
			setOperatorList(opretionList, operatorsList);
			displayQueryTitle[i] = genetrateDisplayQueryTitle(workflowItem);
			displayQueryType[i] = ((AbstractQuery) abstractQuery).getType();
			//TO DO uncomment the code for the  queryExecId
			//queryExecId[i]=queryExecutionIdMap.get(abstractQuery.getId());
		}
		//oprands array
		String[] operands = new String[operandList.size()];
		//operator array
		String[] operators = new String[opretionList.size()];
		//initialize operator and operand array
		opretionList.toArray(operators);
		operandList.toArray(operands);

		// starts selectedqueryId same as operandsArray
		String[] selectedqueryId = operands;
		this.selectedqueryId = selectedqueryId;
		this.displayQueryType = displayQueryType;
		this.displayQueryTitle = displayQueryTitle;

		this.operands = operands;
		this.operators = operators;
		boolean[] chkbox = new boolean[workflowItemList.size()];
		this.chkbox = chkbox;
		this.identifier = identifier;
		this.expression = expression;
		queryIdForRow = identifier;
	}

	/**Method to generate Postfix Expression.
	 * @param abstractQuery  Query
	 * @param exp postfixExpression
	 * @return postfixExpression
	 */
	private String generatePostfixExpression(IAbstractQuery abstractQuery, String exp)
	{
		String postfixExp;

		if (abstractQuery instanceof CompositeQuery)
		{
			CompositeQuery compositeQuery = (CompositeQuery) abstractQuery;
			IAbstractQuery operandOne = compositeQuery.getOperation().getOperandOne();
			IAbstractQuery operandTwo = compositeQuery.getOperation().getOperandTwo();
			exp = exp + generatePostfixExpression(operandTwo, exp) + "_"
					+ generatePostfixExpression(operandOne, exp) + "_"
					+ setShortOperation(compositeQuery.getOperation());
			postfixExp = exp;
		}
		else
		{
			//return abstractQuery.getId().toString();
			postfixExp = abstractQuery.getId().toString();
		}
		return postfixExp;
	}

	/**Method to generate Display Query Title.
	 * @param workflowItem workflow Item
	 * @return query title for workflowItem
	 */
	private String genetrateDisplayQueryTitle(WorkflowItem workflowItem)
	{
		return workflowItem.getQuery().getName();
	}

	private void setOperatorList(List<String> opretionList, List<String> operatorsList)
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

	/**
	 * generates operand List.
	 * @param operandList operand List
	 * @param operandsList operands List
	 */
	private void setoperandList(List<String> operandList, List<Long> operandsList)
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

	/**
	 * generates Operator And OperandList For Query.
	 * @param operatorsList operators List
	 * @param operandsList operands List
	 * @param abstractQuery Query
	 */

	private void generateOperatorAndOperandList(List<String> operatorsList,
			List<Long> operandsList, IAbstractQuery abstractQuery)
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

	/**
	 * generates Operator And OperandList For CompositeQuery.
	 * @param operatorsList operators List
	 * @param operandsList operands List
	 * @param abstractQuery Query
	 */
	private void generateOperatorAndOperandListForCompositeQuery(List<String> operatorsList,
			List<Long> operandsList, IAbstractQuery abstractQuery)
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

	/**This method set Operation For Composite Query
	 * @param operation = operation on the CQ
	 * @return operation object according to the String of operation to perform
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

	/**This method returns the short operation for hte given operation.
	 * @param operation = operation on the CQ
	 * @return operation object according to the String of operation to perform
	 */
	private String setShortOperation(IOperation operation)
	{
		String shortOpr = null;
		if (operation instanceof Union)
		{
			shortOpr = "+";
		}
		else if (operation instanceof Intersection)
		{
			shortOpr = "*";
		}
		else if (operation instanceof Minus)
		{
			shortOpr = "-";
		}
		return shortOpr;
	}

	/**
	 * @return identifier
	 */
	public String[] getIdentifier()
	{
		String[] identifier = this.identifier;
		return identifier;
	}

	/**
	 * @param identifier identifier array
	 */
	public void setIdentifier(String[] identifier)
	{
		String[] temp = new String[identifier.length];
		System.arraycopy(identifier, 0, temp, 0, identifier.length);
		this.identifier = temp;
	}

	@Override
	public void setAddNewObjectIdentifier(String addNewFor, Long addObjectIdentifier)
	{
		// TODO Auto-generated method stub
	}

}
