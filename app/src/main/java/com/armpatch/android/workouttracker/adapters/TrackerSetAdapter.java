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
import com.armpatch.android.workouttracker.model.WorkoutRepository;

import java.util.List;

public class TrackerSetAdapter extends RecyclerView.Adapter<TrackerSetAdapter.SetHolder> {

    private Context activityContext;
    List<ExerciseSet> sets;
    ExerciseSet template;

    TrackerSetAdapter(Context activityContext, List<ExerciseSet> sets) {
        this.sets = sets;
        this.activityContext = activityContext;
        createTemplateSet();
    }

    void createTemplateSet() {
        ExerciseSet firstSet = sets.get(0);
        template = new ExerciseSet(
                firstSet.getDate(),
                firstSet.getExerciseName(),
                firstSet.getMeasurement1(),
                firstSet.getMeasurement2(),
                0);
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
        return sets.size();
    }

    void addSet(float measurement1, float measurement2) {
        ExerciseSet set = new ExerciseSet(template.getDate(), template.getExerciseName(), measurement1, measurement2, getItemCount());
        sets.add(set);
        TrackerSetAdapter.this.refresh();
    }

    void refresh() {
        notifyDataSetChanged();
        new WriteSetsToDatabaseTask(sets).execute();
    }

    class WriteSetsToDatabaseTask extends AsyncTask<Void, Void, Void> {

        List<ExerciseSet> sets;

        WriteSetsToDatabaseTask(List<ExerciseSet> sets){
            this.sets = sets;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            new WorkoutRepository(activityContext).update(sets);
            return null;
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
