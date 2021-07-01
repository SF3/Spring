package demos.spring.notes.v0;

public class SampleShop {
    private PricingEngine pricingEngine;
    private StockCheckEngine stockCheckEngine;
    private PaymentEngine paymentEngine;

    public SampleShop() {
        pricingEngine = new PricingEngine(500, 10);
        stockCheckEngine = new StockCheckEngine();
        paymentEngine = new PaymentEngine("www.somewhere.com");
    }

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
