package com.armpatch.android.workouttracker.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.armpatch.android.workouttracker.R;

public class ExerciseTrackerActivity extends AppCompatActivity {

    public static String KEY_EXERCISE_NAME = "KEY_EXERCISE_NAME";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercise_tracker);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    public static Intent getIntent(Context activityContext, String exerciseName) {
        Intent intent = new Intent(activityContext, ExerciseTrackerActivity.class);
        intent.putExtra(KEY_EXERCISE_NAME, exerciseName);
        return intent;
    }
}
