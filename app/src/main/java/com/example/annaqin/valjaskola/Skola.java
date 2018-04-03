package com.example.annaqin.valjaskola;

/**
 * Created by annaqin on 2018-03-26.
 */

public class Skola {

    String name;
    double latitude;
    double longitude;

    public Skola(String name, double latitude, double longitude) {
        this.name=name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Skola(){};


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
