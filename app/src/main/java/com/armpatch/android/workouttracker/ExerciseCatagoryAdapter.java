package com.armpatch.android.workouttracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.armpatch.android.workouttracker.model.Exercise;

import java.util.List;

public class ExerciseCatagoryAdapter extends RecyclerView.Adapter<ExerciseCatagoryAdapter.CategoryHolder> {

    Context activityContext;
    List<Exercise> exerciseList;

    public ExerciseCatagoryAdapter(Context activityContext, List<Exercise> exerciseList) {
        this.activityContext = activityContext;
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(activityContext)
                .inflate(R.layout.content_exercise_category, null);

        return new CategoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder categoryHolder, int position) {
        String categoryName = exerciseList.get(position).getName();
        categoryHolder.categoryName.setText(categoryName);
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder {

        TextView categoryName;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);

            categoryName = itemView.findViewById(R.id.category_name);
        }
    }
}
