package com.example.Ticket.Booking.App.repository;

import com.example.Ticket.Booking.App.domian.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface userRepository extends JpaRepository<User, UUID>{
}
