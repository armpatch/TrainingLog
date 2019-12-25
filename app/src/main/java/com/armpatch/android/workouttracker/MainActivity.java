package com.armpatch.android.workouttracker;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import org.threeten.bp.LocalDate;

public class MainActivity extends AppCompatActivity {

    TextView dateBarText;
    ViewPager viewPager;
    LocalDate selectedDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        dateBarText = findViewById(R.id.date_bar_text);
        viewPager = findViewById(R.id.view_pager);

        setSupportActionBar(toolbar);

        dateBarText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoToday();
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        WorkoutListAdapter adapter = new WorkoutListAdapter(this);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                updateDateBarText(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
        int relativePosition = WorkoutListAdapter.relativePosition(position);
        String day = Tools.relativeDateText(this, relativePosition);
        dateBarText.setText(day);
    }

    private void gotoToday() {
        viewPager.setCurrentItem(WorkoutListAdapter.STARTING_ITEM, false);
    }
}
