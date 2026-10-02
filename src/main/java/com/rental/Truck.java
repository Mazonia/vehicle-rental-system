package com.rental;

public class Truck extends Vehicle {
    private static final double SURCHARGE = 50.0;

    public Truck(String vehicleId, String model, double baseRentalRate) {
        super(vehicleId, model, baseRentalRate);
    }

    @Override
    public double calculateRentalCost(int days) {
        if (days <= 0) {
            throw new IllegalArgumentException("Rental days must be positive");
        }
        return (getBaseRentalRate() * days) + SURCHARGE;
    }

    @Override
    public boolean isAvailableForRental() {
        return isAvailable();
    }
}
