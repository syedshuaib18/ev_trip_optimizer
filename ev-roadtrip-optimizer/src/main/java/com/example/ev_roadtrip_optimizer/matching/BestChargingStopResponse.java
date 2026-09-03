package com.example.ev_roadtrip_optimizer.matching;

public class BestChargingStopResponse {

    private String stationName;
    private double stationLatitude;
    private double stationLongitude;
    private String chargerType;
    private Double chargingSpeedKw;
    private double distanceFromOriginKm;
    private double batteryPercentageAtStop;

    public BestChargingStopResponse(
            String stationName,
            double stationLatitude,
            double stationLongitude,
            String chargerType,
            Double chargingSpeedKw,
            double distanceFromOriginKm,
            double batteryPercentageAtStop) {

        this.stationName = stationName;
        this.stationLatitude = stationLatitude;
        this.stationLongitude = stationLongitude;
        this.chargerType = chargerType;
        this.chargingSpeedKw = chargingSpeedKw;
        this.distanceFromOriginKm = distanceFromOriginKm;
        this.batteryPercentageAtStop = batteryPercentageAtStop;
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

    public double getDistanceFromOriginKm() {
        return distanceFromOriginKm;
    }

    public double getBatteryPercentageAtStop() {
        return batteryPercentageAtStop;
    }
}