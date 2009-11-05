/**
 * Package
 */
package edu.wustl.common.query.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.common.dynamicextensions.domaininterface.AssociationInterface;
import edu.common.dynamicextensions.domaininterface.TaggedValueInterface;
import edu.common.dynamicextensions.exception.DataTypeFactoryInitializationException;
import edu.common.dynamicextensions.exception.DynamicExtensionsSystemException;
import edu.wustl.common.query.exeptions.SQLXMLException;
import edu.wustl.common.query.impl.predicate.AbstractPredicate;
import edu.wustl.common.query.impl.predicate.PredicateGenerator;
import edu.wustl.common.query.impl.predicate.Predicates;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.exceptions.SqlException;
import edu.wustl.common.querysuite.metadata.associations.IAssociation;
import edu.wustl.common.querysuite.metadata.associations.IIntraModelAssociation;
import edu.wustl.common.querysuite.metadata.associations.impl.IntraModelAssociation;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableObjectInterface;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;

/**
 * @author juberahamad_patel
 *
 */

public class PassOneXQueryGenerator extends AbstractXQueryGenerator
{

	/**
	 * map of for variables and corresponding expressions created in pass one xquery.
	 */
	private Map<IExpression, String> passOneForVariables;


	/**
	 * Constructor.
	 */
	public PassOneXQueryGenerator()
	{
		super();
		passOneForVariables = new LinkedHashMap<IExpression, String>();
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * build the for clause of xquery.
	 * @param predicateGenerator - PredicateGenerator
	 * @param expression - IExpression
	 * @return the For Clause of XQuery
	 * @throws MultipleRootsException - in case an expression has multiple parents
	 * @throws DynamicExtensionsSystemException - DE exception
	 */
	private String buildXQueryForClause(PredicateGenerator predicateGenerator,
			IExpression expression) throws MultipleRootsException, DynamicExtensionsSystemException
	{

		StringBuilder xqueryForClause = new StringBuilder(1024);
		setPassOneForVariables(expression);

		StringBuilder laterPart = new StringBuilder(1024);

		StringBuilder rootPath = new StringBuilder().append(getAliasName(expression)).append(
				Constants.QUERY_RELATIONAL);

		appendChildren(expression, predicateGenerator, laterPart);

		xqueryForClause.append(Constants.QUERY_DOLLAR).append(rootPath.toString()).append(
				laterPart.toString());

		return xqueryForClause.toString();
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * set the for variables used in pass one xquery.
	 * the assumption is that pass one for variables are present among general for variables
	 * @param expression - IExpression
	 */
	private void setPassOneForVariables(IExpression expression)
	{
		QueryableObjectInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();
		if (entity.isTagPresent(Constants.VERSION))
		{
			String variable = getForVariables().get(expression);
			passOneForVariables.put(expression, variable);
		}

		for (IExpression child : joinGraph.getChildrenList(expression))
		{
			setPassOneForVariables(child);
		}
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * append xquery fragments in the for clause for given expression.
	 * and its children recursively
	 * @param expression - IExpression
	 * @param predicateGenerator - PredicateGenerator object
	 * @param laterPart - String buffer to be returned
	 */
	private void appendChildren(IExpression expression, PredicateGenerator predicateGenerator,
			StringBuilder laterPart)
	{
		String targetRole = getTargetRoles().get(expression);
		String entityName = "";

		laterPart.append(getTargetRole(targetRole));

		QueryableObjectInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();
		entityName = getEntityName(expression);

		laterPart.append('/').append(entityName);

		if (entity.isTagPresent(Constants.VERSION))
		{
			String localPredicates = getAllDownstreamPredicates(predicateGenerator, expression, "");

			laterPart.append(getLocalPredicates(localPredicates));
		}
		else
		{
			laterPart.append(getPredicates(expression, predicateGenerator));
			List<IExpression> children = getNonMainChildren(expression);
			for (IExpression child : children)
			{
				String childTargetRole = getTargetRoles().get(child);
				/**
				 * added to fix bug 11656.
				 * Now we can possibly have branching before we encounter hasVersion becomes true.
				 * To avoid the wrong
				 * branch, check if child is
				 * childless and does not have version.
				 */
				if (childTargetRole == null)
				{
					continue;
				}

				appendChildren(child, predicateGenerator, laterPart);
			}

		}

	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @param localPredicates - predicates for that expression
	 * @return - string representation of predicates
	 */
	private String getLocalPredicates(String localPredicates)
	{
		StringBuffer laterPart = new StringBuffer();
		if (("").equals(localPredicates))
		{
			laterPart.append("");
		}
		else
		{
			laterPart.append('[').append(localPredicates).append(']');
		}
		return laterPart.toString();
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @param targetRole - target role as defined in the domain model
	 * @return target role if the expression has one to many relationship
	 * 			with its parent
	 */
	private String getTargetRole(String targetRole)
	{
		StringBuffer newTargetRole = new StringBuffer();
		if (targetRole == null)
		{
			newTargetRole.append("");
		}
		else
		{
			newTargetRole.append('/').append(targetRole);
		}
		return newTargetRole.toString();
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @param expression - IExpression
	 * @param predicateGenerator - A generator that gets the predicates for the expression
	 * @return predicates for the expression
	 */
	private String getPredicates(IExpression expression, PredicateGenerator predicateGenerator)
	{
		StringBuilder laterPart = new StringBuilder();
		Predicates predicates = predicateGenerator.getPredicates(expression);

		if (predicates != null)
		{
			replaceRhsForVariables(predicates);
			laterPart.append('[').append(predicates.assemble()).append(']');
		}
		return laterPart.toString();
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @param expression - IExpression
	 * @return In case of main expression the name of the entity has the first letter capital
	 *         which is not in case of child entities
	 */
	private String getEntityName(IExpression expression)
	{
		String entityName = "";
		QueryableObjectInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();
		if (getMainExpressions().contains(expression))
		{
			entityName = entity.getName();
		}
		else
		{
			entityName = deCapitalize(entity.getName());
		}
		return entityName;

	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    JuberAhamadPatel  15-Feb-2009       Siddharth Shah                 Initial
	 *    Siddharth Shah    15-Apr-2009                                    Bug Id 12170
	 */
	/**
	 * build the right paths for all the predicates of given expression
	 * and all its children relative to the expression, recursively.
	 * @param predicateGenerator - PredicateGenerator object
	 * @param expression - IExpression
	 * @param prefix - The prefix of FOR clause
	 * @return all predicates of its children
	 */
	private String getAllDownstreamPredicates(PredicateGenerator predicateGenerator,
			IExpression expression, String prefix)
	{
		//Changes for bug ID 12170 begins
		//boolean value to detect whether the entity is Version Tagged or Not

		//Changes for bug ID 12170 ends
		StringBuilder downStreamPredicates = new StringBuilder();
		Predicates localPredicates = predicateGenerator.getPredicates(expression);

		if (localPredicates != null)
		{
			replaceRhsForVariables(localPredicates);

			// Changes for bug 12170 begins
			QueryableObjectInterface entity = expression.getQueryEntity()
					.getDynamicExtensionsEntity();
			downStreamPredicates
					.append(processVersionedExpression(prefix, localPredicates, entity));

			// Changes for bug 12170 ends
			List<IExpression> children = getNonMainNonEmptyChildren(expression);

			//end recursion
			if (!children.isEmpty())
			{
				downStreamPredicates.append(processChildPredicates(children, prefix,
						predicateGenerator));
			}

		}
		return Utility.removeLastAnd(downStreamPredicates.toString());

	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @param children List of child expression
	 * @param prefix - prefix of For clause
	 * @param predicateGenerator - predicateGenerator object
	 * @return Child Predicates
	 */
	private String processChildPredicates(List<IExpression> children, String prefix,
			PredicateGenerator predicateGenerator)
	{
		StringBuilder downStreamPredicates = new StringBuilder();
		for (IExpression child : children)
		{
			String appendPredicates = "";
			//skip children that do not have for variables
			if (!getForVariables().containsKey(child))
			{
				continue;
			}

			String targetRole = getTargetRoles().get(child);
			String entityName = deCapitalize(child.getQueryEntity().getDynamicExtensionsEntity()
					.getName());
			StringBuilder newPrefix = new StringBuilder(prefix);

			if (targetRole != null)
			{
				newPrefix.append(targetRole).append('/');
			}

			newPrefix.append(entityName).append('/');
			appendPredicates = getAllDownstreamPredicates(predicateGenerator, child, newPrefix
					.toString());
			if (!(("").equals(appendPredicates)))
			{
				downStreamPredicates.append(appendPredicates).append(Constants.QUERY_AND);
			}

		}
		return downStreamPredicates.toString();
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @param prefix - prefix for FOR clause
	 * @param localPredicates - predicates for the related expression
	 * @param entity - DE entity
	 * @return create the XPath for each Main Expression
	 */
	private String processVersionedExpression(String prefix, Predicates localPredicates,
			QueryableObjectInterface entity)
	{
		boolean hasVersion = false;
		StringBuilder downStreamPredicates = new StringBuilder();
		hasVersion = isVersionExpression(entity);
		String newPrefix = prefix;
		if (hasVersion)
		{
			// If the expression has Version Tag
			downStreamPredicates.append(localPredicates.assemble(newPrefix)).append(
					Constants.QUERY_AND);
		}
		else
		{
			// If the expression does not have Version Tag
			newPrefix = prefix.substring(0, prefix.length() - 1);
			downStreamPredicates.append(newPrefix).append("[").append(localPredicates.assemble(""))
					.append("]").append(Constants.QUERY_AND);
		}
		return downStreamPredicates.toString();
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * Method to identify whether the expression has Version Tag or not.
	 * @param entity - Entity Interface Object
	 * @return Boolean value indicating whether the entity is a version tagged entity or not
	 */

	private boolean isVersionExpression(QueryableObjectInterface entity)
	{
		boolean versionExpression = false;
		Collection<TaggedValueInterface> taggedValues = entity.getTaggedValueCollection();
		for (TaggedValueInterface value : taggedValues)
		{
			if (value.getValue().equals(Constants.VERSION))
			{
				versionExpression = true;
			}
		}
		return versionExpression;
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * to get the columns clause of XQuery.
	 * @param attribute - IOutputAttribute
	 * @param expression - IExpression
	 * @return - columns Clause
	 * @throws DataTypeFactoryInitializationException - Exception
	 */
	protected String getColumnsClause(IOutputAttribute attribute, IExpression expression)
			throws DataTypeFactoryInitializationException
	{
		StringBuilder columnsPart = new StringBuilder(512);
		QueryableAttributeInterface attributes = attribute.getAttribute();
		String attributeAlias = Utility.getAliasFor(attributes, expression);
		String dataType = getDataTypeInformation(attributes);
		columnsPart.append(attributeAlias).append(' ').append(dataType)
				.append(Constants.QUERY_PATH).append("'").append(
						getRelativePath(expression, attribute)).append("'").append(
						Constants.QUERY_COMMA);
		return columnsPart.toString();

	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * to get the XPath of each attribute.
	 * @param expression - IExpression
	 * @param attribute - IOutputAttribute
	 * @return relative path relative to current expression
	 */
	private String getRelativePath(IExpression expression, IOutputAttribute attribute)
	{
		StringBuilder relativePath = new StringBuilder();
		while (true)
		{
			if (passOneForVariables.containsKey(expression))
			{
				relativePath.append(attribute.getAttribute().getName());
				break;
			}

			boolean isParent = getAttributeDirection(expression, true);
			if (isParent)
			{
				relativePath.append(getChildRelativePath(expression, attribute)).toString();
				break;
			}
			else
			{
				relativePath.append(getParentRelativePath(expression, attribute, true)).toString();
				break;
			}
		}
		return relativePath.toString();
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * get Path in case the expression is the parent of Version Tagged Expression.
	 * @param expression - IExpression Object
	 * @param attribute - IOutputAttribute object
	 * @param value - boolean
	 * @return - Correct path in columns clause
	 */

	private String getParentRelativePath(IExpression expression, IOutputAttribute attribute,
			boolean value)
	{
		StringBuilder newRelativePath = new StringBuilder();
		IExpression newExpression = expression;
		boolean exitPath = value;
		while (exitPath)
		{
			newExpression = getChildrenCollection(expression);
			boolean newExprNull = (newExpression == null);
			boolean isTagPresent = expression.getQueryEntity().getDynamicExtensionsEntity()
					.isTagPresent(Constants.TAGGED_PARENT_ENTITY);
			boolean forExpression = passOneForVariables.containsKey(expression);
			boolean isOneToOne = newExprNull	&& !isTagPresent && !forExpression;
			boolean relPath = forExpression || newExprNull;
			if (isOneToOne)
			{
				IExpression newparentExpression = joinGraph.getParentList(expression).get(0);
				newRelativePath.append(getParentRelativePath(newparentExpression, attribute,
						exitPath));
				processMainExpressions(expression, newRelativePath, newparentExpression);
				exitPath = false;
				break;
			}
			else if (relPath)
			{
				newRelativePath.append(attribute.getAttribute().getName());
				exitPath = false;
				break;
			}

			//additional "../" for entities having target role eg. demographicsCollection
			if (getTargetRoles().containsKey(newExpression))
			{
				newRelativePath.insert(0, Constants.XQUERY_PARENTING);
			}

			newRelativePath.insert(0, Constants.XQUERY_PARENTING);
			newRelativePath.append(getParentRelativePath(newExpression, attribute, exitPath));
			exitPath = false;
		}
		return newRelativePath.toString();
	}

	/**
	 * This method process the main expressions.
	 * @param expression in query
	 * @param newRelativePath relative path
	 * @param newparentExpression new parent expression
	 */
	private void processMainExpressions(IExpression expression, StringBuilder newRelativePath,
			IExpression newparentExpression)
	{
		if (!mainExpressions.contains(expression))
		{
			IntraModelAssociation newAssociation = (IntraModelAssociation) joinGraph
					.getAssociation(newparentExpression, expression);
			String targetRole = newAssociation.getDynamicExtensionsAssociation()
					.getTargetRole().getName();
			int index = newRelativePath.lastIndexOf("/");
			newRelativePath.insert(index + 1, targetRole + "/");
		}
	}


	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * Method to get the right expression to create the XPath.
	 * @param expression - IExpression object
	 * @return - correct expression to be processed
	 */

	private IExpression getChildrenCollection(IExpression expression)
	{
		IExpression newExpression = null;
		List<IExpression> expressions = joinGraph.getChildrenList(expression);
		for (IExpression childExpression : expressions)
		{
			if (getTargetRoles().containsKey(childExpression))
			{
				newExpression = childExpression;
				break;
			}

		}
		return newExpression;
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * get Path in case the expression is the child of Version Tagged Expression.
	 * @param expression - IExpression object
	 * @param attribute - IOutputAttribute object
	 * @return - String returning the correct columns path
	 */
	private String getChildRelativePath(IExpression expression, IOutputAttribute attribute)
	{
		StringBuilder attributePath = new StringBuilder();
		attributePath.append(getParentPath(expression, attribute, attributePath, true));
		attributePath.append("/");
		IExpression parentExpression = joinGraph.getParentList(expression).get(0);
		IAssociation association = joinGraph.getAssociation(parentExpression, expression);
		AssociationInterface eavAssociation = ((IIntraModelAssociation) association)
				.getDynamicExtensionsAssociation();

		//		int cardinality = eavAssociation.getTargetRole()
		//				.getMaximumCardinality().getValue();
		if (getTargetRoles().containsKey(expression))
		{
			attributePath.append(getTargetRoles().get(expression)).append("/").append(
					expression.getQueryEntity().getDynamicExtensionsEntity().getName());
		}
		else
		{
			attributePath.append(eavAssociation.getTargetRole().getName());
		}

		attributePath.append("/");
		attributePath.append(attribute.getAttribute().getName());

		return attributePath.toString();
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * The method provides a boolean value which indicates whether the expression to be processed
	 * is the child of Version Tag Expression or the parent.
	 * true - expression is a child of Version Tagged Expression
	 * false - expression is a parent of Version Tagged Expression
	 * @param expression - IExpression Object
	 * @param exitCall - boolean
	 * @return - boolean
	 */

	private boolean getAttributeDirection(IExpression expression, boolean exitCall)
	{
		boolean newValue = false;
		boolean exitPath = exitCall;
		List<IExpression> parentExpression = joinGraph.getParentList(expression);
		while (exitPath)
		{
			if (parentExpression.isEmpty())
			{
				exitPath = false;
				break;
			}
			else
			{
				QueryableObjectInterface entity = parentExpression.get(0).getQueryEntity()
						.getDynamicExtensionsEntity();
				if (entity.isTagPresent(Constants.VERSION))
				{
					newValue = true;
					exitPath = false;
					break;
				}
			}
			newValue = getAttributeDirection(parentExpression.get(0), exitPath);
			exitPath = false;
		}
		return newValue;
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * Get the path of the parent Expression.
	 * @param expression - IExpression Object
	 * @param attribute - IAtrribute Object
	 * @param relativePath - String representing columns path
	 * @param value - boolean
	 * @return - newRelativePath - String representing complete columns path
	 */
	private String getParentPath(IExpression expression, IOutputAttribute attribute,
			StringBuilder relativePath, boolean value)
	{
		StringBuilder newRelativePath = new StringBuilder(relativePath);
		boolean exitPath = value;
		while (exitPath)
		{
			IExpression parentExpression = joinGraph.getParentList(expression).get(0);
			if (passOneForVariables.containsKey(parentExpression))
			{
				newRelativePath.insert(0, Constants.QUERY_DOT);
				exitPath = false;
				break;
			}
			newRelativePath.insert(0, "/"
				+ deCapitalize(parentExpression.getQueryEntity().getDynamicExtensionsEntity()
							.getName()));
			if (getTargetRoles().containsKey(parentExpression))
			{
				newRelativePath.insert(0, "/" + getTargetRoles().get(parentExpression));
			}
			getParentPath(parentExpression, attribute, newRelativePath, exitPath);
			exitPath = false;
		}

		return newRelativePath.toString();
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @param predicates - PredicateGenerator object
	 */
	private void replaceRhsForVariables(Predicates predicates)
	{
		String rhsFor = "";
		List<AbstractPredicate> predicateList = predicates.getPredicates();

		for (AbstractPredicate predicate : predicateList)
		{
			String rhs = predicate.getRhs();
			rhsFor = replaceRhsForVariable(rhs);
			predicate.setRhs(rhsFor);

		}
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * replace the for variable in the rhs with appropriate pass one for variable.
	 * the old for variable is replaced with an xpath expression involving an exisiting
	 * pass one for variable.
	 * @param rhs - replacing the path obtained by where clause
	 * 				with the appropriate prefix
	 * @return - appropriate RHS
	 */

	private String replaceRhsForVariable(String rhs)
	{
		String newRhs = null;

		//return unchanged
		if (rhs == null || !rhs.startsWith(String.valueOf(Constants.QUERY_DOLLAR)))
		{
			newRhs = rhs;
		}
		else
		{
			String forVariable = rhs.substring(0, rhs.indexOf('/'));
			String attribute = rhs.substring(rhs.indexOf('/') + 1);
			newRhs = getNewRhs(rhs, forVariable, attribute);
		}

		return newRhs;

	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @param rhs - RHS
	 * @param forVariable - for variable
	 * @param attribute - name of the attribute
	 * @return appropriate RHS value
	 */
	private String getNewRhs(String rhs, String forVariable, String attribute)
	{
		String newRhs = "";
		String newForVariable = forVariable;
		List<IExpression> expressionList = new ArrayList<IExpression>();
		//return unchanged
		if (passOneForVariables.containsValue(newForVariable))
		{
			newRhs = rhs;
		}
		else
		{

			//traverse down the hierarchy of the expression
			//to find an expresssion which is in passOneForVariables
			//this could also be theoratically up the hierarchy but not in our xmls
			IExpression expression = null;
			StringBuilder relativePath = new StringBuilder();
			StringBuilder path = new StringBuilder();
			expression = getForVariable(newForVariable);

			//build the relative path and the complete replacement of for variable
			while (true)
			{
				if (passOneForVariables.containsValue(newForVariable))
				{
					path.append(newForVariable).append('/');
					path.append(relativePath.toString());
					path.append(attribute);
					newRhs = path.toString();
					break;
				}
				expressionList.addAll(getNonMainNonEmptyChildren(expression));
				expression = getVersionedExpression(expressionList);
				//expression = getNonMainNonEmptyChildren(expression).get(0);
				newForVariable = getForVariables().get(expression);

				//additional "../" for entities having target role eg. demographicsCollection
				if (getTargetRoles().containsKey(expression))
				{
					relativePath.append(Constants.XQUERY_PARENTING);
				}

				relativePath.append(Constants.XQUERY_PARENTING);
			}
		}

		return newRhs;
	}

	/**
	 * @param expressionList - List of IExpression
	 * @return Version Tagged Expression
	 */
	private IExpression getVersionedExpression(List<IExpression> expressionList)
	{
		IExpression expression = null;
		for (IExpression childExpression : expressionList)
		{
			if (childExpression.getQueryEntity().getDynamicExtensionsEntity().isTagPresent(
					Constants.VERSION))
			{
				expression = childExpression;
			}
		}
		return expression;
	}

	/**
	 * @param forVariable - String representation of For Variable
	 * @return IExpresssion
	 */
	private IExpression getForVariable(String forVariable)
	{
		IExpression expression = null;
		//find the expression corresponding to the for variable
		for (Entry<IExpression, String> entry : getForVariables().entrySet())
		{
			if (forVariable.equals(entry.getValue()))
			{
				expression = entry.getKey();
				break;
			}
		}
		return expression;
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @param predicateGenerator -PredicateGenerator object
	 * @param expression - IExpression
	 * @return returns the XQuery formed from IQuery object
	 * @throws SQLXMLException - XQuery exception
	 * @throws DynamicExtensionsSystemException - DE exception
	 * @throws MultipleRootsException - in case an DAG has more
	 * 									than 1 root expression
	 * @throws SqlException - SQl Exception
	 */

	protected String buildXQuery(PredicateGenerator predicateGenerator, IExpression expression)
			throws SQLXMLException, DynamicExtensionsSystemException, MultipleRootsException,
			SqlException

	{
		StringBuffer xQuery = new StringBuffer(1024);

		xQuery.append(buildXQueryForClause(predicateGenerator, expression));
		return xQuery.toString();

	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @return getParametrizedCondition String
	 */
	@Override
	protected String getParametrizedCondition()
	{
		return "";
	}

	/*
	 * Change History
	 *        Author           Date            Reviewed By                  Comments
	 *    Siddharth Shah    02-Jun-2009      					              Initial
	 */
	/**
	 * @param attribute - QueryableAttributeInterface
	 * @param expression - IExpression
	 * @return isParametrizedAttribute boolean
	 */
	@Override
	protected boolean isParametrizedAttribute(QueryableAttributeInterface attribute,
			IExpression expression)
	{
		return false;
	}

}

