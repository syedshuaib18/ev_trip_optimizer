package com.example.ev_roadtrip_optimizer.charging;

import com.example.ev_roadtrip_optimizer.matching.RoutePoint;
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
                        new RuntimeException(
                                "Charging station not found"));
    }

    public List<ChargingStationResponse> findNearbyStations(
            double latitude,
            double longitude,
            int radiusMeters) {

        String query = String.format("""
                [out:json];
                nwr["amenity"="charging_station"]
                (around:%d,%f,%f);
                out center;
                """,
                radiusMeters,
                latitude,
                longitude);

        return executeOverpassQuery(query);
    }

    public List<ChargingStationResponse> findStationsAroundRoute(
            List<RoutePoint> routePoints,
            int radiusMeters) {

        List<ChargingStationResponse> stations =
                new ArrayList<>();

        for (RoutePoint point : routePoints) {

            try {
                stations.addAll(
                        findNearbyStations(
                                point.getLatitude(),
                                point.getLongitude(),
                                radiusMeters)
                );

                Thread.sleep(1500);

            } catch (Exception ignored) {
                // Skip failed Overpass request
            }
        }

        List<ChargingStationResponse> uniqueStations =
                new ArrayList<>();

        for (ChargingStationResponse station : stations) {

            if (!isValidStation(station)) {
                continue;
            }

            if (isUniqueStation(station, uniqueStations)) {
                uniqueStations.add(station);
            }
        }

        return uniqueStations;
    }

    private boolean isUniqueStation(
            ChargingStationResponse station,
            List<ChargingStationResponse> existingStations) {

        for (ChargingStationResponse existing : existingStations) {

            double distanceKm =
                    calculateDistanceKm(
                            station.getLatitude(),
                            station.getLongitude(),
                            existing.getLatitude(),
                            existing.getLongitude());

            if (distanceKm < 0.05) {
                return false;
            }
        }

        return true;
    }

    private double calculateDistanceKm(
            double latitude1,
            double longitude1,
            double latitude2,
            double longitude2) {

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
                2 * Math.atan2(
                        Math.sqrt(a),
                        Math.sqrt(1 - a));

        return earthRadiusKm * c;
    }

    private List<ChargingStationResponse>
    executeOverpassQuery(String query) {

        String url =
                "https://overpass-api.de/api/interpreter";

        Map<String, Object> response =
                restTemplate.postForObject(
                        url,
                        query,
                        Map.class);

        if (response == null) {
            throw new RuntimeException(
                    "No response from OpenStreetMap");
        }

        List<Map<String, Object>> elements =
                (List<Map<String, Object>>)
                        response.get("elements");

        List<ChargingStationResponse> stations =
                new ArrayList<>();

        if (elements == null) {
            return stations;
        }

        for (Map<String, Object> element : elements) {

            Map<String, Object> tags =
                    (Map<String, Object>)
                            element.getOrDefault(
                                    "tags",
                                    Map.of());

            double latitude;
            double longitude;

            if ("node".equals(element.get("type"))) {

                latitude =
                        ((Number) element.get("lat"))
                                .doubleValue();

                longitude =
                        ((Number) element.get("lon"))
                                .doubleValue();

            } else {

                Map<String, Object> center =
                        (Map<String, Object>)
                                element.get("center");

                if (center == null) {
                    continue;
                }

                latitude =
                        ((Number) center.get("lat"))
                                .doubleValue();

                longitude =
                        ((Number) center.get("lon"))
                                .doubleValue();
            }

            Object nameObject = tags.get("name");

            if (nameObject == null) {
                continue;
            }

            String name = nameObject.toString().trim();

            if (name.isEmpty()) {
                continue;
            }

            String chargerType =
                    getChargerType(tags);

            Double chargingSpeed =
                    getChargingSpeed(tags);

            stations.add(
                    new ChargingStationResponse(
                            name,
                            latitude,
                            longitude,
                            chargerType,
                            chargingSpeed
                    )
            );
        }

        return stations;
    }

    private String getChargerType(
            Map<String, Object> tags) {

        if (tags.containsKey("socket:ccs")) {
            return "CCS";
        }

        if (tags.containsKey("socket:type2")) {
            return "Type 2";
        }

        if (tags.containsKey("socket:chademo")) {
            return "CHAdeMO";
        }

        if (tags.containsKey("socket:type1")) {
            return "Type 1";
        }

        if (tags.containsKey("socket:tesla_supercharger")) {
            return "Tesla Supercharger";
        }

        return "Unknown";
    }

    private Double getChargingSpeed(
            Map<String, Object> tags) {

        String[] speedTags = {
                "socket:output",
                "socket:ccs:output",
                "socket:type2:output",
                "socket:type2_combo:output"
        };

        for (String tag : speedTags) {

            Object value = tags.get(tag);

            if (value == null) {
                continue;
            }

            try {
                String speed =
                        value.toString()
                                .replace(" kW", "")
                                .replace("kW", "")
                                .trim();

                return Double.parseDouble(speed);

            } catch (NumberFormatException ignored) {
            }
        }

        return null;
    }

    private boolean isValidStation(
            ChargingStationResponse station) {

        return station.getName() != null
                && !station.getName().isBlank();
    }

    private boolean isUniqueStation(
            ChargingStationResponse station) {

        return true;
    }
}