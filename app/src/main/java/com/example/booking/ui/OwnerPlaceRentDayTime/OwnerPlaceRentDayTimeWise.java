package com.example.booking.ui.OwnerPlaceRentDayTime;

import static com.example.booking.Utils.key_friday;
import static com.example.booking.Utils.key_monday;
import static com.example.booking.Utils.key_per_hour_rent;
import static com.example.booking.Utils.key_saturday;
import static com.example.booking.Utils.key_sunday;
import static com.example.booking.Utils.key_thursday;
import static com.example.booking.Utils.key_tuesday;
import static com.example.booking.Utils.key_wednesday;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.example.booking.R;
import com.example.booking.Utils;

import java.util.HashMap;

public class OwnerPlaceRentDayTimeWise extends AppCompatActivity {



    String per_hour_rent_String = "0";

    //-----------------------------------------------------------------------------------------------------------------------------
    TextView tv_rent_per_hour_monday, tv_rent_per_hour_tuesday, tv_rent_per_hour_wednesday, tv_rent_per_hour_thursay, tv_rent_per_hour_friday, tv_rent_per_hour_saturday, tv_rent_per_hour_sunday;
    CardView cv_rent_per_hour_monday, cv_rent_per_hour_tuesday, cv_rent_per_hour_wednesday, cv_rent_per_hour_thursay, cv_rent_per_hour_friday, cv_rent_per_hour_saturday, cv_rent_per_hour_sunday;
    HashMap<String, Integer> days_selected_hashmap;
    //-----------------------------------------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_rent_hour_day_time);

        // get per hour rent
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        Intent intent=getIntent();
        per_hour_rent_String = intent.getStringExtra(key_per_hour_rent);

        if(per_hour_rent_String == null ){
            per_hour_rent_String = "0";
        }
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


        // Initialization
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // set default all day  selected
        days_selected_hashmap.put(Utils.key_monday, 1);
        days_selected_hashmap.put(key_tuesday, 1);
        days_selected_hashmap.put(key_wednesday, 1);
        days_selected_hashmap.put(key_thursday, 1);
        days_selected_hashmap.put(key_friday, 1);
        days_selected_hashmap.put(key_saturday, 1);
        days_selected_hashmap.put(key_sunday, 1);


        tv_rent_per_hour_monday = findViewById(R.id.tv_rent_per_hour_monday);
        tv_rent_per_hour_tuesday = findViewById(R.id.tv_rent_per_hour_tuesday);
        tv_rent_per_hour_wednesday = findViewById(R.id.tv_rent_per_hour_wednesday);
        tv_rent_per_hour_thursay = findViewById(R.id.tv_rent_per_hour_thursay);
        tv_rent_per_hour_friday = findViewById(R.id.tv_rent_per_hour_friday);
        tv_rent_per_hour_saturday = findViewById(R.id.tv_rent_per_hour_saturday);
        tv_rent_per_hour_sunday = findViewById(R.id.tv_rent_per_hour_sunday);

        cv_rent_per_hour_monday = findViewById(R.id.cv_rent_per_hour_monday);
        cv_rent_per_hour_tuesday = findViewById(R.id.cv_rent_per_hour_tuesday);
        cv_rent_per_hour_wednesday = findViewById(R.id.cv_rent_per_hour_wednesday);
        cv_rent_per_hour_thursay = findViewById(R.id.cv_rent_per_hour_thursay);
        cv_rent_per_hour_friday = findViewById(R.id.cv_rent_per_hour_friday);
        cv_rent_per_hour_saturday = findViewById(R.id.cv_rent_per_hour_saturday);
        cv_rent_per_hour_sunday = findViewById(R.id.cv_rent_per_hour_sunday);

        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=




        // Textview Days State select unselect
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

