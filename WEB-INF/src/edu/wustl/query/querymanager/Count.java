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
   private int queryExectionId;
   private String status;
   
    /**
     * DEFAULT CONSTRUCTOR
     */
	public Count()
	{

	}
   /**
    * 
    * @param count
    * @param queryExectionId
    * @param status
    */
   public Count(int count,int queryExectionId,String status)
   {
	   this.count = count;
	   this.queryExectionId = queryExectionId;
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
	public int getQueryExectionId()
	{
		return queryExectionId;
	}
	
	/**
	 * 
	 * @param queryExectionId
	 */
	public void setQueryExectionId(int queryExectionId)
	{
		this.queryExectionId = queryExectionId;
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
