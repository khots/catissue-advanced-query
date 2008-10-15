
package edu.wustl.common.query.factory;

import edu.wustl.common.query.ISqlGenerator;
import edu.wustl.common.query.impl.SqlGenerator;


/**
 * Factory to return the SqlGenerator's instance. 
 * @author deepti_shelar
 *
 */
public abstract class SqlGeneratorFactory
{

	/**
	 * Method to create instance of class SqlGenerator. 
	 * @return The reference of SqlGenerator. 
	 */
	public static ISqlGenerator getInstance()
	{
		return new SqlGenerator();
	}
}
