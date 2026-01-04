package com.streamseat.eventservice.service;

import com.streamseat.eventservice.dto.EventDTO;
import com.streamseat.eventservice.dto.SeatType;
import com.streamseat.eventservice.model.Event;
import com.streamseat.eventservice.repository.EventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;


    public void createEvent(EventDTO event){
        if(event.getFrontSeats()+ event.getBackSeats()!= event.getCapacity()){
            throw new RuntimeException("Number of front seats and back seats doesnt match the capacity");
        }
        Event eventObj= Event.builder()
                .eventDateTime(event.getEventDateTime())
                .title(event.getTitle())
                .backSeats(event.getBackSeats())
                .capacity(event.getCapacity())
                .frontSeats(event.getFrontSeats())
                .description(event.getDescription())
                .build();
        eventRepository.save(eventObj);
    }
    @Transactional
    public void reserveSeat(Long eventId, SeatType type) {
        Event event = eventRepository.findByIdWithLock(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Using a Switch statement with Enums is very clean
        switch (type) {
            case FRONT_SEAT -> {
                if (event.getAvailableFrontSeats() <= 0) throw new RuntimeException("Front full");
                event.setAvailableFrontSeats(event.getAvailableFrontSeats() - 1);
            }
            case BACK_SEAT -> {
                if (event.getAvailableBackSeats() <= 0) throw new RuntimeException("Back full");
                event.setAvailableBackSeats(event.getAvailableBackSeats() - 1);
            }
        }
        eventRepository.save(event);
    }
}
