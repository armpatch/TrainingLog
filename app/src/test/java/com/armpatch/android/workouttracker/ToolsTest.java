package com.armpatch.android.workouttracker;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.threeten.bp.LocalDate;

public class ToolsTest {
    @Test
    public void evaluatesFormattedDate() {
        LocalDate localDate = LocalDate.of(2020, 1, 1);
        String result = Tools.formattedDate(localDate);
        String expected = "Wednesday, January 1";
        assertEquals(expected, result);
    }
}
