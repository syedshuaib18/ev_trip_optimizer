package com.example.ev_roadtrip_optimizer.trip;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TripService {

    private final TripRepository tripRepository;

    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public Trip saveTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    public Trip getTripById(Long id) {
        return tripRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trip not found"));
    }

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }
    public Trip updateTrip(Long id,Trip trip){
        Trip existing =getTripById(id);
        existing.setVehicleId(trip.getVehicleId());
        existing.setOrigin(trip.getOrigin());
        existing.setDestination(trip.getDestination());
        existing.setOriginLatitude(trip.getOriginLatitude());
        existing.setOriginLongitude(trip.getOriginLongitude());
        existing.setDestinationLatitude(trip.getDestinationLatitude());
        existing.setDestinationLongitude(trip.getDestinationLongitude());
        return tripRepository.save(existing);
    }
    public void deleteTrip(Long id) {
        Trip existing = getTripById(id);
        tripRepository.delete(existing);
    }
}