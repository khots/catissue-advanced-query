
package edu.wustl.query.bizlogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.common.dynamicextensions.domain.StringValue;
import edu.common.dynamicextensions.domaininterface.PermissibleValueInterface;
import edu.wustl.common.bizlogic.DefaultBizLogic;
import edu.wustl.common.query.factory.PermissibleValueManagerFactory;
import edu.wustl.common.query.pvmanager.impl.LexBIGPermissibleValueManager;
import edu.wustl.common.query.pvmanager.impl.PVManagerException;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;
import edu.wustl.common.util.Utility;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.common.vocab.IConcept;
import edu.wustl.common.vocab.IDefinition;
import edu.wustl.common.vocab.IPresentation;
import edu.wustl.common.vocab.IVocabulary;
import edu.wustl.common.vocab.IVocabularyManager;
import edu.wustl.common.vocab.VocabularyException;
import edu.wustl.common.vocab.impl.Definition;
import edu.wustl.common.vocab.impl.VocabularyManager;
import edu.wustl.common.vocab.utility.ConceptNameComparator;
import edu.wustl.common.vocab.utility.VIError;
import edu.wustl.common.vocab.utility.VocabUtil;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.VIProperties;
import edu.wustl.vi.enums.VISearchAlgorithm;
import edu.wustl.vi.search.SearchConcept;

/**
 * @author amit_doshi
 * Class to hold the bizlogic of Vocabulary Interface
 */
public class SearchPermissibleValueBizlogic extends DefaultBizLogic
{

	/**
	 * logger for this class.
	 */
	private static org.apache.log4j.Logger logger = LoggerConfig
			.getConfiguredLogger(SearchPermissibleValueBizlogic.class);

	/**
	 * PVManager instance.
	 */
	private final transient LexBIGPermissibleValueManager pvManager = (LexBIGPermissibleValueManager) PermissibleValueManagerFactory
			.getPermissibleValueManager();
	/**
	 * Vocab Manager instance.
	 */
	private final IVocabularyManager vocabularyManager = VocabularyManager.getInstance();

	/**
	 * This method returns List of vocabularies.
	 * @return List of IVocabulary
	 * @throws VocabularyException - Exception
	 */
	public List<IVocabulary> getVocabularies() throws VocabularyException
	{
		return vocabularyManager.getConfiguredVocabularies();
	}

	/**
	 * This method returns the list of permissible values.
	 * @param attribute - QueryableAttributeInterface
	 * @param showMessage - List of Integer
	 * @return List of IConcept
	 * @throws PVManagerException - Exception
	 * @throws VocabularyException - Exception
	 */
	public List<IConcept> getConfiguredPermissibleValueList(QueryableAttributeInterface attribute,
			List<Integer> showMessage) throws PVManagerException, VocabularyException
	{

		List<IConcept> permissibleConcepts = new ArrayList<IConcept>();
		try
		{
			//long startTime = System.currentTimeMillis();
			List<PermissibleValueInterface> permissibleValues = pvManager
					.getPermissibleValueList(attribute);
			IVocabulary sourceVocabulary = getVocabulary(VIProperties.sourceVocabUrn);
			//permissibleConcepts =
			//resolvePermissibleCodesToConcept(sourceVocabulary,permissibleValues);
			if (permissibleValues != null)
			{
				for (PermissibleValueInterface perValue : permissibleValues)
				{
					List<IConcept> concepts = sourceVocabulary.getConceptForCode(perValue
							.getValueAsObject().toString());
					if (permissibleConcepts.size() >= VIProperties.maxPVsToShow)
					{
						showMessage.add(Constants.ONE);
						showMessage.add(permissibleConcepts.size());
						//number of result to show
						showMessage.add(permissibleValues.size());
						//total number of result
						break;
					}
					getPermissibleConcepts(concepts, permissibleConcepts);
				}
			}
			/*System.out.print("PV fetched in.." + (System.currentTimeMillis() - startTime) / 1000
					+ " Secs");*/

		}
		catch (PVManagerException e)
		{
			throw new PVManagerException(
					"Server encountered a problem in fetching the permissible values for "
							+ attribute.getQueryEntity().getName(), e);
		}
		return permissibleConcepts;
	}

