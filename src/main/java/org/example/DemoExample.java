package org.example;

import org.example.products.*;
import org.example.customer.*;
import org.example.cart.*;
import org.example.checkout.*;


import java.time.*;

public class DemoExample {
    public static void main(String[] args) {
        Cheese cheese = new Cheese("Cheese", 100.0, 10, LocalDate.now().plusDays(7), 0.4);
        TV tv = new TV("TV", 150.0, 5, 1.0);
        ScratchCard scratchCard = new ScratchCard("Mobile Scratch Card", 50.0, 20);
        
        Customer customer = new Customer("John Doe", 1000.0);
        Cart cart = new Cart();

        try {
            cart.add(cheese, 2);
            cart.add(tv, 3);
            cart.add(scratchCard, 1);
            
            checkout(customer, cart);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public static void checkout(Customer customer, Cart cart) throws CheckoutException {
        CheckoutService checkoutService = new CheckoutService();
        checkoutService.checkout(customer, cart);
    }
}
