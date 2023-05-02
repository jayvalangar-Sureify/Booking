package com.example.booking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
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


//            holder.tv_booking_details_place_address.setText(placeAddress);
//            holder.tv_booking_details_staff_number.setText(staffNumber);
//            holder.tv_latitude_longitude_hide.setText(""+latitude);
//            holder.tv_latitude_longitude_hide.setText(""+longitude);

        } catch (Exception e) {
            e.printStackTrace();
        }
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
