package demos.spring.cdi.v1;

import demos.spring.notes.common.PaymentEngine;
import demos.spring.notes.common.PricingEngine;
import demos.spring.notes.common.Shop;
import demos.spring.notes.common.StockCheckEngine;

import javax.inject.Inject;
import javax.inject.Named;

@Named("shop")
public class SampleShop implements Shop {
    private PricingEngine pricingEngine;
    private StockCheckEngine stockCheckEngine;
    private PaymentEngine paymentEngine;

    @Inject
    public void setPaymentEngine(@Named("payment") PaymentEngine paymentEngine) {
        this.paymentEngine = paymentEngine;
    }

    @Inject
    public void setPricingEngine(@Named("pricing") PricingEngine pricingEngine) {
        this.pricingEngine = pricingEngine;
    }

    @Inject
    public void setStockCheckEngine(@Named("stock") StockCheckEngine stockCheckEngine) {
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
