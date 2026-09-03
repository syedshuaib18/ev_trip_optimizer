package com.example.ev_roadtrip_optimizer.matching;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/best-charging-stop")
public class BestChargingStopController {

    private final BestChargingStopService service;
    private final RouteStopPlannerService routeStopPlannerService;

    public BestChargingStopController(
            BestChargingStopService service,
            RouteStopPlannerService routeStopPlannerService) {

        this.service = service;
        this.routeStopPlannerService = routeStopPlannerService;
    }

    @GetMapping
    public BestChargingStopResponse findBestChargingStop(
            @RequestParam Long vehicleId,
            @RequestParam double originLat,
            @RequestParam double originLon,
            @RequestParam double destinationLat,
            @RequestParam double destinationLon,
            @RequestParam(defaultValue = "10") int numberOfStops,
            @RequestParam(defaultValue = "5000") int radiusMeters,
            @RequestParam(defaultValue = "15") double minimumBatteryPercentage) {

        List<RoutePoint> routePoints =
                routeStopPlannerService.generateRoutePoints(
                        originLat,
                        originLon,
                        destinationLat,
                        destinationLon,
                        numberOfStops);

        return service.findBestChargingStop(
                vehicleId,
                routePoints,
                radiusMeters,
                minimumBatteryPercentage);
    }
}