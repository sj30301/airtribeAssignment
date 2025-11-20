package com.parkinglot.strategy;

import com.parkinglot.model.ParkingSpot;
import com.parkinglot.model.VehicleType;
import java.util.Optional;

public interface ParkingStrategy {
    Optional<ParkingSpot> findSpot(VehicleType vehicleType);
}
