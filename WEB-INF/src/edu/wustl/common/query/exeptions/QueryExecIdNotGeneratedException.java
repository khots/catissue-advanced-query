package edu.wustl.common.query.exeptions;

import java.io.PrintStream;
import java.io.PrintWriter;

import edu.wustl.common.util.logger.LoggerConfig;

/**
 * This class is responsible for handling exceptions when Query Execution Id is
 * not generated.
 *
 * @author ravindra_jain
 * @version 1.0
 * @since January 8, 2009
 */
public class QueryExecIdNotGeneratedException extends Exception
{
    /**
     * Static logger instance.
     */
    private static org.apache.log4j.Logger logger = LoggerConfig
            .getConfiguredLogger(QueryExecIdNotGeneratedException.class);
    /**
     * Default serial version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * wrapped exception instance.
     */
    private Exception wrapException;

    /**
     * message initialized with super.getMessage().
     */
    private String message = super.getMessage();

    /**
     * message which is used as supporting message to the main message.
     */
    private String supportingMessage;

    /**
     * Constructor with mesasge string.
     *
     * @param messageString
     *            Message String.
     */
    public QueryExecIdNotGeneratedException(String messageString)
    {
        this(messageString, null);
    }

    /**
     * Constructor with parent exception.
     *
     * @param exception
     *            Exception.
     */
    public QueryExecIdNotGeneratedException(Exception exception)
    {
        this(exception.getMessage(), exception);
    }

    /**
     * Constructor with message string and parent exception.
     *
     * @param messageString
     *            Message String.
     * @param exception
     *            Wrapped Exception.
     */
    public QueryExecIdNotGeneratedException(String messageString,
            Exception exception)
    {
        super(messageString, exception);
        this.wrapException = exception;
    }

    /**
     * Returns the wrapped exception.
     *
     * @return Returns the wrapException.
     */
    public Exception getWrapException()
    {
        return wrapException;
    }

    /**
     * Sets the exception to be wrapped.
     *
     * @param exception
     *            The wrapException to set.
     */
    public void setWrapException(Exception exception)
    {
        this.wrapException = exception;
    }

    /**
     * Overriden method to print the stack trace.
     */
    @Override
    public void printStackTrace()
    {
        super.printStackTrace();
        if (wrapException != null)
        {
            logger.error(wrapException.getMessage(), wrapException);
        }
    }

    /**
     * Overriden method to print the stack trace to the printwriter object.
     *
     * @param thePrintWriter
     *            Printwriter object.
     */
    @Override
    public void printStackTrace(PrintWriter thePrintWriter)
    {
        super.printStackTrace(thePrintWriter);
        if (wrapException != null)
        {
            wrapException.printStackTrace(thePrintWriter);
        }
    }

    /**
     * Overrirden method to print the stack trace.
     *
     * @param thePrintStream
     *            Printstream.
     */
    @Override
    public void printStackTrace(PrintStream thePrintStream)
    {
        super.printStackTrace(thePrintStream);
        if (wrapException != null)
        {
            wrapException.printStackTrace(thePrintStream);
        }
    }

    /**
     * Returns the message string.
     *
     * @return Returns the message.
     */
    @Override
    public String getMessage()
    {
        return message;
    }

    /**
     * Sets the message string.
     *
     * @param messageString
     *            The message to set.
     */
    public void setMessage(String messageString)
    {
        this.message = messageString;
    }

    /**
     * Returns the supporting messsage.
     *
     * @return Returns the supportingMessage.
     */
    public String getSupportingMessage()
    {
        return supportingMessage;
    }

    /**
     * Sets the supporting message.
     *
     * @param supportingMessageString
     *            The supportingMessage to set.
     */
    public void setSupportingMessage(String supportingMessageString)
    {
        this.supportingMessage = supportingMessageString;
    }
}
