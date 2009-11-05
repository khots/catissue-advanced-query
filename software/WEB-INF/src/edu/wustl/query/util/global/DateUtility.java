package edu.wustl.query.util.global;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleException;


/**
 * It will contain all the Utility methods required for Date Processing.
 * @author pavan_kalantri
 *
 */
public class DateUtility
{
	/**
	 * It will return the Date only up to the Minute Fields.
	 * @param fullDate String value
	 * @return date up to minutess
	 */
	public static String getDateUpToMin(String fullDate)
	{
		StringBuffer dateUpToMin = new StringBuffer("");
		if (fullDate != null && !fullDate.equals(""))
		{
			StringTokenizer stringTokenizer = new StringTokenizer(fullDate, ":");
			String date = stringTokenizer.nextToken();
			if (stringTokenizer.hasMoreTokens())
			{
				dateUpToMin.append(date).append(':').append(stringTokenizer.nextToken());
			}
		}
		return dateUpToMin.toString();
	}

	/**
	 * It will return the Date which wash present before the given no of years from
	 * current date & returns the date in given pattern.
	 * @param years the noOf years.
	 * @param datePattern date pattern required in return string.
	 * @return string representation of date before no of years from current date.
	 */
	public static String getDateBeforeYears(String years,String datePattern)
	{
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
		Double firstValue = Double.parseDouble(years);
		int noOfYears = (int) Math.floor(firstValue);
		double afterYears = firstValue - noOfYears;
		currentDate.add(Calendar.YEAR,(-noOfYears) );
		int seconds = (int)(afterYears*(365.2425*24*60*60));
		currentDate.add(Calendar.SECOND, (-seconds));
		return dateFormatter.format(currentDate.getTime());
	}

	/**
	 *It will return the different between the two dates in the no of years.
	 * @param date first date
	 * @param dateOfBirth second date.
	 * @return no of years between the two dates.
	 */
	public static String getDateDifference(Date date, Date dateOfBirth, String datePattern)
	{
		Calendar tempDate = GregorianCalendar.getInstance();
		tempDate.setTime(date);
		Calendar tempDateOfBirth = GregorianCalendar.getInstance();
		tempDateOfBirth.setTime(dateOfBirth);
		long end = tempDate.getTimeInMillis()
				+ tempDate.getTimeZone().getOffset(tempDate.getTimeInMillis());
		long start = tempDateOfBirth.getTimeInMillis()
				+ tempDateOfBirth.getTimeZone().getOffset(tempDateOfBirth.getTimeInMillis());
		Double milliSeconds = 1000 * 60 * 60 * 24 * 365.25;
		Double diffe = new Double((end - start) /milliSeconds);

		DecimalFormat decimalFormat = new DecimalFormat(datePattern);
		return decimalFormat.format(diffe);
	}

	/**
	 * This method converts String to Date in the given format.
	 * @param dateValue date in string
	 * @param format in which date has to be converted
	 * @return converted Date
	 * @throws QueryModuleException Exception
	 */
	public static Date parseDateFromString(String dateValue, String format)
			throws QueryModuleException
	{
		DateFormat dFormat = new SimpleDateFormat(format);
		Date date;
		try
		{
			date = new Date(dFormat.parse(dateValue).getTime());
		}
		catch (ParseException e)
		{
			throw new QueryModuleException("Invalid date", e, QueryModuleError.GENERIC_EXCEPTION);
		}
		return date;
	}
}
