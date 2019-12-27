package com.armpatch.android.workouttracker;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.armpatch.android.workouttracker.model.WorkoutNote;
import com.armpatch.android.workouttracker.model.WorkoutNoteViewModel;

import org.threeten.bp.LocalDate;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView dateBarText;
    ViewPager viewPager;
    LocalDate selectedDate;
    WorkoutNoteViewModel workoutNoteViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        dateBarText = findViewById(R.id.date_bar_text);
        viewPager = findViewById(R.id.view_pager);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        dateBarText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoToday();
            }
        });

        final WorkoutListAdapter adapter = new WorkoutListAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                updateDateBarText(position);
            }

            @Override
            public void onPageSelected(int position) { }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
        workoutNoteViewModel = new ViewModelProvider(this).get(WorkoutNoteViewModel.class);
        workoutNoteViewModel.getAllWorkoutNotes().observe(this, new Observer<List<WorkoutNote>>() {
            @Override
            public void onChanged(List<WorkoutNote> workoutNotes) {
                adapter.setWorkoutNotes(workoutNotes);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        gotoToday();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void updateDateBarText(int position) {
        int relativePosition = WorkoutListAdapter.relativeDay(position);
        String day = Tools.relativeDateText(this, relativePosition);
        dateBarText.setText(day);
    }

    private void gotoToday() {
        viewPager.setCurrentItem(WorkoutListAdapter.STARTING_ITEM, false);
    }
}
