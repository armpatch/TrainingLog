package com.armpatch.android.workouttracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.armpatch.android.workouttracker.R;
import com.armpatch.android.workouttracker.Tools;
import com.armpatch.android.workouttracker.adapters.WorkoutContentAdapter;
import com.armpatch.android.workouttracker.adapters.WorkoutPagerAdapter;

import org.threeten.bp.LocalDate;

import static org.threeten.bp.temporal.ChronoUnit.DAYS;

public class WorkoutViewerActivity extends AppCompatActivity implements WorkoutContentAdapter.Callback{

    LocalDate selectedDate;
    TextView dateBarText;
    ViewPager workoutPager;
    WorkoutPagerAdapter workoutPagerAdapter;

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            selectedDate = workoutPagerAdapter.getItemDate(position);
            dateBarText.setText(Tools.getRelativeDateText(WorkoutViewerActivity.this, selectedDate));
        }

        @Override
        public void onPageScrollStateChanged(int state) { }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_workout_viewer);
        setupToolbar();
        setupDateBar();
        setupWorkoutPager();
        changeSelectedDay(LocalDate.now());
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void setupDateBar() {
        dateBarText = findViewById(R.id.date_bar_text);
        dateBarText.setOnClickListener(v -> changeSelectedDay(LocalDate.now()));

        ImageView leftArrow = findViewById(R.id.prev_day_arrow);
        leftArrow.setOnClickListener(v -> jumpToNextDay());

        ImageView rightArrow = findViewById(R.id.next_day_arrow);
        rightArrow.setOnClickListener(v -> jumpToPreviousDay());
    }

    private void setupWorkoutPager() {
        workoutPager = findViewById(R.id.view_pager);
        workoutPagerAdapter = new WorkoutPagerAdapter(this);
        workoutPager.setAdapter(workoutPagerAdapter);
        workoutPager.addOnPageChangeListener(pageChangeListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        workoutPagerAdapter.updateCurrentWorkoutHolder();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_workout_viewer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menu_item_add_exercise) {
            Intent addExerciseIntent = ExerciseSelectionActivity.getIntent(this, Tools.stringFromDate(selectedDate));
            startActivity(addExerciseIntent);
        } else if (itemId == R.id.menu_item_calendar){
            android.app.DatePickerDialog dialog = new android.app.DatePickerDialog(this);
            dialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
                LocalDate date = LocalDate.of(year, month + 1, dayOfMonth);
                changeSelectedDay(date);
            });
            dialog.show();
        }
        return true;
    }

    private void changeSelectedDay(LocalDate date) {
        int relativeDays = (int) LocalDate.now().until(date, DAYS );
        int position = WorkoutPagerAdapter.POSITION_TODAY + relativeDays;
        workoutPager.setCurrentItem(position);
    }

    private void jumpToPreviousDay() {
        workoutPager.setCurrentItem(workoutPager.getCurrentItem() + 1);
    }

    private void jumpToNextDay() {
        workoutPager.setCurrentItem(workoutPager.getCurrentItem() - 1);
    }

    @Override
    public void onExerciseGroupSelected(String exerciseName) {
        Intent exerciseTracker = ExerciseTrackerActivity.getIntent(
                this,
                Tools.stringFromDate(selectedDate),
                exerciseName);

        startActivity(exerciseTracker);
    }
}
