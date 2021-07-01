package demos.spring.notes.v11;

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
    public SampleShop(@Qualifier("pricing") PricingEngine pricingEngine,
                      @Qualifier("payment") PaymentEngine paymentEngine,
                      @Qualifier("stock") StockCheckEngine stockCheckEngine) {
        super();
        this.pricingEngine = pricingEngine;
        this.paymentEngine = paymentEngine;
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
