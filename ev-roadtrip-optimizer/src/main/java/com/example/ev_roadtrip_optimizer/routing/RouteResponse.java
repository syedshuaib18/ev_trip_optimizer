package com.example.ev_roadtrip_optimizer.routing;

import java.util.List;

public class RouteResponse {

    private double distanceKm;
    private double durationMinutes;
    private List<List<Double>> coordinates;

    public RouteResponse(
            double distanceKm,
            double durationMinutes,
            List<List<Double>> coordinates) {

        this.distanceKm = distanceKm;
        this.durationMinutes = durationMinutes;
        this.coordinates = coordinates;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public double getDurationMinutes() {
        return durationMinutes;
    }

    public List<List<Double>> getCoordinates() {
        return coordinates;
    }
}