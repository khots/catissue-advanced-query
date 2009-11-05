/**
 * 
 */

package edu.wustl.query.bizlogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import junit.framework.Assert;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.common.dynamicextensions.domaininterface.PermissibleValueInterface;
import edu.common.dynamicextensions.exception.DynamicExtensionsApplicationException;
import edu.common.dynamicextensions.exception.DynamicExtensionsSystemException;
import edu.wustl.common.query.pvmanager.impl.PVManagerException;
import edu.wustl.common.querysuite.querableobject.QueryableObjectUtility;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableObjectInterface;
import edu.wustl.common.vocab.IConcept;
import edu.wustl.common.vocab.IVocabulary;
import edu.wustl.common.vocab.IVocabularyManager;
import edu.wustl.common.vocab.VocabularyException;
import edu.wustl.common.vocab.utility.VocabUtil;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.QueryBuilder;
import edu.wustl.query.util.global.VIProperties;
import edu.wustl.query.utility.Utility;
import edu.wustl.vi.enums.VISearchAlgorithm;
import edu.wustl.vi.search.SearchConcept;

/**
 * @author amit_doshi
 *
 */
public class SearchPermissibleValueBizlogicTestCases extends QueryBaseTestCases
{

	SearchPermissibleValueBizlogic searchpermissiblevaluebizlogic = null;
	IVocabularyManager vocabMngr = null;

