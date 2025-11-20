package com.parkinglot.model;

import java.time.LocalDateTime;

public class Ticket {
    private String id;
    private String vehicleNumber;
    private ParkingSpot spot;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double amount;
    private boolean isPaid;

    public Ticket(String id, String vehicleNumber, ParkingSpot spot, LocalDateTime entryTime) {
        this.id = id;
        this.vehicleNumber = vehicleNumber;
        this.spot = spot;
        this.entryTime = entryTime;
        this.isPaid = false;
    }

    public String getId() {
        return id;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public ParkingSpot getSpot() {
        return spot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }
}
