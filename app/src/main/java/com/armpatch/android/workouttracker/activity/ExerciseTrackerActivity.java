package com.armpatch.android.workouttracker.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.armpatch.android.workouttracker.R;
import com.armpatch.android.workouttracker.adapters.TrackerPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import static com.armpatch.android.workouttracker.Tools.KEY_EXERCISE_DATE;
import static com.armpatch.android.workouttracker.Tools.KEY_EXERCISE_NAME;

public class ExerciseTrackerActivity extends AppCompatActivity {

    TextView toolbarTitle;
    TabLayout tabLayout;
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

        toolbarTitle = findViewById(R.id.date_title);
        toolbarTitle.setText(exerciseName);

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        trackerPagerAdapter = new TrackerPagerAdapter(this, currentDate, exerciseName);

        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(trackerPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 1) {
                    trackerPagerAdapter.loadHistoryPage();
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public static Intent getIntent(Context activityContext, String date, String exerciseName) {
        Intent intent = new Intent(activityContext, ExerciseTrackerActivity.class);
        intent.putExtra(KEY_EXERCISE_NAME, exerciseName);
        intent.putExtra(KEY_EXERCISE_DATE, date);
        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}
