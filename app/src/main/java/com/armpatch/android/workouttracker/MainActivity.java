package com.armpatch.android.workouttracker;

import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    TextView dateBarText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dateBarText = findViewById(R.id.date_bar_text);


        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        ViewPager viewPager = findViewById(R.id.view_pager);
        WorkoutListAdapter adapter = new WorkoutListAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(WorkoutListAdapter.STARTING_ITEM);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void updateDateBarText(int position) {
        int relativePosition = WorkoutListAdapter.relativePosition(position);
        String day = Tools.getRelativeDate(this, relativePosition);
        dateBarText.setText(day);
    }
}
