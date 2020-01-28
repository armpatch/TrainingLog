package com.armpatch.android.workouttracker.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Category exerciseCategory);

    @Query("SELECT * FROM category_table WHERE name = :name")
    Category getExerciseCategory(String name);

    @Query("SELECT * FROM category_table")
    List<Category> getCategories();

    @Query("DELETE FROM category_table")
    void clearTable();
}
