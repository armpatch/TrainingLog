package com.armpatch.android.workouttracker.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "workout_comment_table")
public class WorkoutComment {

    @PrimaryKey
    @NonNull
    private String date;

    private String text;

    public WorkoutComment(String date, String text) {
        this.date = date;
        this.text = text;
    }

    public String getDate() {
        return this.date;
    }

    public String getText(){
        return this.text;
    }
}