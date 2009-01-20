package edu.wustl.common.beans;


public class RecentQueriesBean
{
	String queryTitle;
	String status;
	Long resultCount;
	String queryCreationDate;
	Long queyExecutionId;
	
	public String getQueryTitle()
	{
		return queryTitle;
	}
	
	public void setQueryTitle(String queryTitle)
	{
		this.queryTitle = queryTitle;
	}
	
	public String getStatus()
	{
		return status;
	}
	
	public void setStatus(String status)
	{
		this.status = status;
	}
	
	public Long getResultCount()
	{
		return resultCount;
	}
	
	public void setResultCount(Long resultCount)
	{
		this.resultCount = resultCount;
	}

	
	public String getQueryCreationDate()
	{
		return queryCreationDate;
	}

	
	public void setQueryCreationDate(String queryCreationDate)
	{
		this.queryCreationDate = queryCreationDate;
	}

	
	public Long getQueyExecutionId()
	{
		return queyExecutionId;
	}

	
	public void setQueyExecutionId(Long queyExecutionId)
	{
		this.queyExecutionId = queyExecutionId;
	}

}
