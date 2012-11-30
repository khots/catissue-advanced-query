
package edu.wustl.query.bizlogic;

import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.tags.bizlogic.ITagBizlogic;
import edu.wustl.common.tags.dao.TagDAO;
import edu.wustl.common.tags.domain.Tag;
import edu.wustl.common.tags.domain.TagItem;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.util.global.AQConstants;

public class QueryTagBizLogic implements ITagBizlogic
{

	/**
	 * Insert New Tag to the database.
	 * @param entityName from hbm file.
	 * @param label for new Tag.
	 * @param userId.
	 * @return tag identifier.
	 * @throws DAOException,BizLogicException.
	 */


	public long createNewTag(String entityName, String label, long userId) throws DAOException,
			BizLogicException
	{
		Tag tag = new Tag();
		tag.setLabel(label);
		tag.setUserId(userId);
		TagDAO tagDao = new TagDAO(entityName);
		tagDao.insertTag(tag);
		return tag.getIdentifier();
	}

	/**
	 * Assign the Query to existing folder.
	 * @param entityName from hbm file.
	 * @param tagId.
	 * @param objId.
	 * @throws DAOException,BizLogicException.
	 */
	
	public void assignTag(String entityName, long tagId, long objId) throws DAOException,
			BizLogicException
	{
		Tag tag = new Tag();
		tag.setIdentifier(tagId);
		TagItem<ParameterizedQuery> tagItem = new TagItem<ParameterizedQuery>();
  
		tagItem.setTag(tag);
		tagItem.setObjId(objId);

		TagDAO<ParameterizedQuery> tagDao = new TagDAO<ParameterizedQuery>(entityName);
		tagDao.insertTagItem(tagItem);
	}

	/**
	 * Get list of Tags from the database.
	 * @param entityName from hbm file.
	 * @param obj Object to be inserted in database
	 * @throws DAOException,BizLogicException.
	 */

	public List<Tag> getTagList(String entityName) throws DAOException, BizLogicException
	{
		List<Tag> tagList = null;
		TagDAO tagDao = null;
		tagDao = new TagDAO(entityName);
		tagList = tagDao.getTags();
		return tagList;
	}

 	/**
	 * Get Tag object.
	 * @param entityName from hbm file.
	 * @param  tagId.
	 * @return Tag Object.
	 * @throws DAOException,BizLogicException.
	 */

	public Tag getTagById(String entityName, long tagId) throws DAOException, BizLogicException
	{
		TagDAO tagDao = new TagDAO(entityName);
		Tag tag = tagDao.getTagById(tagId);
		return tag;
	}

	/**
	 * Get the Set of TagItems.
	 * @param entityName from hbm file.
	 * @param tagId.
	 * @return Set<TagItem>.
	 * @throws DAOException,BizLogicException.
	 */
	public Set<TagItem> getTagItemByTagId(String entityName, long tagId) throws BizLogicException
	{
		TagDAO tagDao = new TagDAO(entityName);
		Set<TagItem> tagItem = tagDao.getTagItemBytagId(tagId);
		return tagItem;
	}

	 
	/**
	 * Delete the Tag from database.
	 * @param entityName from hbm file.
	 * @param tagId to retrieve TagItem Object and delete it from database.
	 * @throws DAOException,BizLogicException.
	 */

	public void deleteTag(String entityName, long tagId) throws DAOException, BizLogicException
	{
		TagDAO tagDao = new TagDAO(entityName); 
		Tag tag = tagDao.getTagById(tagId); 
		tagDao.deleteTag(tag);

	}
	/**
	 * Delete the Tag Item from database.
	 * @param entityName from hbm file.
	 * @param objId to retrieve TagItem Object and delete it from database.
	 * @throws DAOException,BizLogicException.
	 */


	public void deleteTagItem(String entityName, long itemId) throws DAOException,
			BizLogicException
	{
		TagDAO tagDAO = new TagDAO(entityName);
		TagItem tagItem = tagDAO.getTagItemById(itemId);
		tagDAO.deleteTagItem(tagItem);
	}
	
	/**
	 * Get queries from database.
	 * @param Set<TagItem> tagItemList.
	 * @throws DAOException.
	 */
	public List<List<String>> getQueries(long tagId) throws DAOException, BizLogicException
	{
		CommonQueryBizLogic commonQueryBizLogic = new CommonQueryBizLogic();
		List<List<String>> result = commonQueryBizLogic.getQueries(tagId);
		return result;
	}

	/**
	 * Get the Tag Item from database for Tree Grid.
	 * @param entityName from hbm file.
	 * @param  tagId.
	 * @return Json Object.
	 * @throws DAOException,BizLogicException.
	 */
	public JSONObject getJSONObj(String entityName, long tagId) throws DAOException,
			BizLogicException
	{

		JSONObject arrayObj = new JSONObject();
		try
		{
			JSONArray treeData = new JSONArray();
			int childCount = 0;
			List<List<String>> result = getQueries(tagId); 
			for (List<String> row : result)
			{
 				JSONObject obj = new JSONObject();
				obj.put(AQConstants.IDENTIFIER, Long.parseLong(row.get(0)));
				obj.put(AQConstants.NAME, row.get(2));
				childCount++;
				treeData.put(obj);
			}

			arrayObj.put(AQConstants.TREE_DATA, treeData);
			arrayObj.put(AQConstants.CHILDCOUNT, childCount);

		}
		catch (Exception e)
		{
		}
		return arrayObj;

	}

}
