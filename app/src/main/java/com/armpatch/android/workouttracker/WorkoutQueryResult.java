package com.armpatch.android.workouttracker;

public class WorkoutQueryResult {
    private String comment;

    //List<WorkoutSet> TODO: add this data type
    WorkoutQueryResult (String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }
}