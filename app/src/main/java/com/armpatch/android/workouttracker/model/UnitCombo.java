package com.armpatch.android.workouttracker.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "unit_combo_table")
public class UnitCombo {

    @PrimaryKey
    @NonNull
    private String name;

    String unit1;
    String unit2;

    public UnitCombo(String name, String unit1, String unit2) {
        this.name = name;
        this.unit1 = unit1;
        this.unit2 = unit2;
    }

    @Ignore
    public UnitCombo(String name, String unit1) {
        this(name, unit1, null);
    }

    public List<String> getUnits() {
        List<String> units = new ArrayList<>();

        units.add(unit1);
        if (unit2 != null) units.add(unit2);

        return units;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public static String toString(UnitCombo u) {
        String output =  u.name + "," + u.unit1;

        if (u.unit2 != null)
            output = output + "," + u.unit2;

        return output;
    }

    public static UnitCombo fromString(String data) {
        String[] params = data.split(",");
        UnitCombo unitCombo;

        if (params.length == 2) {
            unitCombo = new UnitCombo(params[0], params[1]);
            return unitCombo;
        }

        unitCombo = new UnitCombo(params[0], params[1], params[2]);
        return unitCombo;
    }
}
