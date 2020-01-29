package com.armpatch.android.workouttracker.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.armpatch.android.workouttracker.model.ExerciseSet;

import java.util.List;

public class TrackerSetAdapter extends RecyclerView.Adapter<TrackerSetAdapter.SetHolder> {

    private List<ExerciseSet> sets;

    TrackerSetAdapter(List<ExerciseSet> sets) {
        this.sets = sets;
    }

    @NonNull
    @Override
    public SetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SetHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class SetHolder extends RecyclerView.ViewHolder {

        public SetHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
