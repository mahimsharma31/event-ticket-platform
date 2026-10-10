package com.example.Ticket.Booking.App.domian.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPublishedEventDetailTicketTypeResponseDto {
    private UUID id;
    private String name;
    private Double price;
    private String description;

}
