package edu.wustl.common.query.pvmanager.impl;

import edu.common.dynamicextensions.domain.PermissibleValue;

/**
 * This class is a wrapper for a  concept code in LexBig
 * @author namita_hardikar
 *
 */
public class ConceptValue extends PermissibleValue //implements ConceptValueInteface 
{

	private static final long serialVersionUID = 0L;
	/**
	 * concept code
	 */
	private String conceptCode;

	/**
	 * concept code description
	 */
	private String conceptDescription;

	/**
	 * Name of the coding scheme to which this code belongs
	 */
	private String codingSchemeName;

	/**
	 *Version or tag of the coding scheme to which this code belongs 
	 */
	private String codingSchemeVersionOrTag;


	/**
	 * getter setters for the members
	 * 
	 */

	public String getConceptCode() {
		return conceptCode;
	}

	public void setConceptCode(String conceptCode) {
		this.conceptCode = conceptCode;
	}

	public String getConceptDescription() {
		return conceptDescription;
	}

	public void setConceptDescription(String conceptDescription) {
		this.conceptDescription = conceptDescription;
	}

	public String getCodingSchemeName() {
		return codingSchemeName;
	}

	public void setCodingSchemeName(String codingSchemeName) {
		this.codingSchemeName = codingSchemeName;
	}

	public String getCodingSchemeVersionOrTag() {
		return codingSchemeVersionOrTag;
	}

	public void setCodingSchemeVersionOrTag(String codingSchemeVersionOrTag) {
		this.codingSchemeVersionOrTag = codingSchemeVersionOrTag;
	}



	
	@Override
	public Object getValueAsObject()
	{
		// TODO Auto-generated method stub
		return this;
	}

	/**
	 * this method will return concatenated value of concept code and description
	 * written as an helper for UI
	 */
	public String toString() {
		return "\t" + conceptCode + "\t: \t" + conceptDescription;

	}

}
