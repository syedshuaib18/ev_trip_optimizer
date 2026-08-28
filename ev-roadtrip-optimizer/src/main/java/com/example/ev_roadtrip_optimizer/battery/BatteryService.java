package com.example.ev_roadtrip_optimizer.battery;

import com.example.ev_roadtrip_optimizer.vehicle.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class BatteryService {

    public double calculateEnergyConsumed(Vehicle vehicle, double distanceKm) {
        return (distanceKm / 100) * vehicle.getConsumptionKwhPer100Km();
    }

    public double calculateRemainingBattery(Vehicle vehicle, double distanceKm) {
        double energyConsumed = calculateEnergyConsumed(vehicle, distanceKm);
        double startingEnergy =
                vehicle.getBatteryCapacityKwh()
                        * vehicle.getCurrentBatteryPercentage() / 100;

        return startingEnergy - energyConsumed;
    }

    public double calculateRemainingBatteryPercentage(Vehicle vehicle, double distanceKm) {
        double remainingEnergy = calculateRemainingBattery(vehicle, distanceKm);
        return (remainingEnergy / vehicle.getBatteryCapacityKwh()) * 100;
    }

    public boolean canReach(
            Vehicle vehicle,
            double distanceKm,
            double minimumBatteryPercentage) {

        double remaining =
                calculateRemainingBatteryPercentage(vehicle, distanceKm);

        return remaining >= minimumBatteryPercentage;
    }

    public boolean needsCharging(
            Vehicle vehicle,
            double distanceKm,
            double minimumBatteryPercentage) {

        return !canReach(vehicle, distanceKm, minimumBatteryPercentage);
    }

    public double calculateMaximumReachableDistance(
            Vehicle vehicle,
            double minimumBatteryPercentage) {

        double usableBatteryPercentage =
                vehicle.getCurrentBatteryPercentage()
                        - minimumBatteryPercentage;

        double usableEnergy =
                vehicle.getBatteryCapacityKwh()
                        * usableBatteryPercentage / 100;

        return (usableEnergy / vehicle.getConsumptionKwhPer100Km()) * 100;
    }
}