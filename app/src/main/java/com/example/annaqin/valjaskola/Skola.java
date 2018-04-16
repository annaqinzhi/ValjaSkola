package com.example.annaqin.valjaskola;

public class Skola implements Comparable<Skola> {

    private String name;
    private String kummun;
    private String type;
    private double gmeriv;
    private String adress;
    private String tel;
    private String utbil;
    private String uppn;
    private double latitude;
    private double longitude;


    public Skola() {
    }

    public Skola(String name, String kummun, String type, double gmeriv, String adress, String tel, String utbil, String uppn, double latitude, double longitude) {
        this.name = name;
        this.kummun = kummun;
        this.type = type;
        this.gmeriv = gmeriv;
        this.adress = adress;
        this.tel = tel;
        this.utbil = utbil;
        this.uppn = uppn;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKummun() {
        return kummun;
    }

    public void setKummun(String kummun) {
        this.kummun = kummun;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getGmeriv() {
        return gmeriv;
    }

    public void setGmeriv(double gmeriv) {
        this.gmeriv = gmeriv;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUtbil() {
        return utbil;
    }

    public void setUtbil(String utbil) {
        this.utbil = utbil;
    }

    public String getUppn() {
        return uppn;
    }

    public void setUppn(String uppn) {
        this.uppn = uppn;
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

    @Override
    public int compareTo(Skola compskola) {

        /* For Ascending order*/
        return new Double(gmeriv).compareTo( compskola.gmeriv);

        /* For Descending order do like this */
        //return compareGmeriv-this.gmeriv;
    }

    @Override
    public String toString() {
        return String.valueOf(gmeriv);
    }
}



