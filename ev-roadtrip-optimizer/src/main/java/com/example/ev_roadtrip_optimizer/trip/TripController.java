package com.example.ev_roadtrip_optimizer.trip;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping
    public Trip createTrip(@RequestBody Trip trip) {
        return tripService.saveTrip(trip);
    }

    @GetMapping("/{id}")
    public Trip getTrip(@PathVariable Long id) {
        return tripService.getTripById(id);
    }

    @GetMapping
    public List<Trip> getAllTrips() {
        return tripService.getAllTrips();
    }
    @PutMapping("/{id}")
    public Trip updateTrip(@PathVariable Long id,@RequestBody Trip trip){
        return tripService.updateTrip(id,trip);
    }
    @DeleteMapping("/{id}")
    public void deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
    }
}