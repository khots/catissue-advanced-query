package edu.wustl.query.actionForm;

import java.util.ArrayList;
import java.util.List;

import edu.wustl.common.actionForm.AbstractActionForm;
import edu.wustl.common.beans.NameValueBean;
import edu.wustl.common.beans.RecentQueriesBean;
import edu.wustl.common.domain.AbstractDomainObject;


/**
 * @author chitra_garg
  * used for retrieved queries
 */
public class ShowRetrieveRecentForm extends AbstractActionForm
{
	private static final long serialVersionUID = 1L;
	/**
	 * recent Queries Bean List 
	 */
	private List<RecentQueriesBean> recentQueriesBeanList;
	/**
	 * drop down on recent queries page 
	 */
	List<NameValueBean> resultsPerPageOptions=new ArrayList<NameValueBean>();
	@Override
	public int getFormId()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void reset()
	{
		// TODO Auto-generated method stub

	}

	public void setAllValues(AbstractDomainObject arg0)
	{
		// TODO Auto-generated method stub

	}

	
	/**
	 * @return recent Queries Bean List 
	 */
	public List<RecentQueriesBean> getRecentQueriesBeanList()
	{
		return recentQueriesBeanList;
	}

	
	/**
	 * @param recentQueriesBeanList =recent Queries Bean List 
	 */
	public void setRecentQueriesBeanList(List<RecentQueriesBean> recentQueriesBeanList)
	{
		this.recentQueriesBeanList = recentQueriesBeanList;
	}

	
	/**
	 * @return drop down on recent queries page 
	 */ 
	public List<NameValueBean> getResultsPerPageOptions()
	{
		return resultsPerPageOptions;
	}

	
	/**
	 * @param resultsPerPageOptions =drop down on recent queries page 
	 */
	public void setResultsPerPageOptions(List<NameValueBean> resultsPerPageOptions)
	{
		this.resultsPerPageOptions = resultsPerPageOptions;
	}
	

}
