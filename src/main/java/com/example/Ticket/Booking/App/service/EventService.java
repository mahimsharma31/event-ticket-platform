package com.example.Ticket.Booking.App.service;

import com.example.Ticket.Booking.App.domian.CreateEventRequest;
import com.example.Ticket.Booking.App.domian.UpdateEventRequest;
import com.example.Ticket.Booking.App.domian.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface EventService {
    Event createEvent(UUID organizerID, CreateEventRequest request);
    Page<Event> listEventForOrganizer(UUID organizerID, Pageable pageable);
    Optional<Event> getEventForOrganizer(UUID organizerID, UUID id);
    Event updateEventForOrganizer(UUID organizerId, UUID id, UpdateEventRequest event);
    void deleteEventForOrganizer(UUID organizerId, UUID id);
    Page<Event> listPublishedEvent(Pageable pageable);
    Page<Event> searchPublishedEvent(String query, Pageable pageable);
    Optional<Event> getPublishedEvent(UUID id);
}
