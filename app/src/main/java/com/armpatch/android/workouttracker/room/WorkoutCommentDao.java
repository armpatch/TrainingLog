package com.armpatch.android.workouttracker.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface WorkoutCommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WorkoutComment workoutComment);

    @Query("SELECT * from workout_comment_table WHERE date = :date")
    WorkoutComment getWorkoutComment(String date);
}
