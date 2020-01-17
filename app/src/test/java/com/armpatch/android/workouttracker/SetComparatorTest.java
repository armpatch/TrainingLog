package com.armpatch.android.workouttracker;

import com.armpatch.android.workouttracker.model.ExerciseSet;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetComparatorTest {

    @Test
    public void test1() {
        String date = Tools.stringFromDate(LocalDate.now());

        List<ExerciseSet> sets = new ArrayList<>();

        int[] setOrders = new int[] {5,4,3,2,1};
        int[] expectedOrder = new int[] {1,2,3,4,5};

        for (int currentSetOrder : setOrders) {
            sets.add(new ExerciseSet(date,
                    "pullup",
                    150,
                    150,
                    0,
                    currentSetOrder));
        }

        sets.sort(SetComparator.get());

        int[] sortedSetOrders = new int[setOrders.length];

        for (int index = 0; index < setOrders.length; index++) {
            sortedSetOrders[index] = sets.get(index).getOrder();
        }

        assertEquals(Arrays.toString(expectedOrder), Arrays.toString(sortedSetOrders));
    }
}
