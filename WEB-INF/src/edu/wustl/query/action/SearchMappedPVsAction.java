package edu.wustl.query.action;

import java.util.ArrayList;
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
import edu.wustl.common.vocab.utility.VocabUtil;
import edu.wustl.query.bizlogic.BizLogicFactory;
import edu.wustl.query.bizlogic.SearchPermissibleValueBizlogic;
import edu.wustl.query.util.global.Constants;

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
		int count=Integer.parseInt( VocabUtil.getVocabProperties().getProperty("pvs.to.show"));
		StringBuffer html = new StringBuffer();
	
		List<IConcept> pvList = bizLogic.getConfiguredPermissibleValueList(attribute, entity);
		String vocabName = VocabUtil.getVocabProperties().getProperty("source.vocab.name");
		String vocabVer = VocabUtil.getVocabProperties().getProperty("source.vocab.version");
		String vocabDisName = bizLogic.getDisplayNameForVocab(vocabName, vocabVer);
		html.append(bizLogic.getRootVocabularyNodeHTML(vocabName, vocabVer, vocabDisName));
		if(pvList!=null)
		{
			for(IConcept concept:pvList)
			{
				String id = vocabName + "@" + vocabVer + ":" + concept.getCode();
				html.append(bizLogic.getHTMLForConcept(vocabName, vocabVer, concept,id));
			}
			html.append(bizLogic.getEndHTML());
			if( pvList.size()==count)// Need to show Message Too Many Result on UI 
			{
				request.getSession().setAttribute(Constants.SRC_VOCAB_MESSAGE, bizLogic.getInfoMessage()
						.replace("MSG$-$",""));
			}
		}
		//set the data in session because need to show this data on page load
		request.getSession().setAttribute(Constants.MED_PV_HTML, html.toString());
		request.getSession().setAttribute(Constants.VOCABULIRES, bizLogic.getVocabulries());
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
		String sourceVocabulary = VocabUtil.getVocabProperties().getProperty("source.vocab.name");
		String sourceVocabVer = VocabUtil.getVocabProperties().getProperty("source.vocab.version");
		// Get the target vocabulary info from parameter
		String targetVacbArray[] = targetVocab.split("#");
		String targetVocabName = targetVacbArray[0];
		String targetVocabVer = targetVacbArray[1];
		String targetVocabURN = targetVacbArray[2];
		String targetVocabDisName = targetVacbArray[3];
		IVocabulary targetVocabulary = new Vocabulary(targetVocabName, targetVocabVer,targetVocabURN);
		
		StringBuffer html = new StringBuffer();
		if (!sourceVocabulary.equalsIgnoreCase(targetVocabName)
					|| !sourceVocabVer.equalsIgnoreCase(targetVocabVer))
			{
				html.append(bizLogic.getRootVocabularyNodeHTML(targetVocabName, targetVocabVer,
						targetVocabDisName));
				Map<String, List<IConcept>> vocabMappings = bizLogic.getMappedConcepts(attribute,
						targetVocabulary, entity);
				html.append(getMappedHTMLForTargetVocab(targetVocabName, targetVocabVer, vocabMappings));
	
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
	private StringBuffer getMappedHTMLForTargetVocab(String vocabName, String vocabversoin,
			Map<String, List<IConcept>> vocabMappings) throws NumberFormatException, VocabularyException
	{
		SearchPermissibleValueBizlogic bizLogic = (SearchPermissibleValueBizlogic) BizLogicFactory
				.getInstance().getBizLogic(Constants.SEARCH_PV_FROM_VOCAB_BILOGIC_ID);
		StringBuffer mappedHTML=new StringBuffer();
		int displayPVCount=1;
		boolean isMsgDisplayed=false;
		int count=Integer.parseInt(VocabUtil.getVocabProperties().getProperty("pvs.to.show"));
		if (vocabMappings != null)
		{
			Set<String> keySet = vocabMappings.keySet();
			Iterator<String> iterator = keySet.iterator();
			while (iterator.hasNext())
			{
				String conceptCode = iterator.next();
				List<IConcept> mappingList = (ArrayList) vocabMappings.get(conceptCode);
				ListIterator<IConcept> mappingListItr = mappingList.listIterator();
				while (mappingListItr.hasNext())
				{
					
					IConcept concept = (IConcept) mappingListItr.next();
					if(displayPVCount<=count)// Need to show only specified number of Concepts on UI
					{
						//we need to use the MED Concept code with mapped values
					String checkboxId = vocabName + "@" + vocabversoin + ":" + conceptCode;
					mappedHTML.append(bizLogic.getHTMLForConcept(vocabName, vocabversoin,
							concept, checkboxId));
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
