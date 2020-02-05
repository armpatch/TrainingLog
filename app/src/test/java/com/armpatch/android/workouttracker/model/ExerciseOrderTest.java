package com.armpatch.android.workouttracker.model;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class ExerciseOrderTest {

    @Test
    public void removeExercise() {
        String date = LocalDate.now().toString();
        ExerciseOrder exerciseOrder = new ExerciseOrder(date);

        String[] startingNames = {"one", "two", "three"};
        exerciseOrder.setExerciseOrder(startingNames);

        exerciseOrder.removeExercise("two");
        String expected = "one,three";

        assertEquals(expected, exerciseOrder.getExerciseOrder());
    }
}