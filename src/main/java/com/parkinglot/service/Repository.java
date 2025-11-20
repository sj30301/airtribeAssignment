package com.parkinglot.service;

import com.parkinglot.model.Ticket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Repository {
    private Map<String, Ticket> tickets = new ConcurrentHashMap<>();

    public void saveTicket(Ticket ticket) {
        tickets.put(ticket.getId(), ticket);
    }

    public Ticket getTicket(String id) {
        return tickets.get(id);
    }
}
