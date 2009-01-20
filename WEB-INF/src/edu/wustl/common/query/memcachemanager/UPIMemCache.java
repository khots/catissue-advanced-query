package edu.wustl.common.query.memcachemanager;

import java.util.HashSet;

/**
 * This class is responsible for Maintaining a Set of UPI's
 * 
 * @author ravindra_jain
 * @version 1.0
 * @since January 5, 2009
 */
public class UPIMemCache extends MemCache
{
	/**
	 * HashSet maintaining UPI's
	 */
	private HashSet<String> upiCache;
	
	
	/**
	 * CONSTRUCTOR
	 */
	public UPIMemCache()
	{
		upiCache = new HashSet<String>();
	}
	
	@Override
	/**
	 * To add Person UPI to Set 
	 * @param upi
	 * @return
	 */
	public boolean add(Object upi)
	{
		return upiCache.add(upi.toString());
	}

	@Override
	/**
	 * To clear the Cache
	 */
	public void clear()
	{
		upiCache.clear();
	}
}
