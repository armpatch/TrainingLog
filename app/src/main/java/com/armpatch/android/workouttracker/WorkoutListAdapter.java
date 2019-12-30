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
        return ((WorkoutViewHolder)object).getItemView() == view;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = inflater.inflate(R.layout.content_workout_exercises, null);
        LocalDate currentDate = LocalDate.now().plusDays(position - STARTING_ITEM);
        WorkoutViewHolder workoutViewHolder = new WorkoutViewHolder(itemView,currentDate);

        // query data
        workoutViewHolder.updateData();

        // add item to container
        container.addView(itemView);
        return workoutViewHolder;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(((WorkoutViewHolder) object).itemView);
    }

    static int getDaysFromToday(int position) {
        return position - STARTING_ITEM;
    }

    class WorkoutViewHolder {
        private LocalDate itemDate;
        View itemView;
        private TextView notesTextView;

        WorkoutViewHolder(@NonNull View itemView, LocalDate itemDate) {
            if (itemView == null) {
                throw new IllegalArgumentException("itemView may not be null");
            }
            this.itemView = itemView;
            this.itemDate = itemDate;

            notesTextView = itemView.findViewById(R.id.workout_notes);

        }

        void updateData() {
            new QueryWorkoutTask().execute(this);
        }

        View getItemView() { return itemView; }

        void setComment(String comment) {
            if (comment == null) return;
            notesTextView.setText(comment);
        }

        String getComment() {
            return (String) notesTextView.getText();
        }

        LocalDate getItemDate() {
            return itemDate;
        }

    }

    class QueryWorkoutTask extends AsyncTask<WorkoutViewHolder,Integer, WorkoutQueryResult> {

        WorkoutViewHolder viewHolder;

        @Override
        protected WorkoutQueryResult doInBackground(WorkoutViewHolder... workoutViewHolders) {
            viewHolder = workoutViewHolders[0];
            LocalDate currentDate = viewHolder.getItemDate();

            // query repository
            WorkoutNote note = repository.getWorkoutNote(currentDate);

            // assign result of query to workoutQueryResult Object
            String comment = (note == null)? "" : note.getDate();
            return new WorkoutQueryResult(comment);
        }

        @Override
        protected void onPostExecute(WorkoutQueryResult workoutQueryResult) { // **Performed on the UI thread**
            viewHolder.setComment(workoutQueryResult.comment);
        }

    }

    class WorkoutQueryResult {
        String comment;
        //List<WorkoutSet> TODO: add this data type

        WorkoutQueryResult (String comment) {
            this.comment = comment;
        }
    }

}
