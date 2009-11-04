/**
 * 
 */

package edu.wustl.query.util.querysuite;

import java.util.ArrayList;
import java.util.List;

import edu.wustl.common.querysuite.queryobject.DSInterval;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.querysuite.queryobject.TimeInterval;
import edu.wustl.common.querysuite.queryobject.YMInterval;
import edu.wustl.query.util.global.Constants;

/**
 * @author supriya_dankh
 *
 */
public class TemporalQueryUtility
{

	/**
	 * This method returns relational operators valid for temporal query.
	 * @return list of relational operators.
	 */
	public static List<String> getRelationalOperators()
	{
		/**
		 * Getting relational operators excluding those deals with Strings
		 */
		List<String> relationalOperatorsList = new ArrayList<String>();
		for (RelationalOperator operator : RelationalOperator.values())
		{
			if ((!operator.getStringRepresentation().equalsIgnoreCase(Constants.Contains))
				&& (!operator.getStringRepresentation().equalsIgnoreCase(Constants.STRATS_WITH))
				&& (!operator.getStringRepresentation().equalsIgnoreCase(Constants.ENDS_WITH))
				&& (!operator.getStringRepresentation().equalsIgnoreCase(Constants.IN))
				&& (!operator.getStringRepresentation().equalsIgnoreCase(Constants.Not_In))
				&& (!operator.getStringRepresentation().equalsIgnoreCase(Constants.IS_NULL))
				&& (!operator.getStringRepresentation().equalsIgnoreCase(Constants.IS_NOT_NULL))
				&& (!operator.getStringRepresentation().equalsIgnoreCase(Constants.NOT_BETWEEN)))

			{
				relationalOperatorsList.add(operator.getStringRepresentation());
			}
		}
		return relationalOperatorsList;
	}

	/**
	 * This method returns the time interval.
	 * @return time interval.
	 */
	public static List<String> getTimeIntervals()
	{
		List<String> timeIntervalList = new ArrayList<String>();
		/**
		 * Getting all days time Intervals
		 */
		for (DSInterval timeInterval : DSInterval.values())
		{
			timeIntervalList.add(timeInterval.name() + "s");
		}

		for (YMInterval timeInterval1 : YMInterval.values())
		{
			timeIntervalList.add(timeInterval1.name() + "s");
		}
		return timeIntervalList;
	}

	/**
	 * Gets time interval.
	 * @param timeIntervalValue interval value
	 * @return TimeInterval
	 */
	public static TimeInterval getTimeInterval(String timeIntervalValue)
	{
		TimeInterval timeInterval = null;
		for (TimeInterval time : TimeInterval.values())
		{
			if (timeIntervalValue.equals(time.name() + "s"))
			{
				timeInterval = time;
				break;
			}
		}
		return timeInterval;
	}

	/**
	 * Returns relational operator based on the string passed.
	 * @param relationalOp relational operator
	 * @return RelationalOperator
	 */
	public static RelationalOperator getRelationalOperator(String relationalOp)
	{
		RelationalOperator relOp = null;
		for (RelationalOperator operator : RelationalOperator.values())
		{
			if ((operator.getStringRepresentation().equals(relationalOp)))
			{
				relOp = operator;
				break;
			}
		}
		return relOp;
	}

}
