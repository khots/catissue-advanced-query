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
 */
public class Predicates
{
	List<Predicate> predicates;
	
	public Predicates()
	{
		predicates = new ArrayList<Predicate>();
	}
	
	public void addPredicate(Predicate predicate)
	{
		predicates.add(predicate);
	}
	
	public String assemble(String prefix)
	{
		if(prefix == null)
		{
			prefix = "";
		}
		
		StringBuilder string = new StringBuilder();
		for(Predicate predicate : predicates)
		{
			string.append(predicate.assemble(prefix)).append(Constants.QUERY_AND);
		}
		
		return Constants.QUERY_OPENING_PARENTHESIS + Utility.removeLastAnd(string.toString()) + Constants.QUERY_CLOSING_PARENTHESIS;
		
		

	}

	public String assemble()
	{
		return assemble("");
	}

}
