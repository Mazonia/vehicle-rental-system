package com.rental;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private final String customerId;
    private final String name;
    private int loyaltyPoints;
    private final List<Rating> ratings;

    public Customer(String customerId, String name) {
        if (customerId == null || name == null) {
            throw new IllegalArgumentException("Customer ID and name cannot be null");
        }
        this.customerId = customerId;
        this.name = name;
        this.loyaltyPoints = 0;
        this.ratings = new ArrayList<>();
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void addPoints(int points) {
        if (points > 0) {
            this.loyaltyPoints += points;
        }
    }

    public String getLoyaltyTier() {
        if (loyaltyPoints >= 1000) return "GOLD";
        if (loyaltyPoints >= 500) return "SILVER";
        return "BRONZE";
    }

    public int calculateLoyaltyPoints(int rentalDays) {
        return rentalDays * 10; // 10 points per rental day
    }

    public void addRating(Rating rating) {
        ratings.add(rating);
    }

    public List<Rating> getRatings() {
        return new ArrayList<>(ratings);
    }
}