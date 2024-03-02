package com.example.myapplication;

public class RegistrationHandler {
    private String username;
    private String email;
    private String pass;
    private String pass1;
    private String ime;
    private String priimek;
    private String naslov;
    private String telefon;
    private String registrska_stevilka;
    private String vozilo_id;
    private String drzava;
    private String model;
    // ... other member variables and methods

    public void registerPage1(String username, String email,String pass, String pass1) {
        // validate and store registration data for page 1
        this.username = username;
        this.email = email;
        this.pass = pass;
        this.pass1 = pass1;



    }

    public void registerPage2(String ime,String priimek, String naslov, String telefon) {
        // validate and store registration data for page 2
        this.ime = ime;
        this.priimek = priimek;
        this.naslov = naslov;
        this.telefon = telefon;
    }

    public void registerPage3(String registrska_stevilka, String vozilo_id, String drzava, String model) {
        // validate and store registration data for page 3
        this.registrska_stevilka = registrska_stevilka;
        this.vozilo_id = vozilo_id;
        this.drzava = drzava;
        this.model = model;
    }

    public void registerAll(String username, String email,String pass, String pass1, String ime,String priimek, String naslov, String telefon, String registrska_stevilka, String vozilo_id, String drzava, String model) {
        registerPage1(username, email, pass, pass1);
        registerPage2(ime, priimek, naslov, telefon);
        registerPage3(registrska_stevilka, vozilo_id, drzava, model);
    }
}
