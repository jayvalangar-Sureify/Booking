package com.example.booking;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.booking.databinding.ActivityMainBinding;
import com.example.booking.ui.SplashScreen.SplashScreen;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity {

    private LocationRequest locationRequest;
    private static final int REQUEST_CHECK_SETTINGS = 10001;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;


    // Fetch Data
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String user_Id_string;
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialization
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        user_Id_string = firebaseAuth.getCurrentUser().getUid();

        NavigationView navigationView = binding.navView;
        DrawerLayout drawer = binding.drawerLayout;
        int blue_text_color = ContextCompat.getColor(getApplicationContext(), R.color.combo_text_blue);
        navigationView.setItemTextColor(ColorStateList.valueOf(blue_text_color));
        navigationView.setItemIconTintList(ColorStateList.valueOf(blue_text_color));

        View headerView = navigationView.getHeaderView(0);
        TextView tv_nav_header_user_name = (TextView) headerView.findViewById(R.id.tv_nav_header_user_name);
        TextView tv_nav_header_login_type = (TextView) headerView.findViewById(R.id.tv_nav_header_login_type);
        ImageView iv_nav_header_profile = (ImageView) headerView.findViewById(R.id.iv_nav_header_profile);
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

        setSupportActionBar(binding.appBarMain.toolbar);



        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_booking_details, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        // Check User or Owner
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

            if (Utils.get_SharedPreference_type_of_login(getApplicationContext()).contains(Utils.user)) {
                // Fetch user Data from Firestore
                //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
               try {
                   DocumentReference documentReference = firebaseFirestore.collection(Utils.key_users_firestore).document(user_Id_string);
                   documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                       @Override
                       public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                           try {
                               String user_name_string = value.getString(Utils.map_key_User_Name);
                               String user_email_string = value.getString(Utils.map_key_User_Email);
                               String user_password_string = value.getString(Utils.map_key_User_Password);
                               String user_phone_number_string = value.getString(Utils.map_key_User_Phone_Number);

                               iv_nav_header_profile.setImageDrawable(getDrawable(R.drawable.icon_user));
                               tv_nav_header_login_type.setText("USER");
                               tv_nav_header_user_name.setText("Hello " + user_name_string + " !");
                           }catch (Exception e){
                               e.getMessage();
                           }


                       }
                   });
               }catch (Exception e){
                   e.getMessage();
               }
                //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
            }


        if (Utils.get_SharedPreference_type_of_login(getApplicationContext()).contains(Utils.owner)) {
                // Fetch owner Data from Firestore
                //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                try {
                    DocumentReference documentReference = firebaseFirestore.collection(Utils.key_owner_firestore).document(user_Id_string);
                    documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                            try {
                                String owner_name_string = value.getString(Utils.map_key_owner_Name);
                                String owner_email_string = value.getString(Utils.map_key_owner_Email);
                                String owner_password_string = value.getString(Utils.map_key_owner_Password);
                                String owner_phone_number_string = value.getString(Utils.map_key_owner_Phone_Number);



                                iv_nav_header_profile.setImageDrawable(getDrawable(R.drawable.icon_owner));
                                tv_nav_header_login_type.setText("OWNER");
                                tv_nav_header_user_name.setText("Hello " + owner_name_string + " !");
                            }catch (Exception e){
                                e.getMessage();
                            }


                        }
                    });
                } catch (Exception e) {
                    e.getMessage();
                }
                //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
            }
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

}

    @Override
    protected void onResume() {
        super.onResume();

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);


        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
//                    Toast.makeText(MainActivity.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException)e;
                                resolvableApiException.startResolutionForResult(MainActivity.this,REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CHECK_SETTINGS) {

            switch (resultCode) {
                case Activity.RESULT_OK:
//                    Toast.makeText(this, "GPS is tured on", Toast.LENGTH_SHORT).show();

                case Activity.RESULT_CANCELED:
                    Toast.makeText(this, "GPS required to be tured on", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    // App Bar Menu items
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                // do something
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, SplashScreen.class));
                finish();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


}