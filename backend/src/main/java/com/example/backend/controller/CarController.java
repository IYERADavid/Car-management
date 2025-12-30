package com.example.backend.controller;

import com.example.backend.dto.AddFuelRequest;
import com.example.backend.dto.CarResponse;
import com.example.backend.dto.CreateCarRequest;
import com.example.backend.dto.FuelStatsResponse;
import com.example.backend.exception.InvalidInputException;
import com.example.backend.model.Car;
import com.example.backend.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    
    private final CarService carService;
    
    public CarController(CarService carService) {
        this.carService = carService;
    }
    
    @PostMapping
    public ResponseEntity<CarResponse> createCar(@RequestBody CreateCarRequest request) {
        if (request == null) {
            throw new InvalidInputException("Request body is required");
        }
        
        if (request.getBrand() == null || request.getBrand().trim().isEmpty()) {
            throw new InvalidInputException("Brand is required and cannot be empty");
        }
        if (request.getModel() == null || request.getModel().trim().isEmpty()) {
            throw new InvalidInputException("Model is required and cannot be empty");
        }
        if (request.getYear() == null) {
            throw new InvalidInputException("Year is required");
        }
        if (request.getYear() < 1900 || request.getYear() > 2100) {
            throw new InvalidInputException("Year must be between 1900 and 2100");
        }
        
        Car car = carService.createCar(
            request.getBrand().trim(),
            request.getModel().trim(),
            request.getYear()
        );
        
        CarResponse response = new CarResponse(
            car.getId(),
            car.getBrand(),
            car.getModel(),
            car.getYear()
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping
    public ResponseEntity<List<CarResponse>> getAllCars() {
        List<Car> cars = carService.getAllCars();
        
        List<CarResponse> responses = cars.stream()
            .map(car -> new CarResponse(
                car.getId(),
                car.getBrand(),
                car.getModel(),
                car.getYear()
            ))
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(responses);
    }
    
    @PostMapping("/{id}/fuel")
    public ResponseEntity<Void> addFuelEntry(
            @PathVariable Long id,
            @RequestBody AddFuelRequest request) {
        
        if (id == null) {
            throw new InvalidInputException("Car ID is required");
        }
        
        carService.addFuelEntry(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @GetMapping("/{id}/fuel/stats")
    public ResponseEntity<FuelStatsResponse> getFuelStats(@PathVariable Long id) {
        if (id == null) {
            throw new InvalidInputException("Car ID is required");
        }
        
        FuelStatsResponse stats = carService.getFuelStats(id);
        return ResponseEntity.ok(stats);
    }
}
