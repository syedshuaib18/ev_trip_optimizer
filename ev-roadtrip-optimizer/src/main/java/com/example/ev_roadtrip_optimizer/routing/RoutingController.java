package com.example.ev_roadtrip_optimizer.routing;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/routes")
public class RoutingController {
    private final RoutingService routingService;

    public RoutingController(RoutingService routingService) {
        this.routingService = routingService;
    }

    @GetMapping
    public RouteResponse calculateRoute(
            @RequestParam double originLat,
            @RequestParam double originLon,
            @RequestParam double destinationLat,
            @RequestParam double destinationLon) {

        return routingService.calculateRoute(
                originLat, originLon, destinationLat, destinationLon);
    }
}