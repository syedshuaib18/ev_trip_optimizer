package com.example.ev_roadtrip_optimizer.matching;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class RestaurantChargingMatchController {

    private final RestaurantChargingMatchService service;

    public RestaurantChargingMatchController(
            RestaurantChargingMatchService service) {
        this.service = service;
    }

    @GetMapping
    public List<RestaurantChargingMatchResponse> findMatches(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "5000") int radiusMeters) {

        return service.findMatches(
                latitude,
                longitude,
                radiusMeters);
    }
}