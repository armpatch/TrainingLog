package com.armpatch.android.workouttracker.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "workout_table")
public class Workout {

    @PrimaryKey
    @NonNull
    private String date;

    private String comments;

    private String exerciseOrder;

    public Workout(String date) {
        this.date = date;
        comments = "";
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getExerciseOrder() {
        return exerciseOrder;
    }

    public void setExerciseOrder(String exerciseOrder) {
        this.exerciseOrder = exerciseOrder;
    }
}
