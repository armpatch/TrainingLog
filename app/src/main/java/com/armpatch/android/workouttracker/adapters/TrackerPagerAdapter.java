package com.armpatch.android.workouttracker.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.armpatch.android.workouttracker.R;
import com.armpatch.android.workouttracker.model.ExerciseSet;
import com.armpatch.android.workouttracker.model.WorkoutRepository;

import java.util.List;

public class TrackerPagerAdapter extends PagerAdapter {

    private Context activityContext;
    private SetEditorPageHolder setEditorPageHolder;

    private String currentDate;
    private String exerciseName;

    public TrackerPagerAdapter(Context activityContext, String currentDate, String exerciseName) {
        this.activityContext = activityContext;
        this.currentDate = currentDate;
        this.exerciseName = exerciseName;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return true;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = new View(activityContext);

        if (position == 0) {
            itemView = LayoutInflater.from(activityContext).inflate(R.layout.content_tracker_layout, container, false);
            setEditorPageHolder = new SetEditorPageHolder(itemView);
        }

        container.addView(itemView);
        return setEditorPageHolder;
    }

    /**
     * This holder contains the tab for adding and modifying sets for the currently selected exercise
     */
    class SetEditorPageHolder {
        View itemView;

        TrackerSetAdapter trackerSetAdapter;
        RecyclerView setRecycler;
        NumberPicker weightPicker;
        NumberPicker repsPicker;
        Button addSetButton;

        SetEditorPageHolder(View itemView) {
            if (itemView == null) {
                throw new IllegalArgumentException("itemView may not be null");
            }
            this.itemView = itemView;

            findViews();
            setNumberPickerRanges();

            new GetSetsTask().execute();
        }

        private void findViews() {
            weightPicker = itemView.findViewById(R.id.weight_number_picker);
            repsPicker = itemView.findViewById(R.id.reps_number_picker);
            addSetButton = itemView.findViewById(R.id.add_set_button);
            addSetButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addSetToAdapter();
                }
            });
        }

        void addSetToAdapter() {
            float weight = weightPicker.getValue();
            float reps = repsPicker.getValue();

            trackerSetAdapter.addSet(weight, reps);
        }

        private void setNumberPickerRanges() {
            weightPicker.setMinValue(0);
            weightPicker.setMaxValue(1000);
            repsPicker.setMinValue(0);
            repsPicker.setMaxValue(20);
        }

        void setNumberPickerStartingValue() {
            ExerciseSet lastSet = trackerSetAdapter.sets.get(trackerSetAdapter.sets.size() - 1);
            int weight = (int) lastSet.getMeasurement1();
            int reps = (int) lastSet.getMeasurement2();

            weightPicker.setValue(weight);
            repsPicker.setValue(reps);
        }

        void setupAdapter(List<ExerciseSet> sets) {
            if (sets.size() == 0) {
                trackerSetAdapter = TrackerSetAdapter.newExercise(activityContext, exerciseName, currentDate);
            } else {
                trackerSetAdapter = TrackerSetAdapter.existingExercise(activityContext, sets);
                setNumberPickerStartingValue();
            }

            setRecycler = itemView.findViewById(R.id.recycler_view);
            setRecycler.setLayoutManager(new LinearLayoutManager(activityContext));
            setRecycler.setAdapter(trackerSetAdapter);
        }
    }

    class GetSetsTask extends AsyncTask<Void, Void, Void> {

        List<ExerciseSet> sets;

        @Override
        protected Void doInBackground(Void... voids) {
            WorkoutRepository repository = new WorkoutRepository(activityContext);
            sets = repository.getExerciseSets(currentDate, exerciseName);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            setEditorPageHolder.setupAdapter(sets);
        }
    }
}
