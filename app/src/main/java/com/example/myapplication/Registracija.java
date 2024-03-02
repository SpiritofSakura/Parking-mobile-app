package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;


//Add a code that will show the register1.xml when the user click the register button in the previous activity called MainActivity.java of activity_main.xml
public class Registracija extends AppCompatActivity {


    public static String username;
    public static String email;
    public static String pass;
    private boolean isFormValid = false;
    private boolean canProceed = false;
    public static Uporabnik uporabnik;

    SharedPreferences sharedPreferences;

    TextView username_text;
    TextView email_text;
    TextView password_text;
    TextView password1_text;


    private void validateInput(TextView username_text, TextView email_text, TextView password_text,TextView password1) {
        canProceed = true;

        // Check the error status of each TextView
        if (TextUtils.isEmpty(username_text.getText())) {
            canProceed = false;
        }

        if (TextUtils.isEmpty(email_text.getText())) {
            canProceed = false;
        } else {
            Pattern pattern = Patterns.EMAIL_ADDRESS;
            boolean isEmailValid = pattern.matcher(email_text.getText().toString()).matches();
            if(!isEmailValid) {
                canProceed = false;
            }
        }

        if (TextUtils.isEmpty(password_text.getText())) {
            canProceed = false;
        } else {
            boolean isEightCharsLong = password_text.getText().length() >= 8;
            boolean containsUpperCase = !password_text.getText().toString().equals(password_text.getText().toString().toLowerCase());
            boolean containsLowerCase = !password_text.getText().toString().equals(password_text.getText().toString().toUpperCase());
            boolean containsDigit = password_text.getText().toString().matches(".*\\d.*");
            boolean containsSpecialChar = password_text.getText().toString().matches(".*[@#$%^&+=.].*");
            boolean containsWhiteSpace = password_text.getText().toString().contains(" ");

            if (isEightCharsLong && containsUpperCase && containsLowerCase && containsDigit && containsSpecialChar && !containsWhiteSpace) {
            } else {
                String errorMessage = "Geslo mora biti:";
                if (!isEightCharsLong) {
                    errorMessage += "\n- vsaj 8 znakov dolgo";
                }
                if (!containsUpperCase) {
                    errorMessage += "\n- vsebovati vsaj eno veliko črko";
                }
                if (!containsLowerCase) {
                    errorMessage += "\n- vsebovati vsaj eno malo črko";
                }
                if (!containsDigit) {
                    errorMessage += "\n- vsebovati vsaj eno številko";
                }
                if (!containsSpecialChar) {
                    errorMessage += "\n- vsebovati vsaj en poseben znak (@#$%^&+=.)";
                }
                if (containsWhiteSpace) {
                    errorMessage += "\n- ne vsebovati presledkov";
                }
                canProceed = false;
            }
        }

        if(TextUtils.isEmpty(password1.getText()))
        {
            canProceed = false;
        }
        else
        {
            if(!password1.getText().toString().equals(password_text.getText().toString()))
            {
                canProceed = false;
            }
            else
            {
            }
        }

        if (canProceed) {
            saveData();
            Intent intent = new Intent(Registracija.this, Registracija2.class);
            startActivity(intent);
        }
    }

