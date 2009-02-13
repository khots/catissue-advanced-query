/**
 * 
 */

package edu.wustl.common.query.impl.predicate;

import java.util.ArrayList;
import java.util.List;

import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;

/**
 * @author juberahamad_patel
 *
 *represents a collection of predicates
 */
public class Predicates
{

	final private List<AbstractPredicate> predicates;

	public Predicates()
	{
		predicates = new ArrayList<AbstractPredicate>();
	}

	/**
	 * add given predicate to this collection
	 * @param predicate
	 */
	public void addPredicate(AbstractPredicate predicate)
	{
		predicates.add(predicate);
	}

	/**
	 * generate xquery fragment to represent this collection of predicates
	 * @param prefix
	 * @return
	 */
	public String assemble(String prefix)
	{
		String newPrefix = prefix;

		if (newPrefix == null)
		{
			newPrefix = "";
		}

		StringBuilder string = new StringBuilder();
		for (AbstractPredicate predicate : predicates)
		{
			string.append(predicate.assemble(prefix)).append(Constants.QUERY_AND);
		}

		return Constants.QUERY_OPENING_PARENTHESIS + Utility.removeLastAnd(string.toString())
				+ Constants.QUERY_CLOSING_PARENTHESIS;

	}

	/**
	 * convenience method for creating predicates without prefix
	 * @return
	 */
	public String assemble()
	{
		return assemble("");
	}

	/**
	 * @return the predicates
	 */
	public List<AbstractPredicate> getPredicates()
	{
		return predicates;
	}

}