	/**
	 * This method used to process the Permissible values.
	 * @param concepts - IConcepts
	 * @param permissibleConcepts - List of IConcept
	 */
	private void getPermissibleConcepts(List<IConcept> concepts, List<IConcept> permissibleConcepts)
	{
		if (concepts != null && !concepts.isEmpty())
		{
			permissibleConcepts.addAll(concepts);
		}
	}

	/**
	 * This method used to get the Permissible values form DB.
	 * @param attribute - QueryableAttributeInterface
	 * @return List of PermissibleValueInterface
	 * @throws PVManagerException - Exception
	 */
	public List<PermissibleValueInterface> getPermissibleValueListFromDB(
			QueryableAttributeInterface attribute) throws PVManagerException
	{
		return pvManager.getPermissibleValueList(attribute);
	}

	/**
	 * This method returns the Mapped concept code of target vocabulary with source vocabulary.
	 * @param attribute - QueryableAttributeInterface
	 * @param targetVocabulary - IVocabulary
	 * @param showMessage - List of Integer
	 * @return Map of [String, List of IConcept]
	 * @throws VocabularyException - Exception
	 * @throws PVManagerException - Exception
	 */
	public Map<String, List<IConcept>> getMappedConcepts(QueryableAttributeInterface attribute,
			IVocabulary targetVocabulary, List<Integer> showMessage) throws VocabularyException,
			PVManagerException
	{
		IVocabulary sourceVocabulary = getVocabulary(VIProperties.sourceVocabUrn);
		Map<String, List<IConcept>> mappedConcepts = new HashMap<String, List<IConcept>>();
		try
		{
			//long startTime = System.currentTimeMillis();
			List<PermissibleValueInterface> permissibleValues = pvManager
					.getPermissibleValueList(attribute);
			/*System.out.print("PV fetched for getMappedConcepts in.."
					+ (System.currentTimeMillis() - startTime) / 1000 + " Secs");*/
			if (permissibleValues != null && !permissibleValues.isEmpty())
			{
				mappedConcepts = processPVs(targetVocabulary, showMessage, sourceVocabulary,
						permissibleValues);
			}
		}
		catch (VocabularyException e)
		{
			logger.error(e.getMessage(), e);
		}

		return mappedConcepts;
	}

	/**
	 * This method is used to process the PVs.
	 * @param targetVocabulary - IVocabulary
	 * @param showMessage - List of Integer
	 * @param sourceVocabulary - IVocabulary
	 * @param permissibleValues - List of PermissibleValueInterface
	 * @return Map[String, List of IConcept]
	 * @throws VocabularyException - Exception
	 */
	private Map<String, List<IConcept>> processPVs(IVocabulary targetVocabulary,
			List<Integer> showMessage, IVocabulary sourceVocabulary,
			List<PermissibleValueInterface> permissibleValues) throws VocabularyException
	{
		int maxPVsToShow = 0;
		Map<String, List<IConcept>> mappedConcepts = new HashMap<String, List<IConcept>>();
		for (PermissibleValueInterface perValueInterface : permissibleValues)
		{
			List<IConcept> conList = sourceVocabulary.getMappedConcepts(perValueInterface
					.getValueAsObject().toString(), VIProperties.translationAssociation,
					targetVocabulary);
			if (conList != null && !conList.isEmpty())
			{
				mappedConcepts.put(perValueInterface.getValueAsObject().toString(), conList);
				maxPVsToShow = maxPVsToShow + conList.size();
				if (maxPVsToShow >= VIProperties.maxPVsToShow)
				{
					showMessage.add(Constants.ONE);
					showMessage.add(maxPVsToShow);//number of result to show
					showMessage.add(permissibleValues.size());//total number of result
					break;
				}
			}
		}
		return mappedConcepts;
	}

