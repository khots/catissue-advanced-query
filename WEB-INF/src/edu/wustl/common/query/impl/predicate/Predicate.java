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
 *
 */
public abstract class Predicate
{

	String forVariable;
	String attribute;
	String operator;
	String rhs;

	public Predicate(String forVariable, String attribute, String operator, String rhs)
	{
		this.forVariable = forVariable;
		this.attribute = attribute;
		this.operator = operator;
		this.rhs = rhs;
	}

	public abstract String assemble(String prefix);

}
