package com.armpatch.android.workouttracker.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface WorkoutDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Workout workout);

    @Query("SELECT * FROM workout_table WHERE date = :date")
    Workout getWorkout(String date);
}
