package edu.wustl.query.bizlogic;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.bizlogic.DefaultBizLogic;
import edu.wustl.common.dao.DAO;
import edu.wustl.common.security.exceptions.UserNotAuthorizedException;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.query.domain.Workflow;

/**
 * 
 * @author ravindra_jain
 *
 */
public class WorkflowBizLogic extends DefaultBizLogic
{
	
	protected void insert(Object obj, DAO dao, SessionDataBean sessionDataBean) throws DAOException
	{
		Workflow workflow = (Workflow) obj;
		
		System.out.println("In  WORKFLOW  BIZ LOGIC >>>>>> INSERT METHOD");
		System.out.println("#### Workflow Name #### ::  "+workflow.getName());
	}
	
	
	protected void update(DAO dao, Object obj, Object oldObj, SessionDataBean sessionDataBean) throws DAOException, UserNotAuthorizedException
	{
		
	}
}
