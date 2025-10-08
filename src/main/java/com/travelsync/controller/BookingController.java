package com.travelsync.config;
package com.travelsync.controller;

import com.travelsync.dto.BookingDto;
import com.travelsync.dto.BookingRequest;
import com.travelsync.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingDto> createBooking(@Valid @RequestBody BookingRequest request) {
        BookingDto booking = bookingService.createBooking(request);
        return ResponseEntity.ok(booking);
    }

    @GetMapping
    public ResponseEntity<List<BookingDto>> getUser Bookings() {
        List<BookingDto> bookings = bookingService.getUser Bookings();
        return ResponseEntity.ok(bookings);
    }
}