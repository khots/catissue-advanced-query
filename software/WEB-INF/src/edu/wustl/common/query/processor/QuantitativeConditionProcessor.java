package edu.wustl.common.query.processor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableObjectInterface;
import edu.wustl.common.querysuite.queryobject.ArithmeticOperator;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IExpressionAttribute;
import edu.wustl.common.querysuite.queryobject.INumericLiteral;
import edu.wustl.common.querysuite.queryobject.ITerm;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.query.util.global.Constants;

/**
 * This class processes the conditions for quantitative attributes.
 * Creates new conditions / custom formulas with modified
 * conditions for quantitative attributes.
 * @author rukhsana_sameer
 *
 */
public class QuantitativeConditionProcessor
{
	/**
	 * Queryable attribute for low of first attribute.
	 */
	private QueryableAttributeInterface minQueryableAttribute1 = null;
	/**
	 * Queryable attribute for high of first attribute.
	 */
	private QueryableAttributeInterface maxQueryableAttribute1 = null;
	/**
	 * Queryable attribute for low of second attribute.
	 */
	private QueryableAttributeInterface minQueryableAttribute2 = null;
	/**
	 * Queryable attribute for low of second attribute.
	 */
	private QueryableAttributeInterface maxQueryableAttribute2 = null;

	/**
	 * This method creates new rule after processing quantitative conditions.
	 * @param condition old condition.
	 * @param targetEntity quantitative entity.
	 * @return rule
	 */
	public List<ICondition> getNewConditions(ICondition condition, QueryableObjectInterface targetEntity)
	{
		Collection<QueryableAttributeInterface> entityAttributesForQuery = targetEntity
				.getEntityAttributesForQuery();
		for (QueryableAttributeInterface attribute : entityAttributesForQuery)
		{
			if (attribute.isTagPresent(Constants.TAG_MIN_RANGE))
			{
				minQueryableAttribute1 = attribute;
			}
			if (attribute.isTagPresent(Constants.TAG_MAX_RANGE))
			{
				maxQueryableAttribute1 = attribute;
			}
		}
		List<ICondition> newConditions = processQuantitativeConditions(condition);

		return newConditions;
	}

	/**
	 * This method processes conditions on quantitative attributes.
	 * @param condition old condition
	 * @return list of conditions
	 * TODO convert if else to switch statement
	 */
	private List<ICondition> processQuantitativeConditions(ICondition condition)
	{
		List<ICondition> newConditions = new ArrayList<ICondition>();
		ICondition newCondition = null;
		switch(condition.getRelationalOperator())
		{
			case LessThan:
				newCondition = QueryObjectFactory.createCondition(minQueryableAttribute1,
						RelationalOperator.LessThan, condition.getValues());
				newConditions.add(newCondition);
				break;
			case GreaterThan:
				newCondition = QueryObjectFactory.createCondition(maxQueryableAttribute1,
						RelationalOperator.GreaterThan, condition.getValues());
				newConditions.add(newCondition);
				break;
			case Equals:
				getConditionsForEquals(condition,newConditions);
				break;
			case GreaterThanOrEquals:
				newCondition = QueryObjectFactory.createCondition(maxQueryableAttribute1,
						RelationalOperator.GreaterThanOrEquals, condition.getValues());
				newConditions.add(newCondition);
				break;
			case LessThanOrEquals:
				newCondition = QueryObjectFactory.createCondition(minQueryableAttribute1,
						RelationalOperator.LessThanOrEquals, condition.getValues());
				newConditions.add(newCondition);
				break;
			case Between:
				getConditionsForBetween(condition, newConditions);
				break;
		}
		return newConditions;
	}

