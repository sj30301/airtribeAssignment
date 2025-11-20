package com.parkinglot.service;

import com.parkinglot.exception.InvalidTicketException;
import com.parkinglot.exception.ParkingFullException;
import com.parkinglot.model.ParkingSpot;
import com.parkinglot.model.Ticket;
import com.parkinglot.model.Vehicle;
import com.parkinglot.strategy.FeeCalculationStrategy;
import com.parkinglot.strategy.ParkingStrategy;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class ParkingLotService {
    private ParkingStrategy parkingStrategy;
    private FeeCalculationStrategy feeCalculationStrategy;
    private Repository repository;
    private final ReentrantLock lock = new ReentrantLock();

    public ParkingLotService(ParkingStrategy parkingStrategy, FeeCalculationStrategy feeCalculationStrategy, Repository repository) {
        this.parkingStrategy = parkingStrategy;
        this.feeCalculationStrategy = feeCalculationStrategy;
        this.repository = repository;
    }

    public Ticket entry(Vehicle vehicle) {
        lock.lock();
        try {
            Optional<ParkingSpot> spotOptional = parkingStrategy.findSpot(vehicle.getType());
            if (spotOptional.isPresent()) {
                ParkingSpot spot = spotOptional.get();
                spot.assignVehicle(vehicle);
                
                String ticketId = UUID.randomUUID().toString();
                Ticket ticket = new Ticket(ticketId, vehicle.getLicensePlate(), spot, LocalDateTime.now());
                repository.saveTicket(ticket);
                return ticket;
            } else {
                throw new ParkingFullException("No spot available for vehicle type: " + vehicle.getType());
            }
        } finally {
            lock.unlock();
        }
    }

    public Ticket exit(String ticketId) {
        Ticket ticket = repository.getTicket(ticketId);
        if (ticket == null) {
            throw new InvalidTicketException("Invalid ticket ID: " + ticketId);
        }
        
        // In a real scenario, we might want to lock here too if multiple exits for same ticket are possible
        // But for simplicity, we assume one exit per ticket.
        // However, we need to ensure spot release is thread-safe, which ParkingSpot handles.
        
        ticket.setExitTime(LocalDateTime.now());
        double amount = feeCalculationStrategy.calculateFee(ticket);
        ticket.setAmount(amount);
        ticket.setPaid(true); // Assuming auto-payment
        
        ticket.getSpot().removeVehicle();
        
        return ticket;
    }
}
