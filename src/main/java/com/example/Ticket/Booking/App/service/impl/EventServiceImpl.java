package com.example.Ticket.Booking.App.service.impl;

import com.example.Ticket.Booking.App.domian.CreateEventRequest;
import com.example.Ticket.Booking.App.domian.UpdateEventRequest;
import com.example.Ticket.Booking.App.domian.UpdateTicketTypeRequest;
import com.example.Ticket.Booking.App.domian.entities.Event;
import com.example.Ticket.Booking.App.domian.entities.EventStatusEnum;
import com.example.Ticket.Booking.App.domian.entities.TicketType;
import com.example.Ticket.Booking.App.domian.entities.User;


import com.example.Ticket.Booking.App.exception.EventNotFoundException;
import com.example.Ticket.Booking.App.exception.EventUpdateException;
import com.example.Ticket.Booking.App.exception.TicketTypeNotFoundException;
import com.example.Ticket.Booking.App.exception.UserNotFoundException;
import com.example.Ticket.Booking.App.repository.EventRepository;
import com.example.Ticket.Booking.App.repository.UserRepository;
import com.example.Ticket.Booking.App.service.EventService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
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

    @Override
    @Transactional
    public Event updateEventForOrganizer(UUID organizerId, UUID id, UpdateEventRequest event) {
        if(event.getId() == null){
            throw new EventUpdateException("Event Id can not be null");
        }
        if(!id.equals(event.getId())){
            throw new EventUpdateException("Cannot update the Id of event");
        }

        Event existingEvent = eventRepository
                .findByIdAndOrganizerId(id, organizerId)
                .orElseThrow(() -> new EventNotFoundException(
                        String.format("Event with Id %s not exist", id))
                );

        existingEvent.setName(event.getName());
        existingEvent.setStart(event.getStart());
        existingEvent.setEnd(event.getEnd());
        existingEvent.setVenue(event.getVenue());
        existingEvent.setSaleStart(event.getSalesStart());
        existingEvent.setSaleEnd(event.getSalesEnd());
        existingEvent.setStatus(event.getStatus());

        Set<UUID> requestTicketTypeId = event.getTicketTypes()
                .stream()
                .map(UpdateTicketTypeRequest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        existingEvent.getTicketTypes().removeIf(
                ticketType -> !requestTicketTypeId.contains(ticketType.getId())
        );

        Map<UUID, TicketType> existingTicketTypeIndex = existingEvent.getTicketTypes()
                .stream()
                .collect(Collectors.toMap(TicketType::getId, Function.identity()));

        for(UpdateTicketTypeRequest ticketType : event.getTicketTypes()){
            if(ticketType.getId() == null){
                TicketType ticketTypeToCreate = new TicketType();
                ticketTypeToCreate.setName(ticketType.getName());
                ticketTypeToCreate.setPrice(ticketType.getPrice());
                ticketTypeToCreate.setDescription(ticketType.getDescription());
                ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());
                ticketTypeToCreate.setEvent(existingEvent);
                existingEvent.getTicketTypes().add(ticketTypeToCreate);
            }else if(existingTicketTypeIndex.containsKey(ticketType.getId())){
                TicketType ticketTypeToUpdate = existingTicketTypeIndex.get(ticketType.getId());
                ticketTypeToUpdate.setName(ticketType.getName());
                ticketTypeToUpdate.setPrice(ticketType.getPrice());
                ticketTypeToUpdate.setDescription(ticketType.getDescription());
                ticketTypeToUpdate.setTotalAvailable(ticketType.getTotalAvailable());
            }else{
                throw new TicketTypeNotFoundException(
                        String.format("Ticket Type with '%s' not exist",ticketType.getId())
                );
            }
        }
        return eventRepository.save(existingEvent);
    }

    @Override
    public void deleteEventForOrganizer(UUID organizerId, UUID id) {
        getEventForOrganizer(organizerId,id).ifPresent(eventRepository::delete);
    }




}
