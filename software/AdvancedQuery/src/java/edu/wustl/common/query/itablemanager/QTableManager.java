package edu.wustl.common.query.itablemanager;

import edu.wustl.dao.exception.DAOException;

/**
 * This class is responsible for
 * 
 * @author ravindra_jain
 * @version 1.0
 * @since January 7, 2009
 */

public class QTableManager
{	
	int batchSize;
	
	private static QTableManager sINSTANCE; 
	
	/**
	 * 
	 * @return
	 * @throws DAOException
	 */
	public static QTableManager getInstance() throws DAOException
	{
		if(sINSTANCE == null)
		{
			sINSTANCE = new QTableManager();
		}
		return sINSTANCE;
	}
	
	/**
	 * 
	 * @param query_excecution_id
	 * @return
	 */
	public int getCount(int query_excecution_id)
	{
		return 0;
	}
	
	/**
	 * 
	 * @param queryExecId
	 * @return
	 */
	public String getQueryStatus(int queryExecId)
	{
		String status = null;
		
		return status;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getBatchSize()
	{
		return batchSize;
	}

	/**
	 * 
	 * @param batchSize
	 */
	public void setBatchSize(int batchSize)
	{
		this.batchSize = batchSize;
	}
	
}

