/*L
 *  Copyright Washington University in St. Louis
 *  Copyright SemanticBits
 *  Copyright Persistent Systems
 *  Copyright Krishagni
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/catissue-advanced-query/LICENSE.txt for details.
 */

package edu.wustl.query.util.querysuite;

import edu.wustl.common.util.global.CommonServiceLocator;

/**
 * This class is a used to set different parameter like application name,
 *  configure in DynamicExtensionDAO.properties file.
 * @author suhas_khot
 *
 */
public final class AdvanceQueryDAO
{

	/**
	 * object of class AdvanceQueryDAO.
	 */
	private static AdvanceQueryDAO queryDAO = new AdvanceQueryDAO();

	/**
	 * Application Name used for getting DAO.
	 */
	private String appName;

	/**
	 *No argument constructor.
	 *Here all the properties are set
	 */
	private AdvanceQueryDAO()
	{
		//initProps();
		this.appName=CommonServiceLocator.getInstance().getAppName();
	}

	/**
	 * This method return object of the class CommonServiceLocator.
	 * @return object of the class CommonServiceLocator.
	 */
	public static AdvanceQueryDAO getInstance()
	{
		return queryDAO;
	}

	/**
	 * @return the application name.
	 */
	public String getAppName()
	{
		return appName;
	}
}
