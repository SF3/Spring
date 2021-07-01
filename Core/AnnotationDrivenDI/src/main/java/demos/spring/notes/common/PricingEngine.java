package demos.spring.notes.common;

public interface PricingEngine {
    public double price(String itemNo, int quantity);
}