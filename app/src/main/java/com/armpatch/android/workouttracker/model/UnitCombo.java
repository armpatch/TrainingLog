package com.armpatch.android.workouttracker.model;

import android.os.BadParcelableException;
import android.provider.Contacts;

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

    private String unit1;
    private String unit2;
    private String unit3;

    public UnitCombo(String name, String unit1, String unit2, String unit3) {
        this.name = name;
        this.unit1 = unit1;
        this.unit2 = unit2;
        this.unit3 = unit3;
    }

    public UnitCombo(String name, String unit1, String unit2) {
        this(name, unit1, unit2, "");
    }

    public UnitCombo(String name, String unit1) {
        this(name, unit1, "", "");
    }

    public List<String> getUnits() {
        List<String> units = new ArrayList<>();

        units.add(unit1);
        if (unit2 != null) units.add(unit2);
        if (unit3 != null) units.add(unit3);

        return units;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public static String toParceableString(UnitCombo u) {
        return u.name + ","
                + u.unit1 + ","
                + u.unit2 + ","
                + u.unit3;
    }

    public static UnitCombo fromString(String data) {
        String[] params = data.split(",");

        if (params.length != 4)
            throw new BadParcelableException("String has improper number of delimiters");

        return new UnitCombo(params[0], params[1], params[2], params[3]);
    }
}
