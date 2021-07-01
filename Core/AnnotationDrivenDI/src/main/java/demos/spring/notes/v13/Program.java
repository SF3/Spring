package demos.spring.notes.v13;

import demos.spring.notes.common.Shop;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.io.PrintStream;

public class Program {
    public static void main(String[] args) throws Exception {
        try (AbstractApplicationContext context = buildContext()) {
            SampleShop shop1 = context.getBean("shop", SampleShop.class);
            SampleShop shop2 = context.getBean("shop", SampleShop.class);
            useShop(shop1);
            useShop(shop2);
            testDependencies(shop1, shop2);
        }
    }

    private static void testDependencies(SampleShop shop1, SampleShop shop2) {
        PrintStream out = System.out;
        if (shop1 == shop2) {
            out.println("There is a single shop");
        } else {
            out.println("There are multiple shops");
        }
        if (shop1.getPricingEngine() == shop2.getPricingEngine()) {
            out.println("There is a single pricing engine");
        } else {
            out.println("There are multiple pricing engines");
        }
        if (shop1.getPaymentEngine() == shop2.getPaymentEngine()) {
            out.println("There is a single payment engine");
        } else {
            out.println("There are multiple payment engines");
        }
        if (shop1.getStockCheckEngine() == shop2.getStockCheckEngine()) {
            out.println("There is a single stock check engine");
        } else {
            out.println("There are multiple stock check engines");
        }
    }

    private static void useShop(Shop shop) {
        if (shop.makePurchase("AB123", 20, "DEF456GHI78")) {
            System.out.println("Purchase Succeeded");
        } else {
            System.out.println("Purchase Failed");
        }
    }

    private static AbstractApplicationContext buildContext() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(SampleConfig.class);
        context.register(SampleShop.class);
        context.refresh();
        return context;
    }
}
