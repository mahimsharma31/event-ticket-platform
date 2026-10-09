package com.example.Ticket.Booking.App.service.impl;

import com.example.Ticket.Booking.App.domian.CreateEventRequest;
import com.example.Ticket.Booking.App.domian.entities.Event;
import com.example.Ticket.Booking.App.domian.entities.TicketType;
import com.example.Ticket.Booking.App.domian.entities.User;


import com.example.Ticket.Booking.App.exception.UserNotFoundException;
import com.example.Ticket.Booking.App.repository.EventRepository;
import com.example.Ticket.Booking.App.repository.UserRepository;
import com.example.Ticket.Booking.App.service.EventService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public Event createEvent(UUID organizerID, CreateEventRequest event) {
        User organizer = userRepository.findById(organizerID).orElseThrow(() -> new UserNotFoundException(
                String.format("User with ID '%s' not found", organizerID)
        ));

        Event eventToCreate = new Event();

        List<TicketType> ticketTypesToCreate = event.getTicketTypes().stream().map(
                ticketType -> {
                    TicketType ticketTypeToCreate = new TicketType();
                    ticketTypeToCreate.setName(ticketType.getName());
                    ticketTypeToCreate.setPrice(ticketType.getPrice());
                    ticketTypeToCreate.setDescription(ticketType.getDescription());
                    ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());
                    ticketTypeToCreate.setEvent(eventToCreate);
                    return ticketTypeToCreate;
                }
        ).toList();


        eventToCreate.setName(event.getName());
        eventToCreate.setStart(event.getStart());
        eventToCreate.setEnd(event.getEnd());
        eventToCreate.setVenue(event.getVenue());
        eventToCreate.setSaleStart(event.getSalesStart());
        eventToCreate.setSaleEnd(event.getSalesStart());
        eventToCreate.setStatus(event.getStatus());
        eventToCreate.setOrganizer(organizer);
        eventToCreate.setTicketTypes(ticketTypesToCreate);

        return eventRepository.save(eventToCreate);
    }

    @Override
    public Page<Event> listEventForOrganizer(UUID organizerID, Pageable pageable) {
        return eventRepository.findByOrganizerId(organizerID, pageable);
    }

    @Override
    public Optional<Event> getEventForOrganizer(UUID organizerID, UUID id) {
        return eventRepository.findByIdAndOrganizerId(id, organizerID);
    }
}
