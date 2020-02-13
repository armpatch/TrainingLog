package com.armpatch.android.workouttracker.model;

import android.content.Context;

import com.armpatch.android.workouttracker.SetComparator;

import java.util.ArrayList;
import java.util.List;

public class WorkoutEditorHelper {

    private WorkoutRepository repo;

    public WorkoutEditorHelper(Context activityContext) {
        repo = new WorkoutRepository(activityContext);
    }

    public void addSet(ExerciseSet set) {
        ExerciseOrder exerciseOrder = repo.getExerciseOrder(set.getDate());

        if (exerciseOrder == null) {
            exerciseOrder = new ExerciseOrder(set.getDate());
        }

        // checks if this is the first set of this exercise
        if (!exerciseOrder.containsExercise(set.getExerciseName())) {
            exerciseOrder.appendExercise(set.getExerciseName());
            repo.insert(exerciseOrder);
        }

        int order = repo.getSetCount(set.getDate(), set.getExerciseName()) + 1;
        set.setOrder(order);
        repo.insert(set);
    }
    public void deleteSet(ExerciseSet set) {
        repo.delete(set);

        List<ExerciseSet> remainingSets = repo.getExerciseSets(set.getDate(), set.getExerciseName());

        if (remainingSets.size() == 0) {
            deleteExerciseGroup(set);
        } else {
            // update order of sets
            remainingSets.sort(new SetComparator());

            int index = 1;
            for (ExerciseSet current : remainingSets) {
                current.setOrder(index);
                index++;
            }

            repo.update(remainingSets);
        }
    }

    void deleteExerciseGroup(ExerciseSet set) {
        ExerciseOrder exerciseOrder = repo.getExerciseOrder(set.getDate());

        exerciseOrder.removeExercise(set.getExerciseName());
        repo.insert(exerciseOrder);
    }

    public void updateSet(ExerciseSet set) {
        ArrayList<ExerciseSet> sets = new ArrayList<>();
        sets.add(set);
        repo.update(sets);
    }

    public void swapExerciseOrder(String date, int position1, int position2) {
        ExerciseOrder exerciseOrder = repo.getExerciseOrder(date);
        exerciseOrder.swapExercises(position1, position2);

        repo.insert(exerciseOrder);
    }

}
