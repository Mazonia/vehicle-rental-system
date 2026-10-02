package com.rental;

import java.util.Scanner;
import java.util.List;

public class RentalSystemTest {
    private static RentalAgency agency;
    private static Scanner scanner;

    public static void main(String[] args) {
        agency = new RentalAgency();
        scanner = new Scanner(System.in);
        initializeVehicles();

        while (true) {
            displayMenu();
            int choice = getIntInput("Enter your choice (1-7): ");

            try {
                switch (choice) {
                    case 1:
                        registerNewCustomer();
                        break;
                    case 2:
                        rentVehicle();
                        break;
                    case 3:
                        returnVehicle();
                        break;
                    case 4:
                        rateService();
                        break;
                    case 5:
                        displayAvailableVehicles();
                        break;
                    case 6:
                        displayCustomerInfo();
                        break;
                    case 7:
                        System.out.println("\nThank you for using our Vehicle Rental System!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }

    private static void displayMenu() {
        System.out.println("\n=== Vehicle Rental System ===");
        System.out.println("1. Register New Customer");
        System.out.println("2. Rent a Vehicle");
        System.out.println("3. Return a Vehicle");
        System.out.println("4. Rate a Vehicle");
        System.out.println("5. View Available Vehicles");
        System.out.println("6. View Customer Information");
        System.out.println("7. Exit");
    }

    private static void initializeVehicles() {
        // Add sample vehicles
        agency.addVehicle(new Car("C001", "Toyota Camry", 50.0));
        agency.addVehicle(new Car("C002", "Honda Accord", 55.0));
        agency.addVehicle(new Truck("T001", "Ford F-150", 80.0));
        agency.addVehicle(new Motorcycle("M001", "Honda CBR", 40.0));
    }

    private static void registerNewCustomer() {
        System.out.println("\n=== Customer Registration ===");
        String customerId = getStringInput("Enter customer ID: ");
        String name = getStringInput("Enter customer name: ");

        try {
            Customer customer = new Customer(customerId, name);
            agency.registerCustomer(customer);
            System.out.println("\nCustomer registered successfully!");
            System.out.println("Customer ID: " + customer.getCustomerId());
            System.out.println("Initial Loyalty Tier: " + customer.getLoyaltyTier());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void rentVehicle() {
        System.out.println("\n=== Rent a Vehicle ===");
        displayAvailableVehicles();
        
        String customerId = getStringInput("Enter customer ID: ");
        String model = getStringInput("Enter vehicle model: ");
        int days = getIntInput("Enter number of rental days: ");

        try {
            agency.rentVehicle(model, customerId, days);
            System.out.println("Vehicle rented successfully!");
        } catch (RentalException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void returnVehicle() {
        System.out.println("\n=== Return a Vehicle ===");
        displayAllVehicles();
        
        String model = getStringInput("Enter vehicle model to return: ");
        
        Vehicle vehicle = findVehicleByModel(model);
        if (vehicle != null) {
            agency.returnVehicle(vehicle);
            System.out.println("Vehicle returned successfully!");
            
            System.out.print("Would you like to rate your experience? (y/n): ");
            if (scanner.nextLine().toLowerCase().startsWith("y")) {
                rateService();
            }
        } else {
            System.out.println("Vehicle not found.");
        }
    }

    private static void rateService() {
        System.out.println("\n=== Rate Service ===");
        String model = getStringInput("Enter vehicle model to rate: ");
        
        Vehicle vehicle = findVehicleByModel(model);
        if (vehicle != null) {
            String customerId = getStringInput("Enter your customer ID: ");
            Customer customer = agency.getCustomer(customerId);
            
            if (customer != null) {
                int rating = getIntInput("Enter rating (1-5): ");
                String comment = getStringInput("Enter comment: ");
                
                try {
                    agency.rateVehicle(vehicle, customer, rating, comment);
                    System.out.println("Thank you for your rating!");
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else {
                System.out.println("Customer not found.");
            }
        } else {
            System.out.println("Vehicle not found.");
        }
    }

    private static void displayAvailableVehicles() {
        System.out.println("\n=== Available Vehicles ===");
        System.out.printf("%-15s | %-12s | %-10s | %-15s | %-10s%n",
                "Model", "Type", "Status", "Daily Rate", "Avg Rating");
        System.out.println("-".repeat(70));
        
        for (Vehicle vehicle : agency.getVehicleFleet()) {
            if (vehicle.isAvailable()) {
                String type = vehicle instanceof Car ? "Car" :
                             vehicle instanceof Truck ? "Truck" : "Motorcycle";
                
                System.out.printf("%-15s | %-12s | %-10s | $%-14.2f | %.1f/5.0%n",
                    vehicle.getModel(),
                    type,
                    "Available",
                    vehicle.getBaseRentalRate(),
                    vehicle.getAverageRating());
            }
        }
    }

    private static void displayAllVehicles() {
        System.out.println("\n=== All Vehicles ===");
        System.out.printf("%-15s | %-12s | %-10s | %-15s | %-10s%n",
                "Model", "Type", "Status", "Daily Rate", "Avg Rating");
        System.out.println("-".repeat(70));
        
        for (Vehicle vehicle : agency.getVehicleFleet()) {
            String type = vehicle instanceof Car ? "Car" :
                         vehicle instanceof Truck ? "Truck" : "Motorcycle";
            
            System.out.printf("%-15s | %-12s | %-10s | $%-14.2f | %.1f/5.0%n",
                vehicle.getModel(),
                type,
                vehicle.isAvailable() ? "Available" : "Rented",
                vehicle.getBaseRentalRate(),
                vehicle.getAverageRating());
        }
    }

    private static void displayCustomerInfo() {
        System.out.println("\n=== Customer Information ===");
        String customerId = getStringInput("Enter customer ID: ");
        
        Customer customer = agency.getCustomer(customerId);
        if (customer != null) {
            System.out.println("\nCustomer Details:");
            System.out.println("Name: " + customer.getName());
            System.out.println("ID: " + customer.getCustomerId());
            System.out.println("Loyalty Points: " + customer.getLoyaltyPoints());
            System.out.println("Loyalty Tier: " + customer.getLoyaltyTier());
            
            List<Rating> ratings = customer.getRatings();
            if (!ratings.isEmpty()) {
                System.out.println("\nRatings Received:");
                for (Rating rating : ratings) {
                    System.out.printf("Rating: %d/5 - %s%n", 
                        rating.getScore(), 
                        rating.getComment());
                }
            }
        } else {
            System.out.println("Customer not found.");
        }
    }

    private static Vehicle findVehicleByModel(String model) {
        return agency.getVehicleFleet().stream()
                .filter(v -> v.getModel().toLowerCase().contains(model.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value < 0) {
                    System.out.println("Please enter a positive number.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
} 