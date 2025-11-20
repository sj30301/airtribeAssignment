package com.parkinglot.model;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private String id;
    private String name;
    private String address;
    private List<ParkingFloor> floors;

    private static ParkingLot instance = null;

    private ParkingLot(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.floors = new ArrayList<>();
    }

    public static synchronized ParkingLot getInstance(String id, String name, String address) {
        if (instance == null) {
            instance = new ParkingLot(id, name, address);
        }
        return instance;
    }
    
    public static ParkingLot getInstance() {
        return instance;
    }
    
    public static void reset() {
        instance = null;
    }

    public void addFloor(ParkingFloor floor) {
        floors.add(floor);
    }

    public List<ParkingFloor> getFloors() {
        return floors;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
