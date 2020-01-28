package com.armpatch.android.workouttracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.armpatch.android.workouttracker.R;

public class TrackerPagerAdapter extends PagerAdapter {

    Context activityContext;

    public TrackerPagerAdapter(Context activityContext) {
        this.activityContext = activityContext;
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

        itemView = LayoutInflater.from(activityContext).inflate(R.layout.content_tracker_layout, container, false);

        container.addView(itemView);
        return itemView;
    }
}
