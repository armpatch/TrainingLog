package com.armpatch.android.workouttracker;

import com.armpatch.android.workouttracker.model.Workout;

import org.junit.Test;
import org.threeten.bp.LocalDate;
import static org.junit.Assert.assertEquals;

public class WorkoutTest {


    @Test
    public void evaluatesGetExerciseOrderArray() {
        Workout workout = new Workout(LocalDate.now().toString());

        String exerciseOrder = "one";
        workout.setExerciseOrder(exerciseOrder);
        String[] expected = {"one"};
        String[] result = workout.getExerciseOrderArray();

        for (int i = 0; i < expected.length ; i++) {
            assertEquals(expected[i],result[i]);
        }
    }
}
