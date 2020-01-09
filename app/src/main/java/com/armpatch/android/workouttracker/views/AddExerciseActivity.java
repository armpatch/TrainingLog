package com.armpatch.android.workouttracker.views;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.armpatch.android.workouttracker.R;
import com.armpatch.android.workouttracker.adapters.ExerciseCategoryAdapter;
import com.armpatch.android.workouttracker.adapters.ExerciseAdapter;
import com.armpatch.android.workouttracker.model.Exercise;
import com.armpatch.android.workouttracker.model.ExerciseCategory;
import com.armpatch.android.workouttracker.model.WorkoutRepository;

import java.util.List;

public class AddExerciseActivity extends AppCompatActivity implements ExerciseCategoryAdapter.Callback {

    RecyclerView recyclerView;
    ExerciseCategoryAdapter categoryAdapter;
    ExerciseAdapter exerciseAdapter;
    List<ExerciseCategory> categories;
    List<Exercise> exercises;

    @Override
    public void showExercisesFrom(ExerciseCategory category) {
        new SetExercisesAdapterTask(category).execute();
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
                categoryAdapter = new ExerciseCategoryAdapter(AddExerciseActivity.this, categories);
                recyclerView.setAdapter(categoryAdapter);
            } else {
                categoryAdapter.setCategories(categories);
                categoryAdapter.notifyDataSetChanged();
            }
        }

    }

    class SetExercisesAdapterTask extends AsyncTask<Void, Void, Void> {

        ExerciseCategory category;

        SetExercisesAdapterTask(ExerciseCategory category) {
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
}