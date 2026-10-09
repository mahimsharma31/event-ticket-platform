package com.example.Ticket.Booking.App.domian.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public class GetEventDetailTicketTypeResponseDto {
    private UUID id;
    private String name;
    private Double price;
    private String description;
    private Integer totalAvailable;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
