package com.armpatch.android.workouttracker.activity;

import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.armpatch.android.workouttracker.R;

public class CreateExerciseActivity extends AppCompatActivity {

    TextView editName;
    Spinner categorySpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_exercise);

        editName = findViewById(R.id.edit_name);
        categorySpinner = findViewById(R.id.category_spinner);
    }
}
