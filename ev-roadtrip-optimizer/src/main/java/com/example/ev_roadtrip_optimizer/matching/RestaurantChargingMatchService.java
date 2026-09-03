package com.example.ev_roadtrip_optimizer.matching;

import com.example.ev_roadtrip_optimizer.charging.ChargingStationResponse;
import com.example.ev_roadtrip_optimizer.charging.ChargingStationService;
import com.example.ev_roadtrip_optimizer.restaurant.RestaurantResponse;
import com.example.ev_roadtrip_optimizer.restaurant.RestaurantService;
import org.springframework.stereotype.Service;
import java.util.Comparator;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantChargingMatchService {

    private final ChargingStationService chargingStationService;
    private final RestaurantService restaurantService;

    public RestaurantChargingMatchService(
            ChargingStationService chargingStationService,
            RestaurantService restaurantService) {

        this.chargingStationService = chargingStationService;
        this.restaurantService = restaurantService;
    }

    public List<RestaurantChargingMatchResponse> findMatches(
            double latitude,
            double longitude,
            int radiusMeters) {

        List<ChargingStationResponse> stations =
                chargingStationService.findNearbyStations(
                        latitude, longitude, radiusMeters);

        List<RestaurantResponse> restaurants =
                restaurantService.findNearbyRestaurants(
                        latitude, longitude, radiusMeters);

        List<RestaurantChargingMatchResponse> matches =
                new ArrayList<>();

        for (ChargingStationResponse station : stations) {

            for (RestaurantResponse restaurant : restaurants) {

                double distanceKm = calculateDistanceKm(
                        station.getLatitude(),
                        station.getLongitude(),
                        restaurant.getLatitude(),
                        restaurant.getLongitude());

                if (distanceKm <= 0.5) {

                    matches.add(
                            new RestaurantChargingMatchResponse(
                                    restaurant.getName(),
                                    restaurant.getLatitude(),
                                    restaurant.getLongitude(),
                                    restaurant.getCuisine(),
                                    station.getName(),
                                    station.getLatitude(),
                                    station.getLongitude(),
                                    distanceKm
                            )
                    );
                }
            }
        }
        matches.sort(Comparator.comparingDouble(
                RestaurantChargingMatchResponse::getDistanceKm));

        return matches;

    }

    public double calculateDistanceKm(
            double latitude1, double longitude1,
            double latitude2, double longitude2) {

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
                2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadiusKm * c;
    }
}