package com.armpatch.android.workouttracker.model;

import android.content.Context;

import java.util.List;

public class WorkoutRepository {

    private WorkoutDao workoutDao;
    private WorkoutCommentDao workoutCommentDao;
    private ExerciseDao exerciseDao;
    private ExerciseSetDao exerciseSetDao;
    private CategoryDao categoryDao;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples

    public WorkoutRepository(Context application) {
        WorkoutRoomDatabase db = WorkoutRoomDatabase.getDatabase(application);
        workoutDao = db.workoutDao();
        workoutCommentDao = db.workoutCommentDao();
        exerciseDao = db.exerciseDao();
        exerciseSetDao = db.exerciseSetDao();
        categoryDao = db.categoryDao();
    }

    // Access methods

    public Workout getWorkout(String date) {
        return workoutDao.getWorkout(date);
    }

    public WorkoutComment getComment(String date) {
        return workoutCommentDao.getComment(date);
    }

    public List<Exercise> getExercises(Category category) {
        return exerciseDao.getExercises(category);
    }

    public List<Category> getCategories() {
        return categoryDao.getCategories();
    }

    public List<ExerciseSet> getExerciseSets(String date, String exerciseName) {
        return exerciseSetDao.getExerciseSets(date, exerciseName);
    }

    public List<ExerciseSet> getExerciseSets(String date) {
        return exerciseSetDao.getExerciseSets(date);
    }

    // Insert methods

    public void insert(final Workout workout) {
        WorkoutRoomDatabase.databaseWriteExecutor.execute(
                () -> workoutDao.insert(workout));
    }

    public void insert(final WorkoutComment workoutComment) {
        WorkoutRoomDatabase.databaseWriteExecutor.execute(
                () -> workoutCommentDao.insert(workoutComment));
    }

    public void insert(final Exercise exercise) {
        WorkoutRoomDatabase.databaseWriteExecutor.execute(
                () -> exerciseDao.insert(exercise));
    }

    // Update methods

    public void update(List<ExerciseSet> sets) {
        exerciseSetDao.update(sets);
    }

    // delete methods

    public void clearAllTables() {
        workoutDao.clearTable();
        exerciseDao.clearTable();
        exerciseSetDao.clearTable();
        categoryDao.clearTable();
    }
}
