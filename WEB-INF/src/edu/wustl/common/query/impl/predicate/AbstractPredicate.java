/**
 * 
 */
package edu.wustl.common.query.impl.predicate;



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
	
	protected String forVariable;
	protected String attribute;
	protected String operator;
	protected String rhs;
	
	public AbstractPredicate(String forVariable, String attribute, String operator, String rhs)
	{
		this.forVariable = forVariable;
		this.attribute = attribute;
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
	

}
