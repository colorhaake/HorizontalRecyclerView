package joseph.example.com.horizontalrecyclerview.util;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@SuppressLint( "SimpleDateFormat" )
public class DateUtil {

	private static final TimeZone TZ_UTC = TimeZone.getTimeZone("UTC");

	private static final String DATE_FORMAT_MMM = "MMM";
	public static final String DATE_FORMAT_MDD = "M/dd";
	public static final String DATE_FORMAT_MMMD = "MMM d";
	private static final String DATE_FORMAT_MMMDD = "MMM dd";
	private static final String DATE_FORMAT_EEEEMMMMD = "EEEE, MMMM d";
	private static final String DATE_FORMAT_EEEEMMMMDHMMA = "EEEE, MMMM d, h:mm a";
	private static final String DATE_FORMAT_EEEEMMMMDYYYYHMMA = "EEEE, MMMM d, yyyy h:mm a";
	private static final String DATE_FORMAT_MDDHMMA = "M/dd h:mm a";
	private static final String DATE_FORMAT_MDDYYHMM = "M/dd/yy h:mm";
	private static final String DATE_FORMAT_TBD = "M/dd 'TBD'";
	public static final String DATE_FORMAT_MMMDYYYY = "MMM d yyyy";
	public static final String DATE_FORMAT_MDDYY = "M/dd/yy";
	public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	private static final String DATE_FORMAT_UTC = "yyyy-MM-dd'T'HH:mm:ss";
	private static final String DATE_FORMAT_HMMA = "h:mm a";
	private static final String DATE_FORMAT_EEMDD = "EEE M/dd";

	public static final long MILLIS_SEC = 1000;
	public static final long MILLIS_MIN = 60 * MILLIS_SEC;
	public static final long MILLIS_HOUR = 3600 * MILLIS_SEC;
	public static final long MILLIS_DAY = 86400 * MILLIS_SEC;

	public static final Calendar getCurrentCalendar() { return Calendar.getInstance(); }
	public static final Date getCurrentDate() { return getCurrentCalendar().getTime(); }

	public static final Calendar getCurrentCalendar(Date date) {
		Calendar result = getCurrentCalendar();
		result.setTime(date);
		return result;
	}


	public static final DateFormat formatter(String format) {
		return new SimpleDateFormat(format);
	}

	private static final DateFormat formatter(String format, TimeZone tz) {
		DateFormat out = formatter(format);
		out.setTimeZone( tz );
		return out;
	}

	public static final String format(Date date, String format) {
		return formatter(format).format(date);
	}

	public static final DateFormat parser(String format) {
		return new SimpleDateFormat(format);
	}

	private static final DateFormat parser(String format, TimeZone tz) {
		DateFormat out = parser(format);
		out.setTimeZone(tz);
		return out;
	}

	public static final Date parse(String str, String format) throws ParseException {
		return parser(format).parse(str);
	}

	public static final int getCurrentYear() {
		return getCurrentCalendar().get( Calendar.YEAR );
	}

	// M/dd h:mm a
	public static final String toString_mddhmma(Date date) {
		return format(date, DATE_FORMAT_MDDHMMA);
	}

	// EEEE, MMMM d, h:mm a
	public static final String toString_EEEEMMMMdhmma(Date date) {
		return format(date, DATE_FORMAT_EEEEMMMMDHMMA);
	}

	// EEEE, MMMM d, yyyy h:mm a
	public static final String toString_EEEEMMMMD(Date date) {
		return format(date, DATE_FORMAT_EEEEMMMMD);
	}

	// EEEE, MMMM d, yyyy h:mm a
	public static final String toString_EEEEMMMMdyyyyhmma(Date date) {
		return format(date, DATE_FORMAT_EEEEMMMMDYYYYHMMA);
	}

	// MMM (JAN)
	public static final String toString_MMM(Date date) {
		return format(date, DATE_FORMAT_MMM);
	}

	// M/dd/yy
	public static final String toString_mddyy(Date date) {
		return format(date, DATE_FORMAT_MDDYY);
	}

	// M/dd
	public static final String toString_mdd(Date date) {
		return format(date, DATE_FORMAT_MDD);
	}

	// MMM d
	public static final String toString_mmmd(Date date) {
		return format(date, DATE_FORMAT_MMMD);
	}

	// MMM dd
	public static final String toString_mmmdd(Date date) {
		return format(date, DATE_FORMAT_MMMDD);
	}

	public static final Date toDate_mddyy(String mddyy) throws ParseException {
		return parse(mddyy, DATE_FORMAT_MDDYY);
	}

	// Mon 10/07
	public static final String toString_EEEMdd(Date date) throws ParseException {
		return format(date, DATE_FORMAT_EEMDD);
	}

	// 11:00 AM - doesn't handle 16:00 and 4:00 PM according to user prefs, use our Formatter or Android DateFormat instead
	public static final String toString_hmma(Date date) {
		return format(date, DATE_FORMAT_HMMA);
	}

	public static final String toString_utc(Date date) throws ParseException {
		return formatter(DATE_FORMAT_UTC, TZ_UTC).format(date);
	}

	// Local Time from UTC Time String
	public static final Date toDateFromUTCString(String str) throws ParseException {
		return parser(DATE_FORMAT_UTC, TZ_UTC ).parse(str);
	}

	// Parses a UTC formatted string that is not an actual UTC time - ie no timezone offset
	public static final Date toDateFromUTCLocalString(String str) throws ParseException {
		return parse(str, DATE_FORMAT_UTC);
	}

	// Parses a MDDYYHHMM formatted UTC date string to a local timezone Date
	public static final Date toDateFromMDDYYHHMMUTC(String date) throws ParseException {
		return parser(DATE_FORMAT_MDDYYHMM, TZ_UTC).parse(date);
	}

	public static final Date toDateFromMDD(String mdd) throws ParseException {
		return parse(mdd, DATE_FORMAT_MDD);
	}

	public static final String toString_MddTBD(Date date) throws ParseException {
		return format(date, DATE_FORMAT_TBD);
	}

	public static Date addField(Date date, int field, int amount) {
		Calendar result = getCurrentCalendar(date);
		result.add(field, amount);
		return result.getTime();
	}

	public static Date addDay(Date date, int days) {
		return addField(date, Calendar.DATE, days);
	}

	public static Date addSeconds(Date date, int seconds) {
		return addField(date, Calendar.SECOND, seconds);
	}

	public static Date addMinutes(Date date, int minutes) {
		return addField(date, Calendar.MINUTE, minutes);
	}
}
