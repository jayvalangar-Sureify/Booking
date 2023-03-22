package com.example.booking;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class Utils {
    public static String user = "user";
    public static String owner = "owner";

    // Firestore
    public static String key_users_firestore = "Users";
    public static String map_key_User_Name = "User_Name";
    public static String map_key_User_Email = "User_Email";
    public static String map_key_User_Phone_Number = "User_Phone_Number";
    public static String map_key_User_Password = "User_Password";

    public static String key_owner_firestore = "Owners";
    public static String map_key_owner_Name = "Owner_Name";
    public static String map_key_owner_Email = "Owner_Email";
    public static String map_key_owner_Phone_Number = "Owner_Phone_Number";
    public static String map_key_owner_Password = "Owner_Password";


    // Key Hash-Map User
    public static String key_user_hashmap = "key_user_hashmap";
    public static String key_user_hashmap_data = "key_user_hashmap_data";

    // Key Hash-Map Owner
    public static String key_owner_hashmap = "key_owner_hashmap";
    public static String key_owner_hashmap_data = "key_owner_hashmap_data";

    // sharedpreference key
    // splash activity
    public static String key_save_type_of_login = "key_save_type_of_login";



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
}
