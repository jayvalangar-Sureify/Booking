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
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking.R;

import java.util.ArrayList;


public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingHistoryAdapter.ViewHolder> {

        Context context;

    ArrayList<String> history_date_arrayList = new ArrayList<>();
    ArrayList<String> history_timeslotsandprice_arraylist = new ArrayList<>();
    ArrayList<String> history_indetailBookingDetails_arraylist = new ArrayList<>();


    public BookingHistoryAdapter(ArrayList<String> history_date_arrayList, ArrayList<String> history_timeslotsandprice_arraylist, ArrayList<String> history_indetailBookingDetails_arraylist, Context applicationContext) {
        this.history_date_arrayList = history_date_arrayList;
        this.history_timeslotsandprice_arraylist = history_timeslotsandprice_arraylist;
        this.history_indetailBookingDetails_arraylist = history_indetailBookingDetails_arraylist;
        this.context = applicationContext;
    }

    public void setData(ArrayList<String> history_date_arrayList, ArrayList<String> history_timeslotsandprice_arraylist, ArrayList<String> history_indetailBookingDetails_arraylist) {
        this.history_date_arrayList = history_date_arrayList;
        this.history_timeslotsandprice_arraylist = history_timeslotsandprice_arraylist;
        this.history_indetailBookingDetails_arraylist = history_indetailBookingDetails_arraylist;
            notifyDataSetChanged();
        }



    @Override
        public com.example.booking.adapter.BookingHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_history_details_item_card, parent, false);
            return new com.example.booking.adapter.BookingHistoryAdapter.ViewHolder(view);
        }



        @Override
        public void onBindViewHolder(com.example.booking.adapter.BookingHistoryAdapter.ViewHolder holder, int position) {
            String date_string = history_date_arrayList.get(position);
            String time_price_string = history_timeslotsandprice_arraylist.get(position);
            String indetail_string = history_indetailBookingDetails_arraylist.get(position);

            // Date
            //------------------------------------------------------------------------------------------
            String[] dates_parts = date_string.split(" ");
            String day = dates_parts[0];
            String month = dates_parts[1];
            String year = dates_parts[2];
            holder.tv_booking_history_date_day.setText(day);
            holder.tv_booking_history_date_month.setText(month);
            holder.tv_booking_history_date_year.setText(year);
            //------------------------------------------------------------------------------------------


            // Timeslot and Price
            //------------------------------------------------------------------------------------------
            holder.tv_booking_history_details_place_time_slots.setText(time_price_string);
            //------------------------------------------------------------------------------------------


            // Indetails Place address and Contact number
            //------------------------------------------------------------------------------------------
            try {

                String addressKey = "key_booking_place_address=";
                int addressIndex = indetail_string.indexOf(addressKey);
                int endIndex = indetail_string.indexOf(",", addressIndex);
                if (endIndex == -1) {
                    endIndex = indetail_string.indexOf("}", addressIndex);
                }
                String addressValue = indetail_string.substring(addressIndex + addressKey.length(), endIndex);


                holder.tv_booking_history_details_place_address.setText(addressValue);
//            holder.tv_booking_details_staff_number.setText(staffNumber);
//            holder.tv_latitude_longitude_hide.setText(""+latitude);
//            holder.tv_latitude_longitude_hide.setText(""+longitude);

            } catch (Exception e) {
                e.printStackTrace();
            }
            //------------------------------------------------------------------------------------------


            //------------------------------------------------------------------------------------------
            String input = indetail_string;
            // Extracting values one by one using substring() and indexOf() methods
            String userId = input.substring(input.indexOf("key_booking_user_id=") + "key_booking_user_id=".length(), input.indexOf(", ", input.indexOf("key_booking_user_id=")));
            String placeName = input.substring(input.indexOf("key_booking_place_name=") + "key_booking_place_name=".length(), input.indexOf(", ", input.indexOf("key_booking_place_name=")));
            String placeLatitude = input.substring(input.indexOf("key_booking_place_latitude=") + "key_booking_place_latitude=".length(), input.indexOf(", ", input.indexOf("key_booking_place_latitude=")));
            String placeLongitude = input.substring(input.indexOf("key_booking_place_longitude=") + "key_booking_place_longitude=".length(), input.indexOf(", ", input.indexOf("key_booking_place_longitude=")));
            String paymentId = input.substring(input.indexOf("key_booking_payment_id_successful=") + "key_booking_payment_id_successful=".length(), input.indexOf(", ", input.indexOf("key_booking_payment_id_successful=")));
            String placeAddress = input.substring(input.indexOf("key_booking_place_address=") + "key_booking_place_address=".length(), input.indexOf(", ", input.indexOf("key_booking_place_address=")));
            String staffNumber = input.substring(input.indexOf("key_booking_staff_number=") + "key_booking_staff_number=".length(), input.indexOf(", ", input.indexOf("key_booking_staff_number=")));
            String bookingDate = input.substring(input.indexOf("key_booking_date=") + "key_booking_date=".length(), input.indexOf("}", input.indexOf("key_booking_date=")));

// Printing the extracted values
            System.out.println("User ID: " + userId);
            System.out.println("Place Name: " + placeName);
            System.out.println("Place Latitude: " + placeLatitude);
            System.out.println("Place Longitude: " + placeLongitude);
            System.out.println("Payment ID: " + paymentId);
            System.out.println("Place Address: " + placeAddress);
            System.out.println("Staff Number: " + staffNumber);
            System.out.println("Booking Date: " + bookingDate);


            holder.tv_booking_history_details_place_address.setText(placeAddress);
            holder.tv_history_latitude_longitude_hide.setText(placeLatitude+","+placeLongitude);
            holder.tv_booking_history_details_staff_number.setText(staffNumber);

            //------------------------------------------------------------------------------------------




            //------------------------------------------------------------------------------------------

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
            //------------------------------------------------------------------------------------------

        }

        @Override
        public int getItemCount() {
            if (history_date_arrayList.size() == 0) {
                Toast.makeText(context, "No History found", Toast.LENGTH_SHORT).show();
                return 0;
            } else {
                return history_date_arrayList.size();
            }

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
