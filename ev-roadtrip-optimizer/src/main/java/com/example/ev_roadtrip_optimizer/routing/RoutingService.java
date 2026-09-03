package com.example.ev_roadtrip_optimizer.routing;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class RoutingService {

    private final RestTemplate restTemplate;

    public RoutingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RouteResponse calculateRoute(
            double originLat,
            double originLon,
            double destinationLat,
            double destinationLon) {

        String url = String.format(
                "https://router.project-osrm.org/route/v1/driving/" +
                        "%f,%f;%f,%f" +
                        "?overview=full&geometries=geojson",
                originLon,
                originLat,
                destinationLon,
                destinationLat
        );

        Map<String, Object> response =
                restTemplate.getForObject(url, Map.class);

        if (response == null) {
            throw new RuntimeException("No response from OSRM");
        }

        List<Map<String, Object>> routes =
                (List<Map<String, Object>>) response.get("routes");

        if (routes == null || routes.isEmpty()) {
            throw new RuntimeException("No route found");
        }

        Map<String, Object> route = routes.get(0);

        double distanceKm =
                ((Number) route.get("distance")).doubleValue()
                        / 1000.0;

        double durationMinutes =
                ((Number) route.get("duration")).doubleValue()
                        / 60.0;

        Map<String, Object> geometry =
                (Map<String, Object>) route.get("geometry");

        List<List<Double>> coordinates =
                (List<List<Double>>) geometry.get("coordinates");

        return new RouteResponse(
                distanceKm,
                durationMinutes,
                coordinates
        );
    }
}