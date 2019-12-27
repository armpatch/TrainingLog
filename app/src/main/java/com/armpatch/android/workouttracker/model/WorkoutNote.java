package com.armpatch.android.workouttracker.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "workout_note_table")
public class WorkoutNote {

    @PrimaryKey
    @NonNull
    private String date;

    @NonNull
    private String note;

    public WorkoutNote(String date, String word) {
        this.date = date;
        this.note = word;
    }

    public String getDate() {
        return this.date;
    }

    public String getNote(){
        return this.note;
    }
}