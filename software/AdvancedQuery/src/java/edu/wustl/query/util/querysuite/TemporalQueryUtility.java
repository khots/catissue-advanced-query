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
import edu.wustl.query.util.global.AQConstants;

/**
 * @author supriya_dankh
 *
 */
public class TemporalQueryUtility
{

	public static List<String> getRelationalOperators()
	{
		/**
		 * Getting relational operators excluding those deals with Strings
		 */
		List<String> relationalOperatorsList = new ArrayList<String>();
		for (RelationalOperator operator : RelationalOperator.values())
		{
			String opStr = operator.getStringRepresentation();
			if ((!opStr.equals(AQConstants.Contains))
					&& (!opStr.equals(AQConstants.STRATS_WITH))
					&& (!opStr.equals(AQConstants.ENDS_WITH))
					&& (!opStr.equals(AQConstants.IN_OPERATOR))
					&& (!opStr.equals(AQConstants.Between))
					&& (!opStr.equals(AQConstants.Not_In))
					&& (!opStr.equalsIgnoreCase(AQConstants.IS_NULL))
					&& (!opStr.equalsIgnoreCase(AQConstants.IS_NOT_NULL))
					&& (!opStr.equalsIgnoreCase(AQConstants.NOT_BETWEEN)))

			{
				relationalOperatorsList.add(opStr);
			}
		}
		return relationalOperatorsList;
	}

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

	public static RelationalOperator getRelationalOperator(String relationalOp)
	{
		RelationalOperator relOp = null;
		for (RelationalOperator operator : RelationalOperator.values())
		{
			if (operator.getStringRepresentation().equals(relationalOp))
			{
				relOp = operator;
				break;
			}
		}
		return relOp;
	}

}
