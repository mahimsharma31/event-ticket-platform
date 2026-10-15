package com.example.Ticket.Booking.App.mapper;

import com.example.Ticket.Booking.App.domian.dtos.TicketValidationResponseDto;
import com.example.Ticket.Booking.App.domian.entities.TicketValidation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "Spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketValidationMapper {

    @Mapping(target = "ticketId",  source = "ticket.id")
    TicketValidationResponseDto toTicketValidationResponseDto(TicketValidation ticketValidation);
}
