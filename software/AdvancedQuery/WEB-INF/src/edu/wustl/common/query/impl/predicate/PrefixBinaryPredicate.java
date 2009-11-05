/**
 * 
 */

package edu.wustl.common.query.impl.predicate;

/**
 * @author juberahamad_patel
 * 
 * represnts a predicate involving a prefix binary operator such as 'contains'
 *
 */
public class PrefixBinaryPredicate extends AbstractPredicate
{

	public PrefixBinaryPredicate(String attribute, String operator, String rhs)
	{
		super(attribute, operator, rhs);

	}

	/* (non-Javadoc)
	 * @see edu.wustl.common.query.impl.predicate.Predicate#assemble(java.lang.String)
	 */
	@Override
	public String assemble(String prefix)
	{
		StringBuilder predicate = new StringBuilder();
		predicate.append(getOperator()).append(prefix).append(getLhs()).append("),").append(
				getRhs()).append(')');

		return predicate.toString();
	}

}