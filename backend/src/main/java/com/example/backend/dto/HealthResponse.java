package com.example.backend.dto;

import java.time.LocalDateTime;

public class HealthResponse {
    
    private String status;
    private String message;
    private LocalDateTime timestamp;
    
    public HealthResponse() {
        this.timestamp = LocalDateTime.now();
    }
    
    public HealthResponse(String status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
    
    public String getStatus() {
        return status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

