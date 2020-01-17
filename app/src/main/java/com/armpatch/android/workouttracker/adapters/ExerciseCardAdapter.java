package com.armpatch.android.workouttracker.adapters;


import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.armpatch.android.workouttracker.R;
import com.armpatch.android.workouttracker.SetComparator;
import com.armpatch.android.workouttracker.model.Exercise;
import com.armpatch.android.workouttracker.model.ExerciseSet;

import java.util.List;

public class ExerciseCardAdapter implements ListAdapter {

    Context activityContext;
    List<ExerciseSet> sets;

    public ExerciseCardAdapter(Context activityContext, List<ExerciseSet> sets) {
        this.sets = sets;
        this.activityContext = activityContext;

        // TODO separate sets into groups

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
        return sets.size();
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
        View view = LayoutInflater.from(activityContext).inflate(R.layout.content_tracker_set, parent, false);

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
        return false;
    }

    void sortSets(List<ExerciseSet> sets) {
        sets.sort(SetComparator.get());


    }

    class ExerciseCardHolder {
        View itemView;
        Exercise exercise;
        int orderInWorkout;
        List<ExerciseSet> sets;
    }

    class GroupData {
        Exercise exercise;
        int orderInWorkout;
        List<ExerciseSet> sets;
    }
}
