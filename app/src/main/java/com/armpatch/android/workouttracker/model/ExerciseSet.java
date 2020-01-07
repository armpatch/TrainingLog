package com.armpatch.android.workouttracker.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "exercise_set_table")
public class ExerciseSet {

    @NonNull
    private String date;

    @NonNull
    private int exerciseId;

    @NonNull
    private int exerciseOrder;

    @NonNull
    private int order;

    private String comment;

    @NonNull
    private float measurement1;
    private float measurement2;
    private float measurement3;

    public ExerciseSet(String date, int exerciseId) {
        this.date = date;
        this.exerciseId = exerciseId;
    }

    // setters

    public void setExerciseOrder(int exerciseOrder) {
        this.exerciseOrder = exerciseOrder;
    }

    public void setOrder(int order) {
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

    public void setMeasurement3(float measurement3) {
        this.measurement3 = measurement3;
    }

    // getters

    public String getDate() {
        return this.date;
    }

    public int getExerciseId() {
        return this.exerciseId;
    }

    public int getExerciseOrder() {
        return this.exerciseOrder;
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

    public float getMeasurement3() {
        return measurement3;
    }
}
