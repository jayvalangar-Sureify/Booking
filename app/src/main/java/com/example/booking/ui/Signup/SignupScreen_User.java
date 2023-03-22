package com.example.booking.ui.Signup;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.booking.MainActivity;
import com.example.booking.R;
import com.example.booking.databinding.ActivitySignupScreenUserBinding;
import com.example.booking.ui.Login.LoginScreenActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupScreen_User extends AppCompatActivity {

    ActivitySignupScreenUserBinding binding;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen_user);

        binding = ActivitySignupScreenUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // re-set error
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        binding.tvSignupErrorDisplay.setText("");
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


        // Initialize
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
          firebaseAuth = FirebaseAuth.getInstance();
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

                String user_name_String = binding.etSignupEnterUserName.getText().toString();
                String user_email_String = binding.etSignupEnterEmail.getText().toString();
                String user_password_String = binding.etSignupEnterPassword.getText().toString();
                String user_phone_String = binding.etSignupEnterPhoneNumber.getText().toString();

                if(TextUtils.isEmpty(user_name_String)){
                    binding.tvSignupErrorDisplay.setText("User name is required");
                    binding.etSignupEnterUserName.setError("User name is required");
                    return;
                }

                if(TextUtils.isEmpty(user_phone_String)){
                    binding.tvSignupErrorDisplay.setText("Phone Number is required");
                    binding.etSignupEnterPhoneNumber.setError("User Phone Number is required");
                    return;
                }

                if(TextUtils.isEmpty(user_email_String)){
                    binding.tvSignupErrorDisplay.setText("Email is required");
                    binding.etSignupEnterEmail.setError("User Email is required");
                    return;
                }

                if(TextUtils.isEmpty(user_password_String)){
                    binding.tvSignupErrorDisplay.setText("User Password is required");
                    binding.etSignupEnterPassword.setError("User Password is required");
                    return;
                }



                // Now start progressbar
                binding.signupProgressbar.setVisibility(View.VISIBLE);

                // Create User
                firebaseAuth.createUserWithEmailAndPassword(user_email_String, user_password_String).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
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