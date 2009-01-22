package edu.wustl.query.xquerydatatypes;


/**
 * @author chetan_patil
 */
public class XQueryDataTypeInitializationException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private Exception wrapException;

	/**
	 * This is a parameterized Constructor method accepting an exception message.
	 * @param message Exception message
	 */
	public XQueryDataTypeInitializationException(String message)
	{
		this(message, null);
	}

	public XQueryDataTypeInitializationException(String message,
			Object object) {
		// TODO Auto-generated constructor stub
	}

	/**
	 * This is a parameterized Constructor method an exception object.
	 * @param exception Exception
	 */
	public XQueryDataTypeInitializationException(Exception exception)
	{
		this("", exception);
	}

	/**
	 * This is a parameterized Constructor method accepting an exception message and an Exception object.
	 * @param message Exception message
	 * @param wrapException Exception
	 */
	public XQueryDataTypeInitializationException(String message, Exception wrapException)
	{
		super(message);
		this.wrapException = wrapException;
	}

	/**
	 * @return Returns the wrapException.
	 */
	public Exception getWrapException()
	{
		return wrapException;
	}

	/**
	 * @param wrapException The wrapException to set.
	 */
	public void setWrapException(Exception wrapException)
	{
		this.wrapException = wrapException;		
	}
	
}
