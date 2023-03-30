package com.example.booking.ui.OwnerPlaceRentDayTime;

import static com.example.booking.Utils.key_per_hour_rent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.example.booking.R;
import com.example.booking.Utils;

import java.util.HashMap;

public class OwnerPlaceRentDayTimeWise extends AppCompatActivity {



    String per_hour_defauly_rent_String = "0";

    //-----------------------------------------------------------------------------------------------------------------------------
    TextView tv_rent_per_hour_monday, tv_rent_per_hour_tuesday, tv_rent_per_hour_wednesday, tv_rent_per_hour_thursay, tv_rent_per_hour_friday, tv_rent_per_hour_saturday, tv_rent_per_hour_sunday;
    CardView cv_rent_per_hour_monday, cv_rent_per_hour_tuesday, cv_rent_per_hour_wednesday, cv_rent_per_hour_thursay, cv_rent_per_hour_friday, cv_rent_per_hour_saturday, cv_rent_per_hour_sunday;
    EditText et_place_rent_00a_01a, et_place_rent_01a_02a, et_place_rent_02a_03a, et_place_rent_03a_04a, et_place_rent_04a_05a, et_place_rent_05a_06a, et_place_rent_06a_07a, et_place_rent_07a_08a, et_place_rent_08a_09a, et_place_rent_09a_10a, et_place_rent_10a_11a, et_place_rent_11a_12p, et_place_rent_12p_01p, et_place_rent_01p_02p, et_place_rent_02p_03p, et_place_rent_03p_04p, et_place_rent_04p_05p, et_place_rent_05p_06p, et_place_rent_06p_07p, et_place_rent_07p_08p, et_place_rent_08p_09p, et_place_rent_09p_10p, et_place_rent_10p_11p, et_place_rent_11p_12a;


    Button btn_add_place_rent;
    HashMap<String, Integer> days_selected_hashmap = new HashMap<>();
    //-----------------------------------------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_rent_hour_day_time);

        // get per hour rent
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        Intent intent=getIntent();
        per_hour_defauly_rent_String = intent.getStringExtra(key_per_hour_rent);

        if(per_hour_defauly_rent_String == null ){
            per_hour_defauly_rent_String = "0";
        }
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

        btn_add_place_rent = findViewById(R.id.btn_add_place_rent);

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





            }
        });
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
                    days_selected_hashmap.put(Utils.key_monday, 1);
                } else {
                    // Set the TextView to the selected state
                    v.setSelected(true);
                    is_tv_selected(tv_rent_per_hour_monday, cv_rent_per_hour_monday, false, getApplicationContext());
                    days_selected_hashmap.put(Utils.key_monday, 0);
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
                    days_selected_hashmap.put(Utils.key_tuesday, 1);
                } else {
                    // Set the TextView to the selected state
                    v.setSelected(true);
                    is_tv_selected(tv_rent_per_hour_tuesday, cv_rent_per_hour_tuesday, false, getApplicationContext());
                    days_selected_hashmap.put(Utils.key_tuesday, 0);
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
                    days_selected_hashmap.put(Utils.key_wednesday, 1);
                } else {
                    // Set the TextView to the selected state
                    v.setSelected(true);
                    is_tv_selected(tv_rent_per_hour_wednesday, cv_rent_per_hour_wednesday, false, getApplicationContext());
                    days_selected_hashmap.put(Utils.key_wednesday, 0);
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
                    days_selected_hashmap.put(Utils.key_thursday, 1);
                } else {
                    // Set the TextView to the selected state
                    v.setSelected(true);
                    is_tv_selected(tv_rent_per_hour_thursay, cv_rent_per_hour_thursay, false, getApplicationContext());
                    days_selected_hashmap.put(Utils.key_thursday, 0);
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
                    days_selected_hashmap.put(Utils.key_friday, 1);
                } else {
                    // Set the TextView to the selected state
                    v.setSelected(true);
                    is_tv_selected(tv_rent_per_hour_friday, cv_rent_per_hour_friday, false, getApplicationContext());
                    days_selected_hashmap.put(Utils.key_friday, 0);
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
                    days_selected_hashmap.put(Utils.key_saturday, 1);
                } else {
                    // Set the TextView to the selected state
                    v.setSelected(true);
                    is_tv_selected(tv_rent_per_hour_saturday, cv_rent_per_hour_saturday, false, getApplicationContext());
                    days_selected_hashmap.put(Utils.key_saturday, 0);
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
                    days_selected_hashmap.put(Utils.key_sunday, 1);
                } else {
                    // Set the TextView to the selected state
                    v.setSelected(true);
                    is_tv_selected(tv_rent_per_hour_sunday, cv_rent_per_hour_sunday, false, getApplicationContext());
                    days_selected_hashmap.put(Utils.key_sunday, 0);
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