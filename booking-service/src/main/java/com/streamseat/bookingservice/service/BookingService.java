package com.streamseat.bookingservice.service;

import com.streamseat.bookingservice.client.EventClient;
import com.streamseat.bookingservice.dto.EventDTO;
import com.streamseat.bookingservice.dto.Status;
import com.streamseat.bookingservice.dto.TicketDTO;
import com.streamseat.bookingservice.dto.SeatType;
import com.streamseat.bookingservice.model.Booking;
import com.streamseat.bookingservice.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Book;

@Service
@RequiredArgsConstructor
public class BookingService {
    public final EventClient eventClient;
    private final BookingRepository bookingRepository;
    public String bookTicket(Long eventId, SeatType seat,String userId){
        EventDTO event=eventClient.getEventById(eventId);
        Booking booking=new Booking();
        booking.setBookingTime(event.getEventDateTime());
        booking.setSeatType(seat);
        booking.setEventId(eventId);
        booking.setUserId(userId);
        booking.setStatus(Status.CONFIRMED);
        bookingRepository.save(booking);

        return "Booked";
    }
}
