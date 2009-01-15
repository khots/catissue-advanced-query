/**
 * 
 */
package edu.wustl.common.query.impl;

import java.util.Map.Entry;

import edu.common.dynamicextensions.exception.DynamicExtensionsSystemException;
import edu.wustl.common.query.impl.predicate.PredicateGenerator;
import edu.wustl.common.query.impl.predicate.Predicates;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;



/**
 * @author juberahamad_patel
 *
 */
public abstract class PassTwoXQueryGenerator extends XQueryGenerator
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
		xqueryForClause.append(Constants.QUERY_FOR);

		for (Entry<IExpression, String> entry : forVariables.entrySet())
		{
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
			if(predicates != null)
			{
				xqueryForClause.append('[').append(predicates.assemble()).append(']');
			}
			
			xqueryForClause.append(Constants.QUERY_COMMA);
		}

		Utility.removeLastComma(xqueryForClause);
		return xqueryForClause.toString();
		
	}
	
		
	
	
}
