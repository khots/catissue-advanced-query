
package edu.wustl.query.util.global;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.json.JSONException;
import org.json.JSONObject;

import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.common.dynamicextensions.domaininterface.TaggedValueInterface;
import edu.common.dynamicextensions.entitymanager.EntityManagerConstantsInterface;
import edu.wustl.cab2b.client.ui.dag.ambiguityresolver.AmbiguityObject;
import edu.wustl.cab2b.client.ui.query.IClientQueryBuilderInterface;
import edu.wustl.cab2b.client.ui.query.IPathFinder;
import edu.wustl.cab2b.common.exception.CheckedException;
import edu.wustl.cider.querymanager.CiderQueryPrivilege;
import edu.wustl.common.beans.QueryResultObjectData;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.bizlogic.IBizLogic;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.factory.AbstractFactoryConfig;
import edu.wustl.common.factory.IFactory;
import edu.wustl.common.query.QueryPrivilege;
import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.common.query.factory.ITableManagerFactory;
import edu.wustl.common.query.impl.CommonPathFinder;
import edu.wustl.common.query.itablemanager.ITableManager;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.metadata.path.IPath;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IOutputTerm;
import edu.wustl.common.querysuite.queryobject.IParameter;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.IRule;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.Query;
import edu.wustl.common.querysuite.utils.ConstraintsObjectBuilder;
import edu.wustl.common.querysuite.utils.IConstraintsObjectBuilderInterface;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.dao.HibernateDAO;
import edu.wustl.dao.JDBCDAO;
import edu.wustl.dao.daofactory.DAOConfigFactory;
import edu.wustl.dao.daofactory.IDAOFactory;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.bizlogic.ValidateQueryBizLogic;
import edu.wustl.query.bizlogic.WorkflowBizLogic;
import edu.wustl.query.domain.Execution;
import edu.wustl.query.domain.Workflow;
import edu.wustl.query.domain.WorkflowItem;
import edu.wustl.query.flex.dag.DAGResolveAmbiguity;
import edu.wustl.query.htmlprovider.HtmlUtility;
import edu.wustl.query.htmlprovider.ParseXMLFile;
import edu.wustl.query.queryexecutor.AbstractQueryExecutor;
import edu.wustl.query.queryexecutor.Db2QueryExecuter;
import edu.wustl.query.queryexecutor.MysqlQueryExecutor;
import edu.wustl.query.queryexecutor.OracleQueryExecutor;
import edu.wustl.query.querymanager.Count;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.query.util.querysuite.AdvanceQueryDAO;
import edu.wustl.query.util.querysuite.DAOUtil;
import edu.wustl.query.util.querysuite.QueryAddContainmentsUtil;
import edu.wustl.query.util.querysuite.QueryDetails;
import edu.wustl.query.util.querysuite.QueryModuleException;
import edu.wustl.query.viewmanager.ResultsViewComponentGenerator;
import edu.wustl.security.exception.SMException;
import edu.wustl.security.privilege.PrivilegeCache;
import edu.wustl.security.privilege.PrivilegeManager;

/**Utility class.
 * @author vijay_pande
 *
 */
public class Utility
{
	/** logger for this class. **/
	private static org.apache.log4j.Logger logger = LoggerConfig.getConfiguredLogger(Utility.class);

	/** date pattern. **/
	private static String pattern = "MM-dd-yyyy";

	/**Method to process objects according to new grid format.
	 * @param obj object to be processed
	 * @return Object
	 */
	public static Object toNewGridFormat(final Object obj)
	{
		Object newobj = edu.wustl.common.util.Utility.toGridFormat(obj);
		if (newobj instanceof String)
		{
			final String objString = (String) newobj;
			final StringBuffer tokenedString = new StringBuffer();

			final StringTokenizer tokenString = new StringTokenizer(objString, ",");

			while (tokenString.hasMoreTokens())
			{
				tokenedString.append(tokenString.nextToken()).append(Constants.SPACE);
			}
			final String gridFormattedStr = new String(tokenedString);
			newobj = gridFormattedStr;
		}

		return newobj;
	}

	/**
	 * Adds the attribute values in the list in sorted order and returns the
	 * list containing the attribute values in proper order.
	 *
	 * @param dataType -
	 *            data type of the attribute value
	 * @param value1 -
	 *            first attribute value
	 * @param value2 -
	 *            second attribute value
	 * @return List containing value1 and valu2 in sorted order
	 */
	public static ArrayList<String> getAttributeValuesInProperOrder(final String dataType, final String value1,
			final String value2)
	{
		String tempValue1 = value1;
		String tempValue2 = value2;
		final ArrayList<String> attributeValues = new ArrayList<String>();
		if (dataType.equalsIgnoreCase(EntityManagerConstantsInterface.DATE_ATTRIBUTE_TYPE))
		{
			final SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			try
			{
				final Date date1 = dateFormat.parse(value1);
				final Date date2 = dateFormat.parse(value2);
				if (date1.after(date2))
				{
					tempValue1 = value2;
					tempValue2 = value1;
				}
			}
			catch (final ParseException e)
			{
				logger.error("Can not parse the given date in " +
							"getAttributeValuesInProperOrder() method :"
								+ e.getMessage());
			}
		}
		else
		{
			if (dataType.equalsIgnoreCase(EntityManagerConstantsInterface.INTEGER_ATTRIBUTE_TYPE)
				|| dataType.equalsIgnoreCase(EntityManagerConstantsInterface.LONG_ATTRIBUTE_TYPE))
			{
				if (Long.parseLong(value1) > Long.parseLong(value2))
				{
					tempValue1 = value2;
					tempValue2 = value1;
				}

			}
			else
			{
				if (dataType
					.equalsIgnoreCase(EntityManagerConstantsInterface.DOUBLE_ATTRIBUTE_TYPE))
				{
					if (Double.parseDouble(value1) > Double.parseDouble(value2))
					{
						tempValue1 = value2;
						tempValue2 = value1;
					}

				}
			}
		}
		attributeValues.add(tempValue1);
		attributeValues.add(tempValue2);
		return attributeValues;
	}

