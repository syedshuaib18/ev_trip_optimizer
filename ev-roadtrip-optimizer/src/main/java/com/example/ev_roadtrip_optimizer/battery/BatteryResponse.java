package com.example.ev_roadtrip_optimizer.battery;

public class BatteryResponse {

    private double distanceKm;
    private double energyConsumedKwh;
    private double remainingBatteryPercentage;

    public BatteryResponse(double distanceKm,
                           double energyConsumedKwh,
                           double remainingBatteryPercentage) {
        this.distanceKm = distanceKm;
        this.energyConsumedKwh = energyConsumedKwh;
        this.remainingBatteryPercentage = remainingBatteryPercentage;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public double getEnergyConsumedKwh() {
        return energyConsumedKwh;
    }

    public double getRemainingBatteryPercentage() {
        return remainingBatteryPercentage;
    }
}