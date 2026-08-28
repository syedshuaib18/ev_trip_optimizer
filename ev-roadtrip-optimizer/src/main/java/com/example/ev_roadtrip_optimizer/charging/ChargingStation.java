package com.example.ev_roadtrip_optimizer.charging;

import jakarta.persistence.*;

@Entity
public class ChargingStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double latitude;
    private Double longitude;
    private String chargerType;
    private Double chargingSpeedKw;
    private Double pricePerKwh;

    public ChargingStation() {}

    public ChargingStation(String name, Double latitude, Double longitude,
                           String chargerType, Double chargingSpeedKw,
                           Double pricePerKwh) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.chargerType = chargerType;
        this.chargingSpeedKw = chargingSpeedKw;
        this.pricePerKwh = pricePerKwh;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getChargerType() { return chargerType; }
    public void setChargerType(String chargerType) { this.chargerType = chargerType; }

    public Double getChargingSpeedKw() { return chargingSpeedKw; }
    public void setChargingSpeedKw(Double chargingSpeedKw) { this.chargingSpeedKw = chargingSpeedKw; }

    public Double getPricePerKwh() { return pricePerKwh; }
    public void setPricePerKwh(Double pricePerKwh) { this.pricePerKwh = pricePerKwh; }
}