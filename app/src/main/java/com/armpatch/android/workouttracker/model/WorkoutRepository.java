package com.armpatch.android.workouttracker.model;

import android.content.Context;

import java.util.List;

public class WorkoutRepository {

    private ExerciseOrderDao exerciseOrderDao;
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
        exerciseOrderDao = db.workoutDao();
        workoutCommentDao = db.workoutCommentDao();
        exerciseDao = db.exerciseDao();
        exerciseSetDao = db.exerciseSetDao();
        categoryDao = db.categoryDao();
    }

    // Access methods

    public Exercise getExercise(String name) {
        return exerciseDao.getExercise(name);
    }

    public ExerciseOrder getExerciseOrder(String date) {
        return exerciseOrderDao.getExerciseOrder(date);
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

    public List<ExerciseSet> getSetHistory(String exerciseName) {
        return exerciseSetDao.getHistory(exerciseName);
    }

    public int getSetCount(String date, String exerciseName) {
        return exerciseSetDao.getSetCount(date, exerciseName);
    }

    // Insert methods

    void insert(final ExerciseSet set) {
        exerciseSetDao.insert(set);
    }

    void insert(final ExerciseOrder exerciseOrder) {
        exerciseOrderDao.insert(exerciseOrder);
    }

    public void insert(final WorkoutComment workoutComment) {
        workoutCommentDao.insert(workoutComment);
    }

    public void insert(final Exercise exercise) {
        exerciseDao.insert(exercise);
    }

    // Update methods

    public void update(List<ExerciseSet> sets) {
        exerciseSetDao.update(sets);
    }

    // delete methods

    void delete(ExerciseSet set) {
        exerciseSetDao.delete(set);
    }

    public void clearAllTables() {
        exerciseOrderDao.clearTable();
        exerciseDao.clearTable();
        exerciseSetDao.clearTable();
        categoryDao.clearTable();
    }
}
