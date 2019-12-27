package com.armpatch.android.workouttracker.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

public class WorkoutRepository {

    private WorkoutNoteDao noteDao;
    private LiveData<WorkoutNote> workoutNote;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples

    public WorkoutRepository(Application application) {
        WorkoutRoomDatabase db = WorkoutRoomDatabase.getDatabase(application);
        noteDao = db.noteDao();
        workoutNote = noteDao.getWorkoutNote();
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
