package com.example.booking.ui.UserClickOnMap;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking.R;
import com.example.booking.Utils;
import com.example.booking.adapter.UserClickOnMapSlotsAdapter;
import com.example.booking.interfaces.user_timeslot_Selected_OnclickListner;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.reflect.TypeToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.gson.Gson;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;


public class UserClickOnMapShowThatPlaceAllDetails extends AppCompatActivity implements user_timeslot_Selected_OnclickListner, PaymentResultListener {

    HashMap<String, HashMap<String, String>> get_is_already_booking_done_date_time_userid_hahmap = new HashMap<>();

    int total_price = 0;
    String show_selected_date_data = "";
    HashMap<String, Integer> time_slots_with_price;
    public HashMap<String, String> place_whole_details_hashmap = new HashMap<>();
    HashMap<String, HashMap<String, Integer>> map;
    private UserClickOnMapSlotsAdapter userClickOnMapSlotsAdapter;
    private RecyclerView recycleview_show_available_time_day_slots;

    CardView card_view_address;

    TextView tv_place_name_user_click_on_map, tv_address_header, tv_place_name_header, tv_place_full_address_user_click_on_map, tv_show_calendar_date;
    ImageView iv_user_select_calendar;

    LinearLayout ll_address_header, ll_place_name_header, ll_calendar_header;

    Button btn_book_place_from_user;

    //----------------------------------------------------------------------------------------------
    String value_booking_user_name = "";
    String value_booking_user_number = "";
    String value_booking_owner_name = "";
    String value_booking_time_slot = "";
    String value_booking_staff_number = "";
    String value_booking_place_name = "";
    String value_booking_place_address = "";
    String value_booking_place_latitude = "";
    String value_booking_place_longitude = "";
    //----------------------------------------------------------------------------------------------


    //----------------------------------------------------------------------------------------------
    String get_type_of_login;
    String userID_string = "", owner_place_ID_string = "";
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    // document_id ==> userid_ownerid_date_time, Hashmap_key ==> time_slot, Hashmap_value ==> whole_details
    public HashMap<String, HashMap<String, String>> submit_booking_data_from_user_hashmap;
    //----------------------------------------------------------------------------------------------

