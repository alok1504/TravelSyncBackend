package com.travelsync.config;
package com.travelsync.controller;

import com.travelsync.dto.UserDto;
import com.travelsync.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getProfile() {
        UserDto profile = userService.getProfile();
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserDto> updateProfile(@RequestBody UserDto dto) {
        UserDto updated = userService.updateProfile(dto);
        return ResponseEntity.ok(updated);
    }
}