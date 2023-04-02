package com.example.booking.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.booking.R;

import java.util.HashMap;

public class UserClickOnMapSlotsAdapter extends RecyclerView.Adapter<UserClickOnMapSlotsAdapter.ViewHolder> {

    private HashMap<String, HashMap<String, Integer>> place_slots_details_hashmap;

    public UserClickOnMapSlotsAdapter(HashMap<String, HashMap<String, Integer>> hasmapData) {
        place_slots_details_hashmap = hasmapData;
    }

    public void setData(HashMap<String, HashMap<String, Integer>> hasMapdata) {
        place_slots_details_hashmap = hasMapdata;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_click_on_map_slots_adapter_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String key = (String) place_slots_details_hashmap.keySet().toArray()[position];
        HashMap<String, Integer> values = place_slots_details_hashmap.get(key);



        holder.tv_show_place_time_slots.setText(key);

        holder.tv_show_place_date_slots.setText(values.toString());
    }

    @Override
    public int getItemCount() {
        return place_slots_details_hashmap.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_show_place_date_slots;
        public TextView tv_show_place_time_slots;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_show_place_date_slots = (TextView) itemView.findViewById(R.id.tv_show_place_date_slots);
            tv_show_place_time_slots = (TextView) itemView.findViewById(R.id.tv_show_place_time_slots);
        }
    }
}
