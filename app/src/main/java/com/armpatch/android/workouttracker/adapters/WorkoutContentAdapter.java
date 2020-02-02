package com.armpatch.android.workouttracker.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.armpatch.android.workouttracker.EditCommentsDialog;
import com.armpatch.android.workouttracker.R;
import com.armpatch.android.workouttracker.Tools;
import com.armpatch.android.workouttracker.WorkoutSetSorter;
import com.armpatch.android.workouttracker.model.ExerciseSet;
import com.armpatch.android.workouttracker.model.Workout;
import com.armpatch.android.workouttracker.model.WorkoutRepository;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

@SuppressLint("StaticFieldLeak")
public class WorkoutContentAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements EditCommentsDialog.Callbacks{

    private static final int VIEW_TYPE_COMMENTS = 1;
    private static final int VIEW_TYPE_EXERCISE = 2;

    private Context activityContext;
    private Callback activityCallback;
    private Workout workout;
    private String currentDate;
    private String[] orderedExerciseNames;
    private Hashtable<String, ArrayList<ExerciseSet>> setMap;

    public interface Callback {
        void onExerciseGroupSelected(String exerciseName);
    }

    public WorkoutContentAdapter(Context activityContext, LocalDate date) {
        this.activityContext = activityContext;
        activityCallback = (Callback) activityContext;

        currentDate = Tools.stringFromDate(date);
        refresh();
    }

    void refresh() {
        new WorkoutQueryTask(currentDate).execute();
    }

    private boolean workoutHasComments() {
        if (workout == null) {
            return false;
        }

        return workout.getComments().length() > 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && workoutHasComments()) {
            return VIEW_TYPE_COMMENTS;
        } else {
            return VIEW_TYPE_EXERCISE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        if (viewType == VIEW_TYPE_COMMENTS) {
            itemView = LayoutInflater.from(activityContext).inflate(R.layout.list_item_comments, parent, false);
            return new CommentsHolder(itemView);
        } else {
            itemView = LayoutInflater.from(activityContext).inflate(R.layout.list_item_exercise_group, parent, false);
            return new ExerciseGroupHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_COMMENTS) {
            ((CommentsHolder) holder ).setCommentText();
        } else {
            ((ExerciseGroupHolder) holder ).bind(position);
        }
    }

    @Override
    public int getItemCount() {
        int itemCount = 0;

        if (workoutHasComments()) {
            itemCount++;
        }

        if (orderedExerciseNames != null) {
            itemCount += orderedExerciseNames.length;
        }

        return itemCount;
    }

    @Override
    public void onCommentChanged(String comment) {
        workout.setComments(comment);
        new SaveCommentsTask().execute();
    }

    private class WorkoutQueryTask extends AsyncTask<Void, Void, Void> {
        String date;
        List<ExerciseSet> sets;

        WorkoutQueryTask(String date) {
            this.date = date;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            WorkoutRepository repository = new WorkoutRepository(activityContext);
            workout = repository.getWorkout(date);
            sets = repository.getExerciseSets(date);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (workout != null && !workout.isEmpty()) {
                orderedExerciseNames = workout.getExerciseOrderArray();
                setMap = WorkoutSetSorter.getSortedTable(orderedExerciseNames, sets);
                notifyDataSetChanged();
            }
        }
    }

    private class SaveCommentsTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            WorkoutRepository repository = new WorkoutRepository(activityContext);
            repository.update(workout);
            return null;
        }
    }

    class CommentsHolder extends RecyclerView.ViewHolder{

        TextView commentsView;

        public CommentsHolder(@NonNull View itemView) {
            super(itemView);
            commentsView = itemView.findViewById(R.id.workout_comments);
            itemView.setOnClickListener(v -> showEditorDialog());
        }

        void setCommentText() {
            commentsView.setText(workout.getComments());
        }

        void showEditorDialog() {
            EditCommentsDialog dialog = new EditCommentsDialog(
                    activityContext,
                    WorkoutContentAdapter.this);
            dialog.setText(commentsView.getText().toString());
            dialog.show();
        }
    }

    class ExerciseGroupHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        List<ExerciseSet> currentExerciseSets;
        LinearLayout setListLayout;
        TextView exerciseTitle;

        ExerciseGroupHolder(View itemView) {
            super(itemView);
            setListLayout = itemView.findViewById(R.id.sets_layout);
            exerciseTitle = itemView.findViewById(R.id.exercise_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String name = currentExerciseSets.get(0).getExerciseName();
            activityCallback.onExerciseGroupSelected(name);
        }

        void bind(int position) {
            currentExerciseSets = setMap.get(orderedExerciseNames[position]);
            exerciseTitle.setText(currentExerciseSets.get(0).getExerciseName());
            createExerciseSetViews(currentExerciseSets);
        }

        void createExerciseSetViews(List<ExerciseSet> sets){
            setListLayout.removeAllViews();

            for (ExerciseSet set : sets) {
                View setView = LayoutInflater.from(activityContext).inflate(
                        R.layout.list_item_set_historical, setListLayout, false);

                TextView weight = setView.findViewById(R.id.weight);
                weight.setText(activityContext.getString(R.string.weight_lbs, set.getMeasurement1()));

                TextView reps = setView.findViewById(R.id.reps);
                reps.setText(activityContext.getString(R.string.reps, set.getMeasurement2()));

                setListLayout.addView(setView);
            }
        }
    }
}
