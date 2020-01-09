package com.armpatch.android.workouttracker.model;

import android.content.Context;

import com.armpatch.android.workouttracker.Tools;
import org.threeten.bp.LocalDate;

import java.util.List;

public class WorkoutRepository {

    private WorkoutCommentDao commentDao;
    private ExerciseDao exerciseDao;
    private ExerciseSetDao exerciseSetDao;
    private ExerciseCategoryDao exerciseCategoryDao;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples

    public WorkoutRepository(Context application) {
        WorkoutRoomDatabase db = WorkoutRoomDatabase.getDatabase(application);
        commentDao = db.commentDao();
        exerciseDao = db.exerciseDao();
        exerciseSetDao = db.exerciseSetDao();
        exerciseCategoryDao = db.exerciseCategoryDao();
    }

    // Retrieve

    public WorkoutComment getWorkoutComment(LocalDate localDate) {
        String date = Tools.stringFromDate(localDate);
        return commentDao.getWorkoutComment(date);
    }

    public List<Exercise> getAllExercises() {
        return exerciseDao.getAllExercises();
    }

    public List<ExerciseCategory> getCategories() {
        return exerciseCategoryDao.getExerciseCategories();
    }

    // Insert

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(final WorkoutComment comment) {
        WorkoutRoomDatabase.databaseWriteExecutor.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        commentDao.insert(comment);
                    }
                });
    }

    public void insert(final Exercise exercise) {
        WorkoutRoomDatabase.databaseWriteExecutor.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        exerciseDao.insert(exercise);
                    }
                });
    }
}