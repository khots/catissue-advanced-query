package edu.wustl.query.util.querysuite;


public abstract class AbstractSecurityEventHandler
{
	abstract public void auditQuery(Long queryExecId, String securityCode);
}
