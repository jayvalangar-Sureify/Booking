package com.example.booking.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking.R;
import com.example.booking.interfaces.user_timeslot_Selected_OnclickListner;

import java.util.HashMap;

public class UserClickOnMapSlotsAdapter extends RecyclerView.Adapter<UserClickOnMapSlotsAdapter.ViewHolder> implements user_timeslot_Selected_OnclickListner {

    private HashMap<String, Integer> place_slots_details_hashmap;
    private HashMap<String, Boolean> mSelectedItems = new HashMap<>();

    private user_timeslot_Selected_OnclickListner mListener;

    public UserClickOnMapSlotsAdapter(HashMap<String, Integer> hasmapData, user_timeslot_Selected_OnclickListner listener) {
        place_slots_details_hashmap = hasmapData;
        mListener = listener;

        for (String key : place_slots_details_hashmap.keySet()) {
            mSelectedItems.put(key, false);
        }
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

    public String getItem(int position) {
        return place_slots_details_hashmap.keySet().toArray(new String[0])[position];
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int light_green_color = ContextCompat.getColor(holder.itemView.getContext(), R.color.combo_background_green);
        int light_red_color = ContextCompat.getColor(holder.itemView.getContext(), R.color.background_red);

        String time_slots_key = (String) place_slots_details_hashmap.keySet().toArray()[position];
        Integer price_value = place_slots_details_hashmap.get(time_slots_key);



        holder.tv_show_place_time_slots.setText(time_slots_key);
        holder.tv_show_place_time_slots_price.setText(price_value.toString() + " Rs");


        if (mSelectedItems.get(time_slots_key)) {
            holder.card_view_show_user_time_slots.setBackgroundColor(light_red_color);
        } else {
            holder.card_view_show_user_time_slots.setBackgroundColor(light_green_color);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the selected state of the current item
                mSelectedItems.put(time_slots_key, !mSelectedItems.get(time_slots_key));
                // Update the background color of the CardView based on its selected state
                if (mSelectedItems.get(time_slots_key)) {
                    holder.card_view_show_user_time_slots.setBackgroundColor(light_red_color);
                } else {
                    holder.card_view_show_user_time_slots.setBackgroundColor(light_green_color);
                }

                mListener.onHashMapClick(mSelectedItems);
            }
        });
    }

    @Override
    public int getItemCount() {
        return place_slots_details_hashmap.size();
    }

    @Override
    public void onHashMapClick(HashMap<String, Boolean> hashMap) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_show_place_time_slots_price;
        public TextView tv_show_place_time_slots;
        public CardView card_view_show_user_time_slots;

        public ViewHolder(View itemView) {
            super(itemView);

            card_view_show_user_time_slots = (CardView) itemView.findViewById(R.id.card_view_show_user_time_slots);
            tv_show_place_time_slots_price = (TextView) itemView.findViewById(R.id.tv_show_place_time_slots_price);
            tv_show_place_time_slots = (TextView) itemView.findViewById(R.id.tv_show_place_time_slots);
        }
    }
}
