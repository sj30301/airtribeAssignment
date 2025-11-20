package com.parkinglot.strategy;

import com.parkinglot.model.Ticket;

public interface FeeCalculationStrategy {
    double calculateFee(Ticket ticket);
}
