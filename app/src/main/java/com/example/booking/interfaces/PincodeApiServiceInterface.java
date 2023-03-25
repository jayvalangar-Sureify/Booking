package com.example.booking.interfaces;

import com.example.booking.model.PincodeModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PincodeApiServiceInterface {
    @GET("pincode/{pincode}")
    Call<List<PincodeModel>> getPincodeData(@Path("pincode") String pincode);
}
