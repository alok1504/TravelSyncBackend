package com.travelsync.dto;

import java.time.LocalDateTime;

public class NotificationDto {
    private Long id;
    private String message;
    private String type;  // success, error
    private LocalDateTime date;
    private boolean read;

    // Getters/Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }
}