package com.armpatch.android.workouttracker.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExerciseCategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ExerciseCategory exerciseCategory);

    @Query("SELECT * FROM exercise_category_table WHERE name = :name")
    ExerciseCategory getExerciseCategory(String name);

    @Query("SELECT * FROM exercise_category_table")
    List<ExerciseCategory> getExerciseCategories();
}
