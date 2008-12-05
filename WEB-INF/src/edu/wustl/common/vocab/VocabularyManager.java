
package edu.wustl.common.vocab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.LexGrid.LexBIG.DataModel.Collections.AssociationList;
import org.LexGrid.LexBIG.DataModel.Collections.CodingSchemeRenderingList;
import org.LexGrid.LexBIG.DataModel.Collections.LocalNameList;
import org.LexGrid.LexBIG.DataModel.Collections.NameAndValueList;
import org.LexGrid.LexBIG.DataModel.Collections.ResolvedConceptReferenceList;
import org.LexGrid.LexBIG.DataModel.Collections.SortOptionList;
import org.LexGrid.LexBIG.DataModel.Core.AssociatedConcept;
import org.LexGrid.LexBIG.DataModel.Core.Association;
import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeSummary;
import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.DataModel.Core.NameAndValue;
import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.DataModel.InterfaceElements.CodingSchemeRendering;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Exceptions.LBInvocationException;
import org.LexGrid.LexBIG.Exceptions.LBParameterException;
import org.LexGrid.LexBIG.Impl.LexBIGServiceImpl;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.ActiveOption;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.SearchDesignationOption;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.Utility.ConvenienceMethods;
import org.LexGrid.codingSchemes.CodingScheme;
import org.LexGrid.concepts.CodedEntry;
import org.LexGrid.concepts.Concepts;

import edu.wustl.query.util.global.Variables;
/**
 * 
 * @author namita_hardikar
 * 
 */
public final class VocabularyManager
{

	/**
	 * singleton instance of VocabularyManager class
	 **/
	private static VocabularyManager vocabManager;

	/**
	 * LexGrid Service
	 * **/
	private static LexBIGService lbservice;

	/**
	 * private constructor
	 */
	private VocabularyManager()
	{
		lbservice = LexBIGServiceImpl.defaultInstance();

	}

	/**
	 * public method that will return singleton instance of the
	 * VocabularyManager
	 * 
	 * @return
	 */
	public synchronized static VocabularyManager getVocabularyManager()
	{

		if (vocabManager == null)
		{
			vocabManager = new VocabularyManager();
		}
		return vocabManager;

	}

	/**
	 * return all coding concepts in a service
	 */

	public List<CodingSchemeSummary> getAllCodingSchemes()
	{

		List<CodingSchemeSummary> csSummaryList = new ArrayList<CodingSchemeSummary>();

		try
		{
			CodingSchemeRenderingList supportedCodingSchemes = lbservice
					.getSupportedCodingSchemes();
			CodingSchemeRendering csRenderedArray[] = supportedCodingSchemes
					.getCodingSchemeRendering();
			for (int i = 0; i < csRenderedArray.length; i++)
			{
				csSummaryList.add(csRenderedArray[i].getCodingSchemeSummary());
			}
		}
		catch (LBException lbe)
		{
			// logger
			lbe.printStackTrace();
		}
		return csSummaryList;
	}

	/**
	 * returns a conceptcode for a given Codingschemename
	 * 
	 * @param codingSchemeName
	 * @param conceptCodeName
	 * @return
	 */

	public List<CodedEntry> getAllConceptsFromService(final String codingSchemeName,
			final String codingSchemeVersion)
	{
		List<CodedEntry> conValueList = new ArrayList<CodedEntry>();

		CodingSchemeVersionOrTag version = new CodingSchemeVersionOrTag();
		version.setVersion(codingSchemeVersion);
	try
		{
			CodedNodeSet codes = lbservice.getCodingSchemeConcepts(codingSchemeName, version);
			ResolvedConceptReferenceList resolvedConcepts = codes.resolveToList(null, null,
					null, 100);
			ResolvedConceptReference[] resolvedRef = resolvedConcepts.getResolvedConceptReference();

			for (int i = 0; i < resolvedRef.length; i++)
			{
				resolvedRef[i].getReferencedEntry();
				conValueList.add(resolvedRef[i].getReferencedEntry());
			}

		}
		catch (LBException lbe)
		{
			lbe.printStackTrace();
		}
		return conValueList;
	}

	/**
	 * get coding scheme with the given name and version
	 * @param codingSchemeName
	 * @param version
	 * @return
	 */
	public CodingScheme getCodingSchemeWithName(final String codingSchemeName, final String version)
	{
		
		CodingScheme codingScheme = null;
		CodingSchemeVersionOrTag csVersion = new CodingSchemeVersionOrTag();
		try
		{
					csVersion.setVersion(version);
					codingScheme = lbservice.resolveCodingScheme(codingSchemeName, csVersion);
					
				
		}
		
		catch (LBException lbe)
		{// logger
			lbe.printStackTrace();
		}
		return codingScheme;
	}

