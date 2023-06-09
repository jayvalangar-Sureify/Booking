package com.example.booking.ui.SplashScreen;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.booking.ConnectivityReceiver;
import com.example.booking.FirebaseProductionSingletonClass;
import com.example.booking.MainActivity;
import com.example.booking.R;
import com.example.booking.Utils;
import com.example.booking.databinding.ActivitySplashScreenBinding;
import com.example.booking.ui.Login.LoginScreenActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    ConnectivityReceiver networkChangeListener = new ConnectivityReceiver();
    ActivitySplashScreenBinding binding;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // set build env first
        //-=-=-=--=-=-=-=-=-=--==-=-=--=-=-=-=-=-=-=-=-=-=-=-=--=-==--=-=-=-=-=-=--=-=-=-=-=-=-=-=-=
          Utils.set_SharedPreference_staging_or_production_enviorment(Utils.value_production, getApplicationContext());
        //-=-=-=--=-=-=-=-=-=--==-=-=--=-=-=-=-=-=-=-=-=-=-=-=--=-==--=-=-=-=-=-=--=-=-=-=-=-=-=-=-=

        // Initialize
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
          if(Utils.get_SharedPreference_staging_or_production_enviorment(getApplicationContext()).contains(Utils.value_production)){
              firebaseAuth = FirebaseAuth.getInstance(FirebaseProductionSingletonClass.getInstance(getApplicationContext()));
              setTitle(getString(R.string.app_name));
          }else{
              firebaseAuth = FirebaseAuth.getInstance();
              setTitle(getString(R.string.app_name_staging));

          }
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


        // Check If session is running ? IF yes than open dashboard
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
        binding.btnSplashUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.set_SharedPreference_type_of_login(Utils.user, getApplicationContext());
                Intent i = new Intent(SplashScreen.this, LoginScreenActivity.class);
                startActivity(i);
                finish();
            }
        });
        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-


        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
        binding.btnSplashOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.set_SharedPreference_type_of_login(Utils.owner, getApplicationContext());
                Intent i = new Intent(SplashScreen.this, LoginScreenActivity.class);
                startActivity(i);
                finish();
            }
        });
        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-


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