	/**Method to prepare dataList.
	 * @param dataList data
	 * @return my Data
	 */
	public static String getmyData(final List dataList)
	{
		StringBuffer myData = new StringBuffer("[");
		int index = 0;
		if (dataList != null && !dataList.isEmpty())
		{
			for (index = 0; index < (dataList.size() - Constants.ONE); index++)
			{
				final List row = (List) dataList.get(index);
				int index2;
				myData = myData.append("\"");
				for (index2= 0; index2 < (row.size() - Constants.ONE); index2++)
				{
					myData = myData.append(Utility.toNewGridFormat(
							row.get(index2)).toString());
					myData = myData.append(",");
				}
				myData = myData.append(Utility.toNewGridFormat(row.get(index2)).toString());
				myData = myData.append("\"");
				myData = myData.append(",");
			}

			final List row = (List) dataList.get(index);
			int index2;
			myData = myData.append("\"");
			for (index2 = 0; index2 < (row.size() - Constants.ONE); index2++)
			{
				myData = myData.append(Utility.toNewGridFormat(row.get(index2)).toString());
				myData = myData.append(",");
			}
			myData = myData.append(Utility.toNewGridFormat(row.get(index2)).toString());
			myData = myData.append("\"");
		}
		myData = myData.append("]");
		return myData.toString();
	}

	/**Method to get list of columns.
	 * @param columnList columns list
	 * @return String column name
	 */
	public static String getcolumns(final List columnList)
	{
		StringBuffer columns = new StringBuffer("\"");
		int col;
		if (columnList != null)
		{
			for (col = 0; col < (columnList.size() - Constants.ONE); col++)
			{
				columns = columns.append(columnList.get(col));
				columns = columns.append(",");
			}
			columns = columns.append(columnList.get(col));
		}
		columns = columns.append("\"");
		return columns.toString();
	}

	/**Method to get width of columns.
	 * @param columnList column list
	 * @param isWidthInPercent boolean for is width in percent
	 * @return String value
	 */
	public static String getcolWidth(final List columnList, final boolean isWidthInPercent)
	{

		StringBuffer colWidth = new StringBuffer("\"");
		int col;
		if (columnList != null)
		{
			String fixedColWidth = null;
			if (isWidthInPercent)
			{
				fixedColWidth = String.valueOf(Constants.HUNDRED / columnList.size());
			}
			else
			{
				fixedColWidth = "100";
			}
			for (col = 0; col < (columnList.size() - Constants.ONE); col++)
			{
				colWidth = colWidth.append(fixedColWidth);
				colWidth = colWidth.append(",");
			}
			colWidth = colWidth.append(fixedColWidth);
		}
		colWidth = colWidth.append("\"");
		return colWidth.toString();
	}

	/**Method to get column type list.
	 * @param dataList data list
	 * @return String cloumn types
	 */
	public static String getcolTypes(final List dataList)
	{
		final StringBuffer colTypes = new StringBuffer();
		colTypes.append('"');
		colTypes.append(Variables.prepareColTypes(dataList));
		colTypes.append('"');
		return colTypes.toString();
	}

	/**Method to set grid data.
	 * @param dataList data list
	 * @param columnList column list
	 * @param request Object of HttpServletRequest
	 */
	public static void setGridData(final List dataList, final List columnList, final HttpServletRequest request)
	{
		request.setAttribute("myData", getmyData(dataList));
		request.setAttribute("columns", getcolumns(columnList));
		boolean isWidthInPercent = false;
		if (columnList.size() < Constants.TEN)
		{
			isWidthInPercent = true;
		}
		request.setAttribute("colWidth", getcolWidth(columnList, isWidthInPercent));
		request.setAttribute("isWidthInPercent", isWidthInPercent);
		request.setAttribute("colTypes", getcolTypes(dataList));
		int heightOfGrid = Constants.HUNDRED;
		if (dataList != null)
		{
			final int noOfRows = dataList.size();
			heightOfGrid = (noOfRows + Constants.TWO) * 20;
			if (heightOfGrid > 240)
			{
				heightOfGrid = 230;
			}
		}
		request.setAttribute("heightOfGrid", heightOfGrid);
		int col = 0;
		int index = 0;
		String hiddenColumnNumbers = "";
		for (col = 0; col < columnList.size(); col++)
		{
			if (columnList.get(col).toString().trim().equals("ID")
					|| columnList.get(col).toString().trim().equals("Status")
					|| columnList.get(col).toString().trim().equals("Site Name")
					|| columnList.get(col).toString().trim().equals("Report Collection Date"))
			{
				hiddenColumnNumbers = hiddenColumnNumbers + "hiddenColumnNumbers[" + index + "] = "
						+ col + ";";
				index++;
			}
		}

		request.setAttribute("hiddenColumnNumbers", hiddenColumnNumbers);
	}

	/**Method for new grid format with hyper link.
	 * @param row list of rows
	 * @param hyperlinkColumnMap hyper link column map
	 * @param index index value
	 * @return Object
	 */
	public static Object toNewGridFormatWithHref(final List<String> row,
			final Map<Integer, QueryResultObjectData> hyperlinkColumnMap, final int index)
	{
		Object obj = row.get(index);

		if (obj instanceof String)
		{
			obj = toNewGridFormat(obj);

			final QueryResultObjectData queryResultObj = hyperlinkColumnMap.get(index);

			if (queryResultObj != null)// This column is to be shown as
			// hyperlink.
			{
				if (obj == null || obj.equals(""))
				{
					obj = "NA";
				}

				/**
				 * Name : Prafull Bug ID: 4223 Patch ID: 4223_1 Description:
				 * Edit User: password fields empty & error on submitting new
				 * password Added PageOf Attribute as request parameter in the
				 * link.
				 */
				final String aliasName = queryResultObj.getAliasName();
				final String link = "SimpleSearchEdit.do?"
					+ edu.wustl.common.util.global.Constants.TABLE_ALIAS_NAME + "=" + aliasName
					+ "&" + edu.wustl.common.util.global.Constants.SYSTEM_IDENTIFIER + "="
					+ row.get(queryResultObj.getIdentifierColumnId()) + "&"
					+ Constants.PAGE_OF + "=" + Variables.aliasAndPageOfMap.get(aliasName);
				/**
				 * bug ID: 4225 Patch id: 4225_1 Description : Passing a
				 * different name to the popup window
				 */
				final String onclickStr = " onclick=javascript:NewWindow('" + link
						+ "','simpleSearch','800','600','yes') ";
				final String hrefTag = "<a class='normalLink' href='#'" +
								onclickStr + ">" + obj + "</a>";
				// String hrefTag = "<a href='"+ link+ "'>"+obj+"</a>";
				obj = hrefTag;
			}
		}
		return obj;
	}

