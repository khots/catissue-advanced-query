/**
 * 
 */
package edu.wustl.common.query.impl.predicate;



/**
 * @author juberahamad_patel
 * 
 * represnts a predicate involving negation of any other predicate
 *
 */
public class NegationPredicate extends AbstractPredicate
{

	private AbstractPredicate predicate;
	
	/**
	 * @param attribute
	 * @param operator
	 * @param rhs
	 */
	public NegationPredicate(AbstractPredicate predicate)
	{
		super(null, null, null);
		this.predicate = predicate;
		
	}

	/* (non-Javadoc)
	 * @see edu.wustl.common.query.impl.predicate.AbstractPredicate#assemble(java.lang.String)
	 */
	@Override
	public String assemble(String prefix)
	{
		StringBuilder predicate = new StringBuilder();
		predicate.append("not(").append(this.predicate.assemble(prefix)).append(')');
		
		return predicate.toString();
		
	}
	
	/**
	 * @return the rhs of the negated predicate
	 */
	public String getRhs()
	{
		return predicate.getRhs();
	}

	
	/**
	 * @param rhs the rhs to set in the negated predicate
	 */
	public void setRhs(String rhs)
	{
		predicate.setRhs(rhs);
	}

}
