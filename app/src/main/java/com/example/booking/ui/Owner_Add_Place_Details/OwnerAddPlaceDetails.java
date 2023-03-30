package com.example.booking.ui.Owner_Add_Place_Details;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.booking.MainActivity;
import com.example.booking.R;
import com.example.booking.Utils;
import com.example.booking.databinding.ActivityOwnerAddPlaceDetailsBinding;
import com.example.booking.interfaces.PincodeApiServiceInterface;
import com.example.booking.model.PincodeModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OwnerAddPlaceDetails extends AppCompatActivity {

    Double owner_place_latitude, owner_place_longitude;
    ActivityOwnerAddPlaceDetailsBinding binding;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String userID_string;

    String[] place_opening_time_spinner = {"12 AM", "01 AM", "02 AM", "03 AM", "04 AM", "05 AM", "06 AM", "07 AM", "08 AM", "09 AM", "10 AM", "11 AM", "12 PM", "1 PM", "2 PM", "3 PM", "4 PM", "5 PM", "6 PM", "7 PM", "8 PM", "9 PM", "10 PM", "11 PM"};
    String[] place_closing_time_spinner = {"12 AM", "01 AM", "02 AM", "03 AM", "04 AM", "05 AM", "06 AM", "07 AM", "08 AM", "09 AM", "10 AM", "11 AM", "12 PM", "1 PM", "2 PM", "3 PM", "4 PM", "5 PM", "6 PM", "7 PM", "8 PM", "9 PM", "10 PM", "11 PM"};

    ArrayAdapter<String> place_opening_time_spinner_adapter;
    ArrayAdapter<String> place_closing_time_spinner_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_add_place_details);
        binding = ActivityOwnerAddPlaceDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // Create a new ArrayAdapter and set it as the adapter for the spinner
        place_opening_time_spinner_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, place_opening_time_spinner);
        place_closing_time_spinner_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, place_closing_time_spinner);

        binding.ownerPlaceOpeningSpinner.setAdapter(place_opening_time_spinner_adapter);
        binding.ownerPlaceClosingSpinner.setAdapter(place_closing_time_spinner_adapter);

        // Set default time
        binding.ownerPlaceOpeningSpinner.setSelection(7);
        binding.ownerPlaceClosingSpinner.setSelection(23);
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=






        // get latitude and longitude
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        Intent intent=getIntent();
        owner_place_latitude = intent.getDoubleExtra(Utils.key_latitude, 0);
        owner_place_longitude = intent.getDoubleExtra(Utils.key_longitude, 0);
        Log.i("test_response", "");
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

        // Button Add Owner Place
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        binding.btnOwnerAddPlaceSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // re-set error
                binding.tvOwnerAddError.setText("");

                String owner_place_name_String = binding.etOwnerAddPlaceName.getText().toString();
                String owner_place_pincode_String = binding.etOwnerAddPlacePincode.getText().toString();
                String owner_place_country_state_district_String = binding.etOwnerAddPlaceDistrictCityCountry.getText().toString();
                String owner_place_address_String = binding.etOwnerAddPlaceAddress.getText().toString();
                String owner_place_staff_phone_number_string = binding.etOwnerAddPlaceStuffNumber.getText().toString();
                String owner_place_total_nets_String = binding.etOwnerAddPlaceAvailableNets.getText().toString();
                String owner_place_opening_time_String = binding.ownerPlaceOpeningSpinner.getSelectedItem().toString();
                String owner_place_closing_time_String = binding.ownerPlaceClosingSpinner.getSelectedItem().toString();
                String owner_place_per_hour_rent_String = binding.etOwnerPlacePerHourRent.getText().toString();



                if(TextUtils.isEmpty(owner_place_name_String)){
                    binding.tvOwnerAddError.setText(getString(R.string.error_enter_place_name));
                    binding.etOwnerAddPlaceName.setError(getString(R.string.error_enter_place_name));
                    return;
                }


                if(TextUtils.isEmpty(owner_place_pincode_String)){
                    binding.tvOwnerAddError.setText(getString(R.string.error_enter_pincode));
                    binding.etOwnerAddPlacePincode.setError(getString(R.string.error_enter_pincode));
                    return;
                }


                if(TextUtils.isEmpty(owner_place_country_state_district_String)){
                    binding.tvOwnerAddError.setText(getString(R.string.error_state_city_district));
                    binding.etOwnerAddPlaceDistrictCityCountry.setError(getString(R.string.error_state_city_district));
                    return;
                }


                if(TextUtils.isEmpty(owner_place_address_String)){
                    binding.tvOwnerAddError.setText(getString(R.string.error_enter_address));
                    binding.etOwnerAddPlaceAddress.setError(getString(R.string.error_enter_address));
                    return;
                }


                if(TextUtils.isEmpty(owner_place_staff_phone_number_string)){
                    binding.tvOwnerAddError.setText(getString(R.string.error_enter_ground_staff_number));
                    binding.etOwnerAddPlaceStuffNumber.setError(getString(R.string.enter_valid_name));
                    return;
                }


                if(TextUtils.isEmpty(owner_place_total_nets_String)){
                    binding.tvOwnerAddError.setText(getString(R.string.error_enter_total_nets_Available));
                    binding.etOwnerAddPlaceAvailableNets.setError(getString(R.string.error_enter_total_nets_Available));
                    return;
                }

                if(TextUtils.isEmpty(owner_place_opening_time_String)){
                    binding.tvOwnerAddError.setText(getString(R.string.error_enter_place_opening_time));
                    binding.etOwnerAddPlaceAvailableNets.setError(getString(R.string.error_enter_total_nets_Available));
                    return;
                }

                if(TextUtils.isEmpty(owner_place_closing_time_String)){
                    binding.tvOwnerAddError.setText(getString(R.string.error_enter_place_close_time));
                    binding.etOwnerAddPlaceAvailableNets.setError(getString(R.string.error_enter_total_nets_Available));
                    return;
                }

                if(TextUtils.isEmpty(owner_place_per_hour_rent_String)){
                    binding.tvOwnerAddError.setText(getString(R.string.error_enter_place_default_per_hour_rent));
                    binding.etOwnerAddPlaceAvailableNets.setError(getString(R.string.error_enter_place_default_per_hour_rent));
                    return;
                }



                int opening_time_index = binding.ownerPlaceOpeningSpinner.getSelectedItemPosition();
                int closing_time_index = binding.ownerPlaceClosingSpinner.getSelectedItemPosition();

                int total_hours_place_is_open_int = 0;
                if(closing_time_index > opening_time_index){
                    total_hours_place_is_open_int = (closing_time_index - opening_time_index);
                }else{
                    total_hours_place_is_open_int = 24 + (closing_time_index - opening_time_index);
                }



                // Now start progressbar
                binding.addPlaceProgressBar.setVisibility(View.VISIBLE);



                                //------------------------------------------------------------------------------------------------------------------------------------
                                try {
                                    // Store data into FirebaseStore
                                    userID_string = firebaseAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference = firebaseFirestore.collection(Utils.key_ownerplace_firestore).document(userID_string);
                                    Map<String, Object> owner_place_detail_map = new HashMap<>();
                                    owner_place_detail_map.put(Utils.map_key_owner_place_name, owner_place_name_String);
                                    owner_place_detail_map.put(Utils.map_key_owner_place_latitude, owner_place_latitude);
                                    owner_place_detail_map.put(Utils.map_key_owner_place_longitude, owner_place_longitude);
                                    owner_place_detail_map.put(Utils.map_key_owner_place_address, owner_place_address_String);
                                    owner_place_detail_map.put(Utils.map_key_owner_place_pincode, owner_place_pincode_String);
                                    owner_place_detail_map.put(Utils.map_key_owner_place_country_city_district, owner_place_country_state_district_String);
                                    owner_place_detail_map.put(Utils.map_key_owner_place_full_address, owner_place_address_String + "\n" + "Pincode : " +owner_place_pincode_String+ "\n" + owner_place_country_state_district_String);
                                    owner_place_detail_map.put(Utils.map_key_owner_place_ground_staff_number, owner_place_staff_phone_number_string);
                                    owner_place_detail_map.put(Utils.map_key_owner_place_total_nets, owner_place_total_nets_String);
                                    owner_place_detail_map.put(Utils.map_key_owner_place_opening_time, owner_place_opening_time_String);
                                    owner_place_detail_map.put(Utils.map_key_owner_place_closing_time, owner_place_closing_time_String);
                                    owner_place_detail_map.put(Utils.map_key_owner_default_per_hour_rent, owner_place_per_hour_rent_String);
                                    owner_place_detail_map.put(Utils.map_key_owner_place_total_hours_open, total_hours_place_is_open_int);

                                    documentReference.set(owner_place_detail_map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.i("test_response", "Data added successfully entered on FIRESTORE");

                                            Utils.set_SharedPreference_owner_completed_add_placele_procedure("1", getApplicationContext());
                                            // Signing Successful Redirect to Dashboard
                                            //------------------------------------------------------------------------------------------------------------------------------------
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                            finish();
                                            //------------------------------------------------------------------------------------------------------------------------------------

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Utils.set_SharedPreference_owner_completed_add_placele_procedure("0", getApplicationContext());
                                            binding.tvOwnerAddError.setText("Error : " + e.getMessage());
                                            Log.i("test_response", "Error : " + e.getMessage());
                                        }
                                    });

                                } catch (Exception e) {
                                    Utils.set_SharedPreference_owner_completed_add_placele_procedure("0", getApplicationContext());
                                    binding.tvOwnerAddError.setText("Error : " + e.getMessage());
                                    e.getMessage();
                                }
                                //------------------------------------------------------------------------------------------------------------------------------------

                        binding.addPlaceProgressBar.setVisibility(View.GONE);
                    }
        });

        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


        // Pincode Textwatcher
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        binding.etOwnerAddPlacePincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // This method is called before the text is changed
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // This method is called when the text is changed

                if(charSequence != null) {
                    int length = charSequence.length();
                    if (length == 6) {
                        // start searching state, city, country
                        binding.addPlaceProgressBar.setVisibility(View.VISIBLE);
                        find_district_city_country(charSequence.toString());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // This method is called after the text is changed
            }
        });
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=




    }




    // From pincode get others data
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public void find_district_city_country(String pincode){
        // Create a Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.postalpincode.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

// Create an instance of the ApiService interface
        PincodeApiServiceInterface pincodeApiServiceInterface = retrofit.create(PincodeApiServiceInterface.class);

        // Call the API and receive the response
        Call<List<PincodeModel>> call = pincodeApiServiceInterface.getPincodeData(pincode);
        call.enqueue(new Callback<List<PincodeModel>>() {
            @Override
            public void onResponse(Call<List<PincodeModel>> call, Response<List<PincodeModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Use the pincode data here
                    PincodeModel pincodeModel = response.body().get(0);
                    try {
                        String country = pincodeModel.getPostOffice().get(0).getCountry();
                        String state = pincodeModel.getPostOffice().get(0).getState();
                        String district = pincodeModel.getPostOffice().get(0).getDistrict();
                        binding.etOwnerAddPlaceDistrictCityCountry.setText("" + country + "," + state + "," + district);
                    }catch (Exception e){
                        e.getMessage();
                        binding.etOwnerAddPlaceDistrictCityCountry.setText("");
                        Toast.makeText(OwnerAddPlaceDetails.this, "Enter Valid Pincode", Toast.LENGTH_LONG).show();
                    }
                    binding.addPlaceProgressBar.setVisibility(View.GONE);
                } else {
                    // Handle the error here
                    binding.addPlaceProgressBar.setVisibility(View.GONE);
                    binding.etOwnerAddPlaceDistrictCityCountry.setText("");
                    Toast.makeText(OwnerAddPlaceDetails.this, "Enter Valid Pincode", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<PincodeModel>> call, Throwable t) {
                // Handle the error here
                binding.addPlaceProgressBar.setVisibility(View.GONE);
                t.getMessage();
            }
        });

    }
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

}