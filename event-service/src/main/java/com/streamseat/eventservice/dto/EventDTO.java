package com.streamseat.eventservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    private String title;
    private String description;
    private LocalDateTime eventDateTime;
    private int capacity;
    private int frontSeats;
    private int backSeats;
}
