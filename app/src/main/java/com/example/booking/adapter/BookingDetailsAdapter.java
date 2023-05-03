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
import com.example.booking.ui.booking.BookingDetailsFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class BookingDetailsAdapter extends RecyclerView.Adapter<BookingDetailsAdapter.ViewHolder> {

    ArrayList<String> date_arrayList = new ArrayList<>();
    ArrayList<String> timeslotsandprice_arraylist = new ArrayList<>();
    ArrayList<String> indetailBookingDetails_arraylist = new ArrayList<>();
    Context context;

    public BookingDetailsAdapter(ArrayList<String> date_arrayList, ArrayList<String> timeslotsandprice_arraylist, ArrayList<String> indetailBookingDetails_arraylist, BookingDetailsFragment bookingDetailsFragment) {
    this.date_arrayList = date_arrayList;
    this.timeslotsandprice_arraylist = timeslotsandprice_arraylist;
    this.indetailBookingDetails_arraylist = indetailBookingDetails_arraylist;
    this.context = bookingDetailsFragment.getActivity();
    }

    public void setData(ArrayList<String> date_arrayList, ArrayList<String> timeslotsandprice_arraylist, ArrayList<String> indetailBookingDetails_arraylist, BookingDetailsFragment bookingDetailsFragment) {
        this.date_arrayList = date_arrayList;
        this.timeslotsandprice_arraylist = timeslotsandprice_arraylist;
        this.indetailBookingDetails_arraylist = indetailBookingDetails_arraylist;
        this.context = bookingDetailsFragment.getActivity();
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_details_item_card, parent, false);
        return new ViewHolder(view);
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
        String date_string = date_arrayList.get(position);
        String time_price_string = timeslotsandprice_arraylist.get(position);
        String indetail_string = indetailBookingDetails_arraylist.get(position);

        // Date
        //------------------------------------------------------------------------------------------
        String[] dates_parts = date_string.split(" ");
        String day = dates_parts[0];
        String month = dates_parts[1];
        String year = dates_parts[2];
        holder.tv_date_day.setText(day);
        holder.tv_date_month.setText(month);
        holder.tv_date_year.setText(year);
        //------------------------------------------------------------------------------------------


        // Timeslot and Price
        //------------------------------------------------------------------------------------------
        holder.tv_booking_details_place_time_slots.setText(time_price_string);
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


            holder.tv_booking_details_place_address.setText(addressValue);
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


        holder.tv_booking_details_place_address.setText(placeAddress);
        holder.tv_latitude_longitude_hide.setText(placeLatitude+","+placeLongitude);
        holder.tv_booking_details_staff_number.setText(staffNumber);

        //------------------------------------------------------------------------------------------




        //------------------------------------------------------------------------------------------

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
        //------------------------------------------------------------------------------------------

    }

    @Override
    public int getItemCount() {
        return date_arrayList.size();
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
