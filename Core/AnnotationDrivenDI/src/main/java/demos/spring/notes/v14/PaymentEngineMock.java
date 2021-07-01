package demos.spring.notes.v14;

import demos.spring.notes.common.PaymentEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("payment")
public class PaymentEngineMock implements PaymentEngine {
    private List<String> bannedCards;

    @Autowired
    public PaymentEngineMock(@Value("#{T(demos.spring.notes.v14.SampleDataSource).buildBannedCards()}") List<String> bannedCards) {
        super();
        this.bannedCards = bannedCards;
    }

    public boolean authorize(String cardNo, double amount) {
        if (bannedCards.contains(cardNo)) {
            return false;
        }
        return amount < 1000;
    }
}
