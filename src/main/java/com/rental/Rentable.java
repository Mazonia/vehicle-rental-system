package com.rental;

public interface Rentable {
    void rent(Customer customer, int days) throws RentalException;
    void returnVehicle();
}
