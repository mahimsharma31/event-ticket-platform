package com.example.Ticket.Booking.App.service;

import com.example.Ticket.Booking.App.domian.entities.QrCode;
import com.example.Ticket.Booking.App.domian.entities.Ticket;

public interface QrCodeService {

    QrCode generateQrCode(Ticket ticket);
}
