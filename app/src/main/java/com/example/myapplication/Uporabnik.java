package com.example.myapplication;

public class Uporabnik {
    //Atributi uporabnik iz Registracija, Registracija2, Registracija3, podatki o vozilu


    private final String username;
    private final String email;
    private final String geslo;
    private final String ime;
    private final String priimek;
    private final String naslov;
    private final String telefon;
    private final String registrska_stevilka;
    private final String vozilo_id;
    private final String drzava;
    private final String model;


    public Uporabnik(String username, String email, String geslo, String ime, String priimek, String naslov, String telefon, String registrska_stevilka, String vozilo_id, String drzava, String model) {
        this.username = username;
        this.email = email;
        this.geslo = geslo;
        this.ime = ime;
        this.priimek = priimek;
        this.naslov = naslov;
        this.telefon = telefon;
        this.registrska_stevilka = registrska_stevilka;
        this.vozilo_id = vozilo_id;
        this.drzava = drzava;
        this.model = model;
    }



    public String getRegistrska_stevilka() {
        return registrska_stevilka;
    }

    public String getVozilo_id() {
        return vozilo_id;
    }

    public String getDrzava() {
        return drzava;
    }

    public String getModel() {
        return model;
    }

    public String getIme() {
        return ime;
    }

    public String getPriimek() {
        return priimek;
    }

    public String getNaslov() {
        return naslov;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getGeslo() {
        return geslo;
    }
}
