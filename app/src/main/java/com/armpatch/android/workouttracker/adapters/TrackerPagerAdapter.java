package com.armpatch.android.workouttracker.adapters;

import android.content.Context;
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

public class TrackerPagerAdapter extends PagerAdapter {

    private Context activityContext;
    private SetEditorPage setEditorPage;

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
            itemView = LayoutInflater.from(activityContext).inflate(R.layout.content_set_editor_page, container, false);
            setEditorPage = new SetEditorPage(itemView);
        }

        container.addView(itemView);
        return setEditorPage;
    }

    /**
     * This holder contains the tab for adding and modifying sets for the currently selected exercise
     */
    class SetEditorPage implements TrackerSetAdapter.SelectionCallback{
        View itemView;

        TrackerSetAdapter trackerSetAdapter;
        RecyclerView setRecycler;
        NumberPicker weightPicker;
        NumberPicker repsPicker;
        Button addOrUpdateButton;
        Button deleteButton;

        ExerciseSet currentlySelectedSet;

        SetEditorPage(View itemView) {
            if (itemView == null) {
                throw new IllegalArgumentException("itemView may not be null");
            }
            this.itemView = itemView;

            setupButtons();
            setupNumberPickers();
            setupAdapter();
        }

        private void setupButtons() {
            addOrUpdateButton = itemView.findViewById(R.id.add_set_button);
            addOrUpdateButton.setOnClickListener(v -> addOrUpdateSet());

            deleteButton = itemView.findViewById(R.id.delete_button);
            deleteButton.setVisibility(View.GONE);
            deleteButton.setOnClickListener(v -> deleteSet());

        }

        private void setupNumberPickers() {
            weightPicker = itemView.findViewById(R.id.weight_number_picker);
            repsPicker = itemView.findViewById(R.id.reps_number_picker);

            weightPicker.setMinValue(0);
            weightPicker.setMaxValue(1000);
            repsPicker.setMinValue(0);
            repsPicker.setMaxValue(20);
        }

        void setupAdapter() {
            setRecycler = itemView.findViewById(R.id.recycler_view);
            setRecycler.setLayoutManager(new LinearLayoutManager(activityContext));
            trackerSetAdapter = new TrackerSetAdapter(activityContext, exerciseName, currentDate);
            setRecycler.setAdapter(trackerSetAdapter);
            trackerSetAdapter.retrieveSetsFromDatabase();
            trackerSetAdapter.setSelectionCallback(this);
        }

        private void addOrUpdateSet() {
            if (currentlySelectedSet == null) {
                addExerciseSet();
            } else {
                updateExerciseSet();
            }
        }

        private void addExerciseSet() {
            int weight = weightPicker.getValue();
            int reps = repsPicker.getValue();

            trackerSetAdapter.addSet(weight, reps);
        }

        private void updateExerciseSet() {
            int weight = weightPicker.getValue();
            int reps = repsPicker.getValue();

            trackerSetAdapter.updateSet(currentlySelectedSet, weight, reps);
            deselectSet();
        }

        private void deleteSet() {
            trackerSetAdapter.deleteSet(currentlySelectedSet);
            deselectSet();
        }

        @Override
        public void onSetHolderClicked(ExerciseSet clickedSet) {
            if (currentlySelectedSet == clickedSet) {
                deselectSet();
            } else {
                selectSet(clickedSet);
            }
        }

        private void deselectSet() {
            deleteButton.setVisibility(View.GONE);
            currentlySelectedSet = null;
            //trackerSetAdapter.removeSelectionIndication();
        }

        private void selectSet(ExerciseSet set) {
            currentlySelectedSet = set;
            deleteButton.setVisibility(View.VISIBLE);
            weightPicker.setValue((int) set.getMeasurement1());
            repsPicker.setValue((int) set.getMeasurement2());
            //trackerSetAdapter.showAsSelected(currentlySelectedSet);
        }
    }
}
