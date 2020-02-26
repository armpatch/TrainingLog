package com.armpatch.android.workouttracker;

import android.content.Context;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import static org.threeten.bp.temporal.ChronoUnit.DAYS;

public class Tools {

    public static String KEY_EXERCISE_NAME = "KEY_EXERCISE_NAME";
    public static String KEY_EXERCISE_DATE = "KEY_EXERCISE_DATE";

    public static String getRelativeDateText(Context activityContext, LocalDate selectedDate) {
        long days = LocalDate.now().until(selectedDate, DAYS );

        if (days == 0)
            return activityContext.getString(R.string.today);
        if (days == 1)
            return activityContext.getString(R.string.tomorrow);
        if (days == -1)
            return activityContext.getString(R.string.yesterday);

        return formattedDate(selectedDate);
    }

    /**
     * Returns a formatted date. Ex: Monday, August 1
     * @param date LocalDate
     * @return a string
     */
    public static String formattedDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("EEEE, MMMM d")); //Ex: Monday, August 1
    }

    public static String toHistoricalDate(String date) {
        LocalDate localDate = LocalDate.parse(date);

        return localDate.format(DateTimeFormatter.ofPattern("EEEE, MMMM d, y")); //Ex: Monday, August 1
    }

    public static LocalDate dateFromString(String string) {
        return LocalDate.parse(string, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static String stringFromDate(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

}
