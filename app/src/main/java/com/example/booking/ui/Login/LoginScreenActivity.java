package com.example.booking.ui.Login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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
import com.example.booking.Utils;
import com.example.booking.databinding.ActivityLoginScreenBinding;
import com.example.booking.ui.Signup.SignupScreen_User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginScreenActivity extends AppCompatActivity {

    String get_type_of_login;

    ActivityLoginScreenBinding binding;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String user_Id_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        binding = ActivityLoginScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        get_type_of_login = Utils.get_SharedPreference_type_of_login(getApplicationContext());

        // Initialize
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
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
                finish();
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
                    binding.tvLoginErrorDisplay.setText(getString(R.string.enter_valid_email));
                    binding.etLoginEnterEmail.setError(getString(R.string.enter_valid_email));
                    return;
                }

                if(TextUtils.isEmpty(user_password_String)){
                    binding.tvLoginErrorDisplay.setText(getString(R.string.enter_valid_password));
                    binding.etLoginEnterPassword.setError(getString(R.string.enter_valid_password));
                    return;
                }

                // Now start progressbar
                binding.loginProgressbar.setVisibility(View.VISIBLE);

                binding.btnLogin.setEnabled(false);

                //Login User
                firebaseAuth.signInWithEmailAndPassword(user_email_String, user_password_String).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-==-=-=
                            // Initally set login-type as a user, and check in owner list if it's really owner than change type of login to owner
                            Utils.set_SharedPreference_type_of_login(Utils.user, getApplicationContext());
                            user_Id_string = firebaseAuth.getCurrentUser().getUid();

                            firebaseFirestore.collection(Utils.key_owner_firestore)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if((document.getId() != null) && (user_Id_string != null)){
                                                        if(document.getId().equals(user_Id_string)){
                                                            // OWNER ID FOUND, SET LOGIN TYPE == OWNER
                                                            Utils.set_SharedPreference_type_of_login(Utils.owner, getApplicationContext());
                                                            return;
                                                        }
                                                    }
                                                }
                                            } else {
                                                Log.i("test_response", "Error getting documents: ", task.getException());
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.i("test_response", "Error getting documents: "+e.getMessage());
                                        }
                                    });
                            //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-==-=-=


                            //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-==-=-=
                            // Check owner already added place or not
                            firebaseFirestore.collection(Utils.key_ownerplace_firestore)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if((document.getId() != null) && (user_Id_string != null)){
                                                        if(document.getId().equals(user_Id_string)){
                                                            // OWNER ID FOUND, SET LOGIN TYPE == OWNER
                                                            Utils.set_SharedPreference_owner_completed_add_placele_procedure("1", getApplicationContext());
                                                            return;
                                                        }else{
                                                            Utils.set_SharedPreference_owner_completed_add_placele_procedure("0", getApplicationContext());
                                                        }
                                                    }
                                                }
                                            } else {
                                                Log.i("test_response", "Error getting documents: ", task.getException());
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.i("test_response", "Error getting documents: "+e.getMessage());
                                        }
                                    });
                            //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-==-=-=

                            binding.loginProgressbar.setVisibility(View.VISIBLE);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    binding.loginProgressbar.setVisibility(View.GONE);
                                    // Redirect Successful Login
                                    //----------------------------------------------------------------------
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    //----------------------------------------------------------------------
                                    binding.btnLogin.setEnabled(true);
                                }
                            }, 3000);

                        }else{

                            // Progressbar gone
                            binding.loginProgressbar.setVisibility(View.GONE);
                            binding.btnLogin.setEnabled(true);
                            binding.tvLoginErrorDisplay.setText("Error : "+task.getException().getMessage());
                        }

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
                            tv_reset_passwrd_error.setText(getString(R.string.enter_valid_email));
                            et_reset_password_enter_email.setError(getString(R.string.enter_valid_email));
                            return;
                        }


                        firebaseAuth.sendPasswordResetEmail(reset_email_string).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                tv_reset_passwrd_error.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.combo_text_green));
                                tv_reset_passwrd_error.setText(getResources().getString(R.string.email_sent_success_message));
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



        // Show / Hide Password Image
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

             binding.ivLoginShowHideImage.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {

                     if(view.getId()==R.id.iv_login_show_hide_image){
                         if(binding.etLoginEnterPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                             ((ImageView)(view)).setImageResource(R.drawable.icon_hide_password_24);
                             //Show Password
                             binding.etLoginEnterPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                         }
                         else{
                             ((ImageView)(view)).setImageResource(R.drawable.icon_show_password_24);
                             //Hide Password
                             binding.etLoginEnterPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                         }
                     }

                 }
             });
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    }
}