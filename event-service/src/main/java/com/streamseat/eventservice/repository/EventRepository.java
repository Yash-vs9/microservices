package com.streamseat.eventservice.repository;

import com.streamseat.eventservice.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

// This gives us free methods like .save(), .findAll(), .findById()
public interface EventRepository extends JpaRepository<Event, Long> {
}