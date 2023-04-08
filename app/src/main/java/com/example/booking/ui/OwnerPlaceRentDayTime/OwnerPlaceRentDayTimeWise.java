package com.example.booking.ui.OwnerPlaceRentDayTime;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.booking.R;
import com.example.booking.Utils;
import com.example.booking.ui.Owner_Add_Place_Details.OwnerAddPlaceDetails;
import com.google.gson.Gson;

import java.util.HashMap;

public class OwnerPlaceRentDayTimeWise extends AppCompatActivity {

    public Dialog review_all_Day_rent_dialog;

    HashMap<String, ArrayMap<String, Integer>> submit_day_wise_rent_hashmap = new HashMap<>();
    ArrayMap<String, Integer> monday_day_time_rent_arrayhashmap = new ArrayMap<>();
    ArrayMap<String, Integer> tuesday_day_time_rent_arrayhashmap = new ArrayMap<>();
    ArrayMap<String, Integer> wednesday_day_time_rent_arrayhashmap = new ArrayMap<>();
    ArrayMap<String, Integer> thursday_day_time_rent_arrayhashmap = new ArrayMap<>();
    ArrayMap<String, Integer> friday_day_time_rent_arrayhashmap = new ArrayMap<>();
    ArrayMap<String, Integer> saturday_day_time_rent_arrayhashmap = new ArrayMap<>();
    ArrayMap<String, Integer> sunday_day_time_rent_arrayhashmap = new ArrayMap<>();


    Double owner_place_latitude, owner_place_longitude;
    String per_hour_defauly_rent_String = "0";


    //-----------------------------------------------------------------------------------------------------------------------------
    EditText et_default_rent, et_place_rent_00a_01a, et_place_rent_01a_02a, et_place_rent_02a_03a, et_place_rent_03a_04a, et_place_rent_04a_05a, et_place_rent_05a_06a, et_place_rent_06a_07a, et_place_rent_07a_08a, et_place_rent_08a_09a, et_place_rent_09a_10a, et_place_rent_10a_11a, et_place_rent_11a_12p, et_place_rent_12p_01p, et_place_rent_01p_02p, et_place_rent_02p_03p, et_place_rent_03p_04p, et_place_rent_04p_05p, et_place_rent_05p_06p, et_place_rent_06p_07p, et_place_rent_07p_08p, et_place_rent_08p_09p, et_place_rent_09p_10p, et_place_rent_10p_11p, et_place_rent_11p_12a;
    TextView tv_error_rent_hour_dat_activity;
    RelativeLayout rl_progressbar_rent_hour_dayr;
    Button btn_add_place_rent, btn_done_save_all_Rent, btn_review_all_day_rents;
    public static HashMap<String, Integer> days_selected_hashmap = new HashMap<>();
    //-----------------------------------------------------------------------------------------------------------------------------


    //-----------------------------------------------------------------------------------------------------------------------------
    ArrayAdapter<String> place_rent_day_spinner_adapter;
    Spinner place_rent_day_spinner;
    String[] day_wise_spinner = {
            "Add Same Rent For All Days",
            "Add Rent For Monday",
            "Add Rent For Tuesday",
            "Add Rent For Wednesday",
            "Add Rent For Thursday",
            "Add Rent For Friday",
            "Add Rent For Saturday",
            "Add Rent For Sunday"
    };
    //-----------------------------------------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_rent_hour_day_time);

        // get latitude and longitude
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        Intent intent=getIntent();
        owner_place_latitude = intent.getDoubleExtra(Utils.key_latitude, 0);
        owner_place_longitude = intent.getDoubleExtra(Utils.key_longitude, 0);
        Log.i("test_response", "");
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


        // Initialization
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // set default all day  selected
        days_selected_hashmap.put(Utils.key_monday, 1);
        days_selected_hashmap.put(Utils.key_tuesday, 1);
        days_selected_hashmap.put(Utils.key_wednesday, 1);
        days_selected_hashmap.put(Utils.key_thursday, 1);
        days_selected_hashmap.put(Utils.key_friday, 1);
        days_selected_hashmap.put(Utils.key_saturday, 1);
        days_selected_hashmap.put(Utils.key_sunday, 1);


