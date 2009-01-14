package edu.wustl.common.query.factory;

import edu.wustl.common.util.Utility;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;

/**
 * Factory to return the AbstractQueryUIManager instance.
 * 
 * @author maninder_randhawa
 *
 */
public class AbstractQueryUIManagerFactory {
	/**
	 * Method to create instance of class AbstractQueryUIManager. 
	 * @return The reference of AbstractQueryUIManager. 
	 */
	public static AbstractQueryUIManager getDefaultAbstractQueryUIManager()
	{
		return (AbstractQueryUIManager) Utility.getObject(Variables.abstractQueryUIManagerClassName);
	}
	
	/**
	 * Method to create instance of class AbstractQueryUIManager. 
	 * @return The reference of AbstractQueryUIManager. 
	 */
	public static AbstractQueryUIManager ConfigureDefaultAbstractQueryUIManager(String className)
	{
		return (AbstractQueryUIManager) Utility.getObject(className);
	}
}
