package com.example.ev_roadtrip_optimizer.vehicle;
import java.util.List;
import org.springframework.stereotype.Service;
@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository){
        this.vehicleRepository=vehicleRepository;
    }

    public Vehicle saveVehicle(Vehicle vehicle){
        return vehicleRepository.save(vehicle);
    }
    public Vehicle getVehicleById(Long id){
        return vehicleRepository.findById(id)
                .orElseThrow(() ->new RuntimeException("Vehicle not found"));
    }
    public List<Vehicle>getAllVehicles(){
        return vehicleRepository.findAll();
    }
    public Vehicle updateVehicle(Long id,Vehicle vehicle){
        Vehicle existing =getVehicleById(id);
        existing.setMake(vehicle.getMake());
        existing.setModel(vehicle.getModel());
        existing.setBatteryCapacityKwh(vehicle.getBatteryCapacityKwh());
        existing.setConsumptionKwhPer100Km(vehicle.getConsumptionKwhPer100Km());
        existing.setCurrentBatteryPercentage(vehicle.getCurrentBatteryPercentage());
        return vehicleRepository.save(existing);
    }
    public void deleteVehicle(Long id){
    Vehicle vehicle = getVehicleById(id);
    vehicleRepository.delete(vehicle);
}
}