    private HashMap<String, Boolean> mSelectedItems_hashmap = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_click_on_map_show_that_place_all_details);

        Intent intent=getIntent();
        String getHashmapPlaceWholeData_String = intent.getStringExtra(Utils.key_whole_place_details);
        place_whole_details_hashmap = Utils.convertStringToHashMap_StringString(getHashmapPlaceWholeData_String);

        //----------------------------------------------------------------------------------------------
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        get_type_of_login = Utils.get_SharedPreference_type_of_login(getApplicationContext());
        userID_string = firebaseAuth.getCurrentUser().getUid();
        //----------------------------------------------------------------------------------------------

        recycleview_show_available_time_day_slots = findViewById(R.id.recycleview_show_available_time_day_slots);
        tv_place_name_user_click_on_map = (TextView) findViewById(R.id.tv_place_name_user_click_on_map);
        tv_place_full_address_user_click_on_map = (TextView) findViewById(R.id.tv_place_full_address_user_click_on_map);
        tv_show_calendar_date = (TextView) findViewById(R.id.tv_show_calendar_date);
        tv_place_name_header = (TextView) findViewById(R.id.tv_place_name_header);
        iv_user_select_calendar = (ImageView) findViewById(R.id.iv_user_select_calendar);
        btn_book_place_from_user = (Button) findViewById(R.id.btn_book_place_from_user);
        tv_address_header = (TextView) findViewById(R.id.tv_address_header);
        card_view_address = (CardView) findViewById(R.id.card_view_address);
        ll_address_header = (LinearLayout) findViewById(R.id.ll_address_header);
        ll_place_name_header = (LinearLayout) findViewById(R.id.ll_place_name_header);
        ll_calendar_header = (LinearLayout) findViewById(R.id.ll_calendar_header);

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
            String remove_forward_space = entry.getValue().replace("/", "");
            String without_slash_hashmap_value = remove_forward_space.replace("\\", "");

            Log.i("test_response", "KEY : "+key);
            Log.i("test_response", "VALUE : "+without_slash_hashmap_value);

            if(key.equals("booking_done_time_slots_converting_hashmap_to_string")){
                Gson gson = new Gson();
                Type type = new TypeToken<HashMap<String, HashMap<String, String>>>(){}.getType();
                get_is_already_booking_done_date_time_userid_hahmap = gson.fromJson(without_slash_hashmap_value, type);
            }

            if(key.equals("time_slots_converting_hashmap_to_string")){
                Gson gson = new Gson();
                Type typeOfHashMap = new TypeToken<HashMap<String, HashMap<String, Integer>>>() {}.getType();
                map = gson.fromJson(without_slash_hashmap_value, typeOfHashMap);

            }

            if(key.equals("owner_place_id_string")){
                owner_place_ID_string = without_slash_hashmap_value;
            }

            if(key.equals("owner_place_name_string")){
                tv_place_name_user_click_on_map.setText(without_slash_hashmap_value);
                value_booking_place_name = without_slash_hashmap_value;
            }

            if(key.equals("owner_place_full_address_string")){
                tv_place_full_address_user_click_on_map.setText(without_slash_hashmap_value);
                value_booking_place_address = without_slash_hashmap_value;
            }

            if(key.equals("owner_place_latitude")){
                value_booking_place_latitude = without_slash_hashmap_value;
            }

            if(key.equals("owner_place_longitude")){
                value_booking_place_longitude = without_slash_hashmap_value;
            }

            if(key.equals("owner_place_staff_number_string")){
                value_booking_staff_number = without_slash_hashmap_value;
            }


        });
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-


        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
        ll_address_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_place_full_address_user_click_on_map.getVisibility() == View.VISIBLE) {
                    tv_place_full_address_user_click_on_map.setVisibility(View.GONE);
                } else {
                    tv_place_full_address_user_click_on_map.setVisibility(View.VISIBLE);
                }
            }
        });
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-


        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
        ll_place_name_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_place_name_user_click_on_map.getVisibility() == View.VISIBLE) {
                    tv_place_name_user_click_on_map.setVisibility(View.GONE);
                } else {
                    tv_place_name_user_click_on_map.setVisibility(View.VISIBLE);
                }
            }
        });
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
        ll_calendar_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Initialize calendar instance
                Calendar calendar = Calendar.getInstance();


