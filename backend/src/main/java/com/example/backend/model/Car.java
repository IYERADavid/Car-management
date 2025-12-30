package com.example.backend.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a car entity with fuel tracking capabilities.
 */
public class Car {
    
    private Long id;
    private String brand;
    private String model;
    private Integer year;
    private List<FuelEntry> fuelEntries;
    
    public Car() {
        this.fuelEntries = new ArrayList<>();
    }
    
    public Car(Long id, String brand, String model, Integer year) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.fuelEntries = new ArrayList<>();
    }
    
    public Long getId() {
        return id;
    }
    
    public String getBrand() {
        return brand;
    }
    
    public String getModel() {
        return model;
    }
    
    public Integer getYear() {
        return year;
    }
    
    public List<FuelEntry> getFuelEntries() {
        return fuelEntries;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public void setYear(Integer year) {
        this.year = year;
    }
    
    public void setFuelEntries(List<FuelEntry> fuelEntries) {
        this.fuelEntries = fuelEntries;
    }
    
    public void addFuelEntry(FuelEntry fuelEntry) {
        this.fuelEntries.add(fuelEntry);
    }
}
