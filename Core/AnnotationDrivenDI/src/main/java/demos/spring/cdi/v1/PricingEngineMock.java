package demos.spring.cdi.v1;

import demos.spring.notes.common.PricingEngine;

import javax.inject.Named;

@Named("pricing")
public class PricingEngineMock implements PricingEngine {
    public double price(String itemNo, int quantity) {
        return quantity * 5.50;
    }
}
