package com.parkinglot.model;

import java.util.concurrent.atomic.AtomicBoolean;

public class ParkingSpot {
    private String id;
    private int floorNumber;
    private int number;
    private SpotType type;
    private AtomicBoolean isFree;
    private Vehicle currentVehicle;

    public ParkingSpot(String id, int floorNumber, int number, SpotType type) {
        this.id = id;
        this.floorNumber = floorNumber;
        this.number = number;
        this.type = type;
        this.isFree = new AtomicBoolean(true);
    }

    public boolean isFree() {
        return isFree.get();
    }

    public synchronized void assignVehicle(Vehicle vehicle) {
        this.currentVehicle = vehicle;
        this.isFree.set(false);
    }

    public synchronized void removeVehicle() {
        this.currentVehicle = null;
        this.isFree.set(true);
    }

    public String getId() {
        return id;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public int getNumber() {
        return number;
    }

    public SpotType getType() {
        return type;
    }

    public Vehicle getCurrentVehicle() {
        return currentVehicle;
    }
}
