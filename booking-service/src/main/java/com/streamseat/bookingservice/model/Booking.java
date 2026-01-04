package com.streamseat.bookingservice.model;

import com.streamseat.bookingservice.dto.SeatType;
import com.streamseat.bookingservice.dto.Status;
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
    @Enumerated(EnumType.STRING)
    private SeatType seatType;
    private Status status;   // CONFIRMED, CANCELLED
    private LocalDateTime bookingTime;
}