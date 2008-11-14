package edu.wustl.query.pvmanager.impl;

import java.util.List;

import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.wustl.query.pvmanager.IPermissibleValueManager;

public class CIDERPermissibleValueManager implements IPermissibleValueManager
{
	public CIDERPermissibleValueManager()
	{
		
	}

	public List<String> getPermissibleValueList(AttributeInterface attribute)
	{
		// TODO Auto-generated method stub
		//Check if attribute.getEntity has tag CONCEPT_LOOKUP_TABLE and attribute.getEntity has tag PV_FILTER and attribute.name=="name"
		//get the med concept codes from MED lookup table using the filter
		//get LexBig Concepts for the fetched med concept codes.
		return null;
	}

	public boolean isEnumerated(AttributeInterface attribute)
	{
		// TODO Auto-generated method stub
		//Check if attribute.getEntity has tag CONCEPT_LOOKUP_TABLE and attribute.getEntity has tag PV_FILTER and attribute.name=="name"
		//if yes return true else return false
		return false;
	}

	public boolean showListBoxForPV(AttributeInterface attribute)
	{
		// TODO Auto-generated method stub
		//count of vocab ==1 and attribute.pv count is < 5 then return true else false.
		return false;
	}
	
	public List getPermissibleValues(AttributeInterface attribute,String vocabulary)
	{
		//Check if attribute.getEntity has tag CONCEPT_LOOKUP_TABLE and attribute.getEntity has tag PV_FILTER and attribute.name=="name"
		//get MED concept codes from MED lookup table using filter.
		//iterate through MED concepts,fetch the corresponding mapped concept of given vocabulary.
		return null;
	}
	
	public List<String> getVocabularies()
	{
		return null;
	}
	 
}
