package com.rental;

import java.util.*;

public class RentalAgency {
    private final List<Vehicle> vehicleFleet;
    private final List<RentalTransaction> transactions;
    private final Map<String, Customer> customers;

    public RentalAgency() {
        this.vehicleFleet = new ArrayList<>();
        this.transactions = new ArrayList<>();
        this.customers = new HashMap<>();
    }

    public void addVehicle(Vehicle vehicle) {
        if (vehicle != null) {
            vehicleFleet.add(vehicle);
        }
    }

    public Vehicle findAvailableVehicle(String model) {
        for (Vehicle vehicle : vehicleFleet) {
            if (vehicle.isAvailable() && vehicle.getModel().toLowerCase().contains(model.toLowerCase())) {
                return vehicle;
            }
        }
        return null;
    }

    public void rentVehicle(String model, String customerId, int days) throws RentalException {
        Customer customer = customers.get(customerId);
        if (customer == null) {
            throw new RentalException("Customer not found");
        }

        Vehicle vehicle = findAvailableVehicle(model);
        if (vehicle == null) {
            throw new VehicleNotAvailableException(model);
        }

        vehicle.rent(customer, days);
        
        RentalTransaction transaction = new RentalTransaction.Builder()
            .customer(customer)
            .vehicle(vehicle)
            .rentalDays(days)
            .calculateCost()
            .build();

        transactions.add(transaction);
        
        int points = customer.calculateLoyaltyPoints(days);
        customer.addPoints(points);
        
        System.out.println("Rental transaction completed successfully");
        System.out.println("Transaction ID: " + transaction.getTransactionId());
        System.out.println("Added " + points + " points. Current tier: " + customer.getLoyaltyTier());
    }

    public void returnVehicle(Vehicle vehicle) {
        vehicle.setAvailable(true);
        System.out.println("Vehicle returned successfully");
    }

    public void generateRentalReport() {
        System.out.println("Rental Agency Report:");
        for (Vehicle vehicle : vehicleFleet) {
            System.out.println(vehicle.getModel() + " - " + (vehicle.isAvailable() ? "Available" : "Rented"));
        }
    }

    public void rateVehicle(Vehicle vehicle, Customer customer, int score, String comment) {
        Rating rating = new Rating(score, comment, customer.getName());
        vehicle.addRating(rating);
    }

    public void rateCustomer(Customer customer, int score, String comment) {
        Rating rating = new Rating(score, comment, "RentalAgency");
        customer.addRating(rating);
    }

    public List<Vehicle> getVehicleFleet() {
        return new ArrayList<>(vehicleFleet); // Return a copy to maintain encapsulation
    }

    public void generateTransactionReport() {
        System.out.println("\n=== Transaction History ===");
        for (RentalTransaction transaction : transactions) {
            System.out.printf("ID: %s | Customer: %s | Vehicle: %s | Days: %d | Cost: $%.2f%n",
                transaction.getTransactionId(),
                transaction.getCustomer().getName(),
                transaction.getVehicle().getModel(),
                transaction.getRentalDays(),
                transaction.getTotalCost());
        }
    }

    public void registerCustomer(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
    }

    public Customer getCustomer(String customerId) {
        return customers.get(customerId);
    }

    public void rateService(String model, Customer customer, int rating, String comment) {
        Vehicle vehicle = findAvailableVehicle(model);
        if (vehicle != null) {
            Rating newRating = new Rating(rating, comment, customer.getName());
            vehicle.addRating(newRating);
            System.out.println("Rating added successfully!");
        } else {
            System.out.println("Vehicle not found.");
        }
    }

    public static void main(String[] args) {
        // Create a rental agency
        RentalAgency agency = new RentalAgency();

        // Add some vehicles
        Car car = new Car("C001", "Toyota Camry", 50.0);
        Truck truck = new Truck("T001", "Ford F-150", 80.0);
        Motorcycle motorcycle = new Motorcycle("M001", "Honda CBR", 40.0);

        agency.addVehicle(car);
        agency.addVehicle(truck);
        agency.addVehicle(motorcycle);

        // Create a customer
        Customer customer = new Customer("CUST001", "John Doe");
        agency.registerCustomer(customer);

        // Demonstrate rental operations
        try {
            agency.generateRentalReport();  // Show initial state
            agency.rentVehicle("Toyota Camry", "CUST001", 5);
            agency.generateRentalReport();  // Show state after rental
            agency.returnVehicle(car);
            agency.generateRentalReport();  // Show state after return
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
