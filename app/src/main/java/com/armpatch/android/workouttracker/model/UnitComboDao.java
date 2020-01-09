package com.armpatch.android.workouttracker.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface UnitComboDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(UnitCombo unitCombo);

    @Query("SELECT * FROM unit_combo_table WHERE name = :name")
    UnitCombo getUnitCombo(String name);
}
