package com.armpatch.android.workouttracker;

import com.armpatch.android.workouttracker.model.ExerciseOrder;

import org.junit.Test;
import org.threeten.bp.LocalDate;
import static org.junit.Assert.assertEquals;

public class ExerciseOrderTest {


    @Test
    public void evaluatesGetExerciseOrderArray() {
        ExerciseOrder workout = new ExerciseOrder(LocalDate.now().toString());

        String exerciseOrder = "one";
        workout.setExerciseOrder(exerciseOrder);
        String[] expected = {"one"};
        String[] result = workout.getExerciseOrderArray();

        for (int i = 0; i < expected.length ; i++) {
            assertEquals(expected[i],result[i]);
        }
    }
}
