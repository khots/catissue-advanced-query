
package edu.wustl.common.query.category;

import java.util.List;

import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.queryobject.ArithmeticOperator;
import edu.wustl.common.querysuite.queryobject.IArithmeticOperand;
import edu.wustl.common.querysuite.queryobject.IConnector;
import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IDateOffsetLiteral;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IExpressionOperand;
import edu.wustl.common.querysuite.queryobject.IOutputTerm;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.IRule;
import edu.wustl.common.querysuite.queryobject.ITerm;
import edu.wustl.common.querysuite.queryobject.TimeInterval;

public class CategoryProcessorUtility
{

	/**
	 * It will check weather the query("original query") is composed of category or not.
	 * if it is composed of category it will return true else false.
	 * @param query object
	 * @return true if query is on category else false.
	 */
	public static boolean isCategoryQuery(IQuery query)
	{
		boolean isCategory = false;
		for (IExpression expression : query.getConstraints())
		{
			if (expression.getQueryEntity().getDynamicExtensionsEntity().isCategory())
			{
				isCategory = true;
				break;
			}
		}
		return isCategory;
	}

	/**
	 * It will return the Rule present in the Expression.
	 * @param expression
	 * @return
	 */
	public static IRule getRule(IExpression expression)
	{
		IRule rule = null;
		for (IExpressionOperand operand : expression)
		{
			if (operand instanceof IRule)
			{
				rule = (IRule) operand;
			}
		}
		return rule;
	}

	/**
	 * It will create the Rhs from the oldCustomFormuala which is same as the oldCustomFormula's RHS.
	 * and add it to the newCustomFormula
	 * @param oldCustomFormula
	 * @param newCustomFormula
	 * @return The Rhs Term created For new Custom Formula 
	 */
	public static void addOldRhsToNewCustomFormula(ICustomFormula oldCustomFormula,
			ICustomFormula newCustomFormula)
	{

		for (ITerm rhs : oldCustomFormula.getAllRhs())
		{
			ITerm newRHs = QueryObjectFactory.createTerm();
			newRHs.addOperand(rhs.getOperand(0));
			newCustomFormula.addRhs(newRHs);
		}

	}

	/**
	 * It will form the newCustomFormulaby adding the  lhsOperand1 ,lhsOperand2, taking the rhs and Operator from the oldCustomFormula
	 * @param oldCustomFormula from which new Custom Formula is to form.
	 * @param lhsOperand1 lhs operand which is to be added in the newCustomFormula
	 * @param lhsOperand2 lhs operand which is to be added in the newCustomFormula
	 * @return new Custom Formula formed from the old one.
	 */
	public static ICustomFormula formNewCustomFormula(ICustomFormula oldCustomFormula,
			IArithmeticOperand lhsOperand1, IArithmeticOperand lhsOperand2)
	{
		ITerm newLhs = createNewLhsTerm(lhsOperand1, lhsOperand2, oldCustomFormula.getLhs()
				.getConnector(0, 1));
		ICustomFormula newCustomFormula = QueryObjectFactory.createCustomFormula();
		newCustomFormula.setLhs(newLhs);
		addOldRhsToNewCustomFormula(oldCustomFormula, newCustomFormula);
		newCustomFormula.setOperator(oldCustomFormula.getOperator());
		return newCustomFormula;
	}

	/**
	 * It will create the Term which will act as lhs in the new Custom Formula.
	 * @param lhsOperand1 which is to be added in the lhs Term
	 * @param lhsOperand2 which is to be added in the lhs Term
	 * @param connector which is present between the above two operands.
	 * @return Lhs Term created from the given parameters. 
	 */
	private static ITerm createNewLhsTerm(IArithmeticOperand lhsOperand1,
			IArithmeticOperand lhsOperand2, IConnector<ArithmeticOperator> connector)
	{
		ITerm newLhs = QueryObjectFactory.createTerm();
		newLhs.addOperand(lhsOperand1);
		newLhs.addOperand(connector, lhsOperand2);
		return newLhs;
	}

	/**
	 * Creates output terms and adds it to Query. This will display temporal columns in results.
	 * @param query object
	 * @param customFormula ICustomFormula
	 * @param customColumnName column name
	 */
	public static void addOutputTermsToQuery(IQuery query, ICustomFormula customFormula,
			String customColumnName)
	{
		IOutputTerm outputTerm = QueryObjectFactory.createOutputTerm();
		outputTerm.setTerm(customFormula.getLhs());
		List<ITerm> allRhs = customFormula.getAllRhs();
		String timeIntervalName = "";
		for (ITerm rhs : allRhs)
		{
			IArithmeticOperand operand = rhs.getOperand(0);
			if (operand instanceof IDateOffsetLiteral)
			{
				IDateOffsetLiteral dateOffLit = (IDateOffsetLiteral) operand;
				TimeInterval<?> timeInterval = dateOffLit.getTimeInterval();
				outputTerm.setTimeInterval(timeInterval);
				timeIntervalName = timeInterval.name();
			}
		}
		String tqColumnName = customColumnName + " (" + timeIntervalName + ")";
		outputTerm.setName(tqColumnName);
		query.getOutputTerms().add(outputTerm);
	}
}
