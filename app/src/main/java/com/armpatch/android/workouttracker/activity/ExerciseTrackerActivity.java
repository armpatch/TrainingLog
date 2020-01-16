package com.armpatch.android.workouttracker.activity;

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
import com.armpatch.android.workouttracker.Tools;
import com.armpatch.android.workouttracker.model.Exercise;
import com.armpatch.android.workouttracker.model.ExerciseSet;
import com.armpatch.android.workouttracker.model.WorkoutRepository;

import org.threeten.bp.LocalDate;

import java.util.List;

import static com.armpatch.android.workouttracker.Tools.KEY_EXERCISE_DATE;
import static com.armpatch.android.workouttracker.Tools.KEY_EXERCISE_NAME;
import static com.armpatch.android.workouttracker.Tools.KEY_ORDER_IN_WORKOUT;

public class ExerciseTrackerActivity extends AppCompatActivity {

    TextView toolbarTitle;

    String exerciseName;
    Exercise exercise;
    LocalDate currentDate;
    Integer exerciseOrderInWorkout;
    List<ExerciseSet> sets;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_tracker);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        toolbarTitle = findViewById(R.id.exercise_title);

        getDataFromIntent();
        new GetExerciseFromNameTask(exerciseName).execute();

        if (exerciseOrderInWorkout != null) {
            new GetWorkoutSetsTask().execute();
        } else {
            new GetNextExerciseOrder().execute();
        }

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();

        exerciseName = intent.getStringExtra(KEY_EXERCISE_NAME);
        currentDate = Tools.dateFromString(intent.getStringExtra(KEY_EXERCISE_DATE));

    }
    public static Intent getIntent(Context activityContext, LocalDate date, Exercise exercise, Integer exerciseOrderInWorkout) {
        Intent intent = new Intent(activityContext, ExerciseTrackerActivity.class);
        intent.putExtra(KEY_EXERCISE_NAME, exercise.getName());
        intent.putExtra(KEY_EXERCISE_DATE, Tools.stringFromDate(date));
        intent.putExtra(KEY_ORDER_IN_WORKOUT, exerciseOrderInWorkout);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.exercise_tracker, menu);
        return true;
    }

    class GetExerciseFromNameTask extends AsyncTask<Void, Void, Void> {

        String name;

        private GetExerciseFromNameTask(String name) {
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

    class GetWorkoutSetsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            WorkoutRepository repository = new WorkoutRepository(ExerciseTrackerActivity.this);
            sets = repository.getExerciseSets(currentDate, exerciseOrderInWorkout);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Todo update ui with existing sets and allow user to add sets
        }
    }

    class GetNextExerciseOrder extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            WorkoutRepository repository = new WorkoutRepository(ExerciseTrackerActivity.this);
            exerciseOrderInWorkout = repository.getExerciseCount(currentDate) + 1;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Todo allow user to add sets
        }
    }

}
