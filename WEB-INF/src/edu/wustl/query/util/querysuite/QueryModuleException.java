
package edu.wustl.query.util.querysuite;

/**Class to handle all exceptions of query  module.
 * @author vijay_pande
 *
 */
public class QueryModuleException extends Exception
{

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;
	private transient QueryModuleError key;

	/**Parameterized constructor.
	 * @param message error message
	 */
	public QueryModuleException(String message)
	{
		super(message);
	}

	/**Parameterized constructor.
	 * @param message error message
	 * @param exception Exception object
	 * @param errorKey QueryModuleError
	 */
	public QueryModuleException(String message, Exception exception, QueryModuleError errorKey)
	{
		super(message, exception);
		key = errorKey;
	}

	/**
	 * @return the key
	 */
	public QueryModuleError getKey()
	{
		return key;
	}

}
