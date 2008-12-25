package edu.wustl.query.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.common.vocab.IConcept;
import edu.wustl.common.vocab.VocabularyException;
import edu.wustl.common.vocab.utility.VocabUtil;
import edu.wustl.query.bizlogic.BizLogicFactory;
import edu.wustl.query.bizlogic.SearchPermissibleValuesFromVocabBizlogic;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Variables;
/**
 * @author amit_doshi
 * Action Class to show the UI for Vcabulary Interface and to handle the Ajax request
 */
public class SearchPermissibleValuesAction extends Action {
	
	/**
	 * This method loads the data required for categorySearch.jsp
	 * @param mapping mapping
	 * @param form form
	 * @param request request
	 * @param response response
	 * @throws Exception Exception
	 * @return ActionForward actionForward
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		final String targetVocab=request.getParameter("selectedCheckBox");
		String searchTerm=request.getParameter("searchTerm");
		String targetVocabsForSearchTerm=request.getParameter("targetVocabsForSearchTerm");
		//get the id of the component on which user click to search for PVs
		String componentId=request.getParameter("componentId");
		if(componentId==null)
		{
			//need to save componetid into the session for next Ajax requests
			componentId=(String)request.getSession().getAttribute(Constants.COMPONENT_ID);
		}
		 String entityName =(String) request.getSession().getAttribute(Constants.ENTITY_NAME);
		 Entity entity = (Entity) EntityCache.getCache().getEntityById(Long.valueOf((entityName)));
		 Map<String,AttributeInterface> enumAttributeMap=(HashMap<String,AttributeInterface>) request.getSession().getAttribute(Constants.ENUMRATED_ATTRIBUTE);
		 AttributeInterface attribute=(AttributeInterface)enumAttributeMap.get(Constants.ATTRIBUTE_INTERFACE+componentId);
		
		
		if(targetVocab!=null)
		{
			// Ajax Request handler for Getting Mapping data for source to target vocabulries
			String html=getMappedVocabDataAsHTML(targetVocab,attribute,entity);
			response.getWriter().write(html);
			return null;
		}
		if(searchTerm!=null && targetVocabsForSearchTerm!=null)
		{
			// Ajax Request handler for Getting search term Result data for source to target vocabulries
			try
			{
			String html=getSearchedVocabDataAsHTML(searchTerm,targetVocabsForSearchTerm);
			response.getWriter().write(html);
			}
			catch(VocabularyException e)
			{
				response.getWriter().write("<table width='100%' height='100%'><tr><td class='black_ar_tt'>Please enter valid search term<td></tr></table>");
			}
			return  null;
		}
		
		generatePermValueHTMLForMED(attribute,entity,componentId,request);
		return mapping.findForward(edu.wustl.query.util.global.Constants.SUCCESS);
	}
	private String getSearchedVocabDataAsHTML(String searchTerm,String targetVocabsForSearchTerm) throws VocabularyException 
	{
		SearchPermissibleValuesFromVocabBizlogic bizLogic = (SearchPermissibleValuesFromVocabBizlogic)BizLogicFactory.getInstance().getBizLogic(Constants.SEARCH_PV_FROM_VOCAB_BILOGIC_ID);
		StringBuffer html=new StringBuffer();
		StringTokenizer token=new StringTokenizer(targetVocabsForSearchTerm,"@");
		while(token.hasMoreTokens())
		{
			String vocabFullName[]=token.nextToken().split(":");
			String vocabName=vocabFullName[0];
			String vocabVersion=vocabFullName[1]; 
			html.append(bizLogic.getRootVocabularyHTMLForSearch("srh_"+vocabName, vocabVersion));
			List<IConcept> conceptList=bizLogic.searchConcept(searchTerm, vocabName, vocabVersion);
			if(conceptList!=null)
			{
				for(int i=0;i<conceptList.size();i++)
				{
					IConcept concept=conceptList.get(i);
					String checkboxId=vocabName+"@"+vocabVersion+":"+concept.getCode();//TODO need to change into MED concept code when API will be completed
					html.append(bizLogic.getMappedVocabularyPVChildAsHTML("srh_"+vocabName,vocabVersion, concept, checkboxId));
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
	private void generatePermValueHTMLForMED(AttributeInterface attribute, EntityInterface entity, String componentId,HttpServletRequest request) throws VocabularyException {
		
		SearchPermissibleValuesFromVocabBizlogic bizLogic = (SearchPermissibleValuesFromVocabBizlogic)BizLogicFactory.getInstance().getBizLogic(Constants.SEARCH_PV_FROM_VOCAB_BILOGIC_ID);
		List<IConcept> premValueList=bizLogic.getPermissibleValueList(attribute, entity);
		String vocabName=VocabUtil.getVocabProperties().getProperty("source.vocab.name").toUpperCase();
		String vocabVer=VocabUtil.getVocabProperties().getProperty("source.vocab.version").toUpperCase();
		StringBuffer html=new StringBuffer();
		html.append(bizLogic.getRootVocabularyNodeHTML(vocabName,vocabVer));
		for(int i=0;i<premValueList.size();i++)
		{
			IConcept concept=(IConcept)premValueList.get(i);
			String id=vocabName+"@"+vocabVer+":"+concept.getCode();
			html.append(bizLogic.getMappedVocabularyPVChildAsHTML(vocabName,vocabVer, concept, id));
		}
		html.append(bizLogic.getEndHTML());
		request.getSession().setAttribute(Constants.MED_PV_HTML, html.toString());
		request.getSession().setAttribute(Constants.VOCABULIRES, bizLogic.getVocabulries());
		if(componentId!=null)
		{
			request.getSession().setAttribute(Constants.COMPONENT_ID,componentId );
		}
	}
	
	
	private String getMappedVocabDataAsHTML(String targetVocab,AttributeInterface attribute,EntityInterface entity) throws VocabularyException 
	{
		
		SearchPermissibleValuesFromVocabBizlogic bizLogic = (SearchPermissibleValuesFromVocabBizlogic)BizLogicFactory.getInstance().getBizLogic(Constants.SEARCH_PV_FROM_VOCAB_BILOGIC_ID);
		String sourceVocabulary=VocabUtil.getVocabProperties().getProperty("source.vocab.name");
		String sourceVocabVer=VocabUtil.getVocabProperties().getProperty("source.vocab.version");
		String targetVacbArray[] = targetVocab.split(":");
		String targetVocabName=targetVacbArray[0];
		String targetVocabVer=targetVacbArray[1];
		StringBuffer html=new StringBuffer();
		if(!sourceVocabulary.equalsIgnoreCase(targetVocabName) || !sourceVocabVer.equalsIgnoreCase(targetVocabVer))
			{
				
				String vocabName=targetVocabName.toUpperCase();
				html.append(bizLogic.getRootVocabularyNodeHTML(vocabName,targetVocabVer));
				Map<String,List<IConcept>> vocabMappings=bizLogic.getMappedConcepts(attribute,targetVocabName,targetVocabVer, entity);
				getMappingDataAsHTML(html, vocabName,targetVocabVer, vocabMappings);
				
			}
			
		
		return html.toString();
	
	}
	private void getMappingDataAsHTML(StringBuffer html, String vocabName,String vocabversoin,
			Map<String, List<IConcept>> vocabMappings) {
		SearchPermissibleValuesFromVocabBizlogic bizLogic = (SearchPermissibleValuesFromVocabBizlogic)BizLogicFactory.getInstance().getBizLogic(Constants.SEARCH_PV_FROM_VOCAB_BILOGIC_ID);
		if(vocabMappings!=null)
		{
			Set<String> keySet = vocabMappings.keySet();
			Iterator<String> iterator = keySet.iterator();
			while (iterator.hasNext())
			{
				String conceptCode =iterator.next();
				List<IConcept> mappingList=(ArrayList)vocabMappings.get(conceptCode);
				ListIterator<IConcept> mappingListItr = mappingList.listIterator();
				while(mappingListItr.hasNext())
				{
					IConcept concept = (IConcept)mappingListItr.next();
					String checkboxId=vocabName+"@"+vocabversoin+":"+conceptCode;//we need to use the MED Concept code with mapped values
					html.append(bizLogic.getMappedVocabularyPVChildAsHTML(vocabName,vocabversoin, concept,checkboxId));
					
				}
			}
		}
		else
		{
			html.append(bizLogic.getNoMappingFoundHTML());
		}
		html.append(bizLogic.getEndHTML());
	}
	


	
}
