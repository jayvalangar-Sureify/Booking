package com.example.booking.adapter;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking.R;
import com.example.booking.interfaces.user_timeslot_Selected_OnclickListner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UserClickOnMapSlotsAdapter extends RecyclerView.Adapter<UserClickOnMapSlotsAdapter.ViewHolder> implements user_timeslot_Selected_OnclickListner {


    // Finally, create a new LinkedHashMap to preserve the sorted order, and put the sorted entries in it
    LinkedHashMap<String, Integer> sortedTimeSlots = new LinkedHashMap<>();

    String user_selected_date_string;
    HashMap<String, HashMap<String, Boolean>> get_is_already_booking_done_date_time_userid_hahmap;
    private HashMap<String, Integer> place_slots_details_hashmap;
    private HashMap<String, Boolean> mSelectedItems = new HashMap<>();
    private HashMap<String, Boolean> mAlreadyBookedItems = new HashMap<>();

    private user_timeslot_Selected_OnclickListner mListener;

    public UserClickOnMapSlotsAdapter(HashMap<String, Integer> hasmapData, String user_selected_date_string, HashMap<String, HashMap<String, Boolean>> get_is_already_booking_done_date_time_userid_hahmap, user_timeslot_Selected_OnclickListner listener) {
        this.place_slots_details_hashmap = hasmapData;
        mListener = listener;
        this.get_is_already_booking_done_date_time_userid_hahmap = get_is_already_booking_done_date_time_userid_hahmap;
        this.user_selected_date_string = user_selected_date_string;

        sortPlaceSlotsDetails(place_slots_details_hashmap);

        for (String key : place_slots_details_hashmap.keySet()) {
            mSelectedItems.put(key, false);
            mAlreadyBookedItems.put(key, false);
        }

        if(get_is_already_booking_done_date_time_userid_hahmap != null) {
            readIsAlreadySlotsBooked();
        }
    }

    private void sortPlaceSlotsDetails(HashMap<String, Integer> place_slots_details_hashmap) {
        // Then, create a list of entries from the HashMap
        List<Map.Entry<String, Integer>> timeslotList = new ArrayList<>(place_slots_details_hashmap.entrySet());

// Next, sort the list of entries by timeslot
        Collections.sort(timeslotList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
                // Extract the hour and AM/PM designation from the start times in the keys
                String startTime1 = entry1.getKey().substring(0, 2);
                String startAMPM1 = entry1.getKey().substring(3, 5);
                String startTime2 = entry2.getKey().substring(0, 2);
                String startAMPM2 = entry2.getKey().substring(3, 5);

                // Convert the hour to an integer for easier comparison
                int hour1 = Integer.parseInt(startTime1);
                int hour2 = Integer.parseInt(startTime2);

                // If the AM/PM designation is "PM", add 12 to the hour to convert to 24-hour time
                if (startAMPM1.equals("PM")) {
                    hour1 += 12;
                }
                if (startAMPM2.equals("PM")) {
                    hour2 += 12;
                }

                // Compare the hours
                int result = Integer.compare(hour1, hour2);

                // If the hours are the same, compare the AM/PM designation
                if (result == 0) {
                    result = startAMPM1.compareTo(startAMPM2);
                }

                return result;
            }
        });

        sortedTimeSlots = new LinkedHashMap<>();
        // Finally, loop through the sorted list and print out the timeslots and prices
        for (Map.Entry<String, Integer> timeslot : timeslotList) {
            System.out.println(timeslot.getKey() + " - " + timeslot.getValue());

            sortedTimeSlots.put(timeslot.getKey(), timeslot.getValue());
        }


    }

    private void readIsAlreadySlotsBooked() {
        // assuming the HashMap is stored in a variable called 'outerMap'
        for (String get_booked_date_key : get_is_already_booking_done_date_time_userid_hahmap.keySet()) {

            if(get_booked_date_key.contains(user_selected_date_string)) {
                HashMap<String, Boolean> timeslots_userid_innerMap = get_is_already_booking_done_date_time_userid_hahmap.get(get_booked_date_key);
                for (String get_book_timeslot_key : timeslots_userid_innerMap.keySet()) {
                    String timeRange_only = get_book_timeslot_key.substring(0, 14).trim();

                    mAlreadyBookedItems.put(timeRange_only, true);
                }
            }
        }
    }

    public void setData(HashMap<String, Integer> hasMapdata) {
        place_slots_details_hashmap = hasMapdata;
        sortPlaceSlotsDetails(place_slots_details_hashmap);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_click_on_map_slots_adapter_card, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int light_green_color = ContextCompat.getColor(holder.itemView.getContext(), R.color.combo_background_green);
        int light_red_color = ContextCompat.getColor(holder.itemView.getContext(), R.color.background_red);
        int light_grey_color = ContextCompat.getColor(holder.itemView.getContext(), R.color.grey_1);


        String time_slots_key = (String) sortedTimeSlots.keySet().toArray()[position];
        Integer price_value = sortedTimeSlots.get(time_slots_key);



        holder.tv_show_place_time_slots.setText(time_slots_key);
        holder.tv_show_place_time_slots_price.setText(price_value.toString() + " Rs");



            if(mAlreadyBookedItems.get(time_slots_key)){
                holder.card_view_show_user_time_slots.setBackgroundColor(light_grey_color);
            }else{
                if (mSelectedItems.get(time_slots_key)) {
                    holder.card_view_show_user_time_slots.setBackgroundColor(light_red_color);
                }else {
                    holder.card_view_show_user_time_slots.setBackgroundColor(light_green_color);
                }
            }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardView cardView = holder.card_view_show_user_time_slots;
                Drawable backgroundDrawable = cardView.getBackground();
                int card_backgroundColor = 0;

                if (backgroundDrawable instanceof ColorDrawable) {
                    card_backgroundColor = ((ColorDrawable) backgroundDrawable).getColor();
                }

                if(card_backgroundColor != light_grey_color) {
                    // Toggle the selected state of the current item
                    mSelectedItems.put(time_slots_key, !mSelectedItems.get(time_slots_key));
                    // Update the background color of the CardView based on its selected state
                    if (mSelectedItems.get(time_slots_key)) {
                        holder.card_view_show_user_time_slots.setBackgroundColor(light_red_color);
                    } else {
                        holder.card_view_show_user_time_slots.setBackgroundColor(light_green_color);
                    }

                    mListener.onHashMapClick(mSelectedItems);
                }else {
                    Toast.makeText(holder.itemView.getContext(),"Sorry, Already Booked", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return sortedTimeSlots.size();
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
