package com.armpatch.android.workouttracker.adapters;


import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import com.armpatch.android.workouttracker.R;
import com.armpatch.android.workouttracker.model.Exercise;
import com.armpatch.android.workouttracker.model.ExerciseSet;
import com.armpatch.android.workouttracker.model.Workout;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class ExerciseViewAdapter implements ListAdapter {

    private Context activityContext;
    String[] orderedExercises;
    Hashtable<String, ArrayList<ExerciseSet>> setMap;

    public ExerciseViewAdapter(Context activityContext, Workout workout, List<ExerciseSet> sets) {
        this.activityContext = activityContext;

        orderedExercises = workout.getExerciseOrderArray();

        setMap = WorkoutSetSorter.getSortedTable(orderedExercises, sets);

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
        View v = LayoutInflater.from(activityContext).inflate(R.layout.content_exercise_group, parent, false);

        ExerciseGroupHolder viewHolder = new ExerciseGroupHolder(v);
        viewHolder.addSets(setMap.get(orderedExercises[position]));

        return viewHolder.itemView;
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



    class ExerciseGroupHolder {
        final private View itemView;
        Exercise exercise;
        List<ExerciseSet> sets;
        LinearLayout setsLayout;

        ExerciseGroupHolder(View itemView) {
            this.itemView = itemView;
            setsLayout = itemView.findViewById(R.id.sets_layout);
        }

        void addSets(List<ExerciseSet> sets){
            this.sets = sets;

            for (ExerciseSet set : sets) {
                View setView = LayoutInflater.from(activityContext).inflate(R.layout.content_tracker_set, null);

                setsLayout.addView(setView);
            }

        }


    }
}
