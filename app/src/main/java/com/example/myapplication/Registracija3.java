package com.example.myapplication;


import static com.example.myapplication.MainActivity.token;
import static com.google.firebase.messaging.Constants.MessageNotificationKeys.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import TessBaseAPI
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.googlecode.tesseract.android.TessBaseAPI;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registracija3 extends AppCompatActivity {


    public static String registrska_stevilka;
    public static String vozilo_id;
    public static String drzava;
    public static String model;

    Boolean canProceed;

    SharedPreferences sharedPreferences;

    TextView registrska_stevilka_text;
    TextView vozilo_id_text;
    TextView drzava_text;
    TextView model_text;






    private boolean validateInput(TextView registrska_stevilka_text, TextView vozilo_id_text, TextView drzava_text, TextView model_text) {
        canProceed = true;

        // Check the error status of each TextView
        if (TextUtils.isEmpty(registrska_stevilka_text.getText())) {
            canProceed = false;
        }

        if (TextUtils.isEmpty(vozilo_id_text.getText())) {
            canProceed = false;
        }

        if (TextUtils.isEmpty(drzava_text.getText())) {
            canProceed = false;
        }

        if (TextUtils.isEmpty(model_text.getText())) {
            canProceed = false;
        }

        return canProceed;
    }


    private void saveData(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("registrska", registrska_stevilka_text.getText().toString());
        editor.putString("vozilo_id", vozilo_id_text.getText().toString());
        editor.putString("drzava", drzava_text.getText().toString());
        editor.putString("model", model_text.getText().toString());
        editor.apply();


    }

    private void loadData() {
        String registrska = sharedPreferences.getString("registrska", "");
        String vozilo_id = sharedPreferences.getString("vozilo_id", "");
        String drzava = sharedPreferences.getString("drzava", "");
        String model = sharedPreferences.getString("model", "");
        registrska_stevilka_text.setText(registrska);
        vozilo_id_text.setText(vozilo_id);
        drzava_text.setText(drzava);
        model_text.setText(model);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register3);
        sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);

         registrska_stevilka_text = findViewById(R.id.license_plate);
         vozilo_id_text = findViewById(R.id.car_id);
         drzava_text = findViewById(R.id.drzava);
         model_text = findViewById(R.id.model);


        Button nazaj2 = findViewById(R.id.btn_nazaj2);
        Button koncano = findViewById(R.id.btn_koncano);




        TextView domov3 = findViewById(R.id.domov3);


        Drawable error = getResources().getDrawable(R.drawable.ic_outline_cancel_24);
        Drawable success = ContextCompat.getDrawable(this, R.drawable.ic_outline_check_circle_24);
        success.setBounds(0, 0, success.getIntrinsicWidth(), success.getIntrinsicHeight());
        error.setBounds(0, 0, error.getIntrinsicWidth(), error.getIntrinsicHeight());
        loadData();
        koncano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrska_stevilka = registrska_stevilka_text.getText().toString();
                vozilo_id = vozilo_id_text.getText().toString();
                drzava = drzava_text.getText().toString();
                model = model_text.getText().toString();





                if (validateInput(registrska_stevilka_text, vozilo_id_text, drzava_text, model_text)) {

                    saveData();
                    Registracija.uporabnik = new Uporabnik(Registracija.username,Registracija.email,Registracija.pass,Registracija2.ime,Registracija2.priimek,Registracija2.naslov,Registracija2.telefon,Registracija3.registrska_stevilka,Registracija3.vozilo_id,Registracija3.drzava,Registracija3.model);


                    if(Baza.Registracija(Registracija.uporabnik) == true){




                        System.out.println("Registracija uspešna");
                        // Create an AlertDialog builder
                        AlertDialog.Builder builder = new AlertDialog.Builder(Registracija3.this);

                        // Set the message and title of the dialog
                        builder.setMessage("SUCCESS")
                                .setTitle("Registracija uspešna!");

                        // Add a button to the dialog
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.apply();
                                // User clicked OK button
                                Intent intent = new Intent(Registracija3.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });

                        // Create and show the AlertDialog
                        AlertDialog dialog = builder.create();
                        dialog.show();





                    }else{
                        System.out.println("Registracija neuspešna");
                        AlertDialog.Builder builder = new AlertDialog.Builder(Registracija3.this);

                        // Set the message and title of the dialog
                        builder.setMessage("ERROR")
                                .setTitle("NAPAKA");

                        // Add a button to the dialog
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                                Intent intent = new Intent(Registracija3.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });

                        // Create and show the AlertDialog
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }


                }


            }
        });

        registrska_stevilka_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    registrska_stevilka_text.setError("Vnesite registrsko tablico!", error);
                    //username_text.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, error, null);
                } else {
                    registrska_stevilka_text.setError(null);
                    registrska_stevilka_text.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, success, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        vozilo_id_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    vozilo_id_text.setError("Vnesite ID od vozila!", error);
                    //email_text.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, error, null);
                } else {
                    vozilo_id_text.setError(null);
                    vozilo_id_text.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, success, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        drzava_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    drzava_text.setError("Vnesite drzavo!", error);
                    //password_text.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, error, null);
                } else {
                    drzava_text.setError(null);
                    drzava_text.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, success, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        model_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    model_text.setError("Vnesite model avtomobila!", error);
                    //password1_text.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, error, null);
                } else {
                    model_text.setError(null);
                    model_text.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, success, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });



        nazaj2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registracija3.this, Registracija2.class);
                startActivity(intent);
            }
        });



        domov3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(Registracija3.this, MainActivity.class);
                startActivity(intent);
            }
        });

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registrska_stevilka_text.clearFocus();
                drzava_text.clearFocus();
                model_text.clearFocus();
                vozilo_id_text.clearFocus();



            }
        };

        View rootView = findViewById(android.R.id.content);
        rootView.setOnClickListener(onClickListener);

        registrska_stevilka_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

        drzava_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

        model_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

        vozilo_id_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });












    }
//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            TessBaseAPI baseApi = new TessBaseAPI();
//            baseApi.init(DATA_PATH, "eng"); // Initialize Tesseract with the English language
//            baseApi.setImage(imageBitmap); // Set the image to recognize text from
//            String recognizedText = baseApi.getUTF8Text(); // Get the recognized text as a string
//            baseApi.end(); // End the recognition session
//        }
//    }



}
