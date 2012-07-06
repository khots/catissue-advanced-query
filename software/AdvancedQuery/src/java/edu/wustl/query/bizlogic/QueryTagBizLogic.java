
package edu.wustl.query.bizlogic;

import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.wustl.common.bizlogic.IBizLogic;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.tags.bizlogic.ITagBizlogic;
import edu.wustl.common.tags.dao.TagDAO;
import edu.wustl.common.tags.domain.Tag;
import edu.wustl.common.tags.domain.TagItem;
import edu.wustl.dao.HibernateDAO;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.util.global.AQConstants;
import edu.wustl.query.util.querysuite.DAOUtil;

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

	@Override
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
	@Override
	public void assignTag(String entityName, long tagId, long objId) throws DAOException,
			BizLogicException
	{
		Tag tag = new Tag();
		tag.setIdentifier(tagId);
		TagItem<ParameterizedQuery> tagItem = new TagItem<ParameterizedQuery>();

		//ParameterizedQuery query = getQueryById(objId);
		ParameterizedQuery query = new ParameterizedQuery();
		query.setId(objId);

		tagItem.setTag(tag);
		tagItem.setObj(query);

		TagDAO<ParameterizedQuery> tagDao = new TagDAO<ParameterizedQuery>(entityName);
		tagDao.insertTagItem(tagItem);
	}

	/**
	 * Get list of Tags from the database.
	 * @param entityName from hbm file.
	 * @param obj Object to be inserted in database
	 * @throws DAOException,BizLogicException.
	 */
	@Override
	public List<Tag> getTagList(String entityName) throws DAOException, BizLogicException
	{
		List<Tag> tagList = null;
		TagDAO tagDao = null;
		tagDao = new TagDAO(entityName);
		tagList = tagDao.getTags();
		return tagList;
	}

	/**
	 * Assign the Query to existing folder or new folder.
	 * @param entityName from hbm file.
	 * @param label for new Tag.
	 * @param userId.
	 * @param objId.
	 * @throws DAOException,BizLogicException.
	 */
	@Override
	public void assignTag(String entityName, String label, long userId, long objId)
			throws DAOException, BizLogicException
	{
		ParameterizedQuery query = new ParameterizedQuery();
		TagItem<ParameterizedQuery> tagItem = new TagItem<ParameterizedQuery>();
		query.setId(objId);
		tagItem.setObj(query);
		long tagId = createNewTag(entityName, label, userId);
		tagItem.setTagId(tagId);
		TagDAO<ParameterizedQuery> tagDao = new TagDAO<ParameterizedQuery>(entityName);
		tagDao.insertTagItem(tagItem);
	}

	/**
	 * Get Tag object.
	 * @param entityName from hbm file.
	 * @param  tagId.
	 * @return Tag Object.
	 * @throws DAOException,BizLogicException.
	 */
	@Override
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

	/*
		/**
		 * Get the ParameterizedQuery object.
		 * @param objId to get ParameterizedQuery.
		 * @return ParameterizedQuery object 
		 * @throws DAOException,BizLogicException.
		 *//*
		public ParameterizedQuery getQueryById(long objId) throws DAOException, BizLogicException
		{
		ParameterizedQuery parameterizedQuery = null;
		HibernateDAO hibernateDao = null;
		try
		{
			hibernateDao = DAOUtil.getHibernateDAO(null);
			parameterizedQuery = (ParameterizedQuery) hibernateDao.retrieveById(
					ParameterizedQuery.class.getName(), objId);
		}
		catch (Exception e)
		{

		}
		finally
		{
			try
			{
				DAOUtil.closeHibernateDAO(hibernateDao);
			}
			catch (DAOException e)
			{
				throw new BizLogicException(e);
			}

		}
		return parameterizedQuery;
		}
		*/
	/**
	 * Delete the Tag from database.
	 * @param entityName from hbm file.
	 * @param tagId to retrieve TagItem Object and delete it from database.
	 * @throws DAOException,BizLogicException.
	 */
	@Override
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

	@Override
	public void deleteTagItem(String entityName, long itemId) throws DAOException,
			BizLogicException
	{
		TagDAO tagDAO = new TagDAO(entityName);
		TagItem tagItem = tagDAO.getTagItemById(itemId);
		tagDAO.deleteTagItem(tagItem);
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
			Set<TagItem> tagItemList = getTagItemByTagId(entityName, tagId);
			JSONArray treeData = new JSONArray();

			int childCount = 0;
			for (TagItem tagItem : tagItemList)
			{
				long objId = ((IParameterizedQuery) tagItem.getObj()).getId();
				String objName = ((IParameterizedQuery) tagItem.getObj()).getName();

				JSONObject obj = new JSONObject();
				obj.put(AQConstants.IDENTIFIER, tagItem.getIdentifier());
				obj.put(AQConstants.NAME, objName);
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