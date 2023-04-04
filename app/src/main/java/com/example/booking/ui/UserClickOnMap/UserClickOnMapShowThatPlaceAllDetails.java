package com.example.booking.ui.UserClickOnMap;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking.R;
import com.example.booking.Utils;
import com.example.booking.adapter.UserClickOnMapSlotsAdapter;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;


public class UserClickOnMapShowThatPlaceAllDetails extends AppCompatActivity {


    HashMap<String, Integer> time_slots_with_price;
    public HashMap<String, String> place_whole_details_hashmap = new HashMap<>();
    HashMap<String, HashMap<String, Integer>> map;
    private UserClickOnMapSlotsAdapter userClickOnMapSlotsAdapter;
    private RecyclerView recycleview_show_available_time_day_slots;

    TextView tv_place_name_user_click_on_map, tv_place_full_address_user_click_on_map, tv_show_calendar_date;
    ImageView iv_user_select_calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_click_on_map_show_that_place_all_details);

        Intent intent=getIntent();
        String getHashmapPlaceWholeData_String = intent.getStringExtra(Utils.key_whole_place_details);
        place_whole_details_hashmap = Utils.convertStringToHashMap_StringString(getHashmapPlaceWholeData_String);

        tv_place_name_user_click_on_map = (TextView) findViewById(R.id.tv_place_name_user_click_on_map);
        tv_place_full_address_user_click_on_map = (TextView) findViewById(R.id.tv_place_full_address_user_click_on_map);
        tv_show_calendar_date = (TextView) findViewById(R.id.tv_show_calendar_date);
        iv_user_select_calendar = (ImageView) findViewById(R.id.iv_user_select_calendar);


        // Initially give today's date
        Calendar calendars = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String todayDate = dateFormat.format(calendars.getTime());
        tv_show_calendar_date.setText(todayDate);

        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
        iv_user_select_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Initialize calendar instance
                Calendar calendar = Calendar.getInstance();

// Create a Date Picker Dialog and set the date range
                DatePickerDialog datePickerDialog = new DatePickerDialog(UserClickOnMapShowThatPlaceAllDetails.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = String.format("%02d %s %04d", dayOfMonth, new DateFormatSymbols().getShortMonths()[month], year);
                        tv_show_calendar_date.setText(selectedDate);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
//                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis() - 1000);
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis() - 1000);
                calendar.add(Calendar.DATE, 10);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis() - 1000);

                datePickerDialog.show();

            }
        });
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-



        place_whole_details_hashmap.entrySet().forEach(entry -> {
            // seperating latitude and longitude from key
            String key = entry.getKey();
            String remove_forward_space_value = entry.getValue().replace("/", "");
            String without_slash_value = remove_forward_space_value.replace("\\", "");


            Log.i("test_response", "KEY : "+key);
            Log.i("test_response", "VALUE : "+without_slash_value);

            if(key.equals("time_slots_converting_hashmap_to_string")){
                Gson gson = new Gson();
                Type typeOfHashMap = new TypeToken<HashMap<String, HashMap<String, Integer>>>() {}.getType();
                map = gson.fromJson(without_slash_value, typeOfHashMap);

            }


            if(key.equals("owner_place_name_string")){
                tv_place_name_user_click_on_map.setText(remove_forward_space_value);
            }

            if(key.equals("owner_place_full_address_string")){
                tv_place_full_address_user_click_on_map.setText(remove_forward_space_value);
            }
        });


        if(map != null) {


            // Get today's date
            Calendar calendar = Calendar.getInstance();
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

           // Use switch-case to determine the day of the week
            switch (dayOfWeek) {
                case Calendar.SUNDAY:
                    // Code for Sunday
                    time_slots_with_price = map.get("Sunday");
                    break;
                case Calendar.MONDAY:
                    // Code for Monday
                    time_slots_with_price = map.get("Monday");
                    break;
                case Calendar.TUESDAY:
                    // Code for Tuesday
                    time_slots_with_price = map.get("Tuesday");
                    break;
                case Calendar.WEDNESDAY:
                    // Code for Wednesday
                    time_slots_with_price = map.get("Wednesday");
                    break;
                case Calendar.THURSDAY:
                    // Code for Thursday
                    time_slots_with_price = map.get("Thursday");
                    break;
                case Calendar.FRIDAY:
                    // Code for Friday
                    time_slots_with_price = map.get("Friday");
                    break;
                case Calendar.SATURDAY:
                    // Code for Saturday
                    time_slots_with_price = map.get("Saturday");
                    break;
            }


            if(time_slots_with_price != null) {

//         //Initialize RecyclerView and Adapter
                recycleview_show_available_time_day_slots = findViewById(R.id.recycleview_show_available_time_day_slots);
                userClickOnMapSlotsAdapter = new UserClickOnMapSlotsAdapter(time_slots_with_price);
                recycleview_show_available_time_day_slots.setAdapter(userClickOnMapSlotsAdapter);
                recycleview_show_available_time_day_slots.setLayoutManager(new GridLayoutManager(this, 2));
            }
        }
    }
}