package com.armpatch.android.workouttracker.views;

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
import com.armpatch.android.workouttracker.adapters.CategoryAdapter;
import com.armpatch.android.workouttracker.adapters.ExerciseAdapter;
import com.armpatch.android.workouttracker.model.Category;
import com.armpatch.android.workouttracker.model.Exercise;
import com.armpatch.android.workouttracker.model.WorkoutRepository;

import org.threeten.bp.LocalDate;

import java.util.List;

import static com.armpatch.android.workouttracker.Tools.KEY_EXERCISE_DATE;

public class AddExerciseActivity extends AppCompatActivity
        implements CategoryAdapter.Callback, ExerciseAdapter.Callback {

    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    ExerciseAdapter exerciseAdapter;
    List<Category> categories;
    List<Exercise> exercises;

    LocalDate currentDate;

    public static Intent getIntent(Context activityContext, LocalDate date) {
        Intent intent = new Intent(activityContext, ExerciseTrackerActivity.class);
        intent.putExtra(KEY_EXERCISE_DATE, Tools.stringFromDate(date));
        return intent;
    }

    @Override
    public void onCategoryHolderSelected(Category category) {
        new SetExercisesAdapterTask(category).execute();
    }

    @Override
    public void onExerciseHolderSelected(Exercise exercise) {
        Intent intent = ExerciseTrackerActivity.getIntent(this, currentDate, exercise, null);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromIntent();

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

    private void getDataFromIntent() {
        Intent intent = getIntent();

        currentDate = Tools.dateFromString(intent.getStringExtra(KEY_EXERCISE_DATE));
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