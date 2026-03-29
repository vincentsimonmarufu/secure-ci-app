package com.myapp;

public class OrderService {

    private static final double BULK_DISCOUNT_THRESHOLD = 10;
    private static final double BULK_DISCOUNT_RATE = 0.10;
    private static final double TAX_RATE = 0.15;

    public double calculateDiscount(Order order) {
        if (order.getQuantity() >= BULK_DISCOUNT_THRESHOLD) {
            return order.getSubtotal() * BULK_DISCOUNT_RATE;
        }
        return 0.0;
    }

    public double calculateTax(double amount) {
        return amount * TAX_RATE;
    }

    public double calculateTotal(Order order) {
        double subtotal = order.getSubtotal();
        double discount = calculateDiscount(order);
        double discountedAmount = subtotal - discount;
        double tax = calculateTax(discountedAmount);
        return discountedAmount + tax;
    }

    public String generateSummary(Order order) {
        double subtotal = order.getSubtotal();
        double discount = calculateDiscount(order);
        double total = calculateTotal(order);

        return String.format(
            "Order Summary: %s | Qty: %d | Subtotal: $%.2f | Discount: $%.2f | Total: $%.2f",
            order.getProductName(),
            order.getQuantity(),
            subtotal,
            discount,
            total
        );
    }
}