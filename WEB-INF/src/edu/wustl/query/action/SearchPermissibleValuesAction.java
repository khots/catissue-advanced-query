
package edu.wustl.query.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
		
		List<PermissibleValueInterface> pvList=bizLogic.getPermissibleValueListFromDB(attribute, entity);
		int count=Integer.parseInt(VocabUtil.getVocabProperties().getProperty("pvs.to.show"));
		while (allTrgVocabs.hasMoreTokens())
		{
			String[] vocabDetail = allTrgVocabs.nextToken().split(":");
			String vocabName = vocabDetail[0];
			String vocabVersion = vocabDetail[1];
			String vocabDisName = vocabDetail[2];
			
			html.append(bizLogic.getRootVocabularyHTMLForSearch("srh_" + vocabName, vocabVersion,
																vocabDisName));
			List<IConcept> conceptList = bizLogic.searchConcept(searchTerm, vocabName, vocabVersion);
			int displayPVCount=1;
			if (conceptList != null && conceptList.size()!=0)
			{
				for(IConcept concept:conceptList)
				{
					IConcept sourceConcept=null;
					String status=isSourceVocabMappedTerm(concept,pvList,sourceConcept) ;
					IConcept medRelatedConcept =sourceConcept;
					String checkboxId=null;
					if(medRelatedConcept==null)
					{
						checkboxId = vocabName + "@" + vocabVersion + ":" + concept.getCode();// concept.getCode();
					}
					else
					{
						checkboxId = vocabName + "@" + vocabVersion + ":" + medRelatedConcept.getCode();// concept.getCode();
					}
					
					if(displayPVCount<=count)// Need to show only specified number of Concepts on UI
					{
					 html.append(bizLogic.getHTMLForSearchedConcept("srh_" + vocabName,
							vocabVersion, concept,"srh_"+checkboxId,status));
					 displayPVCount++;
					}
					else
					{
						html.append(bizLogic.getInfoMessage());
						break;
					}
				}
			}
			else
			{
				html.append(bizLogic.getNoMappingFoundHTML());
			}

			html.append(bizLogic.getEndHTML());
		}
		return html.toString();
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
	private String isSourceVocabMappedTerm(IConcept concept, List<PermissibleValueInterface> pvList, IConcept sourceConcept) 
					throws VocabularyException
	{
		SearchPermissibleValueBizlogic bizLogic = (SearchPermissibleValueBizlogic) BizLogicFactory
		.getInstance().getBizLogic(Constants.SEARCH_PV_FROM_VOCAB_BILOGIC_ID);
		
		String relationType =VocabUtil.getVocabProperties().getProperty("vocab.translation.association.name");
		IVocabulary sourceVocabulary = new Vocabulary(VocabUtil.getVocabProperties().getProperty(
		"source.vocab.name"), VocabUtil.getVocabProperties().getProperty(
		"source.vocab.version"),VocabUtil.getVocabProperties().getProperty(
		"source.vocab.urn"));
		String status = "";
		if(((Vocabulary)sourceVocabulary).equals((Vocabulary)concept.getVocabulary()))
		{
			sourceConcept= bizLogic.isConceptExistInPVList(concept, pvList);
			if(sourceConcept!=null)
			{
				//If MED coded and its part of PV then show text should be bold with normal 
				status="Normal_Bold_Enabled";
			}
			else
			{
				/*user searched on the MED and the concept is not valid for
				 * entity text should be normal disabled 
				 */
				status="Normal_Disabled";
			}
		}
		else
		{
			List<IConcept> concepts=bizLogic.isSourceVocabCodedTerm(concept, relationType, sourceVocabulary);
			if(concepts!=null)
			{
				sourceConcept=bizLogic.isConceptsExistInPVList(concepts, pvList);
			}
			if(concepts!=null && sourceConcept!=null )
			{
				/*user has searched on other vocabulary and the searched concept is MED coded 
				 * and its is  valid PV for entity then show text bold with enabled.
				 */
				 status="Normal_Bold_Enabled";
			}
			else if(concepts!=null && sourceConcept==null )
			{
				/*user has searched on other vocabulary and the searched concept is MED coded 
				 * but  its is not valid PV  entity then show text Bold Italic Disabled.
				 */
				 status="Bold_Italic_Disabled";
			}
			else
			{
				/* user has searched on other vocabulary and the searched concept is not MED coded
				 * then show text Italic Normal and Disabled;  
				 */
				 status="Normal_Italic_Disabled";
			}
		}
		return status;
	}
}
