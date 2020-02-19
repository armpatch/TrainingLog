package com.armpatch.android.workouttracker;

import com.armpatch.android.workouttracker.model.ExerciseOrder;
import com.armpatch.android.workouttracker.model.ExerciseSet;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class WorkoutSetSorter {

    public static Hashtable<String, ArrayList<ExerciseSet>> getSortedTable (ExerciseOrder exerciseOrder, List<ExerciseSet> sets) {
        Hashtable<String, ArrayList<ExerciseSet>> setMap = new Hashtable<>();

        if (sets.size() == 0) {
            return setMap;
        }

        for (ExerciseSet set : sets) {
            putSetIntoMap(setMap, set);
        }

        for (String exercise : exerciseOrder.toArray()) {
            setMap.get(exercise).sort(new SetComparator());
        }

        return setMap;
    }

    private static void putSetIntoMap(Hashtable<String, ArrayList<ExerciseSet>> setMap, ExerciseSet set) {
        String name = set.getExerciseName();

        if (!setMap.containsKey(name)) {
            setMap.put(name, new ArrayList<>());
        }

        setMap.get(name).add(set);
    }

}