	static
	{
		Utility.initTest();
	}

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setVIProperties();
		searchpermissiblevaluebizlogic = new SearchPermissibleValueBizlogic();
	}

	/**
	* Test the Method: getVocabularies()
	* 
	*/
	public void testGetVocabularies() throws Exception
	{

		try
		{
			System.out.println("---Executing Test Case----:testGetVocabularies()");
			//Create a variable of the returnType for method call 'getVocabularies'
			List<IVocabulary> vocabularies = null;
			//Call to the Utility Method
			vocabularies = searchpermissiblevaluebizlogic.getVocabularies();
			System.out.println("Vocab List Size :: " + vocabularies.size());
			//Call Asserts on the return value of the Utility method under test.
			// We also call the assert on the getter methods present on the Return variable Class.
			Assert.assertTrue("Error Occurred while loading the  Vocabularies.",
					(vocabularies != null && !vocabularies.isEmpty()));
		}
		catch (VocabularyException vocabularyexception)
		{
			vocabularyexception.printStackTrace();
			Assert.assertFalse("Error Occurred while loading the  Vocabularies", true);
		}

	}

	/**
	* Test the Method: getConfiguredPermissibleValueList(QueryableAttributeInterface,List)
	* 
	*/
	public void testGetConfiguredPermissibleValueList() throws Exception
	{
		try
		{
			System.out
					.println("---Executing Test Case----:testGetConfiguredPermissibleValueList()");
			//Create a variable of the returnType for method call 'getConfiguredPermissibleValueList'
			List<IConcept> conceptList = null;
			QueryableAttributeInterface attribute = getQueryableAttributeInterface("Race", "name");
			List<Integer> showMessage = new ArrayList<Integer>();
			//Call to the Utility Method
			conceptList = searchpermissiblevaluebizlogic.getConfiguredPermissibleValueList(
					attribute, showMessage);
			//Call Asserts on the return value of the Utility method under test.
			// We also call the assert on the getter methods present on the Return variable Class.
			Assert.assertTrue("Error Occurred while retrieving Configured Permissible Values",
					(conceptList != null && !conceptList.isEmpty()));
		}
		catch (VocabularyException vocabularyexception1)
		{
			vocabularyexception1.printStackTrace();
			Assert.assertFalse("Error Occurred while retrieving Configured Permissible Values",
					true);
		}
		catch (PVManagerException pvmanagerexception)
		{
			pvmanagerexception.printStackTrace();
			Assert.assertFalse("Error Occurred while retrieving Configured Permissible Values",
					true);
		}

	}

	/**
	* Test the Method: getPermissibleValueListFromDB(QueryableAttributeInterface)
	* 
	*/
	public void testGetPermissibleValueListFromDB() throws Exception
	{

		try
		{
			System.out.println("---Executing Test Case----:testGetPermissibleValueListFromDB()");
			QueryableAttributeInterface queryableattributeinterface = getQueryableAttributeInterface(
					"Race", "name");
			//Create a variable of the returnType for method call 'getPermissibleValueListFromDB'

			//Call to the Utility Method
			List<PermissibleValueInterface> pvList = searchpermissiblevaluebizlogic
					.getPermissibleValueListFromDB(queryableattributeinterface);
			//Call Asserts on the return value of the Utility method under test.
			// We also call the assert on the getter methods present on the Return variable Class.
			Assert.assertTrue("Error Occurred while retrieving values form DB",
					(pvList != null && !pvList.isEmpty()));
		}
		catch (PVManagerException pvmanagerexception1)
		{
			pvmanagerexception1.printStackTrace();
			Assert.assertFalse("Error Occurred while retrieving values form DB", true);
		}

	}

	/**
	* Test the Method: getMappedConcepts(QueryableAttributeInterface,IVocabulary,List)
	* 
	*/
	public void testGetMappedConcepts() throws Exception
	{

		try
		{
			System.out.println("---Executing Test Case----:testGetMappedConcepts()");
			//Create a variable of the returnType for method call 'getMappedConcepts'
			Map mappedConcept;
			QueryableAttributeInterface queryableattributeinterface = getQueryableAttributeInterface(
					"Gender", "name");
			//Call to the Utility Method
			IVocabulary targVocabulary = searchpermissiblevaluebizlogic
					.getVocabulary("urn:oid:11.11.0.235");
			//IVocabulary target = vocabMngr.getVocabulary();
			List<Integer> showMessage = new ArrayList<Integer>();
			mappedConcept = searchpermissiblevaluebizlogic.getMappedConcepts(
					queryableattributeinterface, targVocabulary, showMessage);
			//Call Asserts on the return value of the Utility method under test.
			// We also call the assert on the getter methods present on the Return variable Class.
			Assert.assertTrue("Error Occurred while retrieving Mapped concept",
					(mappedConcept != null && !mappedConcept.isEmpty()));
		}
		catch (PVManagerException pvmanagerexception2)
		{
			pvmanagerexception2.printStackTrace();
			Assert.assertFalse("Error Occurred while retrieving Mapped concept", true);
		}
		catch (VocabularyException vocabularyexception4)
		{
			vocabularyexception4.printStackTrace();
			Assert.assertFalse("Error Occurred while retrieving Mapped concept", true);
		}

	}

	/**
	* Test the Method: sortResults(List)
	* 
	*/
	public void testSortResults() throws Exception
	{
		try
		{
			System.out.println("---Executing Test Case----:testSortResults()");
			QueryableAttributeInterface attribute = getQueryableAttributeInterface("Gender", "name");
			List<Integer> showMessage = new ArrayList<Integer>();
			List<IConcept> conceptList = searchpermissiblevaluebizlogic
					.getConfiguredPermissibleValueList(attribute, showMessage);
			//Call to the Utility Method
			searchpermissiblevaluebizlogic.sortResults(conceptList);
			if (conceptList != null && conceptList.size() >= 2)
			{
				IConcept concept1 = conceptList.get(0);
				IConcept concept2 = conceptList.get(1);
				int isEqualTo = concept1.getDescription().trim().compareToIgnoreCase(
						concept2.getDescription().trim());
				Assert.assertTrue("Error Occurred while Sorting the Concpet List", isEqualTo < 0);
			}
			else
			{
				Assert.assertFalse("Error Occurred while Sorting the Concpet List", true);
			}
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			Assert.assertFalse("Error Occurred while retrieving Mapped concept", true);
		}

	}

	/**
	* Test the Method: getConceptForCode(SearchConcept)
	* 
	*/
	public void testGetConceptForCode() throws Exception
	{

		try
		{
			System.out.println("---Executing Test Case----:testGetConceptForCode()");
			//Create a variable of the returnType for method call 'getConceptForCode'
			List<IConcept> conceptList;
			SearchConcept searchConcept = new SearchConcept();
			searchConcept.setSearchTerm("1270");
			searchConcept.setVocabURN("urn:oid:11.11.0.225");
			//Call to the Utility Method
			conceptList = searchpermissiblevaluebizlogic.getConceptForCode(searchConcept);
			//Call Asserts on the return value of the Utility method under test.
			// We also call the assert on the getter methods present on the Return variable Class.
			Assert.assertTrue("Error Occurred while getting Concept for Code",
					(conceptList != null && !conceptList.isEmpty()));
		}
		catch (VocabularyException exception)
		{
			exception.printStackTrace();
			Assert.assertFalse("Error Occurred while getting Concept for Code", true);
		}

	}

	/**
	* Test the Method: searchConcept(SearchConcept)
	* 
	*/
	public void testSearchConceptForExact_Phrase() throws Exception
	{

		try
		{
			System.out.println("---Executing Test Case----:testSearchConceptForExact_Phrase()");
			SearchConcept searchConcept = new SearchConcept();
			searchConcept.setSearchTerm("Female");
			searchConcept.setSearchCriteria(VISearchAlgorithm.EXACT_PHRASE.toString());
			searchConcept.setVocabURN("urn:oid:11.11.0.225");
			//Create a variable of the returnType for method call 'searchConcept'
			List seachedConceptList;
			seachedConceptList = null;

			//Call to the Utility Method
			seachedConceptList = searchpermissiblevaluebizlogic.searchConcept(searchConcept);
			//Call Asserts on the return value of the Utility method under test.
			// We also call the assert on the getter methods present on the Return variable Class.
			System.out.println("seachedConceptList--" + seachedConceptList);
			Assert.assertTrue("Error Occurred while searching for the Concept",
					(seachedConceptList != null && !seachedConceptList.isEmpty()));
		}
		catch (VocabularyException exception)
		{
			exception.printStackTrace();
			Assert.assertFalse("Error Occurred while searching for the Concept", true);
		}

	}

	/**
	* Test the Method: searchConcept(SearchConcept)
	* 
	*/
	public void testSearchConceptForAny_Word() throws Exception
	{

		try
		{
			System.out.println("---Executing Test Case----:testSearchConceptForAny_Word()");
			SearchConcept searchConcept = new SearchConcept();
			searchConcept.setSearchTerm("Female");
			searchConcept.setSearchCriteria(VISearchAlgorithm.ANY_WORD.toString());
			searchConcept.setVocabURN("urn:oid:11.11.0.225");
			//Create a variable of the returnType for method call 'searchConcept'
			List seachedConceptList;
			seachedConceptList = null;

			//Call to the Utility Method
			seachedConceptList = searchpermissiblevaluebizlogic.searchConcept(searchConcept);
			//Call Asserts on the return value of the Utility method under test.
			// We also call the assert on the getter methods present on the Return variable Class.
			System.out.println("seachedConceptList--" + seachedConceptList);
			Assert.assertTrue(
					"Error Occurred while searching in ANY_WORD search Criteria for the Concept",
					(seachedConceptList != null && !seachedConceptList.isEmpty()));
		}
		catch (VocabularyException exception)
		{
			exception.printStackTrace();
			Assert.assertFalse(
					"Error Occurred while searching in ANY_WORD search Criteria for the Concept ",
					true);
		}

	}

	/**
	* Test the Method: getNoMappingFoundHTML()
	* 
	*/
	public void testGetNoMappingFoundHTML() throws Exception
	{

		try
		{
			System.out.println("---Executing Test Case----:testGetNoMappingFoundHTML()");
			//Create a variable of the returnType for method call 'getNoMappingFoundHTML'
			String html = "";
			//Call to the Utility Method
			html = searchpermissiblevaluebizlogic.getNoMappingFoundHTML();
			//Call Asserts on the return value of the Utility method under test.
			// We also call the assert on the getter methods present on the Return variable Class.
			Assert.assertNotSame("Error Occurred while getting the HTML", "", html);
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			Assert.assertFalse("Error Occurred while getting the HTML", true);
		}

	}

	/**
	* Test the Method: getInfoMessage(int,int)
	* 
	*/
	public void testGetInfoMessage() throws Exception
	{

		try
		{
			System.out.println("---Executing Test Case----:testGetInfoMessage()");
			//Create a variable of the returnType for method call 'getInfoMessage'
			String message;
			//Call to the Utility Method
			message = searchpermissiblevaluebizlogic.getInfoMessage(100, 1000);
			//Call Asserts on the return value of the Utility method under test.
			// We also call the assert on the getter methods present on the Return variable Class.
			Assert.assertTrue("Error Occurred while getting the Info Message", !message
					.contains("##"));

		}
		catch (VocabularyException exception)
		{
			exception.printStackTrace();
			Assert.assertFalse("Error Occurred while getting the Info Message", true);
		}

	}

	/**
	* Test the Method: getHTMLForConcept(String,IConcept,String)
	* 
	*/
	public void testGetHTMLForConcept() throws Exception
	{

		try
		{
			System.out.println("---Executing Test Case----:testGetHTMLForConcept()");
			//Create a variable of the returnType for method call 'getHTMLForConcept'
			String html;
			List<IConcept> conceptList;
			SearchConcept searchConcept = new SearchConcept();
			searchConcept.setSearchTerm("1270");
			searchConcept.setVocabURN("urn:oid:11.11.0.225");
			conceptList = searchpermissiblevaluebizlogic.getConceptForCode(searchConcept);
			//Call to the Utility Method
			html = searchpermissiblevaluebizlogic.getHTMLForConcept("urn:oid:11.11.0.225",
					conceptList.get(0), "1270", "123");
			//Call Asserts on the return value of the Utility method under test.
			// We also call the assert on the getter methods present on the Return variable Class.
			Assert.assertNotSame("Error Occurred while getting the HTML", "", html);
		}
		catch (VocabularyException vocabularyexception)
		{
			vocabularyexception.printStackTrace();
			Assert.assertFalse("Error Occurred while getting the HTML", true);
		}

	}

	/**
	* Test the Method: getHTMLForTargetConcept(String,IConcept,String,String)
	* 
	*/
	public void testGetHTMLForTargetConcept() throws Exception
	{

		try
		{
			System.out.println("---Executing Test Case----:testGetHTMLForTargetConcept()");
			//Create a variable of the returnType for method call 'getHTMLForConcept'
			String html;
			List<IConcept> conceptList;
			SearchConcept searchConcept = new SearchConcept();
			searchConcept.setSearchTerm("1270");
			searchConcept.setVocabURN("urn:oid:11.11.0.225");
			conceptList = searchpermissiblevaluebizlogic.getConceptForCode(searchConcept);
			//Call to the Utility Method
			html = searchpermissiblevaluebizlogic.getHTMLForTargetConcept("urn:oid:11.11.0.225",
					conceptList.get(0), "1270", "50");
			//Call Asserts on the return value of the Utility method under test.
			// We also call the assert on the getter methods present on the Return variable Class.
			Assert.assertNotSame("Error Occurred while getting the HTML", "", html);
		}
		catch (VocabularyException vocabularyexception)
		{
			vocabularyexception.printStackTrace();
			Assert.assertFalse("Error Occurred while getting the HTML", true);
		}

	}

	/**
	* Test the Method: getHTMLForSearchedConcept(String,IConcept,String,String)
	* 
	*/
	public void testGetHTMLForSearchedConcept() throws Exception
	{

		try
		{
			System.out.println("---Executing Test Case----:testGetHTMLForSearchedConcept()");
			//Create a variable of the returnType for method call 'getHTMLForConcept'
			String html;
			List<IConcept> conceptList;
			SearchConcept searchConcept = new SearchConcept();
			searchConcept.setSearchTerm("1270");
			searchConcept.setVocabURN("urn:oid:11.11.0.225");
			conceptList = searchpermissiblevaluebizlogic.getConceptForCode(searchConcept);
			//create the Map of concept Details
			Map<String, Object> conceptsDetail = new HashMap<String, Object>();
			String vocabURN = "srh_"
					+ conceptsDetail.put(edu.wustl.query.util.global.Constants.VOCAB_URN,
							"urn:oid:11.11.0.225");
			Map<String, String> conceptCodeVsVolumeInDb = new HashMap<String, String>();
			conceptCodeVsVolumeInDb.put("1270", "123");

			conceptsDetail.put(edu.wustl.query.util.global.Constants.CON_CODE_VS_VOL_INDB,
					conceptCodeVsVolumeInDb);
			conceptsDetail.put(edu.wustl.query.util.global.Constants.STATUS,
					edu.wustl.query.util.global.Constants.NOT_MED_MAPPED_PVCONCEPT);

			String checkboxId = vocabURN + edu.wustl.query.util.global.Constants.ID_DEL + "1270";
			//Call to the Utility Method
			html = searchpermissiblevaluebizlogic.getHTMLForSearchedConcept(conceptList.get(0),
					checkboxId, conceptsDetail);
			//Call Asserts on the return value of the Utility method under test.
			// We also call the assert on the getter methods present on the Return variable Class.
			Assert.assertNotSame("Error Occurred while getting the HTML", "", html);

		}
		catch (VocabularyException exception)
		{
			exception.printStackTrace();
			Assert.assertFalse("Error Occurred while getting the HTML", true);
		}

	}

	/**
	* Test the Method: getRootVocabularyNodeHTML(String,String)
	* 
	*/
	public void testGetRootVocabularyNodeHTML() throws Exception
	{

		try
		{
			System.out.println("---Executing Test Case----:testGetRootVocabularyNodeHTML()");
			//Create a variable of the returnType for method call 'getRootVocabularyNodeHTML'
			String html;
			//Call to the Utility Method
			html = searchpermissiblevaluebizlogic.getRootVocabularyNodeHTML("urn:oid:11.11.0.225",
					"BJC-MED");
			//Call Asserts on the return value of the Utility method under test.
			// We also call the assert on the getter methods present on the Return variable Class.
			Assert.assertNotSame("Error Occurred while getting the HTML", "", html);

		}
		catch (VocabularyException exception)
		{
			exception.printStackTrace();
			Assert.assertFalse("Error Occurred while getting the HTML", true);
		}

	}

	/**
	* Test the Method: getRootVocabularyHTMLForSearch(String,String)
	* 
	*/
	public void testGetRootVocabularyHTMLForSearch() throws Exception
	{

		try
		{
			System.out.println("---Executing Test Case----:testGetRootVocabularyHTMLForSearch()");
			//Create a variable of the returnType for method call 'getRootVocabularyNodeHTML'
			String html;
			//Call to the Utility Method
			html = searchpermissiblevaluebizlogic.getRootVocabularyHTMLForSearch(
					"urn:oid:11.11.0.225", "BJC-MED");
			//Call Asserts on the return value of the Utility method under test.
			// We also call the assert on the getter methods present on the Return variable Class.
			Assert.assertNotSame("Error Occurred while getting the HTML", "", html);
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			Assert.assertFalse("Error Occurred while getting the HTML", true);
		}

	}

	/**
	* Test the Method: getEndHTML()
	* 
	*/
	public void testGetEndHTML() throws Exception
	{

		try
		{
			System.out.println("---Executing Test Case----:testGetRootVocabularyHTMLForSearch()");
			//Create a variable of the returnType for method call 'getEndHTML'
			String html;
			//Call to the Utility Method
			html = searchpermissiblevaluebizlogic.getEndHTML();
			//Call Asserts on the return value of the Utility method under test.
			// We also call the assert on the getter methods present on the Return variable Class.
			Assert.assertNotSame("Error Occurred while getting the HTML", "", html);
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			Assert.assertFalse("Error Occurred while getting the HTML", true);
		}

	}

	/**
	* Test the Method: getErrorMessageAsHTML(String)
	* 
	*/
	public void testGetErrorMessageAsHTML() throws Exception
	{
		try
		{
			System.out.println("---Executing Test Case----:testGetErrorMessageAsHTML()");
			//Create a variable of the returnType for method call 'getErrorMessageAsHTML'
			String html;
			//Call to the Utility Method
			html = searchpermissiblevaluebizlogic.getErrorMessageAsHTML("Error Message");
			//Call Asserts on the return value of the Utility method under test.
			// We also call the assert on the getter methods present on the Return variable Class.
			Assert.assertNotSame("Error Occurred while getting the HTML", "", html);
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			Assert.assertFalse("Error Occurred while getting the HTML", true);
		}
	}

	/**
	* Test the Method: isSourceVocabCodedTerm(IConcept,String,IVocabulary)
	* 
	*/
	public void testIsSourceVocabCodedTerm() throws Exception
	{

		try
		{
			System.out.println("---Executing Test Case----:testIsSourceVocabCodedTerm()");
			//Create a variable of the returnType for method call 'isSourceVocabCodedTerm'
			List<IConcept> concepts;
			List<IConcept> conceptList;
			SearchConcept searchConcept = new SearchConcept();
			searchConcept.setSearchTerm("3112681");
			searchConcept.setVocabURN("urn:oid:11.11.0.235");
			conceptList = searchpermissiblevaluebizlogic.getConceptForCode(searchConcept);
			//Call to the Utility Method
			IVocabulary vocab = searchpermissiblevaluebizlogic.getVocabulary("urn:oid:11.11.0.225");
			concepts = searchpermissiblevaluebizlogic.isSourceVocabCodedTerm(conceptList.get(0),
					VIProperties.translationAssociation, vocab);
			//Call Asserts on the return value of the Utility method under test.
			// We also call the assert on the getter methods present on the Return variable Class.
			Assert.assertTrue("Error Occurred while Checking for the Term in Source Vocabulary",
					(concepts != null && !concepts.isEmpty()));
		}
		catch (VocabularyException exception)
		{
			exception.printStackTrace();
			Assert.assertFalse("Error Occurred while Checking for the Term in Source Vocabulary",
					true);
		}

	}

	/**
	* Test the Method: isConceptExistInPVList(IConcept,List)
	* 
	*/
	public void testIsConceptExistInPVList() throws Exception
	{
		try
		{
			System.out
					.println("---Executing Test Case----:testIsConceptExistInPVList(concept,list)");
			//Create a variable of the returnType for method call 'isConceptExistInPVList'
			IConcept iconcept1;
			QueryableAttributeInterface queryableattributeinterface = getQueryableAttributeInterface(
					"Gender", "name");
			List<PermissibleValueInterface> pvList = searchpermissiblevaluebizlogic
					.getPermissibleValueListFromDB(queryableattributeinterface);
			List<IConcept> conceptList;
			SearchConcept searchConcept = new SearchConcept();
			searchConcept.setSearchTerm("1270");
			searchConcept.setVocabURN("urn:oid:11.11.0.225");
			conceptList = searchpermissiblevaluebizlogic.getConceptForCode(searchConcept);
			//Call to the Utility Method
			iconcept1 = searchpermissiblevaluebizlogic.isConceptExistInPVList(conceptList.get(0),
					pvList);
			//Call Asserts on the return value of the Utility method under test.
			// We also call the assert on the getter methods present on the Return variable Class.
			Assert.assertNotNull("Error Occurred While checking for the Concept in PVlist",
					iconcept1);
		}
		catch (VocabularyException exception)
		{
			exception.printStackTrace();
			Assert.assertFalse("Error Occurred While checking for the Concept in PVlist", true);
		}

	}

	/**
	* Test the Method: isConceptsExistInPVList(List,List)
	* 
	*/
	public void testIsConceptsExistInPVList() throws Exception
	{
		try
		{
			System.out.println("---Executing Test Case----:testIsConceptsExistInPVList(list,list)");

			//Create a variable of the returnType for method call 'isConceptExistInPVList'
			IConcept iconcept1;
			QueryableAttributeInterface queryableattributeinterface = getQueryableAttributeInterface(
					"Gender", "name");
			List<PermissibleValueInterface> pvList = searchpermissiblevaluebizlogic
					.getPermissibleValueListFromDB(queryableattributeinterface);
			List<IConcept> conceptList;
			SearchConcept searchConcept = new SearchConcept();
			searchConcept.setSearchTerm("1270");
			searchConcept.setVocabURN("urn:oid:11.11.0.225");
			conceptList = searchpermissiblevaluebizlogic.getConceptForCode(searchConcept);
			//Call to the Utility Method
			iconcept1 = searchpermissiblevaluebizlogic.isConceptsExistInPVList(conceptList, pvList);
			//Call Asserts on the return value of the Utility method under test.
			// We also call the assert on the getter methods present on the Return variable Class.
			Assert.assertNotNull("Error Occurred While checking for the Concepts in PVlist",
					iconcept1);
		}
		catch (VocabularyException exception)
		{
			exception.printStackTrace();
			Assert.assertFalse("Error Occurred While checking for the Concepts in PVlist", true);
		}

	}

	/**
	* Test the Method: getDisplayNameForVocab(String)
	* 
	*/
	public void testGetDisplayNameForVocab() throws Exception
	{

		try
		{
			System.out.println("---Executing Test Case----:testGetDisplayNameForVocab()");
			//Create a variable of the returnType for method call 'getDisplayNameForVocab'
			String displayName;
			displayName = "";

			//Call to the Utility Method
			displayName = searchpermissiblevaluebizlogic.getDisplayNameForVocab("MED");
			//Call Asserts on the return value of the Utility method under test.
			// We also call the assert on the getter methods present on the Return variable Class.
			Assert.assertEquals("Error Occurred While getting the display name for MED",
					"MED_PRODUCTION", displayName);

		}
		catch (VocabularyException exception)
		{
			exception.printStackTrace();
			Assert.assertFalse("Error Occurred While getting the display name for MED", true);
		}

	}

	/**
	* Test the Method: getSearchMessage()
	* 
	*/
	public void testGetSearchMessage() throws Exception
	{

		try
		{
			System.out.println("---Executing Test Case----:testGetSearchMessage()");
			//Create a variable of the returnType for method call 'getSearchMessage'
			String message;
			//Call to the Utility Method
			message = searchpermissiblevaluebizlogic.getSearchMessage();
			//Call Asserts on the return value of the Utility method under test.
			// We also call the assert on the getter methods present on the Return variable Class.
			Assert.assertNotSame("Error Occurred while getting the Search Message", "", message);

		}
		catch (VocabularyException exception)
		{
			exception.printStackTrace();
			Assert.assertFalse("Error Occurred while getting the Search Message", true);
		}

	}

	/**
	* Test the Method: getVolumeInDb()
	* 
	*/
	public void testGetVolumeInDb() throws Exception
	{

		try
		{
			System.out.println("---Executing Test Case----:testGetVolumeInDb()");
			//Create a variable of the returnType for method call 'getVolumeInDb'
			Map<String, String> conceptCodeVsVolumeInDb = null;
			//Call to the Utility Method
			conceptCodeVsVolumeInDb = searchpermissiblevaluebizlogic.getVolumeInDb();
			//Call Asserts on the return value of the Utility method under test.
			// We also call the assert on the getter methods present on the Return variable Class.
			Assert.assertNotNull("Error Occurred while getting the Volume In DB",
					conceptCodeVsVolumeInDb);
		}
		catch (PVManagerException pvmanagerexception)
		{
			pvmanagerexception.printStackTrace();
			Assert.assertFalse("Error Occurred while getting the Volume In DB", true);
		}

	}

	/**
	* Test the Method: showDefaultPermissibleValues()
	*/
	public void testShowDefaultPermissibleValues()
	{

		try
		{
			System.out.println("---Executing Test Case----:testShowDefaultPermissibleValues()");
			//Create a variable of the QueryableAttributeInterface
			QueryableAttributeInterface attribute = getQueryableAttributeInterface("Gender", "name");
			//Call to the Utility Method
			boolean show = searchpermissiblevaluebizlogic.showDefaultPermissibleValues(attribute);
			//Call Asserts on the return value of the Utility method under test.
			// We also call the assert on the getter methods present on the Return variable Class.
			Assert.assertTrue("Error Occurred while ---Executing Test Case----", show);

		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			Assert.assertFalse("Error Occurred while ---Executing Test Case----", true);
		}

	}

	/**
	* Test the Method: showDefaultPermissibleValues()
	*/
	public void testGetMessageToSearchForPVs()
	{

		try
		{
			System.out.println("---Executing Test Case----:testGetMessageToSearchForPVs()");
			//Create a variable of the QueryableAttributeInterface
			QueryableAttributeInterface attribute = getQueryableAttributeInterface("Gender", "name");
			//Call to the Utility Method
			String message = searchpermissiblevaluebizlogic.getMessageToSearchForPVs(attribute);
			//Call Asserts on the return value of the Utility method under test.
			// We also call the assert on the getter methods present on the Return variable Class.
			Assert.assertTrue("Error Occurred while ---Executing Test Case----", (message
					.indexOf("Gender") != -1));

		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			Assert.assertFalse("Error Occurred while ---Executing Test Case----", true);
		}

	}

	/**
	* Test the Method: getToolTip()
	*/
	public void testGetToolTip()
	{

		try
		{
			System.out.println("---Executing Test Case----:testGetToolTip()");
			List<IConcept> conceptList;
			SearchConcept searchConcept = new SearchConcept();
			searchConcept.setSearchTerm("1270");
			searchConcept.setVocabURN("urn:oid:11.11.0.225");
			conceptList = searchpermissiblevaluebizlogic.getConceptForCode(searchConcept);
			//Call to the Utility Method
			String tootlip = searchpermissiblevaluebizlogic.getToolTip(conceptList.get(0), "123");
			//Call Asserts on the return value of the Utility method under test.
			// We also call the assert on the getter methods present on the Return variable Class.
			Assert.assertTrue("Error Occurred while ---Executing Test Case----", (tootlip
					.indexOf(Constants.CONCEPT_CODE) != -1));

		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			Assert.assertFalse("Error Occurred while ---Executing Test Case----", true);
		}

	}

	/**
	 * @return
	 * @throws DynamicExtensionsSystemException
	 * @throws DynamicExtensionsApplicationException
	 */
	private QueryableAttributeInterface getQueryableAttributeInterface(String EntityName,
			String attributeName) throws DynamicExtensionsSystemException,
			DynamicExtensionsApplicationException
	{
		EntityInterface entity = QueryBuilder.getEntityByName(EntityName);
		QueryableObjectInterface queryableObject = QueryableObjectUtility
				.createQueryableObject(entity);
		QueryableAttributeInterface queryableattributeinterface = QueryBuilder.findAttribute(
				queryableObject, attributeName);
		return queryableattributeinterface;
	}

	/**
	 * set the VI Properties
	 * @throws VocabularyException
	 */
	private void setVIProperties() throws VocabularyException
	{

		Properties vocabProperties = VocabUtil.getVocabProperties();
		VIProperties.sourceVocabName = vocabProperties.getProperty("source.vocab.name");
		VIProperties.sourceVocabVersion = vocabProperties.getProperty("source.vocab.version");
		VIProperties.sourceVocabUrn = vocabProperties.getProperty("source.vocab.urn");
		VIProperties.searchAlgorithm = vocabProperties.getProperty("match.algorithm");
		VIProperties.maxPVsToShow = Integer.valueOf(vocabProperties.getProperty("pvs.to.show"));
		VIProperties.maxToReturnFromSearch = Integer.valueOf(vocabProperties
				.getProperty("max.to.return.from.search"));
		VIProperties.translationAssociation = vocabProperties
				.getProperty("vocab.translation.association.name");
		VIProperties.medClassName = vocabProperties.getProperty("med.class.name");

	}

}
