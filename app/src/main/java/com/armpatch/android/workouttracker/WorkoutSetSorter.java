package com.armpatch.android.workouttracker;

import com.armpatch.android.workouttracker.model.ExerciseSet;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class WorkoutSetSorter {

    public static Hashtable<String, ArrayList<ExerciseSet>> getSortedTable (String[] orderedExercises, List<ExerciseSet> sets) {
        Hashtable<String, ArrayList<ExerciseSet>> setMap = new Hashtable<>();

        for (ExerciseSet set : sets) {
            sortSetByExercise(setMap, set);
        }

        for (String exercise : orderedExercises) {
            setMap.get(exercise).sort(new SetComparator());
        }

        return setMap;
    }

    private static void sortSetByExercise(Hashtable<String, ArrayList<ExerciseSet>> setMap, ExerciseSet set) {
        String name = set.getExerciseName();

        if (!setMap.containsKey(name)) {
            setMap.put(name, new ArrayList<ExerciseSet>());
        }

        setMap.get(name).add(set);
    }

}
