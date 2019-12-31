package com.armpatch.android.workouttracker;

import android.content.Context;
import android.os.AsyncTask;

import androidx.viewpager.widget.ViewPager;

import com.armpatch.android.workouttracker.model.WorkoutNote;
import com.armpatch.android.workouttracker.model.WorkoutRepository;

public class UpdateWorkoutTask extends AsyncTask<WorkoutData, Integer, Void> {

    private Context activityContext;
    ViewPager workoutPager;

    public UpdateWorkoutTask (Context activityContext, ViewPager workoutPager) {
        this.activityContext = activityContext;
        this.workoutPager = workoutPager;
    }

    @Override
    protected Void doInBackground(WorkoutData... workoutData) {
        WorkoutRepository repository = new WorkoutRepository(activityContext);
        WorkoutData workoutData0 = workoutData[0];
        String currentDate = Tools.stringFromDate(workoutData0.getDate());
        WorkoutNote workoutNote = new WorkoutNote(currentDate, workoutData0.getComment());
        repository.insert(workoutNote);
        return null;
    }
}