	/**
	 * This method creates a comma separated string of numbers representing
	 * column width.
	 * @param columnNames column names
	 * @return colWidth
	 */
	public static String getColumnWidth(final List columnNames)
	{
		String colWidth = getColumnWidth((String) columnNames.get(0));

		final int size = columnNames.size() - Constants.ONE;

		for (int col = 0; col < size; col++)
		{
			final String columnName = (String) columnNames.get(col);
			colWidth = colWidth + "," + getColumnWidth(columnName);
		}
		return colWidth;
	}

	/**Method to get column width.
	 * @param columnName name of column
	 * @return String value
	 */
	private static String getColumnWidth(final String columnName)
	{
		/*
		 * Patch ID: Bug#3090_31 Description: The first column which is just a
		 * checkbox, used to select the rows, was always given a width of 100.
		 * Now width of 20 is set for the first column. Also, width of 100 was
		 * being applied to each column of the grid, which increasing the total
		 * width of the grid. Now the width of each column is set to 80.
		 */
		String columnWidth = null;
		if ("ID".equals(columnName.trim()))
		{
			columnWidth = "0";
		}
		else if ("".equals(columnName.trim()))
		{
			columnWidth = "20";
		}
		else
		{
			columnWidth = "150";
		}
		return columnWidth;
	}

	/**
	 * @param columnNames List  of  column Names
	 * @return colWidth STRING
	 */
	public static String getColumnWidthP(final List columnNames)
	{
		StringBuffer colWidth = new StringBuffer("");

		final int size = columnNames.size();
		final double temp = 97.5f;

		for (int col = 0; col < size; col++)
		{
			colWidth = colWidth.append(String.valueOf(temp / (size)));
			colWidth = colWidth.append(",");
		}
		if (colWidth.lastIndexOf(",") == colWidth.length() - Constants.ONE)
		{
			colWidth.deleteCharAt(colWidth.length() - Constants.ONE);
		}
		return colWidth.toString();
	}

	/**
	 * @param columnNames List of  column Names
	 * @return GridWidth STRING
	 */
	public static String getGridWidth(final List columnNames)
	{
		Integer gridWidth = 0;
		for (int col = 0; col < columnNames.size(); col++)
		{
			final String columnName = (String) columnNames.get(col);
			gridWidth = gridWidth + Integer.valueOf(getColumnWidth(columnName));
		}
		return gridWidth.toString();
	}

	/**
	 * limits the title of the saved query to 125 characters to avoid horizontal
	 * scrollbar.
	 *
	 * @param title -
	 *            title of the saved query (may be greater than 125 characters)
	 * @return - query title upto 125 characters
	 */

	public static String getQueryTitle(final String title)
	{
		String multilineTitle = "";
		if (title.length() <= Constants.CHARACTERS_IN_ONE_LINE)
		{
			multilineTitle = title;
		}
		else
		{
			multilineTitle = title.substring(0, Constants.CHARACTERS_IN_ONE_LINE) + ".....";
		}
		return multilineTitle;
	}

	/**
	 * returns the entire title to display it in tooltip .
	 *
	 * @param title -
	 *            title of the saved query
	 * @return tooltip string
	 */
	public static String getTooltip(final String title)
	{
		final String tooltip = title.replaceAll("'",
				Constants.SINGLE_QUOTE_ESCAPE_SEQUENCE); // escape sequence
		// for '
		return tooltip;
	}

	/**
	 * Sets action error.
	 * @param errorMessage error message
	 * @param key error key
	 * @return ActionErrors
	 */
	public static ActionErrors setActionError(final String errorMessage, final String key)
	{
		final ActionErrors errors = new ActionErrors();
		final ActionError error = new ActionError("errors.item", errorMessage);
		errors.add(ActionErrors.GLOBAL_ERROR, error);
		return errors;
	}

	/**
	 * This method returns the session data bean.
	 * @param request HttpServletRequest
	 * @return sessionData
	 */
	public static SessionDataBean getSessionData(final HttpServletRequest request)
	{
		final Object obj = request.getSession().getAttribute(
				edu.wustl.common.util.global.Constants.SESSION_DATA);
		SessionDataBean sessionData = null;
		if (obj != null)
		{
			sessionData = (SessionDataBean) obj;
		}
		return sessionData;
	}

	/**
	 * Method checks if attribute is not viewable in results view of Query.
	 * @param attribute QueryableAttributeInterface
	 * @return isNotViewable
	 */
	public static boolean isNotViewable(final QueryableAttributeInterface attribute)
	{
		boolean isNotViewable = false;
		if (attribute.getQueryEntity().isTagPresent(Constants.TAGGED_VALUE_NOT_VIEWABLE)
				|| attribute.isTagPresent(Constants.TAGGED_VALUE_NOT_VIEWABLE))
		{
			isNotViewable = true;
		}
		return isNotViewable;
	}

	/**
	 * This method checks if entity is main entity.
	 * @param entity EntityInterface
	 * @return isMainEntity
	 */
	public static boolean isMainEntity(final EntityInterface entity)
	{
		boolean isMainEntity = false;
		final Collection<TaggedValueInterface> taggedValueCol = entity.getTaggedValueCollection();
		for (final TaggedValueInterface tagValue : taggedValueCol)
		{
			if (tagValue.getKey().equals(Constants.TAGGED_VALUE_MAIN_ENTIY))
			{
				isMainEntity = true;
				break;
			}
		}
		return isMainEntity;
	}

	/**
	 * Removes last And from string.
	 * @param select string
	 * @return after removing and
	 */
	public static String removeLastAnd(final String select)
	{
		String selectString = select;
		if (select.endsWith(Constants.QUERY_AND))
		{
			selectString = selectString.substring(0, selectString.length() - Constants.FIVE);
		}
		return selectString;
	}

	/**
	 * Removes last comma.
	 * @param string for removal.
	 */
	public static void removeLastComma(final StringBuilder string)
	{
		if (!string.toString().equals("")
				&& Constants.QUERY_COMMA.equals(string.substring(string.length() - Constants.TWO)))
		{
			string.delete(string.length() - Constants.TWO, string.length());
		}
	}



