
package edu.wustl.query.bizlogic;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.wustl.common.query.queryobject.util.QueryObjectProcessor;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.dao.HibernateDAO;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.metadata.util.DyExtnObjectCloner;
import edu.wustl.query.enums.QueryType;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.querysuite.DAOUtil;

/**
 * When the user searches or saves a query , the query is checked for the
 * conditions like DAG should not be empty , is there at least one node in view
 * on define view page and does the query contain the main object. If all the
 * conditions are satisfied further process is done else corresponding error
 * message is shown.
 *
 * @author shrutika_chintal
 */
public class ValidateQueryBizLogic
{

	private static org.apache.log4j.Logger logger = LoggerConfig
			.getConfiguredLogger(ValidateQueryBizLogic.class);

	/**
	 * @param pageOf
	 *            - String
	 * @param queryTitle
	 *            - Query Title
	 * @param queryType
	 *            - Query Type
	 * @param query
	 *            object of IQuery to validate
	 * @return - error message . Returns null if the query is correctly formed.
	 */
	public static String getValidationMessage(String pageOf, String queryTitle, String queryType,
			IQuery query)
	{
		String validationMessage = validateQueryTitle(pageOf, queryType, queryTitle, query);
		if (validationMessage == null)
		{
			validationMessage = validateExpressionInView(query);
			if (validationMessage == null)
			{
				validationMessage = getMessageForBaseObject(query.getConstraints(), queryType);
			}
		}
		return validationMessage;
	}

	/**
	 * To check for duplicate query names.
	 *
	 * @param queryTitle
	 *            queryTitle
	 * @param iQueryId
	 *            Query Id
	 * @return - error message. Returns null if the validation is successful.
	 */
	public static String validateForDuplicateQueryName(String queryTitle, Long iQueryId)
	{
		String validationMessage = null;
		HibernateDAO hibernateDAO = null;
		try
		{
			hibernateDAO = DAOUtil.getHibernateDAO(null);
			List list = hibernateDAO.executeQuery("select id from "
					+ edu.wustl.common.querysuite.queryobject.impl.AbstractQuery.class.getName()
					+ "  AbstractQuery where upper(AbstractQuery.name) = " + "'"
					+ queryTitle.toUpperCase() + "'");

			validationMessage = getValidationMessage(list, iQueryId);
			// edit
		}
		catch (DAOException e)
		{
			logger.error(e.getMessage());
		}
		finally
		{
			try
			{
				DAOUtil.closeHibernateDAO(hibernateDAO);
			}
			catch (DAOException e)
			{
				logger.error(e.getMessage());
			}

		}

		return validationMessage;
	}

	/**
	 * @param list
	 *            - List
	 * @param iQueryId
	 *            - Long
	 * @return String
	 */
	private static String getValidationMessage(List list, Long iQueryId)
	{
		String validationMessage = null;
		if (!list.isEmpty() && (iQueryId == null || !iQueryId.equals(list.get(0))))
		{
			validationMessage = ApplicationProperties.getValue("query.title.duplicate");
		}
		return validationMessage;
	}

	/**
	 * @param query
	 *            object of IQuery to validate
	 * @return - error message . Returns null if any one expression is in view.
	 */
	public static String validateExpressionInView(IQuery query)
	{
		String validationMessage = null;
		IConstraints constraints = query.getConstraints();
		boolean noExpressionInView = true;
		for (IExpression expression : constraints)
		{

			if (expression.isInView())
			{
				noExpressionInView = false;
				break;
			}
		}
		if (noExpressionInView)
		{
			validationMessage = ApplicationProperties
					.getValue("query.defineView.noExpression.message");

		}
		return validationMessage;
	}

	/**
	 * This method validates the DataQuery for containing Object with multiple
	 * parents
	 *
	 * @param parameterizedQuery
	 *            object of IQuery to validate
	 * @param validationMessage
	 *            message for validation
	 * @return validationMessage
	 */
	public static String validateDQForMultiParents(IQuery parameterizedQuery,
			String validationMessage)
	{
		if (validationMessage == null)
		{
			ParameterizedQuery queryClone = (ParameterizedQuery) new DyExtnObjectCloner()
					.clone(parameterizedQuery);
			IConstraints constraints = queryClone.getConstraints();
			Boolean isChanged = QueryObjectProcessor.replaceMultipleParents(constraints);
			if (isChanged)
			{
				validationMessage = ApplicationProperties
						.getValue("query.multipleParentObject.message");
			}
		}
		return validationMessage;
	}

	/**
	 * @param query
	 *            object of IQuery to validate
	 * @return error message
	 */
	public static String validateRuleInDAG(IQuery query)
	{
		String validationMessage = null;
		boolean isRulePresentInDag = Utility.checkIfRulePresentInDag(query);
		if (!isRulePresentInDag)
		{
			validationMessage = ApplicationProperties.getValue("query.noLimit.error");
		}
		return validationMessage;
	}

