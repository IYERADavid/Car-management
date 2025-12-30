package com.example.backend.dto;

public class CreateCarRequest {
    
    private String brand;
    private String model;
    private Integer year;
    
    public CreateCarRequest() {
    }
    
    public CreateCarRequest(String brand, String model, Integer year) {
        this.brand = brand;
        this.model = model;
        this.year = year;
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
    
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public void setYear(Integer year) {
        this.year = year;
    }
}
