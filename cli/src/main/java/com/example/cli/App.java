package com.example.cli;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class App {
    
    private static final String BASE_URL = "http://localhost:8080";
    private static final HttpClient httpClient = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(10))
        .build();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    public static void main(String[] args) {
        if (args.length == 0) {
            printUsage();
            System.exit(1);
        }
        
        String command = args[0];
        
        try {
            switch (command) {
                case "create-car":
                    handleCreateCar(args);
                    break;
                case "add-fuel":
                    handleAddFuel(args);
                    break;
                case "fuel-stats":
                    handleFuelStats(args);
                    break;
                default:
                    System.err.println("Unknown command: " + command);
                    printUsage();
                    System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("  create-car --brand <brand> --model <model> --year <year>");
        System.out.println("  add-fuel --carId <id> --liters <liters> --price <price> --odometer <odometer>");
        System.out.println("  fuel-stats --carId <id>");
    }
    
    private static String getArgumentValue(String[] args, String key) {
        List<String> argsList = Arrays.asList(args);
        int index = argsList.indexOf(key);
        if (index == -1 || index == argsList.size() - 1) {
            return null;
        }
        return argsList.get(index + 1);
    }
    
    private static void handleCreateCar(String[] args) throws Exception {
        String[] commandArgs = Arrays.copyOfRange(args, 1, args.length);
        
        String brand = getArgumentValue(commandArgs, "--brand");
        String model = getArgumentValue(commandArgs, "--model");
        String yearStr = getArgumentValue(commandArgs, "--year");
        
        if (brand == null || model == null || yearStr == null) {
            System.err.println("Error: --brand, --model, and --year are required");
            printUsage();
            System.exit(1);
        }
        
        int year;
        try {
            year = Integer.parseInt(yearStr);
        } catch (NumberFormatException e) {
            System.err.println("Error: --year must be a valid number");
            System.exit(1);
            return;
        }
        
        String jsonBody = String.format(
            "{\"brand\":\"%s\",\"model\":\"%s\",\"year\":%d}",
            brand, model, year
        );
        
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/api/cars"))
            .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
            .header("Content-Type", "application/json")
            .timeout(Duration.ofSeconds(10))
            .build();
        
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() == 201) {
            JsonNode jsonNode = objectMapper.readTree(response.body());
            System.out.println("Car created successfully!");
            System.out.println("ID: " + jsonNode.get("id").asLong());
            System.out.println("Brand: " + jsonNode.get("brand").asText());
            System.out.println("Model: " + jsonNode.get("model").asText());
            System.out.println("Year: " + jsonNode.get("year").asInt());
        } else {
            System.err.println("Error: Failed to create car. Status: " + response.statusCode());
            System.err.println("Response: " + response.body());
            System.exit(1);
        }
    }
    
    private static void handleAddFuel(String[] args) throws Exception {
        String[] commandArgs = Arrays.copyOfRange(args, 1, args.length);
        
        String carIdStr = getArgumentValue(commandArgs, "--carId");
        String litersStr = getArgumentValue(commandArgs, "--liters");
        String priceStr = getArgumentValue(commandArgs, "--price");
        String odometerStr = getArgumentValue(commandArgs, "--odometer");
        
        if (carIdStr == null || litersStr == null || priceStr == null || odometerStr == null) {
            System.err.println("Error: --carId, --liters, --price, and --odometer are required");
            printUsage();
            System.exit(1);
        }
        
        long carId;
        double liters;
        double price;
        int odometer;
        
        try {
            carId = Long.parseLong(carIdStr);
            liters = Double.parseDouble(litersStr);
            price = Double.parseDouble(priceStr);
            odometer = Integer.parseInt(odometerStr);
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid number format");
            System.exit(1);
            return;
        }
        
        String jsonBody = String.format(
            "{\"liters\":%.2f,\"price\":%.2f,\"odometer\":%d}",
            liters, price, odometer
        );
        
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/api/cars/" + carId + "/fuel"))
            .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
            .header("Content-Type", "application/json")
            .timeout(Duration.ofSeconds(10))
            .build();
        
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() == 201) {
            System.out.println("Fuel entry added successfully!");
        } else if (response.statusCode() == 404) {
            System.err.println("Error: Car not found with ID: " + carId);
            System.exit(1);
        } else {
            System.err.println("Error: Failed to add fuel entry. Status: " + response.statusCode());
            System.err.println("Response: " + response.body());
            System.exit(1);
        }
    }
    
    private static void handleFuelStats(String[] args) throws Exception {
        String[] commandArgs = Arrays.copyOfRange(args, 1, args.length);
        
        String carIdStr = getArgumentValue(commandArgs, "--carId");
        
        if (carIdStr == null) {
            System.err.println("Error: --carId is required");
            printUsage();
            System.exit(1);
        }
        
        long carId;
        try {
            carId = Long.parseLong(carIdStr);
        } catch (NumberFormatException e) {
            System.err.println("Error: --carId must be a valid number");
            System.exit(1);
            return;
        }
        
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/api/cars/" + carId + "/fuel/stats"))
            .GET()
            .timeout(Duration.ofSeconds(10))
            .build();
        
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() == 200) {
            JsonNode jsonNode = objectMapper.readTree(response.body());
            double totalFuel = jsonNode.get("totalFuel").asDouble();
            double totalCost = jsonNode.get("totalCost").asDouble();
            double avgConsumption = jsonNode.get("avgConsumptionPer100km").asDouble();
            
            System.out.printf("Total fuel: %.0f L%n", totalFuel);
            System.out.printf("Total cost: %.2f%n", totalCost);
            System.out.printf("Average consumption: %.1f L/100km%n", avgConsumption);
        } else if (response.statusCode() == 404) {
            System.err.println("Error: Car not found with ID: " + carId);
            System.exit(1);
        } else {
            System.err.println("Error: Failed to get fuel stats. Status: " + response.statusCode());
            System.err.println("Response: " + response.body());
            System.exit(1);
        }
    }
}