// Create a Date Picker Dialog and set the date range
                DatePickerDialog datePickerDialog = new DatePickerDialog(UserClickOnMapShowThatPlaceAllDetails.this, R.style.MyDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
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
                showCustomDialog(get_selected_key, "= "+total_price+" Rs", show_selected_date_data);
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
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        // Loop through the HashMap to check each value
        for (Map.Entry<String, Boolean> entry : mSelectedItems_hashmap.entrySet()) {
            String key = entry.getKey();
            Boolean value = entry.getValue();
            int slot_price_integer = time_slots_with_price.get(key);

            if (value) { // If the value matches the one you are looking for
                    stringBuilder.append(entry.getKey() + " = " +slot_price_integer);
                    stringBuilder.append("\n"); // Append a newline character to create a new line
                total_price = total_price + slot_price_integer;
            }
        }
        return stringBuilder.toString();
    }


    //=====================================================================================
    @Override
    protected void onResume() {
        super.onResume();
        refreshRecycleview(show_selected_date_data);
    }
    //=====================================================================================


    //=====================================================================================
    public void refreshRecycleview(String show_selected_date_data){
        if(time_slots_with_price != null) {

//         //Initialize RecyclerView and Adapter
            userClickOnMapSlotsAdapter = new UserClickOnMapSlotsAdapter(time_slots_with_price, show_selected_date_data, get_is_already_booking_done_date_time_userid_hahmap, this);
            recycleview_show_available_time_day_slots.setAdapter(userClickOnMapSlotsAdapter);
            recycleview_show_available_time_day_slots.setLayoutManager(new GridLayoutManager(this, 3));
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
                    refreshRecycleview(show_selected_date_data);
                    break;
                case Calendar.MONDAY:
                    // Code for Monday
                    time_slots_with_price = map.get("Mon");
                    refreshRecycleview(show_selected_date_data);
                    break;
                case Calendar.TUESDAY:
                    // Code for Tuesday
                    time_slots_with_price = map.get("Tue");
                    refreshRecycleview(show_selected_date_data);
                    break;
                case Calendar.WEDNESDAY:
                    // Code for Wednesday
                    time_slots_with_price = map.get("Wed");
                    refreshRecycleview(show_selected_date_data);
                    break;
                case Calendar.THURSDAY:
                    // Code for Thursday
                    time_slots_with_price = map.get("Thu");
                    refreshRecycleview(show_selected_date_data);
                    break;
                case Calendar.FRIDAY:
                    // Code for Friday
                    time_slots_with_price = map.get("Fri");
                    refreshRecycleview(show_selected_date_data);
                    break;
                case Calendar.SATURDAY:
                    // Code for Saturday
                    time_slots_with_price = map.get("Sat");
                    refreshRecycleview(show_selected_date_data);

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
    public void showCustomDialog(String selected_time_slots, String total_bill, String show_selected_date_data) {
        Dialog dialog = new Dialog(UserClickOnMapShowThatPlaceAllDetails.this);
        dialog.setContentView(R.layout.booking_review_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);

        //-------------------------------------------------------------------------------
        TextView tv_booking_review_total_bill, tv_booking_review_selected_time_Slots, tv_selected_date_show_review_dialog;
        Button booking_review_dialog_payment, booking_review_dialog_cancel;

        tv_selected_date_show_review_dialog = (TextView) dialog.findViewById(R.id.tv_selected_date_show_review_dialog);
        tv_booking_review_selected_time_Slots = (TextView) dialog.findViewById(R.id.tv_booking_review_selected_time_Slots);
        tv_booking_review_total_bill = (TextView) dialog.findViewById(R.id.tv_booking_review_total_bill);
        booking_review_dialog_cancel = (Button) dialog.findViewById(R.id.booking_review_dialog_cancel);
        booking_review_dialog_payment = (Button) dialog.findViewById(R.id.booking_review_dialog_payment);
        //-------------------------------------------------------------------------------



        //-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-
// Split the string data into lines
        String[] time_slots_line_by_line_array = selected_time_slots.split("\n");

// Create a SpannableString to store the formatted text
        SpannableString spannableString = new SpannableString(selected_time_slots);

// Loop through the lines and apply formatting to each line
        int startIndex = 0;
        for (String line : time_slots_line_by_line_array) {
            int endIndex = startIndex + line.length(); // calculate the end index of the line
            // Apply formatting to the line (e.g., draw a rectangle)
            spannableString.setSpan(new BackgroundColorSpan(getColor(R.color.combo_background_green)), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            startIndex = endIndex + 1; // move the start index to the next line
        }
        //-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-

        tv_selected_date_show_review_dialog.setText(show_selected_date_data);
        tv_booking_review_total_bill.setText(total_bill);
        tv_booking_review_selected_time_Slots.setText(spannableString);

        booking_review_dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        booking_review_dialog_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id_string = userID_string;
                String owner_id_string = owner_place_ID_string;
                String booking_id_string =  owner_id_string+"_"+show_selected_date_data;
                //----------------------------------------------------------------------------------------------------------
                LinkedHashMap<String, String> user_owner_place_booking_details_linkedhashmap = new LinkedHashMap<>();
                user_owner_place_booking_details_linkedhashmap.put(Utils.key_booking_user_id, booking_id_string);
                user_owner_place_booking_details_linkedhashmap.put(Utils.key_booking_owner_id, owner_id_string);
                user_owner_place_booking_details_linkedhashmap.put(Utils.key_booking_date, show_selected_date_data);
                user_owner_place_booking_details_linkedhashmap.put(Utils.key_booking_staff_number, value_booking_staff_number);
                user_owner_place_booking_details_linkedhashmap.put(Utils.key_booking_place_name, value_booking_place_name);
                user_owner_place_booking_details_linkedhashmap.put(Utils.key_booking_place_address, value_booking_place_address);
                user_owner_place_booking_details_linkedhashmap.put(Utils.key_booking_place_latitude, value_booking_place_latitude);
                user_owner_place_booking_details_linkedhashmap.put(Utils.key_booking_place_longitude,value_booking_place_longitude );
                //----------------------------------------------------------------------------------------------------------


                //----------------------------------------------------------------------------------------------------------
                LinkedHashMap<String, LinkedHashMap<String, String>> linked_hash_map_userid = new LinkedHashMap();
                linked_hash_map_userid.put(user_id_string, user_owner_place_booking_details_linkedhashmap);
                //----------------------------------------------------------------------------------------------------------

                //==========================================================================================================
                if (time_slots_line_by_line_array[0] != "") {
                    submit_booking_details(booking_id_string, show_selected_date_data, time_slots_line_by_line_array, linked_hash_map_userid, user_id_string, owner_id_string, total_bill);
                }else{
                    Toast.makeText(getApplicationContext(), "Select atleast one time slots !", Toast.LENGTH_SHORT).show();
                }
                //==========================================================================================================


                dialog.dismiss();
            }
        });

        dialog.show();
    }
    //=====================================================================================



    String final_booking_id, final_date_string, final_user_id_string, final_owner_id_string, final_total_bill;
    String[] final_time_slots_line_by_line_array;
    LinkedHashMap<String, LinkedHashMap<String, String>> final_linked_hash_map_userid;
    //======================================================================================================================================================
     public void submit_booking_details(String booking_id, String date_string, String[] time_slots_line_by_line_array, LinkedHashMap<String, LinkedHashMap<String, String>> linked_hash_map_userid, String user_id_string, String owner_id_string, String total_bill) {

         final_booking_id = booking_id;
         final_date_string = date_string;
         final_user_id_string = user_id_string;
         final_owner_id_string = owner_id_string;
         final_total_bill = total_bill;
         final_time_slots_line_by_line_array = time_slots_line_by_line_array;
         final_linked_hash_map_userid = linked_hash_map_userid;

         makepayment();

     }
    //======================================================================================================================================================



    //======================================================================================================================================================

    private void makepayment()
    {

        Checkout checkout = new Checkout();
        checkout.setKeyID(Utils.razorpay_key_id);

        checkout.setImage(R.drawable.icon_done_50);
        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();

            options.put("name", ""+getString(R.string.app_name));
            options.put("description", ""+getString(R.string.payment_description));
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            // options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#00264D");
            options.put("currency", "INR");
            String inputString = final_total_bill;
            String numericString = inputString.replaceAll("[^\\d.]", "");
            long final_payment_100 = Long.parseLong(numericString.trim()) * 100;
            options.put("amount", ""+final_payment_100);//300 X 100 (amount * 100)
            options.put("prefill.email", "gaurav.kumar@example.com");
            options.put("prefill.contact","7864945278");
            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Log.i("test_response", "Payment Success : "+s.toString());
        //------------------------------------------------------------------------------------------
        // show success dialog
        final Dialog dialog = new Dialog(UserClickOnMapShowThatPlaceAllDetails.this);
        dialog.setContentView(R.layout.booking_done_success_dialog);
        dialog.setCancelable(false);

        int green_color = ContextCompat.getColor(getApplicationContext(), R.color.combo_text_green);
        int light_green_color = ContextCompat.getColor(getApplicationContext(), R.color.combo_background_green);

        ImageView iv_payment_icon = (ImageView) dialog.findViewById(R.id.iv_payment_icon);
        iv_payment_icon.setImageDrawable(getDrawable(R.drawable.icon_done_50));
        iv_payment_icon.setColorFilter(green_color);

        TextView tv_done = (TextView) dialog.findViewById(R.id.tv_done);
        tv_done.setText(getString(R.string.booking_done));
        tv_done.setTextColor(green_color);

        CardView cv_payment_dialog = (CardView) dialog.findViewById(R.id.cv_payment_dialog);
        cv_payment_dialog.setCardBackgroundColor(light_green_color);


        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=
        DocumentReference documentRef = firebaseFirestore.collection(Utils.key_place_booking_firestore).document(final_booking_id);

        //-----------------------------------------------------------------------------------------------------------
        // to submit time-slot book or pending add into owner place
        LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> bookingslotkey_date_time_userid_linkedhashmap = new LinkedHashMap<>();
        LinkedHashMap<String, LinkedHashMap<String, String>> date_time_userid_linkedhashmap = new LinkedHashMap<>();
        LinkedHashMap<String, String> time_userid_linkedhashmap = new LinkedHashMap<>();
        //-----------------------------------------------------------------------------------------------------------


        // time slots --> userid --> detail_data
        LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String,String>>> data = new LinkedHashMap<>();
        for(int i = 0 ; i < final_time_slots_line_by_line_array.length; i++){
            data.put(final_time_slots_line_by_line_array[i], final_linked_hash_map_userid);
            time_userid_linkedhashmap.put(final_time_slots_line_by_line_array[i], final_user_id_string);
        }



        documentRef.set(data, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "Document updated/created successfully!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", "Error updating/creating document: ", e);
            }
        });


        // submit timeslots to OwnerPlaces collection also for mark red and green
        //-----------------------------------------------------------------------------------------
        // adding data
        date_time_userid_linkedhashmap.put(final_date_string, time_userid_linkedhashmap);
        // now add to under one key
        bookingslotkey_date_time_userid_linkedhashmap.put(Utils.key_owner_PlaceBookingDateTimeUserDetails_hashmap, date_time_userid_linkedhashmap);


        DocumentReference ownerplace_documentRef = firebaseFirestore.collection(Utils.key_ownerplace_firestore).document(final_owner_id_string);
        ownerplace_documentRef.set(bookingslotkey_date_time_userid_linkedhashmap, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "Document updated/created successfully!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", "Error updating/creating document: ", e);
            }
        });
        //-----------------------------------------------------------------------------------------

        //-=-=--=-=-=-=-=--=-=-=-==-=-=--==-===-=-=-=-=-=-=-==--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

        dialog.show();

        //-----------------------------------------------------------------------------------------
