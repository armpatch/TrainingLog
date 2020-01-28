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
import androidx.viewpager.widget.ViewPager;

import com.armpatch.android.workouttracker.R;
import com.armpatch.android.workouttracker.Tools;
import com.armpatch.android.workouttracker.adapters.TrackerPagerAdapter;
import com.armpatch.android.workouttracker.model.Exercise;
import com.armpatch.android.workouttracker.model.ExerciseSet;
import com.armpatch.android.workouttracker.model.WorkoutRepository;

import org.threeten.bp.LocalDate;

import java.util.List;

import static com.armpatch.android.workouttracker.Tools.KEY_EXERCISE_DATE;
import static com.armpatch.android.workouttracker.Tools.KEY_EXERCISE_NAME;

public class ExerciseTrackerActivity extends AppCompatActivity {

    TextView toolbarTitle;
    ViewPager viewPager;

    String exerciseName;
    LocalDate currentDate;
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

        currentDate = Tools.dateFromString(getIntent().getStringExtra(KEY_EXERCISE_DATE));
        exerciseName = getIntent().getStringExtra(KEY_EXERCISE_NAME);

        toolbarTitle = findViewById(R.id.exercise_title);
        toolbarTitle.setText(exerciseName);

        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new TrackerPagerAdapter(this));

        new GetSetsTask().execute();
    }

    public static Intent getIntent(Context activityContext, String date, String exerciseName) {
        Intent intent = new Intent(activityContext, ExerciseTrackerActivity.class);
        intent.putExtra(KEY_EXERCISE_NAME, exerciseName);
        intent.putExtra(KEY_EXERCISE_DATE, date);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.exercise_tracker, menu);
        return true;
    }

    class GetSetsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            WorkoutRepository repository = new WorkoutRepository(ExerciseTrackerActivity.this);
            sets = repository.getExerciseSets(currentDate, exerciseName);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Todo update ui with existing sets and allow user to add sets
        }
    }

}
