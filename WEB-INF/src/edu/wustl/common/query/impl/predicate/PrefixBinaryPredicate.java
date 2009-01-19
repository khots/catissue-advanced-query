/**
 * 
 */

package edu.wustl.common.query.impl.predicate;

/**
 * @author juberahamad_patel
 *
 */
public class PrefixBinaryPredicate extends Predicate
{

	public PrefixBinaryPredicate(String forVariable, String attribute, String operator, String rhs)
	{
		super(forVariable, attribute, operator, rhs);

	}

	/* (non-Javadoc)
	 * @see edu.wustl.common.query.impl.predicate.Predicate#assemble(java.lang.String)
	 */
	@Override
	public String assemble(String prefix)
	{
		StringBuilder predicate = new StringBuilder();
		predicate.append(operator).append(prefix).append(attribute).append("),").append(rhs)
				.append(')');

		return predicate.toString();
	}

}
