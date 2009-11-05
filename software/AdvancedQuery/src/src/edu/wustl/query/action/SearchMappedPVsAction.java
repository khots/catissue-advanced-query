
package edu.wustl.query.action;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.query.pvmanager.impl.PVManagerException;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;
import edu.wustl.common.vocab.IConcept;
import edu.wustl.common.vocab.IVocabulary;
import edu.wustl.common.vocab.VocabularyException;
import edu.wustl.common.vocab.impl.Vocabulary;
import edu.wustl.query.bizlogic.BizLogicFactory;
import edu.wustl.query.bizlogic.SearchPermissibleValueBizlogic;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.VIProperties;

/**
 * @author amit_doshi
 * Action Class to show the UI for Vocabulary Interface and to handle the Ajax request.
 */
public class SearchMappedPVsAction extends AbstractQueryBaseAction
{

	/**
	 * This method handles the various Ajax request for VI.
	 * @param mapping mapping
	 * @param form form
	 * @param request request
	 * @param response response
	 * @throws Exception Exception
	 * @return ActionForward actionForward
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected ActionForward executeBaseAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		final String targetVocabURN = request.getParameter(Constants.SELECTED_BOX);
		//get the id of the component on which user click to search for PVs

		String editVocabURN = request.getParameter("editVocabURN");

		final String componentId = getComponentId(request);
		ActionForward target = null;
		//String entityName = (String) request.getSession().getAttribute(Constants.ENTITY_NAME);
		//Entity entity = (Entity) EntityCache.getCache().getEntityById(Long.valueOf((entityName)));
		Map<String, QueryableAttributeInterface> enumAttributeMap = (HashMap<String, QueryableAttributeInterface>) request
				.getSession().getAttribute(Constants.ENUMRATED_ATTRIBUTE);

		QueryableAttributeInterface attribute = enumAttributeMap.get(Constants.ATTRIBUTE_INTERFACE
				+ componentId);

		SearchPermissibleValueBizlogic bizLogic = (SearchPermissibleValueBizlogic) BizLogicFactory
				.getInstance().getBizLogic(Constants.PV_VOCAB_BILOGIC_ID);
		// Need to retrieve HTML for requested Vocabulary Mapped or source vocabulary.
		if (targetVocabURN == null)
		{
			if (bizLogic.showDefaultPermissibleValues(attribute))
			{
				processVIPopupInDefaultOrEditMode(request, editVocabURN, componentId, attribute);
			}
			else
			{
				setVocabularies(request, attribute, editVocabURN);
				setComponentId(request, componentId);

			}
			target = mapping.findForward(edu.wustl.query.util.global.Constants.SUCCESS);
		}
		else
		{
			// user clicked on radio boxes
			//AJAX Request handler for Getting Mapping data for source or target vocabularies
			String htmlResponse = getPVsForRequestedVoab(request, targetVocabURN, attribute);
			response.getWriter().write(htmlResponse);
		}
		return target;
	}

	/**
	 * This method is used to open the VI pop up in Edit or Default mode.
	 * @param request HTTPRequest object
	 * @param editVocabURN edit URN of the Vocabulary
	 * @param componentId componentId of the attribute
	 * @param attribute  QueryableAttributeInterface object
	 * @throws VocabularyException throws VocabularyException
	 * @throws PVManagerException throws PVManagerException
	 */
	private void processVIPopupInDefaultOrEditMode(HttpServletRequest request, String editVocabURN,
			final String componentId, QueryableAttributeInterface attribute)
			throws VocabularyException, PVManagerException
	{
		//new request for entity; remove the message from the session
		removeHTMLFromSesson(request);
		if (Constants.NULL.equals(editVocabURN) || editVocabURN.equals(VIProperties.sourceVocabUrn))
		{
			/* load source vocabulary if in edit mode as well as in default mode*/
			String srcHTML = getPVsForSourceVocab(attribute, request);
			//set the data in session because need to show this data on page load
			request.getSession().setAttribute(Constants.PV_HTML + VIProperties.sourceVocabUrn,
					srcHTML);

		}
		else
		{
			//need to load other vocabulary in edit mode
			setEditVocabHTML(request, editVocabURN, attribute);
		}

		setComponentId(request, componentId);
		SearchPermissibleValueBizlogic bizLogic = (SearchPermissibleValueBizlogic) BizLogicFactory
				.getInstance().getBizLogic(Constants.PV_VOCAB_BILOGIC_ID);
		request.getSession().setAttribute(Constants.VOCABULIRES, bizLogic.getVocabularies());
	}

