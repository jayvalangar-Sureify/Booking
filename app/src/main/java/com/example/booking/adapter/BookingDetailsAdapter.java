package com.example.booking.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking.R;
import com.example.booking.Utils;
import com.example.booking.interfaces.OnHistoryDataChangedListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class BookingDetailsAdapter extends RecyclerView.Adapter<BookingDetailsAdapter.ViewHolder> {

    //---------------------------------------------------------------------------------
    private OnHistoryDataChangedListener onHistoryDataChangedListener;
    LinkedHashMap<String, LinkedHashMap<String, String>> history_data_double_linked_list = new LinkedHashMap<>();
    //---------------------------------------------------------------------------------


    List<Map.Entry<String, HashMap<String, String>>> entries = new ArrayList<>();

    private LinkedHashMap<String, Object> place_slots_details_linkedhashmap;

    public BookingDetailsAdapter(LinkedHashMap<String, Object> hasmapData, OnHistoryDataChangedListener listener) {
        this.place_slots_details_linkedhashmap = hasmapData;
        this.onHistoryDataChangedListener = listener;
        sortLinkHashmap();
    }

    public void setData(LinkedHashMap<String, Object> hasMapdata) {
        this.place_slots_details_linkedhashmap = hasMapdata;
        sortLinkHashmap();
        notifyDataSetChanged();
    }

    private void sortLinkHashmap() {
        // Create a list of entries from the original LinkedHashMap
         entries = new ArrayList(place_slots_details_linkedhashmap.entrySet());

// Sort the list by date using a custom Comparator
        Collections.sort(entries, new Comparator<Map.Entry<String, HashMap<String, String>>>() {
            DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
            @Override
            public int compare(Map.Entry<String, HashMap<String, String>> entry1, Map.Entry<String, HashMap<String, String>> entry2) {
                String date1 = entry1.getValue().get(Utils.key_booking_date);
                String date2 = entry2.getValue().get(Utils.key_booking_date);
                try {
                    Date d1 = dateFormat.parse(date1);
                    Date d2 = dateFormat.parse(date2);
                    return d1.compareTo(d2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });

// Add the sorted entries back to the new LinkedHashMap
        for (Map.Entry<String, HashMap<String, String>> entry : entries) {
            place_slots_details_linkedhashmap.put(entry.getKey(), entry.getValue());
        }

        for (Iterator<Map.Entry<String, HashMap<String, String>>> iterator = entries.iterator(); iterator.hasNext();) {
            Map.Entry<String, HashMap<String, String>> entry = iterator.next();
            HashMap<String, String> hashMap = entry.getValue();
            String bookingDate = hashMap.get(Utils.key_booking_date);
            if (Utils.checkIfItIsOldDate(bookingDate)) {
                iterator.remove(); // Remove the current element from the list
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_details_item_card, parent, false);
        return new ViewHolder(view);
    }

    public String getItem(int position) {
        return place_slots_details_linkedhashmap.keySet().toArray(new String[0])[position];
    }

    private boolean checkIfItPastDates(String checkDateString) {
        // Format the date in the format "dd MMM yyyy"
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        String dateStr = checkDateString; // The date to check in string format
        Date dateToCheck = null;

        try {
            dateToCheck = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

// Check if the date is in the past
        if (dateToCheck != null && dateToCheck.before(Calendar.getInstance().getTime())) {
            // The date is in the past
            System.out.println(dateStr + " is in the past.");
            return true;
        } else if (dateToCheck != null && dateToCheck.equals(Calendar.getInstance().getTime())) {
            // The date is today's date
            System.out.println(dateStr + " is today's date.");
            return false;
        } else {
            // The date is in the future
            System.out.println(dateStr + " is in the future.");
            return false;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //history
        LinkedHashMap<String, String> histort_full_details = new LinkedHashMap<>();
        boolean isPastDate = false;
        String temp_single_history_date = "";

        //=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-
        Map.Entry<String, HashMap<String, String>> objectLinkedHashMap = entries.get(position);// assuming the position variable refers to the position of the item in the RecyclerView

        String time_slots_keys = objectLinkedHashMap.getKey();
        HashMap<String, String> hashMapValues = objectLinkedHashMap.getValue();

        System.out.println("====================================================");
        System.out.println("time_slots_keys: " + time_slots_keys);


        String[] parts = time_slots_keys.split(" = ");
        String timeRange = parts[0];
        String price = parts[1];


        holder.tv_booking_details_place_time_slots.setText(timeRange + " (" + price +" Rs)");
        for (Map.Entry<String, String> hashMapEntry : hashMapValues.entrySet()) {
            String key = hashMapEntry.getKey();
            String value = hashMapEntry.getValue();
            System.out.println("Hash key: " + key + ", Hash value: " + value);

            if(key.contains(Utils.key_booking_user_id)){
                histort_full_details.put(Utils.key_booking_user_id, value);
            }
            if(key.contains(Utils.key_booking_owner_id)){
                histort_full_details.put(Utils.key_booking_owner_id, value);
            }
            if(key.contains(Utils.key_booking_date)){
                histort_full_details.put(Utils.key_booking_date, value);

                if(checkIfItPastDates(value)){
                    isPastDate = true;
                    temp_single_history_date = value;
                }

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
                histort_full_details.put(Utils.key_booking_staff_number, value);
                holder.tv_booking_details_staff_number.setText(value);
            }
            if(key.contains(Utils.key_booking_place_name)){
                histort_full_details.put(Utils.key_booking_place_name, value);
            }
            if(key.contains(Utils.key_booking_place_address)){
                histort_full_details.put(Utils.key_booking_place_address, value);
                holder.tv_booking_details_place_address.setText(value);
            }
            if(key.contains(Utils.key_booking_place_latitude)){
                histort_full_details.put(Utils.key_booking_place_latitude, value);
                holder.tv_latitude_longitude_hide.setText(value);
            }
            if(key.contains(Utils.key_booking_place_longitude)){
                histort_full_details.put(Utils.key_booking_place_longitude, value);
                holder.tv_latitude_longitude_hide.setText(holder.tv_latitude_longitude_hide.getText().toString()+","+value.toString());
            }

        }
        System.out.println("====================================================");
        //=-=-=--==-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-


        holder.cv_booking_details_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(holder.itemView.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(getContext(), new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, 1);
                }

                double latitude = 0;
                double longitude = 0;
                LocationManager locationManager = (LocationManager) holder.itemView.getContext().getSystemService(Context.LOCATION_SERVICE);

                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if (location != null) {
                         latitude = location.getLatitude();
                         longitude = location.getLongitude();

                        // Do something with latitude and longitude values


                        String uri = "http://maps.google.com/maps?saddr=" + latitude + "," + longitude + "&daddr=" + holder.tv_latitude_longitude_hide.getText().toString();
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                        holder.itemView.getContext().startActivity(intent);
                    }
                } else {
                    // GPS is disabled, show a dialog to the user to enable it
                }


            }
        });


        holder.cv_booking_details_staff_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String phoneNumber = holder.tv_booking_details_staff_number.getText().toString().trim();
                    Uri uri = Uri.parse("tel:" + phoneNumber);
                    Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                    holder.itemView.getContext().startActivity(intent);
                }catch (Exception e){
                    e.getMessage();
                }
            }
        });



        // Is past data add into history
        //==========================================================================================
        if(isPastDate){
            history_data_double_linked_list.put(temp_single_history_date, histort_full_details);
            if (onHistoryDataChangedListener != null) {
                onHistoryDataChangedListener.onHistoryDataChanged(history_data_double_linked_list);
            }
        }
        //==========================================================================================
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_booking_details_place_time_slots;
        public TextView tv_booking_details_place_address;
        public TextView tv_booking_details_staff_number;
        public TextView tv_latitude_longitude_hide;
        public TextView tv_date_day, tv_date_month, tv_date_year;
        public CardView cv_booking_details_location, cv_booking_details_staff_contact;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_booking_details_place_time_slots = (TextView) itemView.findViewById(R.id.tv_booking_details_place_time_slots);
            tv_booking_details_place_address = (TextView) itemView.findViewById(R.id.tv_booking_details_place_address);
            tv_booking_details_staff_number = (TextView) itemView.findViewById(R.id.tv_booking_details_staff_number);
            tv_latitude_longitude_hide = (TextView) itemView.findViewById(R.id.tv_latitude_longitude_hide);
            tv_date_day = (TextView) itemView.findViewById(R.id.tv_date_day);
            tv_date_month = (TextView) itemView.findViewById(R.id.tv_date_month);
            tv_date_year = (TextView) itemView.findViewById(R.id.tv_date_year);
            cv_booking_details_location = (CardView) itemView.findViewById(R.id.cv_booking_details_location);
            cv_booking_details_staff_contact = (CardView) itemView.findViewById(R.id.cv_booking_details_staff_contact);

        }
    }
}
