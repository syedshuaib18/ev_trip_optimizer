package com.example.ev_roadtrip_optimizer.restaurant;

import jakarta.persistence.*;

@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double latitude;
    private Double longitude;
    private String cuisine;
    private Double rating;
    private String priceRange;

    public Restaurant() {}

    public Restaurant(String name, Double latitude, Double longitude,
                      String cuisine, Double rating, String priceRange) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cuisine = cuisine;
        this.rating = rating;
        this.priceRange = priceRange;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getCuisine() { return cuisine; }
    public void setCuisine(String cuisine) { this.cuisine = cuisine; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public String getPriceRange() { return priceRange; }
}