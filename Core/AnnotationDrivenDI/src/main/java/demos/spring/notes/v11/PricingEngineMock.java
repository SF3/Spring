package demos.spring.notes.v11;

import demos.spring.notes.common.PricingEngine;
import org.springframework.stereotype.Component;

@Component("pricing")
public class PricingEngineMock implements PricingEngine {
    public double price(String itemNo, int quantity) {
        return quantity * 5.50;
    }
}