	/**
	 * This method creates new conditions when operator is Between.
	 * @param condition old condition in IQuery
	 * @param newConditions list of new conditions to be added.
	 */
	private void getConditionsForBetween(ICondition condition, List<ICondition> newConditions)
	{
		ICondition newCondition;
		List<String> firstValue = new ArrayList<String>();
		firstValue.add(condition.getValues().get(0));
		newCondition = QueryObjectFactory.createCondition(maxQueryableAttribute1,
				RelationalOperator.GreaterThanOrEquals, firstValue);
		newConditions.add(newCondition);

		List<String> secondValue = new ArrayList<String>();
		secondValue.add(condition.getValues().get(1));
		newCondition = QueryObjectFactory.createCondition(minQueryableAttribute1,
				RelationalOperator.LessThanOrEquals, secondValue);
		newConditions.add(newCondition);
	}
	/**
	 * This method creates new conditions when operator is Equals.
	 * @param condition old condition in IQuery.
	 * @param newConditions list of new conditions to be added.
	 */
	private void getConditionsForEquals(ICondition condition, List<ICondition> newConditions)
	{
		ICondition newCondition;
		newCondition = QueryObjectFactory.createCondition(minQueryableAttribute1,
				RelationalOperator.LessThanOrEquals, condition.getValues());
		newConditions.add(newCondition);
		newCondition = QueryObjectFactory.createCondition(maxQueryableAttribute1,
				RelationalOperator.GreaterThanOrEquals, condition.getValues());
		newConditions.add(newCondition);
	}
	/**
	 * This method sets the low and high attributes of second attribute in
	 * temporal condition.
	 * @param attribute2 second attribute.
	 */
	public void setLowHighSecondAttribute(QueryableAttributeInterface attribute2)
	{
		Collection<QueryableAttributeInterface> entityAttributesForQuery2 = attribute2
				.getQueryEntity().getEntityAttributesForQuery();
		for (QueryableAttributeInterface quantitativeAttribute : entityAttributesForQuery2)
		{
			if (quantitativeAttribute.isTagPresent(Constants.TAG_MIN_RANGE))
			{
				minQueryableAttribute2 = quantitativeAttribute;
			}
			if (quantitativeAttribute.isTagPresent(Constants.TAG_MAX_RANGE))
			{
				maxQueryableAttribute2 = quantitativeAttribute;
			}
		}
	}

	/**
	 * This method sets the low and high attributes of first attribute in
	 * temporal condition.
	 * @param attribute first attribute.
	 */
	public void setLowHighFirstAttribute(QueryableAttributeInterface attribute)
	{
		Collection<QueryableAttributeInterface> entityAttributesForQuery = attribute
				.getQueryEntity().getEntityAttributesForQuery();
		for (QueryableAttributeInterface quantitativeAttribute : entityAttributesForQuery)
		{
			if (quantitativeAttribute.isTagPresent(Constants.TAG_MIN_RANGE))
			{
				minQueryableAttribute1 = quantitativeAttribute;
			}
			if (quantitativeAttribute.isTagPresent(Constants.TAG_MAX_RANGE))
			{
				maxQueryableAttribute1 = quantitativeAttribute;
			}
		}
	}
	/**
	 * This method gets the new conditions for temporal conditions.
	 * @param customFormula old custom formula.
	 * @return list of custom formulas.
	 */
	public List<ICustomFormula> getConditionsForTemporal(ICustomFormula customFormula)
	{
		ITerm lhs = customFormula.getLhs();
		IExpressionAttribute operand1 = (IExpressionAttribute) lhs.getOperand(0);
		IExpressionAttribute operand2 = (IExpressionAttribute) lhs.getOperand(1);
		List<ICustomFormula> newCustomFormulas = new ArrayList<ICustomFormula>();
		if (operatorLessThanOrLessThanEquals(customFormula))
		{
			getConditionsLessThanAndLessThanEqualTo(customFormula,operand1, operand2);
		}
		else if (greaterThanOrGreaterThanEquals(customFormula))
		{
			getConditionsGreaterThanAndGreaterThanEqualTo(customFormula,operand1, operand2);
		}
		else if (customFormula.getOperator().equals(RelationalOperator.Equals))
		{
			newCustomFormulas = getConditionsEqual(customFormula, operand1, operand2);
		}
		else if (customFormula.getOperator().equals(RelationalOperator.Between))
		{
			newCustomFormulas = getConditionsBetween(customFormula, operand1, operand2);
		}
		return newCustomFormulas;
	}
	/**
	 * To check if operator is > or >=.
	 * @param customFormula in query
	 * @return true if operator > or >=
	 */
	private boolean greaterThanOrGreaterThanEquals(ICustomFormula customFormula)
	{
		return customFormula.getOperator().equals(RelationalOperator.GreaterThan)
				|| customFormula.getOperator().equals(RelationalOperator.GreaterThanOrEquals);
	}

	/**
	 * To check if operator is < or <=.
	 * @param customFormula in query
	 * @return true if operator is < or <=
	 */
	private boolean operatorLessThanOrLessThanEquals(ICustomFormula customFormula)
	{
		return customFormula.getOperator().equals(RelationalOperator.LessThan)
				|| customFormula.getOperator().equals(RelationalOperator.LessThanOrEquals);
	}

