package edu.wustl.common.query.factory;

import edu.wustl.common.util.Utility;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.viewmanager.AbstractViewIQueryGenerator;


/**
 * @author vijay_pande
 *
 */
public class ViewIQueryGeneratorFactory
{
	/**
	 * Method to create instance of class AbstractViewIQueryGenerator. 
	 * @return The reference of AbstractViewIQueryGenerator. 
	 */
	public static AbstractViewIQueryGenerator getDefaultViewIQueryGenerator()
	{
		return (AbstractViewIQueryGenerator) Utility.getObject(Variables.viewIQueryGeneratorClassName);
	}
}
