package com.example.shifts;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class MySalary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_salary);

        (findViewById(R.id.btnClose)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MySalary.this, WelcomePage.class);
                startActivity(i);
            }
        });

        (findViewById(R.id.btnSendSalaryViaMail)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail(v);
               // sendEmail();
            }
        });
    }
/*
    public void sendEmail() {
        //Getting content for email
        String email = "ben.a254@gmail.com";
        String subject = "testberichtje voor lorenzo";
        String message = "testberichtje voor lorenzo";

        //Creating SendMail object
        SendMail sm = new SendMail(this, email, subject, message);

        //Executing sendmail to send email
        sm.execute();
    }*/

    public void sendMail(View view) {
        new MailCreator().execute("");
    }

    public class MailCreator extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {

            try {

                MailSender sender = new MailSender(getBaseContext(), AppsConstants.SENDER_EMAIL, AppsConstants.SENDER_PASSWORD);
                sender.sendUserDetailWithImage("New User Added", "Hi", "Himanshu",
                        AppsConstants.RECIPEINT_MAIL, "Himanshu Verma", "xyz@gmail.com", "+91 6075959010", "02/02/1994", "25", "New Delhi, India", "");

            } catch (Exception e) {
                Log.e("SendMail", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            Toast.makeText(getActivity(),"Mail Sent",Toast.LENGTH_LONG).show();
        }
    }
}



