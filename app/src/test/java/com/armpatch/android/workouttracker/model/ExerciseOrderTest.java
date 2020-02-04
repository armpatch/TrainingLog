package com.armpatch.android.workouttracker.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class ExerciseOrderTest {


    private String date;
    private ExerciseOrder exerciseOrder;


    @Before
    public void setup() {
        date = LocalDate.now().toString();
        exerciseOrder = new ExerciseOrder(date);
    }

    @Test
    public void containsExercise() {
        exerciseOrder.setExerciseOrder("one,two,three");

        String exercise = "one";

        assert exerciseOrder.containsExercise(exercise);
    }
}