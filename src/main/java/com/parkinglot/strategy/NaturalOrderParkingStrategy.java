package com.parkinglot.strategy;

import com.parkinglot.model.ParkingFloor;
import com.parkinglot.model.ParkingLot;
import com.parkinglot.model.ParkingSpot;
import com.parkinglot.model.SpotType;
import com.parkinglot.model.VehicleType;
import java.util.List;
import java.util.Optional;

public class NaturalOrderParkingStrategy implements ParkingStrategy {

    @Override
    public Optional<ParkingSpot> findSpot(VehicleType vehicleType) {
        ParkingLot parkingLot = ParkingLot.getInstance();
        for (ParkingFloor floor : parkingLot.getFloors()) {
            for (ParkingSpot spot : floor.getSpots()) {
                if (spot.isFree() && isSuitable(spot, vehicleType)) {
                    return Optional.of(spot);
                }
            }
        }
        return Optional.empty();
    }

    private boolean isSuitable(ParkingSpot spot, VehicleType vehicleType) {
        switch (vehicleType) {
            case MOTORCYCLE:
                return spot.getType() == SpotType.MOTORCYCLE || spot.getType() == SpotType.COMPACT || spot.getType() == SpotType.LARGE;
            case CAR:
                return spot.getType() == SpotType.COMPACT || spot.getType() == SpotType.LARGE;
            case BUS:
                return spot.getType() == SpotType.LARGE;
            default:
                return false;
        }
    }
}
