package com.armpatch.android.workouttracker;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.armpatch.android.workouttracker.model.Exercise;
import com.armpatch.android.workouttracker.model.WorkoutRepository;

import java.util.List;

public class AddExerciseActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ExerciseCategoryAdapter adapter;
    List<Exercise> exerciseList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_exercise);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        recyclerView = findViewById(R.id.recycler_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new UpdateRecyclerTask().execute();
    }

    class UpdateRecyclerTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            WorkoutRepository repo = new WorkoutRepository(AddExerciseActivity.this);
            exerciseList = repo.getAllExercises();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (adapter == null) {
                adapter = new ExerciseCategoryAdapter(AddExerciseActivity.this, exerciseList);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.setExercises(exerciseList);
                adapter.notifyDataSetChanged();
            }
            toastThis("adapter length = " + adapter.getItemCount());
        }
    }

    private void toastThis(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
