package com.armpatch.android.workouttracker.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.armpatch.android.workouttracker.R;
import com.armpatch.android.workouttracker.model.ExerciseSet;
import com.armpatch.android.workouttracker.model.WorkoutRepository;

import java.util.List;

public class TrackerPagerAdapter extends PagerAdapter {

    private Context activityContext;
    private TrackerPageHolder trackerPageHolder;

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
            trackerPageHolder = new TrackerPageHolder(itemView);
        }

        container.addView(itemView);
        return trackerPageHolder;
    }

    class TrackerPageHolder {
        View itemView;

        TrackerSetAdapter trackerSetAdapter;
        RecyclerView setRecycler;

        TrackerPageHolder(View itemView) {
            if (itemView == null) {
                throw new IllegalArgumentException("itemView may not be null");
            }
            this.itemView = itemView;

            new GetSetsTask().execute();
        }

        void setAdapter(List<ExerciseSet> sets) {
            trackerSetAdapter = new TrackerSetAdapter(sets);
            setRecycler = itemView.findViewById(R.id.recycler_view);
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
            trackerPageHolder.setAdapter(sets);
        }
    }
}
