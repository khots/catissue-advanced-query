
package edu.wustl.query.util.reflection;

/**
 * Exception class for Reflection Utilities.
 * @author vijay_pande
 *
 */
public class ReflectionException extends Exception
{

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public ReflectionException()
	{
		super();
	}

	/**
	 * Parameterized Constructor.
	 * @param message String message.
	 */
	public ReflectionException(String message)
	{
		super(message);
	}

	/**
	 * Parameterized Constructor.
	 * @param cause Throwable object
	 */
	public ReflectionException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * Parameterized Constructor.
	 * @param message error message
	 * @param cause Throwable object
	 */
	public ReflectionException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
