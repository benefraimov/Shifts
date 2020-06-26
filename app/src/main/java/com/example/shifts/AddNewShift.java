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
    Calendar date1 = Calendar.getInstance();
    Calendar date2 = Calendar.getInstance();

    Button addShift;
    FirebaseAuth mAuth;
    DatabaseReference myRef;
    String shift;
    int t1Hour, t1Minute, t2Hour, t2Minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_shift);

//        findViewById(R.id.btnViewShift).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(InsertShifts.this,ViewShifts.class));
//            }
//        });


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
                date1.clear();
                date1.set(year, Month, dayOfMonth);
            }
        };

        mDataSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int Month = month + 1;
                Log.d(TAG, "onDataSet: date: " + year + "/" + Month + "/" + dayOfMonth);
                mDisplayDate2.setText(Month+"/"+dayOfMonth+"/"+year);+
                date2.clear();
                date2.set(year, Month, dayOfMonth);
            }
        };



        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getUid()).child("Shifts").child(mDisplayDate1.getText().toString());

        addShift = findViewById(R.id.btnConfirm);

        addShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
                DateFormat df = DateFormat.getInstance();

                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = simpleDateFormat.parse(mDisplayTime1.getText().toString());
                    date2 = simpleDateFormat.parse(mDisplayTime2.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long diff = date2.getTimeInMillis() - date1.getTimeInMillis();
                Log.i("diff: ", " ::" + String.valueOf(diff));
                float dayCount = (float)diff/(24*60*60*1000);
                Log.i("======Days Counter", " ::"+ String.valueOf(dayCount));
                long difference = date2.getTime() - date1.getTime();
                int days = (int) (difference / (1000*60*60*24));
                int hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
                int min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
                hours = (hours < 0 ? -hours : hours);
                String totalTime = String.valueOf(hours)+":"+String.valueOf(min);
                SimpleDateFormat f24Hours = new SimpleDateFormat(
                        "HH:mm");
                String diffHours;
                try {
                    Date date = f24Hours.parse(totalTime);
                    //Set selected time on text view
                    diffHours = f24Hours.format(date);
                    Log.i("======= Hours"," :: "+diffHours);
                    shift = "    " + mDisplayDate1.getText().toString() + "            " +
                            mDisplayTime1.getText().toString() + "              " +
                            mDisplayTime2.getText().toString() + "                " +
                            diffHours;
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                 //       startTime.getText().toString() + "                       1          ";
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

    //Counting how many days
    public String getCountOfDays(String createdDateString, String expireDateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        Date createdConvertedDate = null, expireCovertedDate = null, todayWithZeroTime = null;
        try {
            createdConvertedDate = dateFormat.parse(createdDateString);
            expireCovertedDate = dateFormat.parse(expireDateString);

            Date today = new Date();

            todayWithZeroTime = dateFormat.parse(dateFormat.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int cYear = 0, cMonth = 0, cDay = 0;

        if (createdConvertedDate.after(todayWithZeroTime)) {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(createdConvertedDate);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);

        } else {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(todayWithZeroTime);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);
        }


        /*Calendar todayCal = Calendar.getInstance();
        int todayYear = todayCal.get(Calendar.YEAR);
        int today = todayCal.get(Calendar.MONTH);
        int todayDay = todayCal.get(Calendar.DAY_OF_MONTH);
        */

        Calendar eCal = Calendar.getInstance();
        eCal.setTime(expireCovertedDate);

        int eYear = eCal.get(Calendar.YEAR);
        int eMonth = eCal.get(Calendar.MONTH);
        int eDay = eCal.get(Calendar.DAY_OF_MONTH);

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.clear();
        date1.set(cYear, cMonth, cDay);
        date2.clear();
        date2.set(eYear, eMonth, eDay);

        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();

        float dayCount = (float) diff / (24 * 60 * 60 * 1000);

        return ("" + (int) dayCount + " Days");
    }
}
