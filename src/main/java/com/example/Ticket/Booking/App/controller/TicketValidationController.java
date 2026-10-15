package com.example.Ticket.Booking.App.controller;

import com.example.Ticket.Booking.App.domian.dtos.TicketValidationRequestDto;
import com.example.Ticket.Booking.App.domian.dtos.TicketValidationResponseDto;
import com.example.Ticket.Booking.App.domian.entities.TicketValidation;
import com.example.Ticket.Booking.App.domian.entities.TicketValidationMethod;
import com.example.Ticket.Booking.App.mapper.TicketMapper;
import com.example.Ticket.Booking.App.mapper.TicketValidationMapper;
import com.example.Ticket.Booking.App.service.TicketValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/ticker-validations")
public class TicketValidationController {

    private final TicketValidationService ticketValidationService;
    private final TicketValidationMapper ticketValidationMapper;

    public ResponseEntity<TicketValidationResponseDto> validateTicket(
            @RequestBody TicketValidationRequestDto ticketValidationRequestDto
            ){
        TicketValidationMethod method = ticketValidationRequestDto.getMethod();
        TicketValidation ticketValidation;
        if(TicketValidationMethod.MANUAL.equals(method)){
            ticketValidation = ticketValidationService
                    .validateTicketByManually(ticketValidationRequestDto.getId());
        }else{
           ticketValidation =ticketValidationService
                   .validateTicketByQrCode(ticketValidationRequestDto.getId());
        }
        return ResponseEntity.ok(
                ticketValidationMapper.toTicketValidationResponseDto(ticketValidation)
        );
    }
}
