package com.ebooker.taxiappcustomer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.rilixtech.widget.countrycodepicker.Country;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText email, password, name, number;
    private ProgressDialog progressDialog;
    private CountryCodePicker ccp;
    private String countrycode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Window window = this.getWindow();
        number=findViewById(R.id.register_mobile_number);
        email = findViewById(R.id.register_email_address);
        name = findViewById(R.id.register_full_name);
        password = findViewById(R.id.register_password);
        countrycode = "91";
        ccp = findViewById(R.id.register_ccp);
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                countrycode = selectedCountry.getPhoneCode();
            }
        });
        ccp.registerPhoneNumberTextView(number);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait..");
        progressDialog.setMessage("We are adding you to our community.");
        progressDialog.setCanceledOnTouchOutside(false);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, android.R.color.white));
    }

    public void register_login_screen(View view) {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }

    public void register_btn(View view) {
        if (email.getText().toString().isEmpty() || number.getText().toString().isEmpty() || password.getText().toString().isEmpty() || name.getText().toString().isEmpty()) {
            Toast.makeText(this, "Blank field can't be processed.", Toast.LENGTH_SHORT).show();
        } else if(!ccp.isValid()) {
            Toast.makeText(this, "Invalid Mobile Number.", Toast.LENGTH_SHORT).show();
        }
        else
         {
            progressDialog.show();
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            HashMap<String, String> hashMap = new HashMap<>();
                            SplashScreen.sharedPreferences.edit().putString("name",name.getText().toString()).apply();
                            SplashScreen.sharedPreferences.edit().putString("email",email.getText().toString()).apply();
                            hashMap.put("name", name.getText().toString());
                            SplashScreen.sharedPreferences.edit().putString("mobileNumber","+" + countrycode + "" + number.getText().toString().replace(" ","")).apply();
                            SplashScreen.sharedPreferences.edit().putBoolean("isMobileVerified",false).apply();
                            hashMap.put("email", email.getText().toString());
                            hashMap.put("mobileNumber",countrycode + "" + number.getText().toString().replace(" ",""));
                            hashMap.put("password", password.getText().toString());
                            FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressDialog.cancel();
                                    Intent intent=new Intent(RegisterActivity.this, OtpVerification.class);
                                    startActivity(intent);
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.cancel();
                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}