	/**
	 * This method will search the given term in across all the vocabularies
	 * @param searchConcept - SearchConcept
	 * @return List of IConcept
	 * @throws VocabularyException - Exception
	 */
	public List<IConcept> searchConcept(SearchConcept searchConcept) throws VocabularyException
	{
		int maxToReturn = VIProperties.maxToReturnFromSearch;
		String changedTerm = searchConcept.getSearchTerm();
		StringBuilder searchTerm = new StringBuilder(searchConcept.getSearchTerm());
		IVocabulary vocabulary = vocabularyManager.getVocabulary(searchConcept.getVocabURN());
		VISearchAlgorithm searchAloAlgorithm = VISearchAlgorithm.valueOf(searchConcept
				.getSearchCriteria());

		if (searchAloAlgorithm.equals(VISearchAlgorithm.EXACT_PHRASE))
		{
			searchTerm = new StringBuilder("");
			if (changedTerm.indexOf("\"") != Constants.MINUS_ONE)
			{
				changedTerm = changedTerm.replace("\"", "");
			}
			searchTerm.append("\"").append(changedTerm).append("\"");
		}

		return vocabulary.searchConcept(searchTerm.toString(), searchAloAlgorithm.getAlgorithm(),
				maxToReturn);

	}

	/**
	 * This method returns the message no result found.
	 * @return String
	 */
	public String getNoMappingFoundHTML()
	{
		return "<tr><td>&nbsp;</td><td class='black_ar_tt'>" + Constants.NO_RESULT
				+ Constants.TD_TR;
	}

	/**
	 * This method returns the message no result found.
	 * @param noResult - int
	 * @param totalResult - int
	 * @return String
	 * @throws VocabularyException - Exception
	 */
	public String getInfoMessage(int noResult, int totalResult) throws VocabularyException
	{
		String message = VocabUtil.getVocabProperties().getProperty(
				"too.many.results.default.message");
		message = message.replace("##", (Integer.valueOf(noResult)).toString());
		message = message.replace("@@", (Integer.valueOf(totalResult)).toString());
		return Constants.MSG_DEL + message;
	}

	/**
	 * This method returns the HTML for child nodes for all the vocabularies which,
	 *  contains the permissible ,concept code and check box.
	 * @param vocabURN - String
	 * @param concept - IConcept
	 * @param checkboxId - String
	 * @param volumeInDb - to show the volume count along with the Print Name (Description)
	 * @return String
	 */
	public String getHTMLForConcept(String vocabURN, IConcept concept, String checkboxId,
			String volumeInDb)
	{
		//bug Fixed:-14301
		String description = setVolumeCountInDescription(concept, volumeInDb);
		String value = Constants.OPEN_CODE_BARCKET + concept.getCode()
				+ Constants.CLOSE_CODE_BARCKET + Constants.VALUE_DEL + description;

		return "<tr><td style=\"padding-left:30px\">" + "&nbsp;</td><td "
				+ "class=\"black_ar_tt\" >" + "\n" + "<input type=\"checkbox\" name=\"" + vocabURN
				+ "\" id=\"" + checkboxId + "\" value=\"" + value
				+ "\" onclick=\"getCheckedBoxId('" + checkboxId + "');\">"
				+ "</td><td nowrap>&nbsp;<span title='Concept Details'id='text_"+checkboxId
				+ "' class='tooltip' rel='ShowVITooltip.do?" + Constants.CON_CODE + "=" + checkboxId
				+ "'><span class=\"black_ar_tt\" >" + description + "</span></span>"
				+ Constants.TD_TR;
	}

	/**
	 * @param concept source vocab concept.
	 * @param volumeInDb contains the volume in DB.
	 * @return the concept code with updated description with volume count.
	 */
	private String setVolumeCountInDescription(IConcept concept, String volumeInDb)
	{
		StringBuilder description = new StringBuilder(concept.getDescription());
		if (volumeInDb != null && !volumeInDb.trim().equals(""))
		{
			description.append(" ");
			description.append(Constants.OPEN_CODE_BARCKET);
			description.append(Constants.VC_SEP);
			description.append(volumeInDb);
			description.append(Constants.CLOSE_CODE_BARCKET);
		}
		return description.toString();
	}

