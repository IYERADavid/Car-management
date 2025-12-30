package com.example.backend.service;

import com.example.backend.dto.AddFuelRequest;
import com.example.backend.dto.FuelStatsResponse;
import com.example.backend.exception.CarNotFoundException;
import com.example.backend.exception.InvalidInputException;
import com.example.backend.model.Car;
import com.example.backend.model.FuelEntry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Service layer for car and fuel entry management.
 * Uses in-memory storage with thread-safe collections.
 */
@Service
public class CarService {
    
    private final Map<Long, Car> cars = new ConcurrentHashMap<>();
    private final AtomicLong carIdCounter = new AtomicLong(1);
    private final AtomicLong fuelEntryIdCounter = new AtomicLong(1);
    
    public Car createCar(String brand, String model, Integer year) {
        Long id = carIdCounter.getAndIncrement();
        Car car = new Car(id, brand, model, year);
        cars.put(id, car);
        return car;
    }
    
    public List<Car> getAllCars() {
        return new ArrayList<>(cars.values());
    }
    
    public Car getCarById(Long id) {
        return cars.get(id);
    }
    
    public FuelEntry addFuelEntry(Long carId, AddFuelRequest request) {
        if (request == null) {
            throw new InvalidInputException("Request body is required");
        }
        
        Car car = getCarById(carId);
        if (car == null) {
            throw new CarNotFoundException(carId);
        }
        
        if (request.getLiters() == null || request.getLiters() <= 0) {
            throw new InvalidInputException("Liters must be a positive number");
        }
        if (request.getPrice() == null || request.getPrice() <= 0) {
            throw new InvalidInputException("Price must be a positive number");
        }
        if (request.getOdometer() == null || request.getOdometer() < 0) {
            throw new InvalidInputException("Odometer must be a non-negative number");
        }
        
        Long fuelEntryId = fuelEntryIdCounter.getAndIncrement();
        FuelEntry fuelEntry = new FuelEntry(
            fuelEntryId,
            request.getLiters(),
            request.getPrice(),
            request.getOdometer()
        );
        
        car.addFuelEntry(fuelEntry);
        return fuelEntry;
    }
    
    public FuelStatsResponse getFuelStats(Long carId) {
        Car car = getCarById(carId);
        if (car == null) {
            throw new CarNotFoundException(carId);
        }
        
        List<FuelEntry> fuelEntries = car.getFuelEntries();
        if (fuelEntries.isEmpty()) {
            return new FuelStatsResponse(0.0, 0.0, 0.0);
        }
        
        double totalFuel = fuelEntries.stream()
            .mapToDouble(FuelEntry::getLiters)
            .sum();
        
        double totalCost = fuelEntries.stream()
            .mapToDouble(FuelEntry::getTotalCost)
            .sum();
        
        // Sort entries by odometer to handle entries added out of order
        List<FuelEntry> sortedEntries = fuelEntries.stream()
            .sorted(java.util.Comparator.comparing(FuelEntry::getOdometer))
            .collect(Collectors.toList());
        
        int minOdometer = sortedEntries.get(0).getOdometer();
        int maxOdometer = sortedEntries.get(sortedEntries.size() - 1).getOdometer();
        int distanceTraveled = maxOdometer - minOdometer;
        
        // Calculate average consumption per 100km
        // Requires at least 2 entries with different odometer readings to calculate distance
        // Edge cases handled:
        // - Single entry: distanceTraveled = 0, returns 0.0 (can't calculate without distance)
        // - Multiple entries with same odometer: distanceTraveled = 0, returns 0.0
        // - Invalid odometer order: handled by sorting above
        // - All fuel entries are included in totalFuel calculation
        double avgConsumptionPer100km = 0.0;
        if (distanceTraveled > 0 && sortedEntries.size() > 1) {
            // Formula: (total fuel / distance) * 100
            // This calculates average consumption based on total fuel used over the distance traveled
            avgConsumptionPer100km = (totalFuel / distanceTraveled) * 100.0;
        }
        
        return new FuelStatsResponse(totalFuel, totalCost, avgConsumptionPer100km);
    }
}
