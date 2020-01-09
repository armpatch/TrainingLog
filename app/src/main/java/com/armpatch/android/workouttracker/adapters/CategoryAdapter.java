package com.armpatch.android.workouttracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.armpatch.android.workouttracker.R;
import com.armpatch.android.workouttracker.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    Context activityContext;
    List<Category> categories;
    Callback callback;

    public interface Callback {
        void showExercisesFrom(Category category);
    }

    public CategoryAdapter(Context activityContext, List<Category> categories) {
        this.activityContext = activityContext;
        this.categories = categories;
        callback = (Callback) activityContext;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activityContext)
                .inflate(R.layout.content_category, parent, false);

        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder categoryHolder, int position) {
        categoryHolder.bind(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Category category;
        TextView nameTextView;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.category_name);
            itemView.setOnClickListener(this);
        }

        void bind(Category category) {
            this.category = category;
            nameTextView.setText(category.getName());
        }

        @Override
        public void onClick(View v) {
            callback.showExercisesFrom(category);
        }
    }
}