	/**
	 * get the alias for given attribute to identify it uniquely.
	 * @param attribute object
	 * @param expression of query
	 * @return alias for attribute
	 */
	public static String getAliasFor(final QueryableAttributeInterface attribute, final IExpression expression)
	{
		return getAliasFor(attribute.getName(), expression);
	}

	/**
	 * get the alias for given attribute to identify it uniquely.
	 * @param attributeName name of attribute
	 * @param expression of attribute
	 * @return alias for attribute
	 */
	public static String getAliasFor(final String attributeName, final IExpression expression)
	{
		return attributeName + "_" + expression.getExpressionId();
	}



	/**
	 * This method sets output attribute list in query.
	 * @param query object
	 * @param newoutputAttributeList list of output attributes.
	 */
	public static void setQueryOutputAttributeList(final IParameterizedQuery query,
			final List<IOutputAttribute> newoutputAttributeList)
	{
		query.getOutputAttributeList().clear();
		query.getOutputAttributeList().addAll(newoutputAttributeList);
	}

	/** This method is used to get a display name.
	 * @param outputAttribute of query
	 * @return columnDisplayName
	 */
	public static String getDisplayNameForColumn(final IOutputAttribute outputAttribute)
	{
		final QueryableAttributeInterface attribute = outputAttribute.getAttribute();
		//for cp
		String className = outputAttribute.getExpression().getQueryEntity()
				.getDynamicExtensionsEntity().getName();
		className = edu.wustl.common.util.Utility.getDisplayLabel(className);
		final String attributeLabel = edu.wustl.common.util.Utility.getDisplayLabel(attribute
				.getDisplayName());
		return className + " : " + attributeLabel;
	}

	/**
	 * Private method used to generate the List of JSON objects.
	 *
	 * @param executionIdMap
	 *            Execution Id Map
	 * @param workflowBizLogic
	 *            Instance of BizLogic to be used.
	 * @param qUIManager
	 *            Instance of the Query UI Manager.
	 * @param privilege of query
	 * @return The List of JSON Objects
	 * @throws QueryModuleException
	 *             if error while executing the query.
	 */
	public static List<JSONObject> generateExecutionQueryResults(final Map<Long, Long> executionIdMap,
			final WorkflowBizLogic workflowBizLogic, final AbstractQueryUIManager qUIManager,
			final QueryPrivilege privilege) throws QueryModuleException
	{
		Count resultCount;
		JSONObject jsonObject;
		final List<JSONObject> executionQueryResults = new ArrayList<JSONObject>();
		//  Set<Long> titleset = executionIdMap.keySet();
		// Iterator<Long> iterator = titleset.iterator();
		final Iterator<Map.Entry<Long, Long>> entryItr = executionIdMap.entrySet().iterator();
		while (entryItr.hasNext())
		{
			// Long query = iterator.next();
			final Map.Entry<Long, Long> entry = entryItr.next();
			resultCount = workflowBizLogic.getCount(entry.getValue(), privilege);
			qUIManager.auditTooFewRecords(resultCount, privilege);
			jsonObject = createResultJSON(entry.getKey(), resultCount.getCount(), resultCount
					.getStatus(), resultCount.getQueryExectionId());
			executionQueryResults.add(jsonObject);
		}
		return executionQueryResults;
	}

	/**
	 * @param queryId =Query identifier for which execute request sent
	 * @param resultCount =value of result count for query
	 * @param status of query execution
	 * @param executionLogId execution log id
	 * @return jsonObject
	 *
	 * creates the jsonObject for input parameters
	 */
	public static JSONObject createResultJSON(final Long queryId, final Long resultCount, final String status,
			final Long executionLogId)
	{
		final JSONObject resultObject = new JSONObject();
		try
		{
			resultObject.append("queryId", queryId);
			resultObject.append("queryResult", resultCount);
			resultObject.append("status", status);
			resultObject.append("executionLogId", executionLogId);
		}
		catch (final JSONException e)
		{
			logger.info("error in initializing json object " + e);
		}

		return resultObject;
	}

	/**
	 * creates json response
	 * @param request HttpServletRequest
	 * @param writer Writer
	 * @param executionQueryResults List
	 * @throws JSONException JSONException
	 * @throws IOException IOException
	 */
	public static void createResponseForWf(final HttpServletRequest request, final Writer writer,
			final List<JSONObject> executionQueryResults) throws JSONException, IOException
	{
		final JSONObject resultObject = new JSONObject();
		resultObject.put("executionQueryResults", executionQueryResults);
		resultObject.put("projectId", request.getParameter(Constants.SELECTED_PROJECT));
		writer.write(new JSONObject().put("result", resultObject).toString());
	}

	/**
	 * Formats given string.
	 * @param str string to format.
	 * @return String
	 */
	public static String getFormattedString(final String str)
	{
		String returner;

		if (str.equalsIgnoreCase("id"))
		{
			return "Identifier";
		}

		final int upperCaseCount = countUpperCaseLetters(str);
		if (upperCaseCount > 0)
		{
			final String[] splitStrings = splitCamelCaseString(str, upperCaseCount);
			returner = getFormattedString(splitStrings);
		}
		else
		{
			returner = capitalizeFirstCharacter(str);
		}
		return returner;
	}

	/**
	 * This method checks if attribute is already present in outputAttributeList of IQuery.
	 * @param query object
	 * @param attribute check if present in output attribute list
	 * @param expression of query
	 * @return isPresent
	 */
	public static boolean isPresentInOutputAttrList(final IQuery query,
			final QueryableAttributeInterface attribute, final IExpression expression)
	{
		boolean isPresent = false;
		for (final IOutputAttribute outputAttribute : ((ParameterizedQuery) query)
				.getOutputAttributeList())
		{
			if (outputAttribute.getAttribute().equals(attribute)
					&& outputAttribute.getExpression().equals(expression))
			{
				isPresent = true;
				break;
			}
		}
		return isPresent;
	}

