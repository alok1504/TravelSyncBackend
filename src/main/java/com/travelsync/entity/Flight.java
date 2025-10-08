package com.travelsync.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "flights")
@Data
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String number;  // e.g., "AA123"

    @Column(nullable = false)
    private String from;

    @Column(nullable = false)
    private String to;

    private String departureTime;

    private String arrivalTime;

    private double price;

    private int seatsAvailable = 100;
}
