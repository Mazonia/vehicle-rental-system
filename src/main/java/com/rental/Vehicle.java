package com.rental;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Abstract Base Class: Vehicle
public abstract class Vehicle implements Rentable {
    private String vehicleId;
    private String model;
    private double baseRentalRate;
    private boolean isAvailable;
    private List<Rating> ratings;

    public Vehicle(String vehicleId, String model, double baseRentalRate) {
        if (vehicleId == null || model == null || baseRentalRate <= 0) {
            throw new IllegalArgumentException("Invalid input for vehicle details");
        }
        this.vehicleId = vehicleId;
        this.model = model;
        this.baseRentalRate = baseRentalRate;
        this.isAvailable = true;
        this.ratings = new ArrayList<>();
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getModel() {
        return model;
    }

    public double getBaseRentalRate() {
        return baseRentalRate;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public abstract double calculateRentalCost(int days);

    public abstract boolean isAvailableForRental();

    public void addRating(Rating rating) {
        ratings.add(rating);
    }

    public double getAverageRating() {
        if (ratings.isEmpty()) return 0.0;
        return ratings.stream()
                .mapToInt(Rating::getScore)
                .average()
                .orElse(0.0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle)) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(vehicleId, vehicle.vehicleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleId);
    }

    @Override
    public String toString() {
        return String.format("Vehicle{id='%s', model='%s', rate=%.2f, available=%s}",
                vehicleId, model, baseRentalRate, isAvailable);
    }

    // Implement Rentable interface methods
    @Override
    public void rent(Customer customer, int days) throws RentalException {
        if (!isAvailable) {
            throw new VehicleNotAvailableException(model);
        }
        isAvailable = false;
    }

    @Override
    public void returnVehicle() {
        isAvailable = true;
    }

    public List<Rating> getRatings() {
        return new ArrayList<>(ratings);
    }
}
