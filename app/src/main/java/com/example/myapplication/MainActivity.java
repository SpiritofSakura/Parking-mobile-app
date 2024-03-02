package com.example.myapplication;


import static com.example.myapplication.MyFirebaseMessagingService.ODHOD_KEY;
import static com.example.myapplication.MyFirebaseMessagingService.PARKIRISCE_KEY;
import static com.example.myapplication.MyFirebaseMessagingService.PREFS_NAME;
import static com.example.myapplication.MyFirebaseMessagingService.PRIHOD_KEY;
import static com.google.firebase.messaging.Constants.MessageNotificationKeys.TAG;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String username;
    String password;

    public static String token;






    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences(MyFirebaseMessagingService.PREFS_NAME, MODE_PRIVATE);
        boolean wasInBackground = prefs.getBoolean("wasInBackground", false);

//        Domov.vozilo = Baza.OsveziDB();
        if (wasInBackground) {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.cancel(0);

            // Remove the flag from SharedPreferences
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove("wasInBackground");
            editor.apply();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Domov.vozilo = Baza.OsveziDB();
        // Get the data from the Intent
//        Intent intent = getIntent();
//        String parkirisce = intent.getStringExtra(MyFirebaseMessagingService.PARKIRISCE_KEY);
//        String prihod = intent.getStringExtra(MyFirebaseMessagingService.PRIHOD_KEY);
//        String odhod = intent.getStringExtra(MyFirebaseMessagingService.ODHOD_KEY);
//
//        System.out.println(prihod);



//        checkNotificationDataAndStore();




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default_channel_id", "Default Channel", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Default Notification Channel");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

                FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        token = task.getResult();

                        // Log and toast

                        Log.d(TAG, token);
                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                        System.out.println(token);
                    }
                });










        TextView username_text = findViewById(R.id.username);
        TextView password_text = findViewById(R.id.password);
        TextView register = findViewById(R.id.registracija);

        Drawable success = ContextCompat.getDrawable(this, R.drawable.ic_outline_check_circle_24);

        Button login = findViewById(R.id.btn_prijava);
        Drawable error = getResources().getDrawable(R.drawable.ic_outline_cancel_24);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = username_text.getText().toString();
                password = password_text.getText().toString();

                Baza.Prijava(username, password,username_text,password_text,error,success);












            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //When a user clicks on the register button, the user will be redirected to the new page with the registration form called register1.xml
                Intent intent = new Intent(MainActivity.this, Registracija.class);
                startActivity(intent);


            }





    });



        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username_text.clearFocus();
                password_text.clearFocus();



            }
        };

        View rootView = findViewById(android.R.id.content);
        rootView.setOnClickListener(onClickListener);

        username_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

        password_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });



    }




}