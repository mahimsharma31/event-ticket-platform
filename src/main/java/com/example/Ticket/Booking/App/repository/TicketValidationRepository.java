package com.example.Ticket.Booking.App.repository;

import com.example.Ticket.Booking.App.domian.entities.TicketValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TicketValidationRepository extends JpaRepository<TicketValidation, UUID> {
}
