package demos.spring.notes.v14;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class Program {
    public static void main(String[] args) throws Exception {
        try (AbstractApplicationContext context = buildContext()) {
            SampleShop shop = context.getBean("shop", SampleShop.class);
            System.out.println("----- Using shop '" + shop.getShopName() + "' -----");

            if (shop.isOpenDuringWeekends()) {
                System.out.println("\tShop open during weekends");
            } else {
                System.out.println("\tShop closed at weekends");
            }

            System.out.println("----- Trying to make purchase -----");
            if (shop.makePurchase("ABC123", 9, "DEF456GHI78")) {
                System.out.println("\tSample purchase succeeded");
            } else {
                System.out.println("\tSample purchase failed");
            }
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
