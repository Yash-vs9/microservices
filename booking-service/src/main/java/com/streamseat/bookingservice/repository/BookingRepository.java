package com.streamseat.bookingservice.repository;

import com.streamseat.bookingservice.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Custom query to count how many tickets are sold for an event
    long countByEventId(Long eventId);
}