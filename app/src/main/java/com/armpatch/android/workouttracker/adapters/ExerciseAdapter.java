package com.armpatch.android.workouttracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.armpatch.android.workouttracker.R;
import com.armpatch.android.workouttracker.model.Exercise;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseHolder> {

    private Context activityContext;
    private List<Exercise> exercises;
    private Callback activityCallback;

    public interface Callback {
        void onExerciseHolderSelected(String exerciseName);
    }

    public ExerciseAdapter(Context activityContext, List<Exercise> exercises) {
        this.activityContext = activityContext;
        this.exercises = exercises;
        activityCallback = (Callback) activityContext;
    }

    @NonNull
    @Override
    public ExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activityContext)
                .inflate(R.layout.content_category_list_item, parent, false);

        return new ExerciseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseHolder holder, int position) {
        holder.bind(exercises.get(position));
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    class ExerciseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Exercise exercise;
        TextView nameTextView;
        ImageView expandCollapseIcon;

        ExerciseHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.text_view);
            expandCollapseIcon = itemView.findViewById(R.id.expand_collapse_icon);
            expandCollapseIcon.setVisibility(View.GONE);
            itemView.setOnClickListener(this);
        }

        void bind(Exercise exercise) {
            this.exercise = exercise;
            nameTextView.setText(exercise.getName());
        }

        @Override
        public void onClick(View v) {
            activityCallback.onExerciseHolderSelected(exercise.getName());
        }
    }
}
