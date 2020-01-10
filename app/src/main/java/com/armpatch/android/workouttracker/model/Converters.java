package com.armpatch.android.workouttracker.model;

import androidx.room.TypeConverter;

public class Converters {

    @TypeConverter
    public static Category categoryFromString(String name) {
        return new Category(name);
    }

    @TypeConverter
    public static String nameFromExerciseCategory(Category category) {
        return category.getName();
    }

}
