package edu.wustl.query.domain;

/**
 * This class contains all the detailed information to required for workflow
 * execution.
 *
 * @author maninder_randhawa
 *
 */
public class WorkflowDetails
{
    protected transient Workflow workflow;
    protected int workFlowExecId;
    protected DirectedGraph dependencyGraph;

    /**
     * Get the Dependency Graph.
     *
     * @return The Dependency Graph.
     */
    public DirectedGraph getDependencyGraph()
    {
        return dependencyGraph;
    }

    /**
     * Sets the dependency graph.
     *
     * @param dependencyGraphObj
     *            Dependency Graph.
     */
    public void setDependencyGraph(DirectedGraph dependencyGraphObj)
    {
        this.dependencyGraph = dependencyGraphObj;
    }

    /**
     * Parameterized constructor.
     *
     * @param workflowInstance
     *            Workflow object.
     */
    public WorkflowDetails(Workflow workflowInstance)
    {
        super();
        this.workflow = workflowInstance;
    }

    /**
     * This method creates dependency graph by parsing workflow object.
     *
     * @param workflowInstance
     *            Workflow object.
     */
    protected void createGraph(Workflow workflowInstance)
    {
        // empty method.
    }

    /**
     * gets workFlow Execution Id.
     *
     * @return workFlowExecId.
     */
    public int getWorkFlowExecId()
    {
        return workFlowExecId;
    }

    /**
     * sets workFlow Execution Id.
     *
     * @param workFlowExecutionId
     *            Workflow Execution Id.
     */
    public void setWorkFlowExecId(int workFlowExecutionId)
    {
        this.workFlowExecId = workFlowExecutionId;
    }

    /**
     * gets workflow.
     *
     * @return workflow.
     */
    public Workflow getWorkflow()
    {
        return workflow;
    }

    /**
     * sets workflow.
     *
     * @param workflowInstance
     *            Workflow object.
     */
    public void setWrflow(Workflow workflowInstance)
    {
        this.workflow = workflowInstance;
    }

}
