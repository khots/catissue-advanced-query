package edu.wustl.query.pvmanager;

import java.util.List;

import edu.common.dynamicextensions.domaininterface.AttributeInterface;

public interface IPermissibleValueManager
{
	public boolean isEnumerated(AttributeInterface attribute);
	public List<String> getPermissibleValueList(AttributeInterface attribute);
	public boolean showListBoxForPV(AttributeInterface attribute);
}