	/**
	 * This method return new conditions in case of Between operator.
	 * @param customFormula in IQuery
	 * @param operand1 first operand in lhs.
	 * @param operand2 second operand in lhs.
	 * @return list of custom formula
	 */
	private List<ICustomFormula> getConditionsBetween(ICustomFormula customFormula,
			IExpressionAttribute operand1, IExpressionAttribute operand2)
	{
		ITerm lhs = customFormula.getLhs();
		List<ICustomFormula> newCustomFormulas = null;
		int index1 = lhs.indexOfOperand(operand1);
		int index2 = lhs.indexOfOperand(operand2);
		if (lhs.getConnector(index1, index2).getOperator().equals(ArithmeticOperator.Minus))
		{
			newCustomFormulas =getCustomFormulasBetweenMinus(customFormula, operand1, operand2);
		}
		else if (lhs.getConnector(index1, index2).getOperator().equals(ArithmeticOperator.Plus))
		{
			newCustomFormulas = getCustomFormulasBetweenplus(customFormula, operand1, operand2);
		}
		return newCustomFormulas;
	}

	/**
	 * This method return new conditions in case of Between operator with Plus logical connector.
	 * @param customFormula old custom formula in query.
	 * @param operand1 first operand in lhs.
	 * @param operand2 second operand in lhs.
	 * @return list of custom formulas.
	 */
	private List<ICustomFormula> getCustomFormulasBetweenplus(ICustomFormula customFormula,
			IExpressionAttribute operand1, IExpressionAttribute operand2)
	{
		List<ICustomFormula> newCustomFormulas = new ArrayList<ICustomFormula>();
		ITerm newLhs = getFirstFormulaLhsPlus(operand1, operand2);
		List<ITerm> allRhs = customFormula.getAllRhs();
		ITerm term = allRhs.get(0);
		ITerm term1 = allRhs.get(1);
		String secondValue = ((INumericLiteral) term1.getOperand(0)).getNumber();
		INumericLiteral secondLiteral = QueryObjectFactory
				.createNumericLiteral(secondValue);
		ITerm rhs = QueryObjectFactory.createTerm();
		rhs.addOperand(secondLiteral);
		ICustomFormula formula = createCustomFormula(newLhs,rhs,RelationalOperator.LessThanOrEquals);
		newCustomFormulas.add(formula);

		ITerm secondLhs =getSecondFormulaLhsPlus(operand1, operand2);
		String firstValue = ((INumericLiteral) term.getOperand(0)).getNumber();
		INumericLiteral firstLiteral = QueryObjectFactory
				.createNumericLiteral(firstValue);
		ITerm secondRhs = QueryObjectFactory.createTerm();
		secondRhs.addOperand(firstLiteral);
		ICustomFormula formula1 = createCustomFormula(secondLhs, secondRhs,
				RelationalOperator.GreaterThanOrEquals);
		newCustomFormulas.add(formula1);
		return newCustomFormulas;
	}

	/**
	 * This method return new conditions in case of Between operator with Minus logical connector.
	 * @param customFormula old custom formula in query.
	 * @param operand1 first operand in lhs.
	 * @param operand2 second operand in lhs.
	 * @return list of custom formulas.
	 */
	private List<ICustomFormula> getCustomFormulasBetweenMinus(ICustomFormula customFormula,
			IExpressionAttribute operand1, IExpressionAttribute operand2)
	{
		List<ICustomFormula> newCustomFormulas = new ArrayList<ICustomFormula>();
		ITerm newLhs = getSecondFormulaLhs(operand1, operand2);
		List<ITerm> allRhs = customFormula.getAllRhs();
		ITerm term = allRhs.get(0);
		ITerm term1 = allRhs.get(1);
		String firstValue = ((INumericLiteral) term.getOperand(0)).getNumber();
		INumericLiteral firstLiteral = QueryObjectFactory
				.createNumericLiteral(firstValue);
		ITerm rhs = QueryObjectFactory.createTerm();
		rhs.addOperand(firstLiteral);
		ICustomFormula formula = createCustomFormula(newLhs, rhs,RelationalOperator.GreaterThanOrEquals);
		newCustomFormulas.add(formula);

		ITerm secondLhs = getFirstFormulaLhs(operand1, operand2);
		String secondValue = ((INumericLiteral) term1.getOperand(0)).getNumber();
		INumericLiteral secondLiteral = QueryObjectFactory
		.createNumericLiteral(secondValue);
		ITerm secondRhs = QueryObjectFactory.createTerm();
		secondRhs.addOperand(secondLiteral);
		ICustomFormula formula1 = createCustomFormula(secondLhs, secondRhs,
				RelationalOperator.LessThanOrEquals);
		newCustomFormulas.add(formula1);
		return newCustomFormulas;
	}

