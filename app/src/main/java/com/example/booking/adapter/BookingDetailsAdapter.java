package com.example.booking.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking.R;
import com.example.booking.interfaces.user_timeslot_Selected_OnclickListner;

import java.util.HashMap;


public class BookingDetailsAdapter extends RecyclerView.Adapter<BookingDetailsAdapter.ViewHolder> implements user_timeslot_Selected_OnclickListner {

    private HashMap<String, Integer> place_slots_details_hashmap;
    private HashMap<String, Boolean> mSelectedItems = new HashMap<>();

    private user_timeslot_Selected_OnclickListner mListener;

    public BookingDetailsAdapter(HashMap<String, Integer> hasmapData, user_timeslot_Selected_OnclickListner listener) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_details_item_card, parent, false);
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

    }

    @Override
    public int getItemCount() {
        return place_slots_details_hashmap.size();
    }

    @Override
    public void onHashMapClick(HashMap<String, Boolean> hashMap) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_booking_details_place_time_slots;
        public TextView tv_booking_details_place_address;
        public TextView tv_booking_details_staff_number;
        public TextView tv_date_day, tv_date_month, tv_date_year;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_booking_details_place_time_slots = (TextView) itemView.findViewById(R.id.tv_booking_details_place_time_slots);
            tv_booking_details_place_address = (TextView) itemView.findViewById(R.id.tv_booking_details_place_address);
            tv_booking_details_staff_number = (TextView) itemView.findViewById(R.id.tv_booking_details_staff_number);
            tv_date_day = (TextView) itemView.findViewById(R.id.tv_date_day);
            tv_date_month = (TextView) itemView.findViewById(R.id.tv_date_month);
            tv_date_year = (TextView) itemView.findViewById(R.id.tv_date_year);

        }
    }
}
