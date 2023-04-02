package com.example.booking.ui.UserClickOnMap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking.R;
import com.example.booking.Utils;
import com.example.booking.adapter.UserClickOnMapSlotsAdapter;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.HashMap;


public class UserClickOnMapShowThatPlaceAllDetails extends AppCompatActivity {

    public HashMap<String, String> place_whole_details_hashmap = new HashMap<>();
    HashMap<String, HashMap<String, Integer>> map;
    private UserClickOnMapSlotsAdapter userClickOnMapSlotsAdapter;
    private RecyclerView recycleview_show_available_time_day_slots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_click_on_map_show_that_place_all_details);

        Intent intent=getIntent();
        String getHashmapPlaceWholeData_String = intent.getStringExtra(Utils.key_whole_place_details);
        place_whole_details_hashmap = Utils.convertStringToHashMap_StringString(getHashmapPlaceWholeData_String);

        int maxLogSize = 1000;
        for(int i = 0; i <= getHashmapPlaceWholeData_String.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i+1) * maxLogSize;
            end = end > getHashmapPlaceWholeData_String.length() ? getHashmapPlaceWholeData_String.length() : end;
            Log.v("test_response", getHashmapPlaceWholeData_String.substring(start, end));
        }

        place_whole_details_hashmap.entrySet().forEach(entry -> {
            // seperating latitude and longitude from key
            String key = entry.getKey();

            if(key.equals("time_slots_converting_hashmap_to_string")){
                String remove_forward_space_value = entry.getValue().replace("/", "");
                String remove_backward_space_value = remove_forward_space_value.replace("\\", "");
                Gson gson = new Gson();
                Type typeOfHashMap = new TypeToken<HashMap<String, HashMap<String, Integer>>>() {}.getType();
                map = gson.fromJson(remove_backward_space_value, typeOfHashMap);

            }
        });


        if(map != null) {

//         //Initialize RecyclerView and Adapter
            recycleview_show_available_time_day_slots = findViewById(R.id.recycleview_show_available_time_day_slots);
            userClickOnMapSlotsAdapter = new UserClickOnMapSlotsAdapter(map);
            recycleview_show_available_time_day_slots.setAdapter(userClickOnMapSlotsAdapter);
            recycleview_show_available_time_day_slots.setLayoutManager(new GridLayoutManager(this, 2));
        }
    }
}