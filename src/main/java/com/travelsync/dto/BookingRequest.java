package com.travelsync.dto;

import jakarta.validation.constraints.NotBlank;

package com.travelsync.dto;

import java.util.List;

public class BookingRequest {
    private Long id;
    private String passengerName;
    private String seatPreference;  // Ancillary
    private String baggage;  // Ancillary
    private String status;
    private double totalPrice;
    private FlightDto flight;  // Embedded flight info

    // Getters/Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }
    public String getSeatPreference() { return seatPreference; }
    public void setSeatPreference(String seatPreference) { this.seatPreference = seatPreference; }
    public String getBaggage() { return baggage; }
    public void setBaggage(String baggage) { this.baggage = baggage; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public FlightDto getFlight() { return flight; }
    public void setFlight(FlightDto flight) { this.flight = flight; }
}