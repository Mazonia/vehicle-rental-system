package com.rental;

import java.time.LocalDateTime;
import java.util.UUID;

public class RentalTransaction {
    private final String transactionId;
    private final Customer customer;
    private final Vehicle vehicle;
    private final int rentalDays;
    private final double totalCost;
    private final LocalDateTime rentalDate;

    private RentalTransaction(Builder builder) {
        this.transactionId = builder.transactionId;
        this.customer = builder.customer;
        this.vehicle = builder.vehicle;
        this.rentalDays = builder.rentalDays;
        this.totalCost = builder.totalCost;
        this.rentalDate = builder.rentalDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public LocalDateTime getRentalDate() {
        return rentalDate;
    }

    public static class Builder {
        private String transactionId;
        private Customer customer;
        private Vehicle vehicle;
        private int rentalDays;
        private double totalCost;
        private LocalDateTime rentalDate;

        public Builder() {
            this.transactionId = UUID.randomUUID().toString();
            this.rentalDate = LocalDateTime.now();
        }

        public Builder customer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder vehicle(Vehicle vehicle) {
            this.vehicle = vehicle;
            return this;
        }

        public Builder rentalDays(int days) {
            this.rentalDays = days;
            return this;
        }

        public Builder calculateCost() {
            if (vehicle != null && rentalDays > 0) {
                this.totalCost = vehicle.calculateRentalCost(rentalDays);
            }
            return this;
        }

        public RentalTransaction build() {
            if (customer == null || vehicle == null || rentalDays <= 0) {
                throw new IllegalStateException("Missing required fields");
            }
            return new RentalTransaction(this);
        }
    }
} 