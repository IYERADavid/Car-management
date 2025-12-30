package com.example.backend.dto;

public class FuelStatsResponse {
    
    private Double totalFuel;
    private Double totalCost;
    private Double avgConsumptionPer100km;
    
    public FuelStatsResponse() {
    }
    
    public FuelStatsResponse(Double totalFuel, Double totalCost, Double avgConsumptionPer100km) {
        this.totalFuel = totalFuel;
        this.totalCost = totalCost;
        this.avgConsumptionPer100km = avgConsumptionPer100km;
    }
    
    public Double getTotalFuel() {
        return totalFuel;
    }
    
    public Double getTotalCost() {
        return totalCost;
    }
    
    public Double getAvgConsumptionPer100km() {
        return avgConsumptionPer100km;
    }
    
    public void setTotalFuel(Double totalFuel) {
        this.totalFuel = totalFuel;
    }
    
    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }
    
    public void setAvgConsumptionPer100km(Double avgConsumptionPer100km) {
        this.avgConsumptionPer100km = avgConsumptionPer100km;
    }
}
