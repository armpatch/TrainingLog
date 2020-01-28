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

public class CategorySelectionAdapter extends RecyclerView.Adapter<CategorySelectionAdapter.CategoryHolder> {

    private Context activityContext;
    private List<Category> categories;
    private Callback activityCallback;

    public interface Callback {
        void onCategoryHolderSelected(Category category);
    }

    public CategorySelectionAdapter(Context activityContext, List<Category> categories) {
        this.activityContext = activityContext;
        this.categories = categories;
        activityCallback = (Callback) activityContext;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activityContext)
                .inflate(R.layout.list_item_exercise_selection, parent, false);

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

        CategoryHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.text_view);
            itemView.setOnClickListener(this);
        }

        void bind(Category category) {
            this.category = category;
            nameTextView.setText(category.getName());
        }

        @Override
        public void onClick(View v) {
            activityCallback.onCategoryHolderSelected(category);
        }
    }
}
