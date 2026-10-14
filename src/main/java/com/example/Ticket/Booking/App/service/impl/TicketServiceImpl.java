package com.example.Ticket.Booking.App.service.impl;

import com.example.Ticket.Booking.App.domian.entities.Ticket;
import com.example.Ticket.Booking.App.repository.TicketRepository;
import com.example.Ticket.Booking.App.service.TicketService;
import com.example.Ticket.Booking.App.service.TicketTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    @Override
    public Page<Ticket> listTicketForUser(UUID userId, Pageable pageable) {
        return ticketRepository.findByPurchaserId(userId,pageable);
    }

    @Override
    public Optional<Ticket> getTicketForUser(UUID userId, UUID ticketId) {
        return ticketRepository.findByIdAndPurchaserId(ticketId, userId);
    }
}
