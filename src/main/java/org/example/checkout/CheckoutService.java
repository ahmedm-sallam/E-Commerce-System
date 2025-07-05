package org.example.checkout;

import org.example.cart.*;
import org.example.customer.*;
import org.example.products.*;
import org.example.shipping.*;

import java.util.*;

public class CheckoutService {
    private ShippingService shippingService;

    public CheckoutService() {
        this.shippingService = new ShippingService();
    }

    public void checkout(Customer customer, Cart cart) throws CheckoutException {
        if (cart.isEmpty()) {
            throw new CheckoutException("Cart is empty");
        }

        validateCartItems(cart);

        double subtotal = cart.getSubtotal();
        List<Shippable> shippableItems = getShippableItems(cart);
        double shippingFee = shippingService.calculateShippingFee(shippableItems);
        double totalAmount = subtotal + shippingFee;

        if (customer.getBalance() < totalAmount) {
            throw new CheckoutException("Insufficient customer balance");
        }

        if (!shippableItems.isEmpty()) {
            shippingService.processShipping(shippableItems);
        }

        printCheckoutReceipt(cart, subtotal, shippingFee, totalAmount);
        customer.deductBalance(totalAmount);
        updateProductQuantities(cart);
        cart.clear();
    }

    private void validateCartItems(Cart cart) throws CheckoutException {
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            
            if (!product.isInStock()) {
                throw new CheckoutException("Product out of stock");
            }
            
            if (product instanceof Expirable && ((Expirable) product).isExpired()) {
                throw new CheckoutException("Product expired");
            }
            
            if (item.getQuantity() > product.getQuantity()) {
                throw new CheckoutException("Insufficient stock");
            }
        }
    }

    private List<Shippable> getShippableItems(Cart cart) {
        List<Shippable> shippableItems = new ArrayList<>();
        for (CartItem item : cart.getItems()) {
            if (item.getProduct() instanceof Shippable) {
                for (int i = 0; i < item.getQuantity(); i++) {
                    shippableItems.add((Shippable) item.getProduct());
                }
            }
        }
        return shippableItems;
    }

    private void updateProductQuantities(Cart cart) {
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            product.reduceQuantity(item.getQuantity());
        }
    }

    private void printCheckoutReceipt(Cart cart, double subtotal, double shippingFee, double totalAmount) {
        System.out.println("** Checkout receipt **");
        
        for (CartItem item : cart.getItems()) {
            System.out.printf("%dx %s %.0f%n", 
                item.getQuantity(), 
                item.getProduct().getName(), 
                item.getTotalPrice());
        }
        
        System.out.println("----------------------");
        System.out.printf("Subtotal %.0f%n", subtotal);
        System.out.printf("Shipping %.0f%n", shippingFee);
        System.out.printf("Amount %.0f%n", totalAmount);
    }
}
