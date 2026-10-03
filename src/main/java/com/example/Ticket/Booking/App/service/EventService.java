package com.example.Ticket.Booking.App.service;

import com.example.Ticket.Booking.App.domian.CreateEventRequest;
import com.example.Ticket.Booking.App.domian.entities.Event;

import java.util.UUID;

public interface EventService {
    Event createEvent(UUID organizerID, CreateEventRequest request);
}
