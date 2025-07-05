package org.example.products;

import java.time.*;

public class Biscuits extends Product implements Expirable, Shippable {
    private LocalDate expirationDate;
    private double weight;

    public Biscuits(String name, double price, int quantity, LocalDate expirationDate, double weight) {
        super(name, price, quantity);
        if (expirationDate == null) {
            throw new IllegalArgumentException("Expiration date cannot be null");
        }
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be positive");
        }
        this.expirationDate = expirationDate;
        this.weight = weight;
    }

    @Override
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    @Override
    public boolean isExpired() {
        return LocalDate.now().isAfter(expirationDate);
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public boolean isInStock() {
        return super.isInStock() && !isExpired();
    }
}
