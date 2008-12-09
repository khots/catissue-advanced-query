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
import edu.common.dynamicextensions.domaininterface.PermissibleValueInterface;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.common.query.pvmanager.impl.ConceptValue;
import edu.wustl.common.vocab.IConcept;
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
		//get the id of the component on which user click to search for PVs
		String componentId=request.getParameter("componentId");
		if(componentId==null)
		{
			//need to save componet id into the session for Ajax requests
			componentId=(String)request.getSession().getAttribute(Constants.COMPONENT_ID);
		}
		 String entityName =(String) request.getSession().getAttribute(Constants.ENTITY_NAME);
		 Entity entity = (Entity) EntityCache.getCache().getEntityById(Long.valueOf((entityName)));
		 Map<String,AttributeInterface> enumAttributeMap=(HashMap<String,AttributeInterface>) request.getSession().getAttribute(Constants.ENUMRATED_ATTRIBUTE);
		 AttributeInterface attribute=(AttributeInterface)enumAttributeMap.get(Constants.ATTRIBUTE_INTERFACE+componentId);
		
		
		if(targetVocab!=null)
		{
			// Ajax Request handler for Get Mapping data for source to target vocabulries
			String html=getMappedVacbulariesDataAsHTML(targetVocab,attribute,entity);
			response.getWriter().write(html);
			return null;
		}
		
		generatePermValueHTMLForMED(attribute,entity,componentId,request);
		return mapping.findForward(edu.wustl.query.util.global.Constants.SUCCESS);
	}
	private void generatePermValueHTMLForMED(AttributeInterface attribute, EntityInterface entity, String componentId,HttpServletRequest request) {
		
		SearchPermissibleValuesFromVocabBizlogic bizLogic = (SearchPermissibleValuesFromVocabBizlogic)BizLogicFactory.getInstance().getBizLogic(Constants.SEARCH_PV_FROM_VOCAB_BILOGIC_ID);
		List<IConcept> premValueList=bizLogic.getPermissibleValueList(attribute, entity);
		String vocabName=VocabUtil.getVocabProperties().getProperty("source.vocab.name").toUpperCase();
		StringBuffer html=new StringBuffer();
		html.append(getRootVocabularyNodeHTML(vocabName));
		for(int i=0;i<premValueList.size();i++)
		{
			IConcept concept=(IConcept)premValueList.get(i);
			String id=vocabName+":"+concept.getCode();
			html.append(getMappedVocabularyPVChildAsHTML(vocabName, concept, id));
		}
		html.append("</table></div></td></tr></table>");
		request.getSession().setAttribute(Constants.MED_PV_HTML, html.toString());
		request.getSession().setAttribute(Constants.VOCABULIRES, bizLogic.getVocabulries());
		if(componentId!=null)
		{
			request.getSession().setAttribute(Constants.COMPONENT_ID,componentId );
		}
	}
	
	private String getMappedVacbulariesDataAsHTML(String targetVocab,AttributeInterface attribute,EntityInterface entity) 
	{
		
		SearchPermissibleValuesFromVocabBizlogic bizLogic = (SearchPermissibleValuesFromVocabBizlogic)BizLogicFactory.getInstance().getBizLogic(Constants.SEARCH_PV_FROM_VOCAB_BILOGIC_ID);
		String sourceVocabulary=Variables.properties.getProperty("sourceVocabularyName");
		String sourceVocabVer=Variables.properties.getProperty("sourceVocabularyVersion");
		String targetVacbArray[] = targetVocab.split(" ");
		String targetVocabName=targetVacbArray[0];
		String targetVocabVer=targetVacbArray[1];
		StringBuffer html=new StringBuffer();
		if(!sourceVocabulary.equalsIgnoreCase(targetVocabName) && !targetVocabName.equals(""))
			{
				
				String vocabName=targetVocabName.toUpperCase();
				html.append(getRootVocabularyNodeHTML(vocabName));
				Map<String,List<IConcept>> vocabMappings=bizLogic.getMappedConcepts(attribute,targetVocabName,targetVocabVer, entity);
				getMappingDataAsHTML(html, vocabName, vocabMappings);
				
			}
			
		
		return html.toString();
	
	}
	private void getMappingDataAsHTML(StringBuffer html, String vocabName,
			Map<String, List<IConcept>> vocabMappings) {
		
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
					IConcept permValue = (IConcept)mappingListItr.next();
					String checkboxId=vocabName+":"+conceptCode;//we need to use the MED Concept code with mapped values
					html.append(getMappedVocabularyPVChildAsHTML(vocabName, permValue,checkboxId));
					
				}
			}
		}
		else
		{
			html.append(getNoMappingFoundHTML());
		}
		html.append("</table></div></td></tr></table>");
	}
	private String getNoMappingFoundHTML() {
		return "<tr><td>&nbsp;</td><td class='black_ar_tt'>"+Constants.NO_RESULT+"<td></tr>";
	}
	private String getMappedVocabularyPVChildAsHTML(String vocabName,IConcept concept, String checkboxId) {
		return "<tr><td>&nbsp;</td><td class='black_ar_tt'> \n" +
				"<input type='checkbox' name='"+vocabName+"' id='"+checkboxId+"' value='"+concept.getCode()+":"+concept.getDescription()+"' onclick=\"getCheckedBoxId('"+checkboxId+"');\">"+concept.getCode()+":"+concept.getDescription()+"\n" +
				"</input><td></tr>";
	}
	private String getRootVocabularyNodeHTML(String vocabName) {
		
		return "<table><tr><td colspan='2' class='grid_header_text'><a id=\"image_"+vocabName+"\" \n" +
				"onClick=\"showHide('div_"+vocabName+"','image_"+vocabName+"');\" > \n" +
						"<img src=\"images/advancequery/nolines_minus.gif\"/> </a><input type='checkbox' name='"+vocabName+"' id='"+vocabName+"' value='"+vocabName+"' onclick=\"setStatusOfAllCheckBox('"+vocabName+"');\">"+vocabName+"\n" +
								"</input></td></tr>" +
								"<tr><td><div id='div_"+vocabName+"'  ><table>";
	}


	
}
