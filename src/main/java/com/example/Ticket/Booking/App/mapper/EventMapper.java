package com.example.Ticket.Booking.App.mapper;

import com.example.Ticket.Booking.App.domian.CreateEventRequest;
import com.example.Ticket.Booking.App.domian.CreateTicketTypeRequest;
import com.example.Ticket.Booking.App.domian.dtos.CreateEventRequestDto;
import com.example.Ticket.Booking.App.domian.dtos.CreateEventResponseDto;
import com.example.Ticket.Booking.App.domian.dtos.CreateTicketTypeRequestDto;
import com.example.Ticket.Booking.App.domian.dtos.CreateTicketTypeResponseDto;
import com.example.Ticket.Booking.App.domian.entities.Event;
import com.example.Ticket.Booking.App.domian.entities.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "Spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDto dto);

    CreateEventRequest fromDto(CreateEventRequestDto dto);

    CreateEventResponseDto toDto(Event event);

    CreateTicketTypeResponseDto toDto(TicketType ticketType);
}
