package com.armpatch.android.workouttracker.model;

import android.content.Context;

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

        repo.insert(set);
    }
    void deleteSet(ExerciseSet set) {}
    void updateSet(ExerciseSet set) {}
    void deleteExercise(String name) {}
    void swapExercisePosition(int position1, int position2) {}

}
