package com.example.Ticket.Booking.App.controller;

import com.example.Ticket.Booking.App.domian.CreateEventRequest;
import com.example.Ticket.Booking.App.domian.dtos.CreateEventRequestDto;
import com.example.Ticket.Booking.App.domian.dtos.CreateEventResponseDto;
import com.example.Ticket.Booking.App.domian.entities.Event;
import com.example.Ticket.Booking.App.mapper.EventMapper;
import com.example.Ticket.Booking.App.repository.EventRepository;
import com.example.Ticket.Booking.App.service.impl.EventServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        UUID userId = UUID.fromString(jwt.getSubject());

        Event createdEvent = eventService.createEvent(userId, createEventRequest);
        CreateEventResponseDto createEventResponseDto = eventMapper.toDto(createdEvent);
        return new ResponseEntity<>(createEventResponseDto, HttpStatus.CREATED);
    }
}
