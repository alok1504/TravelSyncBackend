package com.travelsync.dto;

import java.util.List;

public class UserDto {
    private Long id;
    private String name;
    private String email;
    private int points;
    private List<BookingDto> bookings;  // Simplified

    // Getters/Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
    public List<BookingDto> getBookings() { return bookings; }
    public void setBookings(List<BookingDto> bookings) { this.bookings = bookings; }
}