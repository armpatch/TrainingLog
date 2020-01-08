package com.armpatch.android.workouttracker.model;

import androidx.room.TypeConverter;

public class Converters {

    @TypeConverter
    public static ExerciseCategory categoryFromString(String name) {
        return new ExerciseCategory(name);
    }

    @TypeConverter
    public static String nameFromExerciseCategory(ExerciseCategory category) {
        return category.getName();
    }

    @TypeConverter
    public static UnitCombo unitCombofromString(String data) {
        return UnitCombo.fromString(data);
    }

    @TypeConverter
    public static String dataFromUnitCombo(UnitCombo unitCombo) {
        return UnitCombo.toParceableString(unitCombo);
    }

}
