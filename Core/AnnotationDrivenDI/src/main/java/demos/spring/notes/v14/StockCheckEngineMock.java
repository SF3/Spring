package demos.spring.notes.v14;

import demos.spring.notes.common.StockCheckEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("stock")
public class StockCheckEngineMock implements StockCheckEngine {
    private Map<String, Integer> testData;

    @Value("#{dataSource.buildStockData()}")
    public void setTestData(Map<String, Integer> testData) {
        this.testData = testData;
    }

    public int check(String itemNo) {
        if (!testData.containsKey(itemNo)) {
            throw new IllegalArgumentException(itemNo + " does not exist in test map");
        }
        return testData.get(itemNo);
    }
}
