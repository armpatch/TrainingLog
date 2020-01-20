package com.armpatch.android.workouttracker.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.armpatch.android.workouttracker.EditCommentsDialog;
import com.armpatch.android.workouttracker.R;
import com.armpatch.android.workouttracker.Tools;
import com.armpatch.android.workouttracker.model.ExerciseSet;
import com.armpatch.android.workouttracker.model.Workout;
import com.armpatch.android.workouttracker.model.WorkoutRepository;

import org.threeten.bp.LocalDate;

import java.util.List;

public class WorkoutPagerAdapter extends PagerAdapter {

    private static final int ITEM_COUNT = 10000;
    public static final int POSITION_TODAY = 5000;

    private Context activityContext;
    private LayoutInflater inflater;

    public WorkoutPagerAdapter(Context context) {
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
        LocalDate currentDate = LocalDate.now().plusDays(position - POSITION_TODAY);

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

    public LocalDate getSelectedItemDate(int currentPosition) {
        return (LocalDate.now().plusDays(currentPosition - POSITION_TODAY));
    }

    class WorkoutHolder implements View.OnClickListener, EditCommentsDialog.Callbacks {
        private Context activityContext;
        private WorkoutRepository repository;

        private LocalDate date;
        private Workout workout;
        List<ExerciseSet> sets;

        private View itemView;
        private TextView commentTextView;
        private ListView exerciseListView;

        WorkoutHolder(final Context activityContext, LocalDate date) {
            this.activityContext = activityContext;
            repository = new WorkoutRepository(activityContext);
            this.date = date;

            itemView = inflater.inflate(R.layout.content_workout_holder, null);
            commentTextView = itemView.findViewById(R.id.workout_comments);
            commentTextView.setOnClickListener(this);

            exerciseListView = itemView.findViewById(R.id.exercise_group_list);
        }

        @Override
        public void onCommentSaved(String comments) {
            workout.setComments(comments);
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
            return date;
        }

        class UpdateFromDatabase extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                String date = Tools.stringFromDate(WorkoutHolder.this.getDate());
                workout = repository.getWorkout(date);
                sets = repository.getExerciseSets(workout.getDate());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                commentTextView.setText(workout.getComments());
                // Todo create views from sets
                ExerciseCardAdapter adapter = new ExerciseCardAdapter(activityContext, sets);
                exerciseListView.setAdapter(adapter);
            }
        }

        class SaveToDatabase extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                repository.update(workout);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                WorkoutHolder.this.updateFromDatabase();
            }
        }
    }
}
