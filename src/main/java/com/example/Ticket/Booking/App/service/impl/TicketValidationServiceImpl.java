package com.example.Ticket.Booking.App.service.impl;

import com.example.Ticket.Booking.App.domian.entities.*;
import com.example.Ticket.Booking.App.exception.QrCodeNotFoundException;
import com.example.Ticket.Booking.App.exception.TicketNotFoundException;
import com.example.Ticket.Booking.App.repository.QrCodeRepository;
import com.example.Ticket.Booking.App.repository.TicketRepository;
import com.example.Ticket.Booking.App.repository.TicketValidationRepository;
import com.example.Ticket.Booking.App.service.TicketValidationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketValidationServiceImpl implements TicketValidationService {

    private final QrCodeRepository qrCodeRepository;
    private final TicketValidationRepository ticketValidationRepository;
    private final TicketRepository ticketRepository;
    @Override
    public TicketValidation validateTicketByQrCode(UUID qrCodeId) {
        QrCode qrCode = qrCodeRepository.findByIdAndStatus(qrCodeId, QrCodeStatusEnum.ACTIVE)
                .orElseThrow(() -> new QrCodeNotFoundException(
                        String.format("Qr code with %s Id not found", qrCodeId)
                ));

        Ticket ticket = qrCode.getTicket();

        return validateTicket(ticket, TicketValidationMethod.QR_SCAN);
    }

    private TicketValidation validateTicket(Ticket ticket, TicketValidationMethod ticketValidationMethod) {
        TicketValidation ticketValidation = new TicketValidation();

        ticketValidation.setTicket(ticket);
        ticketValidation.setValidationMethod(ticketValidationMethod);

        TicketValidationStatusEnum ticketValidationStatus = ticket.getValidations().stream()
                .filter(v -> TicketValidationStatusEnum.VALID.equals(v.getStatus()))
                .findFirst()
                .map(v -> TicketValidationStatusEnum.INVALID)
                .orElse(TicketValidationStatusEnum.VALID);

        ticketValidation.setStatus(ticketValidationStatus);

        return ticketValidationRepository.save(ticketValidation);
    }

    @Override
    public TicketValidation validateTicketByManually(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(TicketNotFoundException::new);

        return validateTicket(ticket, TicketValidationMethod.MANUAL);
    }
}
