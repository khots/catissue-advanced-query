
package edu.wustl.common.query.impl.predicate;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import edu.wustl.common.query.exeptions.SQLXMLException;
import edu.wustl.common.querysuite.queryobject.IExpression;

/**
* @author juberahamad_patel
* 
* generates the predicates for XQuery
*
*/
public class PredicateGenerator
{

	/**
	 * the map of expressions that have a for variable associated with them and those varibale
	 */
	private Map<IExpression, String> forVariables;

	/**
	 * map of expression and corresponding predicates 
	 *  
	 */
	private Map<IExpression, Predicates> predicates;

	public PredicateGenerator(Map<IExpression, String> forVariables, String wherePart)
			throws SQLXMLException
	{
		this.forVariables = forVariables;
		predicates = new LinkedHashMap<IExpression, Predicates>();

		WherePartParser parser = new WherePartParser(wherePart, this);
		try
		{
			parser.parse();
		}
		catch (ParseException e)
		{
			throw new SQLXMLException("problem with parsing", e);
		}
	}

	/**
	 * generate predicates for the entity of the given  expression. 
	 * These include the predicates on the entity as well as 
	 * the predicates on entities of all its subexpressions
	 *  
	 * @param expression
	 * @return 
	 */
	public Predicates getPredicates(IExpression expression)
	{
		return predicates.get(expression);

	}

	public void addPredicate(String forVariable, Predicate predicate)
	{
		IExpression expression = null;

		//find the right expression
		for (Entry<IExpression, String> entry : forVariables.entrySet())
		{
			if (forVariable.equals(entry.getValue()))
			{
				expression = entry.getKey();
				break;
			}
		}

		Predicates values = predicates.get(expression);
		if (values == null)
		{
			values = new Predicates();
		}

		values.addPredicate(predicate);
		predicates.put(expression, values);

	}

}