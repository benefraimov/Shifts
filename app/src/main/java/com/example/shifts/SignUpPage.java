package com.example.shifts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpPage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText email, password, passwordAg, fullName, hourlyWage;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        email = findViewById(R.id.emailSignUp);
        password = findViewById(R.id.passwordSignUp);
        passwordAg = findViewById(R.id.passwordSignUpAgain);
        fullName = findViewById(R.id.fullName);
        hourlyWage = findViewById(R.id.userWage);

        //DataBase
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        //myRef = database.getReference("message");

        findViewById(R.id.btnSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().equals(passwordAg.getText().toString())) {
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(SignUpPage.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        User value = new User(fullName.getText().toString(), email.getText().toString(), Float.parseFloat(hourlyWage.getText().toString()));

                                        FirebaseUser user = mAuth.getCurrentUser();
                                        myRef = database.getReference("Users").child(user.getUid());
                                        myRef.setValue(value);
                                        updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        updateUI(null);
                                    }
                                }
                            });
                } else {
                    Toast.makeText(getApplicationContext(), "Password are not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateUI(FirebaseUser o) {
        if (o != null) {
            startActivity(new Intent(SignUpPage.this, WelcomePage.class));
        } else {
            Toast.makeText(SignUpPage.this, "Authentication failed.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
