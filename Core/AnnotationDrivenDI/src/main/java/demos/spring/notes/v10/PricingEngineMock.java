package demos.spring.notes.v10;

import demos.spring.notes.common.PricingEngine;
import org.springframework.stereotype.Component;

@Component
public class PricingEngineMock implements PricingEngine {
    public double price(String itemNo, int quantity) {
        return quantity * 5.50;
    }
}
