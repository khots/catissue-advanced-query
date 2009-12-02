package edu.wustl.query.bizlogic;

import edu.wustl.common.beans.NameValueBean;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.util.global.Constants;
import edu.wustl.security.exception.SMException;
import edu.wustl.security.manager.SecurityManagerFactory;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShareQueryBizLogic
{
	public List<User> getUsers()
	{
		List<User> users = null;
		try
		{
			users = SecurityManagerFactory.getSecurityManager().getUsers();
		}
		catch (SMException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}
	/**
	 * Returns the list of NameValueBeans with name as "LastName,Firstname" 
	 * and value as systemtIdentifier, of all users who are not disabled. 
	 * @return the list of NameValueBeans with name as "LastName,Firstname" 
	 * and value as systemtIdentifier, of all users who are not disabled.
	 * @throws BizLogicException Biz logic exception
	 */
	public List getCSMUsers() throws BizLogicException 
	{
		//Retrieve the users whose activity status is not disabled.
		List users;
		try
		{
			users = SecurityManagerFactory.getSecurityManager().getUsers();
		} 
		catch (SMException e)
		{
			throw new BizLogicException(null,e,"Exception in getting users through SecurityManager");
		}

		List nameValuePairs = new ArrayList();
		nameValuePairs.add(new NameValueBean(Constants.SELECT_OPTION, String
				.valueOf(Constants.SELECT_OPTION_VALUE)));

		// If the list of users retrieved is not empty. 
		if (!users.isEmpty())
		{
			// Creating name value beans.
			for (int i = 0; i < users.size(); i++)
			{
				gov.nih.nci.security.authorization.domainobjects.User user = (gov.nih.nci.security.authorization.domainobjects.User) users
						.get(i);
				NameValueBean nameValueBean = new NameValueBean(); // NOPMD - Instantiating NameValueBean in loop
				nameValueBean.setName(user.getLastName() + ", " + user.getFirstName());
				nameValueBean.setValue(String.valueOf(user.getUserId()));
				nameValuePairs.add(nameValueBean);
			}
		}

		Collections.sort(nameValuePairs);
		return nameValuePairs;
	}

}