        // monday
        //-----------------------------------------------------------------------------------------------------------------------------
        tv_rent_per_hour_monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isSelected()) {
                    // Set the TextView to the unselected state
                    v.setSelected(false);
                    is_tv_selected(tv_rent_per_hour_monday, cv_rent_per_hour_monday, true, getApplicationContext());
                    days_selected_hashmap.put(key_monday, 1);
                } else {
                    // Set the TextView to the selected state
                    v.setSelected(true);
                    is_tv_selected(tv_rent_per_hour_monday, cv_rent_per_hour_monday, false, getApplicationContext());
                    days_selected_hashmap.put(key_monday, 0);
                }

            }
        });
        //-----------------------------------------------------------------------------------------------------------------------------


        // tuesday
        //-----------------------------------------------------------------------------------------------------------------------------
        tv_rent_per_hour_tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isSelected()) {
                    // Set the TextView to the unselected state
                    v.setSelected(false);
                    is_tv_selected(tv_rent_per_hour_tuesday, cv_rent_per_hour_tuesday, true, getApplicationContext());
                    days_selected_hashmap.put(key_tuesday, 1);
                } else {
                    // Set the TextView to the selected state
                    v.setSelected(true);
                    is_tv_selected(tv_rent_per_hour_tuesday, cv_rent_per_hour_tuesday, false, getApplicationContext());
                    days_selected_hashmap.put(key_tuesday, 0);
                }

            }
        });
        //-----------------------------------------------------------------------------------------------------------------------------


        // wednesday
        //-----------------------------------------------------------------------------------------------------------------------------
        tv_rent_per_hour_wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isSelected()) {
                    // Set the TextView to the unselected state
                    v.setSelected(false);
                    is_tv_selected(tv_rent_per_hour_wednesday, cv_rent_per_hour_wednesday, true, getApplicationContext());
                    days_selected_hashmap.put(key_wednesday, 1);
                } else {
                    // Set the TextView to the selected state
                    v.setSelected(true);
                    is_tv_selected(tv_rent_per_hour_wednesday, cv_rent_per_hour_wednesday, false, getApplicationContext());
                    days_selected_hashmap.put(key_wednesday, 0);
                }

            }
        });
        //-----------------------------------------------------------------------------------------------------------------------------


        // thursday
        //-----------------------------------------------------------------------------------------------------------------------------
        tv_rent_per_hour_thursay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isSelected()) {
                    // Set the TextView to the unselected state
                    v.setSelected(false);
                    is_tv_selected(tv_rent_per_hour_thursay, cv_rent_per_hour_thursay, true, getApplicationContext());
                    days_selected_hashmap.put(key_thursday, 1);
                } else {
                    // Set the TextView to the selected state
                    v.setSelected(true);
                    is_tv_selected(tv_rent_per_hour_thursay, cv_rent_per_hour_thursay, false, getApplicationContext());
                    days_selected_hashmap.put(key_thursday, 0);
                }

            }
        });
        //-----------------------------------------------------------------------------------------------------------------------------


        // friday
        //-----------------------------------------------------------------------------------------------------------------------------
        tv_rent_per_hour_friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isSelected()) {
                    // Set the TextView to the unselected state
                    v.setSelected(false);
                    is_tv_selected(tv_rent_per_hour_friday, cv_rent_per_hour_friday, true, getApplicationContext());
                    days_selected_hashmap.put(key_friday, 1);
                } else {
                    // Set the TextView to the selected state
                    v.setSelected(true);
                    is_tv_selected(tv_rent_per_hour_friday, cv_rent_per_hour_friday, false, getApplicationContext());
                    days_selected_hashmap.put(key_friday, 0);
                }

            }
        });
        //-----------------------------------------------------------------------------------------------------------------------------


        // saturday
        //-----------------------------------------------------------------------------------------------------------------------------
        tv_rent_per_hour_saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isSelected()) {
                    // Set the TextView to the unselected state
                    v.setSelected(false);
                    is_tv_selected(tv_rent_per_hour_saturday, cv_rent_per_hour_saturday, true, getApplicationContext());
                    days_selected_hashmap.put(key_saturday, 1);
                } else {
                    // Set the TextView to the selected state
                    v.setSelected(true);
                    is_tv_selected(tv_rent_per_hour_saturday, cv_rent_per_hour_saturday, false, getApplicationContext());
                    days_selected_hashmap.put(key_saturday, 0);
                }

            }
        });
        //-----------------------------------------------------------------------------------------------------------------------------


        // Sunday
        //-----------------------------------------------------------------------------------------------------------------------------
        tv_rent_per_hour_sunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isSelected()) {
                    // Set the TextView to the unselected state
                    v.setSelected(false);
                    is_tv_selected(tv_rent_per_hour_sunday, cv_rent_per_hour_sunday, true, getApplicationContext());
                    days_selected_hashmap.put(key_sunday, 1);
                } else {
                    // Set the TextView to the selected state
                    v.setSelected(true);
                    is_tv_selected(tv_rent_per_hour_sunday, cv_rent_per_hour_sunday, false, getApplicationContext());
                    days_selected_hashmap.put(key_sunday, 0);
                }

            }
        });
        //-----------------------------------------------------------------------------------------------------------------------------

        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    }


    public static void is_tv_selected(TextView tv, CardView cv, Boolean isSelected, Context context){

        if(isSelected){
            tv.setTextColor(ContextCompat.getColor(context, R.color.combo_text_green));
            cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.combo_background_green));
        }else{
            tv.setTextColor(ContextCompat.getColor(context, R.color.combo_text_blue));
            cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }
    }
}