package com.example.myapplication;





import android.annotation.SuppressLint;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.databinding.HomeBinding;

public class Domov extends AppCompatActivity {

    HomeBinding binding;


    public static Vozilo vozilo;
    public static String prihod;
    public static String odhod;
    public static String parkirisce;

    private static final String PREFS_NAME = "MyPrefs";
    private static final String USERNAME_KEY = "username";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Poveze z bazo
//        Domov.vozilo = Baza.OsveziDB();


        binding = HomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ImageView profil = findViewById(R.id.profil);
        ImageView parking = findViewById(R.id.parking);
        ImageView zemljevid = findViewById(R.id.zemljevid);
        ImageView nastavitve = findViewById(R.id.nastavitve);
        ImageView zgodovina = findViewById(R.id.zgodovina);



        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ProfilFragment profilFragment = new ProfilFragment();
                fragmentTransaction.replace(R.id.top_shape, profilFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        parking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ParkingFragment parkingFragment = new ParkingFragment();
                fragmentTransaction.replace(R.id.top_shape, parkingFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        zemljevid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ZemljevidFragment zemljevidFragment = new ZemljevidFragment();
                fragmentTransaction.replace(R.id.top_shape, zemljevidFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        nastavitve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SettingsFragment nastavitveFragment = new SettingsFragment();
                fragmentTransaction.replace(R.id.top_shape, nastavitveFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        zgodovina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ZgodovinaFragment zgodovinaFragment = new ZgodovinaFragment();
                fragmentTransaction.replace(R.id.top_shape, zgodovinaFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();


    }


}
