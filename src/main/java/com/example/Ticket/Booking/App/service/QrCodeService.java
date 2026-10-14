package com.example.Ticket.Booking.App.service;

import com.example.Ticket.Booking.App.domian.entities.QrCode;
import com.example.Ticket.Booking.App.domian.entities.Ticket;

import java.util.UUID;

public interface QrCodeService {

    QrCode generateQrCode(Ticket ticket);

    byte[] getQrcodeImageForUserAndTicket(UUID userId, UUID ticketId);
}
