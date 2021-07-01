package demos.spring.notes.v9;

import demos.spring.notes.common.StockCheckEngine;
import org.springframework.stereotype.Component;

@Component("stock")
public class StockCheckEngineMock implements StockCheckEngine {
    public int check(String itemNo) {
        return 200;
    }
}
