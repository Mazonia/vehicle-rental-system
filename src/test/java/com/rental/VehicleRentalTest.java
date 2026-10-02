package com.rental;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class VehicleRentalTest {
    private RentalAgency agency;
    private Customer customer;
    private Car car;
    private Truck truck;
    private Motorcycle motorcycle;

    @BeforeEach
    void setUp() {
        agency = new RentalAgency();
        customer = new Customer("C001", "John Doe");
        car = new Car("CAR001", "Toyota Camry", 50.0);
        truck = new Truck("TRK001", "Ford F-150", 80.0);
        motorcycle = new Motorcycle("MTR001", "Honda CBR", 40.0);
        
        agency.registerCustomer(customer);
        agency.addVehicle(car);
        agency.addVehicle(truck);
        agency.addVehicle(motorcycle);
    }

    @Test
    void testVehicleRental() throws RentalException {
        agency.rentVehicle("Camry", customer.getCustomerId(), 5);
        assertFalse(car.isAvailable());
        
        agency.returnVehicle(car);
        assertTrue(car.isAvailable());
    }

    @Test
    void testLoyaltyPoints() throws RentalException {
        agency.rentVehicle("Camry", customer.getCustomerId(), 5);
        assertEquals(50, customer.getLoyaltyPoints()); // 10 points per day
        assertEquals("BRONZE", customer.getLoyaltyTier());
    }

    @Test
    void testRatingSystem() {
        Rating rating = new Rating(5, "Great car!", customer.getName());
        car.addRating(rating);
        assertEquals(5.0, car.getAverageRating(), 0.01);
    }

    @Test
    void testInvalidRental() {
        assertThrows(RentalException.class, () -> {
            agency.rentVehicle("NonExistent", customer.getCustomerId(), 5);
        });
    }

    @Test
    void testVehicleCalculations() {
        // Test car rental with discount (10% discount for rentals > 7 days)
        double expectedCarCost = 50.0 * 10 * 0.9; // 10 days with 10% discount
        assertEquals(expectedCarCost, car.calculateRentalCost(10), 0.01);
        
        // Test truck with surcharge
        double expectedTruckCost = (80.0 * 5) + 50.0; // 5 days plus 50.0 surcharge
        assertEquals(expectedTruckCost, truck.calculateRentalCost(5), 0.01);
        
        // Test motorcycle basic rate
        double expectedMotorcycleCost = 40.0 * 5; // 5 days at basic rate
        assertEquals(expectedMotorcycleCost, motorcycle.calculateRentalCost(5), 0.01);
    }

    @Test
    void testInvalidRentalDays() {
        assertThrows(IllegalArgumentException.class, () -> {
            car.calculateRentalCost(0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            car.calculateRentalCost(-1);
        });
    }

    @Test
    void testVehicleEquality() {
        Car car2 = new Car("CAR001", "Toyota Camry", 50.0);
        assertEquals(car, car2);
        assertNotEquals(car, truck);
    }

    @Test
    void testRatingValidation() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Rating(0, "Invalid rating", customer.getName());
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Rating(6, "Invalid rating", customer.getName());
        });
    }
} 