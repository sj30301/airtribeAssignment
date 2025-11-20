package com.parkinglot.strategy;

import com.parkinglot.model.SpotType;
import com.parkinglot.model.Ticket;
import java.time.Duration;

public class HourlyFeeStrategy implements FeeCalculationStrategy {

    private static final double MOTORCYCLE_RATE = 10.0;
    private static final double COMPACT_RATE = 20.0;
    private static final double LARGE_RATE = 30.0;

    @Override
    public double calculateFee(Ticket ticket) {
        Duration duration = Duration.between(ticket.getEntryTime(), ticket.getExitTime());
        long hours = duration.toHours();
        if (duration.toMinutes() % 60 != 0) {
            hours++;
        }
        if (hours == 0) hours = 1; // Minimum 1 hour

        SpotType spotType = ticket.getSpot().getType();
        switch (spotType) {
            case MOTORCYCLE:
                return hours * MOTORCYCLE_RATE;
            case COMPACT:
                return hours * COMPACT_RATE;
            case LARGE:
                return hours * LARGE_RATE;
            default:
                return 0.0;
        }
    }
}
