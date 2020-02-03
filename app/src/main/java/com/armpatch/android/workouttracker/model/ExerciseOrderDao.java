package com.armpatch.android.workouttracker.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ExerciseOrderDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ExerciseOrder exerciseOrder);

    @Update
    void update(ExerciseOrder exerciseOrder);

    @Query("SELECT * FROM exercise_order_table WHERE date = :date")
    ExerciseOrder getExerciseOrder(String date);

    @Query("DELETE FROM exercise_order_table")
    void clearTable();
}
