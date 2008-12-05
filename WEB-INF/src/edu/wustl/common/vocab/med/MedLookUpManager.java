package edu.wustl.common.vocab.med;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.dao.DAOFactory;
import edu.wustl.common.dao.JDBCDAO;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.query.util.global.Constants;

public class MedLookUpManager
{
	Map<String,List<String> > pvMap = null;
	private static MedLookUpManager medLookUpManager = null;
	
	public MedLookUpManager()
	{
		
	}
	
	public static MedLookUpManager instance()
	{
		if(medLookUpManager == null)
		{
			medLookUpManager = new MedLookUpManager();
			//medLookUpManager.init();
		}
		
		return medLookUpManager;
		
	}
	
	
	
	public List<String> getPermissibleValues(String pvFilter)
	{
		JDBCDAO dao = (JDBCDAO) DAOFactory.getInstance().getDAO(Constants.JDBC_DAO);
		
		List<String> pvList = null;
		try
		{
			SessionDataBean sessionData = new SessionDataBean(); 
			Long userId = Long.valueOf((1));
			sessionData.setUserName("admin@admin.com");
			sessionData.setIpAddress("10.88.88.24");
			sessionData.setUserId(userId);
			sessionData.setFirstName("admin@admin.com");
			sessionData.setLastName("admin@admin.com");
			sessionData.setAdmin(true);
			sessionData.setSecurityRequired(false);
			dao.openSession(sessionData);
			List<List<String>> dataList = dao.executeQuery("select synonym,id from MED_LOOKUP_TABLE where synonym like '" + pvFilter + "'", null, false, false, null);
			if(!dataList.isEmpty())
			{
				pvList = new ArrayList<String>();
				
				for(int i=0; i < dataList.size(); i++)
				{
						pvList.add(dataList.get(i).get(1));
				}
			}
			dao.closeSession();
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
		
		return pvList;

	}
}
