
package edu.wustl.query.util.querysuite;

import edu.wustl.query.actionforms.CategorySearchForm;
import edu.wustl.query.util.global.Constants;

/**
 * This is an utility class to provide methods required for query interface.
 * @author deepti_shelar
 */
/**
 * @author vijay_pande
 *
 */
final public class QueryModuleActionUtil
{

	private QueryModuleActionUtil()
	{
	}

	/**
	 * This is used to set the default selections for the UI components when 
	 * the screen is loaded for the first time.
	 * @param actionForm form bean
	 * @return CategorySearchForm formbean
	 */
	public static CategorySearchForm setDefaultSelections(CategorySearchForm actionForm)
	{
		if (actionForm.getClassChecked() == null)
		{
			actionForm.setClassChecked(Constants.ON);
		}
		if (actionForm.getAttributeChecked() == null)
		{
			actionForm.setAttributeChecked(Constants.ON);
		}
		if (actionForm.getPermissibleValuesChecked() == null)
		{
			actionForm.setPermissibleValuesChecked(Constants.OFF);
		}
		if (actionForm.getIncludeDescriptionChecked() == null)
		{
			actionForm.setIncludeDescriptionChecked(Constants.OFF);
		}
		//TODO check if null and then set the value of seleted.
		// Bug #5131: Commenting until the Concept Code search is fixed
		// actionForm.setSelected(Constants.TEXT_RADIOBUTTON);
		actionForm.setTextField("");
		actionForm.setPermissibleValuesChecked(Constants.OFF);
		return actionForm;
	}
}
