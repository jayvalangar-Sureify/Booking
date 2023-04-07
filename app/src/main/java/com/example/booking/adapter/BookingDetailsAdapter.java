package com.example.booking.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.booking.R;
import com.example.booking.Utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class BookingDetailsAdapter extends RecyclerView.Adapter<BookingDetailsAdapter.ViewHolder> {

    private LinkedHashMap<String, Object> place_slots_details_linkedhashmap;

    public BookingDetailsAdapter(LinkedHashMap<String, Object> hasmapData) {
        place_slots_details_linkedhashmap = hasmapData;
    }

    public void setData(LinkedHashMap<String, Object> hasMapdata) {
        place_slots_details_linkedhashmap = hasMapdata;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_details_item_card, parent, false);
        return new ViewHolder(view);
    }

    public String getItem(int position) {
        return place_slots_details_linkedhashmap.keySet().toArray(new String[0])[position];
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String time_slots_key = (String) place_slots_details_linkedhashmap.keySet().toArray()[position];
        HashMap<String, String> internal_details = (HashMap<String, String>) place_slots_details_linkedhashmap.get(time_slots_key);


            holder.tv_booking_details_place_time_slots.setText(time_slots_key);


        for (Map.Entry<String, String> entry : internal_details.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();


            if(key.contains(Utils.key_booking_user_id)){

            }
            if(key.contains(Utils.key_booking_owner_id)){

            }
            if(key.contains(Utils.key_booking_date)){
                Log.i("test_date_response", ""+value);

                // Split the date string by space
                String[] dateParts = value.split(" ");

                // Assign day, month, and year variables
                String day = dateParts[0];
                String month = dateParts[1];
                String year = dateParts[2];
                holder.tv_date_day.setText(day);
                holder.tv_date_month.setText(month);
                holder.tv_date_year.setText(year);
            }
            if(key.contains(Utils.key_booking_staff_number)){
              holder.tv_booking_details_staff_number.setText(value);
            }
            if(key.contains(Utils.key_booking_place_name)){

            }
            if(key.contains(Utils.key_booking_place_address)){
             holder.tv_booking_details_place_address.setText(value);
            }
            if(key.contains(Utils.key_booking_place_latitude)){

            }
            if(key.contains(Utils.key_booking_place_longitude)){

            }

        }


    }

    @Override
    public int getItemCount() {
        return place_slots_details_linkedhashmap.size();
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
