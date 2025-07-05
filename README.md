# E-Commerce System

A clean, production-ready e-commerce system implemented in Java featuring product management, shopping cart functionality, customer operations, and automated checkout with shipping integration.

## Overview

This system demonstrates professional Java development practices with a focus on clean code, proper separation of concerns, and realistic business logic. The implementation handles various product types, customer transactions, inventory management, and shipping calculations without unnecessary complexity.

## Core Features

### Product Management
- **Abstract Product Base**: Common properties (name, price, quantity) with extensible design
- **Expirable Products**: Cheese and Biscuits with automatic expiration validation
- **Shippable Products**: Physical items (Cheese, Biscuits, TV) with weight-based shipping
- **Digital Products**: Mobile and Scratch Cards requiring no physical shipping
- **Inventory Control**: Real-time stock validation and automatic updates

### Shopping Cart Operations
- Add products with quantity constraints
- Automatic stock availability checking
- Expiration date validation for perishable items
- Flexible item management and removal
- Subtotal calculations

### Customer System
- Balance management with validation
- Secure payment processing
- Transaction history tracking
- Insufficient funds protection

### Checkout Process
- Multi-stage validation (cart, stock, expiration, balance)
- Automated shipping calculations for physical items
- Comprehensive receipt generation
- Inventory updates post-payment
- Error handling for edge cases

### Shipping Integration
- Weight-based fee calculation (30.0 per kg)
- Automatic grouping of shippable items
- Detailed shipment notices with item breakdown
- Interface-based design for extensibility

## Technical Architecture

### Package Structure
```
org.example/
├── products/              # Product domain
│   ├── Product.java           # Abstract base class
│   ├── Expirable.java         # Expiration interface
│   ├── Shippable.java         # Shipping interface
│   ├── Cheese.java            # Expirable + Shippable
│   ├── Biscuits.java          # Expirable + Shippable
│   ├── TV.java                # Shippable only
│   ├── Mobile.java            # Standard product
│   └── ScratchCard.java       # Digital product
├── customer/              # Customer domain
│   └── Customer.java          # Customer entity
├── cart/                  # Shopping cart domain
│   ├── Cart.java              # Cart management
│   └── CartItem.java          # Individual cart items
├── checkout/              # Transaction processing
│   ├── CheckoutService.java   # Main checkout logic
│   └── CheckoutException.java # Domain exceptions
├── shipping/              # Shipping calculations
│   └── ShippingService.java   # Shipping logic
└── Main.java              # Application entry point
```

### Design Principles
- **Single Responsibility**: Each class has a focused purpose
- **Interface Segregation**: Separate concerns (Expirable, Shippable)
- **Open/Closed**: Extensible product types without modification
- **Dependency Inversion**: Service-based architecture

## Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Build and Run
```bash
# Compile the project
mvn compile

# Run the main demo
java -cp target/classes org.example.Main

# Run extended demo with shipping
java -cp target/classes org.example.DemoExample
```

## Usage Examples

### Basic Product Setup
```java
Cheese cheese = new Cheese("Cheese", 100.0, 10, LocalDate.now().plusDays(7), 0.4);
TV tv = new TV("TV", 150.0, 5, 1.0);
ScratchCard scratchCard = new ScratchCard("Mobile Scratch Card", 50.0, 20);
```

### Customer and Cart Operations
```java
Customer customer = new Customer("John Doe", 1000.0);
Cart cart = new Cart();

cart.add(cheese, 2);
cart.add(tv, 3);
cart.add(scratchCard, 1);
```

### Checkout Process
```java
CheckoutService checkoutService = new CheckoutService();
checkoutService.checkout(customer, cart);
```

### Expected Console Output
```
** Shipment notice **
2x Cheese 800g
3x TV 3000g
Total package weight 3.8kg
** Checkout receipt **
2x Cheese 200
3x TV 450
1x Mobile Scratch Card 50
----------------------
Subtotal 700
Shipping 114
Amount 814
```

## Business Rules

### Product Validation
- Products must have positive price and quantity
- Expired products are automatically considered out of stock
- Shippable items require positive weight values

### Cart Management
- Cannot add more items than available in stock
- Cannot add expired products
- Duplicate products increase existing cart item quantity

### Checkout Validation
- Cart cannot be empty
- Customer balance must cover total amount (subtotal + shipping)
- All items must be in stock at checkout time
- Expired products block the transaction

### Shipping Calculations
- Rate: 30.0 per kilogram
- Only applies to products implementing Shippable interface
- Weight displayed in grams for shipment notices

## Error Handling

The system provides comprehensive error handling for:

- **Empty Cart**: Prevents checkout of empty carts
- **Insufficient Balance**: Validates customer can afford the purchase
- **Stock Depletion**: Handles out-of-stock scenarios
- **Product Expiration**: Blocks expired product transactions
- **Invalid Quantities**: Prevents negative or zero quantities
- **Weight Validation**: Ensures positive weights for shippable items

## Extension Points

### Adding New Product Types
1. Extend the `Product` base class
2. Implement `Expirable` and/or `Shippable` interfaces as needed
3. Add any type-specific properties and validation

### Custom Shipping Logic
- Modify `ShippingService` for different rate structures
- Implement volume-based or distance-based calculations
- Add shipping tiers or promotional rates

### Payment Integration
- Extend `Customer` class for multiple payment methods
- Add transaction logging and audit trails
- Implement refund and credit functionality

## Testing

The project includes demonstration classes that exercise various scenarios:

- **Normal Operations**: Standard purchase flow
- **Edge Cases**: Empty carts, insufficient funds
- **Validation**: Stock limits, expiration dates
- **Error Conditions**: Invalid inputs, business rule violations

## Development Notes

- **No Comments**: Code is self-documenting with clear naming
- **Clean Architecture**: Separation of concerns across packages
- **Professional Standards**: Production-quality validation and error handling
- **Extensible Design**: Easy to add new features without breaking existing code

## License

This project is created for educational and demonstration purposes.
