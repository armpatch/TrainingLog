package com.armpatch.android.workouttracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.armpatch.android.workouttracker.R;
import com.armpatch.android.workouttracker.model.Exercise;

import java.util.List;

public class ExerciseSelectionAdapter extends RecyclerView.Adapter<ExerciseSelectionAdapter.ExerciseListItemHolder> {

    private Context activityContext;
    private List<Exercise> exercises;
    private Callback activityCallback;

    public interface Callback {
        void onExerciseHolderSelected(Exercise exercise);
    }

    public ExerciseSelectionAdapter(Context activityContext, List<Exercise> exercises) {
        this.activityContext = activityContext;
        this.exercises = exercises;
        activityCallback = (Callback) activityContext;
    }

    @NonNull
    @Override
    public ExerciseListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activityContext)
                .inflate(R.layout.list_item_exercise_selection, parent, false);

        return new ExerciseListItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseListItemHolder holder, int position) {
        holder.bind(exercises.get(position));
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    class ExerciseListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Exercise exercise;
        TextView nameTextView;

        ExerciseListItemHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.text_view);
            itemView.setOnClickListener(this);
        }

        void bind(Exercise exercise) {
            this.exercise = exercise;
            nameTextView.setText(exercise.getName());
        }

        @Override
        public void onClick(View v) {
            activityCallback.onExerciseHolderSelected(exercise);
        }
    }
}
