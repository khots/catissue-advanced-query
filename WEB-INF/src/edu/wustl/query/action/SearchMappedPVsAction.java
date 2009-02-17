package edu.wustl.query.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.common.dynamicextensions.domain.Entity;
import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.common.query.pvmanager.impl.PVManagerException;
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
 * Action Class to show the UI for Vocabulary Interface and to handle the Ajax request
 */
public class SearchMappedPVsAction extends Action
{

	/**
	 * This method handles the various Ajax request for VI
	 * @param mapping mapping
	 * @param form form
	 * @param request request
	 * @param response response
	 * @throws Exception Exception
	 * @return ActionForward actionForward
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		 final String targetVocab = request.getParameter(Constants.SELECTED_BOX);
		//get the id of the component on which user click to search for PVs
		String componentId = request.getParameter(Constants.COMPONENT_ID);
		if (componentId == null)
		{
			//need to save component id into the session for next Ajax requests
			componentId = (String) request.getSession().getAttribute(Constants.COMPONENT_ID);
		}
		String entityName = (String) request.getSession().getAttribute(Constants.ENTITY_NAME);
		Entity entity = (Entity) EntityCache.getCache().getEntityById(Long.valueOf((entityName)));
		Map<String, AttributeInterface> enumAttributeMap = (HashMap<String, AttributeInterface>)
		request.getSession().getAttribute(Constants.ENUMRATED_ATTRIBUTE);
		
		AttributeInterface attribute = (AttributeInterface) 
		enumAttributeMap.get(Constants.ATTRIBUTE_INTERFACE + componentId);

		if (targetVocab != null)
		{
			// AJAX Request handler for Getting Mapping data for source to target vocabulries
			String htmlResponse = getMappingForTargetVocab(targetVocab, attribute, entity);
			response.getWriter().write(htmlResponse);
			return null;
		}
		//new request for entity; remove the message from the session 
		request.getSession().removeAttribute(Constants.SRC_VOCAB_MESSAGE);
		getPVsFromSourceVocab(attribute, entity, componentId, request);
		return mapping.findForward(edu.wustl.query.util.global.Constants.SUCCESS);
	}
		/**
	 * This method generate the HTML for the Source vocabulary  (MED 1.0)
	 * @param attribute
	 * @param entity
	 * @param componentId
	 * @param request
	 * @throws VocabularyException
	 * @throws PVManagerException
	 */
	private void getPVsFromSourceVocab(AttributeInterface attribute, EntityInterface entity,
			String componentId, HttpServletRequest request) throws VocabularyException,
			PVManagerException
	{

		SearchPermissibleValueBizlogic bizLogic = (SearchPermissibleValueBizlogic) BizLogicFactory
				.getInstance().getBizLogic(Constants.SEARCH_PV_FROM_VOCAB_BILOGIC_ID);
		StringBuffer html = new StringBuffer();
	
		List<IConcept> pvList = bizLogic.getConfiguredPermissibleValueList(attribute, entity);
		String vocabURN = VIProperties.sourceVocabUrn;
		String vocabDisName = bizLogic.getDisplayNameForVocab(vocabURN);
		html.append(bizLogic.getRootVocabularyNodeHTML(vocabURN, vocabDisName));
		if(pvList != null && !pvList.isEmpty())
		{
			for(IConcept concept:pvList)
			{
				String checkboxId = vocabURN + Constants.ID_DEL + concept.getCode();
				html.append(bizLogic.getHTMLForConcept(vocabURN,concept,checkboxId));
			}
			html.append(bizLogic.getEndHTML());
			if( pvList.size()==VIProperties.maxPVsToShow)// Need to show Message Too Many Result on UI 
			{
				request.getSession().setAttribute(Constants.SRC_VOCAB_MESSAGE, bizLogic.getInfoMessage()
						.replace("MSG$-$",""));
			}
		}
		//set the data in session because need to show this data on page load
		request.getSession().setAttribute(Constants.MED_PV_HTML, html.toString());
		request.getSession().setAttribute(Constants.VOCABULIRES, bizLogic.getVocabularies());
		if (componentId != null)
		{
			request.getSession().setAttribute(Constants.COMPONENT_ID, componentId);
		}
	}
	
