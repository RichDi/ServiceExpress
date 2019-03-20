package com.example.serviceexpress;

public class LatLng_FB {

    private double latitude;
    private double longitude;

    public LatLng_FB() {

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

    public LatLng_FB(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
