package com.example.Ticket.Booking.App.domian.dtos;

import com.example.Ticket.Booking.App.domian.entities.TicketValidationMethod;
import com.example.Ticket.Booking.App.domian.entities.TicketValidationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketValidationRequestDto {
    private UUID id;
    private TicketValidationMethod method;
}
