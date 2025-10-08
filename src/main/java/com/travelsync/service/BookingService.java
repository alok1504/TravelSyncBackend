package com.travelsync.service;

import com.travelsync.dto.BookingDto;
import com.travelsync.dto.BookingRequest;
import com.travelsync.entity.Booking;
import com.travelsync.entity.Flight;
import com.travelsync.entity.User;
import com.travelsync.repository.BookingRepository;
import com.travelsync.repository.FlightRepository;
import com.travelsync.repository.UserRepository;
import com.travelsync.repository.NotificationRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;
    private final NotificationService notificationService;  // For post-booking notifications

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, 
                          FlightRepository flightRepository, NotificationService notificationService) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.flightRepository = flightRepository;
        this.notificationService = notificationService;
    }

    public BookingDto createBooking(BookingRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User  not found"));

        Flight flight = flightRepository.findById(request.getFlightId())
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        // FIXED: Check seats availability
        if (flight.getSeatsAvailable() <= 0) {
            throw new RuntimeException("No seats available for this flight");
        }

        Booking booking = new Booking();
        booking.setUser (user);
        booking.setFlight(flight);
        booking.setPassengerName(request.getPassengerName());
        booking.setSeatPreference(request.getSeatPreference());  // Ancillary
        booking.setBaggage(request.getBaggage());  // Ancillary
        booking.setStatus("confirmed");
        // FIXED: Calculate total price (base + ancillary extras)
        double basePrice = flight.getPrice();
        double ancillaryExtra = (request.getBaggage() != null && !request.getBaggage().isEmpty()) ? 50.0 : 0.0;
        booking.setTotalPrice(basePrice + ancillaryExtra);

        // FIXED: Update flight seats
        flight.setSeatsAvailable(flight.getSeatsAvailable() - 1);
        flightRepository.save(flight);

        // FIXED: Add loyalty points
        user.setPoints(user.getPoints() + 100);  // Example: 100 points per booking
        userRepository.save(user);

        booking = bookingRepository.save(booking);

        // FIXED: Create success notification
        notificationService.createNotification(user, "Booking confirmed for flight " + flight.getNumber() + ". Total: $" + booking.getTotalPrice(), "success");

        return toDto(booking);
    }

    public List<BookingDto> getUser Bookings() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User  not found"));
        List<Booking> bookings = bookingRepository.findByUser (user);
        return bookings.stream().map(this::toDto).collect(Collectors.toList());
    }

    private BookingDto toDto(Booking booking) {
        BookingDto dto = new BookingDto();
        dto.setId(booking.getId());
        dto.setPassengerName(booking.getPassengerName());
        dto.setSeatPreference(booking.getSeatPreference());
        dto.setBaggage(booking.getBaggage());
        dto.setStatus(booking.getStatus());
        dto.setTotalPrice(booking.getTotalPrice());
        if (booking.getFlight() != null) {
            FlightDto flightDto = new FlightDto();
            flightDto.setId(booking.getFlight().getId());
            flightDto.setNumber(booking.getFlight().getNumber());
            flightDto.setFrom(booking.getFlight().getFrom());
            flightDto.setTo(booking.getFlight().getTo());
            flightDto.setPrice(booking.getFlight().getPrice());
            dto.setFlight(flightDto);
        }
        return dto;
    }

    // No seed data needed for bookings (created by users)
}
