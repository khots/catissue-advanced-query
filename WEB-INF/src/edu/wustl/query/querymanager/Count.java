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
   private Long count;
   private Long queryExectionId;
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
   public Count(Long count,Long queryExectionId,String status)
   {
	   this.count = count;
	   this.queryExectionId = queryExectionId;
	   this.status = status;
   }

   /**
    * 
    * @return
    */
	public Long getCount()
	{
		return count;
	}
	
	/**
	 * 
	 * @param count
	 */
	public void setCount(Long count)
	{
		this.count = count;
	}
	
	/**
	 * 
	 * @return
	 */
	public Long getQueryExectionId()
	{
		return queryExectionId;
	}
	
	/**
	 * 
	 * @param queryExectionId
	 */
	public void setQueryExectionId(Long queryExectionId)
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
