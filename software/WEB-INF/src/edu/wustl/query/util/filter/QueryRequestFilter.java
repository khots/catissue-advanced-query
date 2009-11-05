
package edu.wustl.query.util.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.query.util.global.Utility;

/**
 * Request filter added to check SessionDataBean.
 * Since Query actions were accessible without log in to the system.
 * @author vijay_pande
 *
 */
public class QueryRequestFilter implements Filter
{

	private static org.apache.log4j.Logger logger = LoggerConfig
			.getConfiguredLogger(QueryRequestFilter.class);

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException
	{

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		SessionDataBean sessionDataBean = Utility.getSessionData(request);
		//Trim initial context path to get URL starting from Action name
		String url = request.getRequestURI().replaceFirst(request.getContextPath() + "/", "");
		if (sessionDataBean == null && isQueryAction(url))
		{
			logger.error("Redirecting to Login Page!");
			response.sendRedirect("Logout.do");
			return;
		}
		chain.doFilter(req, res);
	}

	/**
	 * Method to check validity of URL.
	 * @param url current url
	 * @return isQueryAction boolean for is AdvanceQuery action
	 */
	private boolean isQueryAction(String url)
	{
		return StrutsConfigReader.isQueryAction(url);
	}

	/** Initialization method.
	 * @param config FilterConfig
	 * @throws ServletException Servlet Exception
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig config) throws ServletException
	{

		//add code to initialize resource   
	}

	/**Destroy method.
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy()
	{
		//add code to release any resource   
	}
}
