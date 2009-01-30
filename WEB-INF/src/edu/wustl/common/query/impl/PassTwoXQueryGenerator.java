/**
 * 
 */

package edu.wustl.common.query.impl;

import java.util.Set;
import java.util.Map.Entry;

import edu.common.dynamicextensions.exception.DataTypeFactoryInitializationException;
import edu.common.dynamicextensions.exception.DynamicExtensionsSystemException;
import edu.wustl.common.query.impl.predicate.PredicateGenerator;
import edu.wustl.common.query.impl.predicate.Predicates;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.query.util.global.Constants;

/**
 * @author juberahamad_patel
 *
 */
public class PassTwoXQueryGenerator extends AbstractXQueryGenerator
{

	private Set<IOutputAttribute> parametrizedAttributes;

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
		setParametrizedAttributes();
		StringBuilder xqueryForClause = new StringBuilder(512);

		for (Entry<IExpression, String> entry : forVariables.entrySet())
		{
			xqueryForClause.append(Constants.QUERY_FOR);
			IExpression expression = entry.getKey();
			String variable = entry.getValue();
			if (mainExpressions.contains(expression))
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
				xqueryForClause.append(variable).append(' ').append(Constants.IN).append(' ');
				xqueryForClause.append(entityPaths.get(expression)).append('/').append(
						targetRoles.get(expression)).append('/');
				xqueryForClause.append(expression.getQueryEntity().getDynamicExtensionsEntity()
						.getName());
			}

			Predicates predicates = predicateGenerator.getPredicates(expression);
			if (predicates != null)
			{
				xqueryForClause.append('[').append(predicates.assemble()).append(']');
			}

		}

		return xqueryForClause.toString();

	}

	private void setParametrizedAttributes()
	{
		// TODO Auto-generated method stub

	}

	/**
	 *  @return the Return Clause of SQLXML
	 */
	protected String buildXQueryReturnClause()
	{
		StringBuilder xqueryReturnClause = new StringBuilder(Constants.QUERY_RETURN)
				.append(" <result> ");

		for (Entry<IOutputAttribute, String> entry : attributeAliases.entrySet())
		{
			xqueryReturnClause.append('<').append(entry.getValue()).append(">{");

			String expressionPath = entityPaths.get(entry.getKey().getExpression());
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

		for (Entry<IOutputAttribute, String> entry : attributeAliases.entrySet())
		{
			columnsPart.append(entry.getValue());

			String dataType = getDataTypeInformation(entry.getKey().getAttribute());
			columnsPart.append(dataType).append(" path '").append(entry.getValue()).append('/')
					.append(entry.getKey().getAttribute().getName()).append('\'').append(
							Constants.QUERY_COMMA);
		}

		return columnsPart.toString();
	}

	/* (non-Javadoc)
	 * @see edu.wustl.common.query.impl.AbstractXQueryGenerator#buildPassingPart()
	 */
	@Override
	protected String buildPassingPart() throws DataTypeFactoryInitializationException
	{
		StringBuilder passingPart = new StringBuilder("passing ");

		for (IOutputAttribute attribute : parametrizedAttributes)
		{
			String dataType = getDataTypeInformation(attribute.getAttribute());
			passingPart.append(" cast (? as ").append(dataType).append(") as ").append(
					attribute.getAttribute().getName());
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
