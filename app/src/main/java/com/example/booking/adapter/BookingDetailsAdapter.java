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

        String[] parts = time_slots_key.split(" = ");
        String timeRange = parts[0];
        String price = parts[1];


        holder.tv_booking_details_place_time_slots.setText(timeRange + " (" + price +" Rs)");


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
            holder.tv_latitude_longitude_hide.setText(value);
            }
            if(key.contains(Utils.key_booking_place_longitude)){
            holder.tv_latitude_longitude_hide.setText(holder.tv_latitude_longitude_hide.getText().toString()+","+value.toString());
            }

        }


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
    }

    @Override
    public int getItemCount() {
        return place_slots_details_linkedhashmap.size();
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
