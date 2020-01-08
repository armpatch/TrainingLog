package com.armpatch.android.workouttracker.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Exercise exercise);

    @Query("SELECT * from exercise_table WHERE name = :name")
    Exercise getExercise(String name);
}
