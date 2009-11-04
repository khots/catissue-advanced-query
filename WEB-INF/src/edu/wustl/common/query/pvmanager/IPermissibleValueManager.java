package edu.wustl.common.query.pvmanager;

import java.util.List;

import edu.common.dynamicextensions.domaininterface.PermissibleValueInterface;
import edu.wustl.common.query.pvmanager.impl.PVManagerException;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;

public interface IPermissibleValueManager
{
	List<PermissibleValueInterface> getPermissibleValueList(QueryableAttributeInterface attribute) throws PVManagerException;
	boolean isEnumerated(QueryableAttributeInterface attribute);
	boolean showIcon(QueryableAttributeInterface atttribute)throws PVManagerException;
}

