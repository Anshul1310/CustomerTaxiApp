package com.ebooker.taxiappcustomer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    public static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Window window = this.getWindow();
        sharedPreferences = getSharedPreferences(getPackageName() + "", MODE_PRIVATE);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, android.R.color.white));
        new CountDownTimer(2500, 2500) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    if (sharedPreferences.getBoolean("isMobileVerified", false)) {
                        startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    } else {
                        Intent intent = new Intent(SplashScreen.this, OtpVerification.class);
                        intent.putExtra("mobileNumber", sharedPreferences.getString("mobileNumber", "+91839401639"));
                        startActivity(intent);
                    }
                } else {
                    startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                }
            }
        }.start();

    }
}