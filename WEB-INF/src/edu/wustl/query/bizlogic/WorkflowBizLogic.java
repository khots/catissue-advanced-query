package edu.wustl.query.bizlogic;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.bizlogic.DefaultBizLogic;
import edu.wustl.common.dao.DAO;
import edu.wustl.common.querysuite.queryobject.IAbstractQuery;
import edu.wustl.common.querysuite.queryobject.ICompositeQuery;
import edu.wustl.common.security.exceptions.UserNotAuthorizedException;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.domain.Workflow;
import edu.wustl.query.domain.WorkflowItem;


/**
 * @author vijay_pande
 * BizLogic class to insert/update WorkFlow Object
 */
public class WorkflowBizLogic extends DefaultBizLogic
{
	private static org.apache.log4j.Logger logger =Logger.getLogger(WorkflowBizLogic.class);
	
	protected void insert(Object obj, DAO dao, SessionDataBean sessionDataBean) throws DAOException
	{
		Workflow workflow = (Workflow) obj;
		
		logger.info("In  WORKFLOW  BIZ LOGIC >>>>>> INSERT METHOD");
		logger.info("#### Workflow Name #### ::  "+workflow.getName());
		try
		{
			for(WorkflowItem workflowItem : workflow.getWorkflowItemList())
			{
				IAbstractQuery query = workflowItem.getQuery();
				if(query.getId()==null)
				{
					saveCompositeQuery(dao, sessionDataBean, (ICompositeQuery)query);
				}
			}		
			dao.insert(workflow, sessionDataBean, false, false);
		}
		catch (UserNotAuthorizedException e)
		{
			throw new DAOException("Could not insert Workflow:"+e.getMessage()+e);
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
	private void saveCompositeQuery(DAO dao, SessionDataBean sessionDataBean, ICompositeQuery query) throws UserNotAuthorizedException, DAOException
	{
		if(query.getOperation().getOperandOne().getId()==null)
		{
			saveCompositeQuery(dao, sessionDataBean, (ICompositeQuery)query.getOperation().getOperandOne());
		}
		if(query.getOperation().getOperandTwo().getId()==null)
		{
			saveCompositeQuery(dao, sessionDataBean, (ICompositeQuery)query.getOperation().getOperandTwo());
		}
		dao.insert(query, sessionDataBean, false, true);		
	}


	protected void update(DAO dao, Object obj, Object oldObj, SessionDataBean sessionDataBean) throws DAOException, UserNotAuthorizedException
	{
		Workflow workflow = (Workflow) obj;
		for(WorkflowItem workflowItem : workflow.getWorkflowItemList())
		{
			IAbstractQuery query = workflowItem.getQuery();
			if(query.getId()==null)
			{
				saveCompositeQuery(dao, sessionDataBean, (ICompositeQuery)query);
			}
		}	
		dao.update(obj,null,false,false,false);
	}
	
	
	public Long executeGetCountQuery(Long queryId)
	{
		return 1000l;
	}
	
}
