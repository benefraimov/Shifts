package com.example.shifts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WelcomePage extends AppCompatActivity {

    TextView userName;
    SharedPreferences sharedPref;
    FirebaseAuth mAuth;
    //Reference for Users
    DatabaseReference myRefUsers = FirebaseDatabase.getInstance().getReference("Users");
    FirebaseUser user;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        mAuth = FirebaseAuth.getInstance();
        //userName = findViewById(R.id.testNameApear);
        //user = mAuth.getCurrentUser();
        listView = (ListView)findViewById(R.id.listView);

        myRefUsers = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getUid()).child("Shifts");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.each_item_row,
                R.id.item,arrayList);

        listView.setAdapter(arrayAdapter);

        // Read from the database
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    String value = ds.getValue(String.class);
                    arrayList.add(value);
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        myRefUsers.addListenerForSingleValueEvent(valueEventListener);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(WelcomePage.this)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                arrayList.remove(position);
                                myRefUsers.setValue(arrayList);
                                arrayAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
        //sharedPref = getSharedPreferences("shifts", Context.MODE_PRIVATE);
        //String name = sharedPref.getString("userName", "Not Found!");

        //String name = getIntent().getStringExtra("userName");
        //userName.setText("Hello, " + name);

        // Read from the database
//        myRefUsers.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                User value = dataSnapshot.child(user.getUid()).getValue(User.class);
//                userName.setText("hello, " + value.getFullName());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//            }
//        });

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
                startActivity(new Intent(WelcomePage.this,WelcomePage.class));
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
