package com.example.ev_roadtrip_optimizer.matching;

public class RouteChargingStation {

    private double routePointLatitude;
    private double routePointLongitude;
    private double distanceFromOriginKm;

    private String stationName;
    private double stationLatitude;
    private double stationLongitude;
    private String chargerType;
    private Double chargingSpeedKw;

    public RouteChargingStation(
            double routePointLatitude,
            double routePointLongitude,
            double distanceFromOriginKm,
            String stationName,
            double stationLatitude,
            double stationLongitude,
            String chargerType,
            Double chargingSpeedKw) {

        this.routePointLatitude = routePointLatitude;
        this.routePointLongitude = routePointLongitude;
        this.distanceFromOriginKm = distanceFromOriginKm;
        this.stationName = stationName;
        this.stationLatitude = stationLatitude;
        this.stationLongitude = stationLongitude;
        this.chargerType = chargerType;
        this.chargingSpeedKw = chargingSpeedKw;
    }

    public double getRoutePointLatitude() {
        return routePointLatitude;
    }

    public double getRoutePointLongitude() {
        return routePointLongitude;
    }

    public double getDistanceFromOriginKm() {
        return distanceFromOriginKm;
    }

    public String getStationName() {
        return stationName;
    }

    public double getStationLatitude() {
        return stationLatitude;
    }

    public double getStationLongitude() {
        return stationLongitude;
    }

    public String getChargerType() {
        return chargerType;
    }

    public Double getChargingSpeedKw() {
        return chargingSpeedKw;
    }
}