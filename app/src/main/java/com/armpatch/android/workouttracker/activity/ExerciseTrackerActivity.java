package com.armpatch.android.workouttracker.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.armpatch.android.workouttracker.R;
import com.armpatch.android.workouttracker.adapters.TrackerPagerAdapter;

import static com.armpatch.android.workouttracker.Tools.KEY_EXERCISE_DATE;
import static com.armpatch.android.workouttracker.Tools.KEY_EXERCISE_NAME;

public class ExerciseTrackerActivity extends AppCompatActivity {

    TextView toolbarTitle;
    ViewPager viewPager;
    TrackerPagerAdapter trackerPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_tracker);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String currentDate = getIntent().getStringExtra(KEY_EXERCISE_DATE);
        String exerciseName = getIntent().getStringExtra(KEY_EXERCISE_NAME);

        toolbarTitle = findViewById(R.id.exercise_title);
        toolbarTitle.setText(exerciseName);

        trackerPagerAdapter = new TrackerPagerAdapter(this, currentDate, exerciseName);

        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(trackerPagerAdapter);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}
