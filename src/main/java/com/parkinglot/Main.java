package com.parkinglot;

import com.parkinglot.model.ParkingFloor;
import com.parkinglot.model.ParkingLot;
import com.parkinglot.model.ParkingSpot;
import com.parkinglot.model.SpotType;
import com.parkinglot.model.Ticket;
import com.parkinglot.model.Vehicle;
import com.parkinglot.model.VehicleType;
import com.parkinglot.service.ParkingLotService;
import com.parkinglot.service.Repository;
import com.parkinglot.strategy.HourlyFeeStrategy;
import com.parkinglot.strategy.NaturalOrderParkingStrategy;

public class Main {
    public static void main(String[] args) {
        // Initialize Parking Lot
        ParkingLot parkingLot = ParkingLot.getInstance("PL1", "Downtown Parking", "123 Main St");

        // Add Floors and Spots
        ParkingFloor floor1 = new ParkingFloor(1);
        floor1.addSpot(new ParkingSpot("1-1", 1, 1, SpotType.MOTORCYCLE));
        floor1.addSpot(new ParkingSpot("1-2", 1, 2, SpotType.COMPACT));
        floor1.addSpot(new ParkingSpot("1-3", 1, 3, SpotType.LARGE));
        parkingLot.addFloor(floor1);

        // Initialize Service
        Repository repository = new Repository();
        ParkingLotService service = new ParkingLotService(new NaturalOrderParkingStrategy(), new HourlyFeeStrategy(),
                repository);

        try {
            // 1. Park a Car
            Vehicle car = new Vehicle("CAR-123", VehicleType.CAR) {
            };
            Ticket ticket1 = service.entry(car);
            System.out.println("Parked Car: " + ticket1.getVehicleNumber() + " at Spot: " + ticket1.getSpot().getId());

            // 2. Park a Motorcycle
            Vehicle bike = new Vehicle("BIKE-456", VehicleType.MOTORCYCLE) {
            };
            Ticket ticket2 = service.entry(bike);
            System.out.println("Parked Bike: " + ticket2.getVehicleNumber() + " at Spot: " + ticket2.getSpot().getId());

            // 3. Unpark Car
            Ticket exitTicket1 = service.exit(ticket1.getId());
            System.out.println("Unparked Car: " + exitTicket1.getVehicleNumber() + ", Fee: " + exitTicket1.getAmount());

            // 4. Unpark Bike
            Ticket exitTicket2 = service.exit(ticket2.getId());
            System.out
                    .println("Unparked Bike: " + exitTicket2.getVehicleNumber() + ", Fee: " + exitTicket2.getAmount());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
