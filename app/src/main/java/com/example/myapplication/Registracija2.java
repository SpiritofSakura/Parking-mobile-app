package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registracija2 extends AppCompatActivity {


    public static String ime;
    public static String priimek;
    public static String naslov;
    public static String telefon;
    boolean canProceed;

    SharedPreferences sharedPreferences;

    TextView ime_text;
    TextView priimek_text;
    TextView naslov_text;
    TextView telefon_text;


    private Boolean aktiven = false;

    private void validateInput(TextView ime_text, TextView priimek_text, TextView naslov_text, TextView telefon_text) {
        canProceed = true;

        // Check the error status of each TextView
        if (TextUtils.isEmpty(ime_text.getText())) {
            canProceed = false;
        }

        if (TextUtils.isEmpty(priimek_text.getText())) {
            canProceed = false;
        }

        if (TextUtils.isEmpty(naslov_text.getText())) {
            canProceed = false;
        }

        String phoneNumber = telefon_text.getText().toString();
        // Define a regular expression pattern for a valid phone number format
        String regexPattern = "^\\+(\\d{1,3})[-\\s]?(\\d{1,3})[-\\s]?(\\d{4,})$";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(phoneNumber);
        if (TextUtils.isEmpty(phoneNumber)) {
            canProceed = false;
        } else if (!matcher.matches()) {
            canProceed = false;
        }

        if(canProceed) {
            // Hide the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(telefon_text.getWindowToken(), 0);
            // Start the next activity
            saveData();
            Intent intent = new Intent(Registracija2.this, Registracija3.class);
            startActivity(intent);
        }
    }


    private void saveData(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ime", ime_text.getText().toString());
        editor.putString("priimek", priimek_text.getText().toString());
        editor.putString("naslov", naslov_text.getText().toString());
        editor.putString("telefon", telefon_text.getText().toString());
        editor.apply();
    }

    private void loadData() {
        String ime = sharedPreferences.getString("ime", "");
        String priimek = sharedPreferences.getString("priimek", "");
        String naslov = sharedPreferences.getString("naslov", "");
        String telefon = sharedPreferences.getString("telefon", "");
        ime_text.setText(ime);
        priimek_text.setText(priimek);
        naslov_text.setText(naslov);
        telefon_text.setText(telefon);
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
        setContentView(R.layout.register2);
        sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);



         ime_text = findViewById(R.id.ime);
         priimek_text = findViewById(R.id.priimek);
         naslov_text = findViewById(R.id.naslov);
         telefon_text = findViewById(R.id.telefon);
        Button nazaj = findViewById(R.id.btn_nazaj);
        Button naprej1 = findViewById(R.id.btn_naprej1);
        TextView domov2 = findViewById(R.id.domov2);

        Drawable error = getResources().getDrawable(R.drawable.ic_outline_cancel_24);
        Drawable success = ContextCompat.getDrawable(this, R.drawable.ic_outline_check_circle_24);
        success.setBounds(0, 0, success.getIntrinsicWidth(), success.getIntrinsicHeight());
        error.setBounds(0, 0, error.getIntrinsicWidth(), error.getIntrinsicHeight());

        loadData();
        naprej1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ime = ime_text.getText().toString();
                priimek = priimek_text.getText().toString();
                naslov = naslov_text.getText().toString();
                telefon = telefon_text.getText().toString();

                validateInput(ime_text, priimek_text, naslov_text, telefon_text);
            }
        });

        ime_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    ime_text.setError("Vnesite ime!", error);
                    //username_text.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, error, null);
                } else {
                    ime_text.setError(null);
                    ime_text.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, success, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        priimek_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    priimek_text.setError("Vnesite priimek!", error);
                    //email_text.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, error, null);
                } else {
                    priimek_text.setError(null);
                    priimek_text.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, success, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        naslov_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    naslov_text.setError("Vnesite vaš naslov!", error);
                    //password_text.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, error, null);
                } else {
                    naslov_text.setError(null);
                    naslov_text.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, success, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        telefon_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String phoneNumber = s.toString();
                // Define a regular expression pattern for a valid phone number format
                String regexPattern = "^\\+(\\d{1,3})[-\\s]?(\\d{1,3})[-\\s]?(\\d{4,})$";
                Pattern pattern = Pattern.compile(regexPattern);
                Matcher matcher = pattern.matcher(phoneNumber);
                if (TextUtils.isEmpty(s)) {
                    telefon_text.setError("Vnesite telefonsko številko!", error);
                } else if (matcher.matches()) {

                    telefon_text.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, success, null);
                } else {
                    telefon_text.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, success, null);
                    telefon_text.setError("Napačen format telefonske številke! Začni z +!", error);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


        nazaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registracija2.this, Registracija.class);
                startActivity(intent);
            }
        });


        domov2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(Registracija2.this, MainActivity.class);
                startActivity(intent);
            }
        });


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ime_text.clearFocus();
                priimek_text.clearFocus();
                naslov_text.clearFocus();
                telefon_text.clearFocus();



            }
        };

        View rootView = findViewById(android.R.id.content);
        rootView.setOnClickListener(onClickListener);

        ime_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

        priimek_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

        naslov_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

        telefon_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
