package com.pls.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtility {

	private static final SimpleDateFormat simpleDateFormat =
			new SimpleDateFormat();

	static
	{
		simpleDateFormat.setLenient( false );
	}

	/**
	 * Convert a <code>String</code> into a <code>Date</code>.
	 *
	 * @param str a <code>String</code> value, non null
	 * @param parsePatterns a <code>String[]</code> value, non null, Specifications
	 * can be found in {@link java.util.SimpleDateFormat}.
	 * @return a <code>Date</code> value

	 */
	public static synchronized String dateToString( final Date date,
			final String format ) {
		simpleDateFormat.applyPattern(format);
		return simpleDateFormat.format(date);
	}

	/**
	 * Convert a <code>String</code> into a <code>Date</code>.
	 * This method does not allow for lenient Date conversions. The following
	 * example would throw an exception:
	 * <p><code>
	 *  DateUtility.stringToDate( "32012006", "ddMMyyyy");
	 * </code>
	 * @param date A string representing a date.
	 * @param format A string containing a simple date format, as defined
	 * in {@link java.text.SimpleDateFormat}.
	 * @return A Date object corresponding to the date argument.
	 * @exception java.text.X12ParseException when a Date can't be extracted
	 * from the date parameter using the given format.
	 **/
	public static synchronized Date stringToDate(final String date, final String format)
			throws ParseException {
		simpleDateFormat.applyPattern(format);
		return simpleDateFormat.parse(date);
	}

	/**
	 * For the given date, returns a date object with the same month/day/year, but
	 * sets the time to the beginning of the day, 00:00.
	 * @param date
	 * @return
	 */
	public static Date rollToBeginningOfDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.set(Calendar.AM_PM, Calendar.AM);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		return cal.getTime();
	}

	/**
	 * For the given date, returns a date object with the same month/day/year, but
	 * sets the time to the beginning of the day, 00:00.
	 * @param date
	 * @return
	 */
	public static Date rollToEndOfDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 0);

		return cal.getTime();
	}
}