	/**
	 * 
	 * @param sourceVocab
	 *            source vocabulary name
	 * @param targetVocab
	 *            target vocabulary name
	 * @param conceptCode
	 *            array of concept code names whose mappings are to be fetched
	 * @return a list of maps.Each map has key = the concept code name value =
	 *         list of the mappings for the concept code.
	 */

	public Map<String, List<CodedEntry>> getMappedConcepts(final String sourceVocab,final String srcVocabVersion,
			final String targetVocab,final String targetVocabVersion,final String relationType, final List<String> conceptCode)
	{

		Map<String, List<CodedEntry>> mappings = new HashMap<String, List<CodedEntry>>();

		try
		{
			//CodingScheme codingSch = getCodingSchemeWithName(sourceVocab,srcVocabVersion);
			CodingSchemeVersionOrTag csVersion = new CodingSchemeVersionOrTag();
			String urn = "";
			csVersion.setVersion(srcVocabVersion);
			// get source coding scheme URN
			urn = getCodingSchemeURN(sourceVocab, srcVocabVersion);
			//get the target coding scheme URN 
			String targetCodingSchURN = getCodingSchemeURN(targetVocab,targetVocabVersion);
			// Fetch Mapping for each concept in the list
			ListIterator<String> conceptItr = conceptCode.listIterator();
			while (conceptItr.hasNext())
			{
				String currentConceptCode = conceptItr.next();
				//get the target coding scheme URN
				List<CodedEntry> mappedConceptList = getMappedConceptCode(currentConceptCode, urn,
						csVersion,targetCodingSchURN,relationType);
				if(mappedConceptList != null )
				{
					mappings.put(currentConceptCode, mappedConceptList);
				}
			}
		}
		catch (Exception e)
		{
			// logger
			e.printStackTrace();
		}
		if(mappings.isEmpty()) {
			mappings = null;
		}
		return mappings;
	}