	/**
	 *  Utility method to count upper case characters in the String.
	 * @param str String
	 * @return count Of Capital Letters
	 */
	static int countUpperCaseLetters(final String str)
	{
		/*
		 * This is the count of Capital letters in a string excluding first
		 * character, and continuos uppercase letter in the string.
		 */
		int countOfCapitalLetters = 0;
		final char[] chars = str.toCharArray();

		for (int i = Constants.ONE; i < chars.length; i++)
		{
			final char character = chars[i];
			char nextCharacter = 'x';
			final char prevCharacter = chars[i - Constants.ONE];

			if ((i + Constants.ONE) < chars.length)
			{
				nextCharacter = chars[i + Constants.ONE];
			}
			if ((Character.isUpperCase(character) && Character.isUpperCase(prevCharacter)
				&& Character.isLowerCase(nextCharacter) && i != chars.length - 1)
				|| (Character.isUpperCase(character) && Character.isLowerCase(prevCharacter)
				&& Character.isUpperCase(nextCharacter))
				|| (Character.isUpperCase(character) && Character.isLowerCase(prevCharacter)
				&& Character.isLowerCase(nextCharacter)))
			{
				countOfCapitalLetters++;
			}
		}
		return countOfCapitalLetters;
	}

	/**
	 * @param str
	 * @param countOfUpperCaseLetter
	 * @return
	 */
	static String[] splitCamelCaseString(final String str, final int countOfUpperCaseLetter)
	{
		//
		final String[] splitStrings = new String[countOfUpperCaseLetter + Constants.ONE];

		final char[] chars = str.toCharArray();
		int firstIndex = 0;
		int lastIndex = 0;

		int splitStrCount = 0;

		for (int i = Constants.ONE; i < chars.length; i++) // change indexing from "chars"
		// 1 to length
		{
			final char character = chars[i];
			char nextCharacter;
			char previousCharacter;
			if (splitStrCount != countOfUpperCaseLetter)
			{
				if (Character.isUpperCase(character))
				{
					if (i == (chars.length - Constants.ONE))
					{
						splitStrings[splitStrCount++] = str.substring(0, i);

						final char[] lasrCharIsUpperCase = new char[Constants.ONE];
						lasrCharIsUpperCase[0] = character;
						splitStrings[splitStrCount++] = new String(lasrCharIsUpperCase);
					}
					else
					{
						lastIndex = i;

						previousCharacter = chars[i - Constants.ONE];
						nextCharacter = chars[i + Constants.ONE];
						if (Character.isUpperCase(previousCharacter)
							&& Character.isLowerCase(nextCharacter)
							|| Character.isLowerCase(previousCharacter)
							&& Character.isUpperCase(nextCharacter)
							|| (Character.isLowerCase(previousCharacter) && Character
								.isLowerCase(nextCharacter)))
						{
							String split = str.substring(firstIndex, lastIndex);
							if (splitStrCount == Constants.ZERO)
							{
								split = capitalizeFirstCharacter(split);
							}
							splitStrings[splitStrCount] = split;
							splitStrCount++;
							firstIndex = lastIndex;
						}
						else
						{
							continue;
						}
					}
				}
			}
			else
			{
				firstIndex = lastIndex;
				lastIndex = str.length();
				final String split = str.substring(firstIndex, lastIndex);
				splitStrings[splitStrCount] = split;
				break;
			}

		}

		return splitStrings;
	}

	/**
	 * Utility method to get a formated string.
	 * @param splitStrings string array
	 * @return formatted string
	 */
	public static String getFormattedString(final String[] splitStrings)
	{
		StringBuffer returner = new StringBuffer();
		for (int i = 0; i < splitStrings.length; i++)
		{
			final String str = splitStrings[i];
			if (i == splitStrings.length - Constants.ONE)
			{
				returner = returner.append(str);
			}
			else
			{
				returner = returner.append(str).append(" ");
			}
		}
		return returner.toString();
	}

	/**
	 * Utility method to capitalize first character in the String.
	 * @param str capitalize first character of string
	 * @return String
	 */

	public static String capitalizeFirstCharacter(final String str)
	{
		final char[] chars = str.toCharArray();
		final char firstChar = chars[0];
		chars[0] = Character.toUpperCase(firstChar);
		return new String(chars);
	}

	/**
	 * @param relationalOperator Input Relational Operator
	 * @return Display string for given operator
	 */
	public static String displayStringForRelationalOperator(final RelationalOperator relationalOperator)
	{
		return relationalOperator.getStringRepresentation();
	}

	/**
	 * TODO need to refactored.
	 * @return AbstractQueryExecutor
	 */
	public static AbstractQueryExecutor getQueryExecutor()
	{
		AbstractQueryExecutor queryExecutor;

		final String appName = AdvanceQueryDAO.getInstance().getAppName();
		final IDAOFactory daoFactory = DAOConfigFactory.getInstance().getDAOFactory(appName);
		if ("MYSQL".equals(daoFactory.getDataBaseType()))
		{
			queryExecutor = new MysqlQueryExecutor();
		}
		else if ("ORACLE".equals(daoFactory.getDataBaseType()))
		{
			queryExecutor = new OracleQueryExecutor();
		}
		else
		{
			queryExecutor = new Db2QueryExecuter();
		}
		return queryExecutor;
	}

	/**
	 * Forms display name for attribute as className : attribute name.
	 * @param attribute AttributeInterface
	 * @return columnDisplayName
	 */
	public static String getDisplayNameForColumn(final QueryableAttributeInterface attribute)
	{
		String className = attribute.getActualEntity().getName();
		className = edu.wustl.common.util.Utility.getDisplayLabel(className);
		final String attributeLabel = edu.wustl.common.util.Utility.getDisplayLabel(attribute.getName());
		return className + " : " + attributeLabel;
	}

	/**
	 * It will create the tree & output tree node list for the given Query.
	 * @param  query object of IQuery to validate
	 * @param request Object of HttpServletRequest
	 * @throws QueryModuleException Query Module Exception
	 */
	public static void createRootOutPutTree(final IQuery query, final HttpServletRequest request)
			throws QueryModuleException
	{
		final HttpSession session = request.getSession();
		final ResultsViewComponentGenerator resultViewGenerator = new ResultsViewComponentGenerator(query);
		final List<OutputTreeDataNode> rootOutputNodeList = resultViewGenerator.getRootOutputTreeNode();
		final Map<String, OutputTreeDataNode> uniqueIdNodesMap = resultViewGenerator
				.getAllChildrenNodes(rootOutputNodeList);
		final QueryDetails queryDetailsObj = new QueryDetails(session);
		queryDetailsObj.setUniqueIdNodesMap(uniqueIdNodesMap);

		session.setAttribute(Constants.SAVE_TREE_NODE_LIST, rootOutputNodeList);
		session.setAttribute(Constants.NO_OF_TREES, Long.valueOf(rootOutputNodeList.size()));
		session.setAttribute(Constants.ID_NODES_MAP, uniqueIdNodesMap);
	}