	/**
	 * This method creates lhs for second custom formula for logical connector in Plus.
	 * @param operand1 first operand attribute.
	 * @param operand2 second operand attribute.
	 * @return new lhs.
	 */
	private ITerm getSecondFormulaLhsPlus(IExpressionAttribute operand1,
			IExpressionAttribute operand2)
	{
		ITerm secondLhs = QueryObjectFactory.createTerm();
		IExpressionAttribute firstHigh = QueryObjectFactory
				.createExpressionAttribute(operand1.getExpression(),
						maxQueryableAttribute1);
		secondLhs.addOperand(firstHigh);
		IExpressionAttribute secondHigh = QueryObjectFactory
				.createExpressionAttribute(operand2.getExpression(),
						maxQueryableAttribute2);
		secondLhs.addOperand(QueryObjectFactory
				.createArithmeticConnector(ArithmeticOperator.Plus), secondHigh);
		return secondLhs;
	}

	/**
	 * This method return new conditions in case of Equals operator.
	 * @param customFormula in IQuery
	 * @param operand1 first operand in lhs.
	 * @param operand2 second operand in lhs.
	 * @return list of custom formula
	 */
	private List<ICustomFormula> getConditionsEqual(ICustomFormula customFormula,
			IExpressionAttribute operand1, IExpressionAttribute operand2)
	{
		ITerm lhs = customFormula.getLhs();
		List<ICustomFormula> newCustomFormulas = null;
		int index1 = lhs.indexOfOperand(operand1);
		int index2 = lhs.indexOfOperand(operand2);
		if (lhs.getConnector(index1, index2).getOperator().equals(ArithmeticOperator.Minus))
		{
			newCustomFormulas = getCustomFormulasEqualMinus(customFormula, operand1, operand2);
		}
		else if (lhs.getConnector(index1, index2).getOperator().equals(ArithmeticOperator.Plus))
		{
			newCustomFormulas = getCustomFormulasEqualPlus(customFormula, operand1, operand2);
		}
		return newCustomFormulas;
	}

	/**
	 * This method return new conditions in case of Equals operator with Plus logical connector.
	 * @param customFormula old custom formula in query.
	 * @param operand1 first operand in lhs.
	 * @param operand2 second operand in lhs.
	 * @return list of custom formulas.
	 */
	private List<ICustomFormula> getCustomFormulasEqualPlus(ICustomFormula customFormula,
			IExpressionAttribute operand1, IExpressionAttribute operand2)
	{
		List<ICustomFormula> newCustomFormulas = new ArrayList<ICustomFormula>();
		ITerm newLhs = getFirstFormulaLhsPlus(operand1, operand2);
		List<ITerm> allRhs = customFormula.getAllRhs();
		ITerm term = allRhs.get(0);
		String number = ((INumericLiteral) term.getOperand(0)).getNumber();
		INumericLiteral intLiteral = QueryObjectFactory.createNumericLiteral(number);
		ITerm rhs = QueryObjectFactory.createTerm();
		rhs.addOperand(intLiteral);
		ICustomFormula formula = createCustomFormula(newLhs, rhs,RelationalOperator.LessThanOrEquals);
		newCustomFormulas.add(formula);

		ITerm secondLhs = getSecondFormulaRhsPlus(operand1, operand2);
		INumericLiteral intLiteral2 = QueryObjectFactory.createNumericLiteral(number);
		ITerm secondRhs = QueryObjectFactory.createTerm();
		secondRhs.addOperand(intLiteral2);
		ICustomFormula formula1 = createCustomFormula(secondLhs, secondRhs,
				RelationalOperator.GreaterThanOrEquals);
		newCustomFormulas.add(formula1);
		return newCustomFormulas;
	}

