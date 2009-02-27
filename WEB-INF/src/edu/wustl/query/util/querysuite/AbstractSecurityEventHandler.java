package edu.wustl.query.util.querysuite;


public abstract class AbstractSecurityEventHandler
{
	abstract public void auditQuery(int queryExecId, String securityCode);
}
