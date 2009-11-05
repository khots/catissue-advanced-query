
package edu.wustl.common.query.pvmanager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.vocab.utility.Logger;
import edu.wustl.dao.JDBCDAO;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.DAOUtil;

/**
 *
 * @author amit_doshi
 *
 */
public final class MedLookUpManager
{

	private static final Logger log = Logger.getLogger(MedLookUpManager.class);
	//private Map<String, List<String>> pvMap = null;
	private static MedLookUpManager mLookUpMgrObj = null;
	private static Map<String, String> conceptCodeVsConceptName = new HashMap<String, String>();
	private static final String ErrMsg = "Error occured while fetching the permissible concept codes from MED.";

	/**
	 * This method is used to get the instance of MedLookUpManager
	 * @return returns MedLookUpManager
	 */
	public static MedLookUpManager instance()
	{

		if (mLookUpMgrObj == null)
		{
			synchronized (MedLookUpManager.class)
			{
				mLookUpMgrObj = new MedLookUpManager();
				//medLookUpManager.init();
			}
		}
		return mLookUpMgrObj;
	}

	/**
	 * This method is used to get the distinct concept codes form the database.
	  * @param pvLookupQuery PV lookup query
	 * @return List of concept codes
	 * @throws PVManagerException  throws PVManagerException
	 */
	public List<String> getPermissibleValues(String pvLookupQuery) throws PVManagerException
	{
		List<String> pvList = null;
		JDBCDAO jdbcDAO = null;
		try
		{
			jdbcDAO = DAOUtil.getJDBCDAO(null);
			//execute query for PVLookupQuery
			List<List<String>> dataList = jdbcDAO.executeQuery(pvLookupQuery);
			pvList = getPermissibleValuesWithConcpetCode(dataList);

		}
		catch (DAOException e)
		{
			throw new PVManagerException(ErrMsg, e);
		}
		finally
		{
			handleFinally(jdbcDAO);
		}
		return pvList;
	}

	/**
	 * @param jdbcDAO instance of jdbcDAO
	 */
	private void handleFinally(JDBCDAO jdbcDAO)
	{
		try
		{
			if (jdbcDAO != null)
			{
				DAOUtil.closeJDBCDAO(jdbcDAO);
			}
		}
		catch (DAOException e)
		{
			log.error(ErrMsg, e);
		}
	}

	/**
	 * This method is used to get the Permissible Values with Concept Codes.
	 * @param dataList list of data fetched form DB
	 * @return returns List of Concept Codes of Permissible Values
	 */
	private List<String> getPermissibleValuesWithConcpetCode(List<List<String>> dataList)
	{
		List<String> pvList = null;
		if (!dataList.isEmpty())
		{
			pvList = new ArrayList<String>();

			for (int i = 0; i < dataList.size(); i++)
			{
				pvList.add(dataList.get(i).get(0));
			}
		}
		return pvList;
	}

	/**
	 * This method is used to get the volumes of concept in DB.
	 * @return map of concept Code V/s VolumeInDb for the concept
	 * @throws PVManagerException throws PVManagerException
	 */
	public Map<String, String> getVolumeInDb() throws PVManagerException
	{
		Map<String, String> conceptCodeVsVolumeInDb = null;
		JDBCDAO jdbcDAO = null;
		String query = "select * from concept_data_count";
		try
		{
			jdbcDAO = DAOUtil.getJDBCDAO(null);
			List<List<Object>> dataList = jdbcDAO.executeQuery(query);
			if (!dataList.isEmpty())
			{
				conceptCodeVsVolumeInDb = new HashMap<String, String>();
				for (int i = 0; i < dataList.size(); i++)
				{
					conceptCodeVsVolumeInDb.put((String) (dataList.get(i).get(0)),
							(String) (dataList.get(i).get(Constants.ONE)));
				}
			}
		}
		catch (DAOException e)
		{
			throw new PVManagerException(ErrMsg, e);
		}
		finally
		{
			handleFinally(jdbcDAO);
		}
		return conceptCodeVsVolumeInDb;
	}

	/**
	 * This method is used get the Permissible Value count for the Entity which has given PV filter and View.
	 * @param pvLookupQuery PV lookup query
	 * @return  - total number of permissible values for the Entity
	 * @throws PVManagerException throws PVManagerException
	 */
	public int getPermissibleValuesCount(String pvLookupQuery) throws PVManagerException
	{
		JDBCDAO jdbcDAO = null; //(JDBCDAO) DAOFactory.getInstance().getDAO(Constants.JDBC_DAO);
		String countQuery = null;
		int count = 0;
		try
		{
			jdbcDAO = DAOUtil.getJDBCDAO(null);
			//if PV filter is not given then directly fire queries on VIEW
			/*countQuery = "select count(distinct id) from " + pvView;
			if (pvFilter != null && (!pvFilter.equals("")))
			{
				//if a pvFilter value is give i.e a synonym is given
				countQuery = "select count(distinct id) from " + pvView + Constants.WHERE
						+ pvFilter;
			}*/
			countQuery = pvLookupQuery.replace("select", "select count(");
			countQuery = countQuery.replace("from", ") from");
			List<List<String>> dataListCount = jdbcDAO.executeQuery(countQuery);
			if (!dataListCount.isEmpty())
			{
				count = Integer.parseInt(dataListCount.get(0).get(0));
			}
		}
		catch (DAOException e)
		{
			throw new PVManagerException(ErrMsg, e);
		}
		finally
		{
			handleFinally(jdbcDAO);
		}
		return count;
	}

	/**
	 * Method to get Med concept name based on concept Id.
	 * @param attribute - IOutputAttribute
	 * @param conceptId conceptId of the attribute
	 * @return concept name  of the concept
	 */
	public String getConceptName(IOutputAttribute attribute, String conceptId)
	{
		String conceptName = "";
		if (conceptId != null)
		{
			conceptName = conceptCodeVsConceptName.get(conceptId);
			if (conceptName == null)
			{
				//String pvView = "";
				/*QueryableObjectInterface entity = attribute.getExpression().getQueryEntity()
						.getDynamicExtensionsEntity();*/
				//pvView = entity.getTaggedValue(Constants.PERMISSIBLEVALUEVIEW);
				JDBCDAO jdbcDAO = null;
				String query = "";
				try
				{
					jdbcDAO = DAOUtil.getJDBCDAO(null);
					query = "select " + Constants.PRINT_NAME + " from concept " + Constants.WHERE
							+ Constants.CONCEPT_ID + " = " + conceptId;
					List<List<String>> dataList = jdbcDAO.executeQuery(query);

					if (!dataList.isEmpty())
					{
						conceptName = dataList.get(0).get(0);
					}
				}
				catch (DAOException e)
				{
					log.error(e.getMessage(), e);
				}
				finally
				{
					handleFinallyforGetConceptName(jdbcDAO);
				}
			}
			conceptCodeVsConceptName.put(conceptId, conceptName);
		}
		return conceptName;
	}

	/**
	 * This method will handle the finally for GetConcpetName method.
	 * @param jdbcDAO -instance of JDBCDAO
	 */
	private void handleFinallyforGetConceptName(JDBCDAO jdbcDAO)
	{
		try
		{
			DAOUtil.closeJDBCDAO(jdbcDAO);
		}
		catch (DAOException e)
		{
			log.error(e.getMessage(), e);
		}
	}
}

