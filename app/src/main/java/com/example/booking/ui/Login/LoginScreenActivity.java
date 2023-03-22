package com.example.booking.ui.Login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.booking.MainActivity;
import com.example.booking.R;
import com.example.booking.databinding.ActivityLoginScreenBinding;
import com.example.booking.ui.Signup.SignupScreen_User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginScreenActivity extends AppCompatActivity {

    ActivityLoginScreenBinding binding;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        binding = ActivityLoginScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        firebaseAuth = FirebaseAuth.getInstance();
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


        // Check If session is running ? IF yes than open dashboard
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=



        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        binding.loginTvNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginScreenActivity.this, SignupScreen_User.class);
                startActivity(i);
            }
        });
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_email_String = binding.etLoginEnterEmail.getText().toString();
                String user_password_String = binding.etLoginEnterPassword.getText().toString();

                if(TextUtils.isEmpty(user_email_String)){
                    binding.tvLoginErrorDisplay.setText("User Email is required");
                    binding.etLoginEnterEmail.setError("User Email is required");
                    return;
                }

                if(TextUtils.isEmpty(user_password_String)){
                    binding.tvLoginErrorDisplay.setText("User Password is required");
                    binding.etLoginEnterPassword.setError("User Password is required");
                    return;
                }

                // Now start progressbar
                binding.loginProgressbar.setVisibility(View.VISIBLE);

                //Login User
                firebaseAuth.signInWithEmailAndPassword(user_email_String, user_password_String).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else{
                            binding.tvLoginErrorDisplay.setText("Error : "+task.getException().getMessage());
                        }


                        // Progressbar gone
                        binding.loginProgressbar.setVisibility(View.GONE);
                    }
                });
            }
        });
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=



        // Forgot Password
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        binding.tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // create an alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginScreenActivity.this);
                builder.setCancelable(true);

                // set the custom layout
                final View customLayout = getLayoutInflater().inflate(R.layout.dialog_reset_password, null);
                builder.setView(customLayout);

                //-------------------------------------------------------------------------------
                EditText et_reset_password_enter_email;
                TextView tv_reset_passwrd_error, tv_reset_password_info;
                Button btn_reset_password;
                LinearLayout reset_password_ll;
                ImageView iv_reset_password_right;
                reset_password_ll = (LinearLayout) customLayout.findViewById(R.id.reset_password_ll);
                et_reset_password_enter_email = (EditText) customLayout.findViewById(R.id.et_reset_password_enter_email);
                tv_reset_password_info = (TextView) customLayout.findViewById(R.id.tv_reset_password_info);
                tv_reset_passwrd_error = (TextView) customLayout.findViewById(R.id.tv_reset_passwrd_error);
                iv_reset_password_right = (ImageView) customLayout.findViewById(R.id.iv_reset_right_image);
                btn_reset_password = (Button) customLayout.findViewById(R.id.btn_reset_password);
                //-------------------------------------------------------------------------------




                btn_reset_password.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String reset_email_string = et_reset_password_enter_email.getText().toString();
                        if(TextUtils.isEmpty(reset_email_string)){
                            tv_reset_passwrd_error.setText("Enter Email !");
                            et_reset_password_enter_email.setError("Enter Email !");
                            return;
                        }


                        firebaseAuth.sendPasswordResetEmail(reset_email_string).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                tv_reset_passwrd_error.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.combo_text_green));
                                tv_reset_passwrd_error.setText("Email Sent Successfully, Check Your mail !");
                                iv_reset_password_right.setVisibility(View.VISIBLE);
                                reset_password_ll.setVisibility(View.GONE);
                                tv_reset_password_info.setVisibility(View.GONE);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                tv_reset_passwrd_error.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                                tv_reset_passwrd_error.setText("Error : "+e.getMessage());
                            }
                        });
                    }
                });
                // add a button
                builder.setPositiveButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    }
}