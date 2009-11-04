
package edu.wustl.query.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.PermissibleValueInterface;
import edu.wustl.common.query.pvmanager.impl.PVManagerException;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;
import edu.wustl.common.vocab.IConcept;
import edu.wustl.common.vocab.IVocabulary;
import edu.wustl.common.vocab.VocabularyException;
import edu.wustl.common.vocab.impl.Concept;
import edu.wustl.common.vocab.impl.Vocabulary;
import edu.wustl.common.vocab.utility.VocabUtil;
import edu.wustl.query.bizlogic.BizLogicFactory;
import edu.wustl.query.bizlogic.SearchPermissibleValueBizlogic;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.VIProperties;
import edu.wustl.vi.search.SearchConcept;

/**
 * @author amit_doshi
 * Action Class to show the UI for Vocabulary Interface and to handle the Ajax request.
 */
public class SearchPermissibleValuesAction extends AbstractQueryBaseAction
{

	/**
	 * This method handles the various AJAX request for VI.
	 * @param mapping mapping
	 * @param form form
	 * @param request request
	 * @param response response
	 * @return ActionForward actionForward
	 * @throws Exception Exception
	 */
	@Override
	protected ActionForward executeBaseAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		SearchConcept searchConcept = getSearchConcept(request);

		SearchPermissibleValueBizlogic bizLogic = (SearchPermissibleValueBizlogic) BizLogicFactory
				.getInstance().getBizLogic(Constants.PV_VOCAB_BILOGIC_ID);
		//get the id of the component on which user click to search for PVs

