package com.example.shifts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText email, pass;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            startActivity(new Intent(MainActivity.this,WelcomePage.class));
        }
        else{
            //do nothing
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //INITIALIZE FIREBASE AUTH..
        mAuth = FirebaseAuth.getInstance();

        sharedPref = getSharedPreferences("shifts",Context.MODE_PRIVATE);
        editor = sharedPref.edit();


        email = findViewById(R.id.userName);
        pass = findViewById(R.id.userPass);

        (findViewById(R.id.btnLogin)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        updateUI(null);
                                    }
                                }
                            });
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"email or password are not defined",Toast.LENGTH_SHORT).show();
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

    private void updateUI(FirebaseUser o) {
        if(o!=null){
            startActivity(new Intent(MainActivity.this,WelcomePage.class));
        }
        else{
            Toast.makeText(MainActivity.this, "Authentication failed.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
