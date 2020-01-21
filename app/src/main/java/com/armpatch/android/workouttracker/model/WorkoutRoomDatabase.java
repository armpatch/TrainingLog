package com.armpatch.android.workouttracker.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.armpatch.android.workouttracker.MeasurementType;
import com.armpatch.android.workouttracker.Tools;

import org.threeten.bp.LocalDate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Workout.class, Exercise.class, Category.class, ExerciseSet.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class WorkoutRoomDatabase extends RoomDatabase {

    public abstract WorkoutDao workoutDao();
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
                            .build();
                    INSTANCE.populateInitialData();
                }
            }
        }
        return INSTANCE;
    }

    private void populateInitialData() {
        final Category shoulders = new Category("shoulders");
        final Category arms = new Category("arms");

        final Exercise pull_up = new Exercise("pull up", MeasurementType.WEIGHT_AND_REPS, arms);

        String date = Tools.stringFromDate(LocalDate.now());
        final ExerciseSet set1 = new ExerciseSet(date, pull_up.getName(), 150, 10, 0);

        final Workout workout = new Workout(date);
        workout.setExerciseOrder(pull_up.getName());

        WorkoutRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                categoryDao().insert(shoulders);
                categoryDao().insert(arms);

                exerciseDao().insert(pull_up);
                exerciseSetDao().insert(set1);

                workoutDao().insert(workout);

            }
        });
    }

}

