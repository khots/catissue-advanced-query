/**
 */
package edu.wustl.query.bizlogic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IExpressionOperand;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IRule;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.query.util.global.AQConstants;

/**
 * This class contains the bizlogic to display the details of the query like query name, constraints,
 * export date in the exported csv file.
 * @author pooja_tavase
 *
 */
public class ExportQueryBizLogic
{
	/**
	 * Gathers the query details( query title, export date and constraint list)
	 * to be written in the exported csv file.
	 * @param query IParameterizedQuery object
	 * @param exportList The exportList
	 * @throws MultipleRootsException MultipleRootsException
	 */
	public void exportDetails(IParameterizedQuery query, List<List<String>> exportList)
	throws MultipleRootsException
	{
		StringBuffer queryTitle = new StringBuffer("Title : ");
		if(query.getName()== null)
		{
			queryTitle.append(" Unnamed");
		}
		else
		{
			queryTitle.append(' ').append(query.getName());
		}
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date date = new Date();
		StringBuffer exportDate = new StringBuffer("Export Date : ");
		exportDate.append(dateFormat.format(date));
		List<String> title = new ArrayList<String>();
		title.add(queryTitle.toString());
		List<String> dateList = new ArrayList<String>();
		dateList.add(exportDate.toString());

		List<String> constraintList = new ArrayList<String>();

		IConstraints constraints = query.getConstraints();
		IExpression expression = constraints.getRootExpression();
		StringBuffer formattedStr = new StringBuffer("Query Limits : ");
		populateConstraintList(expression, formattedStr);
		if(formattedStr.charAt(formattedStr.length()-AQConstants.TWO)==',')
		{
			formattedStr.deleteCharAt(formattedStr.length()-AQConstants.TWO);
		}
		constraintList.add(formattedStr.toString());
		exportList.add(constraintList);
		exportList.add(title);
		exportList.add(dateList);
	}

	/**
	 * Recursive method to populate the constraint list to be written in the exported csv file.
	 * @param expression The Expression
	 * @param formattedStr The StringBuffer object
	 */
	private void populateConstraintList(IExpression expression, StringBuffer formattedStr)
	{
		String entityName = expression.getQueryEntity().getDynamicExtensionsEntity().getName();
		entityName = entityName.substring(entityName.lastIndexOf('.')+1,entityName.length());
		formattedStr.append(" | ").append(entityName).append(" : ");
		int noOfRules = expression.numberOfOperands();
		if(noOfRules ==1 && expression.getOperand(0) instanceof IExpression)
		{
			formattedStr.append("");
		}
		else
		{
			formattedStr.append(" | ").append(entityName).append(" : ");
		}
		for(int i=0;i<noOfRules;i++)
		{
			IExpressionOperand operand = expression.getOperand(i);
			if (operand instanceof IRule)
			{
				IRule rule = (IRule) expression.getOperand(i);
				int totalConditions = rule.size();
				for(int j=0;j<totalConditions;j++)
				{
					ICondition condition = rule.getCondition(j);
					String formattedAttrNm = edu.wustl.cab2b.common.util.Utility.
					getFormattedString(condition.getAttribute().getName());
					formattedStr.append(formattedAttrNm).append(' ');
					List<String> values = condition.getValues();
					RelationalOperator operator = condition.getRelationalOperator();
					formattedStr.append(operator.getStringRepresentation()).append(' ');
					for(String value : values)
					{
						formattedStr.append(' ').append(value).append(" and");
					}
					if(!values.isEmpty())
					{
					  formattedStr.delete(formattedStr.lastIndexOf("a"),formattedStr.length());
					}
					formattedStr.append(", ");
				}
				if(totalConditions > 1)
				{
					formattedStr.deleteCharAt(formattedStr.lastIndexOf(","));
				}
            }
			else if (operand instanceof IExpression)
			{
				processForExpressions(formattedStr, operand);
            }
		}
	}

	/**
	 * @param string StringBuffer object.
	 * @param operand Operand
	 */
	private void processForExpressions(StringBuffer string, IExpressionOperand operand)
	{
		if(string.lastIndexOf(",") != -1)
		{
			string.deleteCharAt(string.lastIndexOf(","));
		}
		populateConstraintList((IExpression)operand, string);
	}
}
