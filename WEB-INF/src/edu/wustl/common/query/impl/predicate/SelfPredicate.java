/**
 * 
 */

package edu.wustl.common.query.impl.predicate;

/**
 * @author juberahamad_patel
 *
 */
public class SelfPredicate extends AbstractPredicate
{

	public SelfPredicate(String lhs, String operator)
	{
		super(lhs, operator, null);
	}

	/* (non-Javadoc)
	 * @see edu.wustl.common.query.impl.predicate.AbstractPredicate#assemble(java.lang.String)
	 */
	@Override
	public String assemble(String prefix)
	{
		StringBuilder predicate = new StringBuilder();
		predicate.append(prefix).append(getLhs()).append(getOperator());

		return predicate.toString();
	}

}
