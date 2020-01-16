package com.armpatch.android.workouttracker.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "exercise_set_table")
public class ExerciseSet {

    @PrimaryKey
    public String id;

    @NonNull
    private String date;

    @NonNull
    private String exerciseName;

    private int exerciseOrder;

    private int order;

    private String comment;

    private float measurement1;
    private float measurement2;

    ExerciseSet(String date, String exerciseId, float measurement1, float measurement2) {
        this.date = date;
        this.exerciseName = exerciseId;
        this.measurement1 = measurement1;
        this.measurement2 = measurement2;
        id = UUID.randomUUID().toString();
    }

    ExerciseSet(String date, String exerciseId) {
        this.date = date;
        this.exerciseName = exerciseId;
        id = UUID.randomUUID().toString();
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

    String getExerciseName() {
        return this.exerciseName;
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
