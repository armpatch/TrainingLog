package com.armpatch.android.workouttracker.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {WorkoutComment.class, Exercise.class, ExerciseCategory.class, ExerciseSet.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class WorkoutRoomDatabase extends RoomDatabase {

    public abstract WorkoutCommentDao commentDao();
    public abstract ExerciseDao exerciseDao();
    public abstract ExerciseSetDao exerciseSetDao();
    public abstract ExerciseCategoryDao exerciseCategoryDao();
    public abstract UnitComboDao unitComboDao();

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
                    INSTANCE.populateInitialData();
                }
            }
        }
        return INSTANCE;
    }

    private void populateInitialData() {
        final UnitCombo weight_and_reps = new UnitCombo("Weight and Reps", Units.POUNDS, Units.REPS);

        final ExerciseCategory shoulders = new ExerciseCategory("shoulders");
        final ExerciseCategory arms = new ExerciseCategory("arms");

        final Exercise pullup = new Exercise("pullup", weight_and_reps, arms);

        WorkoutRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                unitComboDao().insert(weight_and_reps);

                exerciseCategoryDao().insert(shoulders);
                exerciseCategoryDao().insert(arms);

                exerciseDao().insert(pullup);
            }
        });
    }

}

