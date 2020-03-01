package com.armpatch.android.workouttracker.component;

import android.view.View;

public class FloatNumberChooser extends NumberChooser<Float> {

    public FloatNumberChooser(View layout, String title, Float value, Float increment) {
        super(layout, title, value, increment);
    }

    @Override
    public Float getValue(String newValue) {
        return Float.parseFloat(newValue);
    }

    @Override
    public String increment(String value) {
        return Float.toString(Float.parseFloat(value) + getIncrement());
    }

    @Override
    public String decrement(String value) {
        return Float.toString(Float.parseFloat(value) - getIncrement());
    }
}