	/**
	 * this method will return the URN of the coding scheme
	 * @param coding scheme name
	 * @param coding scheme version
	 */
	private String getCodingSchemeURN(final String sourceVocab, final String srcVocabVersion) 
	{
		String urn = null;
		CodingSchemeRenderingList csRenderLst;
		try
		{
			csRenderLst = lbservice.getSupportedCodingSchemes();
			CodingSchemeRendering[] rendering = csRenderLst.getCodingSchemeRendering();
			for (int y = 0; y < rendering.length; y++)
			{
				if (rendering[y].getCodingSchemeSummary().getLocalName().equalsIgnoreCase(sourceVocab) 
						&& 
						rendering[y].getCodingSchemeSummary().getRepresentsVersion().equalsIgnoreCase(srcVocabVersion))
				{
					urn = rendering[y].getCodingSchemeSummary().getCodingSchemeURN();
					break;
				}
			}
		}
		catch (LBInvocationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return urn;
	}
	/**
	 * this method takes the concept code , the source vocabulary urn and its
	 * version to return a list of coded entries for the mapped concepts found
	 * 
	 * @param code
	 * @param schemeURN
	 * @param csvt
	 * @return
	 * @throws LBException
	 */
	protected List<CodedEntry> getMappedConceptCode(final String code, final String schemeURN,
			final CodingSchemeVersionOrTag csvt,final String targetSchURN,final String relationType) throws LBException
	{

		//List<CodedEntry> mappedConcepts = new ArrayList<CodedEntry>();
		List<CodedEntry> mappedConcepts = null;
		NameAndValue nameAndvalue = new NameAndValue();
		NameAndValueList nvList = new NameAndValueList();
		nameAndvalue.setName(relationType);
		nvList.addNameAndValue(nameAndvalue);
		
		ResolvedConceptReferenceList matches = lbservice.getNodeGraph(schemeURN, csvt, null)
	
				.restrictToAssociations(nvList, null)
				.resolveAsList(ConvenienceMethods.createConceptReference(code, schemeURN), true,
						false, 1, 1, new LocalNameList(), null, null, 1024);

		if (matches.getResolvedConceptReferenceCount() > 0)
		{
			mappedConcepts = new ArrayList<CodedEntry>();
			ResolvedConceptReference ref = (ResolvedConceptReference) matches
					.enumerateResolvedConceptReference().nextElement();

			AssociationList sourceof = ref.getSourceOf();
			Association[] associations = sourceof
					.getAssociation();
			for (int i = 0; i < associations.length; i++)
			{
				Association assoc = associations[i];
				AssociatedConcept[] assoConcepts = assoc.getAssociatedConcepts().getAssociatedConcept();
				for (int j = 0; j < assoConcepts.length; j++)
				{
					AssociatedConcept assoConcept = assoConcepts[j];
					
					if(assoConcept.getCodingSchemeURN().equals(targetSchURN)) // check URN instead
					{
						
						mappedConcepts.add(assoConcept.getReferencedEntry());
					}
				}
			}
		}
		if(mappedConcepts!=null && mappedConcepts.isEmpty()){
			mappedConcepts = null;
		}
		return mappedConcepts;
	}
	
	/**
	 * Utility method to return a list of coded entries
	 */
	public List<CodedEntry> getCodedEntriesForNames(final String codingschemeName,final String codingSchemeVersion, final List<String> codes)
	{
		CodingScheme codingSch = getCodingSchemeWithName(codingschemeName, codingSchemeVersion);
		Concepts concepts = codingSch.getConcepts();
		CodedEntry allEntries[] = concepts.getConcept();
		Iterator<String> iterator = codes.iterator();
		List<CodedEntry> listOfCodedEntries = new ArrayList<CodedEntry>();
		while (iterator.hasNext())
		{
			for (int i = 0; i < allEntries.length; i++)
			{
				if (allEntries[i].getConceptCode().equals(iterator.next()))
				{
					listOfCodedEntries.add(allEntries[i]);
				}
			}
		}
		return listOfCodedEntries;
	}
	/**
	 * method to return conceptcodes for an array of Ids passed
	 * 
	 * @param codingSchemeName
	 * @param codingSchemeVersion
	 * @param codeIds
	 * @return
	 */

	public List<CodedEntry> getConceptCode(final String codingSchemeName, final String codingSchemeVersion,
			final List<String> codeIds)
	{
		List<CodedEntry> cvList = new ArrayList<CodedEntry>();

		CodingSchemeVersionOrTag version = new CodingSchemeVersionOrTag();
		version.setVersion(codingSchemeVersion);
		SortOptionList sortBy = new SortOptionList();
		sortBy.addEntry(ConvenienceMethods.createSortOption("entityDescription", Boolean.TRUE));
		
		try
		{
			CodedNodeSet codes = lbservice.getCodingSchemeConcepts(codingSchemeName, version);
			ResolvedConceptReferenceList resolvedConcepts = codes.resolveToList(sortBy, null,
					null, 100);
			ResolvedConceptReference[] resolvedRef = resolvedConcepts.getResolvedConceptReference();

			for (int i = 0; i < resolvedRef.length; i++)
			{
				for (int j = 0; j < codeIds.size(); j++)
				{
					if (resolvedRef[i].getConceptCode().equals(codeIds.get(j)))
					{
						cvList.add(resolvedRef[i].getReferencedEntry());
					}
				}
			}
		}
		catch (LBException e)
		{
			e.printStackTrace();
		}

		return cvList;
	}
	
	/**
	 * method to search
	 */
	
	public List<String> search(String term, String codingSchemeName ,String codingSchemeVersion) {
		
		List<String> matchedTerms = null;
	
	
		String matchAlgorithm =Variables.properties.getProperty("matchAlgorithm"); 
		String primarySortOption =  Variables.properties.getProperty("primarySortOption"); 
		String secondarySortOption =  Variables.properties.getProperty("code");
		CodingSchemeVersionOrTag csVersionTag = new CodingSchemeVersionOrTag();
		csVersionTag.setVersion(codingSchemeVersion);
		
		try
		{
			lbservice.getSupportedCodingSchemes();
			CodedNodeSet matchingConcepts = lbservice.getCodingSchemeConcepts(codingSchemeName, csVersionTag)
			.restrictToStatus(ActiveOption.ALL, null)
			.restrictToMatchingDesignations(term,SearchDesignationOption.ALL,
			matchAlgorithm,
				null);
			
			// Sort by search engine recommendation & code ... can be configured
			SortOptionList sortCriteria =
			    Constructors.createSortOptionList(new String[]{primarySortOption, secondarySortOption}); 
			
			// Analyze the result ...
			ResolvedConceptReferenceList matches =
				matchingConcepts.resolveToList(sortCriteria, null, null, 10);
			ResolvedConceptReference conceptRef[] = matches.getResolvedConceptReference();
			if (conceptRef .length !=0 ) {
					matchedTerms = new ArrayList<String>();
					for(int refCount =0 ; refCount < conceptRef.length ; refCount ++)
					{
						matchedTerms.add(conceptRef[refCount].getEntityDescription().getContent()  );
					}
			}
		 }
		
		catch (LBInvocationException e)
		{
			
			e.printStackTrace();
		}
		catch (LBParameterException e)
		{
			
			e.printStackTrace();
		}
		catch (LBException e)
		{
			
			e.printStackTrace();
		}
		return matchedTerms;
	}


}
