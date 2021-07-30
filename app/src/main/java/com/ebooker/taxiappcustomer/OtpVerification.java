package com.ebooker.taxiappcustomer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class OtpVerification extends AppCompatActivity {

    private EditText otpbox1, otpbox2, otpbox3, otpbox4, otpbox5, otpbox6;
    private String phonenumber;
    private TextView resend, notification_txt;
    boolean canbesend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        Window window = this.getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        otpbox1 = findViewById(R.id.optbox_one);
        otpbox1.requestFocus();
        otpbox6 = findViewById(R.id.optbox_six);
        otpbox2 = findViewById(R.id.optbox_two);
        otpbox3 = findViewById(R.id.optbox_three);
        otpbox4 = findViewById(R.id.optbox_four);
        notification_txt=findViewById(R.id.notify_otp_verification_txt);
        otpbox5 = findViewById(R.id.optbox_five);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue_theme));
        resend = findViewById(R.id.resend_otp_verification);
        phonenumber = SplashScreen.sharedPreferences.getString("mobileNumber", "+918394013339");
        notification_txt.setText("Enter the OTP you received on the mobile number "+phonenumber);
        try {
            resend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (canbesend) {
                        initiateotp();
                        View parentLayout = findViewById(android.R.id.content);
                        Snackbar snackbar = Snackbar.make(parentLayout, "Generating OTP", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } else {
                        Toast.makeText(OtpVerification.this, "OTP Sent Successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            new CountDownTimer(30000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    int remainings = (int) (millisUntilFinished / 1000);
                    if (remainings < 10) {
                        String s = "Resend 0:0" + remainings;
                        resend.setText(s);
                    } else {
                        String s = "Resend 0:" + remainings;
                        resend.setText(s);
                    }

                }

                @Override
                public void onFinish() {
                    canbesend = true;
                    String s = "Resend SMS";
                    resend.setText(s);
                }
            }.start();

            initiateotp();
        } catch (Exception exception) {
            Log.d("anshul", exception.getMessage());
        }

    }

    public void nextbtn_otp_verfication(View view) {
        if (otpbox1.getText().toString().isEmpty() ||
                otpbox2.getText().toString().isEmpty() ||
                otpbox3.getText().toString().isEmpty() ||
                otpbox4.getText().toString().isEmpty() ||
                otpbox6.getText().toString().isEmpty() ||
                otpbox5.getText().toString().isEmpty()) {

            Toast.makeText(OtpVerification.this, "Blank Field Cannot Be processed", Toast.LENGTH_SHORT).show();
        } else {
            View parentLayout = findViewById(android.R.id.content);
            Snackbar snackbar = Snackbar.make(parentLayout, "Number Verified Successfully", Snackbar.LENGTH_SHORT);
            snackbar.show();
            SplashScreen.sharedPreferences.edit().putBoolean("isMobileVerified",true).apply();
            startActivity(new Intent(OtpVerification.this, MainActivity.class));
        }
    }


    private void initiateotp() {
        try {
            PhoneAuthOptions options =
                    PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                            .setPhoneNumber(phonenumber)       // Phone number to verify
                            .setTimeout(40L, TimeUnit.SECONDS) // Timeout and unit
                            .setActivity(OtpVerification.this)                 // Activity (for callback binding)
                            .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                                otpbox.setText(phoneAuthCredential.getSmsCode());
                                    try {
                                        otpbox1.setText(phoneAuthCredential.getSmsCode().charAt(0) + "");
                                        otpbox2.setText(phoneAuthCredential.getSmsCode().charAt(1) + "");
                                        otpbox3.setText(phoneAuthCredential.getSmsCode().charAt(2) + "");
                                        otpbox4.setText(phoneAuthCredential.getSmsCode().charAt(3) + "");
                                        otpbox6.setText(phoneAuthCredential.getSmsCode().charAt(5) + "");
                                        otpbox5.setText(phoneAuthCredential.getSmsCode().charAt(4) + "");
                                        startActivity(new Intent(OtpVerification.this, MainActivity.class));
                                    } catch (Exception exception) {
                                        Log.d("anshul", exception.getMessage());
                                    }

                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    Toast.makeText(OtpVerification.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    super.onCodeSent(s, forceResendingToken);
                                    View parentLayout = findViewById(android.R.id.content);
                                    Snackbar snackbar = Snackbar.make(parentLayout, "Code Sent Successfully", Snackbar.LENGTH_SHORT);
                                    snackbar.show();
                                }
                            })          // OnVerificationStateChangedCallbacks
                            .build();
            PhoneAuthProvider.verifyPhoneNumber(options);
        } catch (Exception e) {
            Log.d("anshul", e.getMessage());
        }

    }
}