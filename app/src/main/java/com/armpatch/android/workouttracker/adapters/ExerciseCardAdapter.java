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

import java.util.ArrayList;
import java.util.List;

public class ExerciseCardAdapter implements ListAdapter {

    Context activityContext;
    List<GroupData> groupDataList;

    public ExerciseCardAdapter(Context activityContext, List<ExerciseSet> sets) {
        this.activityContext = activityContext;

        groupDataList = createGroupsDataListFrom(sets);
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
        return groupDataList.size();
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

    private List<GroupData> createGroupsDataListFrom(List<ExerciseSet> sets) {
        sets.sort(SetComparator.get());
        List<GroupData> groupDataList = new ArrayList<>();

        int prevSetsGroupNumber = 1;
        GroupData currentGroupData = null;

        for (ExerciseSet set : sets) {
            if (currentGroupData == null || set.getExerciseOrder() != prevSetsGroupNumber){
                groupDataList.add(currentGroupData);
                currentGroupData = new GroupData(set.getExerciseName(), set.getExerciseOrder());
            }

            currentGroupData.sets.add(set);
            prevSetsGroupNumber = set.getExerciseOrder();
        }
        groupDataList.add(currentGroupData);

        return groupDataList;
    }

    class ExerciseCardHolder {
        View itemView;
        Exercise exercise;
        int orderInWorkout;
        List<ExerciseSet> sets;
    }

    class GroupData {
        private String exercise;
        private int groupNumber;
        List<ExerciseSet> sets;

        GroupData(String exercise, int groupNumber) {
            this.exercise = exercise;
            this.groupNumber = groupNumber;
        }
    }
}
