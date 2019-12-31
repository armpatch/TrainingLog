package com.armpatch.android.workouttracker;

import android.os.AsyncTask;

import com.armpatch.android.workouttracker.model.WorkoutNote;
import com.armpatch.android.workouttracker.model.WorkoutRepository;

import org.threeten.bp.LocalDate;

class QueryWorkoutTask extends AsyncTask<WorkoutListAdapter.WorkoutViewHolder,Integer, WorkoutData> {

    private WorkoutListAdapter.WorkoutViewHolder viewHolder;

    @Override
    protected WorkoutData doInBackground(WorkoutListAdapter.WorkoutViewHolder... workoutViewHolders) {
        viewHolder = workoutViewHolders[0];
        LocalDate currentDate = viewHolder.getDate();

        // query repository
        WorkoutRepository repository = new WorkoutRepository(viewHolder.getContext());
        WorkoutNote note = repository.getWorkoutNote(currentDate);

        // assign result of query to workoutQueryResult Object
        String comment = (note == null)? "" : note.getNote();
        return new WorkoutData(comment);
    }

    @Override
    protected void onPostExecute(WorkoutData workoutData) { // **Performed on the UI thread**
        viewHolder.setComment(workoutData.getComment());
    }

}
