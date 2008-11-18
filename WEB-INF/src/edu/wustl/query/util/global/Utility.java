
package edu.wustl.query.util.global;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.common.dynamicextensions.domaininterface.TaggedValueInterface;
import edu.common.dynamicextensions.entitymanager.EntityManagerConstantsInterface;
import edu.wustl.common.beans.QueryResultObjectData;
import edu.wustl.common.beans.QueryResultObjectDataBean;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.bizlogic.QueryBizLogic;
import edu.wustl.common.dao.QuerySessionData;
import edu.wustl.common.dao.queryExecutor.PagenatedResultData;
import edu.wustl.common.querysuite.queryobject.LogicalOperator;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.bizlogic.QueryOutputSpreadsheetBizLogic;

public class Utility extends edu.wustl.common.util.Utility
{

	private static String pattern = "MM-dd-yyyy";

	public static Object toNewGridFormat(Object obj)
	{
		obj = toGridFormat(obj);
		if (obj instanceof String)
		{
			String objString = (String) obj;
			StringBuffer tokenedString = new StringBuffer();

			StringTokenizer tokenString = new StringTokenizer(objString, ",");

			while (tokenString.hasMoreTokens())
			{
				tokenedString.append(tokenString.nextToken() + " ");
			}
			String gridFormattedStr = new String(tokenedString);
			obj = gridFormattedStr;
		}

		return obj;
	}

	/**
	 * Adds the attribute values in the list in sorted order and returns the
	 * list containing the attribute values in proper order
	 * 
	 * @param dataType -
	 *            data type of the attribute value
	 * @param value1 -
	 *            first attribute value
	 * @param value2 -
	 *            second attribute value
	 * @return List containing value1 and valu2 in sorted order
	 */
	public static ArrayList<String> getAttributeValuesInProperOrder(String dataType, String value1,
			String value2)
	{
		String v1 = value1;
		String v2 = value2;
		ArrayList<String> attributeValues = new ArrayList<String>();
		if (dataType.equalsIgnoreCase(EntityManagerConstantsInterface.DATE_ATTRIBUTE_TYPE))
		{
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			try
			{
				Date date1 = df.parse(value1);
				Date date2 = df.parse(value2);
				if (date1.after(date2))
				{
					v1 = value2;
					v2 = value1;
				}
			}
			catch (ParseException e)
			{
				Logger.out
						.error("Can not parse the given date in getAttributeValuesInProperOrder() method :"
								+ e.getMessage());
				e.printStackTrace();
			}
		}
		else
		{
			if (dataType.equalsIgnoreCase(EntityManagerConstantsInterface.INTEGER_ATTRIBUTE_TYPE)
					|| dataType
							.equalsIgnoreCase(EntityManagerConstantsInterface.LONG_ATTRIBUTE_TYPE))
			{
				if (Long.parseLong(value1) > Long.parseLong(value2))
				{
					v1 = value2;
					v2 = value1;
				}

			}
			else
			{
				if (dataType
						.equalsIgnoreCase(EntityManagerConstantsInterface.DOUBLE_ATTRIBUTE_TYPE))
				{
					if (Double.parseDouble(value1) > Double.parseDouble(value2))
					{
						v1 = value2;
						v2 = value1;
					}

				}
			}
		}
		attributeValues.add(v1);
		attributeValues.add(v2);
		return attributeValues;
	}

