package com.example.booking;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.ArrayMap;
import android.util.Log;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Utils {
    public static String user = "user";
    public static String owner = "owner";


    // Razorpay Payment gateway
    public static String razorpay_key_id = "rzp_test_FTYNMgruxTdMMc";

    // booking Details
//    public static String key_get_Set_booking_user_id = "key_get_Set_booking_user_id";
//    public static String key_get_Set_booking_place_name = "key_get_Set_booking_place_name";
//    public static String key_get_Set_booking_place_latitude = "key_get_Set_booking_place_latitude";
//    public static String key_get_Set_booking_place_longitude = "key_get_Set_booking_place_longitude";
//    public static String key_get_Set_booking_staff_number = "key_get_Set_booking_staff_number";
//    public static String key_get_Set_booking_place_address = "key_get_Set_booking_place_address";
//    public static String key_get_Set_booking_date = "key_get_Set_booking_date";


    // Intent key
    public static String key_whole_place_details = "key_whole_place_details";


    // Location
    public static String key_latitude = "latitude";
    public static String key_longitude = "longitude";

    // Place Rent
    public static String key_per_hour_rent = "key_per_hour_rent";
    public static String key_monday = "Mon";
    public static String key_tuesday = "Tue";
    public static String key_wednesday = "Wed";
    public static String key_thursday = "Thu";
    public static String key_friday = "Fri";
    public static String key_saturday = "Sat";
    public static String key_sunday = "Sun";


    // Booking
    public static String key_place_booking_firestore = "PlaceBookingDetails";
    public static String key_booking_user_id = "key_booking_user_id";
    public static String key_booking_owner_id = "key_booking_user_id";
    public static String key_booking_date = "key_booking_date";
    public static String key_booking_user_name = "key_booking_user_name";
    public static String key_booking_user_number = "key_booking_user_number";
    public static String key_booking_owner_name = "key_booking_owner_name";
    public static String key_booking_staff_number = "key_booking_staff_number";
    public static String key_booking_time_slot = "key_booking_time_slot";
    public static String key_booking_place_name = "key_booking_place_name";
    public static String key_booking_place_address = "key_booking_place_address";
    public static String key_booking_place_latitude = "key_booking_place_latitude";
    public static String key_booking_place_longitude = "key_booking_place_longitude";




    // Firestore User
    public static String key_users_firestore = "Users";
    public static String map_key_User_Name = "User_Name";
    public static String map_key_User_Email = "User_Email";
    public static String map_key_User_Phone_Number = "User_Phone_Number";
    public static String map_key_User_Password = "User_Password";

    // Firestore Common
    public static String map_common_key_login_type = "Login_Type";

    // Firestore Owner
    public static String key_owner_firestore = "Owners";
    public static String map_key_owner_Name = "Owner_Name";
    public static String map_key_owner_Email = "Owner_Email";
    public static String map_key_owner_Phone_Number = "Owner_Phone_Number";
    public static String map_key_owner_Password = "Owner_Password";
    public static String map_key_owner_Location = "Owner_Place_Location";


    // Firestore OwnerPlace
    public static String key_ownerplace_firestore = "OwnerPlaces";
    public static String map_key_owner_place_name = "Owner_Place_Name";
    public static String map_key_owner_place_latitude = "Owner_Place_Latitude";
    public static String map_key_owner_place_longitude = "Owner_Place_Longitude";
    public static String map_key_owner_place_pincode = "Owner_Place_Pincode";
    public static String map_key_owner_place_country_city_district = "Owner_Place_Country_State_District";
    public static String map_key_owner_place_address = "Owner_Place_Address";
    public static String map_key_owner_place_full_address = "Owner_Place_Full_Address";
    public static String map_key_owner_place_ground_staff_number = "Owner_Place_Staff_Number";
    public static String map_key_owner_place_total_nets = "Owner_Place_Total_Nets";
    public static String map_key_owner_place_opening_time = "Owner_Place_Opening_Time";
    public static String map_key_owner_place_closing_time = "Owner_Place_Closing_Time";
    public static String map_key_owner_default_per_hour_rent = "Owner_Place_Default_Per_Hour_Rent";
    public static String map_key_owner_place_total_hours_open = "Owner_Place_Total_Hours_Open";
    public static String map_key_owner_place_day_hour_rent_hashmap = "Owner_Place_day_hour_rent";
    public static String key_owner_PlaceBookingDateTimeUserDetails_hashmap= "PlaceBookingDateTimeUserDetails";


    // Shared Preference Value
    //----------------------------------------------------------------------------------------------
    public static String shared_preference_key_LoggedInUser_name = "shared_preference_key_LoggedInUser_name";
    public static String shared_preference_key_LoggedInUser_email = "shared_preference_key_LoggedInUser_email";
    public static String shared_preference_key_LoggedInUser_phone_number = "shared_preference_key_LoggedInUser_phone_number";
    public static String shared_preference_key_LoggedInUser_password = "shared_preference_key_LoggedInUser_phone_password";
    //----------------------------------------------------------------------------------------------


    // Key Hash-Map User
    public static String key_user_hashmap = "key_user_hashmap";
    public static String key_user_hashmap_data = "key_user_hashmap_data";

    // Key Hash-Map Owner
    public static String key_owner_hashmap = "key_owner_hashmap";
    public static String key_owner_hashmap_data = "key_owner_hashmap_data";

    // sharedpreference key
    // splash activity
    public static String key_save_type_of_login = "key_save_type_of_login";
    public static String key_owner_completed_place_adding_procedure = "key_owner_completed_place_adding_procedure";



    // Convert a HashMap<String, ArrayMap<String, Integer>> to a String
//=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
    public static String hashMap_StringArraymap_ToString(HashMap<String, ArrayMap<String, Integer>> hashMap) {
        try {
            JSONObject json = new JSONObject(hashMap);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
//=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-


    // Convert a String to a HashMap<String, ArrayMap<String, Integer>>
//=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
    public static HashMap<String, ArrayMap<String, Integer>> stringToHashMap_StringArraymap(String jsonString) {
        try {
            JSONObject json = new JSONObject(jsonString);
            HashMap<String, ArrayMap<String, Integer>> hashMap = new HashMap<>();
            JSONArray keys = json.names();
            for (int i = 0; i < keys.length(); i++) {
                String key = keys.getString(i);
                ArrayMap<String, Integer> value = new ArrayMap<>();
                JSONArray subArray = json.getJSONArray(key);
                for (int j = 0; j < subArray.length(); j++) {
                    JSONObject subObject = subArray.getJSONObject(j);
                    String subKey = subObject.getString("key");
                    int subValue = subObject.getInt("value");
                    value.put(subKey, subValue);
                }
                hashMap.put(key, value);
            }

            return hashMap;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("test_response", ""+e.getMessage());
            return null;
        }
    }
//=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-



    // Convert a HashMap<String, ArrayMap<String, Integer>> to a String
//=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
    public static String convertHashMap_StringStringToString(HashMap<String, String> hashMap) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(hashMap);
        return jsonString;
    }

//=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-


    // Convert a String to a HashMap<String, String>
//=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

    public static HashMap<String, String> convertStringToHashMap_StringString(String jsonString) {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, String>>(){}.getType();
        HashMap<String, String> hashMap = new HashMap<>();
        try {
            hashMap = gson.fromJson(jsonString, type);
        }catch (Exception e){
            e.getMessage();
        }

        return hashMap;
    }
//=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

    // set login type
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
    public static void set_SharedPreference_type_of_login(String type_of_login, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(key_save_type_of_login, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key_save_type_of_login, type_of_login);
        editor.commit();
    }
    public static String get_SharedPreference_type_of_login(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key_save_type_of_login, MODE_PRIVATE);
        return sharedPreferences.getString(key_save_type_of_login, "user");
    }
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-


    // set if owner completed signup procedure
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
    public static void set_SharedPreference_owner_completed_add_placele_procedure(String isCompleted, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(key_owner_completed_place_adding_procedure, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key_owner_completed_place_adding_procedure, isCompleted);
        editor.commit();
    }
    public static String get_SharedPreference_owner_completed_add_placele_procedure(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key_owner_completed_place_adding_procedure, MODE_PRIVATE);
        return sharedPreferences.getString(key_owner_completed_place_adding_procedure, "0");
    }
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-


    public static HashMap<String, HashMap<String, Integer>> stringToHashMap(String str) {
        HashMap<String, HashMap<String, Integer>> result = new HashMap<>();

        // Remove curly braces and split the string by commas
        String[] pairs = str.replaceAll("[{}]", "").split(",");

        // Loop through each key-value pair
        for (String pair : pairs) {
            // Split the key and value by the colon
            String[] keyValue = pair.split(":");

            // Extract the day of the week from the key
            String dayOfWeek = keyValue[0].replaceAll("\"", "");

            // Extract the time slots and values from the value
            String[] timeSlotsAndValues = keyValue[1].replaceAll("[{}\"]", "").split(",");

            // Create a new HashMap to store the time slots and values
            HashMap<String, Integer> timeSlots = new HashMap<>();

            // Loop through each time slot and value
            for (String timeSlotAndValue : timeSlotsAndValues) {
                // Split the time slot and value by the colon
                String[] timeSlotAndValueArray = timeSlotAndValue.split(":");

                // Extract the time slot and value
                String timeSlot = timeSlotAndValueArray[0].trim();
                try {
                    int value = Integer.parseInt(timeSlotAndValueArray[1].trim());

                    // Add the time slot and value to the HashMap
                    timeSlots.put(timeSlot, value);
                }catch (Exception e){
                    e.getMessage();
                }
            }

            // Add the day of the week and time slots to the result HashMap
            result.put(dayOfWeek, timeSlots);
        }

        return result;
    }


    //-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=--=
    public static int getDayfromDate(String input_date_string){
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(input_date_string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }
    //=-=--=-=-=-=-=--=-=-=-=-=-=-=-=--==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
}