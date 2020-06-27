package com.example.shifts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;

public class AddNewShift extends AppCompatActivity {

    private static final String TAG = "AddNewShift";

    private ImageButton mDataPicker1, mDataPicker2;
    private ImageButton mTimePicker1, mTimePicker2;
    private TextView mDisplayTime1, mDisplayTime2;
    private TextView mDisplayDate1, mDisplayDate2;
    private DatePickerDialog.OnDateSetListener mDataSetListener1, mDataSetListener2;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener1, mTimeSetListener2;
    Calendar dateCal1 = Calendar.getInstance();
    Calendar dateCal2 = Calendar.getInstance();

    Button addShift;
    private FirebaseAuth mAuth;
    DatabaseReference myRef;
    String shift;
    int t1Hour, t1Minute, t2Hour, t2Minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_shift);

        //Time & Date Variable Assignment
        mTimePicker1 = (ImageButton)findViewById(R.id.timePicker1);
        mTimePicker2 = (ImageButton)findViewById(R.id.timePicker2);
        mDisplayTime1 = (TextView)findViewById(R.id.tvTime1);
        mDisplayTime2 = (TextView)findViewById(R.id.tvTime2);
        mDataPicker1 = (ImageButton) findViewById(R.id.datePicker1);
        mDataPicker2 = (ImageButton) findViewById(R.id.datePicker2);
        mDisplayDate1 = (TextView) findViewById(R.id.tvDate1);
        mDisplayDate2 = (TextView) findViewById(R.id.tvDate2);

        mTimePicker1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AddNewShift.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                //Initialize hour and minute
                                t1Hour = hourOfDay;
                                t1Minute = minute;
                                //Store hour and minute in string
                                String time = t1Hour + ":" + t1Minute;
                                //Initialize 24 hours time format
                                SimpleDateFormat f24Hours = new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date = f24Hours.parse(time);
                                    //Initialize 12 hours time format
                                    SimpleDateFormat f12Hourse = new SimpleDateFormat(
                                            "hh:mm aa"
                                    );
                                    //Set selected time on text view
                                    mDisplayTime1.setText(f12Hourse.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        },12,0,false
                );
                //Set transparent background
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //Displayed previous selected time
                timePickerDialog.updateTime(t1Hour, t1Minute);
                //Show dialog
                timePickerDialog.show();
            }
        });

        mTimePicker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AddNewShift.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                //Initialize hour and minute
                                t2Hour = hourOfDay;
                                t2Minute = minute;
                                //Store hour and minute in string
                                String time = t2Hour + ":" + t2Minute;
                                //Initialize 24 hours time format
                                SimpleDateFormat f24Hours = new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date = f24Hours.parse(time);
                                    //Initialize 12 hours time format
                                    SimpleDateFormat f12Hourse = new SimpleDateFormat(
                                            "hh:mm aa"
                                    );
                                    //Set selected time on text view
                                    mDisplayTime2.setText(f12Hourse.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        },12,0,false
                );
                //Set transparent background
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //Displayed previous selected time
                timePickerDialog.updateTime(t2Hour, t2Minute);
                //Show dialog
                timePickerDialog.show();
            }
        });

        mDataPicker1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddNewShift.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDataSetListener1,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDataPicker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddNewShift.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDataSetListener2,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDataSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int Month = month + 1;
                Log.d(TAG, "onDataSet: date: " + year + "/" + Month + "/" + dayOfMonth);
                mDisplayDate1.setText(Month+"/"+dayOfMonth+"/"+year);
                dateCal1.clear();
                dateCal1.set(year, Month, dayOfMonth);
            }
        };

        mDataSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int Month = month + 1;
                Log.d(TAG, "onDataSet: date: " + year + "/" + Month + "/" + dayOfMonth);
                mDisplayDate2.setText(Month+"/"+dayOfMonth+"/"+year);
                dateCal2.clear();
                dateCal2.set(year, Month, dayOfMonth);
            }
        };


        mAuth = FirebaseAuth.getInstance();

        addShift = findViewById(R.id.btnConfirm);

        addShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Time1 = mDisplayDate1.getText().toString() + " " + mDisplayTime1.getText().toString();
                String Time2 = mDisplayDate2.getText().toString() + " " + mDisplayTime2.getText().toString();
                Log.i("======= Time1"," :: "+Time1);
                Log.i("======= Time2"," :: "+Time2);

                // date format
                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy hh:mm aa");

                Date d1 = null;
                Date d2 = null;
                try {
                    d1 = format.parse(Time1);
                    d2 = format.parse(Time2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long diff = d2.getTime() - d1.getTime();
                long diffSeconds = diff / 1000;
                long diffMinutes = (diff / (60 * 1000))%60;
                long diffHours = diff / (60 * 60 * 1000);

                String totalTime = String.valueOf(diffHours < 10 ? "0"+diffHours : diffHours) +
                        ":" +
                        String.valueOf(diffMinutes < 10 ? "0"+diffMinutes: diffMinutes);

//changing format of hour displaying, not capable when have more than 24 hr!
//                try {
//                    //pattern for Hours Display:
//                    SimpleDateFormat f24Hours = new SimpleDateFormat(
//                            "HH:mm");
//                    String diffHours1;
//                    Date date = f24Hours.parse(totalTime);
//                    //Set selected time on text view.
//                    diffHours1 = f24Hours.format(date);
//                    Log.i("======= Hours"," :: "+diffHours1);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }

                shift = "    " + mDisplayDate1.getText().toString() + "            " +
                        mDisplayTime1.getText().toString() + "              " +
                        mDisplayTime2.getText().toString() + "                " +
                        totalTime;


                DateFormat df = new SimpleDateFormat("MMM yyyy");
                String date = df.format(Calendar.getInstance().getTime());
                myRef = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getUid()).child(date);

                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<String> list = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String eachShift = ds.getValue(String.class);
                            list.add(eachShift);
                        }
                        list.add(shift);
                        myRef.setValue(list);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };
                myRef.addListenerForSingleValueEvent(valueEventListener);
                startActivity(new Intent(AddNewShift.this,WelcomePage.class));

            }
        });

        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddNewShift.this,WelcomePage.class));
            }
        });

    }
}