	/**
	 * Method to validate query and return validation message.
	 * @param request HttpServletRequest object
	 * @param parameterizedQuery query to validate
	 * @return validationMessage validation message
	 * @throws QueryModuleException query exception
	 */
	public static String getValidationMessage(
			final HttpServletRequest request, final IQuery parameterizedQuery)
			throws QueryModuleException
	{
		String validationMessage;
		validationMessage = ValidateQueryBizLogic.validateRuleInDAG(parameterizedQuery);
		validationMessage = ValidateQueryBizLogic.validateDQForMultiParents(parameterizedQuery,
				validationMessage);

		if (validationMessage != null)
		{
			return validationMessage;
		}

		final String pageOf = request.getParameter(Constants.PAGE_OF);
		final String isWorkflow = request.getParameter("isWorkflow");
		String queryType = null;
		if (parameterizedQuery != null)
		{
			queryType = ((Query) parameterizedQuery).getType();
		}
		if ("true".equals(isWorkflow))
		{
			request.setAttribute("isWorkflow", "true");
		}
		final String queryTitle = request.getParameter("queyTitle");
		validationMessage = ValidateQueryBizLogic.getValidationMessage(pageOf, queryTitle,
				queryType, parameterizedQuery);

		return validationMessage;
	}

	/**
	 * It will create a new Rule with the same conditions as in originalRule.
	 * @param originalRule original rule
	 * @return new rule.
	 */
	public static IRule createNewRuleFromOldRule(final IRule originalRule)
	{
		IRule newRule = null;
		if (originalRule != null)
		{
			newRule = QueryObjectFactory.createRule();
			for (final ICondition condition : originalRule)
			{
				final QueryableAttributeInterface queryableAttribute =
					condition.getAttribute();
				final ICondition newCondition =
						QueryObjectFactory.createCondition(queryableAttribute,
						condition.getRelationalOperator(), condition.getValues());
				newRule.addCondition(newCondition);

			}
		}
		return newRule;
	}

	/**
	 *
	 * Set the parameters for last execution.
	 * @param parameterizedQuery query object
	 * @param queryExeId  query Execution Id
	 * @return IParameterizedQuery
	 * @throws BizLogicException  BizLogicException.
	 */
	public static IParameterizedQuery setParameterForRecentQuey(
			final IParameterizedQuery parameterizedQuery,
			final Long queryExeId) throws BizLogicException
	{
		final Execution execution = Utility.retrieveParametersForExecution(queryExeId);
		IParameterizedQuery tempQuery = parameterizedQuery;
		if (execution != null)
		{
			final IConstraints constraints = execution.getConstraints();
			final List<IOutputTerm> outputTerms = execution.getOutputTerms();
			//parameter with execution
			final List<IParameter<?>> parametersForExecution = execution.getParameters();

			IParameterizedQuery newQuery;
			final IConstraintsObjectBuilderInterface queryObject = new ConstraintsObjectBuilder();
			newQuery = (ParameterizedQuery) queryObject.getQuery();

			//Adding all constraints to new Query
			newQuery.setConstraints(constraints);

			//Adding all Parameters
			newQuery.getParameters().addAll(parametersForExecution);

			//Adding all output terms
			newQuery.getOutputTerms().addAll(outputTerms);
			newQuery.setId(parameterizedQuery.getId());
			newQuery.setCreatedBy(parameterizedQuery.getCreatedBy());
			newQuery.setCreatedDate(parameterizedQuery.getCreatedDate());
			newQuery.setName(parameterizedQuery.getName());
			newQuery.setDescription(parameterizedQuery.getDescription());
			newQuery.setIsSystemGenerated(parameterizedQuery.getIsSystemGenerated());
			newQuery.setType(parameterizedQuery.getType());
			newQuery.setOutputAttributeList(parameterizedQuery.getOutputAttributeList());
			newQuery.setUpdatedBy(parameterizedQuery.getUpdatedBy());
			newQuery.setUpdationDate(parameterizedQuery.getUpdationDate());
			tempQuery = newQuery;
		}
		return tempQuery;
	}

	/**
	 * retrieve parameters for given queryExeId.
	 * @param queryExeId query execution id.
	 * @return Execution
	 * @throws BizLogicException exception
	 */
	public static Execution retrieveParametersForExecution(final Long queryExeId)
			throws BizLogicException
	{

		HibernateDAO queryDao = null;
		Execution execution = null;
		try
		{
			queryDao = DAOUtil.getHibernateDAO(null);
			execution = (Execution) queryDao.retrieveById(Execution.class.getName(), queryExeId);
		}
		catch (final Exception e)
		{
			logger.error(e.getMessage());
		}
		finally
		{
			try
			{
				queryDao.closeSession();
			}
			catch (final DAOException e)
			{
				logger.error(e.getMessage());
			}
		}
		return execution;
	}

	/**
	 * this method set the parameters to query for latest execution.
	 * @param parameterizedQuery query object
	 * @param queryExeId execution id
	 * @param executionDate date of execution
	 * @throws BizLogicException exception
	 * @return newQuery
	 */
	public static IParameterizedQuery setParametersForLatestExecution(
			final IParameterizedQuery parameterizedQuery,
			final Long queryExeId, final Date executionDate)
			throws BizLogicException
	{
		IParameterizedQuery newQuery;

		final Date lastUpdationDate = parameterizedQuery.getUpdationDate();

		if (executionDate.after(lastUpdationDate))
		{
			// take output attributes from retrieved Query
			newQuery = Utility.setParameterForRecentQuey(parameterizedQuery, queryExeId);
		}
		else
		{
			newQuery = parameterizedQuery;
		}
		return newQuery;
	}

	/**
	 * this method returns the Parameterized query for the given query id.
	 *
	 * @param queryId query id
	 * @return parameterizedQuery
	 * @throws BizLogicException exception
	 */
	public static IParameterizedQuery retrieveQuery(final Long queryId) throws BizLogicException
	{
		final IFactory factory = AbstractFactoryConfig.getInstance().getBizLogicFactory();
		final IBizLogic bizLogic = factory.getBizLogic(Constants.QUERY_INTERFACE_BIZLOGIC_ID);

		IParameterizedQuery parameterizedQuery = null;
		final List<IParameterizedQuery> queryList = bizLogic.retrieve(ParameterizedQuery.class
				.getName(), Constants.ID, queryId);

		if (queryList != null && !queryList.isEmpty())
		{
			parameterizedQuery = queryList.get(0);
		}
		return parameterizedQuery;
	}