	/**
	 * This method returns the data mapped vocabularies
	 * @param targetVocab
	 * @param attribute
	 * @param entity
	 * @return
	 * @throws VocabularyException
	 * @throws PVManagerException
	 */
	private String getMappingForTargetVocab(String targetVocab, AttributeInterface attribute,
			EntityInterface entity) throws VocabularyException, PVManagerException
	{

		SearchPermissibleValueBizlogic bizLogic = (SearchPermissibleValueBizlogic) BizLogicFactory
				.getInstance().getBizLogic(Constants.SEARCH_PV_FROM_VOCAB_BILOGIC_ID);
		IVocabulary souVocabulary = bizLogic.getVocabulary(VIProperties.sourceVocabUrn);
		// Get the target vocabulary info from parameter
		String targetVacbArray[] = targetVocab.split("#");
		String targetVocabURN = targetVacbArray[0];
		String targetVocabDisName = targetVacbArray[1];
		IVocabulary targVocabulary = bizLogic.getVocabulary(targetVocabURN);
		
		StringBuffer html = new StringBuffer();
		if (!((Vocabulary)souVocabulary).equals(targVocabulary))
			{
				html.append(bizLogic.getRootVocabularyNodeHTML(targetVocabURN,targetVocabDisName));
				Map<String, List<IConcept>> vocabMappings = bizLogic.getMappedConcepts(attribute,
						targVocabulary, entity);
				html.append(getMappedHTMLForTargetVocab(targetVocabURN,vocabMappings));
	
			}

		return html.toString();
	}
	/**
	 * This method returns the mapping data as HTML
	 * @param html
	 * @param vocabName
	 * @param vocabversoin
	 * @param vocabMappings
	 * @throws VocabularyException 
	 * @throws NumberFormatException 
	 */
	private StringBuffer getMappedHTMLForTargetVocab(String vocabURN,
			Map<String, List<IConcept>> vocabMappings) throws NumberFormatException, VocabularyException
	{
		SearchPermissibleValueBizlogic bizLogic = (SearchPermissibleValueBizlogic) BizLogicFactory
				.getInstance().getBizLogic(Constants.SEARCH_PV_FROM_VOCAB_BILOGIC_ID);
		StringBuffer mappedHTML=new StringBuffer();
		int displayPVCount=1;
		boolean isMsgDisplayed=false;
		
		if (vocabMappings != null && vocabMappings.size()!=0)
		{
			Set<String> keySet = vocabMappings.keySet();
			Iterator<String> iterator = keySet.iterator();
			while (iterator.hasNext())
			{
				String conceptCode = iterator.next();
				List<IConcept> mappingList = vocabMappings.get(conceptCode);
				ListIterator<IConcept> mappingListItr = mappingList.listIterator();
				while (mappingListItr.hasNext())
				{
					
					IConcept concept = (IConcept) mappingListItr.next();
					if(displayPVCount<=VIProperties.maxPVsToShow)// Need to show only specified number of Concepts on UI
					{
						//we need to use the MED Concept code with mapped values
						
						
					String checkboxId = vocabURN + Constants.ID_DEL + conceptCode;
					mappedHTML.append(bizLogic.getHTMLForConcept(vocabURN,concept, checkboxId));
					displayPVCount++;
					}
					else
					{
						mappedHTML.append(bizLogic.getInfoMessage());
						isMsgDisplayed=true;
						break;//break inner loop
					}

				}
				if(isMsgDisplayed)
				{
					break; //break outer loop
				}
			}
		}
		else
		{
			mappedHTML.append(bizLogic.getNoMappingFoundHTML());
		}
		mappedHTML.append(bizLogic.getEndHTML());
		
		return mappedHTML;
	}
}
