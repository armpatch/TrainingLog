package com.armpatch.android.workouttracker.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exercise_table")
public class Exercise {

    @PrimaryKey
    @NonNull
    private String name;

    @NonNull
    private UnitCombo unitCombo;

    @NonNull
    private Category category;

    private String notes;

    public Exercise(String name, UnitCombo unitCombo, Category category) {
        this.name = name;
        this.unitCombo = unitCombo;
        this.category = category;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public UnitCombo getUnitCombo() {
        return unitCombo;
    }

    @NonNull
    public Category getCategory() {
        return category;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
