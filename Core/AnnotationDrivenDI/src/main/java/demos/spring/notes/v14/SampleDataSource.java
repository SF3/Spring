package demos.spring.notes.v14;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("dataSource")
public class SampleDataSource {
    public static List<String> buildBannedCards() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("RST456GHI78");
        list.add("UVW456GHI78");
        list.add("XYZ456GHI78");
        return list;
    }

    public int getDiscountAmount() {
        return 500;
    }

    public int getPercentageDiscount() {
        return 10;
    }

    public List<Double> getPrices() {
        ArrayList<Double> list = new ArrayList<Double>();
        list.add(1.20);
        list.add(3.40);
        list.add(5.60);
        return list;
    }

    public Map<String, Integer> buildStockData() {
        HashMap<String, Integer> stockData = new HashMap<String, Integer>();
        stockData.put("ABC123", 10);
        stockData.put("DEF456", 20);
        stockData.put("GHI789", 30);
        return stockData;
    }

    public String getOpenDays() {
        return "Monday Tuesday Thursday Saturday";
    }

    public String getCEO() {
        return "Steve Jobs";
    }
}

