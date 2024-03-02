package com.example.myapplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Baza {


    public static String uporabnik_username;


    public static Connection connectDb(){
        try {


            Connection connection = null;
            connection = DriverManager.getConnection("jdbc:mysql://spiritofsakura.eu:3306/spiritof_parkirisca?useSSL=false", "spiritof_user6809", "1q2w3e4r5t6z7u8i9o0p");

            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void Prijava(String username, String geslo, TextView user, TextView pass,Drawable error,Drawable success)
    {



        success.setBounds(0, 0, success.getIntrinsicWidth(), success.getIntrinsicHeight());
        error.setBounds(0, 0, error.getIntrinsicWidth(), error.getIntrinsicHeight());
        PreparedStatement stmt = null;
        PreparedStatement stmt1 = null;
        ResultSet resultSet1 = null;
        ResultSet resultSet = null;

        Uporabnik uporabnik;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try{


            Class.forName("com.mysql.jdbc.Driver");

            stmt = connectDb().prepareStatement("SELECT username FROM uporabnik WHERE username = ?");
            stmt.setString(1,username);
            resultSet = stmt.executeQuery();



            if(user.getText().toString().isEmpty()){
                user.setError("Vnesite uporabniško ime!",error);
            }
            else
            {
                if(!resultSet.isBeforeFirst()) {
                    System.out.println("Napačno uporabniško ime!"); //POSODOBI
                    user.setError("Napačno uporabniško ime!",error);

                    if(pass.getText().toString().isEmpty()){
                        pass.setError("Vnesite geslo!",error);
                    }

                } else {
                    while (resultSet.next()) {

                        String pridobljen_username = resultSet.getString("username");


                        //UREDI da dobiš vse potrebne stvari in se shrani v objekt uporabnik
                        stmt = connectDb().prepareStatement("SELECT username FROM uporabnik WHERE username = ? AND password = ?");
                        stmt.setString(1,pridobljen_username);
                        stmt.setString(2,geslo);
                        resultSet = stmt.executeQuery();

                        //If resultSet returns an error, then the password is wrong
                        if(!resultSet.isBeforeFirst()){
                            System.out.println("Napačno geslo!"); //POSODOBI
                            pass.setError("Napačno geslo!",error);


                        }
                        else {
                            System.out.println("Geslo je pravilno!"); //POSODOBI
                            user.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, success, null);



                            stmt1 = connectDb().prepareStatement("SELECT token, ime, priimek FROM uporabnik WHERE username = ?");
                            stmt1.setString(1,username);
                            resultSet1 = stmt1.executeQuery();


                            while(resultSet1.next()){
                                String token = resultSet1.getString("token");
                                String ime = resultSet1.getString("ime");
                                String priimek = resultSet1.getString("priimek");
                                System.out.println(token);
                                if(token.equals(MainActivity.token)){
                                    System.out.println("Token se ujema");
                                    Baza.uporabnik_username = pridobljen_username;

                                    Domov.vozilo = OsveziDB();



                                    //Save into myprefs

                                    SharedPreferences myPrefs = user.getContext().getSharedPreferences(MyFirebaseMessagingService.PREFS_NAME, MODE_PRIVATE);
                                    SharedPreferences.Editor editor = myPrefs.edit();
                                    editor.putString("ime", ime);
                                    editor.putString("priimek", priimek);
                                    editor.apply();



                                    Intent intent = new Intent(user.getContext(), Domov.class);
                                    user.getContext().startActivity(intent);

                                    stmt.close();
                                    stmt1.close();
                                    resultSet.close();
                                    resultSet1.close();



                                }
                                else{
                                    System.out.println("Token se ne ujema");
                                    PreparedStatement stmt2 = null;
                                    stmt2 = connectDb().prepareStatement("UPDATE uporabnik SET token = ? WHERE username = ?");
                                    stmt2.setString(1,MainActivity.token);
                                    stmt2.setString(2,username);
                                    stmt2.executeUpdate();

                                    Baza.uporabnik_username = pridobljen_username;

                                    OsveziDB();


                                    //Save into myprefs

                                    SharedPreferences myPrefs = user.getContext().getSharedPreferences("myprefs", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = myPrefs.edit();
                                    editor.putString("username", pridobljen_username);
                                    editor.apply();

                                    Intent intent = new Intent(user.getContext(), Domov.class);
                                    user.getContext().startActivity(intent);

                                    stmt.close();
                                    stmt1.close();
                                    resultSet.close();
                                    resultSet1.close();
                                    stmt2.close();
                                }
                            }

                        }

                        user.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, success, null);
                    }
                }
            }


        }catch (SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null)
            {
                try {
                    resultSet.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (stmt != null)
            {
                try {
                    stmt.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connectDb() != null)
            {
                try {
                    connectDb().close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }

        }
    }


    public static Boolean Registracija(Uporabnik user) {
        PreparedStatement stmtAvtomobili = null;
        PreparedStatement stmtUporabnik = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {


            Class.forName("com.mysql.jdbc.Driver");


            // Prepare the first query for the 'uporabnik' table
            stmtUporabnik = connectDb().prepareStatement("INSERT INTO uporabnik(username, email, password, ime, priimek, naslov, tel_st,token) VALUES(?,?,?,?,?,?,?,?)");

            stmtUporabnik.setString(1, user.getUsername());
            stmtUporabnik.setString(2, user.getEmail());
            stmtUporabnik.setString(3, user.getGeslo());
            stmtUporabnik.setString(4, user.getIme());
            stmtUporabnik.setString(5, user.getPriimek());
            stmtUporabnik.setString(6, user.getNaslov());
            stmtUporabnik.setString(7, user.getTelefon());
            stmtUporabnik.setString(8,MainActivity.token);


            stmtUporabnik.executeUpdate(); // Execute the 'uporabnik' query


            //Prepare the 2nd query
            // Prepare the first query for the 'avtomobili' table
            stmtAvtomobili = connectDb().prepareStatement("INSERT INTO avtomobili(reg_st, identifikacijska_stevilka, naslov, drzava,uporabnik_id) VALUES(?,?,?,?,(SELECT id FROM uporabnik WHERE username = ?))");

            stmtAvtomobili.setString(1, user.getRegistrska_stevilka());
            stmtAvtomobili.setString(2, user.getVozilo_id());
            stmtAvtomobili.setString(3, user.getNaslov());
            stmtAvtomobili.setString(4, user.getDrzava());
            stmtAvtomobili.setString(5, user.getUsername());

            stmtAvtomobili.executeUpdate(); // Execute the 'avtomobili' query

            return true;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        } finally {
            // Close the prepared statements in the finally block
            try {
                if (stmtAvtomobili != null) stmtAvtomobili.close();
                if (stmtUporabnik != null) stmtUporabnik.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }


    public static Vozilo OsveziDB()
    {
        PreparedStatement stmt = null;

        ResultSet resultSet = null;

        String prihod = null;
        String odhod= null;
        String ime_parkirisca= null;
        String stopnja_zasedenosti= null;
        String registrska = null;

        Vozilo vozilo = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try{
            Class.forName("com.mysql.jdbc.Driver");

            stmt = connectDb().prepareStatement("SELECT pa.prihod, pa.odhod, p.ime_parkirisca, p.stopnja_zasedenosti, a.reg_st FROM parkirisca_avtomobili pa INNER JOIN parkirisca p on pa.parkirisce_id = p.id INNER JOIN avtomobili a ON a.id = pa.avtomobil_id = a.id WHERE avtomobil_id = (SELECT id from avtomobili where uporabnik_id = (select id from uporabnik where username = ?))");
            stmt.setString(1, uporabnik_username);
            resultSet = stmt.executeQuery();



            while(resultSet.next()){
                prihod = resultSet.getString("prihod");
                odhod = resultSet.getString("odhod");
                ime_parkirisca = resultSet.getString("ime_parkirisca");
                stopnja_zasedenosti = resultSet.getString("stopnja_zasedenosti");
                registrska = resultSet.getString("reg_st");

                System.out.println(prihod + " " + odhod + " " + ime_parkirisca + " " + stopnja_zasedenosti);



            }
            vozilo = new Vozilo(stopnja_zasedenosti,odhod,prihod,ime_parkirisca,registrska);




        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        if(vozilo == null)
        {
            System.out.println("Uporabnik nima vozila");
        }
        else
        {
            System.out.println(vozilo.getPrihod());
        }
        return vozilo;
    }






}