package demos.spring.notes.v0;

public class Program {
    public static void main(String[] args) throws Exception {
        SampleShop shop = new SampleShop();
        if (shop.makePurchase("AB123", 20, "DEF456GHI78")) {
            System.out.println("Purchase Succeeded");
        } else {
            System.out.println("Purchase Failed");
        }
    }
}

