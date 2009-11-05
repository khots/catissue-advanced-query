/**
 * 
 */
package edu.wustl.vi.enums;


/**
 * @author taraben_khoiwala
 *
 */
public enum VISearchAlgorithm {
	ANY_WORD("contains"),
	EXACT_MATCH("exactMatch"),
	EXACT_PHRASE("LuceneQuery");
	
	private final String algorithm;
	VISearchAlgorithm(String algorithm)
	{
		this.algorithm = algorithm;
	}
	public String getAlgorithm()
	{
		return this.algorithm;
	}
}