		if (searchConcept.getSearchTerm() != null && searchConcept.getTargetVocabs() != null)
		{
			/* AJAX Request handler for Getting search term Result
			 * data for source to target vocabularies*/
			try
			{

				String html = searchTermInTargetVocab(searchConcept, request);
				response.getWriter().write(html);
			}
			catch (VocabularyException e)
			{
				response.getWriter().write(
						bizLogic.getErrorMessageAsHTML(e.getError().getErrorMessage()));
			}

		}
		return null;
	}

	/**
	 * This method will return the search concept with details.
	 * @param request request object
	 * @return SearchConcept
	 */
	private SearchConcept getSearchConcept(HttpServletRequest request)
	{
		SearchConcept searchConcept = new SearchConcept();
		searchConcept.setSearchTerm(request.getParameter(Constants.SEARCH_TERM));
		searchConcept.setSearchCriteria(request.getParameter(Constants.SEARCH_CRITERIA));
		searchConcept.setcodeBasedSearch(request.getParameter(Constants.IS_CODE_BASED_SEARCH));
		searchConcept.setTargetVocabs(request.getParameter(Constants.TARGET_VOCABS));
		searchConcept.setTargetVocabList();
		return searchConcept;
	}

	/**
	 * This method is used to get the component Id of the Attribute.
	 * @param request request object
	 * @return String componentId of the attribute for which VI is opened.
	 */
	private String getComponentId(HttpServletRequest request)
	{
		String componentId = request.getParameter(Constants.COMPONENT_ID);
		if (componentId == null)
		{
			//need to save componetid into the session for next Ajax requests
			componentId = (String) request.getSession().getAttribute(Constants.COMPONENT_ID);
		}
		return componentId;
	}

	/**
	 * This method will search for the entered text by the user across all the vocabularies.
	 * and return the HTML for the searched result
	 * @param searchConcept contains the searchConcept object
	 * @param request request object
	 * @return String HTML for the searchTerm in the target vocabulary.
	 * @throws VocabularyException throws VocabularyException
	 * @throws PVManagerException throws PVManagerException
	 */
	@SuppressWarnings("unchecked")
	private String searchTermInTargetVocab(SearchConcept searchConcept, HttpServletRequest request)
			throws VocabularyException, PVManagerException
	{
		String componentId = getComponentId(request);
		SearchPermissibleValueBizlogic bizLogic = (SearchPermissibleValueBizlogic) BizLogicFactory
				.getInstance().getBizLogic(Constants.PV_VOCAB_BILOGIC_ID);

		StringBuffer html = new StringBuffer();

		//StringTokenizer allTrgVocabs = new StringTokenizer(targetVocabs, "@");

		//String entityName = (String) request.getSession().getAttribute(Constants.ENTITY_NAME);
		Map<String, AttributeInterface> enumAttributeMap = (HashMap<String, AttributeInterface>) request
				.getSession().getAttribute(Constants.ENUMRATED_ATTRIBUTE);
		QueryableAttributeInterface attribute = (QueryableAttributeInterface) enumAttributeMap
				.get(Constants.ATTRIBUTE_INTERFACE + componentId);

		List<PermissibleValueInterface> pvList = bizLogic.getPermissibleValueListFromDB(attribute);
		for (String trgVocab : searchConcept.getTargetVocabList())
		{
			String[] vocabDetail = trgVocab.split("##");
			String vocabURN = vocabDetail[0], vocabDisName = vocabDetail[Constants.ONE];
			html.append(bizLogic.getRootVocabularyHTMLForSearch("srh_" + vocabURN, vocabDisName));
			//Pass the actual ENUM value for the selected search algorithm.
			//Change the VISearchAlgorithm.ANY_WORD to the value of the selected algorithm.
			searchConcept.setVocabURN(vocabURN);
			List<IConcept> conceptList = getSearchConceptsBasedOnTerm(searchConcept, bizLogic);
			html.append(processSearchedConcepts(pvList, vocabURN, conceptList, request));
		}
		return html.toString();
	}

	/**
	 * This method returns the List of IConcepts based on the search Criteria provided.
	 * (Code Based Search or Text Search)
	 * @param searchConcept contains the searchConcept object
	 * @param bizLogic SearchPermissibleValueBizlogic object
	 * @return List of IConcept
	 * @throws VocabularyException throws VocabularyException
	 */
	private List<IConcept> getSearchConceptsBasedOnTerm(SearchConcept searchConcept,
			SearchPermissibleValueBizlogic bizLogic) throws VocabularyException
	{
		return searchConcept.isCodeBasedSearch()
				? bizLogic.getConceptForCode(searchConcept)
				: bizLogic.searchConcept(searchConcept);
	}

	/**
	 * This method is used to process the Search Concept.
	 * @param pvList PermissibleValueInterface list
	 * @param vocabURN URN of the vocabulary
	 * @param conceptList list of concept
	 * @param request -HttpServletRequest
	 * @return HTML String
	 * @throws VocabularyException throws VocabularyException
	 * @throws PVManagerException throws VocabularyException
	 */
	private String processSearchedConcepts(List<PermissibleValueInterface> pvList, String vocabURN,
			List<IConcept> conceptList, HttpServletRequest request) throws VocabularyException,
			PVManagerException
	{
		SearchPermissibleValueBizlogic bizLogic = (SearchPermissibleValueBizlogic) BizLogicFactory
				.getInstance().getBizLogic(Constants.PV_VOCAB_BILOGIC_ID);
		IVocabulary sourceVocabulary = bizLogic.getVocabulary(VIProperties.sourceVocabUrn);
		StringBuffer html = new StringBuffer();
		/**
		 * to maintain the order of search results to show on UI
		 */
		Map<String, List<IConcept>> orderedConcepts = new LinkedHashMap<String, List<IConcept>>();
		List<IConcept> medMappedNValiedPVConcept = new ArrayList<IConcept>();
		List<IConcept> medMappedNNotValiedPVConcept = new ArrayList<IConcept>();
		List<IConcept> notMEDMappedConcept = new ArrayList<IConcept>();
		Map<String, String> sourceConceptMap = new HashMap<String, String>();
		if (conceptList != null && !conceptList.isEmpty())
		{
			for (IConcept concept : conceptList)
			{
				IConcept sourceConceptCode = new Concept();
				int status = isSourceVocabMappedTerm(concept, pvList, sourceConceptCode,
						sourceVocabulary);

				sourceConceptMap.put(concept.getCode(), sourceConceptCode.getCode());

				maintainOrderOfConcepts(orderedConcepts, status, concept,
						medMappedNValiedPVConcept, medMappedNNotValiedPVConcept,
						notMEDMappedConcept);
				//need to put the condition on size of each vocab

				if ((medMappedNValiedPVConcept.size() == VIProperties.maxPVsToShow))
				{
					break;
				}
			}
			/*Code Commented because the Requirement has changed as per the Bug: 14329
			 * Iterator<Map.Entry<String, List<IConcept>>> entryItr = orderedConcepts.entrySet()
					.iterator();
			while (entryItr.hasNext())
			{
				Map.Entry<String, List<IConcept>> entry = entryItr.next();
				String key = entry.getKey();
				bizLogic.sortResults(orderedConcepts.get(key));
			}*/
			html.append(createHTMLForConcept(orderedConcepts, vocabURN, sourceConceptMap, request));
		}
		else
		{
			html.append(bizLogic.getNoMappingFoundHTML());
		}
		int uiListSize = medMappedNValiedPVConcept.size() + medMappedNNotValiedPVConcept.size()
				+ notMEDMappedConcept.size();
		if (uiListSize >= VIProperties.maxPVsToShow)
		{
			html.append(bizLogic.getSearchMessage());
		}
		html.append(bizLogic.getEndHTML());

		return html.toString();
	}

	/**
	 * This method is used to create the HTML of searched Concepts.
	 * @param orderedConcepts Map contains the ordered concepts according to the Requirement
	 * @param vocabURN Vocabulary URN
	 * @param sourceConceptMap This map contains the target vocabulary concept code v/s MED mapped concept code
	 * @param request -HttpServletRequest
	 * @return String -HTML
	  * @throws VocabularyException throws VocabularyException
	 * @throws PVManagerException throws VocabularyException
	 */
	private String createHTMLForConcept(Map<String, List<IConcept>> orderedConcepts,
			String vocabURN, Map<String, String> sourceConceptMap, HttpServletRequest request)
			throws VocabularyException, PVManagerException
	{
		int rowsCreated = 0;
		Map<String, Object> conceptsDetail = new HashMap<String, Object>();
		StringBuffer html = new StringBuffer();
		SearchPermissibleValueBizlogic bizLogic = (SearchPermissibleValueBizlogic) BizLogicFactory
				.getInstance().getBizLogic(Constants.PV_VOCAB_BILOGIC_ID);
		Map<String, String> conceptCodeVsVolumeInDb = bizLogic.getVolumeInDb();
		conceptsDetail.put(Constants.ORDERED_CONCEPTS, orderedConcepts);
		conceptsDetail.put(Constants.VOCAB_URN, vocabURN);
		conceptsDetail.put(Constants.HTML, html);
		conceptsDetail.put(Constants.STATUS, "");
		conceptsDetail.put(Constants.SOURCE_CONCEPT_MAP, sourceConceptMap);
		conceptsDetail.put(Constants.ROW_CREATED, 0);
		conceptsDetail.put(Constants.CON_CODE_VS_VOL_INDB, conceptCodeVsVolumeInDb);
		if (vocabURN.equals(VocabUtil.getVocabProperties().getProperty("source.vocab.urn")))
		{
			/********** Get HTML for Source Vocabulary************/
			//create the HTML for MED MAPPED and Valid PV Concept
			conceptsDetail.put(Constants.STATUS, Constants.MED_MAPPED_N_VALID_PVCONCEPT);
			rowsCreated = getHTML(conceptsDetail, request);
			//create the HTML for NOT MED MAPPED Valid PV Concept
			conceptsDetail.put(Constants.STATUS, Constants.NOT_MED_VALED_PVCONCEPT);
			conceptsDetail.put(Constants.ROW_CREATED, rowsCreated);
			rowsCreated = (rowsCreated < VIProperties.maxPVsToShow) ? getHTML(conceptsDetail,
					request) : VIProperties.maxPVsToShow;
		}
		else
		{
			/********** Get HTML for Other Vocabulary************/
			/*first get the HTML for Result has mapping available
			 * in MED and it is  valid permissible value for the entity.
			 * Show result as bold enabled.
			 */
			conceptsDetail.put(Constants.STATUS, Constants.MED_MAPPED_N_VALID_PVCONCEPT);
			conceptsDetail.put(Constants.ROW_CREATED, rowsCreated);
			rowsCreated = getHTML(conceptsDetail, request);
			/* Result has mapping available in MED but it is not valid permissible value.
			 * Show result as disabled bold italicized.
			 */
			conceptsDetail.put(Constants.STATUS, Constants.MED_MAPPED_NOT_VALID_PVCONCEPT);
			conceptsDetail.put(Constants.ROW_CREATED, rowsCreated);
			rowsCreated = (rowsCreated < VIProperties.maxPVsToShow) ? getHTML(conceptsDetail,
					request) : VIProperties.maxPVsToShow;
			/* 	Result has no mapping available in MED.Show result as disabled  italicized.
			 */
			conceptsDetail.put(Constants.STATUS, Constants.NOT_MED_MAPPED_PVCONCEPT);
			conceptsDetail.put(Constants.ROW_CREATED, rowsCreated);
			rowsCreated = (rowsCreated < VIProperties.maxPVsToShow) ? getHTML(conceptsDetail,
					request) : VIProperties.maxPVsToShow;
		}

		return ((StringBuffer) conceptsDetail.get("html")).toString();

	}

	/**
	 * This method is used to get the HTML of the concepsDetails which is fetched from the LexBig.
	 * @param conceptsDetail This map contains the details of the concept for which we need to create the HTML
	 * @param request -HttpServletRequest
	 * @return int Number of rows created for UI
	 * @throws VocabularyException throws VocabularyException
	 */
	private int getHTML(Map<String, Object> conceptsDetail, HttpServletRequest request)
			throws VocabularyException
	{
		SearchPermissibleValueBizlogic bizLogic = (SearchPermissibleValueBizlogic) BizLogicFactory
				.getInstance().getBizLogic(Constants.PV_VOCAB_BILOGIC_ID);
		int tmpCount = (Integer) conceptsDetail.get(Constants.ROW_CREATED);
		StringBuffer html = (StringBuffer) conceptsDetail.get(Constants.HTML);
		String vocabURN = (String) conceptsDetail.get(Constants.VOCAB_URN);
		Map<String, String> sourceConceptMap = (Map<String, String>) conceptsDetail
				.get(Constants.SOURCE_CONCEPT_MAP);
		LinkedHashMap<String, List<IConcept>> orderedConcepts = (LinkedHashMap<String, List<IConcept>>) conceptsDetail
				.get(Constants.ORDERED_CONCEPTS);
		List<IConcept> conceptList = orderedConcepts.get(conceptsDetail.get("status"));
		/*get the concept code v/s tooltip map from the session*/
		Map<String, String> codeVsTooltip = getCodeVsTooltipMap(request);
		Map<String, String> conceptCodeVsVolumeInDb = (Map<String, String>) conceptsDetail
				.get(Constants.CON_CODE_VS_VOL_INDB);
		if (conceptList != null && !conceptList.isEmpty())
		{
			for (IConcept medRelatedConcept : conceptList)
			{
				String checkboxId = vocabURN + Constants.ID_DEL
						+ sourceConceptMap.get(medRelatedConcept.getCode());

				html.append(bizLogic.getHTMLForSearchedConcept(medRelatedConcept, "srh_"
						+ checkboxId, conceptsDetail));

				codeVsTooltip.put("srh_" + checkboxId, bizLogic.getToolTip(medRelatedConcept,
						conceptCodeVsVolumeInDb.get(medRelatedConcept.getCode())));
				tmpCount++;
				if (tmpCount == VIProperties.maxPVsToShow)
				{
					tmpCount++;
					break;
				}
			}
			conceptsDetail.put("html", html);
		}
		request.getSession().setAttribute(Constants.CODE_VS_TOOLTIP, codeVsTooltip);

		return tmpCount;
	}

	/**
	 * This method will return the MAP of concept Code v/s Tooltip.
	 * @param request - HttpServletRequest
	 * @return MAP of concept Code v/s Tooltip.
	 */
	private Map<String, String> getCodeVsTooltipMap(HttpServletRequest request)
	{
		Map<String, String> codeVsTooltip = (HashMap) request.getSession().getAttribute(
				Constants.CODE_VS_TOOLTIP);
		if (codeVsTooltip == null)
		{
			codeVsTooltip = new HashMap<String, String>();
		}
		return codeVsTooltip;
	}

	/**
	 * This method is used to maintain the order of the Concepts, which will be used to show on the UI.
	 * @param orderedConcepts used to store the order of Concepts
	 * @param status integer status which will be used to maintain the order of Concepts on UI
	 * @param medRelatedConcept List of MED Related Concepts
	 * @param medMappedNValiedPVConcept MAP is used to store the med Mapped and Valid PV Concept
	 * @param medMappedNNotValiedPVConcept MAP is used to store the  med Mapped and not Valid PV Concept
	 * @param notMEDMappedConcept MAP is used to store the not  med  Valid PV Concept
	 */
	private void maintainOrderOfConcepts(Map<String, List<IConcept>> orderedConcepts, int status,
			IConcept medRelatedConcept, List<IConcept> medMappedNValiedPVConcept,
			List<IConcept> medMappedNNotValiedPVConcept, List<IConcept> notMEDMappedConcept)
	{
		switch (status)
		{
			case Constants.ONE :
				medMappedNValiedPVConcept.add(medRelatedConcept);
				orderedConcepts.put(Constants.MED_MAPPED_N_VALID_PVCONCEPT,
						medMappedNValiedPVConcept);
				break;

			case Constants.TWO :
				notMEDMappedConcept.add(medRelatedConcept);
				orderedConcepts.put(Constants.NOT_MED_VALED_PVCONCEPT, notMEDMappedConcept);
				break;

			case Constants.THREE :
				medMappedNValiedPVConcept.add(medRelatedConcept);
				orderedConcepts.put(Constants.MED_MAPPED_N_VALID_PVCONCEPT,
						medMappedNValiedPVConcept);
				break;

			case Constants.FOUR :
				medMappedNNotValiedPVConcept.add(medRelatedConcept);
				orderedConcepts.put(Constants.MED_MAPPED_NOT_VALID_PVCONCEPT,
						medMappedNNotValiedPVConcept);
				break;

			case Constants.FIVE :
				notMEDMappedConcept.add(medRelatedConcept);
				orderedConcepts.put(Constants.NOT_MED_MAPPED_PVCONCEPT, notMEDMappedConcept);
				break;

			default :
				break;
		}

	}

	/**
	 * This method is used to check that concept is source vocabulary Mapped Concept or not.
	 * @param concept searched IConcept object
	 * @param pvList list of PermissibleValueInterface object
	 * @param sourceVocabulary source vocabulary object
	 * @param sourceConceptCode used to store the source concpet code
	 * @return status which will be used to maintain the order of the concepts.
	 * @throws VocabularyException throws VocabularyException
	 * @throws PVManagerException throws PVManagerException
	 */
	private int isSourceVocabMappedTerm(IConcept concept, List<PermissibleValueInterface> pvList,
			IConcept sourceConceptCode, IVocabulary sourceVocabulary) throws VocabularyException,
			PVManagerException
	{
		SearchPermissibleValueBizlogic bizLogic = (SearchPermissibleValueBizlogic) BizLogicFactory
				.getInstance().getBizLogic(Constants.PV_VOCAB_BILOGIC_ID);

		int status = 0;
		if (((Vocabulary) sourceVocabulary).equals(concept.getVocabulary()))
		{
			/*In case of MED check whether the result is valid permissible value for the entity.
			 *If yes make it enable and show it in bold face otherwise disable the result.
			 */
			IConcept returnedConcept = bizLogic.isConceptExistInPVList(concept, pvList);
			if (returnedConcept == null)
			{
				/*user searched on the MED and the concept is not valid for
				 * entity text should be normal disabled
				 */
				status = Constants.TWO;//"Normal_Disabled";
				((Concept) sourceConceptCode).setCode(concept.getCode());
			}
			else
			{
				//If MED coded and its part of PV then show text should be bold with normal
				status = Constants.ONE;//"Normal_Bold_Enabled";
				((Concept) sourceConceptCode).setCode(returnedConcept.getCode());
			}

		}
		else
		{
			List<IConcept> concepts = bizLogic.isSourceVocabCodedTerm(concept,
					VIProperties.translationAssociation, sourceVocabulary);
			if (concepts == null)
			{
				/* 	Result has no mapping available in MED.
				 *	Show result as disabled  italicized.
				 * (user has searched on other vocabulary and the searched concept is not MED coded
				 * then show text Italic Normal and Disabled;)
				 */
				status = Constants.FIVE;//"Normal_Italic_Disabled";
				((Concept) sourceConceptCode).setCode(concept.getCode());
			}
			else
			{
				IConcept sourceConcept = bizLogic.isConceptsExistInPVList(concepts, pvList);
				/*if (!concepts.isEmpty())
				{

					setVolumeInDbForTargetConcept(bizLogic, concepts, concept);
				}*/

				if (sourceConcept != null)
				{
					/*	Result has mapping available in MED and it is valid
					 *  permissible value for the entity.
					 *  Show result as bold enabled.
					 * (user has searched on other vocabulary and the searched concept
					 * is MED coded and its is  valid PV for entity then show text bold
					 * with enabled.)
					 */
					status = Constants.THREE;//"Normal_Bold_Enabled";
					((Concept) sourceConceptCode).setCode(sourceConcept.getCode());
				}
				else
				{
					/* Result has mapping available in MED but it is
					 * not valid permissible value.
					 * Show result as disabled bold italicized.
					 * (user has searched on other vocabulary and the searched concept
					 * is MED coded but its is not valid PV  entity then show text Bold
					 * Italic Disabled.)
					 */
					status = Constants.FOUR;//"Bold_Italic_Disabled";
					((Concept) sourceConceptCode).setCode(concept.getCode());
				}
			}
		}
		return status;
	}
}
