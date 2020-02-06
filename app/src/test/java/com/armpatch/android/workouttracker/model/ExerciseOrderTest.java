package com.armpatch.android.workouttracker.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class ExerciseOrderTest {


    private ExerciseOrder exerciseOrder;

    @Before
    public void setup() {
        String date = LocalDate.now().toString();
        exerciseOrder = new ExerciseOrder(date);
    }


    @Test
    public void removeExercise() {
        String[] startingNames = {"one", "two", "three"};
        exerciseOrder.setExerciseOrder(startingNames);

        exerciseOrder.removeExercise("two");
        String expected = "one,three";

        assertEquals(expected, exerciseOrder.toString());
    }

    @Test
    public void size() {
        exerciseOrder.setExerciseOrder("1,1");

        assertEquals(2, exerciseOrder.size());
    }
}