package com.example.ev_roadtrip_optimizer.charging;

public class ChargingStationResponse {

    private String name;
    private double latitude;
    private double longitude;
    private String chargerType;
    private Double chargingSpeedKw;

    public ChargingStationResponse(
            String name,
            double latitude,
            double longitude,
            String chargerType,
            Double chargingSpeedKw) {

        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.chargerType = chargerType;
        this.chargingSpeedKw = chargingSpeedKw;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getChargerType() {
        return chargerType;
    }

    public Double getChargingSpeedKw() {
        return chargingSpeedKw;
    }
}