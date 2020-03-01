package com.armpatch.android.workouttracker.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.armpatch.android.workouttracker.EditCommentsDialog;
import com.armpatch.android.workouttracker.R;
import com.armpatch.android.workouttracker.SetComparator;
import com.armpatch.android.workouttracker.model.ExerciseSet;
import com.armpatch.android.workouttracker.model.WorkoutEditorHelper;
import com.armpatch.android.workouttracker.model.WorkoutRepository;

import java.util.ArrayList;
import java.util.List;

public class TrackerSetAdapter extends RecyclerView.Adapter<TrackerSetAdapter.SetHolder> {

    public static final String TAG = "TrackerSetAdapter";

    private Context activityContext;
    private RecyclerView recyclerView;
    private List<ExerciseSet> sets;
    private String exerciseName;
    private String exerciseDate;
    private SetSelectionCallback setSelectionCallback;

    interface SetSelectionCallback {
        void onSetHolderClicked(ExerciseSet set);
    }

    void setSelectionCallback(SetSelectionCallback setSelectionCallback) {
        this.setSelectionCallback = setSelectionCallback;
    }

    TrackerSetAdapter(Context activityContext, String exerciseName, String exerciseDate) {
        this.activityContext = activityContext;
        sets = new ArrayList<>();
        this.exerciseName = exerciseName;
        this.exerciseDate = exerciseDate;
    }

    void retrieveSetsFromDatabase() {
        new RetrieveSetsTask().execute();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public SetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activityContext)
                .inflate(R.layout.list_item_set_tracker, parent, false);

        return new SetHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SetHolder holder, int position) {
        holder.bind(sets.get(position));
    }

    @Override
    public int getItemCount() {
        if (sets == null) {
            return 0;
        }
        return sets.size();
    }

    void addSet(Float weight, Integer reps) {
        Log.d(TAG, "-------------------------------addSet()");

        ExerciseSet set = new ExerciseSet(exerciseDate, exerciseName, weight, reps, -1);
        new InsertSetTask(set).execute();
    }

    void deleteSet(ExerciseSet set) {
        new DeleteSetTask(set).execute();
    }

    void updateSet(ExerciseSet set) {
        new UpdateSetTask(set).execute();
    }

    void highlightSet(ExerciseSet set) {
        removeAllHighlights();
        SetHolder holder = ((SetHolder) recyclerView.findViewHolderForAdapterPosition(set.getOrder() - 1));
        if (holder != null)
            holder.showAsSelected();
    }

    void removeAllHighlights() {
        SetHolder holder;
        for (int i = 0; i < getItemCount(); i++) {
            holder = ((SetHolder) recyclerView.findViewHolderForAdapterPosition(i));
            if (holder == null) return;
            holder.showAsUnselected();
        }
    }

    private class RetrieveSetsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "RetrieveSetsTask - doInBackground start");
            Log.d(TAG, "set count = " + sets.size());

            WorkoutRepository repository = new WorkoutRepository(activityContext);
            sets.clear();
            sets.addAll(repository.getExerciseSets(exerciseDate, exerciseName));
            sets.sort(new SetComparator());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d(TAG, "RetrieveSetsTask - onPostExecute");
            Log.d(TAG, "set count = " + sets.size());

            notifyDataSetChanged();
        }
    }

    private class InsertSetTask extends AsyncTask<Void, Void, Void> {

        ExerciseSet set;

        InsertSetTask(ExerciseSet set) {
            this.set = set;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "Insert Set Task - doInBackground()");
            WorkoutEditorHelper helper = new WorkoutEditorHelper(activityContext);
            helper.addSet(set);
            Log.d(TAG, "Insert Set Task - doInBackground done");

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d(TAG, "Insert Set Task - onPostExecute()");

            retrieveSetsFromDatabase();
        }
    }

    private class DeleteSetTask extends AsyncTask<Void, Void, Void> {

        ExerciseSet targetSet;

        DeleteSetTask(ExerciseSet set) {
            targetSet = set;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            new WorkoutEditorHelper(activityContext).deleteSet(targetSet);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            retrieveSetsFromDatabase();
        }
    }

    private class UpdateSetTask extends AsyncTask<Void, Void, Void> {

        private ExerciseSet set;

        UpdateSetTask(ExerciseSet set) {
            this.set = set;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            new WorkoutEditorHelper(activityContext).updateSet(set);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            retrieveSetsFromDatabase();
        }
    }

    class SetHolder extends RecyclerView.ViewHolder implements View.OnClickListener, EditCommentsDialog.Callbacks {

        private ExerciseSet set;

        TextView setNumberText;
        TextView weightText;
        TextView repsText;
        ImageView selectionTint;
        ImageView commentToggle;

        SetHolder(@NonNull View itemView) {
            super(itemView);

            setNumberText = itemView.findViewById(R.id.set_number);
            weightText = itemView.findViewById(R.id.weight_quantity);
            repsText = itemView.findViewById(R.id.reps_quantity);
            selectionTint = itemView.findViewById(R.id.selection_tint);
            selectionTint.setVisibility(View.INVISIBLE);
            commentToggle = itemView.findViewById(R.id.comment);

            itemView.setOnClickListener(this);
            commentToggle.setOnClickListener(this);
        }

        void bind(ExerciseSet set) {
            this.set = set;
            setNumberText.setText(String.valueOf(set.getOrder()));

            weightText.setText(String.valueOf(set.getWeight()));
            repsText.setText(String.valueOf(set.getRepetition()));

            if (set.getComment().length() > 0) {
                commentToggle.setImageResource(R.drawable.ic_comment_filled);
            } else {
                commentToggle.setImageResource(R.drawable.ic_comment_empty);
            }
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.comment) {
                editSetComment();
            } else {
                setSelectionCallback.onSetHolderClicked(set);
            }
        }

        private void editSetComment() {
            EditCommentsDialog.create(activityContext, this, set.getComment());
        }

        @Override
        public void onCommentChanged(String comment) {
            set.setComment(comment);
            updateSet(set);
        }

        public void showAsSelected() {
            selectionTint.setVisibility(View.VISIBLE);
        }

        public void showAsUnselected() {
            selectionTint.setVisibility(View.INVISIBLE);
        }
    }
}
