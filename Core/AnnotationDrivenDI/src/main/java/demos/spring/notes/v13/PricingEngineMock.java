package demos.spring.notes.v13;

import demos.spring.notes.common.PricingEngine;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("pricing")
@Scope("prototype")
public class PricingEngineMock implements PricingEngine {
    public double price(String itemNo, int quantity) {
        return quantity * 5.50;
    }
}
