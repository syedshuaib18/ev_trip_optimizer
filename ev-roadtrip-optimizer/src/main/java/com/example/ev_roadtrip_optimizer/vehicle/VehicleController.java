package com.example.ev_roadtrip_optimizer.vehicle;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicles")

public class VehicleController {
    private final VehicleService vehicleService;
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }    @PostMapping
                public Vehicle createVehicle(@RequestBody Vehicle vehicle){
            return vehicleService.saveVehicle(vehicle);
        }
        @GetMapping("/{id}")
    public Vehicle getVehicle(@PathVariable Long id){
        return vehicleService.getVehicleById(id);
        }
        @GetMapping
    public List<Vehicle>getAllVehicles(){
        return vehicleService.getAllVehicles();
    }
@PutMapping("/{id}")
public Vehicle updateVehicle(@PathVariable Long id,@RequestBody Vehicle vehicle){
    return vehicleService.updateVehicle(id,vehicle);}

    @DeleteMapping("/{id}")
    public void deleteVehicle(@PathVariable Long id){
    vehicleService.deleteVehicle(id);
}
}
