package com.example.ev_roadtrip_optimizer.restaurant;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class RestaurantService {

    private final RestaurantRepository repository;
    private final RestTemplate restTemplate;

    public RestaurantService(RestaurantRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public Restaurant saveRestaurant(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    public List<Restaurant> getAllRestaurants() {
        return repository.findAll();
    }

    public Restaurant getRestaurantById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
    }

    public List<RestaurantResponse> findNearbyRestaurants(
            double latitude, double longitude, int radiusMeters) {

        String query = String.format("""
                [out:json];
                nwr["amenity"="restaurant"](around:%d,%f,%f);
                out center;
                """, radiusMeters, latitude, longitude);

        String url = "https://overpass-api.de/api/interpreter";

        Map<String, Object> response =
                restTemplate.postForObject(url, query, Map.class);

        if (response == null) {
            throw new RuntimeException("No response from OpenStreetMap");
        }

        List<Map<String, Object>> elements =
                (List<Map<String, Object>>) response.get("elements");

        List<RestaurantResponse> restaurants = new ArrayList<>();

        for (Map<String, Object> element : elements) {
            Map<String, Object> tags =
                    (Map<String, Object>) element.getOrDefault("tags", Map.of());

            double lat;
            double lon;

            if ("node".equals(element.get("type"))) {
                lat = ((Number) element.get("lat")).doubleValue();
                lon = ((Number) element.get("lon")).doubleValue();
            } else {
                Map<String, Object> center =
                        (Map<String, Object>) element.get("center");

                if (center == null) continue;

                lat = ((Number) center.get("lat")).doubleValue();
                lon = ((Number) center.get("lon")).doubleValue();
            }

            String name = tags.getOrDefault("name", "Unnamed Restaurant").toString();
            String cuisine = tags.getOrDefault("cuisine", "Unknown").toString();

            restaurants.add(new RestaurantResponse(
                    name, lat, lon, cuisine
            ));
        }

        return restaurants;
    }
}