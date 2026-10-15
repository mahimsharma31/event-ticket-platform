package com.example.Ticket.Booking.App.service;

import com.example.Ticket.Booking.App.domian.entities.TicketValidation;

import java.util.UUID;

public interface TicketValidationService {
    TicketValidation validateTicketByQrCode(UUID qrCodeId);
    TicketValidation validateTicketByManually(UUID ticketId);
}
