package com.example.finalloginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

public class MainActivity extends AppCompatActivity {

    CountryCodePicker ccp;
    EditText number;
    Button getOtp;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ccp = findViewById(R.id.ccp);
        number = findViewById(R.id.inputMobileNum);
        ccp.registerCarrierNumberEditText(number);
        getOtp = findViewById(R.id.getOtpBtn);

        auth=FirebaseAuth.getInstance();

        getOtp.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                    Intent intent = new Intent(MainActivity.this, OtpVerify.class);
                    intent.putExtra("mobileNumber", ccp.getFullNumberWithPlus().replace(" ", ""));
                    startActivity(intent);
            }
        });

        if(auth.getCurrentUser() !=null)
        {
            Intent intent =new Intent(MainActivity.this,dashBoard.class);
            startActivity(intent);
            finish();
        }
    }
}