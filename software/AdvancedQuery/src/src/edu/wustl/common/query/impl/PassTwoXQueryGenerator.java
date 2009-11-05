/**
 * 
 */

package edu.wustl.common.query.impl;

import java.util.Collection;
import java.util.HashSet;

import edu.common.dynamicextensions.domaininterface.TaggedValueInterface;
import edu.common.dynamicextensions.exception.DataTypeFactoryInitializationException;
import edu.common.dynamicextensions.exception.DynamicExtensionsSystemException;
import edu.wustl.common.query.exeptions.SQLXMLException;
import edu.wustl.common.query.impl.predicate.PredicateGenerator;
import edu.wustl.common.query.impl.predicate.Predicates;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.exceptions.SqlException;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IParameter;
import edu.wustl.query.util.global.Constants;

/**
 * @author juberahamad_patel
 *
 */
public class PassTwoXQueryGenerator extends AbstractXQueryGenerator
{

	public PassTwoXQueryGenerator()
	{
		super();
		setSelectDistinct(true);
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @param predicateGenerator  - PredicateGenerator Object
	 * @param expression - IExpression
	 * @return the For Clause of XQuery
	 * @throws MultipleRootsException - in case there are more then 1 root expression
	 * @throws DynamicExtensionsSystemException - DE exception
	 */
	protected String buildXQueryForClause(PredicateGenerator predicateGenerator,
			IExpression expression) throws MultipleRootsException, DynamicExtensionsSystemException
	{
		StringBuilder xqueryForClause = new StringBuilder(512);

		if (mainExpressions.contains(expression))
		{
			xqueryForClause.append(getForMainExpression(expression));

		}
		else
		{
			xqueryForClause.append(getForChildExpression(expression));
		}

		Predicates predicates = predicateGenerator.getPredicates(expression);
		if (predicates != null)
		{
			//			insertParameters(predicates,expression);
			xqueryForClause.append('[').append(predicates.assemble()).append(']');
		}

		xqueryForClause.append(getChildFor(predicateGenerator, expression));

		return xqueryForClause.toString();

	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @param expression - Child Expression
	 * @return for clause for child expression
	 */
	private String getForChildExpression(IExpression expression)
	{

		StringBuilder xqueryForClause = new StringBuilder(512);
		String variable = null;

		StringBuffer entityPath = new StringBuffer();
		xqueryForClause.append(Constants.QUERY_FOR);
		variable = getForVariables().get(expression);
		String entityName = expression.getQueryEntity().getDynamicExtensionsEntity().getName();
		entityPath.append(getTargetRoles().get(expression)).append('/').append(
				deCapitalize(entityName));
		IExpression parent = joinGraph.getParentList(expression).get(0);
		String parentPath = getEntityPaths().get(parent);
		xqueryForClause.append(variable).append(' ').append(Constants.IN).append(' ');
		xqueryForClause.append(parentPath).append('/').append(Constants.QUERY_OPENING_PARENTHESIS)
				.append(entityPath.toString()).append(Constants.QUERY_COMMA).append(".[not(")
				.append(entityPath.toString()).append(")]/<nothing/>").append(
						Constants.QUERY_CLOSING_PARENTHESIS);

		return xqueryForClause.toString();
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @param expression - Main Expression
	 * @return for clause for main Expression
	 */
	private String getForMainExpression(IExpression expression)
	{
		StringBuilder xqueryForClause = new StringBuilder(512);
		String variable = null;

		xqueryForClause.append(Constants.QUERY_FOR);
		variable = getForVariables().get(expression);

		StringBuilder rootPath = new StringBuilder().append(variable).append(
				Constants.QUERY_RELATIONAL).append('/');

		rootPath.append(expression.getQueryEntity().getDynamicExtensionsEntity().getName())
				.toString();

		xqueryForClause.append(variable).append(' ').append(Constants.IN).append(' ').append(
				rootPath.toString());

		return xqueryForClause.toString();
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @param predicateGenerator - PredicateGenerator
	 * @param expression - IExpression
	 * @return for clause for child expression
	 * @throws DynamicExtensionsSystemException - DE exception
	 * @throws MultipleRootsException - in case there are more then 1 root expression
	 */
	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	private String getChildFor(PredicateGenerator predicateGenerator, IExpression expression)
			throws DynamicExtensionsSystemException, MultipleRootsException
	{
		StringBuilder newXQueryForClause = new StringBuilder();
		Collection<IExpression> childExpression = getNonMainChildren(expression);
		for (IExpression child : childExpression)
		{
			if (forVariables.containsKey(child))
			{
				newXQueryForClause.append(buildXQueryForClause(predicateGenerator, child));
			}

		}
		return newXQueryForClause.toString();

	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @param expression - IExpression
	 *  @return the Return Clause of SQLXML
	 */
	protected String buildXQueryReturnClause(IExpression expression)
	{
		StringBuffer xqueryReturnClause = new StringBuffer();

		xqueryReturnClause.append(getReturnClause(expression));
		xqueryReturnClause.append(getChildReturnAttributes(expression, ""));
		xqueryReturnClause.insert(0, Constants.QUERY_RETURN + " <result> ");
		xqueryReturnClause.append(" </result> ");
		return xqueryReturnClause.toString();

	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @param expression - IExpression
	 * @return attributes of main expression
	 */

	private String getReturnClause(IExpression expression)
	{
		StringBuffer xqueryReturnClause = new StringBuffer();
		String expressionAlias = getAliasName(expression);
		HashSet<IOutputAttribute> attributeList = attributeExpressionMap.get(expressionAlias);

		for (IOutputAttribute attribute : attributeList)
		{
			xqueryReturnClause.append('<').append(attribute.getAttribute().getName()).append('_')
					.append(expression.getExpressionId()).append(">{");

			String expressionPath = getEntityPaths().get(attribute.getExpression());
			xqueryReturnClause.append(expressionPath).append('/');
			xqueryReturnClause.append(attribute.getAttribute().getName());

			xqueryReturnClause.append("}</").append(attribute.getAttribute().getName()).append('_')
					.append(expression.getExpressionId()).append('>');

		}
		return xqueryReturnClause.toString();

	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @param expression - IExpression
	 * @param xqueryReturnClause - Return Clause
	 * @return attributes of child expression
	 */
	private String getChildReturnAttributes(IExpression expression, String xqueryReturnClause)
	{
		StringBuffer returnString = new StringBuffer(xqueryReturnClause);

		Collection<IExpression> childExpression;
		if (!(joinGraph.getChildrenList(expression)).isEmpty())
		{
			childExpression = joinGraph.getChildrenList(expression);

			for (IExpression child : childExpression)
			{
				if (mainExpressions.contains(child))
				{
					continue;
				}
				StringBuffer intermediateString = new StringBuffer();
				intermediateString.append(getReturnClause(child));
				returnString.append(getChildReturnAttributes(child, intermediateString.toString()));
			}
		}
		return returnString.toString();
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @param predicateGenerator - PredicateGenerator
	 * @param expression - IExpression
	 * @return XQuery String
	 * @throws SQLXMLException - Exception
	 * @throws DynamicExtensionsSystemException - Exception
	 * @throws MultipleRootsException - Exception
	 * @throws SqlException - Exception
	 * building XQuery for Pass Two Generator
	 */
	@Override
	protected String buildXQuery(PredicateGenerator predicateGenerator, IExpression expression)
			throws SQLXMLException, DynamicExtensionsSystemException, MultipleRootsException,
			SqlException
	{

		StringBuilder xQuery = new StringBuilder(1024);

		xQuery.append(buildXQueryForClause(predicateGenerator, expression));
		xQuery.append(buildXQueryReturnClause(expression));

		return xQuery.toString();
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * to get the columns clause of XQuery
	 * @param attribute - IOutputAttribute
	 * @param expression - IExpression
	 * @return Columns Clause
	 * @throws DataTypeFactoryInitializationException - ExceptionIExpression
	 */
	protected String getColumnsClause(IOutputAttribute attribute, IExpression expression)
			throws DataTypeFactoryInitializationException
	{
		StringBuilder columnsPart = new StringBuilder();

		columnsPart.append(attribute.getAttribute().getName() + "_" + expression.getExpressionId());

		String dataType = getDataTypeInformation(attribute.getAttribute());
		columnsPart.append(' ').append(dataType).append(Constants.QUERY_PATH).append(" '").append(
				attribute.getAttribute().getName() + "_" + expression.getExpressionId())
				.append('/').append(attribute.getAttribute().getName()).append('\'').append(
						Constants.QUERY_COMMA);

		return columnsPart.toString();

	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * Build the where part for temporal query.
	 * @param predicateGenerator
	 * @return where part string
	 */
	@Override
	protected String getParametrizedCondition()
	{
		StringBuffer parametrizedCondition = new StringBuffer();
		for (IExpression expression : getParameters().keySet())
		{
			HashSet<IParameter<ICondition>> parameterSet;
			parameterSet = parameters.get(expression);
			for (IParameter<ICondition> parameter : parameterSet)
			{
				if (parameter.getParameterizedObject().getAttribute().isTagPresent(
						Constants.TAG_PARTITIONED_ATTRIBUTE))
				{
					String attributeName = "";
					QueryableAttributeInterface attribute = parameter.getParameterizedObject()
							.getAttribute();
					String expresionAlias = expression.getQueryEntity()
							.getDynamicExtensionsEntity().getName()
							+ '_' + expression.getExpressionId() + Constants.QUERY_RELATIONAL;
					attributeName = getAttributeName(attribute);
					String attributeAlias = expresionAlias + "." + attributeName;
					parametrizedCondition.append(attributeAlias).append(Constants.QUERY_EQUALS)
							.append('?');
					parametrizedCondition.append(Constants.QUERY_AND);
				}
			}
		}
		return parametrizedCondition.toString();

	}

	/**
	 * @param attribute - QueryableAttributeInterface
	 * @return - Attribute Name
	 */
	private String getAttributeName(QueryableAttributeInterface attribute)
	{
		String attributeName = "";
		Collection<TaggedValueInterface> values = attribute.getTaggedValueCollection();
		for (TaggedValueInterface value : values)
		{
			if (value.getKey().equals(Constants.TAGGED_VALUE_PRIMARY_KEY_VALUE))
			{
				attributeName = value.getValue();
			}
		}
		return attributeName;
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @param attribute - QueryableAttributeInterface
	 * @param expression - IExpression
	 * @return boolean representing parametrized attribute
	 */
	@Override
	protected boolean isParametrizedAttribute(QueryableAttributeInterface attribute,
			IExpression expression)
	{
		boolean checkParametrized = false;
		HashSet<IParameter<ICondition>> parameterSet;
		parameterSet = parameters.get(expression);
		if (parameterSet == null)
		{
			checkParametrized = false;
		}
		else
		{
			for (IParameter<ICondition> parameter : parameterSet)
			{
				QueryableAttributeInterface parameterAttribute = parameter.getParameterizedObject()
						.getAttribute();
				if (parameterAttribute.equals(attribute)
						&& parameterAttribute.isTagPresent(Constants.TAG_PARTITIONED_ATTRIBUTE))
				{
					checkParametrized = true;
				}
			}
		}
		return checkParametrized;
	}
}
