/*L
 *  Copyright Washington University in St. Louis
 *  Copyright SemanticBits
 *  Copyright Persistent Systems
 *  Copyright Krishagni
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/catissue-advanced-query/LICENSE.txt for details.
 */


package edu.wustl.query.util.querysuite;

public class QueryModuleException extends Exception
{

	private String message;
	private QueryModuleError key;

	public QueryModuleException(String message)
	{
		super();
		this.message = message;
	}

	public QueryModuleException(String message, QueryModuleError key)
	{
		super(message);
		this.key = key;
		this.message = message;
	}

	/**
	 * @return the key
	 */
	public QueryModuleError getKey()
	{
		return key;
	}

}
