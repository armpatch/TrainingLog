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

    public void setExerciseOrder (String[] names) {
        if (names.length == 0) return;

        StringBuilder sb = new StringBuilder();

        for (String name : names) {
            if (name.length() > 0) {
                sb.append(name).append(",");
            }
        }
        if (sb.length() > 0)
            sb.deleteCharAt(sb.length() - 1);
        exerciseOrder = sb.toString();
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

    public void swapExercises(int position1, int position2) {
        String[] names = getExerciseOrderArray();
        String temp = names[position1];
        names[position1] = names[position2];
        names[position2] = temp;
        setExerciseOrder(names);
    }

    void removeExercise(String exerciseName) {
        String[] names = getExerciseOrderArray();
        for (int i = 0; i < names.length; i++) {
            if (names[i].equals(exerciseName)) {
                names[i] = "";
            }
        }

        setExerciseOrder(names);
    }
}