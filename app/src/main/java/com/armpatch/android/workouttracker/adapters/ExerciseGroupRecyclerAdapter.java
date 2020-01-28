package com.armpatch.android.workouttracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.armpatch.android.workouttracker.R;
import com.armpatch.android.workouttracker.model.ExerciseSet;
import com.armpatch.android.workouttracker.model.Workout;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class ExerciseGroupRecyclerAdapter extends RecyclerView.Adapter<ExerciseGroupRecyclerAdapter.ExerciseGroupHolder> {

    private Context activityContext;
    String[] orderedExercises;
    Hashtable<String, ArrayList<ExerciseSet>> setMap;

    public ExerciseGroupRecyclerAdapter(Context activityContext, Workout workout, List<ExerciseSet> sets) {
        this.activityContext = activityContext;

        if (!workout.isEmpty()) {
            orderedExercises = workout.getExerciseOrderArray();
            setMap = WorkoutSetSorter.getSortedTable(orderedExercises, sets);
        }
    }

    @NonNull
    @Override
    public ExerciseGroupRecyclerAdapter.ExerciseGroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(activityContext).inflate(R.layout.content_exercise_group, parent, false);

        return new ExerciseGroupHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseGroupRecyclerAdapter.ExerciseGroupHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return (orderedExercises == null)? 0 : orderedExercises.length;
    }

    class ExerciseGroupHolder extends RecyclerView.ViewHolder {
        List<ExerciseSet> sets;
        LinearLayout setsLayout;

        ExerciseGroupHolder(View itemView) {
            super(itemView);
            setsLayout = itemView.findViewById(R.id.sets_layout);
        }

        void bind(int position) {
            addExerciseSetViews(setMap.get(orderedExercises[position]));
        }

        void addExerciseSetViews(List<ExerciseSet> sets){
            this.sets = sets;

            for (ExerciseSet set : sets) {
                View setView = LayoutInflater.from(activityContext).inflate(
                        R.layout.content_historical_set, setsLayout, false);

                TextView weight = setView.findViewById(R.id.weight);
                weight.setText(String.valueOf(set.getMeasurement1()));

                TextView reps = setView.findViewById(R.id.reps);
                reps.setText(String.valueOf(set.getMeasurement2()));

                setsLayout.addView(setView);
            }
        }
    }
}
