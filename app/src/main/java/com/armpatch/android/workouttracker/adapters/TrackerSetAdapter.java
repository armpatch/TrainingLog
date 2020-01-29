package com.armpatch.android.workouttracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.armpatch.android.workouttracker.R;
import com.armpatch.android.workouttracker.model.ExerciseSet;

import java.util.List;

public class TrackerSetAdapter extends RecyclerView.Adapter<TrackerSetAdapter.SetHolder> {

    private Context activityContext;
    private List<ExerciseSet> sets;

    TrackerSetAdapter(Context activityContext, List<ExerciseSet> sets) {
        this.sets = sets;
        this.activityContext = activityContext;
    }

    @NonNull
    @Override
    public SetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activityContext)
                .inflate(R.layout.list_item_set_tracker, parent, false);

        return new SetHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SetHolder holder, int position) {
        holder.bind(sets.get(position));
    }

    @Override
    public int getItemCount() {
        return sets.size();
    }

    class SetHolder extends RecyclerView.ViewHolder {

        TextView setNumberText;
        TextView weightText;
        TextView repsText;

        SetHolder(@NonNull View itemView) {
            super(itemView);

            setNumberText = itemView.findViewById(R.id.set_number);
            weightText = itemView.findViewById(R.id.weight);
            repsText = itemView.findViewById(R.id.reps);
        }

        void bind(ExerciseSet set) {
            setNumberText.setText(String.valueOf(set.getOrder()));

            weightText.setText(activityContext.getString(R.string.weight_lbs, set.getMeasurement1()));
            repsText.setText(activityContext.getString(R.string.reps, set.getMeasurement2()));
        }
    }
}
