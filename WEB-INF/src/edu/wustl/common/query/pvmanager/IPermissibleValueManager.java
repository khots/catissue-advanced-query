package edu.wustl.common.query.pvmanager;

import java.util.List;

import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.common.dynamicextensions.domaininterface.PermissibleValueInterface;

public interface IPermissibleValueManager
{
	public List<PermissibleValueInterface> getPermissibleValueList(AttributeInterface attribute);
	public boolean showListBoxForPV(AttributeInterface attribute);
	public boolean isEnumerated(AttributeInterface attribute);
	public List<PermissibleValueInterface> getPermissibleValueList(AttributeInterface attribute,EntityInterface entity);
	public boolean showListBoxForPV(AttributeInterface attribute,EntityInterface entity);
	public boolean isEnumerated(AttributeInterface attribute,EntityInterface entity);
}
