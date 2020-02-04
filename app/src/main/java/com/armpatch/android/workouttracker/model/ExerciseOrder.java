package com.armpatch.android.workouttracker.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;

@Entity(tableName = "exercise_order_table")
public class ExerciseOrder {

    @PrimaryKey
    @NonNull
    private String date;

    private String exerciseOrder;

    public ExerciseOrder(String date) {
        this.date = date;
        exerciseOrder = "";
    }

    public boolean isEmpty() {
        return exerciseOrder.length() == 0;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    @NonNull

    public String getExerciseOrder() {
        return exerciseOrder;
    }

    public String[] getExerciseOrderArray() {
        return exerciseOrder.split(",");
    }

    public void setExerciseOrder(String exerciseOrder) {
        this.exerciseOrder = exerciseOrder;
    }

    public void appendExercise(String exerciseName) {
        if (exerciseOrder.length() > 0) {
            exerciseOrder = exerciseOrder.concat(",");
        }
        exerciseOrder = exerciseOrder.concat(exerciseName);
    }

    public boolean containsExercise(String exerciseName) {
        if (isEmpty()) return false;
        String[] names = getExerciseOrderArray();

        return  (Arrays.asList(names).contains(exerciseName));
    }
}
