package com.armpatch.android.workouttracker.component;

import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.armpatch.android.workouttracker.R;

public abstract class NumberChooser<T extends Number> {

    private final EditText numberTextView;
    private final T increment;

    NumberChooser(View layout, String title, T value, T increment) {
        this.increment = increment;

        numberTextView = layout.findViewById(R.id.number_text);
        numberTextView.setText(String.valueOf(value));
        numberTextView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                numberTextView.clearFocus();
            }
            return false;
        });

        ImageButton decrementButton = layout.findViewById(R.id.decrement_button);
        decrementButton.setOnClickListener(v -> numberTextView.setText(decrement(numberTextView.getText().toString())));
        ImageButton incrementButton = layout.findViewById(R.id.increment_button);
        incrementButton.setOnClickListener(v -> numberTextView.setText(increment(numberTextView.getText().toString())));

        TextView unitName = layout.findViewById(R.id.date_title);
        unitName.setText(title);
    }

    public T getValue() {
        return getValue(numberTextView.getText().toString());
    }

    public void updateValue(T value) {
        numberTextView.setText(String.valueOf(value));
    }

    T getIncrement() {
        return increment;
    }

    abstract T getValue(String value);

    abstract String increment(String value);

    abstract String decrement(String value);
}