	/**
	 * @param vocabURN - String
	 * @param concept - IConcept
	 * @param checkboxId - String
	 * @param volumeInDb - String
	 * @return String
	 */
	public String getHTMLForTargetConcept(String vocabURN, IConcept concept, String checkboxId,
			String volumeInDb)
	{
		//bug Fixed:-14301
		String description = setVolumeCountInDescription(concept, volumeInDb);
		String value = Constants.OPEN_CODE_BARCKET + concept.getCode()
				+ Constants.CLOSE_CODE_BARCKET + Constants.VALUE_DEL + description;
		return "<tr><td style=\"padding-left:30px\">" + "&nbsp;</td><td class=\"black_ar_tt\" > \n"
				+ "<input type=\"checkbox\" name=\"" + vocabURN + "\" id=\"" + checkboxId
				+ "\" value=\"" + value + "\" onclick=\"getCheckedBoxId('" + checkboxId + "');\">"
				+ "</td><td nowrap>&nbsp;<span title='Concept Details' id='text_"+checkboxId
				+ "' class='tooltip' rel='ShowVITooltip.do?" + Constants.CON_CODE + "=" + checkboxId
				+ "'><span class=\"black_ar_tt\">" + description + "</span></span>"
				+ Constants.TD_TR;
	}

	/**
	 * This method is used to create the Tooltip for Concepts, Which will be used as JQuery Tooltip.
	 * @param concept - IConcept
	 * @param volumeInDb - volumeInDb
	 * @return String
	 */
	public String getToolTip(IConcept concept, String volumeInDb)
	{
		StringBuffer toolTip = new StringBuffer(Constants.STRING_BUF_SIZE);
		String volumeInDBValue = volumeInDb;
		toolTip.append(Constants.TABLE);
		toolTip.append("<tr height='20'><td width='40%' class='content_txt_bold'>");
		toolTip.append(Constants.CONCEPT_CODE);
		toolTip.append("</td><td>").append(concept.getCode()).append(Constants.TD_TR);
		toolTip.append(Constants.TR_STYLE);
		toolTip.append("<td class='content_txt_bold'>");
		toolTip.append(Constants.CONCEPT_DES);
		toolTip.append("</td><td>").append(concept.getDescription()).append(Constants.TD_TR);
		if (volumeInDb == null)
		{
			volumeInDBValue = Constants.NOT_AVAILABLE;
		}
		toolTip.append("<tr height='20'><td width='35%' class='content_txt_bold'>");
		toolTip.append(Constants.CONCEPT_VOLUME);
		toolTip.append("</td><td>").append(volumeInDBValue).append(Constants.TD_TR);
		List<IPresentation> preList = concept.getPresentation();
		toolTip.append(getToolTipList(preList));
		toolTip.append(Constants.TR_STYLE);
		toolTip.append("<td class='content_txt_bold'>");
		toolTip.append(Constants.CONCEPT_DEFI).append(Constants.TD_CLOSE);
		StringBuffer definition = new StringBuffer();
		List<IDefinition> defsList = concept.getDefinition();
		definition.append(Constants.NOT_AVAILABLE);
		if (defsList != null && !defsList.isEmpty())
		{
			definition = processDefinition(defsList);
		}
		toolTip.append("<td>").append(definition).append(Constants.TD_TR);
		toolTip.append("</table>");
		return toolTip.toString();
	}

	/**
	 * This method will process the definition and returns the HTML String.
	 * @param defsList List of definition
	 * @return -StringBuffer
	 */
	private StringBuffer processDefinition(List<IDefinition> defsList)
	{
		StringBuffer definition;
		definition = new StringBuffer();
		for (IDefinition defs : defsList)
		{
			Definition def = (Definition) defs;
			definition.append(def.getDescription());
			definition.append(Constants.HTML_BREAK);
		}
		return definition;
	}

	/**
	 * This method is used to get the tooltip List.
	 * @param preList - List of IPresentation
	 * @return String
	 */
	private String getToolTipList(List<IPresentation> preList)
	{
		StringBuffer toolTip = new StringBuffer(Constants.STRING_BUF_SIZE);
		toolTip.append("<tr height='20'><td class='content_txt_bold'>Med Concept Name: </td><td>");
		toolTip.append(Constants.NOT_AVAILABLE);
		toolTip.append(Constants.TD_TR);
		if (preList != null && !preList.isEmpty())
		{
			for (IPresentation presentation : preList)
			{
				if (presentation.getName().equals(Constants.MED_CONECPT_NAME))
				{
					toolTip = new StringBuffer(Constants.STRING_BUF_SIZE);
					toolTip
							.append("<tr height='20'><td class='content_txt_bold'>Med Concept Name: </td><td>");
					toolTip.append(presentation.getDescription());
					toolTip.append(Constants.TD_TR);
					break;
				}
			}
		}

		return toolTip.toString();
	}

