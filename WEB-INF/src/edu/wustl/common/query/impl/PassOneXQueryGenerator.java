/**
 * 
 */

package edu.wustl.common.query.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.common.dynamicextensions.exception.DataTypeFactoryInitializationException;
import edu.common.dynamicextensions.exception.DynamicExtensionsSystemException;
import edu.wustl.common.query.impl.predicate.PredicateGenerator;
import edu.wustl.common.query.impl.predicate.Predicates;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;

/**
 * @author juberahamad_patel
 *
 */
public class PassOneXQueryGenerator extends XQueryGenerator
{

	private Map<IExpression, String> passOneForVariables;

	public PassOneXQueryGenerator()
	{
		passOneForVariables = new LinkedHashMap<IExpression, String>();
	}

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

		StringBuilder laterPart = new StringBuilder(1024);
		StringBuilder xqueryForClause = new StringBuilder(1024);

		for (IExpression expression : mainExpressions)
		{
			String tableName = expression.getQueryEntity().getDynamicExtensionsEntity().getTableProperties().getName();

			StringBuilder rootPath = new StringBuilder().append(Constants.QUERY_XMLCOLUMN).append(
					Constants.QUERY_OPENING_PARENTHESIS).append("\"").append(tableName).append(
					Constants.QUERY_DOT).append(Constants.QUERY_XMLDATA).append("\"").append(
					Constants.QUERY_CLOSING_PARENTHESIS);

			String variable = appendChildren(expression, predicateGenerator, laterPart);

			xqueryForClause.append(Constants.QUERY_FOR).append(variable).append(' ').append(
					Constants.IN).append(' ').append(rootPath.toString()).append(
					laterPart.toString());

		}

		return xqueryForClause.toString();
	}

	private String appendChildren(IExpression expression, PredicateGenerator predicateGenerator,
			StringBuilder laterPart)
	{
		String targetRole = targetRoles.get(expression);

		if (targetRole != null)
		{
			laterPart.append('/').append(targetRole);
		}

		String entityName = expression.getQueryEntity().getDynamicExtensionsEntity().getName();

		if (!mainExpressions.contains(expression))
		{
			entityName = deCapitalize(entityName);
		}
		
		laterPart.append('/').append(entityName);

		if (dummyShouldAddAllDownstreamPredicatesHere(expression))
		{
			String localPredicates = getAllDownstreamPredicates(predicateGenerator, expression,
					"");
			laterPart.append('[').append(localPredicates).append(']');
			String variable = forVariables.get(expression);
			passOneForVariables.put(expression, variable);

			return variable;
		}
		else
		{
			Predicates predicates = predicateGenerator.getPredicates(expression);

			if (predicates != null)
			{
				laterPart.append('[').append(predicates.assemble()).append(']');
			}

			if (!joinGraph.getChildrenList(expression).isEmpty())
			{
				IExpression firstChild = joinGraph.getChildrenList(expression).get(0);
				return appendChildren(firstChild, predicateGenerator, laterPart);
			}
		}

		return null;
	}

	
	private String getAllDownstreamPredicates(PredicateGenerator predicateGenerator,
			IExpression expression, String prefix)
	{
		StringBuilder downStreamPredicates = new StringBuilder();
		Predicates localPredicates = predicateGenerator.getPredicates(expression);

		if (localPredicates == null)
		{
			return "";
		}

		downStreamPredicates.append(localPredicates.assemble(prefix)).append(Constants.QUERY_AND);

		List<IExpression> children = joinGraph.getChildrenList(expression);

		//end recursion
		if (children.isEmpty())
		{
			return Utility.removeLastAnd(downStreamPredicates.toString());
		}

		for (IExpression child : children)
		{
			String targetRole = targetRoles.get(child);
			String entityName = deCapitalize(child.getQueryEntity().getDynamicExtensionsEntity()
					.getName());
			StringBuilder newPrefix = new StringBuilder(prefix);

			if (targetRole != null)
			{
				newPrefix.append(targetRole).append('/');
			}

			newPrefix.append(entityName).append('/');
			downStreamPredicates.append(getAllDownstreamPredicates(predicateGenerator, child,
					newPrefix.toString()));
		}

		return downStreamPredicates.toString();
	}

	protected String buildXQueryLetClause(PredicateGenerator predicateGenerator)
	{
		StringBuilder xqueryLetClause = new StringBuilder();

		xqueryLetClause.append(Constants.QUERY_LET);

		for (Entry<IOutputAttribute, String> entry : attributeAliases.entrySet())
		{
			xqueryLetClause.append(Constants.QUERY_DOLLAR).append(entry.getValue()).append(" := ");

			IExpression expression = entry.getKey().getExpression();

			//traverse down the hierarchy of the expression to find an expresssion which is in passOneForVariables
			//this could also be theoritically up the hierarchy but not in our xmls

			StringBuilder relativePath = new StringBuilder();
			while (true)
			{
				if (passOneForVariables.containsKey(expression))
				{
					xqueryLetClause.append(passOneForVariables.get(expression)).append('/');
					xqueryLetClause.append(relativePath.toString()).append(
							entry.getKey().getAttribute().getName()).append(Constants.QUERY_COMMA);
					break;
				}

				expression = joinGraph.getChildrenList(expression).get(0);
				
				//additional "../" for entities having target role eg. demographicsCollection 
				if (targetRoles.containsKey(expression))
				{
					relativePath.append("../");
				}

				relativePath.append("../");
				
			}
		}

		Utility.removeLastComma(xqueryLetClause);
		return xqueryLetClause.toString();
	}

	/**
	 * 
	 * @return the Return Clause of SQLXML
	 */
	protected String buildXQueryReturnClause()
	{
		StringBuilder xqueryReturnClause = new StringBuilder(Constants.QUERY_RETURN);
		xqueryReturnClause.append("<return>");

		for (String attributeAlias : attributeAliases.values())
		{
			xqueryReturnClause.append('<').append(attributeAlias).append('>');
			xqueryReturnClause.append('{').append(Constants.QUERY_DOLLAR).append(attributeAlias)
					.append('}');
			xqueryReturnClause.append("</").append(attributeAlias).append('>');
		}

		xqueryReturnClause.append("</return>");
		return xqueryReturnClause.toString();
	}

	/**
	 * 
	 * @return Columns part of SQLXML
	 * @throws DataTypeFactoryInitializationException
	 */
	protected String buildColumnsPart() throws DataTypeFactoryInitializationException
	{
		StringBuilder columnsPart = new StringBuilder(512);
		columnsPart.append(" columns ");

		for (Entry<IOutputAttribute, String> entry : attributeAliases.entrySet())
		{
			String attributeAlias = entry.getValue();
			String dataType = getDataTypeInformation(entry.getKey().getAttribute());
			columnsPart.append(attributeAlias).append(' ').append(dataType).append(" path '")
					.append(attributeAlias).append("'").append(Constants.QUERY_COMMA);
		}

		Utility.removeLastComma(columnsPart);
		return columnsPart.toString();
	}

	@Override
	protected String buildPassingPart()
	{
		return "";
	}

	public boolean dummyShouldAddAllDownstreamPredicatesHere(IExpression expression)
	{
		if (expression.getQueryEntity().getDynamicExtensionsEntity().getName().equalsIgnoreCase(
				"Demographics"))
		{
			return true;
		}

		return false;
	}

}
