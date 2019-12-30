package com.armpatch.android.workouttracker;

import android.os.AsyncTask;

import com.armpatch.android.workouttracker.model.WorkoutNote;
import com.armpatch.android.workouttracker.model.WorkoutRepository;

import org.threeten.bp.LocalDate;

class QueryWorkoutTask extends AsyncTask<WorkoutListAdapter.WorkoutViewHolder,Integer, WorkoutQueryResult> {

    private WorkoutListAdapter.WorkoutViewHolder viewHolder;

    @Override
    protected WorkoutQueryResult doInBackground(WorkoutListAdapter.WorkoutViewHolder... workoutViewHolders) {
        viewHolder = workoutViewHolders[0];
        LocalDate currentDate = viewHolder.getDate();

        // query repository
        WorkoutRepository repository = new WorkoutRepository(viewHolder.getContext());
        WorkoutNote note = repository.getWorkoutNote(currentDate);

        // assign result of query to workoutQueryResult Object
        String comment = (note == null)? "" : note.getDate();
        return new WorkoutQueryResult(comment);
    }

    @Override
    protected void onPostExecute(WorkoutQueryResult workoutQueryResult) { // **Performed on the UI thread**
        viewHolder.setComment(workoutQueryResult.getComment());
    }

}