// Dismiss the dialog after 5 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                onBackPressed();
                finish();
            }
        }, 2000); // 5000 milliseconds = 5 seconds
        //------------------------------------------------------------------------------------------
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.i("test_response", "Payment Error : "+s.toString());
        //------------------------------------------------------------------------------------------
        // show success dialog
        final Dialog dialog = new Dialog(UserClickOnMapShowThatPlaceAllDetails.this);
        dialog.setContentView(R.layout.booking_done_success_dialog);
        dialog.setCancelable(false);

        int red_color = ContextCompat.getColor(getApplicationContext(), R.color.red);
        int light_red_color = ContextCompat.getColor(getApplicationContext(), R.color.background_red);

        ImageView iv_payment_icon = (ImageView) dialog.findViewById(R.id.iv_payment_icon);
        iv_payment_icon.setImageDrawable(getDrawable(R.drawable.payment_failed_50));
        iv_payment_icon.setColorFilter(red_color);

        TextView tv_done = (TextView) dialog.findViewById(R.id.tv_done);
        tv_done.setText(getString(R.string.booking_not_done));
        tv_done.setTextColor(red_color);


        CardView cv_payment_dialog = (CardView) dialog.findViewById(R.id.cv_payment_dialog);
        cv_payment_dialog.setCardBackgroundColor(light_red_color);


        dialog.show();

// Dismiss the dialog after 5 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                onBackPressed();
                finish();
            }
        }, 2000); // 5000 milliseconds = 5 seconds
        //------------------------------------------------------------------------------------------
    }

    //======================================================================================================================================================


}