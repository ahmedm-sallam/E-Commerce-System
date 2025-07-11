package org.example;

import org.example.products.*;
import org.example.customer.*;
import org.example.cart.*;
import org.example.checkout.*;

import java.time.*;

public class Main {
    public static void main(String[] args) {
        Cheese cheese = new Cheese("Cheese", 100.0, 10, LocalDate.now().plusDays(7), 0.4);
        Biscuits biscuits = new Biscuits("Biscuits", 150.0, 5, LocalDate.now().plusDays(30), 0.7);
        
        Customer customer = new Customer("John Doe", 1000.0);
        Cart cart = new Cart();

        try {
            cart.add(cheese, 2);
            cart.add(biscuits, 1);
            
            checkout(customer, cart);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public static void checkout(Customer customer, Cart cart) throws CheckoutException {
        CheckoutService checkoutService = new CheckoutService();
        checkoutService.checkout(customer, cart);
        System.out.printf("Customer balance after payment: $%.2f%n", customer.getBalance());
    }
}