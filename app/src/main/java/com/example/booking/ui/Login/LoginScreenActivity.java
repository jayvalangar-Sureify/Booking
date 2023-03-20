package com.example.booking.ui.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.booking.R;
import com.example.booking.databinding.ActivityLoginScreenBinding;
import com.example.booking.ui.Signup.SignupScreen_User;

public class LoginScreenActivity extends AppCompatActivity {

    ActivityLoginScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        binding = ActivityLoginScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginTvNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginScreenActivity.this, SignupScreen_User.class);
                startActivity(i);
            }
        });


    }
}