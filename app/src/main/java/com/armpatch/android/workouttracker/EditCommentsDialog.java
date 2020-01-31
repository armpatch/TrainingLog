package com.armpatch.android.workouttracker;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

public class EditCommentsDialog extends Dialog {
    private EditText commentEditText;

    public interface Callbacks {
        void onCommentChanged(String comment);
    }

    public EditCommentsDialog(@NonNull Context context, final Callbacks callbacks) {
        super(context);
        setContentView(R.layout.dialog_comment_editor);

        commentEditText = findViewById(R.id.edit_text);

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> {
            callbacks.onCommentChanged(commentEditText.getText().toString());
            dismiss();
        });

        Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(v -> cancel());
    }

    public void setText(String comment) {
        commentEditText.setText(comment);
    }
}
