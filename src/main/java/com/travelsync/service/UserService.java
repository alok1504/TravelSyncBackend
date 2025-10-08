package com.travelsync.service;

import com.travelsync.dto.UserDto;
import com.travelsync.entity.User;
import com.travelsync.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto getProfile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            UserDto dto = new UserDto();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
            dto.setPoints(user.getPoints());
            // Add bookings if needed
            return dto;
        }
        throw new RuntimeException("User  not found");
    }

    public UserDto updateProfile(UserDto dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setName(dto.getName());
            // Email update? Add validation
            userRepository.save(user);
            dto.setId(user.getId());
            return dto;
        }
        throw new RuntimeException("User  not found");
    }

    // Seed default user (called on startup)
    @PostConstruct
    public void initData() {
        if (userRepository.count() == 0) {
            User defaultUser  = new User();
            defaultUser .setName("John Doe");
            defaultUser .setEmail("john@example.com");
            defaultUser .setPassword(passwordEncoder.encode("password"));  // Note: Inject PasswordEncoder if needed
            userRepository.save(defaultUser );
        }
    }
}
