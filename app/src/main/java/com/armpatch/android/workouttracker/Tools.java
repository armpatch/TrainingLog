package com.armpatch.android.workouttracker;

import android.content.Context;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

public class Tools {

    public static String getRelativeDate(Context applicationContext, int days) {
        LocalDate today = LocalDate.now().plusDays(days);

        if (days == 0)
            return applicationContext.getString(R.string.today);
        if (days == 1)
            return applicationContext.getString(R.string.tomorrow);
        if (days == -1)
            return "Yesterday";

        return today.format(DateTimeFormatter.ofPattern("EEEE, MMMM d"));
    }

}
