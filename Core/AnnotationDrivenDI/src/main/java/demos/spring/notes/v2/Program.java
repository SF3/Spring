package demos.spring.notes.v2;

import demos.spring.notes.common.Shop;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class Program {
    public static void main(String[] args) throws Exception {
        try (AbstractApplicationContext context = buildContext()) {
            Shop shop = context.getBean("shop", Shop.class);
            useShop(shop);
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
        String currentPackageName = Program.class.getPackage().getName();
        context.scan(currentPackageName);
        context.refresh();
        return context;
    }
}
