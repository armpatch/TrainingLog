package com.armpatch.android.workouttracker;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

public class EditCommentsDialog extends Dialog {

    Callbacks callbacks;
    EditText commentEditText;

    interface Callbacks {
        void onCommentSaved(String comment);
    }

    public EditCommentsDialog(@NonNull Context context, final Callbacks callbacks) {
        super(context);
        this.callbacks = callbacks;

        setContentView(R.layout.dialog_workout_comment);

        commentEditText = findViewById(R.id.edit_text);

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbacks.onCommentSaved(commentEditText.getText().toString());
                dismiss();
            }
        });

        Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

    }

    void setComment(String comment) {
        commentEditText.setText(comment);
    }
}
