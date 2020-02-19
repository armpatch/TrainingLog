package com.armpatch.android.workouttracker;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class NumberChooser{

    private ImageButton incrementButton, decrementButton;
    private EditText numberTextView;
    private TextView unitName;
    private float value;
    private float increment;

    public NumberChooser(View layout) {
        decrementButton = layout.findViewById(R.id.decrement_button);
        decrementButton.setOnClickListener(v -> decrement());
        incrementButton = layout.findViewById(R.id.increment_button);
        incrementButton.setOnClickListener(v -> increment());

        numberTextView = layout.findViewById(R.id.number_text);
        numberTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                value = Float.valueOf(text);
            }
        });
        numberTextView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                numberTextView.clearFocus();
            }

            return false;
        });

        unitName = layout.findViewById(R.id.date_title);

        setValue(5f);
        setIncrement(5f);
    }

    public void setTitle(String name) {
        unitName.setText(name);
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        numberTextView.setText(String.valueOf(value));
    }

    public void setIncrement(float increment) {
        this.increment = increment;
    }

    public void increment() {
        setValue(value + increment);
    }

    public void decrement() {
        setValue(value - increment);
    }
}
