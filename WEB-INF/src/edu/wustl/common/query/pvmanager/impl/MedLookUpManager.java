package edu.wustl.common.query.pvmanager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.wustl.common.dao.DAOFactory;
import edu.wustl.common.dao.JDBCDAO;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Variables;

public class MedLookUpManager
{
	Map<String,List<String> > pvMap = null;
	private static MedLookUpManager medLookUpManager = null;
	private String lookUpTable=null;
	
	private MedLookUpManager()
	{
		
	}
	
	public static MedLookUpManager instance()
	{
		if(medLookUpManager == null)
		{
			medLookUpManager = new MedLookUpManager();
			medLookUpManager.lookUpTable = Variables.properties.getProperty("med.lookup.table");
			//medLookUpManager.init();
		}
		
		return medLookUpManager;
		
	}
	
	/*private void init()
	{
		JDBCDAO dao = (JDBCDAO) DAOFactory.getInstance().getDAO(Constants.JDBC_DAO);
		try
		{
			dao.openSession(null);
			List<List<String>> dataList = dao.executeQuery("select synonym,id from MED_LOOKUP_TABLE", null, false, false, null);
			if(!dataList.isEmpty())
			{
				pvMap = new HashMap<String, List<String>>();
				String pvFilter;
				for(int i=0; i < dataList.size(); i++)
				{
					pvFilter = dataList.get(i).get(0).substring(0,dataList.get(i).get(0).indexOf("^") + 1) + "%";
					if(pvMap.containsKey(pvFilter))
					{
						pvMap.get(pvFilter).add(dataList.get(i).get(1));
					}
					else
					{
						List<String> pvList = new ArrayList<String>();
						pvList.add(dataList.get(i).get(1));
						pvMap.put(pvFilter,pvList);
					}
				}
			}
		}
		catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
	
	/*public List<String> getPermissibleValues(String pvFilter)
	{
		if(pvMap != null)
			return pvMap.get(pvFilter);
		else
			return null;
	}*/
	public List<String> getPermissibleValues(List<String> pvFilter) throws PVManagerException 
	{
		List<String> pvList = null;
		JDBCDAO dao = (JDBCDAO) DAOFactory.getInstance().getDAO(Constants.JDBC_DAO);
		try
		{
			dao.openSession(null);
			String query = getQuery(pvFilter);
			List<List<String>> dataList = dao.executeQuery(query, null, false, false, null);
			if(!dataList.isEmpty())
			{
				pvList = new ArrayList<String>();
			
				for(int i=0; i < dataList.size(); i++)
				{
					pvList.add(dataList.get(i).get(1));
				}
			}
		}
		catch (DAOException e)
		{
			
			throw new PVManagerException("Error occured while fetching the permissible concept codes from MED.",e);
			
		}
		catch (ClassNotFoundException e)
		{
			throw new PVManagerException("Error occured while fetching the permissible concept codes from MED.",e);			
		}	
		finally
		{
			try
			{
				dao.closeSession();
			}
			catch (DAOException e)
			{
				throw new PVManagerException("Error occured while fetching the permissible concept codes from MED.",e);
			}
		}
		
		return pvList;

	}
	
	private String getQuery(List<String> pvFilterList)
	{
		StringBuffer query = new StringBuffer("select synonym,id from ");
		query.append(lookUpTable);
		query.append(" where ");
		int indx=0;
		for(; indx<pvFilterList.size()-1;indx++)
		{
			query.append("synonym like '");
			query.append(pvFilterList.get(indx));
			query.append("' or ");
		}
		query.append("synonym like '");
		query.append(pvFilterList.get(indx));
		query.append("'");
		
		return query.toString();
	}
}
