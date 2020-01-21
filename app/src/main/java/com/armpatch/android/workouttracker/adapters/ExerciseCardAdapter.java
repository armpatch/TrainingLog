package com.armpatch.android.workouttracker.adapters;


import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.armpatch.android.workouttracker.R;
import com.armpatch.android.workouttracker.model.Exercise;
import com.armpatch.android.workouttracker.model.ExerciseSet;
import com.armpatch.android.workouttracker.model.Workout;

import java.util.List;

public class ExerciseCardAdapter implements ListAdapter {

    private Context activityContext;
    String[] orderedExercises;

    public ExerciseCardAdapter(Context activityContext, Workout workout, List<ExerciseSet> sets) {
        this.activityContext = activityContext;

        orderedExercises = workout.getExerciseOrderArray();

        // query sets using orderedExercises in for loop
        // turn these groups into holders
        //


    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return orderedExercises.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(activityContext).inflate(R.layout.content_exercise_group, parent, false);

        return view;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return getCount() == 0;
    }



    class ExerciseCardHolder {
        View itemView;
        Exercise exercise;
        List<ExerciseSet> sets;
    }
}