	/**
	 * This method returns the HTML for child nodes for all the vocabularies which
	 *  contains the permissible ,concept code and check box.
	 * @param concept - IConcept
	 * @param checkboxId - String
	 * @param conceptsDetail MAP of conceptDetails that contains all the values of concepts
	 * for which we need create HTML , [key=String,value=Object]
	 * @return String
	 * @throws VocabularyException - Exception
	 */
	public String getHTMLForSearchedConcept(IConcept concept, String checkboxId,
			Map<String, Object> conceptsDetail) throws VocabularyException
	{

		String vocabURN = "srh_" + conceptsDetail.get(Constants.VOCAB_URN);
		String textStatus = (String) conceptsDetail.get(Constants.STATUS);
		Map<String, String> conceptCodeVsVolumeInDb = (Map<String, String>) conceptsDetail
				.get(Constants.CON_CODE_VS_VOL_INDB);
		//bug Fixed:-14301
		String description = setVolumeCountInDescription(concept, conceptCodeVsVolumeInDb
				.get(concept.getCode()));
		String value = Constants.OPEN_CODE_BARCKET + concept.getCode()
				+ Constants.CLOSE_CODE_BARCKET + Constants.VALUE_DEL + description;
		String chkBoxStatus = "";
		String cssClass = textStatus;

		if (textStatus.indexOf("Disabled") > Constants.MINUS_ONE)
		{
			chkBoxStatus = "disabled";
		}

		return "<tr><td style=\"padding-left:30px\">" + "&nbsp;</td><td class=\"black_ar_tt\"> \n"
				+ "<input type=\"checkbox\" " + chkBoxStatus + " name=\"" + vocabURN + "\" id=\""
				+ checkboxId + "\" value=\"" + value + "\" onclick=\"getCheckedBoxId('"
				+ checkboxId + "');\">" + "</td><td nowrap>&nbsp;<span title='Concept Details' id='text_"+checkboxId
				+ "' class='searchTooltip' rel='ShowVITooltip.do?" + Constants.CON_CODE + "="
				+ checkboxId + "'><span class='" + cssClass + "'>" + description + "</span></span>"
				+ Constants.TD_TR;
	}

	/**
	 * This method will return HTML for the root node of each vocabularies that require
	 * while creating the tree like structure to show the result.
	 * @param vocabURN - String
	 * @param vocabDisName - String
	 * @return String
	 * @throws VocabularyException - Exception
	 */
	public String getRootVocabularyNodeHTML(String vocabURN, String vocabDisName)
			throws VocabularyException
	{
		String style = "display:";
		String imgpath = "src=\"images/advancequery/nolines_minus.gif\"/";
		String tableHTML = "<table cellpadding ='0' cellspacing ='1'>";
		return tableHTML + "<tr><td>" + tableHTML + "<tr><td class='grid_header_text'>"
				+ "<a id=\"image_" + vocabURN + "\"\n" + "onClick=\"showHide('inner_div_"
				+ vocabURN + "'," + "'image_" + vocabURN + "');\">\n" + "<img " + imgpath
				+ " align='absmiddle'/></a>" + "</td><td><input type='checkbox' name='" + vocabURN
				+ "' id='root_" + vocabURN + "' " + "value='" + vocabURN
				+ "' onclick=\"setStatusOfAllCheckBox(this.id);\"></td>"
				+ "<td align='middle'  class='grid_header_text'>&nbsp;&nbsp;" + vocabDisName + "\n"
				+ "</td></tr></table>" + "</td></tr><tr><td><div id='inner_div_" + vocabURN
				+ "'style='" + style + "'>" + tableHTML;
	}

