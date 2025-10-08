package com.travelsync.service;

import com.travelsync.dto.NotificationDto;
import com.travelsync.entity.Notification;
import com.travelsync.entity.User;
import com.travelsync.repository.NotificationRepository;
import com.travelsync.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public void createNotification(User user, String message, String type) {
        Notification notification = new Notification();
        notification.setUser (user);
        notification.setMessage(message);
        notification.setType(type);
        notification.setRead(false);
        notificationRepository.save(notification);
    }

    public List<NotificationDto> getUser Notifications() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User  not found"));
        List<Notification> notifications = notificationRepository.findByUser (user);
        return notifications.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<NotificationDto> getUnreadNotifications() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User  not found"));
        List<Notification> unread = notificationRepository.findByUser AndReadFalse(user);
        return unread.stream().map(this::toDto).collect(Collectors.toList());
    }

    public void markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    public void deleteNotification(Long id) {
        if (notificationRepository.existsById(id)) {
            notificationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Notification not found");
        }
    }

    private NotificationDto toDto(Notification notification) {
        NotificationDto dto = new NotificationDto();
        dto.setId(notification.getId());
        dto.setMessage(notification.getMessage());
        dto.setType(notification.getType());
        dto.setDate(notification.getDate());
        dto.setRead(notification.isRead());
        return dto;
    }

    // Seed example notifications (for default user)
    @PostConstruct
    public void initData() {
        if (notificationRepository.count() == 0) {
            // Example: Assume default user exists
            User defaultUser  = userRepository.findByEmail("john@example.com").orElse(null);
            if (defaultUser  != null) {
                createNotification(defaultUser , "Welcome to TravelSync! Earn points on bookings.", "success");
                createNotification(defaultUser , "New flights added to NYC routes.", "info");
            }
        }
    }
}
