package edu.wustl.query.actionForm;

import java.util.Iterator;
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

	public void setDisplayQueryTitle(String[] displayQueryTitle)
	{
		this.displayQueryTitle = displayQueryTitle;
	}

	/**
	 * @param queryTypeControl=array containing get count or composite query 
	 */
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
	
	/**
	 * @return queryTitle of queries array selected from the workflow popup
	 */
	public String[] getQueryTitle()
	{
		return queryTitle;
	}
	
	public void setQueryTitle(String[] queryTitle)
	{
		this.queryTitle = queryTitle;
	}

	/**
	 * @return queryType of queries array selected from the workflow popup
	 */
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

	/**
	 * @return operands array.underscore seperated for each row of table 
	 */
	public String[] getSelectedqueryId()
	{
		return selectedqueryId;
	}

	public void setSelectedqueryId(String[] selectedqueryId)
	{
		this.selectedqueryId = selectedqueryId;
	}

	/**
	 * @return number of checkboxes created
	 */
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
		LinkedList<String>  opretionList=new LinkedList<String>();
		LinkedList<String>  operandList=new LinkedList<String>();

		this.id = workflow.getId();
		this.name = Utility.toString(workflow.getName());
		List<WorkflowItem>  workflowItemList=workflow.getWorkflowItemList();
		
		String[] displayQueryTitle=new String[workflowItemList.size()];//  display titile
		for(int i=0;i<workflowItemList.size();i++)// (WorkflowItem workflowItem: workflowItemList)
		{
			WorkflowItem workflowItem=workflowItemList.get(i);
			LinkedList<String> operatorsList = new LinkedList<String>();
			LinkedList<Long> operandsList =new LinkedList<Long>();
			IAbstractQuery abstractQuery=workflowItem.getQuery();
			generateOperatorAndOperandList(operatorsList, operandsList, abstractQuery);	
			if(operandsList!=null)
			{	String  operandString="";
				for(Long operand:operandsList)			
				{
						operandString=operandString+operand+"_";
				}
				if(operandString!=null)
				{
					operandList.add(operandString.substring(0,operandString.lastIndexOf('_')));
				}
			}
			if(operatorsList!=null)
			{
				String  operationString="";
				
				for(String operation:operatorsList)
				{
					operationString=operationString+operation+"_";
				}
				if(operationString!=null&&!(operationString.equals("")))
				{
					opretionList.add(operationString.substring(0,operationString.lastIndexOf('_')));
				}
			}
			//genetrateDisplayQueryTitle(operandsList,operatorsList,displayQueryTitle,i);

		}
		
		String[] operands=new String[operandList.size()];//oprands array
		String[] operators=new String[opretionList.size()];//operator array 
		//initialize operator and operand array 
		opretionList.toArray(operators);
		operandList.toArray(operands);
		genetrateDisplayQueryTitle(operators,operands,displayQueryTitle);

		//starts TO DO query type value retrieve .....currently hard coded
		// Commented as not needed
		 String[] queryTypeControl=new String[workflowItemList.size()];
		 String[] displayQueryType=new String[workflowItemList.size()];
		createQueryTitle(workflowItemList.size(), queryTypeControl, displayQueryType);
		this.queryType=queryType;
		//ends
		// starts selectedqueryId same as operandsArray 
		String[] selectedqueryId=operands;
		this.selectedqueryId=selectedqueryId;
		this.displayQueryTitle=displayQueryTitle;
		this.queryTypeControl=queryTypeControl;
		this.displayQueryType=displayQueryType;
		this.operands=operands;
		this.operators=operators;
		boolean[] chkbox=new boolean[workflowItemList.size()];
		this.chkbox=chkbox;

	}

	/*
	 * */
	private void createQueryTitle(int size, String[] queryTypeControl,
			String[] displayQueryType)
	{
		for(int i=0;i<size;i++)
		{
			queryTypeControl[i]="Get Count";
			displayQueryType[i]="Get Count";
		}
	}

	private void genetrateDisplayQueryTitle(String[] operators, String[] operands,
			String[] displayQueryTitle)
	{
		for(int i=0;i<operators.length;i++)
		{
			String[] operatorsInRow=operators[i].split("_");
			String[] operandsInRow=operands[i].split("_");
			if(operandsInRow.length==1)//PQ
			{
				displayQueryTitle[i]=operandsInRow[0];
			}
			else
			{
				String queryConst="[Query";
				String queryTitle="";
				int j=0;
				for(;j<operatorsInRow.length;j++)
				{
					queryTitle= queryTitle+queryConst +" : " +operandsInRow[j] + "]"  + operatorsInRow[j];
				}
				displayQueryTitle[i]=queryTitle+queryConst +" : " +operandsInRow[j] + "]";
			}
		}
	}

	/**
	 * 
	 * @param operandsList
	 * @param operatorsList
	 * @param diplayQueryTitle
	 * generates the List of  displayQueryTitle
	 */
	private void genetrateDisplayQueryTitle(LinkedList<Long> operandsList,
			LinkedList<String> operatorsList, String[] diplayQueryTitle,int i)
	{
		String queryTitle="";
		Iterator<Long> operandsIter=operandsList.iterator();
		Iterator<String> operatorsIter=operatorsList.iterator();
		while(operandsIter.hasNext())
		{
			if(operatorsList.size()==1) //for PQ one operation     //(operatorsIter.hasNext()) 
			{
				//queryTitle=queryTitle+String.valueOf(operandsIter.next())+" : "+  operatorsIter.next();
				queryTitle=queryTitle + String.valueOf(operandsIter.next());//only one element for PQ
			}
			else if(operatorsIter.hasNext())
			{
				queryTitle= queryTitle + String.valueOf(operandsIter.next()) + " : " +  operatorsIter.next() +" ";
				
			}
			else if(operatorsList.size()!=1)
			{
				queryTitle=queryTitle + String.valueOf(operandsIter.next());
			}
		}
		diplayQueryTitle[i]=queryTitle;

	}


	private void generateOperatorAndOperandList(LinkedList<String> operatorsList, LinkedList<Long> operandsList,
			IAbstractQuery abstractQuery)
	{
		if(abstractQuery instanceof  ParameterizedQuery)
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
		if(abstractQuery  instanceof CompositeQuery)
		{
			CompositeQuery compositeQuery=(CompositeQuery)abstractQuery;
			IOperation operation=compositeQuery.getOperation();
			String operationName = setOperationForCompositeQuery(operation);
			generateOperatorAndOperandListForCompositeQuery(operatorsList,operandsList,
			operation.getOperandOne());
			generateOperatorAndOperandListForCompositeQuery(operatorsList,operandsList,
			operation.getOperandTwo());
			operatorsList.add(operationName);
		}
		else
		{
			operandsList.add(abstractQuery.getId());
		}
	}

	/**
	 * @param operation= operation on the CQ
	 * @return operation object according to the String of operation to perform
	 */
	private String setOperationForCompositeQuery(IOperation operation)
	{
		String operationName = new String();
		if(operation instanceof Union)
		{
			operationName= "Union";
		}
		else if (operation  instanceof Intersection)
		{
			operationName="Intersection";
		}
		else if (operation instanceof Minus)
		{
			operationName="Minus";
		}
		return operationName;
	}

}
