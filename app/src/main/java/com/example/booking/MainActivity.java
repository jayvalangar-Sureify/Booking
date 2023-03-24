package com.example.booking;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.booking.databinding.ActivityMainBinding;
import com.example.booking.ui.Owner_Add_Place_Details.OwnerAddPlaceDetails;
import com.example.booking.ui.SplashScreen.SplashScreen;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity {

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

        View headerView = navigationView.getHeaderView(0);
        TextView tv_nav_header_user_name = (TextView) headerView.findViewById(R.id.tv_nav_header_user_name);
        TextView tv_nav_header_login_type = (TextView) headerView.findViewById(R.id.tv_nav_header_login_type);
        ImageView iv_nav_header_profile = (ImageView) headerView.findViewById(R.id.iv_nav_header_profile);
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, OwnerAddPlaceDetails.class));
            }
        });


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
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