	/**
	 * This method return new conditions in case of Equals operator with Minus logical connector.
	 * @param customFormula old custom formula in query.
	 * @param operand1 first operand in lhs.
	 * @param operand2 second operand in lhs.
	 * @return list of custom formulas.
	 */
	private List<ICustomFormula> getCustomFormulasEqualMinus(ICustomFormula customFormula,
			IExpressionAttribute operand1, IExpressionAttribute operand2)
	{
		List<ICustomFormula> newCustomFormulas = new ArrayList<ICustomFormula>();
		ITerm newLhs = getFirstFormulaLhs(operand1, operand2);
		List<ITerm> allRhs = customFormula.getAllRhs();
		ITerm term = allRhs.get(0);
		String number = ((INumericLiteral) term.getOperand(0)).getNumber();
		INumericLiteral intLiteral = QueryObjectFactory.createNumericLiteral(number);
		ITerm rhs = QueryObjectFactory.createTerm();
		rhs.addOperand(intLiteral);
		ICustomFormula formula = createCustomFormula(newLhs, rhs,RelationalOperator.LessThanOrEquals);
		newCustomFormulas.add(formula);

		ITerm secondLhs = getSecondFormulaLhs(operand1, operand2);
		INumericLiteral intLiteral2 = QueryObjectFactory.createNumericLiteral(number);
		ITerm secondRhs = QueryObjectFactory.createTerm();
		secondRhs.addOperand(intLiteral2);
		ICustomFormula formula1 = createCustomFormula(secondLhs, secondRhs,
				RelationalOperator.GreaterThanOrEquals);
		newCustomFormulas.add(formula1);
		return newCustomFormulas;
	}

	/**
	 * This method creates lhs for second custom formula for logical connector in Plus.
	 * @param operand1 first operand attribute.
	 * @param operand2 second operand attribute.
	 * @return new lhs.
	 */
	private ITerm getSecondFormulaRhsPlus(IExpressionAttribute operand1,
			IExpressionAttribute operand2)
	{
		ITerm secondLhs = QueryObjectFactory.createTerm();
		IExpressionAttribute firstHigh = QueryObjectFactory
				.createExpressionAttribute(operand1.getExpression(),
						maxQueryableAttribute1);
		secondLhs.addOperand(firstHigh);
		IExpressionAttribute highQAformula = QueryObjectFactory
				.createExpressionAttribute(operand2.getExpression(),
						maxQueryableAttribute2);
		secondLhs.addOperand(QueryObjectFactory
				.createArithmeticConnector(ArithmeticOperator.Plus), highQAformula);
		return secondLhs;
	}

	/**
	 * This method creates lhs for first custom formula for logical connector in Plus.
	 * @param operand1 first operand attribute.
	 * @param operand2 second operand attribute.
	 * @return new lhs.
	 */
	private ITerm getFirstFormulaLhsPlus(IExpressionAttribute operand1, IExpressionAttribute operand2)
	{
		ITerm newLhs = QueryObjectFactory.createTerm();
		IExpressionAttribute firstLow = QueryObjectFactory.createExpressionAttribute(
				operand1.getExpression(), minQueryableAttribute1);
		newLhs.addOperand(firstLow);
		IExpressionAttribute secondLow = QueryObjectFactory.createExpressionAttribute(
				operand2.getExpression(), minQueryableAttribute2);
		newLhs.addOperand(QueryObjectFactory
				.createArithmeticConnector(ArithmeticOperator.Plus), secondLow);
		return newLhs;
	}

	/**
	 * This method creates lhs for second custom formula for logical connector in Minus.
	 * @param operand1 first operand attribute.
	 * @param operand2 second operand attribute.
	 * @return new lhs.
	 */
	private ITerm getSecondFormulaLhs(IExpressionAttribute operand1, IExpressionAttribute operand2)
	{
		ITerm lhs2 = QueryObjectFactory.createTerm();
		IExpressionAttribute highQAformula2 = QueryObjectFactory
				.createExpressionAttribute(operand1.getExpression(),
						maxQueryableAttribute1);
		lhs2.addOperand(highQAformula2);
		IExpressionAttribute lowQAformula2 = QueryObjectFactory
				.createExpressionAttribute(operand2.getExpression(),
						minQueryableAttribute2);
		lhs2.addOperand(QueryObjectFactory
				.createArithmeticConnector(ArithmeticOperator.Minus), lowQAformula2);
		return lhs2;
	}

