package edu.wustl.common.query.factory;

import edu.wustl.common.util.Utility;
import edu.wustl.query.util.global.Variables;
import edu.wustl.common.query.AbstractQuery;

public class AbstractQueryFactory {

	/**
	 * Method to create instance of class AbstractQuery. 
	 * @return The reference of AbstractQuery. 
	 */
	public static AbstractQuery getDefaultAbstractQuery()
	{

		return (AbstractQuery) Utility.getObject(Variables.abstractQueryClassName);
	}

	/**
	 * Method to create instance of class AbstractQuery. 
	 * @return The reference of AbstractQuery. 
	 */
	public static AbstractQuery ConfigureAbstractQuery(String className)
	{

		return (AbstractQuery) Utility.getObject(className);
	}
	
	
	
	
	
	
}
