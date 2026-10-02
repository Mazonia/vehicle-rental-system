package com.rental;

public class LoyaltyProgram {
    private static final int POINTS_PER_DAY = 10;
    private static final int SILVER_THRESHOLD = 500;
    private static final int GOLD_THRESHOLD = 1000;

    public static int calculatePoints(int rentalDays) {
        return rentalDays * POINTS_PER_DAY;
    }

    public static String determineTier(int points) {
        if (points >= GOLD_THRESHOLD) {
            return "GOLD";
        } else if (points >= SILVER_THRESHOLD) {
            return "SILVER";
        }
        return "BRONZE";
    }

    public static double getDiscountRate(String tier) {
        switch (tier.toUpperCase()) {
            case "GOLD":
                return 0.15; // 15% discount
            case "SILVER":
                return 0.10; // 10% discount
            default:
                return 0.0;  // No discount for BRONZE
        }
    }
} 