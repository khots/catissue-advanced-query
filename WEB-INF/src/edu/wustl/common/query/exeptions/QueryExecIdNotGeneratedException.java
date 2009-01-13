package edu.wustl.common.query.exeptions;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * This class is responsible for handling exceptions 
 * when Query Execution Id is not generated 
 * 
 * @author ravindra_jain
 * @version 1.0
 * @since January 8, 2009
 */
public class QueryExecIdNotGeneratedException extends Exception
{

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	private Exception wrapException;

	/**
	 *  message initialized with super.getMessage()
	 */
	private String message = super.getMessage();

	/**
	 *  message which is used as supporting message to the main message
	 */
	private String supportingMessage;

	/**
	 * Constructor
	 * @param message
	 */
	public QueryExecIdNotGeneratedException(String message)
	{
		this(message, null);
	}

	/**
	 * Constructor
	 * @param ex
	 */
	public QueryExecIdNotGeneratedException(Exception ex)
	{
		this(ex.getMessage(),ex);
	}

	/**
	 * 
	 * @param message
	 * @param wrapException
	 */
	public QueryExecIdNotGeneratedException(String message, Exception wrapException)
	{
		super(message, wrapException);
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
	private void setWrapException(Exception wrapException)
	{
		this.wrapException = wrapException;
	}

	/**
	 * 
	 */
	public void printStackTrace()
	{
		super.printStackTrace();
		if (wrapException != null)
		{
			wrapException.printStackTrace();
		}
	}

	/**
	 * @param thePrintWriter
	 */
	public void printStackTrace(PrintWriter thePrintWriter)
	{
		super.printStackTrace(thePrintWriter);
		if (wrapException != null)
		{
			wrapException.printStackTrace(thePrintWriter);
		}
	}

	/**
	 * @param thePrintStream
	 */
	public void printStackTrace(PrintStream thePrintStream)
	{
		super.printStackTrace(thePrintStream);
		if (wrapException != null)
		{
			wrapException.printStackTrace(thePrintStream);
		}
	}

	/**
	 * @return Returns the message.
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * @param message The message to set.
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}

	/**
	 * @return Returns the supportingMessage.
	 */
	public String getSupportingMessage()
	{
		return supportingMessage;
	}

	/**
	 * @param supportingMessage The supportingMessage to set.
	 */
	public void setSupportingMessage(String supportingMessage)
	{
		this.supportingMessage = supportingMessage;
	}
}