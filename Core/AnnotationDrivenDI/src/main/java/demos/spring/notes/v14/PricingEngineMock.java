package demos.spring.notes.v14;

import demos.spring.notes.common.PricingEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("pricing")
public class PricingEngineMock implements PricingEngine {
    private List<Double> prices;
    private int priceCount = -1;
    private double minimumDiscountAmount;
    private int percentageDiscount;

    @Value("#{dataSource.discountAmount}")
    public void setMinimumDiscountAmount(double minimumDiscountAmount) {
        this.minimumDiscountAmount = minimumDiscountAmount;
    }

    @Value("#{dataSource.percentageDiscount * 2}")
    public void setPercentageDiscount(int percentageDiscount) {
        this.percentageDiscount = percentageDiscount;
    }

    @Value("#{dataSource.prices}")
    public void setPrices(List<Double> prices) {
        this.prices = prices;
    }

    public double price(String itemNo, int quantity) {
        if (++priceCount == prices.size()) {
            priceCount = 0;
        }
        double price = quantity * prices.get(priceCount);
        if (price >= minimumDiscountAmount) {
            price = price - (price * (percentageDiscount / 100));
        }
        return price;
    }
}
