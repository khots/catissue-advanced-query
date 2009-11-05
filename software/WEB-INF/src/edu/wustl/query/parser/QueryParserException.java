
package edu.wustl.query.parser;

/**
 * Exception class for Query parser.
 * @author vijay_pande
 *
 */
public class QueryParserException extends Exception
{

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public QueryParserException()
	{
		super();
	}

	/**
	 * Parameterized Constructor.
	 * @param message String message.
	 */
	public QueryParserException(String message)
	{
		super(message);
	}

	/**
	 * Parameterized Constructor.
	 * @param cause Throwable object
	 */
	public QueryParserException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * Parameterized Constructor.
	 * @param message error message
	 * @param cause Throwable object
	 */
	public QueryParserException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
