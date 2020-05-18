package com.example.shifts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomePage extends AppCompatActivity {

    TextView userName;
    SharedPreferences sharedPref;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        mAuth = FirebaseAuth.getInstance();
        //userName = findViewById(R.id.userNameWelcomePage);

        //sharedPref = getSharedPreferences("shifts", Context.MODE_PRIVATE);
        //String name = sharedPref.getString("userName", "Not Found!");

        //String name = getIntent().getStringExtra("userName");
        //userName.setText("Hello, " + name);

        (findViewById(R.id.btnMyWage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WelcomePage.this,MySalary.class);
                startActivity(i);
            }
        });

        findViewById(R.id.btnLogOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(WelcomePage.this, MainActivity.class));
            }
        });

        (findViewById(R.id.btnRefresh)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WelcomePage.this,WelcomePage.class);
                startActivity(i);
            }
        });

        (findViewById(R.id.btnAddShip)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WelcomePage.this,AddNewShift.class);
                startActivity(i);
            }
        });
    }

}
