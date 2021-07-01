package demos.spring.cdi.v1;

import demos.spring.notes.common.StockCheckEngine;

import javax.inject.Named;

@Named("stock")
public class StockCheckEngineMock implements StockCheckEngine {
    public int check(String itemNo) {
        return 200;
    }
}