	/**
	 * This method creates lhs for first custom formula for logical connector in Minus.
	 * @param operand1 first operand attribute.
	 * @param operand2 second operand attribute.
	 * @return new lhs.
	 */
	private ITerm getFirstFormulaLhs(IExpressionAttribute operand1, IExpressionAttribute operand2)
	{
		ITerm newLhs = QueryObjectFactory.createTerm();
		IExpressionAttribute firstLow = QueryObjectFactory.createExpressionAttribute(
				operand1.getExpression(), minQueryableAttribute1);
		newLhs.addOperand(firstLow);
		IExpressionAttribute secondHigh = QueryObjectFactory.createExpressionAttribute(
				operand2.getExpression(), maxQueryableAttribute2);
		newLhs.addOperand(QueryObjectFactory
				.createArithmeticConnector(ArithmeticOperator.Minus), secondHigh);
		return newLhs;
	}

	/**
	 * This method creates new custom formula.
	 * @param newLhs lhs in custom formula.
	 * @param rhs rhs in custom formula.
	 * @param operator relational operator
	 * @return CustomFormula
	 */
	private ICustomFormula createCustomFormula(ITerm newLhs, ITerm rhs, RelationalOperator operator)
	{
		ICustomFormula formula = QueryObjectFactory.createCustomFormula();
		formula.setLhs(newLhs);
		formula.getAllRhs().clear();
		formula.addRhs(rhs);
		formula.setOperator(operator);
		return formula;
	}

	/**
	 * This method creates new custom formula in case of operator > or >=.
	 * @param customFormula to be modified
	 * @param operand1 first operand in lhs
	 * @param operand2 second operand in lhs
	 */
	private void getConditionsGreaterThanAndGreaterThanEqualTo(ICustomFormula customFormula,
			IExpressionAttribute operand1, IExpressionAttribute operand2)
	{
		ITerm lhs = customFormula.getLhs();
		ITerm lhs1 = QueryObjectFactory.createTerm();
		IExpressionAttribute highQA = QueryObjectFactory.createExpressionAttribute(operand1
				.getExpression(), maxQueryableAttribute1);
		lhs1.addOperand(highQA);
		int index1 = lhs.indexOfOperand(operand1);
		int index2 = lhs.indexOfOperand(operand2);
		if (lhs.getConnector(index1, index2).getOperator().equals(ArithmeticOperator.Minus))
		{
			IExpressionAttribute lowQA2 = QueryObjectFactory.createExpressionAttribute(
					operand2.getExpression(), minQueryableAttribute2);
			lhs1.addOperand(QueryObjectFactory
					.createArithmeticConnector(ArithmeticOperator.Minus), lowQA2);
		}
		if (lhs.getConnector(index1, index2).getOperator().equals(ArithmeticOperator.Plus))
		{
			IExpressionAttribute highQA2 = QueryObjectFactory.createExpressionAttribute(
					operand2.getExpression(), maxQueryableAttribute2);
			lhs1.addOperand(QueryObjectFactory
					.createArithmeticConnector(ArithmeticOperator.Plus), highQA2);
		}
		customFormula.setLhs(lhs1);
	}

	/**
	 * This method creates new custom formula in case of operator Less Than or
	 * Less Than Equal To.
	 * @param customFormula to be modified
	 * @param operand1 first operand in lhs
	 * @param operand2 second operand in lhs
	 */
	private void getConditionsLessThanAndLessThanEqualTo(ICustomFormula customFormula,
			IExpressionAttribute operand1, IExpressionAttribute operand2)
	{
		ITerm newLhs = QueryObjectFactory.createTerm();
		ITerm lhs = customFormula.getLhs();
		IExpressionAttribute firstLow = QueryObjectFactory.createExpressionAttribute(operand1
				.getExpression(), minQueryableAttribute1);
		newLhs.addOperand(firstLow);
		int index1 = lhs.indexOfOperand(operand1);
		int index2 = lhs.indexOfOperand(operand2);
		if (lhs.getConnector(index1,index2).getOperator().equals(ArithmeticOperator.Minus))
		{
			IExpressionAttribute secondHigh = QueryObjectFactory.createExpressionAttribute(
					operand2.getExpression(), maxQueryableAttribute2);
			newLhs.addOperand(QueryObjectFactory
					.createArithmeticConnector(ArithmeticOperator.Minus), secondHigh);
		}
		if (lhs.getConnector(index1,index2).getOperator().equals(ArithmeticOperator.Plus))
		{
			IExpressionAttribute secondLow = QueryObjectFactory.createExpressionAttribute(
					operand2.getExpression(), minQueryableAttribute2);
			newLhs.addOperand(QueryObjectFactory
					.createArithmeticConnector(ArithmeticOperator.Plus), secondLow);
		}
		customFormula.setLhs(newLhs);
	}
}
