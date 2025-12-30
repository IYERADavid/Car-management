package com.example.backend.dto;

public class CarResponse {
    
    private Long id;
    private String brand;
    private String model;
    private Integer year;
    
    public CarResponse() {
    }
    
    public CarResponse(Long id, String brand, String model, Integer year) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
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
}
