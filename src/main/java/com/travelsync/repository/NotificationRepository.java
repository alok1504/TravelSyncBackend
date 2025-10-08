package com.travelsync.repository;

import com.travelsync.entity.Notification;
import com.travelsync.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserAndReadFalse(User user);  // Unread notifications
    List<Notification> findByUser (User user);
}