	/**
	 * This method will return HTML for the root node of each vocabularies that require
	 * while creating the tree like structure to show the result.
	 * @param vocabURN - String
	 * @param vocabDisName - String
	 * @return String
	 */
	public String getRootVocabularyHTMLForSearch(String vocabURN, String vocabDisName)
	{
		String style = "display:";
		String imgpath = "src=\"images/advancequery/nolines_minus.gif\"/";
		String tableHTML = "<table cellpadding ='0' cellspacing ='1'>";
		return tableHTML + "<tr><td>" + tableHTML
				+ "<tr><td class='grid_header_text'><a id=\"image_" + vocabURN + "\"\n"
				+ "onClick=\"showHide('inner_div_" + vocabURN + "'," + "'image_" + vocabURN
				+ "');\">\n" + "<img " + imgpath + "align='absmiddle'></a>"
				+ "</td><td><input type='checkbox' name='" + vocabURN + "' id='root_" + vocabURN
				+ "' " + "value='" + vocabURN + "'"
				+ " onclick=\"setStatusOfAllCheckBox(this.id);\"></td>"
				+ "<td align='middle'  class='grid_header_text'>&nbsp;&nbsp;" + vocabDisName + "\n"
				+ "</td></tr></table>" + "</td></tr><tr><td><div id='inner_div_" + vocabURN
				+ "'style='" + style + "'>" + tableHTML;
	}

	/**
	 * This method returns end HTML for Tree like structure.
	 * @return String
	 */
	public String getEndHTML()
	{
		return "</table></div></td></tr></table>";
	}

	/**
	 * This method returns  HTML Error message.
	 * @param msg Error Message
	 * @return String
	 */
	public String getErrorMessageAsHTML(String msg)
	{
		return "<table>" + "<tr><td class='black_ar_tt' style='color:red' valign='top'>" + msg
				+ "<td></tr></table>";
	}

	/**
	 * This method check for the given concept code is source vocabulary coded or not.
	 * @param targetConcept - IConcept
	 * @param associationName - String
	 * @param baseVocab - IVocabulary
	 * @return List<IConcept>
	 * @throws VocabularyException - Exception
	 */
	public List<IConcept> isSourceVocabCodedTerm(IConcept targetConcept, String associationName,
			IVocabulary baseVocab) throws VocabularyException
	{
		List<IConcept> concepts = baseVocab.getSourceOf(targetConcept.getCode(), associationName,
				targetConcept.getVocabulary());
		return concepts;

	}

	/**
	 * This method checks that the the given concept exist in the PV list or not for particular VI entity.
	 * @param concept - IConcept
	 * @param pvList - List of PermissibleValueInterface
	 * @return IConcept
	 */
	public IConcept isConceptExistInPVList(IConcept concept, List<PermissibleValueInterface> pvList)
	{
		IConcept medConcept = null;
		for (PermissibleValueInterface pValue : pvList)
		{
			StringValue pvs = (StringValue) pValue;
			if (pvs.getValue().equals(concept.getCode()))
			{
				medConcept = concept;
			}
		}
		return medConcept;
	}

	/**
	 * This method checks that the the given concept List exist in the PV list or not for particular VI entity.
	 * @param concepts - IConcept
	 * @param pvList - List of PermissibleValueInterface
	 * @return IConcept
	 */
	public IConcept isConceptsExistInPVList(List<IConcept> concepts,
			List<PermissibleValueInterface> pvList)
	{
		IConcept sourceConcept = null;
		for (IConcept conc : concepts)
		{

			for (PermissibleValueInterface pValue : pvList)
			{
				StringValue pvs = (StringValue) pValue;
				if (pvs.getValue().equals(conc.getCode()))
				{
					sourceConcept = conc;
					break;
				}
			}
			if (sourceConcept != null)
			{
				break;
			}
		}
		return sourceConcept;
	}