        place_rent_day_spinner = findViewById(R.id.place_rent_day_spinner);
        place_rent_day_spinner_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, day_wise_spinner);


        btn_add_place_rent = findViewById(R.id.btn_add_place_rent);
        btn_done_save_all_Rent = findViewById(R.id.btn_done_save_all_Rent);
        btn_review_all_day_rents = findViewById(R.id.btn_review_all_day_rents);

        tv_error_rent_hour_dat_activity = findViewById(R.id.tv_error_rent_hour_dat_activity);

        rl_progressbar_rent_hour_dayr = findViewById(R.id.rl_progressbar_rent_hour_dayr);

        et_default_rent = findViewById(R.id.et_default_rent);
        et_place_rent_00a_01a = findViewById(R.id.et_place_rent_00a_01a);
        et_place_rent_01a_02a = findViewById(R.id.et_place_rent_01a_02a);
        et_place_rent_02a_03a = findViewById(R.id.et_place_rent_02a_03a);
        et_place_rent_03a_04a = findViewById(R.id.et_place_rent_03a_04a);
        et_place_rent_04a_05a = findViewById(R.id.et_place_rent_04a_05a);
        et_place_rent_05a_06a = findViewById(R.id.et_place_rent_05a_06a);
        et_place_rent_06a_07a = findViewById(R.id.et_place_rent_06a_07a);
        et_place_rent_07a_08a = findViewById(R.id.et_place_rent_07a_08a);
        et_place_rent_08a_09a = findViewById(R.id.et_place_rent_08a_09a);
        et_place_rent_09a_10a = findViewById(R.id.et_place_rent_09a_10a);
        et_place_rent_10a_11a = findViewById(R.id.et_place_rent_10a_11a);
        et_place_rent_11a_12p = findViewById(R.id.et_place_rent_11a_12p);
        et_place_rent_12p_01p = findViewById(R.id.et_place_rent_12p_01p);
        et_place_rent_01p_02p = findViewById(R.id.et_place_rent_01p_02p);
        et_place_rent_02p_03p = findViewById(R.id.et_place_rent_02p_03p);
        et_place_rent_03p_04p = findViewById(R.id.et_place_rent_03p_04p);
        et_place_rent_04p_05p = findViewById(R.id.et_place_rent_04p_05p);
        et_place_rent_05p_06p = findViewById(R.id.et_place_rent_05p_06p);
        et_place_rent_06p_07p = findViewById(R.id.et_place_rent_06p_07p);
        et_place_rent_07p_08p = findViewById(R.id.et_place_rent_07p_08p);
        et_place_rent_08p_09p = findViewById(R.id.et_place_rent_08p_09p);
        et_place_rent_09p_10p = findViewById(R.id.et_place_rent_09p_10p);
        et_place_rent_10p_11p = findViewById(R.id.et_place_rent_10p_11p);
        et_place_rent_11p_12a = findViewById(R.id.et_place_rent_11p_12a);
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


        //set defaults value
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        et_place_rent_00a_01a.setText(per_hour_defauly_rent_String);
        et_place_rent_01a_02a.setText(per_hour_defauly_rent_String);
        et_place_rent_02a_03a.setText(per_hour_defauly_rent_String);
        et_place_rent_03a_04a.setText(per_hour_defauly_rent_String);
        et_place_rent_04a_05a.setText(per_hour_defauly_rent_String);
        et_place_rent_05a_06a.setText(per_hour_defauly_rent_String);
        et_place_rent_06a_07a.setText(per_hour_defauly_rent_String);
        et_place_rent_07a_08a.setText(per_hour_defauly_rent_String);
        et_place_rent_08a_09a.setText(per_hour_defauly_rent_String);
        et_place_rent_09a_10a.setText(per_hour_defauly_rent_String);
        et_place_rent_10a_11a.setText(per_hour_defauly_rent_String);
        et_place_rent_11a_12p.setText(per_hour_defauly_rent_String);
        et_place_rent_12p_01p.setText(per_hour_defauly_rent_String);
        et_place_rent_01p_02p.setText(per_hour_defauly_rent_String);
        et_place_rent_02p_03p.setText(per_hour_defauly_rent_String);
        et_place_rent_03p_04p.setText(per_hour_defauly_rent_String);
        et_place_rent_04p_05p.setText(per_hour_defauly_rent_String);
        et_place_rent_05p_06p.setText(per_hour_defauly_rent_String);
        et_place_rent_06p_07p.setText(per_hour_defauly_rent_String);
        et_place_rent_07p_08p.setText(per_hour_defauly_rent_String);
        et_place_rent_08p_09p.setText(per_hour_defauly_rent_String);
        et_place_rent_09p_10p.setText(per_hour_defauly_rent_String);
        et_place_rent_10p_11p.setText(per_hour_defauly_rent_String);
        et_place_rent_11p_12a.setText(per_hour_defauly_rent_String);
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // Create a new ArrayAdapter and set it as the adapter for the spinner
        place_rent_day_spinner_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, day_wise_spinner);
        place_rent_day_spinner_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, day_wise_spinner);

        place_rent_day_spinner.setAdapter(place_rent_day_spinner_adapter);
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

        tv_error_rent_hour_dat_activity.setVisibility(View.GONE);
        if(submit_day_wise_rent_hashmap.isEmpty()){
            saveDayWiseRentInHashmap(monday_day_time_rent_arrayhashmap, getString(R.string.monday));
            saveDayWiseRentInHashmap(tuesday_day_time_rent_arrayhashmap, getString(R.string.tuesday));
            saveDayWiseRentInHashmap(wednesday_day_time_rent_arrayhashmap, getString(R.string.wednesday));
            saveDayWiseRentInHashmap(thursday_day_time_rent_arrayhashmap, getString(R.string.thursday));
            saveDayWiseRentInHashmap(friday_day_time_rent_arrayhashmap, getString(R.string.friday));
            saveDayWiseRentInHashmap(saturday_day_time_rent_arrayhashmap, getString(R.string.saturday));
            saveDayWiseRentInHashmap(sunday_day_time_rent_arrayhashmap, getString(R.string.sunday));

        }

        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        place_rent_day_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();

                        switch(selectedItem) {
                            case "Add Same Rent For All Days":
                                btn_add_place_rent.setText(getResources().getString(R.string.save_all_day_rent));
                                break;
                            case "Add Rent For Monday":
                                btn_add_place_rent.setText(getResources().getString(R.string.save_monday_rent));
                                break;
                            case "Add Rent For Tuesday":
                                btn_add_place_rent.setText(getResources().getString(R.string.save_tuesday_rent));
                                break;
                            case "Add Rent For Wednesday":
                                btn_add_place_rent.setText(getResources().getString(R.string.save_wednesday_rent));
                                break;
                            case "Add Rent For Thursday":
                                btn_add_place_rent.setText(getResources().getString(R.string.save_thursday_rent));
                                break;
                            case "Add Rent For Friday":
                                btn_add_place_rent.setText(getResources().getString(R.string.save_friday_rent));
                                break;
                            case "Add Rent For Saturday":
                                btn_add_place_rent.setText(getResources().getString(R.string.save_saturday_rent));
                                break;
                            case "Add Rent For Sunday":
                                btn_add_place_rent.setText(getResources().getString(R.string.save_sunday_rent));
                                break;

                            default:
                                break;
                        }

            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=



        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        btn_review_all_day_rents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the custom dialog
                review_all_Day_rent_dialog = new Dialog(OwnerPlaceRentDayTimeWise.this);
                review_all_Day_rent_dialog.setContentView(R.layout.review_all_day_rent_custom_dialog);
                review_all_Day_rent_dialog.setCancelable(true);

                TextView tv_monday_value_review = (TextView) review_all_Day_rent_dialog.findViewById(R.id.tv_monday_value_review);
                TextView tv_tuesday_value_review = (TextView) review_all_Day_rent_dialog.findViewById(R.id.tv_tuesday_value_review);
                TextView tv_wednesday_value_review = (TextView) review_all_Day_rent_dialog.findViewById(R.id.tv_wednesday_value_review);
                TextView tv_thursday_value_review = (TextView) review_all_Day_rent_dialog.findViewById(R.id.tv_thursday_value_review);
                TextView tv_friday_value_review = (TextView) review_all_Day_rent_dialog.findViewById(R.id.tv_friday_value_review);
                TextView tv_saturday_value_review = (TextView) review_all_Day_rent_dialog.findViewById(R.id.tv_saturday_value_review);
                TextView tv_sunday_value_review = (TextView) review_all_Day_rent_dialog.findViewById(R.id.tv_sunday_value_review);

                Button btn_close_review_dialog = (Button) review_all_Day_rent_dialog.findViewById(R.id.btn_close_review_dialog);



                // Print out the keys and values
                for (String key : submit_day_wise_rent_hashmap.keySet()) {

                    switch (key) {
                        case "Mon":
                            tv_monday_value_review.setText("" + getPrintData(key));
                            break;
                        case "Tue":
                            tv_tuesday_value_review.setText("" + getPrintData(key));
                            break;
                        case "Wed":
                            tv_wednesday_value_review.setText("" + getPrintData(key));
                            break;
                        case "Thu":
                            tv_thursday_value_review.setText("" + getPrintData(key));
                            break;
                        case "Fri":
                            tv_friday_value_review.setText("" + getPrintData(key));
                            break;
                        case "Sat":
                            tv_saturday_value_review.setText("" + getPrintData(key));
                            break;
                        case "Sun":
                            tv_sunday_value_review.setText("" + getPrintData(key));
                            break;
                    }
                }


                btn_close_review_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        review_all_Day_rent_dialog.dismiss();
                    }
                });

                // Show the custom dialog
                review_all_Day_rent_dialog.show();

        }

            });
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        btn_done_save_all_Rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(submit_day_wise_rent_hashmap.isEmpty()){
                    tv_error_rent_hour_dat_activity.setVisibility(View.VISIBLE);
                    tv_error_rent_hour_dat_activity.setText("Plase Enter Default Rent and Click on Save Button");
                }else{
                    tv_error_rent_hour_dat_activity.setVisibility(View.GONE);
                    Intent intent = new Intent(OwnerPlaceRentDayTimeWise.this, OwnerAddPlaceDetails.class);
                    intent.putExtra(Utils.key_latitude,owner_place_latitude);
                    intent.putExtra(Utils.key_longitude,owner_place_longitude);
                    String submit_day_wise_rent_hashmap_json_String = new Gson().toJson(submit_day_wise_rent_hashmap);
                    intent.putExtra(Utils.map_key_owner_place_day_hour_rent_hashmap, submit_day_wise_rent_hashmap_json_String);
                    startActivity(intent);
                    finish();
                }

            }
        });
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        et_default_rent.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s != null){
                    setDefaultValueForAllSlots(s.toString());
                }
            }
        });
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        btn_add_place_rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String et_place_rent_00a_01a_string = et_place_rent_00a_01a.getText().toString();
                String et_place_rent_01a_02a_string = et_place_rent_01a_02a.getText().toString();
                String et_place_rent_02a_03a_string = et_place_rent_02a_03a.getText().toString();
                String et_place_rent_03a_04a_string = et_place_rent_03a_04a.getText().toString();
                String et_place_rent_04a_05a_string = et_place_rent_04a_05a.getText().toString();
                String et_place_rent_05a_06a_string =  et_place_rent_05a_06a.getText().toString();
                String et_place_rent_06a_07a_string =  et_place_rent_06a_07a.getText().toString();
                String et_place_rent_07a_08a_string =   et_place_rent_07a_08a.getText().toString();
                String et_place_rent_08a_09a_string =  et_place_rent_08a_09a.getText().toString();
                String et_place_rent_09a_10a_string = et_place_rent_09a_10a.getText().toString();
                String et_place_rent_10a_11a_string = et_place_rent_10a_11a.getText().toString();
                String et_place_rent_11a_12p_string =  et_place_rent_11a_12p.getText().toString();
                String et_place_rent_12p_01p_string =   et_place_rent_12p_01p.getText().toString();
                String et_place_rent_01p_02p_string =  et_place_rent_01p_02p.getText().toString();
                String et_place_rent_02p_03p_string =  et_place_rent_02p_03p.getText().toString();
                String et_place_rent_03p_04p_string =  et_place_rent_03p_04p.getText().toString();
                String et_place_rent_04p_05p_string =  et_place_rent_04p_05p.getText().toString();
                String et_place_rent_05p_06p_string = et_place_rent_05p_06p.getText().toString();
                String et_place_rent_06p_07p_string = et_place_rent_06p_07p.getText().toString();
                String et_place_rent_07p_08p_string = et_place_rent_07p_08p.getText().toString();
                String et_place_rent_08p_09p_string = et_place_rent_08p_09p.getText().toString();
                String et_place_rent_09p_10p_string = et_place_rent_09p_10p.getText().toString();
                String et_place_rent_10p_11p_string = et_place_rent_10p_11p.getText().toString();
                String et_place_rent_11p_12a_string = et_place_rent_11p_12a.getText().toString();

                if(TextUtils.isEmpty(et_place_rent_00a_01a_string)){
                    et_place_rent_00a_01a.setError(getString(R.string.enter_valid_rent));
                    return;
                }
                if(TextUtils.isEmpty(et_place_rent_01a_02a_string)){
                    et_place_rent_01a_02a.setError(getString(R.string.enter_valid_rent));
                    return;
                }
                if(TextUtils.isEmpty(et_place_rent_02a_03a_string)){
                    et_place_rent_02a_03a.setError(getString(R.string.enter_valid_rent));
                    return;
                }
                if(TextUtils.isEmpty(et_place_rent_03a_04a_string)){
                    et_place_rent_03a_04a.setError(getString(R.string.enter_valid_rent));
                    return;
                }
                if(TextUtils.isEmpty(et_place_rent_04a_05a_string)){
                    et_place_rent_04a_05a.setError(getString(R.string.enter_valid_rent));
                    return;
                }
                if(TextUtils.isEmpty(et_place_rent_05a_06a_string)){
                    et_place_rent_05a_06a.setError(getString(R.string.enter_valid_rent));
                    return;
                }
                if(TextUtils.isEmpty(et_place_rent_06a_07a_string)){
                    et_place_rent_06a_07a.setError(getString(R.string.enter_valid_rent));
                    return;
                }
                if(TextUtils.isEmpty(et_place_rent_07a_08a_string)){
                    et_place_rent_07a_08a.setError(getString(R.string.enter_valid_rent));
                    return;
                }
                if(TextUtils.isEmpty(et_place_rent_08a_09a_string)){
                    et_place_rent_08a_09a.setError(getString(R.string.enter_valid_rent));
                    return;
                }
                if(TextUtils.isEmpty(et_place_rent_09a_10a_string)){
                    et_place_rent_09a_10a.setError(getString(R.string.enter_valid_rent));
                    return;
                }
                if(TextUtils.isEmpty(et_place_rent_10a_11a_string)){
                    et_place_rent_10a_11a.setError(getString(R.string.enter_valid_rent));
                    return;
                }
                if(TextUtils.isEmpty(et_place_rent_11a_12p_string)){
                    et_place_rent_11a_12p.setError(getString(R.string.enter_valid_rent));
                    return;
                }
                if(TextUtils.isEmpty(et_place_rent_12p_01p_string)){
                    et_place_rent_12p_01p.setError(getString(R.string.enter_valid_rent));
                    return;
                }
                if(TextUtils.isEmpty(et_place_rent_01p_02p_string)){
                    et_place_rent_01p_02p.setError(getString(R.string.enter_valid_rent));
                    return;
                }
                if(TextUtils.isEmpty(et_place_rent_02p_03p_string)){
                    et_place_rent_02p_03p.setError(getString(R.string.enter_valid_rent));
                    return;
                }
                if(TextUtils.isEmpty(et_place_rent_03p_04p_string)){
                    et_place_rent_03p_04p.setError(getString(R.string.enter_valid_rent));
                    return;
                }
                if(TextUtils.isEmpty(et_place_rent_04p_05p_string)){
                    et_place_rent_04p_05p.setError(getString(R.string.enter_valid_rent));
                    return;
                }
                if(TextUtils.isEmpty(et_place_rent_05p_06p_string)){
                    et_place_rent_05p_06p.setError(getString(R.string.enter_valid_rent));
                    return;
                }
                if(TextUtils.isEmpty(et_place_rent_06p_07p_string)){
                    et_place_rent_06p_07p.setError(getString(R.string.enter_valid_rent));
                    return;
                }
                if(TextUtils.isEmpty(et_place_rent_07p_08p_string)){
                    et_place_rent_07p_08p.setError(getString(R.string.enter_valid_rent));
                    return;
                }
                if(TextUtils.isEmpty(et_place_rent_08p_09p_string)){
                    et_place_rent_08p_09p.setError(getString(R.string.enter_valid_rent));
                    return;
                }
                if(TextUtils.isEmpty(et_place_rent_09p_10p_string)){
                    et_place_rent_09p_10p.setError(getString(R.string.enter_valid_rent));
                    return;
                }
                if(TextUtils.isEmpty(et_place_rent_10p_11p_string)){
                    et_place_rent_10p_11p.setError(getString(R.string.enter_valid_rent));
                    return;
                }
                if(TextUtils.isEmpty(et_place_rent_11p_12a_string)){
                    et_place_rent_11p_12a.setError(getString(R.string.enter_valid_rent));
                    return;
                }


                rl_progressbar_rent_hour_dayr.setVisibility(View.VISIBLE);

                String current_text_on_button = btn_add_place_rent.getText().toString();




                switch (current_text_on_button){

                    case "Save All Day Rent" :
                        monday_day_time_rent_arrayhashmap = new ArrayMap<>();
                        tuesday_day_time_rent_arrayhashmap = new ArrayMap<>();
                        wednesday_day_time_rent_arrayhashmap = new ArrayMap<>();
                        thursday_day_time_rent_arrayhashmap = new ArrayMap<>();
                        friday_day_time_rent_arrayhashmap = new ArrayMap<>();
                        saturday_day_time_rent_arrayhashmap = new ArrayMap<>();
                        sunday_day_time_rent_arrayhashmap = new ArrayMap<>();
                        saveDayWiseRentInHashmap(monday_day_time_rent_arrayhashmap, getString(R.string.monday));
                        saveDayWiseRentInHashmap(tuesday_day_time_rent_arrayhashmap, getString(R.string.tuesday));
                        saveDayWiseRentInHashmap(wednesday_day_time_rent_arrayhashmap, getString(R.string.wednesday));
                        saveDayWiseRentInHashmap(thursday_day_time_rent_arrayhashmap, getString(R.string.thursday));
                        saveDayWiseRentInHashmap(friday_day_time_rent_arrayhashmap, getString(R.string.friday));
                        saveDayWiseRentInHashmap(saturday_day_time_rent_arrayhashmap, getString(R.string.saturday));
                        saveDayWiseRentInHashmap(sunday_day_time_rent_arrayhashmap, getString(R.string.sunday));
                         break;

                    case "Save Monday Rent" :
                        monday_day_time_rent_arrayhashmap = new ArrayMap<>();
                        saveDayWiseRentInHashmap(monday_day_time_rent_arrayhashmap, getString(R.string.monday));
                         break;

                    case "Save Tuesday Rent" :
                        tuesday_day_time_rent_arrayhashmap = new ArrayMap<>();
                        saveDayWiseRentInHashmap(tuesday_day_time_rent_arrayhashmap, getString(R.string.tuesday));
                        break;

                    case "Save Wednesday Rent" :
                        wednesday_day_time_rent_arrayhashmap = new ArrayMap<>();
                        saveDayWiseRentInHashmap(wednesday_day_time_rent_arrayhashmap, getString(R.string.wednesday));
                        break;

                    case "Save Thursday Rent" :
                        thursday_day_time_rent_arrayhashmap = new ArrayMap<>();
                        saveDayWiseRentInHashmap(thursday_day_time_rent_arrayhashmap, getString(R.string.thursday));
                        break;

                    case "Save Friday Rent" :
                        friday_day_time_rent_arrayhashmap = new ArrayMap<>();
                        saveDayWiseRentInHashmap(friday_day_time_rent_arrayhashmap, getString(R.string.friday));
                        break;

                    case "Save Saturday Rent" :
                        saturday_day_time_rent_arrayhashmap = new ArrayMap<>();
                        saveDayWiseRentInHashmap(saturday_day_time_rent_arrayhashmap, getString(R.string.saturday));
                        break;

                    case "Save Sunday Rent" :
                        sunday_day_time_rent_arrayhashmap = new ArrayMap<>();
                        saveDayWiseRentInHashmap(sunday_day_time_rent_arrayhashmap, getString(R.string.sunday));
                        break;
                    default:
                }


                Toast.makeText(getApplicationContext(), "Data Added Successfully !", Toast.LENGTH_LONG).show();
                rl_progressbar_rent_hour_dayr.setVisibility(View.GONE);
            }
        });
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    }

    public void setDefaultValueForAllSlots(String dafault_value){

        et_place_rent_00a_01a.setText(dafault_value);
        et_place_rent_01a_02a.setText(dafault_value);
        et_place_rent_02a_03a.setText(dafault_value);
        et_place_rent_03a_04a.setText(dafault_value);
        et_place_rent_04a_05a.setText(dafault_value);
        et_place_rent_05a_06a.setText(dafault_value);
        et_place_rent_06a_07a.setText(dafault_value);
        et_place_rent_07a_08a.setText(dafault_value);
        et_place_rent_08a_09a.setText(dafault_value);
        et_place_rent_09a_10a.setText(dafault_value);
        et_place_rent_10a_11a.setText(dafault_value);
        et_place_rent_11a_12p.setText(dafault_value);
        et_place_rent_12p_01p.setText(dafault_value);
        et_place_rent_01p_02p.setText(dafault_value);
        et_place_rent_02p_03p.setText(dafault_value);
        et_place_rent_03p_04p.setText(dafault_value);
        et_place_rent_04p_05p.setText(dafault_value);
        et_place_rent_05p_06p.setText(dafault_value);
        et_place_rent_06p_07p.setText(dafault_value);
        et_place_rent_07p_08p.setText(dafault_value);
        et_place_rent_08p_09p.setText(dafault_value);
        et_place_rent_09p_10p.setText(dafault_value);
        et_place_rent_10p_11p.setText(dafault_value);
        et_place_rent_11p_12a.setText(dafault_value);


    }


    public void saveDayWiseRentInHashmap(ArrayMap<String, Integer> day_arrayMap, String key_for_submit_hash_map){

        String et_place_rent_00a_01a_string = et_place_rent_00a_01a.getText().toString();
        String et_place_rent_01a_02a_string = et_place_rent_01a_02a.getText().toString();
        String et_place_rent_02a_03a_string = et_place_rent_02a_03a.getText().toString();
        String et_place_rent_03a_04a_string = et_place_rent_03a_04a.getText().toString();
        String et_place_rent_04a_05a_string = et_place_rent_04a_05a.getText().toString();
        String et_place_rent_05a_06a_string =  et_place_rent_05a_06a.getText().toString();
        String et_place_rent_06a_07a_string =  et_place_rent_06a_07a.getText().toString();
        String et_place_rent_07a_08a_string =   et_place_rent_07a_08a.getText().toString();
        String et_place_rent_08a_09a_string =  et_place_rent_08a_09a.getText().toString();
        String et_place_rent_09a_10a_string = et_place_rent_09a_10a.getText().toString();
        String et_place_rent_10a_11a_string = et_place_rent_10a_11a.getText().toString();
        String et_place_rent_11a_12p_string =  et_place_rent_11a_12p.getText().toString();
        String et_place_rent_12p_01p_string =   et_place_rent_12p_01p.getText().toString();
        String et_place_rent_01p_02p_string =  et_place_rent_01p_02p.getText().toString();
        String et_place_rent_02p_03p_string =  et_place_rent_02p_03p.getText().toString();
        String et_place_rent_03p_04p_string =  et_place_rent_03p_04p.getText().toString();
        String et_place_rent_04p_05p_string =  et_place_rent_04p_05p.getText().toString();
        String et_place_rent_05p_06p_string = et_place_rent_05p_06p.getText().toString();
        String et_place_rent_06p_07p_string = et_place_rent_06p_07p.getText().toString();
        String et_place_rent_07p_08p_string = et_place_rent_07p_08p.getText().toString();
        String et_place_rent_08p_09p_string = et_place_rent_08p_09p.getText().toString();
        String et_place_rent_09p_10p_string = et_place_rent_09p_10p.getText().toString();
        String et_place_rent_10p_11p_string = et_place_rent_10p_11p.getText().toString();
        String et_place_rent_11p_12a_string = et_place_rent_11p_12a.getText().toString();


        day_arrayMap.put(getString(R.string.slot_12A_01A), Integer.parseInt(et_place_rent_00a_01a_string));
        day_arrayMap.put(getString(R.string.slot_01A_02A), Integer.parseInt(et_place_rent_01a_02a_string));
        day_arrayMap.put(getString(R.string.slot_02A_03P), Integer.parseInt(et_place_rent_02a_03a_string));
        day_arrayMap.put(getString(R.string.slot_03A_04A), Integer.parseInt(et_place_rent_03a_04a_string));
        day_arrayMap.put(getString(R.string.slot_04A_05A), Integer.parseInt(et_place_rent_04a_05a_string));
        day_arrayMap.put(getString(R.string.slot_05A_06A), Integer.parseInt(et_place_rent_05a_06a_string));
        day_arrayMap.put(getString(R.string.slot_06A_07A), Integer.parseInt(et_place_rent_06a_07a_string));
        day_arrayMap.put(getString(R.string.slot_07A_08A), Integer.parseInt(et_place_rent_07a_08a_string));
        day_arrayMap.put(getString(R.string.slot_08A_09A), Integer.parseInt(et_place_rent_08a_09a_string));
        day_arrayMap.put(getString(R.string.slot_09A_10A), Integer.parseInt(et_place_rent_09a_10a_string));
        day_arrayMap.put(getString(R.string.slot_10A_11A), Integer.parseInt(et_place_rent_10a_11a_string));
        day_arrayMap.put(getString(R.string.slot_11A_12P), Integer.parseInt(et_place_rent_11a_12p_string));
        day_arrayMap.put(getString(R.string.slot_12P_01P), Integer.parseInt(et_place_rent_12p_01p_string));
        day_arrayMap.put(getString(R.string.slot_01P_02P), Integer.parseInt(et_place_rent_01p_02p_string));
        day_arrayMap.put(getString(R.string.slot_02P_03P), Integer.parseInt(et_place_rent_02p_03p_string));
        day_arrayMap.put(getString(R.string.slot_03P_04P), Integer.parseInt(et_place_rent_03p_04p_string));
        day_arrayMap.put(getString(R.string.slot_04P_05P), Integer.parseInt(et_place_rent_04p_05p_string));
        day_arrayMap.put(getString(R.string.slot_05P_06P), Integer.parseInt(et_place_rent_05p_06p_string));
        day_arrayMap.put(getString(R.string.slot_06P_07P), Integer.parseInt(et_place_rent_06p_07p_string));
        day_arrayMap.put(getString(R.string.slot_07P_08P), Integer.parseInt(et_place_rent_07p_08p_string));
        day_arrayMap.put(getString(R.string.slot_08P_09P), Integer.parseInt(et_place_rent_08p_09p_string));
        day_arrayMap.put(getString(R.string.slot_09P_10P), Integer.parseInt(et_place_rent_09p_10p_string));
        day_arrayMap.put(getString(R.string.slot_10P_11P), Integer.parseInt(et_place_rent_10p_11p_string));
        day_arrayMap.put(getString(R.string.slot_11P_12A), Integer.parseInt(et_place_rent_11p_12a_string));

        submit_day_wise_rent_hashmap.put(key_for_submit_hash_map, day_arrayMap);
    }


    private String getPrintData(String key) {
        String print_data = "";
        ArrayMap<String, Integer> subMap = submit_day_wise_rent_hashmap.get(key);
        for (String slots_key : subMap.keySet()) {
            Integer slots_price_value = subMap.get(slots_key);
            print_data = print_data + "\n" +  slots_key + "-->" + slots_price_value + "Rs";
        }
        if(print_data.isEmpty() ){
            print_data = "Please Add "+key+" Data !";
        }else{
            if(print_data.contains("{}")){
                print_data = "Please Add "+key+" Data !";
            }
        }

        return print_data;
    }

}