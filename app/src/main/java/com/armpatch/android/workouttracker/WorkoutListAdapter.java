package com.armpatch.android.workouttracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.armpatch.android.workouttracker.model.WorkoutNote;

import org.threeten.bp.LocalDate;

import java.util.List;

public class WorkoutListAdapter extends PagerAdapter {

    private static final int ITEM_COUNT = 10000;
    static final int STARTING_ITEM = 5000; // Today

    private LayoutInflater inflater;
    private List<WorkoutNote> workoutNotes;

    WorkoutListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = inflater.inflate(R.layout.content_workout_exercises, null);

        LocalDate localDate = LocalDate.now().plusDays(relativeDay(position));

        // create placeholder for empty exercises
        TextView exercisesPlaceholderText = itemView.findViewById(R.id.exercises_placeholder_text);
        exercisesPlaceholderText.setText("The position is: " + position);

        // set workout notes

        TextView notesTextView = itemView.findViewById(R.id.workout_notes);

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public void setWorkoutNotes(List<WorkoutNote> workoutNotes) {
        this.workoutNotes = workoutNotes;
        notifyDataSetChanged();
    }

    static int relativeDay(int position) {
        return position - STARTING_ITEM;
    }
}
