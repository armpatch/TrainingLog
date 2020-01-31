package com.armpatch.android.workouttracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.armpatch.android.workouttracker.R;

import org.threeten.bp.LocalDate;

public class WorkoutPagerAdapter extends PagerAdapter {

    private static final int ITEM_COUNT = 10000;
    public static final int POSITION_TODAY = 5000;

    private Context activityContext;
    private LayoutInflater inflater;
    private WorkoutHolder currentItem;

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
        workoutHolder.update();

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

    public void updateCurrentWorkoutHolder() {
        if (currentItem != null)
            currentItem.update();
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.setPrimaryItem(container, position, object);
        currentItem = (WorkoutHolder) object;
    }

    class WorkoutHolder {
        private View itemView;
        private RecyclerView exerciseGroupRecycler;
        private ExerciseGroupRecyclerAdapter groupAdapter;

        WorkoutHolder(final Context activityContext, LocalDate date) {
            itemView = inflater.inflate(R.layout.content_workout_holder, null);
            setupRecyclerView(activityContext, date);
        }

        private void setupRecyclerView(Context activityContext, LocalDate date) {
            exerciseGroupRecycler = itemView.findViewById(R.id.exercise_recycler);
            exerciseGroupRecycler.setLayoutManager(new LinearLayoutManager(activityContext));
            groupAdapter = new ExerciseGroupRecyclerAdapter(activityContext, date);
            exerciseGroupRecycler.setAdapter(groupAdapter);
        }

        void update() {
            groupAdapter.refresh();
        }
    }
}
