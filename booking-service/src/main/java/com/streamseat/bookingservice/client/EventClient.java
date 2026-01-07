package com.streamseat.bookingservice.client;

import com.streamseat.bookingservice.dto.EventDTO;
import com.streamseat.bookingservice.dto.SeatType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "event-service")
public interface EventClient {

    @GetMapping("/api/events/{id}")
        // Added ("id") here
    EventDTO getEventById(@PathVariable("id") Long id);

    @PutMapping("/api/events/{id}/reserve")
        // Added ("id") and ("seatType") here
    boolean reserveSeat(@PathVariable("id") Long id,
                        @RequestParam("seatType") SeatType seatType);
}