package com.example.shifts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText userName, userPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = findViewById(R.id.userName);
        userPass = findViewById(R.id.userPass);

        (findViewById(R.id.btnLogin)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,WelcomePage.class);
                if(userName.getText().toString().equals("Ben") && userPass.getText().toString().equals("123")){
                    // Create intent in order to be able for transferring values
                    i.putExtra("userName",userName.getText().toString());
                    startActivity(i);
                    // Make a Toast
                    Toast.makeText(getApplicationContext(), "Login!!", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_LONG).show();
                }
            }
        });

        (findViewById(R.id.btnNewUser)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,SignUpPage.class);
                startActivity(i);
            }
        });

        (findViewById(R.id.userForgot)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,RestorePassPage.class);
                startActivity(i);
            }
        });
    }
}
