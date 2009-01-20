package edu.wustl.query.actionForm;

import java.util.ArrayList;
import java.util.List;

import edu.wustl.common.actionForm.AbstractActionForm;
import edu.wustl.common.beans.NameValueBean;
import edu.wustl.common.beans.RecentQueriesBean;
import edu.wustl.common.domain.AbstractDomainObject;


public class ShowRetrieveRecentForm extends AbstractActionForm
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<RecentQueriesBean> recentQueriesBeanList;
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

	
	public List<RecentQueriesBean> getRecentQueriesBeanList()
	{
		return recentQueriesBeanList;
	}

	
	public void setRecentQueriesBeanList(List<RecentQueriesBean> recentQueriesBeanList)
	{
		this.recentQueriesBeanList = recentQueriesBeanList;
	}

	
	public List<NameValueBean> getResultsPerPageOptions()
	{
		return resultsPerPageOptions;
	}

	
	public void setResultsPerPageOptions(List<NameValueBean> resultsPerPageOptions)
	{
		this.resultsPerPageOptions = resultsPerPageOptions;
	}
	

}
