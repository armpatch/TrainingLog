package com.armpatch.android.workouttracker.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "workout_comment_table")
public class WorkoutComment {

    @PrimaryKey
    @NonNull
    private String date;

    private String comments;


    public WorkoutComment(String date) {
        this.date = date;
        comments = "";
    }

    public boolean hasComments() {
        return comments.length() != 0;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    @NonNull
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}
