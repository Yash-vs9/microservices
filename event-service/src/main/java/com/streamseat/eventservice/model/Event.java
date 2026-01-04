package com.streamseat.eventservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Data                 // <--- Generates Getters, Setters, toString, equals, hashcode
@AllArgsConstructor   // <--- Generates a constructor with all arguments
@NoArgsConstructor    // <--- Generates an empty constructor (Required by Hibernate)
@Builder              // <--- Lets you build objects easily like Event.builder().title("..").build()
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDateTime eventDateTime;
    private int capacity;
    private int frontSeats;
    private int backSeats;
    private int availableFrontSeats;
    private int availableBackSeats;
}