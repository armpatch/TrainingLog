package com.armpatch.android.workouttracker.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "unit_table")
public class Unit {

    @PrimaryKey
    @NonNull
    private String name;

    public Unit(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
