package com.armpatch.android.workouttracker.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.armpatch.android.workouttracker.R;
import com.armpatch.android.workouttracker.Tools;
import com.armpatch.android.workouttracker.model.ExerciseSet;
import com.armpatch.android.workouttracker.model.WorkoutRepository;

import java.util.ArrayList;
import java.util.List;

public class SetHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context activityContext;
    private String exerciseName;
    private static final String TAG = "SetHistoryAdapter";

    private List<List<ExerciseSet>> sortedSetHistory;

    SetHistoryAdapter(Context activityContext, String exerciseName) {
        this.activityContext = activityContext;
        this.exerciseName = exerciseName;
    }

    void refresh() {
        new SetHistoryQueryTask().execute();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(activityContext).inflate(R.layout.list_item_exercise_group_historical, parent, false);

        return new HistoricalExerciseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((HistoricalExerciseHolder) holder).bind(position);
    }

    @Override
    public int getItemCount() {
        if (sortedSetHistory == null) return 0;

        return sortedSetHistory.size();
    }

    class SetHistoryQueryTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            WorkoutRepository repository = new WorkoutRepository(activityContext);
            List<ExerciseSet> allSets = repository.getSetHistory(exerciseName);

            sortedSetHistory = sortSetsByDay(allSets);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            notifyDataSetChanged();
        }
    }

    private List<List<ExerciseSet>> sortSetsByDay(List<ExerciseSet> allSets) {
        List<List<ExerciseSet>> setGroupsByDay = new ArrayList<>();

        List<ExerciseSet> dayGroup = new ArrayList<>();
        String previousSetDate = "";

        for (ExerciseSet set : allSets) {
            String currentSetDate = set.getDate();

            if (!currentSetDate.equals(previousSetDate)) {
                dayGroup = new ArrayList<>();
                setGroupsByDay.add(dayGroup);
            }

            dayGroup.add(set);

            previousSetDate = set.getDate();
        }
        Log.d(TAG, "setCount = " + allSets.size());
        return setGroupsByDay;
    }

    class HistoricalExerciseHolder extends RecyclerView.ViewHolder {
        TextView dateText;

        List<ExerciseSet> sets = new ArrayList<>();
        LinearLayout setsLayout;

        HistoricalExerciseHolder(@NonNull View itemView) {
            super(itemView);

            setsLayout = itemView.findViewById(R.id.sets_layout);
            dateText = itemView.findViewById(R.id.date_title);
        }

        void bind(int position) {
            setsLayout.removeAllViews();
            sets.clear();
            sets.addAll(sortedSetHistory.get(position));

            String date = sets.get(0).getDate();
            dateText.setText(Tools.toHistoricalDate(date));

            LayoutInflater inflater = LayoutInflater.from(activityContext);

            for (ExerciseSet set : sets) {
                View setView = inflater.inflate(R.layout.list_item_set_historical, setsLayout, false);

                TextView weight = setView.findViewById(R.id.weight_quantity);
                weight.setText(String.valueOf(set.getWeight()));

                TextView repsText = setView.findViewById(R.id.reps_quantity);
                repsText.setText(String.valueOf(set.getRepetition()));

                setsLayout.addView(setView);
                Log.d(TAG, "view Added");
            }
        }
    }

}
