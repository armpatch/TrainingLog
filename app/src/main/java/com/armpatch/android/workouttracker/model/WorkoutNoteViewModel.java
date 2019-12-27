package com.armpatch.android.workouttracker.model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WorkoutNoteViewModel extends AndroidViewModel {

    private WorkoutRepository repository;

    private LiveData<List<WorkoutNote>> allWorkoutNotes;

    public WorkoutNoteViewModel(Application application) {
        super(application);
        repository = new WorkoutRepository(application);
        allWorkoutNotes = repository.getAllWorkoutNotes();
    }

    public LiveData<List<WorkoutNote>> getAllWorkoutNotes() { return allWorkoutNotes; }

    public void insert(WorkoutNote workoutNote) { repository.insert(workoutNote); }
}
