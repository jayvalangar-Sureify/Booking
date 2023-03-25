package com.example.booking.ui.Owner_Add_Place_Details;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.booking.R;
import com.example.booking.databinding.ActivityOwnerAddPlaceDetailsBinding;
import com.example.booking.interfaces.PincodeApiServiceInterface;
import com.example.booking.model.PincodeModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OwnerAddPlaceDetails extends AppCompatActivity {

    ActivityOwnerAddPlaceDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_add_place_details);
        binding = ActivityOwnerAddPlaceDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Initialize
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
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