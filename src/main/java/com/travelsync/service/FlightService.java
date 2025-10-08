package com.travelsync.service;

import com.travelsync.dto.FlightDto;
import com.travelsync.entity.Flight;
import com.travelsync.repository.FlightRepository;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<FlightDto> searchFlights(String from, String to) {
        List<Flight> flights = flightRepository.findByFromAndTo(from, to);
        return flights.stream().map(this::toDto).collect(Collectors.toList());
    }

    public FlightDto getFlightById(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
        return toDto(flight);
    }

    private FlightDto toDto(Flight flight) {
        FlightDto dto = new FlightDto();
        dto.setId(flight.getId());
        dto.setNumber(flight.getNumber());
        dto.setFrom(flight.getFrom());
        dto.setTo(flight.getTo());
        dto.setDepartureTime(flight.getDepartureTime());
        dto.setArrivalTime(flight.getArrivalTime());
        dto.setPrice(flight.getPrice());
        dto.setSeatsAvailable(flight.getSeatsAvailable());
        return dto;
    }

    // Seed mock flights
    @PostConstruct
    public void initData() {
        if (flightRepository.count() == 0) {
            Flight flight1 = new Flight();
            flight1.setNumber("AA123");
            flight1.setFrom("NYC");
            flight1.setTo("LAX");
            flight1.setDepartureTime("2024-10-10T10:00");
            flight1.setArrivalTime("2024-10-10T13:00");
            flight1.setPrice(299.99);
            flight1.setSeatsAvailable(50);

            Flight flight2 = new Flight();
            flight2.setNumber("UA456");
            flight2.setFrom("NYC");
            flight2.setTo("MIA");
            flight2.setDepartureTime("2024-10-10T12:00");
            flight2.setArrivalTime("2024-10-10T15:00");
            flight2.setPrice(199.99);
            flight2.setSeatsAvailable(30);

            flightRepository.saveAll(List.of(flight1, flight2));
        }
    }
}
