package com.example.booking.ui.Signup;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.booking.MainActivity;
import com.example.booking.R;
import com.example.booking.Utils;
import com.example.booking.databinding.ActivitySignupScreenUserBinding;
import com.example.booking.ui.Login.LoginScreenActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupScreen_User extends AppCompatActivity {
    String get_type_of_login;
    String userID_string;

    ActivitySignupScreenUserBinding binding;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen_user);
        binding = ActivitySignupScreenUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Initialize
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


        get_type_of_login = Utils.get_SharedPreference_type_of_login(getApplicationContext());

        // re-set error
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        binding.tvSignupErrorDisplay.setText("");
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=




        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        binding.signupTvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupScreen_User.this, LoginScreenActivity.class);
                startActivity(i);
            }
        });
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // re-set error
                binding.tvSignupErrorDisplay.setText("");

                String name_String = binding.etSignupEnterUserName.getText().toString();
                String email_String = binding.etSignupEnterEmail.getText().toString();
                String password_String = binding.etSignupEnterPassword.getText().toString();
                String phone_String = binding.etSignupEnterPhoneNumber.getText().toString();

                if(TextUtils.isEmpty(name_String)){
                    binding.tvSignupErrorDisplay.setText(getString(R.string.enter_valid_name));
                    binding.etSignupEnterUserName.setError(getString(R.string.enter_valid_name));
                    return;
                }

                if(TextUtils.isEmpty(email_String)){
                    binding.tvSignupErrorDisplay.setText(getString(R.string.enter_valid_email));
                    binding.etSignupEnterPhoneNumber.setError(getString(R.string.enter_valid_email));
                    return;
                }

                if(TextUtils.isEmpty(password_String)){
                    binding.tvSignupErrorDisplay.setText(getString(R.string.enter_valid_password));
                    binding.etSignupEnterEmail.setError(getString(R.string.enter_valid_password));
                    return;
                }

                if(TextUtils.isEmpty(phone_String)){
                    binding.tvSignupErrorDisplay.setText(getString(R.string.enter_valid_phone_number));
                    binding.etSignupEnterPassword.setError(getString(R.string.enter_valid_phone_number));
                    return;
                }



                // Now start progressbar
                binding.signupProgressbar.setVisibility(View.VISIBLE);

                // Create User
                firebaseAuth.createUserWithEmailAndPassword(email_String, password_String).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){


                            if(get_type_of_login.contains(Utils.user)) {
                                // Login Type User
                                //------------------------------------------------------------------------------------------------------------------------------------
                                try {
                                    // Store data into FirebaseStore
                                    userID_string = firebaseAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference = firebaseFirestore.collection(Utils.key_users_firestore).document(userID_string);
                                    Map<String, Object> user_map = new HashMap<>();
                                    user_map.put(Utils.map_key_User_Name, name_String);
                                    user_map.put(Utils.map_key_User_Email, email_String);
                                    user_map.put(Utils.map_key_User_Password, password_String);
                                    user_map.put(Utils.map_key_User_Phone_Number, phone_String);
                                    user_map.put(Utils.map_common_key_login_type, Utils.user);

                                    documentReference.set(user_map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.i("test_response", "Data added successfully entered on FIRESTORE");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            binding.tvSignupErrorDisplay.setText("Error : " + e.getMessage());
                                            Log.i("test_response", "Error : " + e.getMessage());
                                        }
                                    });

                                } catch (Exception e) {
                                    e.getMessage();
                                }
                                //------------------------------------------------------------------------------------------------------------------------------------
                            }else {

                                // Login Type Owner
                                //------------------------------------------------------------------------------------------------------------------------------------
                                try {
                                    // Store data into FirebaseStore
                                    userID_string = firebaseAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference = firebaseFirestore.collection(Utils.key_owner_firestore).document(userID_string);
                                    Map<String, Object> owner_map = new HashMap<>();
                                    owner_map.put(Utils.map_key_owner_Name, name_String);
                                    owner_map.put(Utils.map_key_owner_Email, email_String);
                                    owner_map.put(Utils.map_key_owner_Password, password_String);
                                    owner_map.put(Utils.map_key_owner_Phone_Number, phone_String);
                                    owner_map.put(Utils.map_common_key_login_type, Utils.owner);

                                    documentReference.set(owner_map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.i("test_response", "Data added successfully entered on FIRESTORE");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            binding.tvSignupErrorDisplay.setText("Error : " + e.getMessage());
                                            Log.i("test_response", "Error : " + e.getMessage());
                                        }
                                    });

                                } catch (Exception e) {
                                    e.getMessage();
                                }
                                //------------------------------------------------------------------------------------------------------------------------------------
                              }


                            // Signing Successful Redirect to Dashboard
                            //------------------------------------------------------------------------------------------------------------------------------------
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            //------------------------------------------------------------------------------------------------------------------------------------
                        }else{
                            // set error
                            binding.tvSignupErrorDisplay.setText("Error : "+task.getException().getMessage());
                        }


                        binding.signupProgressbar.setVisibility(View.GONE);
                    }
                });
            }
        });
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    }
}