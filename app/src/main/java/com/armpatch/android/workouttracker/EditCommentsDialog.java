package com.armpatch.android.workouttracker;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

public class EditCommentsDialog extends Dialog {
    private EditText commentBox;

    public interface Callbacks {
        void onCommentChanged(String comment);
    }

    public static void create(@NonNull Context context, final Callbacks callbacks, String initialText) {
        EditCommentsDialog dialog = new EditCommentsDialog(context, callbacks, initialText);
        dialog.show();
    }

    private EditCommentsDialog(@NonNull Context context, final Callbacks callbacks, String initialText) {
        super(context);
        setContentView(R.layout.dialog_comments);
        setupCommentBox(initialText);
        setupButtons(callbacks);
    }

    private void setupCommentBox(String comment) {
        commentBox = findViewById(R.id.edit_text);
        commentBox.setText(comment);
    }

    private void setupButtons(Callbacks callbacks) {
        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> {
            callbacks.onCommentChanged(commentBox.getText().toString());
            dismiss();
        });

        Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(v -> cancel());
    }
}
