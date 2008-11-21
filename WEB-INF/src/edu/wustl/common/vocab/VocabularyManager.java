
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
import org.LexGrid.LexBIG.DataModel.Collections.ResolvedConceptReferenceList;
import org.LexGrid.LexBIG.DataModel.Collections.SortOptionList;
import org.LexGrid.LexBIG.DataModel.Core.AssociatedConcept;
import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeSummary;
import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.DataModel.InterfaceElements.CodingSchemeRendering;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Impl.LexBIGServiceImpl;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.Utility.ConvenienceMethods;
import org.LexGrid.codingSchemes.CodingScheme;
import org.LexGrid.concepts.CodedEntry;
import org.LexGrid.concepts.Concepts;
import org.LexGrid.LexBIG.DataModel.Core.Association;
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
		CodedNodeSet.PropertyType[] propType = {CodedNodeSet.PropertyType.GENERIC,
				CodedNodeSet.PropertyType.COMMENT, CodedNodeSet.PropertyType.PRESENTATION,
				CodedNodeSet.PropertyType.INSTRUCTION};
		try
		{
			CodedNodeSet codes = lbservice.getCodingSchemeConcepts(codingSchemeName, version);
			ResolvedConceptReferenceList resolvedConcepts = codes.resolveToList(null, null,
					propType, 100);
			ResolvedConceptReference[] resolvedRef = resolvedConcepts.getResolvedConceptReference();

			for (int i = 0; i < resolvedRef.length; i++)
			{
				resolvedRef[i].getReferencedEntry();
				conValueList.add(resolvedRef[i].getReferencedEntry());
			}

		}
		catch (LBException e)
		{
			// 
			// logger
		}
		return conValueList;
	}

	/**
	 * utility method to return a coding scheme given a codingscheme name
	 */
	// TODO version next iteration
	public CodingScheme getCodingSchemeWithName(final String codingSchemeName)
	{
		CodingSchemeSummary csSummary = null;
		CodingScheme codingScheme = null;
		CodingSchemeVersionOrTag csVersion = new CodingSchemeVersionOrTag();
		try
		{
			CodingSchemeRenderingList supportedCodingSchemes = lbservice
					.getSupportedCodingSchemes();
			CodingSchemeRendering csRenderedArray[] = supportedCodingSchemes
					.getCodingSchemeRendering();
			for (int i = 0; i < csRenderedArray.length; i++)
			{

				csSummary = csRenderedArray[i].getCodingSchemeSummary();
				if (csSummary.getFormalName().equals(codingSchemeName))
				{

					csVersion.setVersion(csSummary.getRepresentsVersion());
					codingScheme = lbservice.resolveCodingScheme(csSummary.getLocalName(), csVersion);
					break;
				}
			}
		}
		catch (LBException lbe)
		{// logger

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
	 *            array od conceptcode anmes whose mappings are to be fetched
	 * @return a list of maps.Each map has key = the concept code name value =
	 *         list of the mappings for the concept code.
	 */

	public Map<String, List<CodedEntry>> getMappedConcepts(final String sourceVocab,
			final String targetVocab, final List<String> conceptCode)
	{

		Map<String, List<CodedEntry>> mappings = new HashMap<String, List<CodedEntry>>();

		try
		{
			CodingScheme codingSch = getCodingSchemeWithName(sourceVocab);
			CodingSchemeVersionOrTag csVersion = new CodingSchemeVersionOrTag();
			String urn = "";
			csVersion.setVersion(codingSch.getRepresentsVersion());
			// get source coding scheme URN
			CodingSchemeRenderingList csRenderLst = lbservice.getSupportedCodingSchemes();
			CodingSchemeRendering[] rendering = csRenderLst.getCodingSchemeRendering();
			for (int y = 0; y < rendering.length; y++)
			{
				if (rendering[y].getCodingSchemeSummary().getFormalName().equalsIgnoreCase(
						sourceVocab)
						|| rendering[y].getCodingSchemeSummary().getLocalName().equalsIgnoreCase(
								sourceVocab))
				{
					urn = rendering[y].getCodingSchemeSummary().getCodingSchemeURN();
					break;
				}
			}
			// Fetch Mapping for each concept in the list
			ListIterator<String> conceptItr = conceptCode.listIterator();
			while (conceptItr.hasNext())
			{
				String currentConceptCode = conceptItr.next();
				List<CodedEntry> mappedConceptList = getMappedConceptCode(currentConceptCode, urn,
						csVersion);
				mappings.put(currentConceptCode, mappedConceptList);
			}
		}
		catch (Exception e)
		{
			// logger
		}

		return mappings;
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
			final CodingSchemeVersionOrTag csvt) throws LBException
	{

		List<CodedEntry> mappedConcepts = new ArrayList<CodedEntry>();

		ResolvedConceptReferenceList matches = lbservice.getNodeGraph(schemeURN, csvt, null)
				.resolveAsList(ConvenienceMethods.createConceptReference(code, schemeURN), true,
						false, 1, 1, new LocalNameList(), null, null, 1024);

		if (matches.getResolvedConceptReferenceCount() > 0)
		{
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
					mappedConcepts.add(assoConcept.getReferencedEntry());
				}
			}
		}
		return mappedConcepts;
	}

	/**
	 * Utility method to return a list of codedentries
	 */
	public List<CodedEntry> getCodedEntriesForNames(final String codingschemeName, final List<String> codes)
	{
		CodingScheme codingSch = getCodingSchemeWithName(codingschemeName);
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
		CodedNodeSet.PropertyType[] propType = {CodedNodeSet.PropertyType.GENERIC,
				CodedNodeSet.PropertyType.COMMENT, CodedNodeSet.PropertyType.PRESENTATION,
				CodedNodeSet.PropertyType.INSTRUCTION};
		SortOptionList sortBy = new SortOptionList();
		sortBy.addEntry(ConvenienceMethods.createSortOption("entityDescription", Boolean.TRUE));
		
		try
		{
			CodedNodeSet codes = lbservice.getCodingSchemeConcepts(codingSchemeName, version);
			ResolvedConceptReferenceList resolvedConcepts = codes.resolveToList(sortBy, null,
					propType, 100);
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
			// logger;
		}

		return cvList;
	}

}
