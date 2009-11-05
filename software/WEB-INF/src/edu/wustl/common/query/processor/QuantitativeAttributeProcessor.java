
package edu.wustl.common.query.processor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IExpressionAttribute;
import edu.wustl.common.querysuite.queryobject.IExpressionOperand;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.IRule;
import edu.wustl.common.querysuite.queryobject.ITerm;
import edu.wustl.common.querysuite.queryobject.LogicalOperator;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * This class processes the conditions on quantitative attributes
 * and modifies the query with new conditions. Also the quantitative
 * attributes in output attributes list of query are processed.
 * @author rukhsana_sameer
 *
 */
public class QuantitativeAttributeProcessor
{

	/**
	 * Query object to be processed for quantitative conditions.
	 */
	private final IQuery query;

	/**
	 * to process quantitative conditions.
	 */
	private final QuantitativeConditionProcessor conditionProcessor;

	/**
	 *
	 * @param query object to be processed for quantitative conditions.
	 */
	public QuantitativeAttributeProcessor(IQuery query)
	{
		this.query = query;
		this.conditionProcessor = new QuantitativeConditionProcessor();
	}

	/**
	 * This method processes the query for quantitative attributes.
	 * @return modified query
	 * @throws QueryModuleException query exception
	 */
	public IQuery processQuery() throws QueryModuleException
	{
		IConstraints constraints = query.getConstraints();
		List<IExpression> allExpressions = getAllExpressions(constraints);
		List<ICustomFormula> customFormulas = new ArrayList<ICustomFormula>();
		List<ICustomFormula> deletedCustomFormulas = new ArrayList<ICustomFormula>();
		for (IExpression expression : allExpressions)
		{
			customFormulas.clear();
			deletedCustomFormulas.clear();
			Iterator<IExpressionOperand> iterator = expression.iterator();
			while (iterator.hasNext())
			{
					IExpressionOperand operand = iterator.next();
					if(operand instanceof ICustomFormula)
					{
						customFormulas = processQuantitativeAttributes
							(deletedCustomFormulas, operand);
					}
					if (operand instanceof IRule)
					{
						IRule rule = (IRule) operand;
						addQuantitativeConditions(rule);
					}
			}
			modifyCustomFormulas(customFormulas, deletedCustomFormulas, expression);
		}
		return query;
	}
	/**
	 * This method adds the new custom formulas in IQuery and deletes the old
	 * from query object.
	 * @param customFormulas list of custom formula to be added
	 * @param deletedCustomFormulas list of custom formulas to be deleted
	 * @param expression in which custom formulas are added/deleted
	 */
	private void modifyCustomFormulas(List<ICustomFormula> customFormulas,
			List<ICustomFormula> deletedCustomFormulas, IExpression expression)
	{
		for (ICustomFormula formula : deletedCustomFormulas)
		{
			expression.removeOperand(formula);
		}
		for (ICustomFormula formula : customFormulas)
		{
			expression.addOperand(QueryObjectFactory
					.createLogicalConnector(LogicalOperator.And), formula);
		}
	}

	/**
	 * This method processes quantitative attributes in temporal conditions.
	 * @param deletedCustomFormulas list of old custom formulas that needs to be deleted from
	 * query object.
	 * @param operand custom formula
	 * @return list of new custom formulas
	 */
	private List<ICustomFormula> processQuantitativeAttributes(
			List<ICustomFormula> deletedCustomFormulas, IExpressionOperand operand)
	{
		List<ICustomFormula> customFormulas = new ArrayList<ICustomFormula>();
		ICustomFormula customFormula = (ICustomFormula)operand;
		ITerm lhs = customFormula.getLhs();
		if(lhs.getOperand(0) instanceof IExpressionAttribute)
		{
			IExpressionAttribute operand1 = (IExpressionAttribute)lhs.getOperand(0);
			QueryableAttributeInterface attribute = operand1.getAttribute();
			if(attribute.isTagPresent(Constants.TAG_QUANTITATIVE_ATTRIBUTE))
			{
				customFormulas = addQuantitativeConditionsForTemporal(customFormula);
				deleteOldFormula(deletedCustomFormulas, customFormula);
			}
		}
		return customFormulas;
	}

	/**
	 * This method creates a list of custom formulas that needs to be deleted from query.
	 * @param deletedCustomFormulas list of custom formulas
	 * @param customFormula to check the operator in custom formula.
	 */
	private void deleteOldFormula(List<ICustomFormula> deletedCustomFormulas,
			ICustomFormula customFormula)
	{
		if(customFormula.getOperator().equals(RelationalOperator.Equals)
				|| customFormula.getOperator().equals(RelationalOperator.Between))
		{
			deletedCustomFormulas.add(customFormula);
		}
	}

	/**
	 * This method creates new custom formulas that needs to be added in query for
	 * temporal conditions.
	 * @param customFormula in IQuery
	 * @return list of new custom formulas.
	 */
	private List<ICustomFormula> addQuantitativeConditionsForTemporal(ICustomFormula customFormula)
	{
		ITerm lhs = customFormula.getLhs();
		IExpressionAttribute operand1 = (IExpressionAttribute) lhs.getOperand(0);
		QueryableAttributeInterface attribute = operand1.getAttribute();
		List<ICustomFormula> newCustomFormulas = new ArrayList<ICustomFormula>();
		if (attribute.isTagPresent(Constants.TAG_QUANTITATIVE_ATTRIBUTE))
		{
			IExpressionAttribute operand2 = (IExpressionAttribute) lhs.getOperand(1);
			QueryableAttributeInterface attribute2 = operand2.getAttribute();
			// get low and high attributes for first quantitative attribute
			conditionProcessor.setLowHighFirstAttribute(attribute);
			// get low and high attributes for second quantitative attribute
			conditionProcessor.setLowHighSecondAttribute(attribute2);
			newCustomFormulas = conditionProcessor.getConditionsForTemporal(customFormula);
		}
		return newCustomFormulas;
	}

	/**
	 * This method creates a new expression and maintains map for attributeVsExpression.
	 * @param rule to modify
	 */
	private void addQuantitativeConditions(IRule rule)
	{
		Iterator<ICondition> iterator = rule.iterator();
		List<ICondition> newConditions = new ArrayList<ICondition>();
		while (iterator.hasNext())
		{
			ICondition condition = iterator.next();
			if (condition.getAttribute().isTagPresent(
					Constants.TAG_QUANTITATIVE_ATTRIBUTE))
			{
				 newConditions.addAll(conditionProcessor.getNewConditions(condition, condition
						.getAttribute().getActualEntity()));
				 iterator.remove();
			}
		}
		for (ICondition newCondition : newConditions)
		{
			rule.addCondition(newCondition);
		}
	}


	/**
	 * This method gets all expressions in the query.
	 * @param constraints of query object.
	 * @return list of expressions.
	 */
	private List<IExpression> getAllExpressions(IConstraints constraints)
	{
		List<IExpression> allExpression = new ArrayList<IExpression>();
		for (IExpression expression : constraints)
		{
			allExpression.add(expression);
		}
		return allExpression;
	}

}