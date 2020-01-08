package com.armpatch.android.workouttracker.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Exercise exercise);

    @Query("SELECT * from exercise_table WHERE name = :name")
    Exercise getExercise(String name);

    @Query("SELECT * from exercise_table")
    List<Exercise> getAllExercises();
}
