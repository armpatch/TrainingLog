package com.armpatch.android.workouttracker.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exercise_table")
public class Exercise {

    @PrimaryKey
    @NonNull
    private String name;

    private int measurementType;

    @NonNull
    private Category category;

    private String notes;

    public Exercise(String name, int measurementType, Category category) {
        this.name = name;
        this.measurementType = measurementType;
        this.category = category;
    }

    @NonNull
    public String getName() {
        return name;
    }

    int getMeasurementType() {
        return measurementType;
    }

    @NonNull
    public Category getCategory() {
        return category;
    }

    String getNotes() {
        return notes;
    }

    void setNotes(String notes) {
        this.notes = notes;
    }

}