	/**
	 * This method is used to set the message for search option.
	 * @param request HttpServletRequest
	 * @param attribute QueryableAttributeInterface
	 * @param editVocabURN -Edit Vocabulary URN
	 * @throws VocabularyException throws VocabularyException
	 */
	private void setVocabularies(HttpServletRequest request, QueryableAttributeInterface attribute,
			String editVocabURN) throws VocabularyException
	{
		removeHTMLFromSesson(request);
		SearchPermissibleValueBizlogic bizLogic = (SearchPermissibleValueBizlogic) BizLogicFactory
				.getInstance().getBizLogic(Constants.PV_VOCAB_BILOGIC_ID);
		request.getSession().setAttribute(Constants.VOCABULIRES, bizLogic.getVocabularies());
		request.getSession().setAttribute(Constants.PV_HTML + VIProperties.sourceVocabUrn,
				bizLogic.getMessageToSearchForPVs(attribute));
		if (editVocabURN != null && !Constants.NULL.equals(editVocabURN))
		{//if editVocabURN is not null i.e. the message is need to set for the editVocabURN
			request.getSession().setAttribute(Constants.PV_HTML + editVocabURN,
					bizLogic.getMessageToSearchForPVs(attribute));
		}

	}

	/**
	 * This method is used to set the Edit Vocabulary HTML.
	 * @param request HTTPRequest object
	 * @param editVocabURN edit URN of the Vocabulary
	 * @param attribute QueryableAttributeInterface object
	 * @throws VocabularyException throws VocabularyException
	 * @throws PVManagerException throws PVManagerException
	 */
	private void setEditVocabHTML(HttpServletRequest request, String editVocabURN,
			QueryableAttributeInterface attribute) throws VocabularyException, PVManagerException
	{
		String trgHTML = getMappingForTargetVocab(editVocabURN, attribute, request);
		String[] trgHTMLAll = trgHTML.split(Constants.MSG_DEL);
		if (trgHTMLAll.length > Constants.ONE)
		{
			request.getSession().setAttribute(Constants.PV_HTML + editVocabURN, trgHTMLAll[0]);
			request.getSession().setAttribute(Constants.SRC_VOCAB_MESSAGE,
					trgHTMLAll[Constants.ONE]);
		}
		else
		{
			request.getSession().setAttribute(Constants.PV_HTML + editVocabURN, trgHTML);
		}
	}

	/**
	 * returns the HTML for the requested vocabulary.
	 * @param request HTTPRequest object
	 * @param targetVocabURN URN of the target Vocabulary
	 * @param attribute QueryableAttributeInterface object
	 * @return String - HTML for the requested vocabulary
	 * @throws VocabularyException throws VocabularyException
	 * @throws PVManagerException throws PVManagerException
	 */
	private String getPVsForRequestedVoab(HttpServletRequest request, final String targetVocabURN,
			QueryableAttributeInterface attribute) throws VocabularyException, PVManagerException
	{
		String htmlResponse;
		if (targetVocabURN.equals(VIProperties.sourceVocabUrn))
		{
			htmlResponse = getPVsForSourceVocab(attribute, request);
		}
		else
		{
			htmlResponse = getMappingForTargetVocab(targetVocabURN, attribute, request);
		}
		return htmlResponse;
	}

	/**
	 * This method is used to set the Component Id of the attribute in the session.
	 * @param request HttpServletRequest object
	 * @param componentId componentId of attribute
	 */
	private void setComponentId(HttpServletRequest request, String componentId)
	{
		if (componentId != null)
		{
			request.getSession().setAttribute(Constants.COMPONENT_ID, componentId);
		}
	}

	/**
	 * This method is used to get the Component Id of the attribute from the session.
	 * @param request HttpServletRequest object
	 * @return componentId componentId of attribute
	 */
	private String getComponentId(HttpServletRequest request)
	{
		String componentId = request.getParameter(Constants.COMPONENT_ID);
		if (componentId == null)
		{
			//need to save component id into the session for next Ajax requests
			componentId = (String) request.getSession().getAttribute(Constants.COMPONENT_ID);
		}
		return componentId;
	}

