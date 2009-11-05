
package edu.wustl.common.query.exeptions;

/**
 * @author Siddharth Shah
 * It will be thrown by SQLXML generator, when there is error in the passed IQuery object.
 */
public class SQLXMLException extends Exception
{

	private static final long serialVersionUID = -1971145240336059949L;

	/**
	 * @param message The error Message.
	 */
	public SQLXMLException(String message)
	{
		super(message);
	}

	/**
	 * @param message  The error Message.
	 * @param cause The cause of Exception.
	 */
	public SQLXMLException(String message, Throwable cause)
	{
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
