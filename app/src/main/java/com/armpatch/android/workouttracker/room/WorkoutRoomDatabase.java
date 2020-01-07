package com.armpatch.android.workouttracker.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {WorkoutComment.class}, version = 1, exportSchema = false)
public abstract class WorkoutRoomDatabase extends RoomDatabase {

    public abstract WorkoutCommentDao commentDao();

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
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}

