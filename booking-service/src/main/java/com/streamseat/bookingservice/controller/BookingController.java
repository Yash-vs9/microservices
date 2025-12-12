package com.streamseat.bookingservice.controller;

import com.streamseat.bookingservice.client.EventClient;
import com.streamseat.bookingservice.dto.EventDTO;
import com.streamseat.bookingservice.model.Booking;
import com.streamseat.bookingservice.repository.BookingRepository;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingRepository bookingRepository;
    private final EventClient eventClient; // <--- The connection to Event Service

    public BookingController(BookingRepository bookingRepository, EventClient eventClient) {
        this.bookingRepository = bookingRepository;
        this.eventClient = eventClient;
    }

    @PostMapping
    public String bookTicket(@RequestBody Booking bookingRequest) {
        Long eventId = bookingRequest.getEventId();

        // 1. TALK to Event Service: "Does this event exist?"
        EventDTO event = eventClient.getEventById(eventId);
        if (event == null) {
            return "Failed: Event not found!";
        }

        // 2. CHECK Capacity: "Are there seats left?"
        long seatsTaken = bookingRepository.countByEventId(eventId);
        if (seatsTaken >= event.getCapacity()) {
            return "Failed: Housefull! Event is sold out.";
        }

        // 3. BOOK IT
        bookingRequest.setStatus("CONFIRMED");
        bookingRequest.setBookingTime(LocalDateTime.now());
        bookingRepository.save(bookingRequest);

        return "Success! Ticket booked for event: " + event.getTitle();
    }
}