package com.example.Ticket.Booking.App.service;

import com.example.Ticket.Booking.App.domian.entities.Ticket;

import java.util.UUID;

public interface TicketTypeService {
    Ticket purchaseTicket(UUID userId, UUID ticketTypeId);
}