	public static Object toNewGridFormatWithHref(List<String> row,
			Map<Integer, QueryResultObjectData> hyperlinkColumnMap, int index)
	{
		Object obj = row.get(index);

		if (obj instanceof String)
		{
			obj = toNewGridFormat(obj);

			QueryResultObjectData queryResultObjectData = hyperlinkColumnMap.get(index);

			if (queryResultObjectData != null)// This column is to be shown as
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
				String aliasName = queryResultObjectData.getAliasName();
				String link = "SimpleSearchEdit.do?"
						+ edu.wustl.common.util.global.Constants.TABLE_ALIAS_NAME + "=" + aliasName
						+ "&" + Constants.SYSTEM_IDENTIFIER + "="
						+ row.get(queryResultObjectData.getIdentifierColumnId()) + "&"
						+ Constants.PAGE_OF + "=" + Variables.aliasAndPageOfMap.get(aliasName);
				/**
				 * bug ID: 4225 Patch id: 4225_1 Description : Passing a
				 * different name to the popup window
				 */
				String onclickStr = " onclick=javascript:NewWindow('" + link
						+ "','simpleSearch','800','600','yes') ";
				String hrefTag = "<a class='normalLink' href='#'" + onclickStr + ">" + obj + "</a>";
				// String hrefTag = "<a href='"+ link+ "'>"+obj+"</a>";
				obj = hrefTag;
			}
		}
		return obj;
	}

	/**
	 * This method creates a comma separated string of numbers representing
	 * column width.
	 * 
	 */
	public static String getColumnWidth(List columnNames)
	{
		String colWidth = getColumnWidth((String) columnNames.get(0));

		for (int col = 1; col < columnNames.size(); col++)
		{
			String columnName = (String) columnNames.get(col);
			colWidth = colWidth + "," + getColumnWidth(columnName);
		}
		return colWidth;
	}

	private static String getColumnWidth(String columnName)
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
			columnWidth = "80";
		}
		return columnWidth;
	}

	/**
	 * limits the title of the saved query to 125 characters to avoid horizontal
	 * scrollbar
	 * 
	 * @param title -
	 *            title of the saved query (may be greater than 125 characters)
	 * @return - query title upto 125 characters
	 */

	public static String getQueryTitle(String title)
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
	public static String getTooltip(String title)
	{
		String tooltip = title.replaceAll("'", Constants.SINGLE_QUOTE_ESCAPE_SEQUENCE); // escape sequence
		// for '
		return tooltip;
	}

	public static List getPaginationDataList(HttpServletRequest request,
			SessionDataBean sessionData, int recordsPerPage, int pageNum,
			QuerySessionData querySessionData) throws DAOException
	{
		List paginationDataList;
		querySessionData.setRecordsPerPage(recordsPerPage);
		int startIndex = recordsPerPage * (pageNum - 1);
		QueryBizLogic qBizLogic = new QueryBizLogic();
		PagenatedResultData pagenatedResultData = qBizLogic.execute(sessionData, querySessionData,
				startIndex);
		paginationDataList = pagenatedResultData.getResult();
		String isSimpleSearch = (String) request.getSession().getAttribute(
				edu.wustl.common.util.global.Constants.IS_SIMPLE_SEARCH);
		if (isSimpleSearch == null || (!isSimpleSearch.equalsIgnoreCase(Constants.TRUE)))
		{
			Map<Long, QueryResultObjectDataBean> queryResultObjectDataBeanMap = querySessionData
					.getQueryResultObjectDataMap();
			if (queryResultObjectDataBeanMap != null)
			{
				for (Iterator<Long> beanMapIterator = queryResultObjectDataBeanMap.keySet()
						.iterator(); beanMapIterator.hasNext();)
				{
					Long id = beanMapIterator.next();
					QueryResultObjectDataBean bean = queryResultObjectDataBeanMap.get(id);
					if (bean.isClobeType())
					{
						List<String> columnsList = (List<String>) request.getSession()
								.getAttribute(Constants.SPREADSHEET_COLUMN_LIST);
						QueryOutputSpreadsheetBizLogic queryBizLogic = new QueryOutputSpreadsheetBizLogic();
						Map<Integer, Integer> fileTypeIndexMainEntityIndexMap = queryBizLogic
								.updateSpreadSheetColumnList(columnsList,
										queryResultObjectDataBeanMap);
						//	QueryOutputSpreadsheetBizLogic.updateDataList(paginationDataList, fileTypeIndexMainEntityIndexMap);
						Map exportMetataDataMap = QueryOutputSpreadsheetBizLogic.updateDataList(
								paginationDataList, fileTypeIndexMainEntityIndexMap);
						request.getSession().setAttribute(Constants.ENTITY_IDS_MAP,
								exportMetataDataMap.get(Constants.ENTITY_IDS_MAP));
						request.getSession().setAttribute(Constants.EXPORT_DATA_LIST,
								exportMetataDataMap.get(Constants.EXPORT_DATA_LIST));
						break;
					}
				}
			}
		}
		return paginationDataList;
	}

	/**
	 * @param entity entity for which Primary Key list is required
	 * @return List<String> primary key list
	 */
	public static List<String> getPrimaryKey(EntityInterface entity)
	{
		Collection<TaggedValueInterface> taggedValueCollection = entity.getTaggedValueCollection();
		List<String> primaryKeyList = new ArrayList<String>();
		for (TaggedValueInterface tag : taggedValueCollection)
		{
			if (Constants.PRIMARY_KEY_TAG_NAME.equals(tag.getKey()))
			{
				StringTokenizer stringTokenizer = new StringTokenizer(tag.getValue(), ",");
				for (int i = 0; stringTokenizer.hasMoreTokens(); i++)
				{
					primaryKeyList.add(stringTokenizer.nextToken());
				}
			}
		}
		return primaryKeyList;
	}

	/**
	 * Method to generate SQL for Node
	 * @param parentNodeId parent node id which contains data
	 * @param tableName temporary table name
	 * @param parentIdColumnName parent node ID column names (, separated)
	 * @param selectSql SQL 
	 * @param idColumnOfCurrentNode id column name (, separated) of current node
	 * @return SQL for current node
	 */
	public static String getSQLForNode(String parentNodeId, String tableName,
			String parentIdColumnName, String selectSql, String idColumnOfCurrentNode)
	{
		selectSql = selectSql + Constants.FROM + tableName;
		if (parentNodeId != null)
		{
			selectSql = selectSql + Constants.WHERE + " (";
			StringTokenizer stringTokenizerParentID = new StringTokenizer(parentIdColumnName, ",");
			StringTokenizer stringTokenizerParentNodeID = new StringTokenizer(parentNodeId,
					Constants.PRIMARY_KEY_ATTRIBUTE_SEPARATOR);
			while (stringTokenizerParentID.hasMoreElements())
			{
				selectSql = selectSql + stringTokenizerParentID.nextElement() + " = '"
						+ stringTokenizerParentNodeID.nextElement() + "' " + LogicalOperator.And
						+ " ";
			}
			StringTokenizer stringTokenizerIDofCurrentNode = new StringTokenizer(
					idColumnOfCurrentNode, ",");
			while (stringTokenizerIDofCurrentNode.hasMoreElements())
			{
				selectSql = selectSql + " " + stringTokenizerIDofCurrentNode.nextElement() + " "
						+ RelationalOperator.getSQL(RelationalOperator.IsNotNull) + " "
						+ LogicalOperator.And;
			}
			if (selectSql.substring(selectSql.length() - 3).equals(LogicalOperator.And.toString()))
			{
				selectSql = selectSql.substring(0, selectSql.length() - 3);
			}
			selectSql = selectSql + ")";
		}
		return selectSql;
	}

}