	/**
	 * returns latest execution id map.
	 * @param request HttpServletRequest
	 * @param workflowId work-flow id
	 * @return executionIdMap executionIdMap
	 * @throws BizLogicException BizLogicException
	 */
	public static Map<Long, Long> generateQueryExecIdMap
					(final HttpServletRequest request, final Long workflowId)
			throws BizLogicException
	{
		Map<Long, Long> executionIdMap = new HashMap<Long, Long>();
		final WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
		final SessionDataBean sessionDataBean = (SessionDataBean) request.getSession().getAttribute(
				edu.wustl.common.util.global.Constants.SESSION_DATA);
		final Long projectId = Long.valueOf(request.getParameter(Constants.SELECTED_PROJECT));

		executionIdMap = workflowBizLogic.generateQueryExecIdMap(sessionDataBean.getUserId(),
				workflowId, projectId);
		return executionIdMap;
	}

	/**
	 * returns latest execution id map.
	 * @param queryExeIdList list of query execution id.
	 * @return executionDateMap execution Date Map
	 * @throws BizLogicException exception
	 */
	public static Map<Long, Date> generateQueryExecDateMap(final Collection<Long> queryExeIdList)
			throws BizLogicException
	{
		final WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
		return workflowBizLogic.getDateForLatestExecution(queryExeIdList);
	}

	/**
	 * returns latest execution id map.
	 * @param queryExeIdList list of query execution ids.
	 * @return executionDateMap
	 * @throws BizLogicException exception
	 * @throws SQLException  SQLException
	 * @throws DAOException  DAOException
	 */
	public static List<Date> generateQueryExecDate(final Long queryExeIdList) throws BizLogicException,
			DAOException, SQLException
	{
		final ITableManager itableManager = ITableManagerFactory.getDefaultITableManager();
		return itableManager.getDateForLatestExecution(queryExeIdList);
	}

	/**
	 * It will add the path between the first exprId & childEntityExprId using the queryObject.
	 * @param exprId first expression through which path is going to start
	 * @param childEntityExprId second expression to which first expression should be connected
	 * @param queryObject object
	 */
	public static void addPath(final Integer exprId, final Integer childEntityExprId,
			final IClientQueryBuilderInterface queryObject)
	{
		final IQuery query = queryObject.getQuery();
		final AmbiguityObject ambiguityObject = new AmbiguityObject(query.getConstraints().getExpression(
				exprId).getQueryEntity().getDynamicExtensionsEntity().getEntity(), query
				.getConstraints().getExpression(childEntityExprId).getQueryEntity()
				.getDynamicExtensionsEntity().getEntity());
		final IPathFinder pathFinder = new CommonPathFinder();
		final DAGResolveAmbiguity resolveAmbigity = new DAGResolveAmbiguity(ambiguityObject, pathFinder);
		final Map<AmbiguityObject, List<IPath>> map = resolveAmbigity.getPathsForAllAmbiguities();
		final List<IPath> pathList = map.get(ambiguityObject);
		if (!queryObject.isPathCreatesCyclicGraph(exprId, childEntityExprId, pathList.get(0)))
		{
			QueryAddContainmentsUtil.linkTwoNodes(exprId, childEntityExprId, pathList.get(0),
					queryObject);
		}
	}



	/**
	 * It will search the Parameter from the parameterizedQuery which is created for
	 * the given condition. If found will return that parameter else will return null.
	 * @param condition condition object
	 * @param parameterizedQuery parameterized query
	 * @return IParameter
	 */
	public static IParameter<?> getParameterForCondition(final ICondition condition,
			final IParameterizedQuery parameterizedQuery)
	{
		final List<IParameter<?>> parameterList = parameterizedQuery.getParameters();
		IParameter<?> parameter = null;
		for (final IParameter<?> oldParameter : parameterList)
		{
			if (condition.equals(oldParameter.getParameterizedObject()))
			{
				parameter = oldParameter;
				break;
			}
		}
		return parameter;
	}

	/**
	 * It will search for the similar (equal) ICondition in the conditionsList for the
	 * given condition. If found will return that else will return null.
	 * @param condition condition object
	 * @param conditionsList conditions list
	 * @return Condition
	 */
	public static ICondition getSimilarConditionFromConditionList(final ICondition condition,
			final List<ICondition> conditionsList)
	{
		ICondition newCondition = null;
		for (final ICondition editedCondition : conditionsList)
		{
			if (editedCondition.getAttribute().equals(condition.getAttribute()))
			{
				newCondition = editedCondition;
				break;
			}
		}
		return newCondition;
	}

	/**
	 * retrieve Workflow.
	 * @param workflowId workflow Id
	 * @return Workflow object
	 * @throws DAOException DAOException
	 */
	public static Workflow retrieveWorkflow(final Long workflowId) throws DAOException
	{
		HibernateDAO dao = null;
		Workflow workflow = null;
		try
		{

			dao = DAOUtil.getHibernateDAO(null);
			workflow = (Workflow) dao.retrieveById(Workflow.class.getName(), workflowId);
		}
		catch (final Exception e)
		{
			logger.error(e.getMessage());
		}
		finally
		{
			if (dao != null)
			{
				dao.closeSession();
			}
		}
		return workflow;
	}

	/**
	 * @param userName user Name
	 * @param objectId object Id
	 * @param privilege privilege
	 * @return hasPrivilege
	 */
	public static boolean hasPrivilege(final String userName, final String objectId, final String privilege)
	{
		boolean hasPrivilege = true;
		try
		{
			final PrivilegeManager privilegeManager = PrivilegeManager.getInstance();

			final PrivilegeCache privilegeCache = privilegeManager.getPrivilegeCache(userName);
			hasPrivilege = privilegeCache.hasPrivilege(objectId, privilege);
		}
		catch (final SMException e)
		{
			logger.debug(e);
		}
		return hasPrivilege;

	}

