package com.example.myapplication;

public class Vozilo {


    private final String stopnja;
    private final String odhod;
    private final String prihod;
    private final String parkirisce;

    private final String registrska_st;

    public Vozilo(String stopnja, String odhod, String prihod, String parkirisce, String registrska_st) {
        this.stopnja = stopnja;
        this.odhod = odhod;
        this.prihod = prihod;
        this.parkirisce = parkirisce;
        this.registrska_st = registrska_st;
    }

    public String getStopnja() {
        return stopnja;
    }

    public String getOdhod() {
        return odhod;
    }

    public String getPrihod() {
        return prihod;
    }

    public String getParkirisce() {
        return parkirisce;
    }

    public String getRegistrska_st() {return registrska_st; }
}
