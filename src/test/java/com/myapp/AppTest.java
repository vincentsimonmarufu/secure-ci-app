package com.myapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        orderService = new OrderService();
    }

    @Test
    public void testSubtotalCalculation() {
        Order order = new Order("Laptop", 2, 999.99);
        assertEquals(1999.98, order.getSubtotal(), 0.01);
    }

    @Test
    public void testNoBulkDiscountBelowThreshold() {
        Order order = new Order("Mouse", 5, 29.99);
        assertEquals(0.0, orderService.calculateDiscount(order), 0.01);
    }

    @Test
    public void testBulkDiscountAppliedAtThreshold() {
        Order order = new Order("Mouse", 10, 29.99);
        double expectedDiscount = 29.99 * 10 * 0.10;
        assertEquals(expectedDiscount, orderService.calculateDiscount(order), 0.01);
    }

    @Test
    public void testTotalWithTaxAndDiscount() {
        Order order = new Order("Keyboard", 10, 50.00);
        double subtotal = 500.00;
        double discount = 50.00;
        double afterDiscount = 450.00;
        double tax = afterDiscount * 0.15;
        double expected = afterDiscount + tax;
        assertEquals(expected, orderService.calculateTotal(order), 0.01);
    }

    @Test
    public void testTotalWithTaxNoDiscount() {
        Order order = new Order("Laptop", 1, 1000.00);
        double expected = 1000.00 * 1.15;
        assertEquals(expected, orderService.calculateTotal(order), 0.01);
    }

    @Test
    public void testInvalidQuantityThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Order("Phone", 0, 299.99);
        });
    }

    @Test
    public void testNegativePriceThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Order("Phone", 1, -10.00);
        });
    }

    @Test
    public void testGenerateSummaryContainsProductName() {
        Order order = new Order("Tablet", 3, 199.99);
        String summary = orderService.generateSummary(order);
        assertTrue(summary.contains("Tablet"));
    }
}