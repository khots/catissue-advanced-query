package edu.wustl.common.beans;

/**
 * Used as bean for setting the parameters needed in recent query ajax call.
 *
 * @author chitra_garg
 */
public class RecentQueriesBean
{
    /**
     * stores query id.
     */
    private Long queryId;

    /**
     * @return query id.
     */
    public Long getQueryId()
    {
        return queryId;
    }

    /**
     * @param queryIdVal
     *            Query id.
     */
    public void setQueryId(Long queryIdVal)
    {
        this.queryId = queryIdVal;
    }

    /**
     * title for query.
     */
    private String queryTitle;

    /**
     * status that is in-progress or Completed.
     */
    private String status;

    /**
     * count
     */
    private Long resultCount;

    /**
     * creation date assigned to query.
     */
    private String queryCreationDate;
    /**
     * execution id assigned to query.
     */
    private Long queyExecutionId;
    /**
     *added for the secure Privilege.
     */
    private boolean isSecurePrivilege = true;

    /**
     * @return query title.
     */
    public String getQueryTitle()
    {
        return queryTitle;
    }

    /**
     * @param queryTitleVal
     *            = title for query
     */
    public void setQueryTitle(String queryTitleVal)
    {
        this.queryTitle = queryTitleVal;
    }

    /**
     * @return status that is in-progress or Completed.
     */
    public String getStatus()
    {
        return status;
    }

    /**
     * @param statusVal
     *            set that is in-progress or Completed.
     */
    public void setStatus(String statusVal)
    {
        this.status = statusVal;
    }

    /**
     * @return count.
     */
    public Long getResultCount()
    {
        return resultCount;
    }

    /**
     * @param resultCountVal
     *            count.
     */
    public void setResultCount(Long resultCountVal)
    {
        this.resultCount = resultCountVal;
    }

    /**
     * @return creation date assigned to query.
     */
    public String getQueryCreationDate()
    {
        return queryCreationDate;
    }

    /**
     * @param queryCreationDateVal
     *            =creation date assigned to query.
     */
    public void setQueryCreationDate(String queryCreationDateVal)
    {
        this.queryCreationDate = queryCreationDateVal;
    }

    /**
     * @return execution id assigned to query
     */
    public Long getQueyExecutionId()
    {
        return queyExecutionId;
    }

    /**
     * @param queyExecutionIdVal
     *            execution id assigned to query.
     */
    public void setQueyExecutionId(Long queyExecutionIdVal)
    {
        this.queyExecutionId = queyExecutionIdVal;
    }

    /**
     * @param isSecurePrivilegeVal
     *            is true if query is executed for a project not having
     *            identified privilege.
     */
    public void setIsSecurePrivilege(boolean isSecurePrivilegeVal)
    {
        this.isSecurePrivilege = isSecurePrivilegeVal;
    }

    /**
     * @return true if query has secure privilege
     */
    public boolean isIsSecurePrivilege()
    {
        return isSecurePrivilege;
    }
}
