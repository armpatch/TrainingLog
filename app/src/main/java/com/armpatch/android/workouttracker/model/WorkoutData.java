package com.armpatch.android.workouttracker.model;

import com.armpatch.android.workouttracker.Tools;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class WorkoutData {
    private String comment;
    private LocalDate date;
    public List<ExerciseSet> sets;

    public WorkoutData(LocalDate date, String comment) {
        this.date = date;
        this.comment = comment;
        sets = new ArrayList<>();
    }

    public WorkoutData(LocalDate date) {
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