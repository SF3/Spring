package demos.spring.notes.v14;

import demos.spring.notes.common.PaymentEngine;
import demos.spring.notes.common.PricingEngine;
import demos.spring.notes.common.Shop;
import demos.spring.notes.common.StockCheckEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("shop")
public class SampleShop implements Shop {
    private String shopName;
    private boolean openDuringWeekends;
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

    public String getShopName() {
        return shopName;
    }

    @Value("#{dataSource.CEO.toUpperCase() == 'STEVE JOBS' ? 'Apple' : 'Microsoft'}")
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public boolean isOpenDuringWeekends() {
        return openDuringWeekends;
    }

    @Value("#{dataSource.openDays matches '.*(Saturday|Sunday).*'}")
    public void setOpenDuringWeekends(boolean openDuringWeekends) {
        this.openDuringWeekends = openDuringWeekends;
    }
}
