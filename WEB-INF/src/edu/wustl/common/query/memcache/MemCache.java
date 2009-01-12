package edu.wustl.common.query.memcache;


/**
 * This class is responsible for Maintaining a Set of UPI's
 * 
 * @author ravindra_jain
 * @version 1.0
 * @since January 5, 2009
 */
public abstract class MemCache
{

	/**
	 * To add Person UPI to Set 
	 * @param upi
	 * @return
	 */
	public abstract boolean add(Object upi);
	
	
}
