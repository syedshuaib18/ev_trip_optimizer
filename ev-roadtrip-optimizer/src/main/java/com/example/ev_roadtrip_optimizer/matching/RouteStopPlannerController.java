package com.example.ev_roadtrip_optimizer.matching;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/route-stops")
public class RouteStopPlannerController {

    private final RouteStopPlannerService service;

    public RouteStopPlannerController(RouteStopPlannerService service) {
        this.service = service;
    }

    @GetMapping
    public List<RoutePoint> generateRoutePoints(
            @RequestParam double originLat,
            @RequestParam double originLon,
            @RequestParam double destinationLat,
            @RequestParam double destinationLon,
            @RequestParam(defaultValue = "5") int numberOfStops) {

        return service.generateRoutePoints(
                originLat,
                originLon,
                destinationLat,
                destinationLon,
                numberOfStops);
    }
}