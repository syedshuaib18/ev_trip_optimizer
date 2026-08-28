package com.example.ev_roadtrip_optimizer.battery;

import com.example.ev_roadtrip_optimizer.routing.RouteResponse;
import com.example.ev_roadtrip_optimizer.routing.RoutingService;
import com.example.ev_roadtrip_optimizer.vehicle.Vehicle;
import com.example.ev_roadtrip_optimizer.vehicle.VehicleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/battery")
public class BatteryController {

    private final BatteryService batteryService;
    private final VehicleService vehicleService;
    private final RoutingService routingService;

    public BatteryController(
            BatteryService batteryService,
            VehicleService vehicleService,
            RoutingService routingService) {
        this.batteryService = batteryService;
        this.vehicleService = vehicleService;
        this.routingService = routingService;
    }

    @GetMapping("/{vehicleId}")
    public BatteryResponse calculateBattery(
            @PathVariable Long vehicleId,
            @RequestParam double originLat,
            @RequestParam double originLon,
            @RequestParam double destinationLat,
            @RequestParam double destinationLon) {

        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);

        RouteResponse route = routingService.calculateRoute(
                originLat, originLon,
                destinationLat, destinationLon);

        double energyConsumed =
                batteryService.calculateEnergyConsumed(
                        vehicle, route.getDistanceKm());

        double remainingBattery =
                batteryService.calculateRemainingBatteryPercentage(
                        vehicle, route.getDistanceKm());

        return new BatteryResponse(
                route.getDistanceKm(),
                energyConsumed,
                remainingBattery
        );
    }

    @GetMapping("/{vehicleId}/range")
    public double calculateRange(
            @PathVariable Long vehicleId,
            @RequestParam double minimumBatteryPercentage) {

        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);

        return batteryService.calculateMaximumReachableDistance(
                vehicle, minimumBatteryPercentage);
    }

    @GetMapping("/{vehicleId}/needs-charging")
    public boolean needsCharging(
            @PathVariable Long vehicleId,
            @RequestParam double distanceKm,
            @RequestParam double minimumBatteryPercentage) {

        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);

        return batteryService.needsCharging(
                vehicle, distanceKm, minimumBatteryPercentage);
    }
}