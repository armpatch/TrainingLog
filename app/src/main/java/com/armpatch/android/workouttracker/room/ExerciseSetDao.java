package com.armpatch.android.workouttracker.room;

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
}
