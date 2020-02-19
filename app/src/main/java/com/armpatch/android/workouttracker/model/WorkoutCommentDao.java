package com.armpatch.android.workouttracker.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface WorkoutCommentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WorkoutComment workoutComment);

    @Query("SELECT * FROM workout_comment_table WHERE date = :date")
    WorkoutComment getComment(String date);

    @Query("DELETE FROM workout_comment_table")
    void clearTable();
}
