package com.armpatch.android.workouttracker.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.armpatch.android.workouttracker.R;
import com.armpatch.android.workouttracker.model.ExerciseSet;
import com.armpatch.android.workouttracker.model.WorkoutEditorHelper;
import com.armpatch.android.workouttracker.model.WorkoutRepository;

import java.util.ArrayList;
import java.util.List;

public class TrackerSetAdapter extends RecyclerView.Adapter<TrackerSetAdapter.SetHolder> {

    private Context activityContext;
    private List<ExerciseSet> sets;
    private String exerciseName;
    private String exerciseDate;

    TrackerSetAdapter(Context activityContext, String exerciseName, String exerciseDate) {
        this.activityContext = activityContext;
        sets = new ArrayList<>();
        this.exerciseName = exerciseName;
        this.exerciseDate = exerciseDate;
    }

    void getSets() {
        new GetSetsTask().execute();
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

    void addSet(float measurement1, float measurement2) {
        ExerciseSet set = new ExerciseSet(exerciseDate, exerciseName, measurement1, measurement2, getItemCount() + 1);
        new InsertSetTask(set).execute();
    }

    private class GetSetsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            WorkoutRepository repository = new WorkoutRepository(activityContext);
            sets.clear();
            sets.addAll(repository.getExerciseSets(exerciseDate, exerciseName));

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
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
            WorkoutEditorHelper helper = new WorkoutEditorHelper(activityContext);
            helper.addSet(set);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            getSets();
        }
    }

    class SetHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView setNumberText;
        TextView weightText;
        TextView repsText;

        SetHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            setNumberText = itemView.findViewById(R.id.set_number);
            weightText = itemView.findViewById(R.id.weight);
            repsText = itemView.findViewById(R.id.reps);
        }

        void bind(ExerciseSet set) {
            setNumberText.setText(String.valueOf(set.getOrder()));

            weightText.setText(activityContext.getString(R.string.weight_lbs, set.getMeasurement1()));
            repsText.setText(activityContext.getString(R.string.reps, set.getMeasurement2()));
        }

        @Override
        public void onClick(View v) {

        }
    }
}
