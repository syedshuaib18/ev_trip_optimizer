package com.example.ev_roadtrip_optimizer.charging;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/charging-stations")
public class ChargingStationController {

    private final ChargingStationService service;

    public ChargingStationController(ChargingStationService service) {
        this.service = service;
    }

    @PostMapping
    public ChargingStation createStation(@RequestBody ChargingStation station) {
        return service.saveStation(station);
    }

    @GetMapping
    public List<ChargingStation> getAllStations() {
        return service.getAllStations();
    }

    @GetMapping("/{id}")
    public ChargingStation getStation(@PathVariable Long id) {
        return service.getStationById(id);
    }

    @GetMapping("/nearby")
    public List<ChargingStationResponse> findNearbyStations(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "5000") int radiusMeters) {

        return service.findNearbyStations(
                latitude, longitude, radiusMeters);
    }
}