package com.example.ev_roadtrip_optimizer.matching;

public class RoutePoint {

    private double latitude;
    private double longitude;
    private double distanceFromOriginKm;

    public RoutePoint(double latitude, double longitude,
                      double distanceFromOriginKm) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.distanceFromOriginKm = distanceFromOriginKm;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getDistanceFromOriginKm() {
        return distanceFromOriginKm;
    }
}