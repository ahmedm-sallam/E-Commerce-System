package org.example.shipping;

import org.example.products.Shippable;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ShippingService {
    private static final double SHIPPING_RATE_PER_KG = 30.0;

    public double calculateShippingFee(List<Shippable> shippableItems) {
        if (shippableItems == null || shippableItems.isEmpty()) {
            return 0.0;
        }

        double totalWeight = shippableItems.stream()
                .mapToDouble(Shippable::getWeight)
                .sum();

        return totalWeight * SHIPPING_RATE_PER_KG;
    }

    public void processShipping(List<Shippable> shippableItems) {
        if (shippableItems == null || shippableItems.isEmpty()) {
            return;
        }

        System.out.println("** Shipment notice **");
        
        Map<String, Integer> itemCounts = new HashMap<>();
        Map<String, Double> itemWeights = new HashMap<>();
        
        for (Shippable item : shippableItems) {
            String name = item.getName();
            itemCounts.put(name, itemCounts.getOrDefault(name, 0) + 1);
            itemWeights.put(name, item.getWeight());
        }
        
        double totalWeight = 0.0;
        for (Map.Entry<String, Integer> entry : itemCounts.entrySet()) {
            String name = entry.getKey();
            int quantity = entry.getValue();
            double weight = itemWeights.get(name) * 1000;
            System.out.printf("%dx %s %.0fg%n", quantity, name, weight);
            totalWeight += itemWeights.get(name) * quantity;
        }
        
        System.out.printf("Total package weight %.1fkg%n", totalWeight);
    }
}
