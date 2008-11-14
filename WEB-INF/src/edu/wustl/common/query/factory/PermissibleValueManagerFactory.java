package edu.wustl.common.query.factory;

import edu.wustl.common.util.Utility;
import edu.wustl.query.pvmanager.IPermissibleValueManager;

public class PermissibleValueManagerFactory
{

	public IPermissibleValueManager getPermissibleValueManager()
	{
		IPermissibleValueManager permissibleValueManager = null;
		//permissibleValueManager = Class.forName("edu.wustl.query.pvmanager.impl.CIDERPermissibleValueManager");
		permissibleValueManager=(IPermissibleValueManager)Utility.getObject("edu.wustl.query.pvmanager.impl.CIDERPermissibleValueManager");
		return permissibleValueManager;
		
	}
	
}
