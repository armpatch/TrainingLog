package com.armpatch.android.workouttracker.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.armpatch.android.workouttracker.MeasurementType;
import com.armpatch.android.workouttracker.R;
import com.armpatch.android.workouttracker.model.Category;
import com.armpatch.android.workouttracker.model.Exercise;
import com.armpatch.android.workouttracker.model.WorkoutRepository;

public class CreateExerciseActivity extends AppCompatActivity {

    TextView editName;
    Spinner categorySpinner;
    Button createButton;
    String name = "";
    Category category;

    Exercise newExercise;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_exercise);

        editName = findViewById(R.id.exercise_name_editor);
        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                name = s.toString();
            }
         });

        categorySpinner = findViewById(R.id.category_spinner);

        createButton = findViewById(R.id.create_exercise_button);
        createButton.setOnClickListener(v -> attemptToSaveExercise());
    }

    class SaveExerciseTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            attemptToSaveExercise();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            finish();
        }
    }

    void attemptToSaveExercise() {
        if (!exerciseCanBeSaved()) return;

        newExercise = new Exercise(name, MeasurementType.WEIGHT_AND_REPS, category);

        WorkoutRepository repository = new WorkoutRepository(this);
        repository.insert(newExercise);
    }

    boolean exerciseCanBeSaved() {
        if (name.trim().length() == 0){
            Toast.makeText(this, "Exercise needs a name.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (category == null) {
            Toast.makeText(this, "Choose a category.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (exerciseNameAlreadyExists()){
            Toast.makeText(this, "Duplicate exercise name.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    boolean exerciseNameAlreadyExists() {
        WorkoutRepository repository = new WorkoutRepository(this);
        Exercise existing = repository.getExercise(name);
        return (existing != null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
