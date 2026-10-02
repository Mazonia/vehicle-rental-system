package com.rental;

public class VehicleNotAvailableException extends RentalException {
    public VehicleNotAvailableException(String vehicleModel) {
        super("Vehicle " + vehicleModel + " is not available for rent");
    }
} 