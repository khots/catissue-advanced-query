/**
 * 
 */
package edu.wustl.query.querymanager;


/**
 * @author supriya_dankh
 *
 */
public class Count
{
   private int count;
   private int query_exection_id;
   private String status;
   
   /**
    * 
    * @param count
    * @param query_exection_id
    * @param status
    */
   public Count(int count,int query_exection_id,String status)
   {
	   this.count = count;
	   this.query_exection_id = query_exection_id;
	   this.status = status;
   }

   /**
    * 
    * @return
    */
	public int getCount()
	{
		return count;
	}
	
	/**
	 * 
	 * @param count
	 */
	public void setCount(int count)
	{
		this.count = count;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getQuery_exection_id()
	{
		return query_exection_id;
	}
	
	/**
	 * 
	 * @param query_exection_id
	 */
	public void setQuery_exection_id(int query_exection_id)
	{
		this.query_exection_id = query_exection_id;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getStatus()
	{
		return status;
	}
	
	/**
	 * 
	 * @param status
	 */
	public void setStatus(String status)
	{
		this.status = status;
	}
   
   
}
