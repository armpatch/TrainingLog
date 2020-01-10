package com.armpatch.android.workouttracker.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exercise_set_table")
public class ExerciseSet {

    @PrimaryKey
    public int id;

    @NonNull
    private String date;

    @NonNull
    private String exerciseId;

    private int exerciseOrder;

    private int order;

    private String comment;

    private float measurement1;
    private float measurement2;

    ExerciseSet(String date, String exerciseId) {
        this.date = date;
        this.exerciseId = exerciseId;
    }

    // setters

    void setExerciseOrder(int exerciseOrder) {
        this.exerciseOrder = exerciseOrder;
    }

    void setOrder(int order) {
        this.order = order;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    void setMeasurement1(float measurement1) {
        this.measurement1 = measurement1;
    }

    void setMeasurement2(float measurement2) {
        this.measurement2 = measurement2;
    }

    // getters

    public String getDate() {
        return this.date;
    }

    String getExerciseId() {
        return this.exerciseId;
    }

    int getExerciseOrder() {
        return this.exerciseOrder;
    }

    int getOrder() {
        return order;
    }

    public String getComment() {
        return comment;
    }

    float getMeasurement1() {
        return measurement1;
    }

    float getMeasurement2() {
        return measurement2;
    }

}
