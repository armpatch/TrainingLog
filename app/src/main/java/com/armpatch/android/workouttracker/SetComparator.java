package com.armpatch.android.workouttracker;

import com.armpatch.android.workouttracker.model.ExerciseSet;

import java.util.Comparator;

/**
 * Sorts Sets first by the order of their groups, then by the order within their group
 */
public class SetComparator implements Comparator<ExerciseSet>{

    @Override
    public int compare(ExerciseSet set1, ExerciseSet set2) {
        return Integer.compare(set1.getOrder(), set2.getOrder());
    }
}
