package edu.wustl.common.beans;

/**
 * Used to check existance of already fetched query in session with respect to
 * workflow.
 *
 * @author chitra_garg
 */
public class QueryWorkflowBean
{

    /**
     * Private constant for Hash Code.
     */
    private static final int DEFAULT_HASH_CODE = 1;
    /**
     * project id.
     */
    private Long projectId;

    /**
     * @return project id.
     */
    public Long getProjectId()
    {
        return projectId;
    }

    /**
     * @param projectIdVal
     *            =project id.
     */
    public void setProjectId(Long projectIdVal)
    {
        this.projectId = projectIdVal;
    }

    /**
     * workflow Id.
     */
    private Long workflowId;

    /**
     * query id.
     */
    private Long queryId;

    /**
     * @return workflow Id.
     */
    public Long getWorkflowId()
    {
        return workflowId;
    }

    /**
     * @param workflowIdVal
     *            workflow Id.
     */
    public void setWorkflowId(Long workflowIdVal)
    {
        this.workflowId = workflowIdVal;
    }

    /**
     * @return query Id.
     */
    public Long getQueryId()
    {
        return queryId;
    }

    /**
     * @param queryIdVal
     *            query Id.
     */
    public void setQueryId(Long queryIdVal)
    {
        this.queryId = queryIdVal;
    }

    /**
     * Overridden hashCode method.
     *
     * @return The hash Code.
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return DEFAULT_HASH_CODE;
    }

    /**
     * Overridden equals method.
     *
     * @param obj
     *            Object to be compared.
     * @return <code>true</code> is two objects are equals else
     *         <code>false</code>.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        boolean isEqual = false;

        if (obj instanceof QueryWorkflowBean)
        {
            QueryWorkflowBean queryWorkflowBean = (QueryWorkflowBean) obj;

            if (this.queryId.equals(queryWorkflowBean.queryId)
                    && this.workflowId.equals(queryWorkflowBean.workflowId)
                    && this.projectId.equals(queryWorkflowBean.projectId))
            {
                isEqual = true;
            }
        }
        return isEqual;
    }
}
