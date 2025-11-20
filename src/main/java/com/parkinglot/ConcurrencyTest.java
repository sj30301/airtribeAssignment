package com.parkinglot;

import com.parkinglot.model.ParkingFloor;
import com.parkinglot.model.ParkingLot;
import com.parkinglot.model.ParkingSpot;
import com.parkinglot.model.SpotType;
import com.parkinglot.model.Vehicle;
import com.parkinglot.model.VehicleType;
import com.parkinglot.service.ParkingLotService;
import com.parkinglot.service.Repository;
import com.parkinglot.strategy.HourlyFeeStrategy;
import com.parkinglot.strategy.NaturalOrderParkingStrategy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrencyTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting Concurrency Test...");
        
        // Reset Singleton
        ParkingLot.reset();
        ParkingLot parkingLot = ParkingLot.getInstance("PL1", "Test Lot", "Test Addr");
        
        // 1 Floor, 10 Spots (All Compact)
        ParkingFloor floor = new ParkingFloor(1);
        for (int i = 1; i <= 10; i++) {
            floor.addSpot(new ParkingSpot("1-" + i, 1, i, SpotType.COMPACT));
        }
        parkingLot.addFloor(floor);
        
        Repository repository = new Repository();
        ParkingLotService service = new ParkingLotService(new NaturalOrderParkingStrategy(), new HourlyFeeStrategy(), repository);
        
        // Try to park 20 cars simultaneously
        int numberOfThreads = 20;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failureCount = new AtomicInteger(0);
        
        for (int i = 0; i < numberOfThreads; i++) {
            final int id = i;
            executor.submit(() -> {
                try {
                    Vehicle car = new Vehicle("CAR-" + id, VehicleType.CAR) {};
                    service.entry(car);
                    successCount.incrementAndGet();
                    System.out.println("Thread " + id + ": Parked successfully.");
                } catch (Exception e) {
                    failureCount.incrementAndGet();
                    System.out.println("Thread " + id + ": Failed to park (" + e.getMessage() + ")");
                }
            });
        }
        
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        
        System.out.println("Test Finished.");
        System.out.println("Successful Parks: " + successCount.get());
        System.out.println("Failed Parks: " + failureCount.get());
        
        if (successCount.get() == 10 && failureCount.get() == 10) {
            System.out.println("SUCCESS: Exactly 10 cars parked, 10 rejected.");
        } else {
            System.out.println("FAILURE: Unexpected counts.");
        }
    }
}
