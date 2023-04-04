package com.example.booking.ui.UserClickOnMap;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking.R;
import com.example.booking.Utils;
import com.example.booking.adapter.UserClickOnMapSlotsAdapter;
import com.example.booking.interfaces.user_timeslot_Selected_OnclickListner;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class UserClickOnMapShowThatPlaceAllDetails extends AppCompatActivity implements user_timeslot_Selected_OnclickListner {

    int total_price = 0;
    String show_selected_date_data = "";
    HashMap<String, Integer> time_slots_with_price;
    public HashMap<String, String> place_whole_details_hashmap = new HashMap<>();
    HashMap<String, HashMap<String, Integer>> map;
    private UserClickOnMapSlotsAdapter userClickOnMapSlotsAdapter;
    private RecyclerView recycleview_show_available_time_day_slots;

    TextView tv_place_name_user_click_on_map, tv_place_full_address_user_click_on_map, tv_show_calendar_date;
    ImageView iv_user_select_calendar;

    Button btn_book_place_from_user;

    private HashMap<String, Boolean> mSelectedItems_hashmap = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_click_on_map_show_that_place_all_details);

        Intent intent=getIntent();
        String getHashmapPlaceWholeData_String = intent.getStringExtra(Utils.key_whole_place_details);
        place_whole_details_hashmap = Utils.convertStringToHashMap_StringString(getHashmapPlaceWholeData_String);

        recycleview_show_available_time_day_slots = findViewById(R.id.recycleview_show_available_time_day_slots);
        tv_place_name_user_click_on_map = (TextView) findViewById(R.id.tv_place_name_user_click_on_map);
        tv_place_full_address_user_click_on_map = (TextView) findViewById(R.id.tv_place_full_address_user_click_on_map);
        tv_show_calendar_date = (TextView) findViewById(R.id.tv_show_calendar_date);
        iv_user_select_calendar = (ImageView) findViewById(R.id.iv_user_select_calendar);
        btn_book_place_from_user = (Button) findViewById(R.id.btn_book_place_from_user);


        // Initially give today's date
        Calendar calendars = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String todayDate = dateFormat.format(calendars.getTime());
        tv_show_calendar_date.setText(todayDate);

        show_selected_date_data = todayDate;

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
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-



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
                        show_selected_date_data = selectedDate;

                        setHashmapdata(Utils.getDayfromDate(show_selected_date_data));
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


        // Button book
      //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
        btn_book_place_from_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String get_selected_key = getSelectedTimeSlotsKey();
                showCustomDialog(get_selected_key, "= "+total_price+" Rs");
                total_price = 0;
            }
        });
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-



        if(map != null) {
            Log.i("test_response", "map.tostring : "+map.toString());

            // get todays date
            int dayOfWeek = Utils.getDayfromDate(show_selected_date_data);
            setHashmapdata(dayOfWeek);
        }
    }

    public String getSelectedTimeSlotsKey() {
        String selected_time_slots_key = "";
        int i = 0;
        // Loop through the HashMap to check each value
        for (Map.Entry<String, Boolean> entry : mSelectedItems_hashmap.entrySet()) {
            String key = entry.getKey();
            Boolean value = entry.getValue();

            if (value) { // If the value matches the one you are looking for
                i = i + 1;
                selected_time_slots_key =
                        "\n"
                        + selected_time_slots_key
                        + "\n"
                        + i+") "
                        + key
                        + " = "
                        +time_slots_with_price.get(key)
                        + " Rs"
                        + "\n -=--=--=--=-=-=-=-=-=-=-=-=-==-= ";
                Integer slot_price_integer = time_slots_with_price.get(key);
                total_price = total_price + slot_price_integer;
            }
        }
        return selected_time_slots_key.trim();
    }


    //=====================================================================================
    @Override
    protected void onResume() {
        super.onResume();
        refreshRecycleview();
    }
    //=====================================================================================


    //=====================================================================================
    public void refreshRecycleview(){
        if(time_slots_with_price != null) {

//         //Initialize RecyclerView and Adapter
            userClickOnMapSlotsAdapter = new UserClickOnMapSlotsAdapter(time_slots_with_price, this);
            recycleview_show_available_time_day_slots.setAdapter(userClickOnMapSlotsAdapter);
            recycleview_show_available_time_day_slots.setLayoutManager(new GridLayoutManager(this, 2));
// Call notifyDataSetChanged() on the adapter to refresh the data displayed in the RecyclerView
            userClickOnMapSlotsAdapter.notifyDataSetChanged();
// Call invalidate() on the RecyclerView to force it to redraw itself
            recycleview_show_available_time_day_slots.invalidate();

        }
    }
    //=====================================================================================


    //=====================================================================================
    public void setHashmapdata(int dayOfWeek){
        if(map != null) {
            Log.i("test_response", "map.tostring : "+map.toString());

            // Use switch-case to determine the day of the week
            switch (dayOfWeek) {
                case Calendar.SUNDAY:
                    // Code for Sunday
                    time_slots_with_price = map.get("Sun");
                    refreshRecycleview();
                    break;
                case Calendar.MONDAY:
                    // Code for Monday
                    time_slots_with_price = map.get("Mon");
                    refreshRecycleview();
                    break;
                case Calendar.TUESDAY:
                    // Code for Tuesday
                    time_slots_with_price = map.get("Tue");
                    refreshRecycleview();
                    break;
                case Calendar.WEDNESDAY:
                    // Code for Wednesday
                    time_slots_with_price = map.get("Wed");
                    refreshRecycleview();
                    break;
                case Calendar.THURSDAY:
                    // Code for Thursday
                    time_slots_with_price = map.get("Thu");
                    refreshRecycleview();
                    break;
                case Calendar.FRIDAY:
                    // Code for Friday
                    time_slots_with_price = map.get("Fri");
                    refreshRecycleview();
                    break;
                case Calendar.SATURDAY:
                    // Code for Saturday
                    time_slots_with_price = map.get("Sat");
                    refreshRecycleview();

                    break;
            }



        }
    }
    //=====================================================================================


    //=====================================================================================
    @Override
    public void onHashMapClick(HashMap<String, Boolean> hashMap) {
        // Do something with the HashMap object here
        mSelectedItems_hashmap = hashMap;
    }
    //=====================================================================================



    //=====================================================================================
    public void showCustomDialog(String selected_time_slots, String total_bill) {
        Dialog dialog = new Dialog(UserClickOnMapShowThatPlaceAllDetails.this);
        dialog.setContentView(R.layout.booking_review_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);

        //-------------------------------------------------------------------------------
        TextView tv_booking_review_total_bill, tv_booking_review_selected_time_Slots;
        Button booking_review_dialog_payment, booking_review_dialog_cancel;

        tv_booking_review_selected_time_Slots = (TextView) dialog.findViewById(R.id.tv_booking_review_selected_time_Slots);
        tv_booking_review_total_bill = (TextView) dialog.findViewById(R.id.tv_booking_review_total_bill);
        booking_review_dialog_cancel = (Button) dialog.findViewById(R.id.booking_review_dialog_cancel);
        booking_review_dialog_payment = (Button) dialog.findViewById(R.id.booking_review_dialog_payment);
        //-------------------------------------------------------------------------------


        tv_booking_review_selected_time_Slots.setText(selected_time_slots);
        tv_booking_review_total_bill.setText(total_bill);

        booking_review_dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        booking_review_dialog_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    //=====================================================================================



}