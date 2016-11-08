package lags;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LagsServiceTest {
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    private static final String TESTDATA_FILENAME = LagsServiceTest.class.getResource("TestData.csv").getPath();

    @Test
    public void loadOrdersFromFileWorks() {
        LagsService service = createStubOrders();
        assertTrue(service.listOrder.size() == 4);
        List<Order> expectedOrders = new ArrayList<>();
        expectedOrders.add(new Order("DONALD", 2015001, 6, 10000.00));
        expectedOrders.add(new Order("DAISY", 2015003, 2, 4000.00));
        expectedOrders.add(new Order("PICSOU", 2015007, 7, 8000.00));
        expectedOrders.add(new Order("MICKEY", 2015008, 7, 9000.00));
        assertEquals(expectedOrders, service.listOrder);
    }

    @Test
    public void listDisplayOrdersCorrectly() {
        LagsService service = createStubOrders();
        service.showOrderList();
        assertEquals("ORDERS LIST\n" +
            "ID            DEBUT DUREE       PRIX\n" +
            "--------    ------- ----- ----------\n" +
            "DONALD      2015001     6 10000,000000\n" +
            "DAISY       2015003     2 4000,000000\n" +
            "PICSOU      2015007     7 8000,000000\n" +
            "MICKEY      2015008     7 9000,000000\n" +
            "--------    ------- ----- ----------\n", systemOutRule.getLog());
    }

    @Test
    public void emptyOrdersWillReturnZeroGrossSales() {
        LagsService service = new LagsService();
        assertEquals(service.calculateGrossSales(Collections.emptyList(), false), 0.00, 0.0);
    }

    private LagsService createStubOrders() {
        LagsService service = new LagsService();
        service.loadOrdersFromFile(TESTDATA_FILENAME);
        return service;
    }

}
