package com.armpatch.android.workouttracker;

import org.threeten.bp.LocalDate;

public class WorkoutData {
    private String comment;
    private LocalDate date;

    //List<WorkoutSet> TODO: add this data type
    WorkoutData(LocalDate date, String comment) {
        this.date = date;
        this.comment = comment;
    }

    WorkoutData(LocalDate date) {
        this(date, "");
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDateString() {
        return Tools.stringFromDate(date);
    }
}