/**
 * 
 */

package edu.wustl.common.query.impl.predicate;

import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.query.util.global.Utility;

/**
 * @author juberahamad_patel
 * 
 * represents a predicate in xquery for clause.
 * the parts of the predicates are kept disassembled so that 
 * the predicate can built using the given prefix.
 * the prefix represents the point relative to which the predicate must be built
 *
 */
public abstract class AbstractPredicate
{

	/**
	 * the attribute on which this predicate is.
	 */
	private String lhs;

	/**
	 * operator involved in the predicate
	 */
	private String operator;

	/**
	 * right hand side of the predicate
	 */
	private String rhs;

	/**
	 * the alias of the attribute of this predicate
	 */
	private String attributeAlias;

	/**
	 * 
	 * @param lhs
	 * @param operator
	 * @param rhs
	 */
	public AbstractPredicate(String lhs, String operator, String rhs)
	{
		this.lhs = lhs;
		this.operator = operator;
		this.rhs = rhs;
	}

	/**
	 * build the string representation of the predicate relative to
	 * the point represented by prefix 
	 * @param prefix   
	 * @return
	 */
	public abstract String assemble(String prefix);

	/**
	 * @return the rhs
	 */
	public String getRhs()
	{
		return rhs;
	}

	/**
	 * @param rhs the rhs to set
	 */
	public void setRhs(String rhs)
	{
		this.rhs = rhs;
	}

	/**
	 * @return the lhs
	 */
	public String getLhs()
	{
		return lhs;
	}

	/**
	 * 
	 * @return the operator
	 */
	protected String getOperator()
	{
		return operator;
	}

	/**
	 * 
	 * @return the alias of the attribute of this predicate
	 */
	public String getAttributeAlias()
	{
		return attributeAlias;
	}

	public void createAttributeAlias(IExpression expression)
	{
		String attributeName = null;
		int index = getLhs().lastIndexOf('/');

		if (index == -1)
		{
			attributeName = getLhs();
		}
		else
		{
			attributeName = getLhs().substring(index + 1);
		}

		attributeAlias = Utility.getAliasFor(attributeName, expression);

	}

}
