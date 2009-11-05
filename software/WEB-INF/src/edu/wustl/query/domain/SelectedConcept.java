
package edu.wustl.query.domain;

public class SelectedConcept
{

	private String vocabName;
	private String vocabVersion;
	private String conceptName;
	private String conceptCode;
	private String medCode;

	/**
	 * 
	 * @return
	 */
	public String getVocabName()
	{
		return vocabName;
	}

	/**
	 * 
	 * @param vocabName
	 */
	public void setVocabName(String vocabName)
	{
		this.vocabName = vocabName;
	}

	/**
	 * 
	 * @return
	 */
	public String getVocabVersion()
	{
		return vocabVersion;
	}

	/**
	 * 
	 * @param vocabVersion
	 */
	public void setVocabVersion(String vocabVersion)
	{
		this.vocabVersion = vocabVersion;
	}

	/**
	 * 
	 * @return
	 */
	public String getConceptName()
	{
		return conceptName;
	}

	/**
	 * 
	 * @param conceptName
	 */
	public void setConceptName(String conceptName)
	{
		this.conceptName = conceptName;
	}

	/**
	 * 
	 * @return
	 */
	public String getConceptCode()
	{
		return conceptCode;
	}

	/**
	 * 
	 * @param conceptCode
	 */
	public void setConceptCode(String conceptCode)
	{
		this.conceptCode = conceptCode;
	}

	/**
	 * 
	 * @return
	 */
	public String getMedCode()
	{
		return medCode;
	}

	/**
	 * 
	 * @param medCode
	 */
	public void setMedCode(String medCode)
	{
		this.medCode = medCode;
	}

}
