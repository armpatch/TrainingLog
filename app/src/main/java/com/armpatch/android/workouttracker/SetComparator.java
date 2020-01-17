package com.armpatch.android.workouttracker;

import com.armpatch.android.workouttracker.model.ExerciseSet;

import java.util.Comparator;

public class SetComparator {

    public static Comparator get() {
        return new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                ExerciseSet set1 = (ExerciseSet) o1;
                ExerciseSet set2 = (ExerciseSet) o2;

                // sort by group
                if (set1.getExerciseOrder() < set2.getExerciseOrder()) {
                    return -1;
                }

                if (set1.getExerciseOrder() > set2.getExerciseOrder()) {
                    return 1;
                }

                // sort by set order
                if (set1.getOrder() < set2.getOrder()) {
                    return -1;
                }

                return 1;
            }
        };
    }
}
