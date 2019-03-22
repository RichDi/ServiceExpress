package com.example.serviceexpress;

import com.google.android.gms.maps.model.LatLng;

public class LatLng_FB {

    private double latitude;
    private double longitude;
    private String address;

    public LatLng_FB() {
    }

    public LatLng_FB(double latitude, double longitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public LatLng_FB(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LatLng getLatLNG(){
        return new LatLng(this.getLatitude(), this.getLongitude());
    }
}
