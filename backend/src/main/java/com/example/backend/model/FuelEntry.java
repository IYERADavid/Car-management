package com.example.backend.model;

import java.time.LocalDateTime;

/**
 * Represents a single fuel refill entry for a car.
 */
public class FuelEntry {
    
    private Long id;
    private Double liters;
    private Double price;
    private Double totalCost;
    private Integer odometer;
    private LocalDateTime timestamp;
    
    public FuelEntry() {
        this.timestamp = LocalDateTime.now();
    }
    
    public FuelEntry(Long id, Double liters, Double price, Integer odometer) {
        this.id = id;
        this.liters = liters;
        this.price = price;
        this.odometer = odometer;
        this.totalCost = liters * price;
        this.timestamp = LocalDateTime.now();
    }
    
    public Long getId() {
        return id;
    }
    
    public Double getLiters() {
        return liters;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public Double getTotalCost() {
        return totalCost;
    }
    
    public Integer getOdometer() {
        return odometer;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setLiters(Double liters) {
        this.liters = liters;
        if (this.price != null) {
            this.totalCost = this.liters * this.price;
        }
    }
    
    public void setPrice(Double price) {
        this.price = price;
        if (this.liters != null) {
            this.totalCost = this.liters * this.price;
        }
    }
    
    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }
    
    public void setOdometer(Integer odometer) {
        this.odometer = odometer;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
