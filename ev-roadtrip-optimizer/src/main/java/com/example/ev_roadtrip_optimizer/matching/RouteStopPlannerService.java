package com.example.ev_roadtrip_optimizer.matching;

import com.example.ev_roadtrip_optimizer.charging.ChargingStationResponse;
import com.example.ev_roadtrip_optimizer.charging.ChargingStationService;
import com.example.ev_roadtrip_optimizer.routing.RouteResponse;
import com.example.ev_roadtrip_optimizer.routing.RoutingService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteStopPlannerService {

    private final RoutingService routingService;
    private final ChargingStationService chargingStationService;

    public RouteStopPlannerService(
            RoutingService routingService,
            ChargingStationService chargingStationService) {

        this.routingService = routingService;
        this.chargingStationService = chargingStationService;
    }

    public List<RoutePoint> generateRoutePoints(
            double originLat,
            double originLon,
            double destinationLat,
            double destinationLon,
            int numberOfStops) {

        RouteResponse route =
                routingService.calculateRoute(
                        originLat,
                        originLon,
                        destinationLat,
                        destinationLon);

        List<List<Double>> coordinates =
                route.getCoordinates();

        List<RoutePoint> points = new ArrayList<>();

        double totalDistance = 0.0;
        double nextStopDistance =
                route.getDistanceKm()
                        / (numberOfStops + 1);

        for (int i = 1; i < coordinates.size(); i++) {

            List<Double> previous =
                    coordinates.get(i - 1);

            List<Double> current =
                    coordinates.get(i);

            double previousLon = previous.get(0);
            double previousLat = previous.get(1);

            double currentLon = current.get(0);
            double currentLat = current.get(1);

            totalDistance += calculateDistanceKm(
                    previousLat,
                    previousLon,
                    currentLat,
                    currentLon);

            if (totalDistance >= nextStopDistance
                    && points.size() < numberOfStops) {

                points.add(
                        new RoutePoint(
                                currentLat,
                                currentLon,
                                totalDistance
                        )
                );

                nextStopDistance +=
                        route.getDistanceKm()
                                / (numberOfStops + 1);
            }
        }

        return points;
    }

    public List<RouteChargingStation>
    findChargingStationsAlongRoute(
            double originLat,
            double originLon,
            double destinationLat,
            double destinationLon,
            int numberOfStops,
            int radiusMeters) {

        List<RoutePoint> routePoints =
                generateRoutePoints(
                        originLat,
                        originLon,
                        destinationLat,
                        destinationLon,
                        numberOfStops);

        List<ChargingStationResponse> stations =
                chargingStationService
                        .findStationsAroundRoute(
                                routePoints,
                                radiusMeters);

        List<RouteChargingStation> result =
                new ArrayList<>();

        for (RoutePoint point : routePoints) {

            for (ChargingStationResponse station :
                    stations) {

                double distanceKm =
                        calculateDistanceKm(
                                point.getLatitude(),
                                point.getLongitude(),
                                station.getLatitude(),
                                station.getLongitude());

                if (distanceKm <=
                        radiusMeters / 1000.0) {

                    result.add(
                            new RouteChargingStation(
                                    point.getLatitude(),
                                    point.getLongitude(),
                                    point.getDistanceFromOriginKm(),
                                    station.getName(),
                                    station.getLatitude(),
                                    station.getLongitude(),
                                    station.getChargerType(),
                                    station.getChargingSpeedKw()
                            )
                    );
                }
            }
        }

        return result;
    }

    private double calculateDistanceKm(
            double latitude1,
            double longitude1,
            double latitude2,
            double longitude2) {

        double earthRadiusKm = 6371.0;

        double latDifference =
                Math.toRadians(
                        latitude2 - latitude1);

        double lonDifference =
                Math.toRadians(
                        longitude2 - longitude1);

        double a =
                Math.sin(latDifference / 2)
                        * Math.sin(latDifference / 2)
                        + Math.cos(
                        Math.toRadians(latitude1))
                        * Math.cos(
                        Math.toRadians(latitude2))
                        * Math.sin(lonDifference / 2)
                        * Math.sin(lonDifference / 2);

        double c =
                2 * Math.atan2(
                        Math.sqrt(a),
                        Math.sqrt(1 - a));

        return earthRadiusKm * c;
    }
}