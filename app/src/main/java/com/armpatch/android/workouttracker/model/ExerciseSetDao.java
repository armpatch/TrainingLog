package com.armpatch.android.workouttracker.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExerciseSetDao {
    @Insert()
    void insert(ExerciseSet exerciseSet);

    @Query("SELECT * from exercise_set_table WHERE date = :date")
    List<ExerciseSet> getExerciseSets (String date);

    @Query("SELECT * FROM exercise_set_table WHERE date = :date AND exerciseOrder = :exerciseGroup")
    List<ExerciseSet> getExerciseSets (String date, int exerciseGroup);

    @Query("SELECT MAX(exerciseOrder) FROM exercise_set_table WHERE date = :date")
    int getExerciseCount(String date);
}
