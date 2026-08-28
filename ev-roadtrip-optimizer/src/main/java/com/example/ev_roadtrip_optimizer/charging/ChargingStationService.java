package com.example.ev_roadtrip_optimizer.charging;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ChargingStationService {

    private final ChargingStationRepository repository;
    private final RestTemplate restTemplate;

    public ChargingStationService(
            ChargingStationRepository repository,
            RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public ChargingStation saveStation(ChargingStation station) {
        return repository.save(station);
    }

    public List<ChargingStation> getAllStations() {
        return repository.findAll();
    }

    public ChargingStation getStationById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Charging station not found"));
    }

    public List<ChargingStationResponse> findNearbyStations(
            double latitude,
            double longitude,
            int radiusMeters) {

        String query = String.format("""
                [out:json];
                (
                  node["amenity"="charging_station"](around:%d,%f,%f);
                  way["amenity"="charging_station"](around:%d,%f,%f);
                  relation["amenity"="charging_station"](around:%d,%f,%f);
                );
                out center;
                """,
                radiusMeters, latitude, longitude,
                radiusMeters, latitude, longitude,
                radiusMeters, latitude, longitude);

        String url = "https://overpass-api.de/api/interpreter";

        Map<String, Object> response =
                restTemplate.postForObject(url, query, Map.class);

        if (response == null) {
            throw new RuntimeException("No response from OpenStreetMap");
        }

        List<Map<String, Object>> elements =
                (List<Map<String, Object>>) response.get("elements");

        List<ChargingStationResponse> stations = new ArrayList<>();

        for (Map<String, Object> element : elements) {

            Map<String, Object> tags =
                    (Map<String, Object>) element.get("tags");

            if (tags == null) {
                tags = Map.of();
            }

            double lat;
            double lon;

            if ("node".equals(element.get("type"))) {
                lat = ((Number) element.get("lat")).doubleValue();
                lon = ((Number) element.get("lon")).doubleValue();
            } else {
                Map<String, Object> center =
                        (Map<String, Object>) element.get("center");

                if (center == null) {
                    continue;
                }

                lat = ((Number) center.get("lat")).doubleValue();
                lon = ((Number) center.get("lon")).doubleValue();
            }

            String name = (String) tags.getOrDefault(
                    "name", "Unnamed Charging Station");

            String chargerType = (String) tags.getOrDefault(
                    "socket:type2_combo", "Unknown");

            Double chargingSpeed = null;

            Object speed =
                    tags.get("socket:type2_combo:output");

            if (speed != null) {
                try {
                    chargingSpeed = Double.parseDouble(
                            speed.toString());
                } catch (NumberFormatException ignored) {
                }
            }

            stations.add(new ChargingStationResponse(
                    name,
                    lat,
                    lon,
                    chargerType,
                    chargingSpeed
            ));
        }

        return stations;
    }
}