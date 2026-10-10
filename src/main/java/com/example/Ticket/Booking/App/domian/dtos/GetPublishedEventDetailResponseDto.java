package com.example.Ticket.Booking.App.domian.dtos;

import com.example.Ticket.Booking.App.domian.entities.EventStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPublishedEventDetailResponseDto {
    private UUID id;
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private String venue;
    private List<GetPublishedEventDetailTicketTypeResponseDto> ticketTypes = new ArrayList<>();


}
