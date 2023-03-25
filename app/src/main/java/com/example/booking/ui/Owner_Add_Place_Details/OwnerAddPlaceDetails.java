package com.example.booking.ui.Owner_Add_Place_Details;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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

    ActivityOwnerAddPlaceDetailsBinding binding;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String userID_string;

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

                // Now start progressbar
                binding.addPlaceProgressBar.setVisibility(View.VISIBLE);



                                //------------------------------------------------------------------------------------------------------------------------------------
                                try {
                                    // Store data into FirebaseStore
                                    userID_string = firebaseAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference = firebaseFirestore.collection(Utils.key_ownerplace_firestore).document(userID_string);
                                    Map<String, Object> user_map = new HashMap<>();
                                    user_map.put(Utils.map_key_owner_place_name, owner_place_name_String);
                                    user_map.put(Utils.map_key_owner_place_address, owner_place_address_String);
                                    user_map.put(Utils.map_key_owner_place_pincode, owner_place_pincode_String);
                                    user_map.put(Utils.map_key_owner_place_ground_staff_number, owner_place_country_state_district_String);
                                    user_map.put(Utils.map_key_owner_place_full_address, owner_place_address_String + "\n" + "Pincode : " +owner_place_pincode_String+ "\n" + owner_place_country_state_district_String);
                                    user_map.put(Utils.map_key_owner_place_ground_staff_number, owner_place_staff_phone_number_string);
                                    user_map.put(Utils.map_key_owner_place_total_nets, owner_place_total_nets_String);

                                    documentReference.set(user_map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.i("test_response", "Data added successfully entered on FIRESTORE");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            binding.tvOwnerAddError.setText("Error : " + e.getMessage());
                                            Log.i("test_response", "Error : " + e.getMessage());
                                        }
                                    });

                                } catch (Exception e) {
                                    e.getMessage();
                                }
                                //------------------------------------------------------------------------------------------------------------------------------------


                            // Signing Successful Redirect to Dashboard
                            //------------------------------------------------------------------------------------------------------------------------------------
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
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