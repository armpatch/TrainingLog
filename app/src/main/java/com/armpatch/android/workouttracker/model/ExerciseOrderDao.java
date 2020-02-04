package com.armpatch.android.workouttracker.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ExerciseOrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ExerciseOrder exerciseOrder);

    @Query("SELECT * FROM exercise_order_table WHERE date = :date")
    ExerciseOrder getExerciseOrder(String date);

    @Query("DELETE FROM exercise_order_table")
    void clearTable();
}
