package com.example.ev_roadtrip_optimizer.restaurant;

public class RestaurantResponse {

    private String name;
    private double latitude;
    private double longitude;
    private String cuisine;

    public RestaurantResponse(String name, double latitude,
                              double longitude, String cuisine) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cuisine = cuisine;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCuisine() {
        return cuisine;
    }
}