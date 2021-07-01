package demos.spring.notes.v5;

import demos.spring.notes.common.StockCheckEngine;

public class StockCheckEngineMock implements StockCheckEngine {
    public int check(String itemNo) {
        return 200;
    }
}
