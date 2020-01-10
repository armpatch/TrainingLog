package com.armpatch.android.workouttracker.model;

import androidx.room.TypeConverter;

class Converters {

    @TypeConverter
    static Category categoryFromString(String name) {
        return new Category(name);
    }

    @TypeConverter
    static String nameFromExerciseCategory(Category category) {
        return category.getName();
    }

}
