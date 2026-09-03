package com.example.ev_roadtrip_optimizer.matching;

public class RestaurantChargingMatchResponse {
    private String restaurantName;
    private double restaurantLatitude;
    private double restaurantLongitude;
    private String cuisine;
    private String chargingStationName;
    private double chargingStationLatitude;
    private double chargingStationLongitude;
    private double distanceKm;

    public RestaurantChargingMatchResponse(
            String restaurantName, double restaurantLatitude, double restaurantLongitude,
            String cuisine, String chargingStationName,
            double chargingStationLatitude, double chargingStationLongitude,
            double distanceKm) {
        this.restaurantName = restaurantName;
        this.restaurantLatitude = restaurantLatitude;
        this.restaurantLongitude = restaurantLongitude;
        this.cuisine = cuisine;
        this.chargingStationName = chargingStationName;
        this.chargingStationLatitude = chargingStationLatitude;
        this.chargingStationLongitude = chargingStationLongitude;
        this.distanceKm = distanceKm;
    }

    public String getRestaurantName() { return restaurantName; }
    public double getRestaurantLatitude() { return restaurantLatitude; }
    public double getRestaurantLongitude() { return restaurantLongitude; }
    public String getCuisine() { return cuisine; }
    public String getChargingStationName() { return chargingStationName; }
    public double getChargingStationLatitude() { return chargingStationLatitude; }
    public double getChargingStationLongitude() { return chargingStationLongitude; }
    public double getDistanceKm() { return distanceKm; }
}