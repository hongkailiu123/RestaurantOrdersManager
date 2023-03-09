package persistence;

import model.Customer;
import model.Item;
import model.Table;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 *    Title: JsonSerializationDemo
 *    Author: Paul Carter
 *    Date: 16 Oct 2021
 *    Availability: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */

public class JsonTest {
    protected void checkTables(List<Table> tables1, List<Table> tables2) {
        assertEquals(tables1.size(), tables2.size());
        int tablesSize = tables1.size();
        for (int i = 0; i < tablesSize; i++) {
            checkTable(tables1.get(i), tables2.get(i));
        }
    }


    private void checkTable(Table t1, Table t2) {
        assertEquals(t1.getTableNumber(), t2.getTableNumber());
        assertEquals(t1.getStatus(), t2.getStatus());
        assertEquals(t1.getTotalAmount(), t2.getTotalAmount());

        int oderlistSize = t1.getOrderList().size();
        assertEquals(oderlistSize, t2.getOrderList().size());

        for (int i = 0; i < oderlistSize; i++) {
            checkItem(t1.getOrderList().get(i), t2.getOrderList().get(i));
        }

    }


    private void checkItem(Item i1, Item i2) {
        assertEquals(i1.getName(), i2.getName());
        assertEquals(i1.getPrice(), i2.getPrice());
    }


    public void checkWaitlist(List<Customer> wl1, List<Customer> wl2) {
        assertEquals(wl1.size(), wl2.size());
        int waitlistSize = wl1.size();

        for (int i = 0; i < waitlistSize; i++) {
            checkCustomer(wl1.get(i), wl2.get(i));
        }
    }


    private void checkCustomer(Customer c1, Customer c2) {
        assertEquals(c1.getName(), c2.getName());
        assertEquals(c1.getPhoneNumber(), c2.getPhoneNumber());
    }
}
