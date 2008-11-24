package edu.wustl.common.query.factory;

import edu.wustl.common.query.pvmanager.IPermissibleValueManager;
import edu.wustl.common.query.pvmanager.impl.LocalDEPermissibleValueManager;
import edu.wustl.common.util.Utility;
import edu.wustl.query.util.global.Variables;


public class PermissibleValueManagerFactory
{

	public static IPermissibleValueManager getPermissibleValueManager()
	{
		IPermissibleValueManager permissibleValueManager = null;
		//permissibleValueManager = Class.forName("edu.wustl.query.pvmanager.impl.CIDERPermissibleValueManager");
		permissibleValueManager=(IPermissibleValueManager)Utility.getObject(Variables.properties.getProperty("permissiblevalue.manager.impl"));
		return permissibleValueManager;
		
	}
	
}
