package edu.wustl.query.enums;

import edu.wustl.query.util.global.Constants;

public enum Privilege 
{
	EXECUTE_MANAGED_QUERY(Constants.EXECUTE_MANAGED_QUERY);

public String privilege = "";

private Privilege(String privilege)
{
	this.privilege = privilege;
}
	
}

