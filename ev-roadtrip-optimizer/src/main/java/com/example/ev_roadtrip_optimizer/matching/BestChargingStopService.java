package com.example.ev_roadtrip_optimizer.matching;

import com.example.ev_roadtrip_optimizer.battery.BatteryService;
import com.example.ev_roadtrip_optimizer.charging.ChargingStationResponse;
import com.example.ev_roadtrip_optimizer.charging.ChargingStationService;
import com.example.ev_roadtrip_optimizer.vehicle.Vehicle;
import com.example.ev_roadtrip_optimizer.vehicle.VehicleService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class BestChargingStopService {

    private final VehicleService vehicleService;
    private final BatteryService batteryService;
    private final ChargingStationService chargingStationService;

    public BestChargingStopService(
            VehicleService vehicleService,
            BatteryService batteryService,
            ChargingStationService chargingStationService) {

        this.vehicleService = vehicleService;
        this.batteryService = batteryService;
        this.chargingStationService = chargingStationService;
    }

    public BestChargingStopResponse findBestChargingStop(
            Long vehicleId,
            List<RoutePoint> routePoints,
            int radiusMeters,
            double minimumBatteryPercentage) {

        Vehicle vehicle =
                vehicleService.getVehicleById(vehicleId);

        List<ChargingStationResponse> stations =
                chargingStationService.findStationsAroundRoute(
                        routePoints,
                        radiusMeters);

        return routePoints.stream()
                .flatMap(point -> stations.stream()
                        .map(station -> createCandidate(
                                vehicle,
                                point,
                                station,
                                radiusMeters,
                                minimumBatteryPercentage)))
                .filter(candidate -> candidate != null)
                .max(Comparator.comparingDouble(
                        BestChargingStopResponse
                                ::getDistanceFromOriginKm))
                .orElse(null);
    }

    private BestChargingStopResponse createCandidate(
            Vehicle vehicle,
            RoutePoint point,
            ChargingStationResponse station,
            int radiusMeters,
            double minimumBatteryPercentage) {

        double distanceKm =
                calculateDistanceKm(
                        point.getLatitude(),
                        point.getLongitude(),
                        station.getLatitude(),
                        station.getLongitude());

        if (distanceKm > radiusMeters / 1000.0) {
            return null;
        }

        double batteryPercentage =
                batteryService
                        .calculateRemainingBatteryPercentage(
                                vehicle,
                                point.getDistanceFromOriginKm());

        if (batteryPercentage < minimumBatteryPercentage) {
            return null;
        }

        return new BestChargingStopResponse(
                station.getName(),
                station.getLatitude(),
                station.getLongitude(),
                station.getChargerType(),
                station.getChargingSpeedKw(),
                point.getDistanceFromOriginKm(),
                batteryPercentage
        );
    }

    private double calculateDistanceKm(
            double latitude1,
            double longitude1,
            double latitude2,
            double longitude2) {

        double earthRadiusKm = 6371.0;

        double latDifference =
                Math.toRadians(latitude2 - latitude1);

        double lonDifference =
                Math.toRadians(longitude2 - longitude1);

        double a =
                Math.sin(latDifference / 2)
                        * Math.sin(latDifference / 2)
                        + Math.cos(Math.toRadians(latitude1))
                        * Math.cos(Math.toRadians(latitude2))
                        * Math.sin(lonDifference / 2)
                        * Math.sin(lonDifference / 2);

        double c =
                2 * Math.atan2(
                        Math.sqrt(a),
                        Math.sqrt(1 - a));

        return earthRadiusKm * c;
    }
}