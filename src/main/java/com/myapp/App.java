package com.myapp;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        OrderService service = new OrderService();

        Order smallOrder = new Order("Laptop", 2, 999.99);
        Order bulkOrder = new Order("Mouse", 15, 29.99);

        System.out.println("=== E-Commerce Order Calculator ===");
        System.out.println(service.generateSummary(smallOrder));
        System.out.println(service.generateSummary(bulkOrder));
    }
}