	/**
	 * It will update the given condition to the empty condition.
	 * @param condition condition to be updated.
	 */
	public static void updateConditionToEmptyCondition(final ICondition condition)
	{
		try
		{
			final String operator = getDefaultOperatorForAttribute(condition.getAttribute());
			condition.setRelationalOperator(RelationalOperator
					.getOperatorForStringRepresentation(operator));
			condition.getValues().clear();
			if (condition.getRelationalOperator().getStringRepresentation().equalsIgnoreCase(
					Constants.Between))
			{
				condition.addValue("");
				condition.addValue("");
			}
			else
			{
				condition.addValue("");
			}
		}
		catch (final CheckedException e)
		{
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * This method will return the Default operator which should be used to form the
	 * Empty conditions.
	 * @param attribute for which the operator is needed.
	 * @return the operator.
	 * @throws CheckedException exception.
	 */
	private static String getDefaultOperatorForAttribute(final QueryableAttributeInterface attribute)
			throws CheckedException
	{
		String operator;
		if (attribute.isTagPresent(Constants.TAGGED_VALUE_VI_HIDDEN))
		{
			operator = RelationalOperator.In.getStringRepresentation();
		}
		else
		{
			final ParseXMLFile parseFile = ParseXMLFile.getInstance(Constants.DYNAMIC_UI_XML);
			operator = HtmlUtility.getConditionsList(attribute, parseFile).get(0);
		}
		return operator;
	}

	/**
	 * set Special Characters for like query.
	 * @param escapeSquence  escape sequence
	 * @param queryNameLikeUpper query name like
	 * @return String value
	 */
	public static String setSpecialCharacters(final StringBuffer escapeSquence, String queryNameLikeUpper)
	{
		if (queryNameLikeUpper.contains("'"))
		{
			// replace "'" with "''"
			queryNameLikeUpper =
				queryNameLikeUpper.replaceAll("'", "''");
		}
		if (queryNameLikeUpper.contains("%"))
		{
			// replace "'" with "''"
			queryNameLikeUpper =
				queryNameLikeUpper.replaceAll("%", "/%");
			escapeSquence.append("ESCAPE '/'");

		}
		if (queryNameLikeUpper.contains("_"))
		{
			// replace "'" with "''"
			queryNameLikeUpper =
				queryNameLikeUpper.replaceAll("_", "/_");
			escapeSquence.append("ESCAPE '/'");

		}
		return queryNameLikeUpper;
	}

	/**
	 * @param query IQuery object
	 * @return boolean for is rule present
	 */
	public static boolean checkIfRulePresentInDag(final IQuery query)
	{
		boolean isRulePresentInDag = false;

		if (query != null)
		{
			final IConstraints constraints = query.getConstraints();
			for (final IExpression expression : constraints)
			{
				if (expression.containsRule())
				{
					isRulePresentInDag = true;
					break;
				}
			}
		}
		return isRulePresentInDag;
	}
	/**
	 * @param query query String
	 * @return result of query
	 * @throws DAOException DAOException
	 */
	public static Collection executeQuery(final String query) throws DAOException
	{
		HibernateDAO hibernateDAO = null;
		Collection result=null;
		try
		{
			hibernateDAO = DAOUtil.getHibernateDAO(null);
			if(query!=null&&!query.equals(""))
			{
				result= hibernateDAO.executeQuery(query);
			}
		}
		catch (final Exception e)
		{
			logger.error(e);
		}
		finally
		{
			DAOUtil.closeHibernateDAO(hibernateDAO);
		}
		return result;
	}


	/**
	 * generate privilege.
	 * @param request HttpServletRequest
	 * @return CiderQueryPrivilege object
	 */
	public static CiderQueryPrivilege getPrivilegeFromSession(final HttpServletRequest request)
	{
		CiderQueryPrivilege privilege;
		final HttpSession session = request.getSession();
		if (session.getAttribute(Constants.QUERY_PRIVILEGE) == null)
		{
			final AbstractQueryUIManager queryUIManager = AbstractQueryUIManagerFactory
			.getDefaultAbstractUIQueryManager();
			privilege = (CiderQueryPrivilege) queryUIManager.getPrivilege(request);

		}
		else
		{
			privilege = (CiderQueryPrivilege) session.getAttribute(Constants.QUERY_PRIVILEGE);

		}
		return privilege;
	}

	/**
	 * Check if data type is numeric.
	 * @param dataType type of attribute
	 * @return boolean
	 */
	public static boolean isNumber(final String dataType)
	{
		boolean isNumber = false;
		for (int i = 0; i < Constants.NUMBER.length; i++)
		{
			if (dataType.equalsIgnoreCase(Constants.NUMBER[i]))
			{
				isNumber = true;
				break;
			}
		}
		return isNumber;
	}
	/**
	 * @param query sql query
	 * @return result list
	 * @throws DAOException DAOException
	 */
	public static List executeSQl(final String query) throws DAOException
	{
		JDBCDAO jdbcDao = null;
		jdbcDao = DAOUtil.getJDBCDAO(null);
		final List results = jdbcDao.executeQuery(query);
		return results;
	}
	/**
	 * @param query sql query
	 * @throws DAOException DAOException
	 */
	public static void executeUpdate(final String query) throws DAOException
	{
		JDBCDAO jdbcDao = null;
		jdbcDao = DAOUtil.getJDBCDAO(null);
		jdbcDao.executeUpdate(query);
	}
	/**
	 * Generate template items.
	 *
	 * @param request the request
	 *
	 * @return the list< workflow item>
	 *
	 * @throws BizLogicException the BizLogic exception
	 */
	public static List<WorkflowItem> generateTemplateItems(
			final HttpServletRequest request)
			throws BizLogicException
	{
		final List<WorkflowItem> workflowItemList=new ArrayList<WorkflowItem>();
		final List<Long> queryIds;
		if(request.getAttribute("queryIds")==null)
		{
			queryIds=new ArrayList<Long>();

		}
		else
		{
			  queryIds=(List<Long>)request.getAttribute("queryIds");
		}
		for(int i=0;i<queryIds.size();i++)
		{
			final Long queryId=queryIds.get(i);
			final IParameterizedQuery parameterizedQuery = retrieveQuery(queryId);
			if(parameterizedQuery==null)
			{
				logger.info("No query found with identifier = " + queryId);
			}
			else
			{
				final WorkflowItem workflowItem=new WorkflowItem();
				workflowItem.setPosition(i);
				workflowItem.setQuery(parameterizedQuery);
				workflowItemList.add(workflowItem);
			}
		}
		return workflowItemList;

	}

}