	/**
	 * This method is used to remove the session attributes.
	 * @param request HttpServletRequest object
	 */
	@SuppressWarnings("unchecked")
	private void removeHTMLFromSesson(HttpServletRequest request)
	{
		request.getSession().removeAttribute(Constants.SRC_VOCAB_MESSAGE);
		request.getSession().removeAttribute(Constants.CODE_VS_TOOLTIP);
		Enumeration attributeNames = request.getSession().getAttributeNames();
		while (attributeNames.hasMoreElements())
		{
			String atr = attributeNames.nextElement().toString();
			if (atr.indexOf(Constants.PV_HTML) == 0)
			{
				request.getSession().removeAttribute(atr);
			}
		}
	}

	/**
	 * This method generate the HTML for the Source vocabulary.
	 * @param attribute QueryableAttributeInterface object
	 * @param request HttpServletRequest object
	 * @throws VocabularyException throws VocabularyException
	 * @throws PVManagerException throws PVManagerException
	 * @return String HTML for the source vocabulary
	 */
	private String getPVsForSourceVocab(QueryableAttributeInterface attribute,
			HttpServletRequest request) throws VocabularyException, PVManagerException
	{

		SearchPermissibleValueBizlogic bizLogic = (SearchPermissibleValueBizlogic) BizLogicFactory
				.getInstance().getBizLogic(Constants.PV_VOCAB_BILOGIC_ID);
		StringBuffer html = new StringBuffer();
		List<Integer> showMessage = new ArrayList<Integer>();
		//Getting the PVs for the entity.
		List<IConcept> pvList = bizLogic.getConfiguredPermissibleValueList(attribute, showMessage);
		/*get the volume count from DB*/
		Map<String, String> conceptCodeVsVolumeInDB = bizLogic.getVolumeInDb();
		/*get the concept code v/s tooltip map from the session*/
		Map<String, String> codeVsTooltip = getCodeVsTooltipMap(request);
		//Sorting the concepts
		bizLogic.sortResults(pvList);
		String srcVocabURN = VIProperties.sourceVocabUrn;
		String vocabDisName = bizLogic.getDisplayNameForVocab(srcVocabURN);
		html.append(bizLogic.getRootVocabularyNodeHTML(srcVocabURN, vocabDisName));
		if (pvList == null || pvList.isEmpty())
		{
			html.append(bizLogic.getNoMappingFoundHTML());
			html.append(bizLogic.getEndHTML());
		}
		else
		{
			for (IConcept concept : pvList)
			{
				String checkboxId = srcVocabURN + Constants.ID_DEL + concept.getCode();
				checkboxId = checkboxId.replaceAll("'", "");
				html.append(bizLogic.getHTMLForConcept(srcVocabURN, concept, checkboxId,
						conceptCodeVsVolumeInDB.get(concept.getCode())));
				codeVsTooltip.put(checkboxId, bizLogic.getToolTip(concept, conceptCodeVsVolumeInDB
						.get(concept.getCode())));
			}
			html.append(bizLogic.getEndHTML());
			if (!showMessage.isEmpty())// Need to show Message Too Many Result on UI
			{
				html.append(bizLogic.getInfoMessage(showMessage.get(Constants.ONE), showMessage
						.get(Constants.TWO)));
				request.getSession().setAttribute(
						Constants.SRC_VOCAB_MESSAGE,
						bizLogic.getInfoMessage(showMessage.get(Constants.ONE),
								showMessage.get(Constants.TWO)).replace(Constants.MSG_DEL, ""));
			}
			request.getSession().setAttribute(Constants.CODE_VS_TOOLTIP, codeVsTooltip);
		}
		return html.toString();
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
	 * This method returns the data mapped vocabularies.
	 * @param targetVocabURN URN of the Target vocabulary
	 * @param attribute QueryableAttributeInterface object
	 * @param request -HttpServletRequest
	 * @return HTML as String
	 * @throws VocabularyException throws VocabularyException
	 * @throws PVManagerException throws PVManagerException
	 */
	private String getMappingForTargetVocab(String targetVocabURN,
			QueryableAttributeInterface attribute, HttpServletRequest request)
			throws VocabularyException, PVManagerException
	{

		SearchPermissibleValueBizlogic bizLogic = (SearchPermissibleValueBizlogic) BizLogicFactory
				.getInstance().getBizLogic(Constants.PV_VOCAB_BILOGIC_ID);
		IVocabulary souVocabulary = bizLogic.getVocabulary(VIProperties.sourceVocabUrn);
		// Get the target vocabulary info from parameter
		List<Integer> showMessage = new ArrayList<Integer>();
		String targetVocabDisName = bizLogic.getDisplayNameForVocab(targetVocabURN);
		IVocabulary targVocabulary = bizLogic.getVocabulary(targetVocabURN);

		StringBuffer html = new StringBuffer();
		if (!((Vocabulary) souVocabulary).equals(targVocabulary))
		{
			html.append(bizLogic.getRootVocabularyNodeHTML(targetVocabURN, targetVocabDisName));
			Map<String, List<IConcept>> vocabMappings = bizLogic.getMappedConcepts(attribute,
					targVocabulary, showMessage);

			html.append(getMappedHTMLForTargetVocab(targetVocabURN, vocabMappings, showMessage,
					request));
		}
		return html.toString();
	}

	/**
	 * This method returns the mapping data as HTML.
	 * @param vocabURN URN of the target vocabulary
	 * @param vocabMappings MAP contains the mapping found in the target vocabulary
	 * @param showMessage list contains the message which needs to be show on UI
	 * @param request -HttpServletRequest
	 * @return StringBuffer of mapped HTML for Target vocabulary.
	 * @throws VocabularyException throws VocabularyException
	 * @throws PVManagerException throws PVManagerException
	 */
	private StringBuffer getMappedHTMLForTargetVocab(String vocabURN,
			Map<String, List<IConcept>> vocabMappings, List<Integer> showMessage,
			HttpServletRequest request) throws VocabularyException, PVManagerException
	{
		SearchPermissibleValueBizlogic bizLogic = (SearchPermissibleValueBizlogic) BizLogicFactory
				.getInstance().getBizLogic(Constants.PV_VOCAB_BILOGIC_ID);
		StringBuffer mappedHTML = new StringBuffer();
		mappedHTML.append(bizLogic.getNoMappingFoundHTML());
		Map<String, String> conceptCodeVsVolumeInDB = bizLogic.getVolumeInDb();
		/*get the concept code v/s tooltip map from the session*/
		Map<String, String> codeVsTooltip = getCodeVsTooltipMap(request);
		if (vocabMappings != null && !vocabMappings.isEmpty())
		{
			mappedHTML = new StringBuffer();
			List<IConcept> mappingList = new ArrayList<IConcept>();
			Map<String, String> targetCodeVsMedCode = getTargetCodeVsMedCodeMap(vocabMappings,
					mappingList);
			//			Sorting the concepts
			bizLogic.sortResults(mappingList);

			for (IConcept concept : mappingList)
			{
				String medConceptCode = targetCodeVsMedCode.get(concept.getCode());
				String checkboxId = vocabURN + Constants.ID_DEL + medConceptCode;
				mappedHTML.append(bizLogic.getHTMLForTargetConcept(vocabURN, concept, checkboxId,
						conceptCodeVsVolumeInDB.get(medConceptCode)));
				codeVsTooltip.put(checkboxId, bizLogic.getToolTip(concept, conceptCodeVsVolumeInDB
						.get(medConceptCode)));

			}
			request.getSession().setAttribute(Constants.CODE_VS_TOOLTIP, codeVsTooltip);
		}

		mappedHTML.append(bizLogic.getEndHTML());
		if (!showMessage.isEmpty())// Need to show Message Too Many Result on UI
		{
			mappedHTML.append(bizLogic.getInfoMessage(showMessage.get(Constants.ONE), showMessage
					.get(Constants.TWO)));
		}

		return mappedHTML;
	}

	/**
	 * This method returns the MAP of target v/s MED CODE.
	 * @param vocabMappings MAP contains the mapping found in the target vocabulary
	 * @param mappingList  list of Mapped Concept
	 * @return MAP of [String,String] target v/s med code
	 */
	private Map<String, String> getTargetCodeVsMedCodeMap(
			Map<String, List<IConcept>> vocabMappings, List<IConcept> mappingList)
	{
		Set<String> keySet = vocabMappings.keySet();
		Map<String, String> targetCodeVsMedCode = new HashMap<String, String>();
		for (String medConceptCode : keySet)
		{
			List<IConcept> targetMappings = vocabMappings.get(medConceptCode);
			mappingList.addAll(targetMappings);
			for (IConcept mappedTargetConcept : targetMappings)
			{
				targetCodeVsMedCode.put(mappedTargetConcept.getCode(), medConceptCode);
			}
		}
		return targetCodeVsMedCode;
	}
}
