
package edu.wustl.common.query.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import edu.common.dynamicextensions.domaininterface.AssociationInterface;
import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.common.dynamicextensions.exception.DynamicExtensionsApplicationException;
import edu.common.dynamicextensions.exception.DynamicExtensionsSystemException;
import edu.wustl.common.querysuite.exceptions.CyclicException;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.metadata.associations.IIntraModelAssociation;
import edu.wustl.common.querysuite.queryobject.ArithmeticOperator;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IConnector;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IExpressionOperand;
import edu.wustl.common.querysuite.queryobject.IJoinGraph;
import edu.wustl.common.querysuite.queryobject.IOperand;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.IQueryEntity;
import edu.wustl.common.querysuite.queryobject.IRule;
import edu.wustl.common.querysuite.queryobject.LogicalOperator;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.querysuite.queryobject.impl.Expression;
import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * Mock to create XQuery Objects.
 * 
 * @author ravindra_jain, juber patel
 * @created 4th December, 2008
 * 
 */
public class IQueryGenerator
{

	public static XQueryEntityManagerMock entityManager = new XQueryEntityManagerMock();

	/**
	 * To create IQuery for Person as:
	 * [Person Upi is not null]
	 * 
	 * @param expression The Expression reference created by function
	 * @return The corresponding join Graph reference.
	 */
	public static IQuery createQuery1()
	{
		IQuery query = null;
		try
		{
			query = QueryObjectFactory.createQuery();
			IConstraints constraints = QueryObjectFactory.createConstraints();
			query.setConstraints(constraints);

			IExpression expression = createExpression(constraints, XQueryEntityManagerMock.PERSON);
			addCondition(expression, "personUpi", "not", "null");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return query;
	}

	private static void addCondition(IExpression expression, String attributeName, String operator,
			String rhs)
	{
		AttributeInterface attribute = findAttribute(expression.getQueryEntity()
				.getDynamicExtensionsEntity(), attributeName);
		ICondition condition = createCondition(attribute, operator, rhs);
		getRule(expression).addCondition(condition);
	}

	/**
	 * @param attribute
	 * @param operator
	 * @param rhs
	 * @return
	 */
	private static ICondition createCondition(AttributeInterface attribute, String operator,
			String rhs)
	{
		ICondition condition = QueryObjectFactory.createCondition();
		condition.setRelationalOperator(RelationalOperator.valueOf(operator));
		condition.setAttribute(attribute);
		return condition;
	}

	public static IExpression createExpression(IConstraints constraints, String expressionType)
			throws DynamicExtensionsSystemException
	{
		EntityInterface entity = entityManager.getEntityByName(expressionType);
		IQueryEntity queryEntity = QueryObjectFactory.createQueryEntity(entity);
		IExpression expression = constraints.addExpression(queryEntity);
		expression.setInView(true);

		List<ICondition> conditions = new ArrayList<ICondition>();
		IRule rule = QueryObjectFactory.createRule(conditions);
		expression.addOperand(rule);

		return expression;
	}

	public static IRule getRule(IExpression expression)
	{
		Iterator<IExpressionOperand> iterator = expression.iterator();
		while (iterator.hasNext())
		{
			IExpressionOperand operand = iterator.next();
			if (operand instanceof IRule)
			{
				return (IRule) operand;
			}
		}

		return null;
	}

	public static void connectExpressions(IExpression source, IExpression target,
			LogicalOperator logicalOperator, IJoinGraph joinGraph)
			throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException,
			CyclicException
	{
		IConnector<LogicalOperator> connector = QueryObjectFactory
				.createLogicalConnector(logicalOperator);

		source.addOperand(connector, target);

		// Add association to Join graph.
		AssociationInterface association = getAssociation(entityManager.getAssociations(source
				.getQueryEntity().getDynamicExtensionsEntity().getId(), target.getQueryEntity()
				.getDynamicExtensionsEntity().getId()), target.getQueryEntity()
				.getDynamicExtensionsEntity());
		IIntraModelAssociation imAssociation = QueryObjectFactory
				.createIntraModelAssociation(association);
		joinGraph.putAssociation(source, target, imAssociation);

	}
	
	
	public static AssociationInterface getAssociation(
			Collection<AssociationInterface> associations, EntityInterface targetEntity)
	{
		for (AssociationInterface theAssociation : associations)
		{
			if (theAssociation == null || theAssociation.getTargetEntity() == null)
			{
				System.out.println("FFOOOOO");
			}
			if (theAssociation.getTargetEntity().equals(targetEntity))
			{
				return theAssociation;
			}
		}

		return null;
	}
	
	
	/**
	 * To search attribute in the Entity.
	 * 
	 * @param entity The Dynamic Extension Entity
	 * @param attributeName The name of the attribute to search.
	 * @return The corresponding attribute.
	 */
	private static AttributeInterface findAttribute(EntityInterface entity, String attributeName)
	{
		Collection<AttributeInterface> attributes = entity.getEntityAttributesForQuery();
		for (AttributeInterface attribute : attributes)
		{
			if (attribute.getName().equals(attributeName))
				return attribute;
		}
		return null;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
