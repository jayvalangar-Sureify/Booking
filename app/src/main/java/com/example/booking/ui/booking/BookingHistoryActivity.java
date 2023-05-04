package com.example.booking.ui.booking;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking.R;
import com.example.booking.adapter.BookingHistoryAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class BookingHistoryActivity extends AppCompatActivity {

    RecyclerView booking_history_details_recycle_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        booking_history_details_recycle_view = (RecyclerView) findViewById(R.id.booking_history_details_recycle_view);

        LinkedHashMap<String, Object> loggedIn_user_data_linked_hashmap = new LinkedHashMap<>();

        // retrieve the ArrayList objects from the Intent
        ArrayList<String> history_date_arrayList = getIntent().getStringArrayListExtra("history_date");
        ArrayList<String> history_timeslotsandprice_arraylist = getIntent().getStringArrayListExtra("history_timeslots_and_price");
        ArrayList<String> history_indetailBookingDetails_arraylist = getIntent().getStringArrayListExtra("history_booking_details");

        //------------------------------------------------------------------------------------------
        BookingHistoryAdapter adapter = new BookingHistoryAdapter(history_date_arrayList, history_timeslotsandprice_arraylist, history_indetailBookingDetails_arraylist, getApplicationContext());
        booking_history_details_recycle_view.setAdapter(adapter);
        booking_history_details_recycle_view.setLayoutManager(new GridLayoutManager(BookingHistoryActivity.this, 1));
// Call notifyDataSetChanged() on the adapter to refresh the data displayed in the RecyclerView
        adapter.notifyDataSetChanged();
// Call invalidate() on the RecyclerView to force it to redraw itself
        booking_history_details_recycle_view.invalidate();
        //------------------------------------------------------------------------------------------
    }
}