package com.example.shifts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class WelcomePage extends AppCompatActivity {

    RelativeLayout r1, r2, r3;

    //Data Base------------------
    //Reference for Users
    DatabaseReference myRefUsers = FirebaseDatabase.getInstance().getReference("Users");
    FirebaseUser user;
    FirebaseAuth mAuth;
    //--------------------------

    //List view to catch the real time database from firebase!!
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    //-------------------------------------------------------!!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        //Connection to the fire base!!!
        mAuth = FirebaseAuth.getInstance();
        //userName = findViewById(R.id.testNameApear);
        //user = mAuth.getCurrentUser();
        listView = (ListView) findViewById(R.id.lisView);

        //designing a data format for the date
        DateFormat df = new SimpleDateFormat("MMM yyyy");
        String date = df.format(Calendar.getInstance().getTime());

        //creating a new Users child child by the current date and adding it to the real time database
        myRefUsers = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getUid()).child(date);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.each_item_row,
                R.id.item, arrayList);

        //inserting into the list view the arrayAdapter wich contain the prder of the way the data enters
        listView.setAdapter(arrayAdapter);

        // Read from the database
        final ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String value = ds.getValue(String.class);
                    arrayList.add(value);
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        //never forget to add the value listener into the listener
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

        //Connecting the Relative Layout to the buttons


        (findViewById(R.id.btnAddShip)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r1 = findViewById(R.id.fragment_AddShift);
                r1.setVisibility(View.VISIBLE);
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                ft.add(R.id.fragment_AddShift, new AddShiftWithFragment());
                ft.commit();
            }
        });

//        findViewById(R.id.btnOpenMenu).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                r2 = findViewById(R.id.fragment_Menu);
////                r2.setVisibility(View.VISIBLE);
////                FragmentManager fm = getSupportFragmentManager();
////                FragmentTransaction ft = fm.beginTransaction();
////                ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
////                ft.add(R.id.fragment_Menu, new MenuWithFragment());
////                ft.commit();
//            }
//        });

        (findViewById(R.id.btnMyWage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r3 = findViewById(R.id.fragment_MyWage);
                r3.setVisibility(View.VISIBLE);
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                ft.add(R.id.fragment_MyWage, new MyWageWithFragment());
                ft.commit();
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
                startActivity(new Intent(WelcomePage.this, WelcomePage.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        int id = item.getItemId();

        if(id==R.id.action_profile){
            Toast.makeText(this,"You selected profile", Toast.LENGTH_SHORT).show();
        }
        else if(id==R.id.nav_share){
            Toast.makeText(this,"You selected share", Toast.LENGTH_SHORT).show();
        }
        else if(id==R.id.nav_send){
            Toast.makeText(this,"You selected send", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

}
