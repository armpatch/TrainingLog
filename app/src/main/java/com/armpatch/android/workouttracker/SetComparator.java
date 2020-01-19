package com.armpatch.android.workouttracker;

import com.armpatch.android.workouttracker.model.ExerciseSet;

import java.util.Comparator;

/**
 * Sorts Sets first by the order of their groups, then by the order within their group
 */
public class SetComparator implements Comparator<ExerciseSet>{

    @Override
    public int compare(ExerciseSet set1, ExerciseSet set2) {
        // sort by group
        if (set1.getExerciseOrder() < set2.getExerciseOrder()) {
            return -1;
        }

        if (set1.getExerciseOrder() > set2.getExerciseOrder()) {
            return 1;
        }

        // then sort within group
        return Integer.compare(set1.getOrder(), set2.getOrder());
    }
}
