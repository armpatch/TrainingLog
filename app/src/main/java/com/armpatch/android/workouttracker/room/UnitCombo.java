package com.armpatch.android.workouttracker.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "unit_combo_table")
public class UnitCombo {

    @PrimaryKey
    @NonNull
    private String name;

    private Unit unit1;
    private Unit unit2;
    private Unit unit3;

    public UnitCombo(String name, Unit unit1, Unit unit2, Unit unit3) {
        this.name = name;
        this.unit1 = unit1;
        this.unit2 = unit2;
        this.unit3 = unit3;
    }

    public UnitCombo(String name, Unit unit1, Unit unit2) {
        this(name,unit1,unit2,null);
    }

    public UnitCombo(String name, Unit unit1) {
        this(name,unit1,null,null);
    }

    public List<Unit> getUnits() {
        List<Unit> units = new ArrayList<>();

        units.add(unit1);
        if (unit2 != null) units.add(unit2);
        if (unit3 != null) units.add(unit3);

        return units;
    }

    @NonNull
    public String getName() {
        return name;
    }
}
