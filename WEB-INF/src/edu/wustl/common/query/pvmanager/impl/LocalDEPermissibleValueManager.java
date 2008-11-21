
package edu.wustl.common.query.pvmanager.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.common.dynamicextensions.domain.PermissibleValue;
import edu.common.dynamicextensions.domain.UserDefinedDE;
import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.BooleanValueInterface;
import edu.common.dynamicextensions.domaininterface.DateValueInterface;
import edu.common.dynamicextensions.domaininterface.DoubleValueInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.common.dynamicextensions.domaininterface.FloatValueInterface;
import edu.common.dynamicextensions.domaininterface.IntegerValueInterface;
import edu.common.dynamicextensions.domaininterface.LongValueInterface;
import edu.common.dynamicextensions.domaininterface.PermissibleValueInterface;
import edu.common.dynamicextensions.domaininterface.ShortValueInterface;
import edu.common.dynamicextensions.domaininterface.StringValueInterface;
import edu.wustl.common.query.pvmanager.IPermissibleValueManager;

public class LocalDEPermissibleValueManager implements IPermissibleValueManager
{


	public List<PermissibleValueInterface> getPermissibleValueList(AttributeInterface attribute, EntityInterface entity)
	{
		UserDefinedDE userDefineDE = (UserDefinedDE) attribute.getAttributeTypeInformation().getDataElement();
		List<PermissibleValueInterface> permissibleValues = new ArrayList<PermissibleValueInterface>();
		if (userDefineDE != null && userDefineDE.getPermissibleValueCollection() != null)
		{
			Iterator<PermissibleValueInterface> permissibleValueInterface = userDefineDE.getPermissibleValueCollection().iterator();
			while (permissibleValueInterface.hasNext())
			{
				PermissibleValue permValue = (PermissibleValue) permissibleValueInterface.next();
				if (permValue instanceof StringValueInterface)
				{
					permissibleValues.add(((StringValueInterface) permValue));
				}
				else if (permValue instanceof ShortValueInterface)
				{
					permissibleValues.add(((ShortValueInterface) permValue));
				}
				else if (permValue instanceof LongValueInterface)
				{
					permissibleValues.add(((LongValueInterface) permValue));
				}
				else if (permValue instanceof DateValueInterface)
				{
					permissibleValues.add(((DateValueInterface) permValue));
				}
				else if (permValue instanceof BooleanValueInterface)
				{
					permissibleValues.add(((BooleanValueInterface) permValue));
				}
				else if (permValue instanceof IntegerValueInterface)
				{
					permissibleValues.add(((IntegerValueInterface) permValue));
				}
				else if (permValue instanceof DoubleValueInterface)
				{
					permissibleValues.add((DoubleValueInterface) permValue);
				}
				else if (permValue instanceof FloatValueInterface)
				{
					permissibleValues.add(((FloatValueInterface) permValue));
				}
			}
		}
		return permissibleValues;
				
	}

	public boolean isEnumerated(AttributeInterface attribute, EntityInterface entity)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean showListBoxForPV(AttributeInterface attribute, EntityInterface entity)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
