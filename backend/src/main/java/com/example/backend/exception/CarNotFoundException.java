package com.example.backend.exception;

public class CarNotFoundException extends RuntimeException {
    
    public CarNotFoundException(Long carId) {
        super("Car with id " + carId + " not found");
    }
    
    public CarNotFoundException(String message) {
        super(message);
    }
}
