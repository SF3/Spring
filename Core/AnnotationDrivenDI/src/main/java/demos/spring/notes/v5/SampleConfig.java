package demos.spring.notes.v5;

import demos.spring.notes.common.PaymentEngine;
import demos.spring.notes.common.PricingEngine;
import demos.spring.notes.common.StockCheckEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleConfig {
    @Bean
    public PricingEngine foo() {
        return new PricingEngineMock();
    }

    @Bean
    public PaymentEngine bar() {
        return new PaymentEngineMock();
    }

    @Bean
    public StockCheckEngine zed() {
        return new StockCheckEngineMock();
    }
}
