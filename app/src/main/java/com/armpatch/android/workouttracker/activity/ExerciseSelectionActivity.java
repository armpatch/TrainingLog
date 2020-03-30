package com.armpatch.android.workouttracker.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.armpatch.android.workouttracker.R;
import com.armpatch.android.workouttracker.adapters.CategorySelectionAdapter;
import com.armpatch.android.workouttracker.adapters.ExerciseSelectionAdapter;
import com.armpatch.android.workouttracker.model.Category;
import com.armpatch.android.workouttracker.model.Exercise;
import com.armpatch.android.workouttracker.model.WorkoutRepository;

import java.util.List;

import static com.armpatch.android.workouttracker.Tools.KEY_EXERCISE_DATE;

public class ExerciseSelectionActivity extends AppCompatActivity
        implements CategorySelectionAdapter.Callback, ExerciseSelectionAdapter.Callback {

    RecyclerView recyclerView;
    CategorySelectionAdapter categorySelectionAdapter;
    ExerciseSelectionAdapter exerciseSelectionAdapter;
    List<Category> categories;
    List<Exercise> exercises;
    TextView toolbarTitle;

    public static Intent getIntent(Context activityContext, String date) {
        Intent intent = new Intent(activityContext, ExerciseSelectionActivity.class);
        intent.putExtra(KEY_EXERCISE_DATE, date);
        return intent;
    }

    @Override
    public void onCategoryHolderSelected(Category category) {
        toolbarTitle.setText(category.getName());
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
        toolbarTitle = findViewById(R.id.toolbar_title);
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
            toolbarTitle.setText(getResources().getText(R.string.all_exercises_title));
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_exercise_selection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menu_item_create_exercise) {
            Intent i = new Intent(this, CreateExerciseActivity.class);
            startActivity(i);
        }

        return true;
    }
}