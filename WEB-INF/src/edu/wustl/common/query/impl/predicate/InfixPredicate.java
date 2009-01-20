/**
 * 
 */

package edu.wustl.common.query.impl.predicate;

/**
 * @author juberahamad_patel
 * 
 * represnts a predicate involving a infix  binary operator such as '>='
 *
 */
public class InfixPredicate extends AbstractPredicate
{

	public InfixPredicate(String attribute, String operator, String rhs)
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
		predicate.append(prefix).append(attribute).append(operator).append(rhs);

		return predicate.toString();
	}

}
