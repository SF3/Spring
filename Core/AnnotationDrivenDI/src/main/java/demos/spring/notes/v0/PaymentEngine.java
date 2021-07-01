package demos.spring.notes.v0;

public class PaymentEngine {
    public PaymentEngine(String serverUrl) {
    }

    public boolean authorize(String cardNo, double amount) {
        return amount < 1000;
    }
}
