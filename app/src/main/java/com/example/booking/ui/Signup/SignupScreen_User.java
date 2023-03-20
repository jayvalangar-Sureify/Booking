package com.example.booking.ui.Signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.booking.R;
import com.example.booking.databinding.ActivitySignupScreenUserBinding;
import com.example.booking.ui.Login.LoginScreenActivity;

public class SignupScreen_User extends AppCompatActivity {

    ActivitySignupScreenUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen_user);

        binding = ActivitySignupScreenUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signupTvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupScreen_User.this, LoginScreenActivity.class);
                startActivity(i);
            }
        });
    }
}