package com.armpatch.android.workouttracker.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.armpatch.android.workouttracker.R;
import com.armpatch.android.workouttracker.Tools;

public class CalendarActivity extends AppCompatActivity {

    static Intent getIntent(Context activityContext, String date) {
        Intent intent = new Intent(activityContext, CalendarActivity.class);
        intent.putExtra(Tools.KEY_EXERCISE_DATE, date);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calendar);
    }
}
