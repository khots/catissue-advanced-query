package edu.wustl.query.domain;

public class SelectedConcept {
	
	private String vocabName;
	private String vocabVersion;
	private String conceptName;
	private String conceptCode;
	private String medCode;
	
	public String getVocabName() {
		return vocabName;
	}
	public void setVocabName(String vocabName) {
		this.vocabName = vocabName;
	}
	public String getVocabVersion() {
		return vocabVersion;
	}
	public void setVocabVersion(String vocabVersion) {
		this.vocabVersion = vocabVersion;
	}
	public String getConceptName() {
		return conceptName;
	}
	public void setConceptName(String conceptName) {
		this.conceptName = conceptName;
	}
	public String getConceptCode() {
		return conceptCode;
	}
	public void setConceptCode(String conceptCode) {
		this.conceptCode = conceptCode;
	}
	public String getMedCode() {
		return medCode;
	}
	public void setMedCode(String medCode) {
		this.medCode = medCode;
	}
	

}
