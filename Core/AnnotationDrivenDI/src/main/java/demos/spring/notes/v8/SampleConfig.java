package demos.spring.notes.v8;

import demos.spring.notes.common.PaymentEngine;
import demos.spring.notes.common.PricingEngine;
import demos.spring.notes.common.StockCheckEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleConfig {
    @Bean(name = "pricing")
    public PricingEngine foo() {
        return new PricingEngineMock();
    }

    @Bean(name = "payment")
    public PaymentEngine bar() {
        return new PaymentEngineMock();
    }

    @Bean(name = "stock")
    public StockCheckEngine zed() {
        return new StockCheckEngineMock();
    }
}
