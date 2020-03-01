package com.armpatch.android.workouttracker.component;

import android.view.View;

public class IntegerNumberChooser extends NumberChooser<Integer> {

    public IntegerNumberChooser(View layout, String title, Integer value, Integer increment) {
        super(layout, title, value, increment);
    }

    @Override
    public Integer getValue(String newValue) {
        return Integer.parseInt(newValue);
    }

    @Override
    public String increment(String value) {
        return Integer.toString(Integer.parseInt(value) + getIncrement());
    }

    @Override
    public String decrement(String value) {
        return Integer.toString(Integer.parseInt(value) - getIncrement());
    }
}
