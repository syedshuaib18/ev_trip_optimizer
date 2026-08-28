package com.example.ev_roadtrip_optimizer.charging;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargingStationRepository
        extends JpaRepository<ChargingStation, Long> {
}