	/**
	 * This method returns the display name for given vocabulary Name and vocabulary version.
	 * @param vocabURN - String
	 * @return String
	 * @throws VocabularyException - Exception
	 */
	public String getDisplayNameForVocab(String vocabURN) throws VocabularyException
	{
		SearchPermissibleValueBizlogic bizLogic = (SearchPermissibleValueBizlogic) BizLogicFactory
				.getInstance().getBizLogic(Constants.PV_VOCAB_BILOGIC_ID);
		List<IVocabulary> vocabularies = bizLogic.getVocabularies();
		String vocabDisName = "";
		for (IVocabulary vocabulary : vocabularies)
		{
			if (vocabulary.getVocabURN().equals(vocabURN))
			{
				vocabDisName = vocabulary.getDisplayName();
				break;
			}
		}
		if ("".equals(vocabDisName))
		{
			throw new VocabularyException("Could not find the vocabulary.", VIError.SYSTEM_ERR);
		}
		return vocabDisName;
	}

	/**
	 * This method returns the instance of IVocabulary for the given vocabulary URN.
	 * @param urn - String
	 * @return String
	 * @throws VocabularyException - Excpetion
	 */
	public IVocabulary getVocabulary(String urn) throws VocabularyException
	{
		IVocabulary vocabulary = null;
		if (vocabularyManager == null)
		{
			throw new VocabularyException("Could not instantiate the Vocabulary Interface,"
					+ "might be the Lexbig database server is down.", VIError.LB_INVCTION_EXCPTION);
		}
		else
		{
			try
			{
				vocabulary = vocabularyManager.getVocabulary(urn);
			}
			catch (VocabularyException e)
			{
				throw new VocabularyException(e.getError().getErrorMessage(),
						VIError.LB_INVCTION_EXCPTION, e);
			}
		}
		return vocabulary;
	}

	/**
	 * This method returns the search message.
	 * @return String
	 * @throws VocabularyException - Exception
	 */
	public String getSearchMessage() throws VocabularyException
	{
		String message = VocabUtil.getVocabProperties().getProperty(
				"too.many.results.search.message");
		return Constants.MSG_DEL + message;

	}

	/**
	 * This method returns the concepts list for the given concept code
	 * @param searchConcept - SearchConcept
	 * @return List of IConcept
	 * @throws VocabularyException - Excpetion
	 */
	public List<IConcept> getConceptForCode(SearchConcept searchConcept) throws VocabularyException
	{
		IVocabulary trgVocabulary = getVocabulary(searchConcept.getVocabURN());
		return trgVocabulary.getConceptForCode(searchConcept.getSearchTerm());
	}

	/**
	 * This method returns the map of conceptCode V/s VolumeInDb.
	 * @return Map of String, String
	 * @throws PVManagerException - exception
	 */
	public Map<String, String> getVolumeInDb() throws PVManagerException
	{
		return pvManager.getVolumeInDb();
	}

	/**
	 * this method will sort the concept list.
	 * @param concepts - List of IConcept
	 */
	public void sortResults(List<IConcept> concepts)
	{
		ConceptNameComparator conceptNameComparator = new ConceptNameComparator();

		Collections.sort(concepts, conceptNameComparator);
	}

	/**
	 * This method is used to check weather need to show default permissible values for selected entity or not.
	 * @param  attribute -QueryableAttributeInterface
	 * @return Map of String, String
	 * @throws PVManagerException - exception
	 */
	public boolean showDefaultPermissibleValues(QueryableAttributeInterface attribute)
			throws PVManagerException
	{
		return pvManager.showDefaultPermissibleValues(attribute);
	}

	/**
	 * This method is used to get the message to display when default values not shown for the entity.
	 * @param attributeInterface QueryableAttributeInterface
	 * @return Message to inform user search for specific Concepts
	 * @throws VocabularyException throws VocabularyException
	 */
	public String getMessageToSearchForPVs(QueryableAttributeInterface attributeInterface)
			throws VocabularyException
	{
		String EntityName = Utility.getDisplayLabel(attributeInterface.getQueryEntity().getName());
		String message = VocabUtil.getVocabProperties().getProperty("search.for.pvs").replaceAll(
				"Entity#Name", EntityName);
		StringBuffer msg = new StringBuffer(Constants.STRING_BUF_SIZE);
		msg
				.append("<table><tr><td class='content_txt' style='padding-left:10px;color:blue' valign='top'>");
		msg.append(message);
		msg.append("<td></tr></table>");
		return msg.toString();

	}

}
