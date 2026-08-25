package com.example.ev_roadtrip_optimizer.vehicle;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String make;
    private String model;
    private double batteryCapacityKwh;
    private double consumptionKwhPer100Km;
    private double currentBatteryPercentage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getBatteryCapacityKwh() {
        return batteryCapacityKwh;
    }

    public void setBatteryCapacityKwh(double batteryCapacityKwh) {
        this.batteryCapacityKwh = batteryCapacityKwh;
    }

    public double getConsumptionKwhPer100Km() {
        return consumptionKwhPer100Km;
    }

    public void setConsumptionKwhPer100Km(double consumptionKwhPer100Km) {
        this.consumptionKwhPer100Km = consumptionKwhPer100Km;
    }

    public double getCurrentBatteryPercentage() {
        return currentBatteryPercentage;
    }

    public void setCurrentBatteryPercentage(double currentBatteryPercentage) {
        this.currentBatteryPercentage = currentBatteryPercentage;
    }



    public Vehicle(){}
    public Vehicle(String make,String model,double batteryCapacityKwh,
                   double consumptionKwhPer100Km,double currentBatteryPercentage){
        this.make=make;
        this.model=model;
        this.batteryCapacityKwh=batteryCapacityKwh;
        this.consumptionKwhPer100Km=consumptionKwhPer100Km;
        this.currentBatteryPercentage=currentBatteryPercentage;

    }





}
