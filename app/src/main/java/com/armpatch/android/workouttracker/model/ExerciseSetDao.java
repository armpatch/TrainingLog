package com.armpatch.android.workouttracker.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExerciseSetDao {
    @Insert()
    void insert(ExerciseSet exerciseSet);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void update(List<ExerciseSet> sets);

    @Query("SELECT * from exercise_set_table WHERE date = :date")
    List<ExerciseSet> getExerciseSets (String date);

    @Query("SELECT * FROM exercise_set_table WHERE date = :date AND exerciseName = :exerciseName")
    List<ExerciseSet> getExerciseSets (String date, String exerciseName);

    @Query("SELECT COUNT(DISTINCT exerciseName) FROM exercise_set_table WHERE date = :date")
    int getDistinctExerciseCount(String date);

    @Query("DELETE FROM exercise_set_table")
    void clearTable();

}
