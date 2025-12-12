package com.streamseat.eventservice.controller;

import com.streamseat.eventservice.model.Event;
import com.streamseat.eventservice.repository.EventRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // 1. Create a new Event
    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return eventRepository.save(event);
    }

    // 2. Get all Events
    @GetMapping
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
}
