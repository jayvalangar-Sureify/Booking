package com.example.booking.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking.R;

import java.util.LinkedHashMap;


public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingHistoryAdapter.ViewHolder> {
    LinkedHashMap<String, LinkedHashMap<String, String>> historyData_double_linkedhashmap;

    public BookingHistoryAdapter(LinkedHashMap<String, LinkedHashMap<String, String>> historyData_double_linkedhashmap) {
       this.historyData_double_linkedhashmap = historyData_double_linkedhashmap;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_history_details_item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String key = (String) historyData_double_linkedhashmap.keySet().toArray()[position];
//        LinkedHashMap<String, String> innerMap = historyData_double_linkedhashmap.get(key);
//        String innerKey = (String) innerMap.keySet().toArray()[0];
//        String innerValue = innerMap.get(innerKey);
        holder.tv_booking_history_details_place_time_slots.setText(key);
//        holder.tv_booking_history_details_place_address.setText(innerValue);


        holder.cv_booking_history_details_location.setOnClickListener(new View.OnClickListener() {
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


                        String uri = "http://maps.google.com/maps?saddr=" + latitude + "," + longitude + "&daddr=" + holder.tv_history_latitude_longitude_hide.getText().toString();
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                        holder.itemView.getContext().startActivity(intent);
                    }
                } else {
                    // GPS is disabled, show a dialog to the user to enable it
                }


            }
        });


        holder.cv_booking_history_details_staff_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String phoneNumber = holder.tv_booking_history_details_staff_number.getText().toString().trim();
                    Uri uri = Uri.parse("tel:" + phoneNumber);
                    Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                    holder.itemView.getContext().startActivity(intent);
                }catch (Exception e){
                    e.getMessage();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return historyData_double_linkedhashmap.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_booking_history_details_place_time_slots;
        public TextView tv_booking_history_details_place_address;
        public TextView tv_booking_history_details_staff_number;
        public TextView tv_history_latitude_longitude_hide;
        public TextView tv_booking_history_date_day, tv_booking_history_date_month, tv_booking_history_date_year;
        public CardView cv_booking_history_details_location, cv_booking_history_details_staff_contact;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_booking_history_details_place_time_slots = (TextView) itemView.findViewById(R.id.tv_booking_history_details_place_time_slots);
            tv_booking_history_details_place_address = (TextView) itemView.findViewById(R.id.tv_booking_history_details_place_address);
            tv_booking_history_details_staff_number = (TextView) itemView.findViewById(R.id.tv_booking_history_details_staff_number);
            tv_history_latitude_longitude_hide = (TextView) itemView.findViewById(R.id.tv_history_latitude_longitude_hide);
            tv_booking_history_date_day = (TextView) itemView.findViewById(R.id.tv_booking_history_date_day);
            tv_booking_history_date_month = (TextView) itemView.findViewById(R.id.tv_booking_history_date_month);
            tv_booking_history_date_year = (TextView) itemView.findViewById(R.id.tv_booking_history_date_year);
            cv_booking_history_details_location = (CardView) itemView.findViewById(R.id.cv_booking_history_details_location);
            cv_booking_history_details_staff_contact = (CardView) itemView.findViewById(R.id.cv_booking_history_details_staff_contact);

        }
    }
}
