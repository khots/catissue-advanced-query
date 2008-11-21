package edu.wustl.common.query.factory;

import edu.wustl.common.query.pvmanager.IPermissibleValueManager;
import edu.wustl.common.util.Utility;


public class PermissibleValueManagerFactory
{

	public static IPermissibleValueManager getPermissibleValueManager()
	{
		IPermissibleValueManager permissibleValueManager = null;
		//permissibleValueManager = Class.forName("edu.wustl.query.pvmanager.impl.CIDERPermissibleValueManager");
		permissibleValueManager=(IPermissibleValueManager)Utility.getObject("edu.wustl.common.query.pvmanager.impl.CIDERPermissibleValueManager");
		return permissibleValueManager;
		
	}
	
}
