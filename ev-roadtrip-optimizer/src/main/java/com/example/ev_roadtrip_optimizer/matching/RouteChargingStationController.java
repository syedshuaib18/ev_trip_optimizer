package com.example.ev_roadtrip_optimizer.matching;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/route-charging-stations")
public class RouteChargingStationController {

    private final RouteStopPlannerService service;

    public RouteChargingStationController(RouteStopPlannerService service) {
        this.service = service;
    }

    @GetMapping
    public List<RouteChargingStation> findChargingStations(
            @RequestParam double originLat,
            @RequestParam double originLon,
            @RequestParam double destinationLat,
            @RequestParam double destinationLon,
            @RequestParam(defaultValue = "5") int numberOfStops,
            @RequestParam(defaultValue = "5000") int radiusMeters) {

        return service.findChargingStationsAlongRoute(
                originLat,
                originLon,
                destinationLat,
                destinationLon,
                numberOfStops,
                radiusMeters);
    }
}