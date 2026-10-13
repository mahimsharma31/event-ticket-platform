package com.example.Ticket.Booking.App.service.impl;

import com.example.Ticket.Booking.App.domian.entities.Ticket;
import com.example.Ticket.Booking.App.domian.entities.TicketStatusEnum;
import com.example.Ticket.Booking.App.domian.entities.TicketType;
import com.example.Ticket.Booking.App.domian.entities.User;
import com.example.Ticket.Booking.App.exception.TicketTypeNotFoundException;
import com.example.Ticket.Booking.App.exception.TicketTypeSoldOutException;
import com.example.Ticket.Booking.App.exception.UserNotFoundException;
import com.example.Ticket.Booking.App.repository.TicketRepository;
import com.example.Ticket.Booking.App.repository.TicketTypeRepository;
import com.example.Ticket.Booking.App.repository.UserRepository;
import com.example.Ticket.Booking.App.service.QrCodeService;
import com.example.Ticket.Booking.App.service.TicketTypeService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final QrCodeService qrCodeService;
    @Override
    @Transactional
    public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(
                String.format("User with Id %s not exist", userId)
        ));
        TicketType ticketType = ticketTypeRepository.findByIdWithLock(ticketTypeId).orElseThrow(() -> new TicketTypeNotFoundException(
                String.format("Ticket Type with Id %s not exist", ticketTypeId)
        ));
        int purchasedTicket = ticketRepository.countByTicketTypeId(ticketTypeId);
        Integer totalAvailable = ticketType.getTotalAvailable();

        if(purchasedTicket + 1 > totalAvailable){
            throw new TicketTypeSoldOutException();
        }
        Ticket ticket = new Ticket();
        ticket.setStatus(TicketStatusEnum.PURCHASED);
        ticket.setTicketType(ticketType);
        ticket.setPurchaser(user);

        Ticket saveTicket = ticketRepository.save(ticket);

        qrCodeService.generateQrCode(saveTicket);

        return ticketRepository.save(saveTicket);
    }
}
