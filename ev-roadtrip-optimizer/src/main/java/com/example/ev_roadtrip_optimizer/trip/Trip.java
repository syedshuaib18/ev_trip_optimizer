package com.example.ev_roadtrip_optimizer.trip;
import jakarta.persistence.*;
@Entity
public class Trip {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long vehicleId;
    private String  origin;
    private String destination;

    private Double originLatitude, originLongitude;
    private Double destinationLatitude,destinationLongitude;


    public Trip(){

    }
    public Trip(Long vehicleId, String origin, String destination,Double originLatitude,Double originLongitude
    ,Double destinationLatitude, Double destinationLongitude){
        this.vehicleId=vehicleId;
        this.origin=origin;
        this.destination=destination;
        this.originLatitude=originLatitude;
        this.originLongitude=originLongitude;
        this.destinationLongitude=destinationLongitude;
        this.destinationLatitude=destinationLatitude;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getVehicleId() { return vehicleId; }
    public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public Double getOriginLatitude() { return originLatitude; }
    public void setOriginLatitude(Double originLatitude) { this.originLatitude = originLatitude; }

    public Double getOriginLongitude() { return originLongitude; }
    public void setOriginLongitude(Double originLongitude) { this.originLongitude = originLongitude; }

    public Double getDestinationLatitude() { return destinationLatitude; }
    public void setDestinationLatitude(Double destinationLatitude) { this.destinationLatitude = destinationLatitude; }

    public Double getDestinationLongitude() { return destinationLongitude; }
    public void setDestinationLongitude(Double destinationLongitude) { this.destinationLongitude = destinationLongitude; }
}