
package edu.wustl.query.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.common.dynamicextensions.domain.Entity;
import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.PermissibleValueInterface;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.common.query.pvmanager.impl.PVManagerException;
import edu.wustl.common.vocab.IConcept;
import edu.wustl.common.vocab.IVocabulary;
import edu.wustl.common.vocab.VocabularyException;
import edu.wustl.common.vocab.impl.Concept;
import edu.wustl.common.vocab.impl.Vocabulary;
import edu.wustl.common.vocab.utility.VocabUtil;
import edu.wustl.query.bizlogic.BizLogicFactory;
import edu.wustl.query.bizlogic.SearchPermissibleValueBizlogic;
import edu.wustl.query.util.global.Constants;

/**
 * @author amit_doshi
 * Action Class to show the UI for Vocabulary Interface and to handle the Ajax request
 */
public class SearchPermissibleValuesAction extends Action
{

	/**
	 * This method handles the various Ajax request for VI
	 * @param mapping mapping
	 * @param form form
	 * @param request request
	 * @param response response
	 * @return ActionForward actionForward
	 * @throws Exception Exception
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		String searchTerm = request.getParameter(Constants.SEARCH_TERM);
		String operation = request.getParameter(Constants.OPERATION);
		String targetVocabs = request.getParameter("targetVocabsForSearchTerm");
		//get the id of the component on which user click to search for PVs
		String componentId = request.getParameter(Constants.COMPONENT_ID);
		if (componentId == null)
		{
			//need to save componetid into the session for next Ajax requests
			componentId = (String) request.getSession().getAttribute(Constants.COMPONENT_ID);
		}
		
		if (searchTerm != null && targetVocabs != null)
		{
			// AJAX Request handler for Getting search term Result data for source to target vocabularies
			SearchPermissibleValueBizlogic bizLogic = (SearchPermissibleValueBizlogic) BizLogicFactory
			.getInstance().getBizLogic(Constants.SEARCH_PV_FROM_VOCAB_BILOGIC_ID);
			if(operation.equals(Constants.ABORT))
			{
				response.getWriter().write(VocabUtil.getVocabProperties().getProperty("abort.message"));
			}
			else
			{
				try
				{
					searchTerm=searchTerm.trim();
					String html = searchTermInTargetVocab(searchTerm, targetVocabs,componentId,request);
					response.getWriter().write(html);
				}
				catch (VocabularyException e)
				{
					response.getWriter().write(bizLogic.getErrorMessageAsHTML());
				}
			}
			
		}
		return null;
	}
	/**
	 * This method will search for the entered text by the user across all the vocabularies
	 * and return the HTML for the searched result
	 * @param searchTerm
	 * @param targetVocabs
	 * @param request 
	 * @param componentId 
	 * @return
	 * @throws VocabularyException
	 * @throws PVManagerException 
	 */
	@SuppressWarnings("unchecked")
	private String searchTermInTargetVocab(String searchTerm, String targetVocabs, String componentId, HttpServletRequest request)
			throws VocabularyException, PVManagerException
	{
		SearchPermissibleValueBizlogic bizLogic = (SearchPermissibleValueBizlogic) BizLogicFactory
		.getInstance().getBizLogic(Constants.SEARCH_PV_FROM_VOCAB_BILOGIC_ID);
		
		StringBuffer html = new StringBuffer();
		
		StringTokenizer allTrgVocabs = new StringTokenizer(targetVocabs, "@");
		
		String entityName = (String) request.getSession().getAttribute(Constants.ENTITY_NAME);
		Entity entity = (Entity) EntityCache.getCache().getEntityById(Long.valueOf((entityName)));
		
		Map<String, AttributeInterface> enumAttributeMap = (HashMap<String, AttributeInterface>)
		request.getSession().getAttribute(Constants.ENUMRATED_ATTRIBUTE);
		AttributeInterface attribute = (AttributeInterface) enumAttributeMap
		.get(Constants.ATTRIBUTE_INTERFACE + componentId);
		int maxPV=Integer.parseInt(VocabUtil.getVocabProperties().getProperty("pvs.to.show"));
		List<PermissibleValueInterface> pvList=bizLogic.getPermissibleValueListFromDB(attribute, entity);
		boolean messageFlag=false;
		while (allTrgVocabs.hasMoreTokens())
		{
			String[] vocabDetail = allTrgVocabs.nextToken().split(":");
			String vocabName = vocabDetail[0],vocabVersion = vocabDetail[1],vocabDisName = vocabDetail[2];
			html.append(bizLogic.getRootVocabularyHTMLForSearch("srh_" + vocabName, vocabVersion,
																vocabDisName));
			List<IConcept> conceptList = bizLogic.searchConcept(searchTerm, vocabName, vocabVersion);
			/**
			 * to maintain the order of search results to show on UI
			 */
			Map<String,List<IConcept>> orderedConcepts=new LinkedHashMap<String,List<IConcept>>();
			List<IConcept> medMappedNValiedPVConcept=new ArrayList<IConcept> ();
			List<IConcept>  medMappedNNotValiedPVConcept=new ArrayList<IConcept> ();
			List<IConcept>  notMEDMappedConcept=new ArrayList<IConcept> ();
			if (conceptList != null && conceptList.size()!=0)
			{
				for(IConcept concept:conceptList)
				{
					IConcept sourceConcept=null;
					int status=isSourceVocabMappedTerm(concept,pvList,sourceConcept) ;
					
					maintainOrderOfConcepts(orderedConcepts,status,concept
							,medMappedNValiedPVConcept,medMappedNNotValiedPVConcept,notMEDMappedConcept);
					//need to put the condition on size of each vocab
					
					if( (medMappedNValiedPVConcept.size()==maxPV) 
							||(medMappedNNotValiedPVConcept.size()==maxPV)|| (notMEDMappedConcept.size()==maxPV))
					{
						messageFlag=true;
						break;
					}
				}
				createHTMLForConcept(orderedConcepts,vocabName,vocabVersion,html);
			}
			else
			{
				html.append(bizLogic.getNoMappingFoundHTML());
			}
			if(messageFlag)
			{
				html.append(bizLogic.getInfoMessage());
			}
			html.append(bizLogic.getEndHTML());
		}
		return html.toString();
	}
	private void createHTMLForConcept(Map<String, List<IConcept>> orderedConcepts,
			String vocabName, String vocabVersion, StringBuffer html) throws VocabularyException
	{
	
		/********** Get HTML for Source Vocabulary************
		 * need to ask about condition ;cause backend change*/ 
		if(vocabName.equals(VocabUtil.getVocabProperties().getProperty("source.vocab.name"))
				&& vocabVersion.equals(VocabUtil.getVocabProperties().getProperty("source.vocab.version")))
		{
				getHTML(orderedConcepts, vocabName, vocabVersion, html,Constants.MED_MAPPED_N_VALID_PVCONCEPT);
				
				getHTML(orderedConcepts, vocabName, vocabVersion, html,Constants.NOT_MED_VALED_PVCONCEPT);
		}
		else
		{
		/********** Get HTML for Other Vocabulary************/
		/*first get the HTML for Result has mapping available in MED and it is valid permissible value for the entity.
		 *  Show result as bold enabled.
		 */
		getHTML(orderedConcepts, vocabName, vocabVersion, html,Constants.MED_MAPPED_N_VALID_PVCONCEPT);
		/* Result has mapping available in MED but it is not valid permissible value.
		 * Show result as disabled bold italicized.
		 */
		getHTML(orderedConcepts, vocabName, vocabVersion, html,Constants.MED_MAPPED_N_NOT_VALIED_PVCONCEPT);
		/* 	Result has no mapping available in MED.
		 *	Show result as disabled  italicized.
		 */
		getHTML(orderedConcepts, vocabName, vocabVersion, html,Constants.NOT_MED_MAPPED_PVCONCEPT);
		}
	}
	/**
	 * @param orderedConcepts
	 * @param vocabName
	 * @param vocabVersion
	 * @param html
	 * @param bizLogic
	 * @param status
	 * @throws VocabularyException
	 */
	private void getHTML(Map<String, List<IConcept>> orderedConcepts, String vocabName,
			String vocabVersion, StringBuffer html, 
			String status) throws VocabularyException
	{
		SearchPermissibleValueBizlogic bizLogic = (SearchPermissibleValueBizlogic) BizLogicFactory
		.getInstance().getBizLogic(Constants.SEARCH_PV_FROM_VOCAB_BILOGIC_ID);
		List<IConcept> conceptList =orderedConcepts.get(status);
		if(conceptList!=null && conceptList.size()>0)
		{
			for(IConcept medRelatedConcept:conceptList)
			{
					String checkboxId=vocabName + "@" + vocabVersion + ":" + medRelatedConcept.getCode();// concept.getCode();
					html.append(bizLogic.getHTMLForSearchedConcept("srh_" + vocabName,
							vocabVersion, medRelatedConcept,"srh_"+checkboxId,status));
				
			}
		}
	}
	private void maintainOrderOfConcepts(Map<String, List<IConcept>> orderedConcepts, int status, 
	IConcept medRelatedConcept, List<IConcept>  medMappedNValiedPVConcept, 
	List<IConcept>  medMappedNNotValiedPVConcept, List<IConcept>  notMEDMappedConcept)
	{
		switch(status)
		{
			case 1:	medMappedNValiedPVConcept.add(medRelatedConcept);
					orderedConcepts.put(Constants.MED_MAPPED_N_VALID_PVCONCEPT,medMappedNValiedPVConcept);
					break;
						
			case 2:	notMEDMappedConcept.add(medRelatedConcept);
					orderedConcepts.put(Constants.NOT_MED_VALED_PVCONCEPT,notMEDMappedConcept); 
					break;
			case 3:	medMappedNValiedPVConcept.add(medRelatedConcept);
					orderedConcepts.put(Constants.MED_MAPPED_N_VALID_PVCONCEPT,medMappedNValiedPVConcept);
					break;
			case 4: medMappedNNotValiedPVConcept.add(medRelatedConcept);
					orderedConcepts.put(Constants.MED_MAPPED_N_NOT_VALIED_PVCONCEPT,medMappedNNotValiedPVConcept);
					break;
			case 5:	notMEDMappedConcept.add(medRelatedConcept);
					orderedConcepts.put(Constants.NOT_MED_MAPPED_PVCONCEPT,notMEDMappedConcept);
					break;
		}
		
	}
	/**
	 * 
	 * @param concept 
	 * @param pvList 
	 * @param sourceConcept 
	 * @param componentId
	 * @param request
	 * @return
	 * @throws VocabularyException 
	 */
	private int isSourceVocabMappedTerm(IConcept concept, List<PermissibleValueInterface> pvList, IConcept sourceConcept) 
					throws VocabularyException
	{
		SearchPermissibleValueBizlogic bizLogic = (SearchPermissibleValueBizlogic) BizLogicFactory
		.getInstance().getBizLogic(Constants.SEARCH_PV_FROM_VOCAB_BILOGIC_ID);
		
		String relationType =VocabUtil.getVocabProperties().getProperty("vocab.translation.association.name");
		IVocabulary sourceVocabulary = new Vocabulary(VocabUtil.getVocabProperties().getProperty(
		"source.vocab.name"), VocabUtil.getVocabProperties().getProperty(
		"source.vocab.version"),VocabUtil.getVocabProperties().getProperty(
		"source.vocab.urn"));
		int status =0;
		if(((Vocabulary)sourceVocabulary).equals((Vocabulary)concept.getVocabulary()))
		{
			/*In case of MED check whether the result is valid permissible value for the entity. 
			 *If yes make it enable and show it in bold face otherwise disable the result.
			 */
			sourceConcept= bizLogic.isConceptExistInPVList(concept, pvList);
			if(sourceConcept!=null)
			{
				/* sourceConcept=new Concept();
				((Concept)sourceConcept).setCode(srcCon.getCode());
				((Concept)sourceConcept).setVocabulary(srcCon.getVocabulary());
				((Concept)sourceConcept).setDescription(srcCon.getDescription());*/
				//If MED coded and its part of PV then show text should be bold with normal 
				status=1;//"Normal_Bold_Enabled";
			}
			else
			{
				/* sourceConcept=new Concept();
				((Concept)sourceConcept).setCode(concept.getCode());
				((Concept)sourceConcept).setVocabulary(concept.getVocabulary());
				((Concept)sourceConcept).setDescription(concept.getDescription());*/
				/*user searched on the MED and the concept is not valid for
				 * entity text should be normal disabled 
				 */
				status=2;//"Normal_Disabled";
			}
		}
		else
		{
			List<IConcept> concepts=bizLogic.isSourceVocabCodedTerm(concept, relationType, sourceVocabulary);
			if(concepts!=null)
			{
				 sourceConcept=bizLogic.isConceptsExistInPVList(concepts, pvList);
				 /*sourceConcept=new Concept();
				((Concept)sourceConcept).setCode(srcCon.getCode());
				((Concept)sourceConcept).setVocabulary(srcCon.getVocabulary());
				((Concept)sourceConcept).setDescription(srcCon.getDescription());*/
			}
			if(concepts!=null && sourceConcept!=null )
			{
				/*	Result has mapping available in MED and it is valid permissible value for the entity.
				 *  Show result as bold enabled.
				 * (user has searched on other vocabulary and the searched concept is MED coded 
				 * and its is  valid PV for entity then show text bold with enabled.)
				 * 
				 */
				 status=3;//"Normal_Bold_Enabled";
			}
			else if(concepts!=null && sourceConcept==null )
			{
				/* Result has mapping available in MED but it is not valid permissible value.
				 * Show result as disabled bold italicized.
				 * (user has searched on other vocabulary and the searched concept is MED coded 
				 * but  its is not valid PV  entity then show text Bold Italic Disabled.)
				 */
				 status=4;//"Bold_Italic_Disabled";
			}
			else
			{
				/* 	Result has no mapping available in MED.
				 *	Show result as disabled  italicized.
				 * (user has searched on other vocabulary and the searched concept is not MED coded
				 * then show text Italic Normal and Disabled;)  
				 */
				 status=5;//"Normal_Italic_Disabled";
			}
		}
		return status;
	}
}
