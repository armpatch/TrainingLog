package com.armpatch.android.workouttracker.model;

import android.content.Context;

import com.armpatch.android.workouttracker.Tools;
import org.threeten.bp.LocalDate;

public class WorkoutRepository {

    private WorkoutNoteDao noteDao;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples

    public WorkoutRepository(Context application) {
        WorkoutRoomDatabase db = WorkoutRoomDatabase.getDatabase(application);
        noteDao = db.noteDao();
    }

    public WorkoutNote getWorkoutNote(LocalDate localDate) {
        String date = Tools.stringFromDate(localDate);
        return noteDao.getWorkoutNote(date);
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(final WorkoutNote note) {
        WorkoutRoomDatabase.databaseWriteExecutor.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        noteDao.insert(note);
                    }
                });
    }

}
