
package edu.wustl.query.action;

import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.vocab.IConcept;
import edu.wustl.common.vocab.IVocabulary;
import edu.wustl.common.vocab.VocabularyException;
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
		
		String searchTerm = request.getParameter("searchTerm");
		String targetVocabsForSearchTerm = request.getParameter("targetVocabsForSearchTerm");
		//get the id of the component on which user click to search for PVs
		String componentId = request.getParameter("componentId");
		if (componentId == null)
		{
			//need to save componetid into the session for next Ajax requests
			componentId = (String) request.getSession().getAttribute(Constants.COMPONENT_ID);
		}
		
		if (searchTerm != null && targetVocabsForSearchTerm != null)
		{
			// AJAX Request handler for Getting search term Result data for source to target vocabularies
			SearchPermissibleValueBizlogic bizLogic = (SearchPermissibleValueBizlogic) BizLogicFactory
					.getInstance().getBizLogic(Constants.SEARCH_PV_FROM_VOCAB_BILOGIC_ID);
			try
			{
				String html = getSearchedVocabDataAsHTML(searchTerm, targetVocabsForSearchTerm);
				response.getWriter().write(html);
			}
			catch (VocabularyException e)
			{
				response.getWriter().write(bizLogic.getErrorMessageAsHTML());
			}
			
		}
		return null;
	}
	/**
	 * This method will search for the entered text by the user across all the vocabularies
	 * and return the HTML for the searched result
	 * @param searchTerm
	 * @param targetVocabsForSearchTerm
	 * @return
	 * @throws VocabularyException
	 */
	private String getSearchedVocabDataAsHTML(String searchTerm, String targetVocabsForSearchTerm)
			throws VocabularyException
	{
		SearchPermissibleValueBizlogic bizLogic = (SearchPermissibleValueBizlogic) BizLogicFactory
				.getInstance().getBizLogic(Constants.SEARCH_PV_FROM_VOCAB_BILOGIC_ID);
		StringBuffer html = new StringBuffer();
		StringTokenizer token = new StringTokenizer(targetVocabsForSearchTerm, "@");
		while (token.hasMoreTokens())
		{
			String[] vocabFullName = token.nextToken().split(":");
			String vocabName = vocabFullName[0];
			String vocabVersion = vocabFullName[1];
			html.append(bizLogic.getRootVocabularyHTMLForSearch("srh_" + vocabName, vocabVersion,
					getDisplayNameForVocab(vocabName, vocabVersion)));
			List<IConcept> conceptList = bizLogic
					.searchConcept(searchTerm, vocabName, vocabVersion);
			if (conceptList != null)
			{
				for(IConcept concept:conceptList)
				{
					 String checkboxId = vocabName + "@" + vocabVersion + ":" + concept.getCode();//TODO need to change into MED concept code when API will be completed
					html.append(bizLogic.getMappedVocabularyPVChildAsHTML("srh_" + vocabName,
							vocabVersion, concept, checkboxId));
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
	 * This method returns the display name for given vocabulary Name and vocabulary version
	 * @param vocabName
	 * @param vocabVer
	 * @return
	 * @throws VocabularyException
	 */
	private String getDisplayNameForVocab(String vocabName, String vocabVer)
			throws VocabularyException
	{
		SearchPermissibleValueBizlogic bizLogic = (SearchPermissibleValueBizlogic) BizLogicFactory
				.getInstance().getBizLogic(Constants.SEARCH_PV_FROM_VOCAB_BILOGIC_ID);
		List<IVocabulary> vocabularies = bizLogic.getVocabulries();
		String vocabDisName = "";
		for (IVocabulary vocabulary : vocabularies)
		{
			if (vocabulary.getName().equals(vocabName) && vocabulary.getVersion().equals(vocabVer))
			{
				vocabDisName = vocabulary.getDisplayName();
				break;
			}
		}
		if (vocabDisName.equals(""))
		{
			throw new VocabularyException("Could not find the vocabulary.");
		}
		return vocabDisName;
	}
	
}
