package com.example.booking.ui.booking;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking.R;
import com.example.booking.adapter.BookingHistoryAdapter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class BookingHistoryActivity extends AppCompatActivity {

    RecyclerView booking_history_details_recycle_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        booking_history_details_recycle_view = (RecyclerView) findViewById(R.id.booking_history_details_recycle_view);

        LinkedHashMap<String, LinkedHashMap<String, String>> historyData_double_linkedhashmap = new LinkedHashMap<>();

        Serializable extra = getIntent().getSerializableExtra("historyData");
        if (extra instanceof HashMap) {
            historyData_double_linkedhashmap = new LinkedHashMap<>((HashMap<String, LinkedHashMap<String, String>>) extra);
        } else if (extra instanceof LinkedHashMap) {
            historyData_double_linkedhashmap = (LinkedHashMap<String, LinkedHashMap<String, String>>) extra;
        } else {
            // handle error case
        }
        //------------------------------------------------------------------------------------------
        BookingHistoryAdapter adapter = new BookingHistoryAdapter(historyData_double_linkedhashmap);
        booking_history_details_recycle_view.setAdapter(adapter);
        booking_history_details_recycle_view.setLayoutManager(new GridLayoutManager(BookingHistoryActivity.this, 1));
// Call notifyDataSetChanged() on the adapter to refresh the data displayed in the RecyclerView
        adapter.notifyDataSetChanged();
// Call invalidate() on the RecyclerView to force it to redraw itself
        booking_history_details_recycle_view.invalidate();
        //------------------------------------------------------------------------------------------
    }
}