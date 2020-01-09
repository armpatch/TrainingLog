package com.armpatch.android.workouttracker.views;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.armpatch.android.workouttracker.R;
import com.armpatch.android.workouttracker.adapters.ExerciseCategoryAdapter;
import com.armpatch.android.workouttracker.model.ExerciseCategory;
import com.armpatch.android.workouttracker.model.WorkoutRepository;

import java.util.List;

public class AddExerciseActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ExerciseCategoryAdapter adapter;
    List<ExerciseCategory> categories;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new SetCategoryAdapterTask().execute();
    }

    class SetCategoryAdapterTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            WorkoutRepository repo = new WorkoutRepository(AddExerciseActivity.this);
            categories = repo.getCategories();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (adapter == null) {
                adapter = new ExerciseCategoryAdapter(AddExerciseActivity.this, categories);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.setCategories(categories);
                adapter.notifyDataSetChanged();
            }
        }
    }

}
