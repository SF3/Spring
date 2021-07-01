package demos.spring.notes.common;

public interface PaymentEngine {
    public boolean authorize(String cardNo, double amount);
}