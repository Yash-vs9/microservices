package com.streamseat.bookingservice.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EventDTO {
    private Long id;
    private String title;
    private int capacity;
    private LocalDateTime eventDateTime;
}