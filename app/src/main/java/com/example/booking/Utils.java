package com.example.booking;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class Utils {
    public static String user = "user";
    public static String owner = "owner";

    // Location
    public static String key_latitude = "latitude";
    public static String key_longitude = "longitude";

    // Place Rent
    public static String key_per_hour_rent = "key_per_hour_rent";
    public static String key_monday = "monday";
    public static String key_tuesday = "tuesday";
    public static String key_wednesday = "wednesday";
    public static String key_thursday = "thursday";
    public static String key_friday = "friday";
    public static String key_saturday = "saturday";
    public static String key_sunday = "sunday";



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

}