    private void saveData(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username_text.getText().toString());
        editor.putString("email", email_text.getText().toString());
        editor.putString("pass", password_text.getText().toString());
        editor.putString("pass1", password1_text.getText().toString());
        editor.apply();
    }

    private void loadData() {
        String username = sharedPreferences.getString("username", "");
        String email = sharedPreferences.getString("email", "");
        String pass = sharedPreferences.getString("pass", "");
        String pass1 = sharedPreferences.getString("pass1", "");
        username_text.setText(username);
        email_text.setText(email);
        password_text.setText(pass);
        password1_text.setText(pass1);
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
        setContentView(R.layout.register1);

        sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);


         username_text = findViewById(R.id.username);
         email_text = findViewById(R.id.eposta);
         password_text = findViewById(R.id.password);
         password1_text = findViewById(R.id.password1);


        //Stvari




        Button naprej = findViewById(R.id.btn_naprej);


        TextView domov1 = findViewById(R.id.domov1);


        Drawable error = getResources().getDrawable(R.drawable.ic_outline_cancel_24);
        Drawable success = ContextCompat.getDrawable(this, R.drawable.ic_outline_check_circle_24);
        success.setBounds(0, 0, success.getIntrinsicWidth(), success.getIntrinsicHeight());
        error.setBounds(0, 0, error.getIntrinsicWidth(), error.getIntrinsicHeight());

        loadData();

        naprej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = username_text.getText().toString();
                email = email_text.getText().toString();
                pass = password_text.getText().toString();
                String pass1 = password1_text.getText().toString();



                validateInput(username_text, email_text, password_text, password1_text);

            }
        });
        username_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    username_text.setError("Vnesite uporabniško ime!", error);
                    isFormValid = false;
                    //username_text.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, error, null);
                } else {
                    isFormValid = true;
                    username_text.setError(null);
                    username_text.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, success, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        email_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    email_text.setError("Vnesite email!", error);
                    isFormValid = false;
                    //email_text.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, error, null);
                } else {


                    email_text.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, success, null);
                    Pattern pattern = Patterns.EMAIL_ADDRESS;
                    boolean isEmailValid = pattern.matcher(s).matches();
                    if(!isEmailValid) {
                        email_text.setError("Vnesite veljaven email naslov!",error);
                        isFormValid = false;
                    }
                    else
                    {
                        isFormValid = true;
                        email_text.setError(null);
                        email_text.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, success, null);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        password_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    password_text.setError("Vnesite geslo!", error);
                    isFormValid = false;
                    password_text.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, error, null);
                } else {
                    boolean isEightCharsLong = s.length() >= 8;
                    boolean containsUpperCase = !s.toString().equals(s.toString().toLowerCase());
                    boolean containsLowerCase = !s.toString().equals(s.toString().toUpperCase());
                    boolean containsDigit = s.toString().matches(".*\\d.*");
                    boolean containsSpecialChar = s.toString().matches(".*[@#$%^&+=.].*");
                    boolean containsWhiteSpace = s.toString().contains(" ");

                    if (isEightCharsLong && containsUpperCase && containsLowerCase && containsDigit && containsSpecialChar && !containsWhiteSpace) {

                        isFormValid = true;
                        password_text.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, success, null);
                    } else {
                        String errorMessage = "Geslo mora biti:";
                        if (!isEightCharsLong) {
                            errorMessage += "\n- vsaj 8 znakov dolgo";
                        }
                        if (!containsUpperCase) {
                            errorMessage += "\n- vsebovati vsaj eno veliko črko";
                        }
                        if (!containsLowerCase) {
                            errorMessage += "\n- vsebovati vsaj eno malo črko";
                        }
                        if (!containsDigit) {
                            errorMessage += "\n- vsebovati vsaj eno številko";
                        }
                        if (!containsSpecialChar) {
                            errorMessage += "\n- vsebovati vsaj en poseben znak (@#$%^&+=.)";
                        }
                        if (containsWhiteSpace) {
                            errorMessage += "\n- ne vsebovati presledkov";
                        }
                        isFormValid = false;
                        password_text.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, success, null);
                        password_text.setError(errorMessage, error);

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });





        password1_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    password1_text.setError("Ponovite geslo!", error);
                    password1_text.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, error, null);
                } else if (s.toString().equals(password_text.getText().toString())) {
                    isFormValid = true;
                    password1_text.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, success, null);
                } else {
                    isFormValid = false;
                    password1_text.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, success, null);
                    password1_text.setError("Gesli se ne ujemata!", error);

                }
            }
        });





        domov1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(Registracija.this, MainActivity.class);
                startActivity(intent);
            }
        });


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username_text.clearFocus();
                password_text.clearFocus();
                email_text.clearFocus();
                password1_text.clearFocus();



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

        email_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

        password1_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
