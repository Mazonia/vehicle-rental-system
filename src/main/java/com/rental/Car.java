package com.rental;

public class Car extends Vehicle {
    private static final double LONG_TERM_DISCOUNT = 0.10; // 10% discount for rentals > 7 days

    public Car(String vehicleId, String model, double baseRentalRate) {
        super(vehicleId, model, baseRentalRate);
    }

    @Override
    public double calculateRentalCost(int days) {
        if (days <= 0) {
            throw new IllegalArgumentException("Rental days must be positive");
        }
        double baseCost = getBaseRentalRate() * days;
        return days > 7 ? baseCost * (1 - LONG_TERM_DISCOUNT) : baseCost;
    }

    @Override
    public boolean isAvailableForRental() {
        return isAvailable();
    }
}
