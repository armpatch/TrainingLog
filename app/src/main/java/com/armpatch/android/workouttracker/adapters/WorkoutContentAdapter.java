package com.armpatch.android.workouttracker.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.armpatch.android.workouttracker.EditCommentsDialog;
import com.armpatch.android.workouttracker.R;
import com.armpatch.android.workouttracker.Tools;
import com.armpatch.android.workouttracker.WorkoutSetSorter;
import com.armpatch.android.workouttracker.activity.ExerciseSelectionActivity;
import com.armpatch.android.workouttracker.model.ExerciseOrder;
import com.armpatch.android.workouttracker.model.ExerciseSet;
import com.armpatch.android.workouttracker.model.WorkoutComment;
import com.armpatch.android.workouttracker.model.WorkoutEditorHelper;
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
    private static final int VIEW_TYPE_NEW_EXERCISE = 3;

    private Context activityContext;
    private Callback activityCallback;
    private String currentDate;
    public WorkoutComment workoutComment;
    private ExerciseOrder exerciseOrder;
    private Hashtable<String, ArrayList<ExerciseSet>> setTable;

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

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_COMMENTS;
        } else if (position == getItemCount() - 1) {
            return VIEW_TYPE_NEW_EXERCISE;
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
        } else if (viewType == VIEW_TYPE_NEW_EXERCISE) {
            itemView = LayoutInflater.from(activityContext).inflate(R.layout.list_item_add_exercise_button, parent, false);
            return new AddExerciseHolder(itemView);
        } else {
            itemView = LayoutInflater.from(activityContext).inflate(R.layout.list_item_exercise_group, parent, false);
            return new ExerciseGroupHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_COMMENTS) {
            ((CommentsHolder) holder ).bind();
        } else if (getItemViewType(position) == VIEW_TYPE_EXERCISE){
            ((ExerciseGroupHolder) holder ).bind(position);
        }
    }

    @Override
    public int getItemCount() {
        int itemCount = 2; // keep this 2 as long as the comment box and "add exercise" holders are present

        if (exerciseOrder != null) {
            itemCount += exerciseOrder.size();
        }

        return itemCount;
    }

    @Override
    public void onCommentChanged(String comment) {
        if (workoutComment == null) {
            workoutComment = new WorkoutComment(currentDate);
        }

        workoutComment.setComments(comment);
        new SaveCommentsTask().execute();
    }

    public void swapExerciseGroups(RecyclerView.ViewHolder holder1, RecyclerView.ViewHolder holder2) {
        int position1 = holder1.getAdapterPosition();
        int position2 = holder2.getAdapterPosition();

        notifyItemMoved(position1, position2);

        int COMMENT_HOLDER_OFFSET = 1;

        new ExerciseOrderSwapTask(position1 - COMMENT_HOLDER_OFFSET, position2 - COMMENT_HOLDER_OFFSET).execute();
    }

    private class ExerciseOrderSwapTask extends AsyncTask<Void, Void, Void> {

        int position1, position2;

        ExerciseOrderSwapTask(int position1, int position2) {
            this.position1 = position1;
            this.position2 = position2;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            WorkoutEditorHelper helper = new WorkoutEditorHelper(activityContext);
            helper.swapExerciseOrder(currentDate, position1, position2);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(activityContext, "Exercises swapped", Toast.LENGTH_SHORT).show();
        }
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
            workoutComment = repository.getComment(date);
            exerciseOrder = repository.getExerciseOrder(date);
            sets = repository.getExerciseSets(date);


            setTable = WorkoutSetSorter.getSortedTable(exerciseOrder, sets);


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            notifyDataSetChanged();
        }
    }

    private class SaveCommentsTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            WorkoutRepository repository = new WorkoutRepository(activityContext);
            repository.insert(workoutComment);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            refresh();
            notifyDataSetChanged();
        }
    }

    class CommentsHolder extends RecyclerView.ViewHolder{

        TextView commentsView;
        TextView placeholder;

        public CommentsHolder(@NonNull View itemView) {
            super(itemView);
            commentsView = itemView.findViewById(R.id.workout_comments);
            commentsView.setVisibility(View.GONE);
            placeholder = itemView.findViewById(R.id.placeholder);
            placeholder.setVisibility(View.GONE);
            itemView.setOnClickListener(v -> showEditorDialog());
        }

        void bind() {
            if (workoutComment != null && workoutComment.hasComments()) {
                commentsView.setText(workoutComment.getComments());
                commentsView.setVisibility(View.VISIBLE);
                placeholder.setVisibility(View.GONE);
            } else {
                placeholder.setVisibility(View.VISIBLE);
                commentsView.setVisibility(View.GONE);
            }
        }

        void showEditorDialog() {
            EditCommentsDialog.create(activityContext, WorkoutContentAdapter.this, commentsView.getText().toString());
        }
    }

    public class ExerciseGroupHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private String exerciseName;
        List<ExerciseSet> exerciseGroupSets;
        LinearLayout setListLayout;
        TextView exerciseTitle;

        ExerciseGroupHolder(View itemView) {
            super(itemView);
            setListLayout = itemView.findViewById(R.id.sets_layout);
            exerciseTitle = itemView.findViewById(R.id.date_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String name = exerciseGroupSets.get(0).getExerciseName();
            activityCallback.onExerciseGroupSelected(name);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }

        void bind(int contentPosition) {
            int orderInWorkout = contentPosition - 1;
            exerciseName = exerciseOrder.toArray()[orderInWorkout];
            exerciseGroupSets = setTable.get(exerciseName);
            exerciseTitle.setText(exerciseGroupSets.get(0).getExerciseName());
            createExerciseSetViews(exerciseGroupSets);
        }

        public String exerciseName() {
            return exerciseName;
        }

        void createExerciseSetViews(List<ExerciseSet> sets){
            setListLayout.removeAllViews();

            int MAX_SETS_VISIBLE = 4;

            for (int i = 0; i < sets.size() && i < MAX_SETS_VISIBLE; i++) {
                ExerciseSet set = sets.get(i);

                View setView = LayoutInflater.from(activityContext).inflate(
                        R.layout.list_item_set_historical, setListLayout, false);

                TextView weight = setView.findViewById(R.id.weight);
                weight.setText(activityContext.getString(R.string.weight_lbs, set.getMeasurement1()));

                TextView reps = setView.findViewById(R.id.reps);
                reps.setText(activityContext.getString(R.string.reps, set.getMeasurement2()));

                setListLayout.addView(setView);
            }

            if (sets.size() > MAX_SETS_VISIBLE) {
                View moreSetsView = LayoutInflater.from(activityContext).inflate(
                        R.layout.list_item_more_sets, setListLayout, false
                );

                int setsRemaining = sets.size() - MAX_SETS_VISIBLE;

                TextView textView = moreSetsView.findViewById(R.id.more_sets_text);
                String text = setsRemaining + " more set";
                if (setsRemaining > 1) text = text + "s";

                textView.setText(text);

                setListLayout.addView(moreSetsView);
            }
        }
    }

    public class AddExerciseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public AddExerciseHolder(@NonNull View itemView) {
            super(itemView);

            View clickableRegion = itemView.findViewById(R.id.clickable_layout);
            clickableRegion.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent addExerciseIntent = ExerciseSelectionActivity.getIntent(activityContext, currentDate);
            activityContext.startActivity(addExerciseIntent);
        }
    }

}
