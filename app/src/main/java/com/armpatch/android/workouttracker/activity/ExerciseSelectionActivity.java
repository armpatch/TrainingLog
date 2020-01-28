package com.armpatch.android.workouttracker.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.armpatch.android.workouttracker.R;
import com.armpatch.android.workouttracker.Tools;
import com.armpatch.android.workouttracker.adapters.CategorySelectionAdapter;
import com.armpatch.android.workouttracker.adapters.ExerciseSelectionAdapter;
import com.armpatch.android.workouttracker.model.Category;
import com.armpatch.android.workouttracker.model.Exercise;
import com.armpatch.android.workouttracker.model.WorkoutRepository;

import org.threeten.bp.LocalDate;

import java.util.List;

import static com.armpatch.android.workouttracker.Tools.KEY_EXERCISE_DATE;

public class ExerciseSelectionActivity extends AppCompatActivity
        implements CategorySelectionAdapter.Callback, ExerciseSelectionAdapter.Callback {

    RecyclerView recyclerView;
    CategorySelectionAdapter categorySelectionAdapter;
    ExerciseSelectionAdapter exerciseSelectionAdapter;
    List<Category> categories;
    List<Exercise> exercises;

    public static Intent getIntent(Context activityContext, LocalDate date) {
        Intent intent = new Intent(activityContext, ExerciseSelectionActivity.class);
        intent.putExtra(KEY_EXERCISE_DATE, Tools.stringFromDate(date));
        return intent;
    }

    @Override
    public void onCategoryHolderSelected(Category category) {
        new SetExercisesAdapterTask(category).execute();
    }

    @Override
    public void onExerciseHolderSelected(Exercise exercise) {
        String date = getIntent().getStringExtra(KEY_EXERCISE_DATE);
        Intent exerciseTracker = ExerciseTrackerActivity.getIntent(this, date, exercise.getName());
        startActivity(exerciseTracker);
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_exercise_selection);
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
            WorkoutRepository repo = new WorkoutRepository(ExerciseSelectionActivity.this);
            categories = repo.getCategories();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (categorySelectionAdapter == null) {
                categorySelectionAdapter = new CategorySelectionAdapter(ExerciseSelectionActivity.this, categories);
                recyclerView.setAdapter(categorySelectionAdapter);
            } else {
                categorySelectionAdapter.setCategories(categories);
                categorySelectionAdapter.notifyDataSetChanged();
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
            WorkoutRepository repo = new WorkoutRepository(ExerciseSelectionActivity.this);
            exercises = repo.getExercises(category);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            exerciseSelectionAdapter = new ExerciseSelectionAdapter(ExerciseSelectionActivity.this, exercises);
            recyclerView.setAdapter(exerciseSelectionAdapter);
        }
    }

    @Override
    public void onBackPressed() {
        if (recyclerView.getAdapter() == exerciseSelectionAdapter) {
            recyclerView.setAdapter(categorySelectionAdapter);
        } else {
            super.onBackPressed();
        }
    }
}