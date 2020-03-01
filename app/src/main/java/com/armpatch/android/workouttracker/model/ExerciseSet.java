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
    private float weight;
    private int repetition;

    public ExerciseSet(String date,
                String exerciseName,
                       float weight,
                       int repetition,
                int order) {

        id = UUID.randomUUID().toString();
        this.date = date;
        this.exerciseName = exerciseName;
        this.order = order;
        comment = "";
        this.weight = weight;
        this.repetition = repetition;
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

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setRepetition(int repetition) {
        this.repetition = repetition;
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

    public float getWeight() {
        return weight;
    }

    public int getRepetition() {
        return repetition;
    }

}
