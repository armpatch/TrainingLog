package com.armpatch.android.workouttracker.views;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.armpatch.android.workouttracker.R;
import com.armpatch.android.workouttracker.adapters.CategoryAdapter;
import com.armpatch.android.workouttracker.adapters.ExerciseAdapter;
import com.armpatch.android.workouttracker.model.Category;
import com.armpatch.android.workouttracker.model.Exercise;
import com.armpatch.android.workouttracker.model.WorkoutRepository;

import java.util.List;

public class AddExerciseActivity extends AppCompatActivity
        implements CategoryAdapter.Callback, ExerciseAdapter.Callback {

    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    ExerciseAdapter exerciseAdapter;
    List<Category> categories;
    List<Exercise> exercises;

    @Override
    public void onCategoryHolderSelected(Category category) {
        new SetExercisesAdapterTask(category).execute();
    }

    @Override
    public void onExerciseHolderSelected(String exerciseName) {
        Intent intent = ExerciseTrackerActivity.getIntent(this, exerciseName);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_exercise);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new SetCategoryAdapterTask().execute();
    }

    class SetCategoryAdapterTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            WorkoutRepository repo = new WorkoutRepository(AddExerciseActivity.this);
            categories = repo.getCategories();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (categoryAdapter == null) {
                categoryAdapter = new CategoryAdapter(AddExerciseActivity.this, categories);
                recyclerView.setAdapter(categoryAdapter);
            } else {
                categoryAdapter.setCategories(categories);
                categoryAdapter.notifyDataSetChanged();
            }
        }

    }

    class SetExercisesAdapterTask extends AsyncTask<Void, Void, Void> {

        Category category;

        SetExercisesAdapterTask(Category category) {
            this.category = category;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            WorkoutRepository repo = new WorkoutRepository(AddExerciseActivity.this);
            exercises = repo.getExercises(category);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            exerciseAdapter = new ExerciseAdapter(AddExerciseActivity.this, exercises);
            recyclerView.setAdapter(exerciseAdapter);
        }
    }

    @Override
    public void onBackPressed() {
        if (recyclerView.getAdapter() == exerciseAdapter) {
            recyclerView.setAdapter(categoryAdapter);
        } else {
            super.onBackPressed();
        }
    }
}