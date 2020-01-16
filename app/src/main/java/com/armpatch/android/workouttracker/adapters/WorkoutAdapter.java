package com.armpatch.android.workouttracker.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.armpatch.android.workouttracker.EditCommentsDialog;
import com.armpatch.android.workouttracker.R;
import com.armpatch.android.workouttracker.model.WorkoutComment;
import com.armpatch.android.workouttracker.model.WorkoutData;
import com.armpatch.android.workouttracker.model.WorkoutRepository;

import org.threeten.bp.LocalDate;

public class WorkoutAdapter extends PagerAdapter {

    private static final int ITEM_COUNT = 10000;
    public static final int STARTING_ITEM = 5000; // Today

    private Context activityContext;
    private LayoutInflater inflater;

    public WorkoutAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        activityContext = context;
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return ((WorkoutHolder)object).itemView == view;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LocalDate currentDate = LocalDate.now().plusDays(position - STARTING_ITEM);

        WorkoutHolder workoutHolder = new WorkoutHolder(activityContext, currentDate);
        workoutHolder.updateFromDatabase();

        // add item to container
        container.addView(workoutHolder.itemView);
        return workoutHolder;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(((WorkoutHolder) object).itemView);
    }

    public static int relativeDays(int position) {
        return position - STARTING_ITEM;
    }

    class WorkoutHolder implements View.OnClickListener, EditCommentsDialog.Callbacks {
        private Context activityContext;
        private WorkoutRepository repository;

        private WorkoutData workoutData;
        private View itemView;
        private TextView commentTextView;

        WorkoutHolder(final Context activityContext, LocalDate date) {
            this.activityContext = activityContext;
            repository = new WorkoutRepository(getContext());
            workoutData = new WorkoutData(date);

            itemView = inflater.inflate(R.layout.content_workout_holder, null);
            commentTextView = itemView.findViewById(R.id.workout_comments);
            commentTextView.setOnClickListener(this);
        }

        @Override
        public void onCommentSaved(String comment) {
            workoutData.setComment(comment);
            new SaveToDatabase().execute();
        }

        @Override
        public void onClick(View v) {
            Log.d("TAG", String.valueOf(v.getId()));

            if (v == commentTextView) {
                EditCommentsDialog dialog = new EditCommentsDialog(activityContext, WorkoutHolder.this);
                dialog.setComment(commentTextView.getText().toString());
                dialog.show();
            }
        }

        Context getContext() {
            return activityContext;
        }

        void updateFromDatabase() {
            new UpdateFromDatabase().execute();
        }

        LocalDate getDate() {
            return workoutData.getDate();
        }

        class UpdateFromDatabase extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                WorkoutComment comment = repository.getWorkoutComment(WorkoutHolder.this.getDate());
                if (comment != null)
                    workoutData.setComment(comment.getText());

                workoutData.sets = repository.getExerciseSets(workoutData.getDate());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                commentTextView.setText(workoutData.getComment());
                // Todo transform list of sets into Views
            }
        }

        class SaveToDatabase extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                repository.insert(new WorkoutComment(workoutData.getDateString(), workoutData.getComment()));
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                WorkoutHolder.this.updateFromDatabase();
            }
        }
    }
}
