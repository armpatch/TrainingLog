package com.armpatch.android.workouttracker.views;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.armpatch.android.workouttracker.R;
import com.armpatch.android.workouttracker.model.Exercise;
import com.armpatch.android.workouttracker.model.ExerciseSet;
import com.armpatch.android.workouttracker.model.WorkoutRepository;

import java.util.List;

public class ExerciseTrackerActivity extends AppCompatActivity {

    public static String KEY_EXERCISE_NAME = "KEY_EXERCISE_NAME";

    TextView toolbarTitle;

    Exercise exercise;
    List<ExerciseSet> sets;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercise_tracker);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        toolbarTitle = findViewById(R.id.exercise_title);

        String exerciseName = getIntent().getStringExtra(KEY_EXERCISE_NAME);
        new GetExerciseTask(exerciseName).execute();
    }

    public static Intent newExerciseIntent(Context activityContext, String exerciseName) {
        Intent intent = new Intent(activityContext, ExerciseTrackerActivity.class);
        intent.putExtra(KEY_EXERCISE_NAME, exerciseName);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.exercise_tracker, menu);
        return true;
    }

    class GetExerciseTask extends AsyncTask<Void, Void, Void> {

        String name;

        private GetExerciseTask(String name) {
            this.name = name;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            WorkoutRepository repository = new WorkoutRepository(ExerciseTrackerActivity.this);
            exercise = repository.getExercise(name);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            toolbarTitle.setText(exercise.getName());
        }
    }
}
