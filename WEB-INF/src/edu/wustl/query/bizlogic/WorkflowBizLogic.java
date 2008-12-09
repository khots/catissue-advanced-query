package edu.wustl.query.bizlogic;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.bizlogic.DefaultBizLogic;
import edu.wustl.common.dao.DAO;
import edu.wustl.common.querysuite.queryobject.IAbstractQuery;
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
					dao.insert(query, sessionDataBean, false, true);
				}
			}		
			dao.insert(workflow, sessionDataBean, false, false);
		}
		catch (UserNotAuthorizedException e)
		{
			throw new DAOException("Could not insert Workflow:"+e.getMessage()+e);
		}
	}
	
	
	protected void update(DAO dao, Object obj, Object oldObj, SessionDataBean sessionDataBean) throws DAOException, UserNotAuthorizedException
	{
		
	}
}
