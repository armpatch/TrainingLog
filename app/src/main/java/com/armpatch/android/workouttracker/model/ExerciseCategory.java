package com.armpatch.android.workouttracker.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exercise_category_table")
public class ExerciseCategory {

    @PrimaryKey
    private String name;

    ExerciseCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
