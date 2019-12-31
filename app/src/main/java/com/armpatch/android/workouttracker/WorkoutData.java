package com.armpatch.android.workouttracker;

import org.threeten.bp.LocalDate;

public class WorkoutData {
    private String comment;
    private LocalDate date;

    //List<WorkoutSet> TODO: add this data type
    WorkoutData(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}