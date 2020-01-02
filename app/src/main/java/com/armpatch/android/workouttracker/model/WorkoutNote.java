package com.armpatch.android.workouttracker.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "workout_note_table")
public class WorkoutNote {

    @PrimaryKey
    @NonNull
    private String date;

    private String comment;

    public WorkoutNote(String date, String comment) {
        this.date = date;
        this.comment = comment;
    }

    public String getDate() {
        return this.date;
    }

    public String getComment(){
        return this.comment;
    }
}