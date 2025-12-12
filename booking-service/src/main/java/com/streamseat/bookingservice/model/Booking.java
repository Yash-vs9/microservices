package com.streamseat.bookingservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long eventId;    // Which event?
    private Long userId;     // Who booked it? (We'll hardcode this for now)
    private String status;   // CONFIRMED, CANCELLED
    private LocalDateTime bookingTime;
}