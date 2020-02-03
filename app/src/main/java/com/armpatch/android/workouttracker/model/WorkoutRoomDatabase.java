package com.armpatch.android.workouttracker.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Workout.class, WorkoutComment.class, Exercise.class, Category.class, ExerciseSet.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class WorkoutRoomDatabase extends RoomDatabase {

    public abstract WorkoutDao workoutDao();
    public abstract WorkoutCommentDao workoutCommentDao();
    public abstract ExerciseDao exerciseDao();
    public abstract ExerciseSetDao exerciseSetDao();
    public abstract CategoryDao categoryDao();

    private static volatile WorkoutRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static WorkoutRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WorkoutRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WorkoutRoomDatabase.class, "workout_database")
                            .createFromAsset("workout_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}

