package com.travelsync.config;
package com.travelsync.controller;

import com.travelsync.dto.FlightDto;
import com.travelsync.service.FlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<FlightDto>> searchFlights(
            @RequestParam String from,
            @RequestParam String to) {
        List<FlightDto> flights = flightService.searchFlights(from, to);
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightDto> getFlight(@PathVariable Long id) {
        FlightDto flight = flightService.getFlightById(id);
        return ResponseEntity.ok(flight);
    }
}