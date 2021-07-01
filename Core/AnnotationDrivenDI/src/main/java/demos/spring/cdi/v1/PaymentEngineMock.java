package demos.spring.cdi.v1;

import demos.spring.notes.common.PaymentEngine;

import javax.inject.Named;

@Named("payment")
public class PaymentEngineMock implements PaymentEngine {
    public boolean authorize(String cardNo, double amount) {
        return amount < 1000;
    }
}
