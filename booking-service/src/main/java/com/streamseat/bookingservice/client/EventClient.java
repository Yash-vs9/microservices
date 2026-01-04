package com.streamseat.bookingservice.client;

import com.streamseat.bookingservice.dto.EventDTO;
import com.streamseat.bookingservice.dto.SeatType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

// NAME must match the name in Eureka (EVENT-SERVICE)
@FeignClient(name = "event-service")
public interface EventClient {

    // We are "mapping" the call to the Event Service's existing endpoint
    // GET http://event-service/api/events/{id}
    // (Note: We need to go add this endpoint to Event Service later if it's missing!)
    @GetMapping("/api/events/{id}")
    EventDTO getEventById(@PathVariable("id") Long id);

    @PutMapping("/api/events/{id}/reserve")
    boolean reserveSeat(@PathVariable("id") Long id, @RequestParam SeatType seatType);
}