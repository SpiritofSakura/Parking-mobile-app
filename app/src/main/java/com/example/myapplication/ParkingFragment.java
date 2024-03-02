package com.example.myapplication;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ParkingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParkingFragment extends Fragment {
    private void countTimeDifference(String inputTime, TextView timerTextView) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
            Date savedDate = sdf.parse(inputTime);
            Date currentDate = new Date(System.currentTimeMillis());

            long millis = currentDate.getTime() - savedDate.getTime();

            int hours = (int) (millis / (1000 * 60 * 60));
            int minutes = (int) ((millis / (1000 * 60)) % 60);
            int seconds = (int) ((millis / 1000) % 60);

            String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            timerTextView.setText(timeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView ura;
    private TextView ime;
    private TextView reg;
    private TextView parkirisce;

    private ImageView parkirisce_slika;

    private BroadcastReceiver notificationReceiver;

    private CountDownTimer  countDownTimer;




    public ParkingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ParkingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ParkingFragment newInstance(String param1, String param2) {
        ParkingFragment fragment = new ParkingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private void updateTimer(ImageView slika, TextView parking, TextView reg_st) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        String cas = null;
        if (Domov.vozilo != null) {

            cas = Domov.vozilo.getPrihod();

        }

        if (cas != null) {


            reg_st.setText(Domov.vozilo.getRegistrska_st());
            slika.setImageResource(R.drawable.tick);
            countTimeDifference(cas, ura);

            String finalCas = cas;
            countDownTimer = new CountDownTimer(Long.MAX_VALUE, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    countTimeDifference(finalCas, ura);
                }

                @Override
                public void onFinish() {

                }
            }.start();
        } else {
            slika.setImageResource(R.drawable.app);

            reg_st.setText("/");
            // If vozilo.PridobiOdhod is empty/null, set the timer to 00:00:00
            ura.setText("00:00:00");
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        Domov.vozilo = Baza.OsveziDB();

        // Initialize the BroadcastReceiver
        notificationReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //Receives notification
                updateTimer(parkirisce_slika,parkirisce,reg);

            }
        };


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);






        }


    }

    @Override
    public void onResume() {
        super.onResume();

        // Register the BroadcastReceiver
        getActivity().registerReceiver(notificationReceiver, new IntentFilter("com.example.myapplication.NOTIFICATION_RECEIVED"));
    }

    @Override
    public void onPause() {
        super.onPause();
        // Unregister the BroadcastReceiver
        getActivity().unregisterReceiver(notificationReceiver);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_parking, container, false);

        //Deklariraš komponente ki so v fragmentu
         ura = view.findViewById(R.id.text_ura);
         ime = view.findViewById(R.id.text_oseba);

         parkirisce = view.findViewById(R.id.text_st_parking);
         reg = view.findViewById(R.id.text_reg);
         parkirisce_slika = view.findViewById(R.id.slika_avta);


        SharedPreferences prefs = requireActivity().getSharedPreferences(MyFirebaseMessagingService.PREFS_NAME, MODE_PRIVATE);
        String ime_uporabnik = prefs.getString("ime", null);
        String priimek = prefs.getString("priimek", null);

        ime.setText("Pozdravljen/a " + ime_uporabnik + " " + priimek + "!");


        updateTimer(parkirisce_slika,parkirisce,reg);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

}