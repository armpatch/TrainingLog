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

public class ExerciseCategoryAdapter extends RecyclerView.Adapter<ExerciseCategoryAdapter.CategoryHolder> {

    Context activityContext;
    List<Exercise> exercises;

    public ExerciseCategoryAdapter(Context activityContext, List<Exercise> exercises) {
        this.activityContext = activityContext;
        this.exercises = exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activityContext)
                .inflate(R.layout.content_exercise_category, parent, false);

        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder categoryHolder, int position) {
        categoryHolder.bind(exercises.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder {

        String name;
        TextView nameTextView;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.category_name);
        }

        void bind(String name) {
            this.name = name;
            nameTextView.setText(name);
        }
    }
}
