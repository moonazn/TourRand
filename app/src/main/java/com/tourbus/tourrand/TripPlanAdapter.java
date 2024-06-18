package com.tourbus.tourrand;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TripPlanAdapter extends RecyclerView.Adapter<TripPlanViewHolder> {

    private Context context;
    private List<TripPlan> tripPlanList;

    public TripPlanAdapter(Context context, List<TripPlan> tripPlanList) {
        this.context = context;
        this.tripPlanList = tripPlanList;
    }

    @NonNull
    @Override
    public TripPlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_trip_plan, parent, false);
        return new TripPlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripPlanViewHolder holder, int position) {
        TripPlan tripPlan = tripPlanList.get(position);
        holder.bind(tripPlan);
    }

    @Override
    public int getItemCount() {
        return tripPlanList.size();
    }
}
