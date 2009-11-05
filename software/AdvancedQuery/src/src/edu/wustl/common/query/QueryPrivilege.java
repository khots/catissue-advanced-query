package edu.wustl.common.query;

/**
 * This class stores the privilege of a query and checks whether the query has identified 
 * or de-identified privilege.
 * @author rukhsana_sameer
 *
 */
public class QueryPrivilege
{
	/**
	 * isSecurePrivilege set to true if query has secure (de-identified) privilege. 
	 */
	protected boolean hasSecurePrivilege;

	
	/**
	 * @return the isSecurePrivilege
	 */
	public boolean isSecurePrivilege()
	{
		return hasSecurePrivilege;
	}

	
	/**
	 * @param isSecurePrivilege the isSecurePrivilege to set
	 */
	public void setSecurePrivilege(boolean hasSecurePrivilege)
	{
		this.hasSecurePrivilege = hasSecurePrivilege;
	}
	
	public QueryPrivilege()
	{
		this.hasSecurePrivilege = true;
	}
	
}
