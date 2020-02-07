package com.armpatch.android.workouttracker.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "exercise_set_table")
public class ExerciseSet {

    @NonNull
    @PrimaryKey
    public String id;

    @NonNull
    private String date;

    @NonNull
    private String exerciseName;

    private int order;
    private String comment;
    private float measurement1;
    private float measurement2;

    public ExerciseSet(String date,
                String exerciseName,
                float measurement1,
                float measurement2,
                int order) {

        id = UUID.randomUUID().toString();
        this.date = date;
        this.exerciseName = exerciseName;
        this.order = order;
        comment = "";
        this.measurement1 = measurement1;
        this.measurement2 = measurement2;
    }

    // setters

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    void setOrder(int order) {
        this.order = order;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setMeasurement1(float measurement1) {
        this.measurement1 = measurement1;
    }

    public void setMeasurement2(float measurement2) {
        this.measurement2 = measurement2;
    }

    // getters

    public String getDate() {
        return this.date;
    }

    public String getExerciseName() {
        return this.exerciseName;
    }

    public int getOrder() {
        return order;
    }

    public String getComment() {
        return comment;
    }

    public float getMeasurement1() {
        return measurement1;
    }

    public float getMeasurement2() {
        return measurement2;
    }

}
