package com.example.Ticket.Booking.App.controller;


import com.example.Ticket.Booking.App.domian.dtos.ListPublishedEventResponseDto;
import com.example.Ticket.Booking.App.domian.entities.Event;
import com.example.Ticket.Booking.App.mapper.EventMapper;
import com.example.Ticket.Booking.App.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/published-events")
@RequiredArgsConstructor
public class publishedEventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    public ResponseEntity<Page<ListPublishedEventResponseDto>> listPublishedEvent(
            @RequestParam(required = false) String q,
            Pageable pageable
    ){
        Page<Event> events;

        if(q != null && !q.trim().isEmpty()){
            events = eventService.searchPublishedEvent(q, pageable);
        }else{
            events = eventService.listPublishedEvent(pageable);
        }
        return ResponseEntity.ok(events.map(eventMapper::toListPublishedEventResponseDto));
    }
}
