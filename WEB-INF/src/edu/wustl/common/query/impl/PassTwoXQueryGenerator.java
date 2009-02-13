/**
 * 
 */

package edu.wustl.common.query.impl;

import java.util.Map.Entry;

import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.exception.DataTypeFactoryInitializationException;
import edu.common.dynamicextensions.exception.DynamicExtensionsSystemException;
import edu.wustl.common.query.impl.predicate.AbstractPredicate;
import edu.wustl.common.query.impl.predicate.PredicateGenerator;
import edu.wustl.common.query.impl.predicate.Predicates;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IParameter;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;

/**
 * @author juberahamad_patel
 *
 */
public class PassTwoXQueryGenerator extends AbstractXQueryGenerator
{

	/**
	 * 
	 * @param predicateGenerator 
	 * @return the For Clause of XQuery
	 * @throws MultipleRootsException
	 * @throws DynamicExtensionsSystemException
	 */
	protected String buildXQueryForClause(PredicateGenerator predicateGenerator)
			throws MultipleRootsException, DynamicExtensionsSystemException
	{
		StringBuilder xqueryForClause = new StringBuilder(512);
		String variable = null;

		for (Entry<IExpression, String> entry : getForVariables().entrySet())
		{
			xqueryForClause.append(Constants.QUERY_FOR);
			IExpression expression = entry.getKey();
			variable = entry.getValue();
			if (getMainExpressions().contains(expression))
			{
				String tableName = expression.getQueryEntity().getDynamicExtensionsEntity()
						.getTableProperties().getName();

				StringBuilder rootPath = new StringBuilder().append(Constants.QUERY_XMLCOLUMN)
						.append(Constants.QUERY_OPENING_PARENTHESIS).append("\"").append(tableName)
						.append(Constants.QUERY_DOT).append(Constants.QUERY_XMLDATA).append("\"")
						.append(Constants.QUERY_CLOSING_PARENTHESIS).append('/');

				rootPath.append(expression.getQueryEntity().getDynamicExtensionsEntity().getName())
						.toString();

				xqueryForClause.append(variable).append(' ').append(Constants.IN).append(' ')
						.append(rootPath.toString());
			}
			else
			{
				IExpression parent = joinGraph.getParentList(expression).get(0);
				String parentPath  = getEntityPaths().get(parent);
				xqueryForClause.append(variable).append(' ').append(Constants.IN).append(' ');
				xqueryForClause.append(parentPath).append('/').append(
						getTargetRoles().get(expression)).append('/');
				String entityName = expression.getQueryEntity().getDynamicExtensionsEntity()
						.getName();
				xqueryForClause.append(deCapitalize(entityName));
			}

			Predicates predicates = predicateGenerator.getPredicates(expression);
			if (predicates != null)
			{
				insertParameters(predicates);
				xqueryForClause.append('[').append(predicates.assemble()).append(']');
			}

		}

		return xqueryForClause.toString();

	}

	/**
	 * replace parameter placeholders with parameter names
	 * @param predicates the predicates
	 */
	private void insertParameters(Predicates predicates)
	{
		for (Entry<IParameter<ICondition>, IExpression> entry : getParameters().entrySet())
		{
			AttributeInterface attribute = entry.getKey().getParameterizedObject().getAttribute();
			String attributeAlias = Utility.getAliasFor(attribute, entry.getValue());

			for (AbstractPredicate predicate : predicates.getPredicates())
			{
				if (attributeAlias.equals(predicate.getAttributeAlias()))
				{
					predicate.setRhs(Constants.QUERY_DOLLAR + attributeAlias);
				}
			}
		}
	}

	/**
	 *  @return the Return Clause of SQLXML
	 */
	protected String buildXQueryReturnClause()
	{
		StringBuilder xqueryReturnClause = new StringBuilder(Constants.QUERY_RETURN)
				.append(" <result> ");

		for (Entry<IOutputAttribute, String> entry : getAttributeAliases().entrySet())
		{
			xqueryReturnClause.append('<').append(entry.getValue()).append(">{");

			String expressionPath = getEntityPaths().get(entry.getKey().getExpression());
			xqueryReturnClause.append(expressionPath).append('/');
			xqueryReturnClause.append(entry.getKey().getAttribute().getName());

			xqueryReturnClause.append("}</").append(entry.getValue()).append('>');

		}

		xqueryReturnClause.append(" </result> ");
		return xqueryReturnClause.toString();

	}

	/* (non-Javadoc)
	 * @see edu.wustl.common.query.impl.AbstractXQueryGenerator#buildColumnsPart()
	 */
	@Override
	protected String buildColumnsPart() throws DataTypeFactoryInitializationException
	{
		StringBuilder columnsPart = new StringBuilder(" columns ");

		for (Entry<IOutputAttribute, String> entry : getAttributeAliases().entrySet())
		{
			columnsPart.append(entry.getValue());

			String dataType = getDataTypeInformation(entry.getKey().getAttribute());
			columnsPart.append(' ').append(dataType).append(" path '").append(entry.getValue())
					.append('/').append(entry.getKey().getAttribute().getName()).append('\'')
					.append(Constants.QUERY_COMMA);
		}

		Utility.removeLastComma(columnsPart);
		return columnsPart.toString();
	}

	/* (non-Javadoc)
	 * @see edu.wustl.common.query.impl.AbstractXQueryGenerator#buildPassingPart()
	 */
	@Override
	protected String buildPassingPart() throws DataTypeFactoryInitializationException
	{
		StringBuilder passingPart = new StringBuilder("passing ");

		for (Entry<IParameter<ICondition>, IExpression> entry : getParameters().entrySet())
		{
			AttributeInterface attribute = entry.getKey().getParameterizedObject().getAttribute();
			String alias = Utility.getAliasFor(attribute, entry.getValue());
			String dataType = getDataTypeInformation(attribute);
			passingPart.append(" cast (? as ").append(dataType).append(") as ").append("\"")
					.append(alias).append("\"");
		}

		return passingPart.toString();
	}

	/* (non-Javadoc)
	 * @see edu.wustl.common.query.impl.AbstractXQueryGenerator#buildXQueryLetClause(edu.wustl.common.query.impl.predicate.PredicateGenerator)
	 */
	@Override
	protected String buildXQueryLetClause(PredicateGenerator predicateGenerator)
	{
		return "";
	}

}
