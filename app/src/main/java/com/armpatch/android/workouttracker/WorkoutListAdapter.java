package com.armpatch.android.workouttracker;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.armpatch.android.workouttracker.model.WorkoutNote;
import com.armpatch.android.workouttracker.model.WorkoutRepository;

import org.threeten.bp.LocalDate;

public class WorkoutListAdapter extends PagerAdapter {

    private static final int ITEM_COUNT = 10000;
    static final int STARTING_ITEM = 5000; // Today

    private LayoutInflater inflater;
    private WorkoutRepository repository;

    WorkoutListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        repository = new WorkoutRepository(context);
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

        // query and set workout notes
        TextView notesTextView = itemView.findViewById(R.id.workout_notes);
        LocalDate itemDate = LocalDate.now().plusDays(getDaysFromToday(position));
        QueryWorkoutNotesTask task = new QueryWorkoutNotesTask(notesTextView);
        task.execute(itemDate);


        // add item to container
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    static int getDaysFromToday(int position) {
        return position - STARTING_ITEM;
    }

    class QueryWorkoutNotesTask extends AsyncTask<LocalDate,Integer,String> {

        TextView textView;

        QueryWorkoutNotesTask(TextView textView) {
            this.textView = textView;
        }

        @Override
        protected String doInBackground(LocalDate... dates) {
            WorkoutNote note = repository.getWorkoutNote(dates[0]);

            return (note == null)?
                    "" :
                    note.getDate();
        }

        @Override
        protected void onPostExecute(String s) {
            textView.setText(s);
        }
    }
}
