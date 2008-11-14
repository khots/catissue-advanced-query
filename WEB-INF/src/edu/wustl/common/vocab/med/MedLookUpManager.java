package edu.wustl.common.vocab.med;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.wustl.common.dao.AbstractDAO;
import edu.wustl.common.dao.DAOFactory;
import edu.wustl.common.dao.JDBCDAO;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;

public class MedLookUpManager
{
	Map<String,List<String> > pvMap = null;
	private static MedLookUpManager medLookUpManager = null;
	
	private MedLookUpManager()
	{
		
	}
	
	public static MedLookUpManager instance()
	{
		if(medLookUpManager == null)
		{
			medLookUpManager = new MedLookUpManager();
			medLookUpManager.init();
		}
		
		return medLookUpManager;
		
	}
	
	private void init()
	{
		JDBCDAO dao = (JDBCDAO) DAOFactory.getInstance().getDAO(Constants.JDBC_DAO);
		List<List<String>> dataList = new ArrayList<List<String>>();
		try
		{
			dao.openSession(null);
			dataList = dao.executeQuery("select * from MED_LOOKUP_VIEW", null, false, false, null);
			if(!dataList.isEmpty())
			{
				pvMap = new HashMap<String, List<String>>();
				for(int i=0; i < dataList.size(); i++)
				{
					if(pvMap.containsKey(dataList.get(0)))
					{
						((List)pvMap.get(dataList.get(0))).add(dataList.get(1));
					}
					else
					{
						pvMap.put(dataList.get(0), new ArrayList<String>().add(dataList.get(1)));
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
		
	}
	
	public List<String> getPermissibleValues(String pvFilter)
	{
		if(pvMap != null)
			return pvMap.get(pvFilter);
		else
			return null;

	}
}
