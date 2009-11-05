package edu.wustl.query.util.filter;

import javax.servlet.http.HttpServletRequest;

/**
 * This interface is used to check if the logged in user has privilege to
 * perform a particular action or not.
 * @author rukhsana_sameer
 *
 */
public interface IUrlAccessValidator
{
	/**
	 * This method checks if user is authorized to perform a particular action.
	 * @param request object for the user logged in.
	 * @return true if user has privilege to perform that action.
	 */
	boolean isAuthorized(HttpServletRequest request);
}
