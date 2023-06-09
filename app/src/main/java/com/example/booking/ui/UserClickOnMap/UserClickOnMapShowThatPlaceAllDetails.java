package com.example.booking.ui.UserClickOnMap;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking.ConnectivityReceiver;
import com.example.booking.FirebaseProductionSingletonClass;
import com.example.booking.MyPreferences;
import com.example.booking.R;
import com.example.booking.Utils;
import com.example.booking.adapter.UserClickOnMapSlotsAdapter;
import com.example.booking.interfaces.user_timeslot_Selected_OnclickListner;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.reflect.TypeToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
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

    ConnectivityReceiver networkChangeListener = new ConnectivityReceiver();

    RelativeLayout rl_slots_booking_progressbar;
    HashMap<String, HashMap<String, Boolean>> already_booked_date_and_timeslot_hashmap = new HashMap<>();

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
    String value_total_number_of_nets_available = "";
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    int for_selected_timeslot_count_how_many_nets_full = 0;
    //----------------------------------------------------------------------------------------------


    //----------------------------------------------------------------------------------------------
    String get_type_of_login;
    String userID_string = "", owner_place_ID_string = "";
    FirebaseAuth firebaseAuth;
    public static FirebaseFirestore firebaseFirestore;

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

        // Initialize
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        if(Utils.get_SharedPreference_staging_or_production_enviorment(getApplicationContext()).contains(Utils.value_production)){
            firebaseAuth = FirebaseAuth.getInstance(FirebaseProductionSingletonClass.getInstance(getApplicationContext()));
            firebaseFirestore = FirebaseFirestore.getInstance(FirebaseProductionSingletonClass.getInstance(getApplicationContext()));

        }else{
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseFirestore = FirebaseFirestore.getInstance();
        }
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

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
        rl_slots_booking_progressbar = (RelativeLayout) findViewById(R.id.rl_slots_booking_progressbar);

        // Initially give today's date
        Calendar calendars = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String todayDate = dateFormat.format(calendars.getTime());
        tv_show_calendar_date.setText(todayDate+ " - "+ Utils.fromDateTogetDay_3_char(todayDate));

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
//                get_is_already_booking_done_date_time_userid_hahmap = gson.fromJson(without_slash_hashmap_value, type);
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

            if(key.equals("owner_place_total_nets_string")){
                value_total_number_of_nets_available = without_slash_hashmap_value;
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
                        tv_show_calendar_date.setText(selectedDate+" - "+Utils.fromDateTogetDay_3_char(selectedDate));
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

        // Listen data change event
        //------------------------------------------------------------------------------------------
        CollectionReference bookingsRef = firebaseFirestore.collection(Utils.key_place_booking_firestore);

        bookingsRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("TAG", "Listen failed.", e);
                    return;
                }

                // Process changes to the collection here
                for (DocumentChange dc : snapshot.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case ADDED:
                            // A new document was added
                            DocumentSnapshot newDocument = dc.getDocument();
                            // Notify the user of the new document
                            refreshRecycleview(show_selected_date_data);
//                            Toast.makeText(getApplicationContext(), "Data Change : ADDED", Toast.LENGTH_SHORT).show();
                            break;
                        case MODIFIED:
                            // An existing document was modified
                            DocumentSnapshot modifiedDocument = dc.getDocument();
                            refreshRecycleview(show_selected_date_data);
                            // Notify the user of the modified document
//                            Toast.makeText(getApplicationContext(), "Data Change : MODIFIED", Toast.LENGTH_SHORT).show();
                            break;
                        case REMOVED:
                            // An existing document was removed
                            DocumentSnapshot removedDocument = dc.getDocument();
                            // Notify the user of the removed document
                            refreshRecycleview(show_selected_date_data);
//                            Toast.makeText(getApplicationContext(), "Data Change : REMOVED", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }
        });
        //------------------------------------------------------------------------------------------
    }

    private void readAlreadyBookingDetails(String owner_place_id_string) {
        rl_slots_booking_progressbar.setVisibility(View.VISIBLE);
        CollectionReference collectionRef = firebaseFirestore.collection(Utils.key_place_booking_firestore);
        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    int totalEntries = task.getResult().getDocuments().size();
                    final int[] currentIndex = {0};
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String[] parts = document.getId().split("_");
                        String owner_id = parts[0]; // "bHbwpPf7xFhJXX42vC9pEomm8Ey2"
                        String date = parts[1]; // "14 Apr 2023"

                        if(owner_id.contains(owner_place_id_string)){
                            //-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=
//                            Map<String, Object> bookingData = document.getData();
//                            HashMap<String, Boolean> booked_time_slots_hashmap = new HashMap<>();
//                            for (Map.Entry<String, Object> bookingEntry : bookingData.entrySet()) {
//                                // Get the booking time slot
//                                String timeSlot = bookingEntry.getKey();
//                                Log.d("TAG", "Time slot: " + timeSlot);
//                                booked_time_slots_hashmap.put(timeSlot, true);
//                            }
//                            already_booked_date_and_timeslot_hashmap.put(date, booked_time_slots_hashmap);
                            //-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=


                            //****************************************************************************************
                            Map<String, Object> bookingData = document.getData();


                            String TAG = "test_latest_response";
                            for_selected_timeslot_count_how_many_nets_full = 0;
                            HashMap<String, Boolean> booked_time_slots_hashmap = new HashMap<>();
                            for (Map.Entry<String, Object> entry : bookingData.entrySet()) {
                                String key = entry.getKey();
                                Map<String, Object> value = (Map<String, Object>) entry.getValue();
                                Log.d(TAG, "Key: " + key + ", Value: " + value.toString());

                                for_selected_timeslot_count_how_many_nets_full = for_selected_timeslot_count_how_many_nets_full + 1;

                                for (Map.Entry<String, Object> innerEntry : value.entrySet()){
                                    String timeSlot = innerEntry.getKey();
                                    Map<String, Object> inner_value = (Map<String, Object>) innerEntry.getValue();
//                                    Log.d(TAG, "inner_key: " + timeSlot + ", inner_value: " + inner_value.toString());

                                    // User clicked on map, Now show user to how many timeslots are full or empty, now check last net timeslots, if present that's means it's full
                                    if(for_selected_timeslot_count_how_many_nets_full == Integer.parseInt(value_total_number_of_nets_available)){
                                        booked_time_slots_hashmap.put(timeSlot, true);
                                    }
                                }
                            }

                            already_booked_date_and_timeslot_hashmap.put(date, booked_time_slots_hashmap);
                            //****************************************************************************************

                        }

                        // Access the document data here
                        Log.d("TAG", document.getId() + " => " + document.getData());

                        // check if this is the last iteration
                        if (++currentIndex[0] == totalEntries) {
                            rl_slots_booking_progressbar.setVisibility(View.GONE);
                            recycleData();
                        }
                    }

                    if(already_booked_date_and_timeslot_hashmap.size() == 0){
                        rl_slots_booking_progressbar.setVisibility(View.GONE);
                        recycleData();
                    }
                    Log.d("TAG", "=========\n"+already_booked_date_and_timeslot_hashmap.toString());
                } else {
                    Log.d("TAG","Error getting documents: ", task.getException());
                }
            }
        });

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
        readAlreadyBookingDetails(owner_place_ID_string);
    }


    public void recycleData(){
        if(time_slots_with_price != null) {

//         //Initialize RecyclerView and Adapter
            userClickOnMapSlotsAdapter = new UserClickOnMapSlotsAdapter(time_slots_with_price, show_selected_date_data, already_booked_date_and_timeslot_hashmap, this);
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
        TextView tv_booking_review_total_bill, tv_booking_review_selected_time_Slots, tv_selected_date_show_review_dialog, tv_selected_day_show_review_dialog;
        Button booking_review_dialog_payment, booking_review_dialog_cancel;

        tv_selected_date_show_review_dialog = (TextView) dialog.findViewById(R.id.tv_selected_date_show_review_dialog);
        tv_selected_day_show_review_dialog = (TextView) dialog.findViewById(R.id.tv_selected_day_show_review_dialog);
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
            spannableString.setSpan(new BackgroundColorSpan(getColor(R.color.background_red)), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            startIndex = endIndex + 1; // move the start index to the next line
        }
        //-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-

        tv_selected_day_show_review_dialog.setText(Utils.fromDateTogetDay(show_selected_date_data));
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


                //==========================================================================================================
                if (time_slots_line_by_line_array[0] != "") {
                    submit_booking_details(booking_id_string, show_selected_date_data, time_slots_line_by_line_array, user_id_string, owner_id_string, total_bill);
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



    static String final_booking_id;
    String final_date_string;
    String final_user_id_string;
    String final_owner_id_string;
    String final_total_bill;
    String[] final_time_slots_line_by_line_array;

    //======================================================================================================================================================
    public void submit_booking_details(String booking_id, String date_string, String[] time_slots_line_by_line_array, String user_id_string, String owner_id_string, String total_bill) {

        final_booking_id = booking_id;
        final_date_string = date_string;
        final_user_id_string = user_id_string;
        final_owner_id_string = owner_id_string;
        final_total_bill = total_bill;
        final_time_slots_line_by_line_array = time_slots_line_by_line_array;

        makepayment();

    }
    //======================================================================================================================================================



    //======================================================================================================================================================

    private void makepayment()
    {


        Checkout checkout = new Checkout();
        checkout.setKeyID(Utils.razorpay_key_id);
        checkout.setImage(R.drawable.icon_location_in_blue_payment);
        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();

            options.put("name", ""+getString(R.string.app_name));
            options.put("description", "Payment Done By ID : "+userID_string);
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            // options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#00264D");
            options.put("currency", "INR");
            String inputString = final_total_bill;
            String numericString = inputString.replaceAll("[^\\d.]", "");
            long final_payment_100 = Long.parseLong(numericString.trim()) * 100;
            options.put("amount", ""+final_payment_100);//300 X 100 (amount * 100)
            options.put("prefill.email", ""+ MyPreferences.getStringValue(Utils.shared_preference_key_LoggedInUser_email, "User Not Found",getApplicationContext()));
            options.put("prefill.contact",""+MyPreferences.getStringValue(Utils.shared_preference_key_LoggedInUser_phone_number, "Phone Number Not Found", getApplicationContext()));
            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String payment_id_string) {
        Log.i("test_response", "Payment Success : "+payment_id_string.toString());
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



        //==========================================================================================
        //----------------------------------------------------------------------------------------------------------
        String user_id_string = userID_string;
        String owner_id_string = owner_place_ID_string;
        String booking_id_string =  owner_id_string+"_"+show_selected_date_data;

        LinkedHashMap<String, String> user_owner_place_booking_details_linkedhashmap = new LinkedHashMap<>();
        user_owner_place_booking_details_linkedhashmap.put(Utils.key_booking_user_id, booking_id_string);
        user_owner_place_booking_details_linkedhashmap.put(Utils.key_booking_owner_id, owner_id_string);
        user_owner_place_booking_details_linkedhashmap.put(Utils.key_booking_date, show_selected_date_data);
        user_owner_place_booking_details_linkedhashmap.put(Utils.key_booking_staff_number, value_booking_staff_number);
        user_owner_place_booking_details_linkedhashmap.put(Utils.key_booking_place_name, value_booking_place_name);
        user_owner_place_booking_details_linkedhashmap.put(Utils.key_booking_place_address, value_booking_place_address);
        user_owner_place_booking_details_linkedhashmap.put(Utils.key_booking_place_latitude, value_booking_place_latitude);
        user_owner_place_booking_details_linkedhashmap.put(Utils.key_booking_place_longitude,value_booking_place_longitude );
        user_owner_place_booking_details_linkedhashmap.put(Utils.key_booking_payment_id_successful,payment_id_string );
        //----------------------------------------------------------------------------------------------------------


        //----------------------------------------------------------------------------------------------------------
        LinkedHashMap<String, LinkedHashMap<String, String>> linked_hash_map_userid = new LinkedHashMap();
        linked_hash_map_userid.put(user_id_string, user_owner_place_booking_details_linkedhashmap);
        //----------------------------------------------------------------------------------------------------------
        //==========================================================================================


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
            data.put(final_time_slots_line_by_line_array[i], linked_hash_map_userid);
            time_userid_linkedhashmap.put(final_time_slots_line_by_line_array[i], final_user_id_string);
        }

        //******************************************************************************************************

        checkWhichBookingNetsAvailableandSubmitData(time_userid_linkedhashmap, linked_hash_map_userid, owner_id_string, show_selected_date_data);

        //******************************************************************************************************


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



    //********************************************************************************************************************
    private void checkWhichBookingNetsAvailableandSubmitData(LinkedHashMap<String, String> time_userid_linkedhashmap, LinkedHashMap<String, LinkedHashMap<String, String>> linked_hash_map_userid, String owner_place_id_string, String show_selected_date_data) {
        rl_slots_booking_progressbar.setVisibility(View.VISIBLE);
        CollectionReference collectionRef = firebaseFirestore.collection(Utils.key_place_booking_firestore);
        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {


                    // 1st loop for number of selected timeslots ---------------------------------------------------------------------------------
                    for (Map.Entry<String, String> time_userid_linkedhashmap : time_userid_linkedhashmap.entrySet()) {
                        String selected_time_slot_string_key = time_userid_linkedhashmap.getKey();

                        Log.i("test_real_data", "============= 1st key ============="+selected_time_slot_string_key);

                        // Checking if new entry we have to do if already that date of data is not present ---------------------------------------------------------------------------------
                        QuerySnapshot documents = task.getResult();
                        int server_booking_data_size = documents.size();
                        if(server_booking_data_size == 0){
                            LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String,String>>> data = new LinkedHashMap<>();
                            data.put(selected_time_slot_string_key, linked_hash_map_userid);
                            submitBookingDetails("1", data);
                            rl_slots_booking_progressbar.setVisibility(View.GONE);
                            recycleData();
                        }
                        // END : Checking if new entry we have to do if already that date of data is not present ---------------------------------------------------------------------------------



                        // 2nd Loop : Red server data, and choose perfect place to submit data  ---------------------------------------------------------------------------------
                        outerloop:
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            String[] parts = document.getId().split("_");
                            String owner_id = parts[0]; // "bHbwpPf7xFhJXX42vC9pEomm8Ey2"
                            String date = parts[1]; // "14 Apr 2023"


                            // Check which date of data you want to read ---------------------------------------------------------
                            if (document.getId().contains(owner_place_id_string+"_"+show_selected_date_data)) {

                                Map<String, Object> bookingData = document.getData();


                                int curren_innerloop_tIteration = 1;
                                int total_innerloop_Iterations = bookingData.entrySet().size();


                                // 3rd Loop : Number of nets, Net wise read timeslots, 3rd loop Nets, 4th loop timeslots  ---------------------------------------------------------------------------------
                                innerloop:
                                for (Map.Entry<String, Object> entry : bookingData.entrySet()) {
                                    String list_number_key = entry.getKey();

//                                    Log.i("test_real_data", "============= 3rd key =============" + list_number_key);

                                    Map<String, Object> value = (Map<String, Object>) entry.getValue();


                                    // 4th Loop : get timeslots from server   ---------------------------------------------------------------------------------
                                    boolean isSameTimeSlotAlreadyFound = false;
                                    for (Map.Entry<String, Object> innerEntry : value.entrySet()) {
                                        String timeSlot = innerEntry.getKey();
//                                        Log.i("test_real_data", "============= 4th key =============" + timeSlot);

                                        if (selected_time_slot_string_key.contains(timeSlot)) {
//                                            Log.i("test_real_data", "@@@@@@@@@@ SAME TO SAME @@@@@@@@@");
                                            isSameTimeSlotAlreadyFound = true;
                                        }

                                    }
                                    // END : 4th Loop : get timeslots from server   ---------------------------------------------------------------------------------


                                    // loop finish still not found same value than submit -----------------------------------------------------------------
                                    if (!isSameTimeSlotAlreadyFound) {
//                                        Log.i("test_real_data", "$$$$$$$$$ It's UNIQUE SUBMIT YOUR DATA TO : " + list_number_key);
                                        LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String,String>>> data = new LinkedHashMap<>();
                                        data.put(selected_time_slot_string_key, linked_hash_map_userid);
                                        submitBookingDetails(""+list_number_key, data);
                                        rl_slots_booking_progressbar.setVisibility(View.GONE);
                                        recycleData();
                                        break  innerloop;
                                    }
                                    //------------------------------------------------------------------------------------------------------------


                                    // check if it is the last innerloop iteration, and still isSameTimeSlotAlreadyFound = true, than submit your data to next node
                                    if (curren_innerloop_tIteration == total_innerloop_Iterations && isSameTimeSlotAlreadyFound) {
                                        // code to execute for the last iteration
//                                        Log.i("test_real_data", "$$$$$$$$$ It's UNIQUE SUBMIT YOUR DATA TO : " + (Integer.parseInt(list_number_key)+1));
                                        LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String,String>>> data = new LinkedHashMap<>();
                                        data.put(selected_time_slot_string_key, linked_hash_map_userid);
                                        submitBookingDetails(""+(Integer.parseInt(list_number_key)+1), data);
                                        rl_slots_booking_progressbar.setVisibility(View.GONE);
                                        recycleData();
                                    }

                                    curren_innerloop_tIteration++;

                                }
                                // END : 3rd Loop : Number of nets, Net wise read timeslots  ---------------------------------------------------------------------------------

                            }else{
                                // new date found
//                                Log.i("test_real_data", "$$$$$$$$$ It's UNIQUE SUBMIT Your Data, NEW DATE FOUND");
                                LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String,String>>> data = new LinkedHashMap<>();
                                data.put(selected_time_slot_string_key, linked_hash_map_userid);
                                submitBookingDetails("1", data);
                                rl_slots_booking_progressbar.setVisibility(View.GONE);
                                recycleData();
                                break  outerloop;
                            }
                            // END : If else : If : date found, Else : New Date found -------------------------------------------------------------------------

                        }
                        // END : Outerloop : 2nd Loop : Red server data, and choose perfect place to submit data  ---------------------------------------------------------------------------------
                    }
                    // END : 1st loop for number of selected timeslots ---------------------------------------------------------------------------------


                }

            }
        });

    }
    //********************************************************************************************************************


    public static void submitBookingDetails(String net_number_string, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> data){
        DocumentReference documentRef = firebaseFirestore.collection(Utils.key_place_booking_firestore).document(final_booking_id);

        Log.d("test_real_data", "Inside : "+net_number_string+" ,"+data.toString());

        LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String,String>>>> data2 = new LinkedHashMap<>();
        data2.put(net_number_string, data);

        documentRef.set(data2, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "Document updated/created successfully!");
                Log.d("test_real_data", "SUCESSSSSSS");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", "Error updating/creating document: ", e);
                Log.d("test_real_data", "Error : "+e.getMessage());
            }
        });

    }
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
    @Override
    protected void onStart() {
        try {
            IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(networkChangeListener, intentFilter);
        }catch (Exception e){
            e.getMessage();
        }
        super.onStart();
    }
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-


    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
    @Override
    protected void onStop() {
        try{
            unregisterReceiver(networkChangeListener);
        }catch (Exception e){
            e.getMessage();
        }
        super.onStop();
    }
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

}