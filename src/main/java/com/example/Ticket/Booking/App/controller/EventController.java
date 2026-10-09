package com.example.Ticket.Booking.App.controller;

import com.example.Ticket.Booking.App.domian.CreateEventRequest;
import com.example.Ticket.Booking.App.domian.dtos.CreateEventRequestDto;
import com.example.Ticket.Booking.App.domian.dtos.CreateEventResponseDto;
import com.example.Ticket.Booking.App.domian.dtos.GetEventDetailResponseDto;
import com.example.Ticket.Booking.App.domian.dtos.ListEventResponseDto;
import com.example.Ticket.Booking.App.domian.entities.Event;
import com.example.Ticket.Booking.App.mapper.EventMapper;
import com.example.Ticket.Booking.App.repository.EventRepository;
import com.example.Ticket.Booking.App.service.impl.EventServiceImpl;
import com.sun.java.accessibility.util.GUIInitializedListener;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/event")
public class EventController {

    private final EventMapper eventMapper;
    private final EventServiceImpl eventService;

    @PostMapping
    public ResponseEntity<CreateEventResponseDto> createEvent(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateEventRequestDto createEventRequestDto){

        CreateEventRequest createEventRequest = eventMapper.fromDto(createEventRequestDto);
        UUID userId = parseUserId(jwt);

        Event createdEvent = eventService.createEvent(userId, createEventRequest);
        CreateEventResponseDto createEventResponseDto = eventMapper.toDto(createdEvent);
        return new ResponseEntity<>(createEventResponseDto, HttpStatus.CREATED);
    }
    @GetMapping
    private ResponseEntity<Page<ListEventResponseDto>> listEvent(
            @AuthenticationPrincipal Jwt jwt,
            Pageable pageable){
        UUID userId = parseUserId(jwt);

        Page<Event> events = eventService.listEventForOrganizer(userId, pageable);
        return ResponseEntity.ok(
                events.map(eventMapper::toListEventResponseDto));
    }

    @GetMapping(path = "/{eventID}")
    private ResponseEntity<GetEventDetailResponseDto> getEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventID
            ){
        UUID userId = parseUserId(jwt);
        return eventService.getEventForOrganizer(userId,eventID)
                .map(eventMapper::toGetEventDetailResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private UUID parseUserId(Jwt jwt){
        return UUID.fromString(jwt.getSubject());
    }
}
