/**
 * 
 */

package edu.wustl.common.query.impl.predicate;

/**
 * @author juberahamad_patel
 * 
 *  represnts a predicate involving a prefix unary operator such as 'exists'
 */
public class PrefixUnaryPredicate extends AbstractPredicate
{

	public PrefixUnaryPredicate(String attribute, String operator)
	{
		super(attribute, operator, null);
	}

	/* (non-Javadoc)
	 * @see edu.wustl.common.query.impl.predicate.Predicate#assemble(java.lang.String)
	 */
	@Override
	public String assemble(String prefix)
	{
		StringBuilder predicate = new StringBuilder();
		predicate.append(getOperator()).append(prefix).append(getLhs()).append(')');

		return predicate.toString();
	}

}
