package com.armpatch.android.workouttracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.armpatch.android.workouttracker.component.FloatNumberChooser;
import com.armpatch.android.workouttracker.component.IntegerNumberChooser;
import com.armpatch.android.workouttracker.R;
import com.armpatch.android.workouttracker.model.ExerciseSet;

public class TrackerPagerAdapter extends PagerAdapter {

    private Context activityContext;
    private SetEditorPage setEditorPage;
    private HistoryPage historyPage;

    private String currentDate;
    private String exerciseName;

    public TrackerPagerAdapter(Context activityContext, String currentDate, String exerciseName) {
        this.activityContext = activityContext;
        this.currentDate = currentDate;
        this.exerciseName = exerciseName;
    }

    public void loadHistoryPage() {
        historyPage.refresh();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return activityContext.getString(R.string.tracker_page_title_1);
        } else {
            return activityContext.getString(R.string.tracker_page_title_2);
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        if (object instanceof SetEditorPage) {
            return ((SetEditorPage) object).itemView == view;
        }

        if (object instanceof HistoryPage) {
            return ((HistoryPage) object).itemView == view;
        }

        return false;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        if (position == 0) {
            View itemView = LayoutInflater.from(activityContext).inflate(R.layout.content_page_set_editor, container, false);
            setEditorPage = new SetEditorPage(itemView);
            container.addView(itemView);
            return setEditorPage;
        } else {
            View itemView = LayoutInflater.from(activityContext).inflate(R.layout.content_page_exercise_history, container, false);
            historyPage = new HistoryPage(itemView);
            container.addView(itemView);
            return historyPage;
        }
    }

    /**
     * This holder contains the tab for adding and modifying sets for the currently selected exercise
     */
    class SetEditorPage implements TrackerSetAdapter.SetSelectionCallback {
        View itemView;

        TrackerSetAdapter trackerSetAdapter;
        RecyclerView setRecycler;
        FloatNumberChooser weightChooser;
        IntegerNumberChooser repsChooser;
        Button addUpdateButton;
        Button deleteButton;

        ExerciseSet selectedSet;

        SetEditorPage(View itemView) {
            if (itemView == null) {
                throw new IllegalArgumentException("itemView may not be null");
            }
            this.itemView = itemView;

            setupNumberChoosers();
            setupButtons();
            setupRecycler();
        }

        private void setupNumberChoosers() {
            View weightChooserLayout = itemView.findViewById(R.id.weight_picker);
            View repsChooserLayout = itemView.findViewById(R.id.reps_picker);

            weightChooser = new FloatNumberChooser(weightChooserLayout, "Weight (lbs)", 5.0f, 2.5f);
            repsChooser = new IntegerNumberChooser(repsChooserLayout, "Reps", 5, 1);
        }

        private void setupButtons() {
            addUpdateButton = itemView.findViewById(R.id.update_button);
            addUpdateButton.setOnClickListener(v -> {
                if (selectedSet == null) addExerciseSet(); else updateExerciseSet();
            });

            deleteButton = itemView.findViewById(R.id.delete_button);
            deleteButton.setEnabled(false);
            deleteButton.setOnClickListener(v -> deleteSet());
        }

        void setupRecycler() {
            setRecycler = itemView.findViewById(R.id.recycler_view);
            setRecycler.setLayoutManager(new LinearLayoutManager(activityContext));
            trackerSetAdapter = new TrackerSetAdapter(activityContext, exerciseName, currentDate);
            setRecycler.setAdapter(trackerSetAdapter);
            trackerSetAdapter.retrieveSetsFromDatabase();
            trackerSetAdapter.setSelectionCallback(this);
        }

        private void addExerciseSet() {
            Float weight = weightChooser.getValue();
            Integer reps = repsChooser.getValue();

            trackerSetAdapter.addSet(weight, reps);
        }

        private void updateExerciseSet() {
            float weight = weightChooser.getValue();
            Integer reps = repsChooser.getValue();

            selectedSet.setWeight(weight);
            selectedSet.setRepetition(reps);

            trackerSetAdapter.updateSet(selectedSet);
            deselectSet();
        }

        private void deleteSet() {
            trackerSetAdapter.deleteSet(selectedSet);
            deselectSet();
        }

        @Override
        public void onSetHolderClicked(ExerciseSet clickedSet) {
            if (selectedSet == clickedSet) {
                deselectSet();
            } else {
                selectSet(clickedSet);
            }
        }

        private void deselectSet() {
            deleteButton.setEnabled(false);
            addUpdateButton.setText(activityContext.getString(R.string.add_set_button_text));

            trackerSetAdapter.removeAllHighlights();
            selectedSet = null;
        }

        private void selectSet(ExerciseSet set) {
            selectedSet = set;
            deleteButton.setEnabled(true);
            addUpdateButton.setText(activityContext.getString(R.string.update_button_text));
            weightChooser.updateValue(set.getWeight());
            repsChooser.updateValue(set.getRepetition());
            trackerSetAdapter.highlightSet(set);
        }
    }

    class HistoryPage {
        View itemView;

        RecyclerView recyclerView;
        SetHistoryAdapter setHistoryAdapter;

        public HistoryPage(View itemView) {
            if (itemView == null) {
                throw new IllegalArgumentException("itemView may not be null");
            }
            this.itemView = itemView;

            recyclerView = itemView.findViewById(R.id.recycler_view);
            setHistoryAdapter = new SetHistoryAdapter(activityContext, exerciseName);
            recyclerView.setLayoutManager(new LinearLayoutManager(activityContext));
            recyclerView.setAdapter(setHistoryAdapter);
        }

        void refresh() {
            setHistoryAdapter.refresh();
        }

    }
}
