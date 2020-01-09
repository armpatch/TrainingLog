package com.armpatch.android.workouttracker.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.armpatch.android.workouttracker.R;
import com.armpatch.android.workouttracker.Tools;
import com.armpatch.android.workouttracker.adapters.WorkoutAdapter;

import org.threeten.bp.LocalDate;

public class MainActivity extends AppCompatActivity {

    TextView dateBarText;
    ViewPager viewPager;
    LocalDate selectedDate;

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            selectedDate = (LocalDate.now().plusDays(WorkoutAdapter.relativeDays(position)));
            dateBarText.setText(Tools.relativeDateText(MainActivity.this, WorkoutAdapter.relativeDays(position)));
        }

        @Override
        public void onPageScrollStateChanged(int state) { }
    };
    private View.OnClickListener gotoToday = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            gotoToday();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        dateBarText = findViewById(R.id.date_bar_text);
        dateBarText.setOnClickListener(gotoToday);

        viewPager = findViewById(R.id.view_pager);
        WorkoutAdapter adapter = new WorkoutAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(pageChangeListener);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.add_exercise) {
            Intent addExerciseIntent = new Intent(this, AddExerciseActivity.class);
            startActivity(addExerciseIntent);
        }

        return true;
    }

    private void gotoToday() {
        viewPager.setCurrentItem(WorkoutAdapter.STARTING_ITEM, false);
    }
}