	/**
	 * @param pageOf
	 *            Value of pageOf variable
	 * @param queryType
	 *            type of query
	 * @param queryTitle
	 *            Query title
	 * @param query
	 *            - IQuery
	 * @return error message
	 */
	public static String validateQueryTitle(String pageOf, String queryType, String queryTitle,
			IQuery query)
	{
		String validationMessage = null;
		boolean defineFilter = "DefineFilter".equals(pageOf);
		boolean defineView = "DefineView".equals(pageOf);
		boolean queryDateType = (QueryType.GET_DATA.type).equals(queryType);
		boolean firstCheck = defineFilter || defineView || queryDateType;
		if (!(firstCheck) && queryTitle == null || "".equals(queryTitle))
		{
			validationMessage = ApplicationProperties.getValue("query.title.madatory");
		}
		else if (!firstCheck && isXMLCharacter(queryTitle))
		{
			validationMessage = ApplicationProperties.getValue("error.querytitle.invalid.char");
		}
		else if (!(QueryType.GET_DATA.type).equals(queryType))
		{
			validationMessage = validateForDuplicateQueryName(queryTitle, query.getId());
		}
		return validationMessage;
	}

	/**
	 * @param constraints
	 *            Constraints of IQuery object
	 * @param queryType
	 *            type of query
	 * @return error message
	 */
	public static String getMessageForBaseObject(IConstraints constraints, String queryType)
	{
		String validationMessage = null;
		if (queryType.equals(QueryType.GET_DATA.type))
		{
			validationMessage = getMessageForBaseObjectForDataQuery(constraints);
		}
		else
		{
			validationMessage = getMessageForBaseObjectForCountQuery(constraints);
		}
		return validationMessage;
	}

	/**
	 * This method will verify that the constraints contains the Main Object
	 * i.e. Person In it For Get Count Query. If not will return the respective
	 * validationMessage
	 *
	 * @param constraints
	 *            - Constraints
	 * @return String
	 */
	private static String getMessageForBaseObjectForCountQuery(IConstraints constraints)
	{
		boolean istagPresent = false;
		boolean isCategoryQuery = false;
		String message = null;
		try
		{
			isCategoryQuery = constraints.getRootExpression().getQueryEntity()
					.getDynamicExtensionsEntity().isCategory();
			for (IExpression expression : constraints)
			{
				istagPresent = expression.getQueryEntity().getDynamicExtensionsEntity()
						.getRootQueryableObject().isTagPresent(Constants.BASE_MAIN_ENTITY);

				if (istagPresent)
				{
					break;
				}
			}
			if (!istagPresent)
			{
				message = getErrorMessage(isCategoryQuery);

			}
		}
		catch (MultipleRootsException e)
		{
			message = ApplicationProperties.getValue(Constants.QUERY_MULTIPLE_ROOTS);
			logger.error(e.getMessage(), e);
		}
		return message;
	}

	/**
	 * It will return the error message tobe displayed to user when no main
	 * expression is added in the Query. Message is decided on the basis of
	 * isCategoryQuery.
	 *
	 * @param isCategoryQuery
	 *            it says that query formed using the categories.
	 * @return errorMessage.
	 */
	private static String getErrorMessage(boolean isCategoryQuery)
	{
		String validationMessage;
		if (isCategoryQuery)
		{
			validationMessage = ApplicationProperties
					.getValue(Constants.QUERY_NO_MAIN_CAT_EXPRESSION);
		}
		else
		{
			validationMessage = ApplicationProperties.getValue(Constants.QUERY_NO_MAINEXPRESSION);
		}
		return validationMessage;
	}

	/**
	 * This method will verify that the RootExpression of the constraints is
	 * Main Object i.e Person For Get Data Query. If not will return the
	 * respective validationMessage
	 *
	 * @param constraints
	 *            - IConstraint
	 * @return String
	 */
	private static String getMessageForBaseObjectForDataQuery(IConstraints constraints)
	{
		boolean istagPresent = false;
		String message = null;
		boolean isCategoryQuery = false;
		try
		{
			IExpression root = constraints.getJoinGraph().getRoot();
			istagPresent = root.getQueryEntity().getDynamicExtensionsEntity()
					.getRootQueryableObject().isTagPresent(Constants.BASE_MAIN_ENTITY);
			isCategoryQuery = root.getQueryEntity().getDynamicExtensionsEntity().isCategory();
			if (!istagPresent)
			{
				if (isCategoryQuery)
				{
					message = ApplicationProperties
							.getValue(Constants.QUERY_NO_ROOT_CAT_EXPRESSION);
				}
				else
				{
					message = ApplicationProperties.getValue(Constants.QUERY_NO_ROOTEXPRESSION);
				}
			}
		}
		catch (MultipleRootsException e)
		{
			message = ApplicationProperties.getValue(Constants.QUERY_MULTIPLE_ROOTS);
		}

		return message;
	}

	/**Method to validate XML characters in query title.
	 * @param value query title string
	 * @return isXML boolean value
	 */
	public static boolean isXMLCharacter(String value)
	{
		boolean isXML = false;
		if (value != null)
		{
			Pattern pattern = Pattern.compile("&[gl]t");
			Matcher matcher = pattern.matcher(value);
			isXML = matcher.find();
		}
		return isXML;
	}
}
