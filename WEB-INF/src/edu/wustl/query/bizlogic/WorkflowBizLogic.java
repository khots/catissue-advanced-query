
package edu.wustl.query.bizlogic;
import edu.wustl.query.querymanager.Count;
import edu.wustl.query.util.global.Constants;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.bizlogic.DefaultBizLogic;
import edu.wustl.common.dao.AbstractDAO;
import edu.wustl.common.dao.DAO;
import edu.wustl.common.dao.DAOFactory;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.common.querysuite.queryobject.IAbstractQuery;
import edu.wustl.common.querysuite.queryobject.ICompositeQuery;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.security.exceptions.UserNotAuthorizedException;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.domain.Workflow;
import edu.wustl.query.domain.WorkflowItem;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.query.util.querysuite.CiderQueryUIManager;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * @author vijay_pande
 * BizLogic class to insert/update WorkFlow Object
 */
public class WorkflowBizLogic extends DefaultBizLogic
{

	private static org.apache.log4j.Logger logger = Logger.getLogger(WorkflowBizLogic.class);
	/**
	 * Inserts domain object 
	 * @param obj The object to be inserted.
	 * @param dao the dao object
	 * @param sessionDataBean session specific data
	 * @throws DAOException
	 */
	protected void insert(Object obj, DAO dao, SessionDataBean sessionDataBean) throws DAOException
	{
		Workflow workflow = (Workflow) obj;

		logger.info("In  WORKFLOW  BIZ LOGIC >>>>>> INSERT METHOD");
		logger.info("#### Workflow Name #### ::  " + workflow.getName());
		try
		{
			for (WorkflowItem workflowItem : workflow.getWorkflowItemList())
			{
				IAbstractQuery query = workflowItem.getQuery();
				if (query.getId() == null)
				{
					saveCompositeQuery(dao, sessionDataBean, (ICompositeQuery) query);
				}
			}
			dao.insert(workflow, sessionDataBean, false, false);
		}
		catch (UserNotAuthorizedException e)
		{
			throw new DAOException("Could not insert Workflow:" + e.getMessage() + e);
		}
	}

	/**
	 * Method to save compositeQuery object
	 * @param dao Object of DAO
	 * @param sessionDataBean object of SessionDataBean
	 * @param query object of ICompositeQuery to be saved
	 * @throws UserNotAuthorizedException User not authorized exception
	 * @throws DAOException DAO exception
	 */
	private void saveCompositeQuery(DAO dao, SessionDataBean sessionDataBean, ICompositeQuery query)
			throws UserNotAuthorizedException, DAOException
	{
		if (query.getOperation().getOperandOne().getId() == null)
		{
			saveCompositeQuery(dao, sessionDataBean, (ICompositeQuery) query.getOperation()
					.getOperandOne());
		}
		if (query.getOperation().getOperandTwo().getId() == null)
		{
			saveCompositeQuery(dao, sessionDataBean, (ICompositeQuery) query.getOperation()
					.getOperandTwo());
		}
		dao.insert(query, sessionDataBean, false, true);
	}

	/**
	 * Updates  domain object 
	 * @param dao the dao object
	 * @param obj The object to be updated into the database. 
	 * @param oldObj old object that is to be updated
	 * @param sessionDataBean session specific data
	 * @throws DAOException
	 * @throws UserNotAuthorizedException
	 */
	protected void update(DAO dao, Object obj, Object oldObj, SessionDataBean sessionDataBean)
			throws DAOException, UserNotAuthorizedException
	{
		Workflow workflow = (Workflow) obj;
		for (WorkflowItem workflowItem : workflow.getWorkflowItemList())
		{
			IAbstractQuery query = workflowItem.getQuery();
			if (query.getId() == null)
			{
				saveCompositeQuery(dao, sessionDataBean, (ICompositeQuery) query);
			}
		}
		dao.update(obj, null, false, false, false);
	}

	/**
	 * @param queryId=id of query for which counts will be returned
	 * @return count value
	 * @throws DAOException 
	 * @throws QueryModuleException 
	 * @throws SQLException 
	 */
	public int executeGetCountQuery(Long queryId,HttpServletRequest request) throws BizLogicException
	{
		//TO DO 
		/*
		 * set the IQuery from the query ID 
		 */
		 IQuery query = null;
         try
         {
               if (queryId != null)
               {
                 AbstractDAO dao = DAOFactory.getInstance().getDAO(Constants.HIBERNATE_DAO);
                  dao.openSession(null);
                  query = (IParameterizedQuery) dao.retrieve(ParameterizedQuery.class.getName(), Long .valueOf(queryId));
                  dao.closeSession();
               }
               AbstractQueryUIManager qUIManager = null;

           		int queryExecId;
           		
           		 qUIManager = AbstractQueryUIManagerFactory.configureDefaultAbstractUIQueryManager
           		 (this.getClass(), request, query);
           			queryExecId = qUIManager.searchQuery(null);
           	
           		return queryExecId;
           		
      }
      catch (DAOException ex)
      {
            BizLogicException bizLogicException = new BizLogicException(ex.getMessage(),ex);
            throw bizLogicException;
      }

		catch (QueryModuleException e)
		{
	        BizLogicException bizLogicException = new BizLogicException(e.getMessage(),e);
	        throw bizLogicException;
		}
 


	}

	public HashMap<String, Count> getCount(HashSet<String> idList,int queryExecId) throws QueryModuleException 
	{
		//CiderQueryUIManager ciderQueryUIManager = new CiderQueryUIManager();
		AbstractQueryUIManager qUIManager = AbstractQueryUIManagerFactory.getDefaultAbstractUIQueryManager();
		Count countObject = ((CiderQueryUIManager)qUIManager).getCount(queryExecId);
		
		
		HashMap<String,Count> resultMap=new HashMap<String, Count>();
		Iterator<String>  queryIdIter=idList.iterator();
		int count=countObject.getCount();
		if(queryIdIter.hasNext())
		{
			while(queryIdIter.hasNext())
			{
				resultMap.put(queryIdIter.next(),countObject);//change milli seconds to count
			}
		}
		else
		{
			resultMap=null;
		}
		return resultMap;
	}
	
    /**
     * Overriding the parent class's method to validate the enumerated attribute values
     */
	protected boolean validate(Object obj, DAO dao, String operation) throws DAOException
    {
		Workflow workflow=(Workflow)obj;
		//forming Query to validate workflow Name 
		
		String sourceObjectName = Workflow.class.getName();
		String[] selectColumnName = { "id" };
		String[] whereColumnName = { "name" };
		String[] whereColumnCondition = { "=" };
		Object[] whereColumnValue = { workflow.getName()};
		String joinCondition = null;

		List list = dao.retrieve(sourceObjectName, selectColumnName,
				whereColumnName, whereColumnCondition,
				whereColumnValue, joinCondition);
		System.out.println();
		if (!list.isEmpty()) 
		{	
			throw new DAOException("Workflow with same name already exist");
		}
		return true;
	    
    }
}
