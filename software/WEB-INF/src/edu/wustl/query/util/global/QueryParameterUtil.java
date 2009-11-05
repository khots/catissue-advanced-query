package edu.wustl.query.util.global;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.wustl.common.querysuite.queryobject.IArithmeticOperand;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IExpressionOperand;
import edu.wustl.common.querysuite.queryobject.IParameter;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IRule;
import edu.wustl.common.querysuite.queryobject.ITerm;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.querysuite.queryobject.impl.DateLiteral;
import edu.wustl.common.querysuite.queryobject.impl.DateOffsetLiteral;
import edu.wustl.common.querysuite.queryobject.impl.NumericLiteral;
import edu.wustl.query.util.querysuite.QueryModuleConstants;

/**
 * @author baljeet_dhindhwal
 * This class deals with removing empty conditions and parameters
 * from a query before execution
 */

public class QueryParameterUtil
{
	/**
	 * @author baljeet_dhindhwal
	 * This method removes the empty conditions from IQuery before execution.
	 * @param query  Query Object
	 */
	public static void removeEmptyParameters(final IParameterizedQuery query)
	{
		//Now remove the empty conditions from the IQuery
		final IConstraints constraints = query.getConstraints();
		List <ICustomFormula> emptyCustomFormulasList = new ArrayList<ICustomFormula>();
		for (final IExpression expression : constraints)
		{
			for (final IExpressionOperand expressionOperand : expression)
			{
				if (expressionOperand instanceof IRule)
				{
					final IRule iRule = (IRule) expressionOperand;
					final List<ICondition> removalConditionList = new ArrayList<ICondition>();
					//For each expression operand of type rule, iterate over the IConditions
					removeRuleIFCondition(iRule, removalConditionList);

					// Now remove the removal condition list
					removeConditionList(iRule, removalConditionList);
				}
				else if(expressionOperand instanceof ICustomFormula)
				{
					ICustomFormula customFormula = (ICustomFormula)expressionOperand;
					getEmptyCustomFromulaList(emptyCustomFormulasList, customFormula);
				}
			}
		}
		//Now we have find out all the empty custom formulas
		removeEmptyCustomFormulas(query, emptyCustomFormulasList);
	}

	/**
	 * This method adds custom formula to List if any of the RHS
	 * value is empty
	 * @param emptyCustomFormulasList List that needs to be populated
	 * @param customFormula Custom Formula that is checked for rhs value
	 */
	private static void getEmptyCustomFromulaList(List<ICustomFormula> emptyCustomFormulasList,
			ICustomFormula customFormula)
	{
		RelationalOperator operator = customFormula.getOperator();
		List <ITerm> allRhsList = customFormula.getAllRhs();
		if(operator.getStringRepresentation().equalsIgnoreCase(QueryModuleConstants.Between)
				&& allRhsList.size() == 2)
		{
			//If this is between operator,it will have two RHSs
			//So check if any of the RHS value is empty or null
			ITerm  term = null;
			//Now check if term is empty
			term = allRhsList.get(0);
			boolean  isFirstTermEmpty = isCfOperandEmpty(term.getOperand(0));
			term = allRhsList.get(1);
			boolean isSecondTermEmpty = isCfOperandEmpty(term.getOperand(0));
			if(isFirstTermEmpty || isSecondTermEmpty)
			{
				emptyCustomFormulasList.add(customFormula);
			}
		}
		else
		{
			ITerm  term = null;
			//Now check if term is empty
			term = allRhsList.get(0);
			boolean  isFirstTermEmpty = isCfOperandEmpty(term.getOperand(0));
			if(isFirstTermEmpty)
			{
				emptyCustomFormulasList.add(customFormula);
			}
		}
	}

	/**
	 * This method removes the empty custom formula from query
	 * @param query : Parameterized Query Instance
	 * @param emptyCustomFormulasList : LIst of empty custom formulas
	 */
	private static void removeEmptyCustomFormulas(IParameterizedQuery query,
			List<ICustomFormula> emptyCustomFormulasList)
	{
		ICustomFormula remaovableCf = null;
		final IConstraints constraints = query.getConstraints();
		final List<IParameter<?>> parameters = query.getParameters();
		if(!emptyCustomFormulasList.isEmpty())
		{
			for(int j=0; j<emptyCustomFormulasList.size(); j++)
			{
				remaovableCf = emptyCustomFormulasList.get(j);
				for (IExpression expression : constraints)
				{
					if(expression.containsOperand(remaovableCf))
					{
						expression.removeOperand(remaovableCf);
						removeParameter(remaovableCf, parameters);
						break;
					}
				}
			}
		}
	}
	/**
	 * This method removes the parameter if a custom formula is removed
	 * from Query and that custom formula is parameterized
	 * @param removableCf : Custom Formula to be removed
	 * @param parameters : List of Query Parameters
	 */
	private static void  removeParameter(ICustomFormula removableCf,List<IParameter<?>> parameters)
	{
		IParameter<?>removableParameter = null;
		for (IParameter<?> parameter : parameters)
		{
			if (parameter.getParameterizedObject() instanceof ICustomFormula)
			{
				ICustomFormula paramCustomFormula =
				(ICustomFormula) parameter.getParameterizedObject();
		    	if(paramCustomFormula.getId().longValue() == removableCf.getId().longValue())
		    	{
		    		removableParameter = parameter;
		    		break;
		    	}
			 }
		 }
		if(removableParameter != null)
		{
			parameters.remove(removableParameter);
		}
	}

	/**
	 * This method check if the custom formula operand is empty
	 * @param operand : Custom Formula Operand
	 * @return  boolean indicating whether custom formula
	 * operand is empty
	 */
	private static boolean isCfOperandEmpty(IArithmeticOperand operand)
	{
		boolean isCustomFormulaEmpty = false;
		String timeValue = "";
		if(operand!=null)
		{
			if (operand instanceof DateOffsetLiteral)
			{
				DateOffsetLiteral dateOffSetLit = (DateOffsetLiteral) operand;
				timeValue = dateOffSetLit.getOffset();
			}
			else if (operand instanceof DateLiteral)
			{
				DateLiteral dateLit = (DateLiteral) operand;
				 if(dateLit.getDate() == null)
				 {
					 timeValue = "";
				 }
				 else
				 {
					 SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
					 timeValue = formatter.format(dateLit.getDate());
				 }
			}
			else
			{
				NumericLiteral literal = (NumericLiteral)operand;
				timeValue = literal.getNumber();
			}
		}
		if(timeValue == null || timeValue.equals(""))
		{
			isCustomFormulaEmpty = true;
		}
		return isCustomFormulaEmpty;
	}

	/**
	 *  Now remove the removal condition list.
	 * @param iRule rule
	 * @param removalConditionList list of conditions to be removed
	 */
	private static void removeConditionList(final IRule iRule, final List<ICondition> removalConditionList)
	{
		if (!removalConditionList.isEmpty())
		{
			for (final ICondition removalCondition : removalConditionList)
			{
				iRule.removeCondition(removalCondition);
			}
		}
	}

	/**
	 * For each expression operand of type rule, iterate over the IConditions.
	 * @param iRule rule
	 * @param removalConditionList list of conditions to be removed
	 */
	private static void removeRuleIFCondition(final IRule iRule, final List<ICondition> removalConditionList)
	{
		for (final ICondition codition : iRule)
		{
			final String conditionValue = codition.getValue();
			if (conditionValue != null && conditionValue.equalsIgnoreCase(""))
			{
				removalConditionList.add(codition);
			}
		}
	}
}
