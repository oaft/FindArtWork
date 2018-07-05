package com.example.inquallity.findartwork;

import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * This class helps to work with various dates formats
 *
 * @author Olga Aleksandrova on 03-Jul-18.
 */
public final class DateTimeUtil {

    /**
     * This function parses supplied date string with specified {@link SimpleDateFormat} pattern
     *
     * @param date    String representation of some date and time
     * @param pattern Supplied pattern for use in {@link SimpleDateFormat} to parse date
     * @return parsed datetime in milliseconds or 0 if nothing to parse or {@link ParseException} occurred
     */
    public static long parse(String date, String pattern) {
        if (!TextUtils.isEmpty(date)) {
            final SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.US);
            try {
                return sdf.parse(date).getTime();
            } catch (ParseException e) {
                Log.e(DateTimeUtil.class.getSimpleName(), e.getMessage(), e);
                return 0L;
            }
        }
        return 0L;
    }

    /**
     * This function formats supplied datetime with specified {@link SimpleDateFormat} pattern
     *
     * @param millis  Datetime in milliseconds
     * @param pattern Supplied pattern for use in {@link SimpleDateFormat} to format datetime
     * @return formatted datetime string
     */
    public static String format(long millis, String pattern) {
        final SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.US);
        return sdf.format(millis);
    }
}
