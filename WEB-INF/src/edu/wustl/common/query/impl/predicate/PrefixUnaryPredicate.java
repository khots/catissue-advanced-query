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

	public PrefixUnaryPredicate(String forVariable, String attribute, String operator)
	{
		super(forVariable, attribute, operator, null);
	}

	/* (non-Javadoc)
	 * @see edu.wustl.common.query.impl.predicate.Predicate#assemble(java.lang.String)
	 */
	@Override
	public String assemble(String prefix)
	{
		StringBuilder predicate = new StringBuilder();
		predicate.append(operator).append(prefix).append(attribute).append(')');

		return predicate.toString();
	}

}
