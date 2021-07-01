package demos.spring.notes.v7;

import demos.spring.notes.common.PaymentEngine;
import demos.spring.notes.common.PricingEngine;
import demos.spring.notes.common.Shop;
import demos.spring.notes.common.StockCheckEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("shop")
public class SampleShop implements Shop {
    private PricingEngine pricingEngine;
    private StockCheckEngine stockCheckEngine;
    private PaymentEngine paymentEngine;

    @Autowired
    public void startup(@Qualifier("payment") PaymentEngine paymentEngine,
                        @Qualifier("pricing") PricingEngine pricingEngine,
                        @Qualifier("stock") StockCheckEngine stockCheckEngine) {
        this.paymentEngine = paymentEngine;
        this.pricingEngine = pricingEngine;
        this.stockCheckEngine = stockCheckEngine;
    }

    @Override
    public boolean makePurchase(String itemNo, int quantity, String cardNo) {
        if (stockCheckEngine.check(itemNo) >= quantity) {
            double charge = pricingEngine.price(itemNo, quantity);
            if (paymentEngine.authorize(cardNo, charge)) {
                return true;
            }
        }
        return false;
    }
}
