package com.armpatch.android.workouttracker;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    TextView dateBarText;
    ViewPager viewPager;

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            updateDateBarText(position);
        }

        @Override
        public void onPageSelected(int position) { }

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
        WorkoutListAdapter adapter = new WorkoutListAdapter(this);
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

        if (item.getItemId() == R.id.edit_workout_note) {
            showWorkoutNoteDialog();
        }

        return true;
    }

    private void showWorkoutNoteDialog() {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialog_workout_note);

        final EditText editText = dialog.findViewById(R.id.edit_text);

        Button saveButton = dialog.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = editText.toString();
                // TODO save the note into workout notes
                dialog.dismiss();
            }
        });

        Button cancelButton = dialog.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();

    }

    private void updateDateBarText(int position) {
        int relativePosition = WorkoutListAdapter.getDaysFromToday(position);
        String day = Tools.relativeDateText(this, relativePosition);
        dateBarText.setText(day);
    }

    private void gotoToday() {
        viewPager.setCurrentItem(WorkoutListAdapter.STARTING_ITEM, false);
    }
}
