package com.example.booking.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.booking.R;

import java.util.HashMap;

public class UserClickOnMapSlotsAdapter extends RecyclerView.Adapter<UserClickOnMapSlotsAdapter.ViewHolder> {

    private HashMap<String, Integer> place_slots_details_hashmap;

    public UserClickOnMapSlotsAdapter(HashMap<String, Integer> hasmapData) {
        place_slots_details_hashmap = hasmapData;
    }

    public void setData(HashMap<String, Integer> hasMapdata) {
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
        String time_slots_key = (String) place_slots_details_hashmap.keySet().toArray()[position];
        Integer price_value = place_slots_details_hashmap.get(time_slots_key);



        holder.tv_show_place_time_slots.setText(time_slots_key);
        holder.tv_show_place_time_slots_price.setText(price_value.toString() + " Rs");
    }

    @Override
    public int getItemCount() {
        return place_slots_details_hashmap.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_show_place_time_slots_price;
        public TextView tv_show_place_time_slots;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_show_place_time_slots_price = (TextView) itemView.findViewById(R.id.tv_show_place_time_slots_price);
            tv_show_place_time_slots = (TextView) itemView.findViewById(R.id.tv_show_place_time_slots);
        }
    }
}
