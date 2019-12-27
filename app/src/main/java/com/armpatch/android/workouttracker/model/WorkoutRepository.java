package com.armpatch.android.workouttracker.model;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WorkoutRepository {

    private WorkoutNoteDao noteDao;
    private LiveData<List<WorkoutNote>> allWorkoutNotes;


    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples

    public WorkoutRepository(Context application) {
        WorkoutRoomDatabase db = WorkoutRoomDatabase.getDatabase(application);
        noteDao = db.noteDao();
        allWorkoutNotes = noteDao.getWorkoutNotes();
    }

    LiveData<List<WorkoutNote>> getAllWorkoutNotes() {
        return allWorkoutNotes;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(final WorkoutNote note) {
        WorkoutRoomDatabase.databaseWriteExecutor.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        noteDao.insert(note);
                    }
                });
    }

}
