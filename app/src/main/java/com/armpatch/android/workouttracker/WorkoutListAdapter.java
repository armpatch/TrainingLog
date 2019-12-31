package com.armpatch.android.workouttracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import org.threeten.bp.LocalDate;

public class WorkoutListAdapter extends PagerAdapter {

    private static final int ITEM_COUNT = 10000;
    static final int STARTING_ITEM = 5000; // Today

    private Context activityContext;
    private LayoutInflater inflater;

    WorkoutListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        activityContext = context;
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return ((WorkoutViewHolder)object).getItemView() == view;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LocalDate currentDate = LocalDate.now().plusDays(position - STARTING_ITEM);

        WorkoutViewHolder workoutViewHolder = new WorkoutViewHolder(activityContext, currentDate);
        workoutViewHolder.fetchWorkoutData();

        // add item to container
        container.addView(workoutViewHolder.getItemView());
        return workoutViewHolder;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(((WorkoutViewHolder) object).getItemView());
    }

    static int relativeDays(int position) {
        return position - STARTING_ITEM;
    }

    class WorkoutViewHolder {
        private Context activityContext;
        private LocalDate date;
        private View itemView;
        private TextView comments;

        WorkoutViewHolder(Context activityContext, LocalDate date) {
            this.activityContext = activityContext;
            this.date = date;

            setItemView(inflater.inflate(R.layout.content_workout_exercises, null));
            comments = itemView.findViewById(R.id.workout_notes);
        }

        Context getContext() {
            return activityContext;
        }

        void fetchWorkoutData() {
            new QueryWorkoutTask().execute(this);
        }

        private void setItemView(View v) {
            itemView = v;
        }

        View getItemView() { return itemView; }

        void setComment(String comment) {
            if (comment == null) return;
            comments.setText(comment);
        }

        String getComment() {
            return (String) comments.getText();
        }

        LocalDate getDate() {
            return date;
        }

    }

}
