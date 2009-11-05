package edu.wustl.vi.search;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;



public class SearchConcept
{
	private String searchTerm;
	private String searchCriteria;
	private String codeBasedSearch;
	private String targetVocabs;
	private List<String> targetVocabList;
	private String vocabURN;
	
	

	
	
	public String getVocabURN()
	{
		return vocabURN;
	}


	
	public void setVocabURN(String vocabURN)
	{
		this.vocabURN = vocabURN;
	}


	public String getSearchTerm()
	{
		return searchTerm = searchTerm.trim();
	}

	
	public void setSearchTerm(String searchTerm)
	{
		this.searchTerm = searchTerm;
	}

	
	public String getSearchCriteria()
	{
		return searchCriteria;
	}

	
	public void setSearchCriteria(String searchCriteria)
	{
		this.searchCriteria = searchCriteria;
	}

	
	public String getCodeBasedSearch()
	{
		return codeBasedSearch;
	}

	
	public void setcodeBasedSearch(String codeBasedSearch)
	{
		this.codeBasedSearch = codeBasedSearch;
	}

	
	public String getTargetVocabs()
	{
		return targetVocabs;
	}

	
	public void setTargetVocabs(String targetVocabs)
	{
		this.targetVocabs = targetVocabs;
	}

	
	public List<String> getTargetVocabList()
	{
		return targetVocabList;
	}

	
	/*public void setTargetVocabList(List targetVocabList)
	{
		this.targetVocabList = targetVocabList;
	}*/
	
	public  void setTargetVocabList()
	{
		this.targetVocabList=new ArrayList<String>();
		if(targetVocabs!=null)
		{
			StringTokenizer allTrgVocabs = new StringTokenizer(targetVocabs, "@");
			while (allTrgVocabs.hasMoreTokens())
			{
				targetVocabList.add(allTrgVocabs.nextToken());
			}
		}
	}
	public boolean isCodeBasedSearch()
	{
		boolean isCodeBasedSearch=false;
		if(codeBasedSearch!=null && codeBasedSearch.equals("true"))
		{
			isCodeBasedSearch=true;
		}
		
		return isCodeBasedSearch;
	}
}
