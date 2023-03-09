package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RestaurantTest {
    private final String bossNameTest = "Kai";
    private final int tableNumTest = 2;
    private Restaurant restaurantTest;

    @BeforeEach
    void runBefore() {
        restaurantTest = new Restaurant(bossNameTest);
    }

    @Test
    void testConstructor() {
        assertEquals(restaurantTest.getBossName(),bossNameTest);
        assertEquals(restaurantTest.getWaitlist().size(),0);
        assertEquals(restaurantTest.getTables().size(),0);
    }

    @Test
    void testSetTables() {
        restaurantTest.setTables(tableNumTest);
        assertEquals(restaurantTest.getTables().size(),tableNumTest);
    }

    @Test
    void testAddCustomerToWaitlist() {
        assertEquals(restaurantTest.getWaitlist().size(),0);
        restaurantTest.addCustomerToWaitlist(new Customer("KAI","111"));
        assertEquals(restaurantTest.getWaitlist().size(),1);
        restaurantTest.addCustomerToWaitlist(new Customer("Joy","222"));
        assertEquals(restaurantTest.getWaitlist().size(),2);
    }


    @Test
    void testToJsonEmptyWaitlistEmptyTables() {
        JSONObject jsonTest = restaurantTest.toJson();
        assertEquals(jsonTest.getString("name"), restaurantTest.getBossName());
        assertTrue(jsonTest.getJSONArray("tables").isEmpty());
        assertTrue(jsonTest.getJSONArray("waitlist").isEmpty());
    }

    @Test
    void testToJsonEmptyWaitlistTwoTables() {
        restaurantTest.setTables(tableNumTest);
        JSONObject jsonTest = restaurantTest.toJson();
        assertEquals(jsonTest.getString("name"), restaurantTest.getBossName());

        JSONObject jsonTable0 = (JSONObject) jsonTest.getJSONArray("tables").get(0);
        Table table0 = restaurantTest.getTables().get(0);
        assertEquals(jsonTable0.getDouble("totalAmount"),table0.getTotalAmount());
        assertEquals(jsonTable0.getInt("tableNum"),table0.getTableNumber());
        assertTrue(jsonTable0.getJSONArray("orderList").isEmpty());
        assertEquals(jsonTable0.getBoolean("status"),table0.getStatus());

        JSONObject jsonTable1 = (JSONObject) jsonTest.getJSONArray("tables").get(1);
        Table table1 = restaurantTest.getTables().get(1);
        assertEquals(jsonTable1.getDouble("totalAmount"),table1.getTotalAmount());
        assertEquals(jsonTable1.getInt("tableNum"),table1.getTableNumber());
        assertTrue(jsonTable1.getJSONArray("orderList").isEmpty());
        assertEquals(jsonTable1.getBoolean("status"),table1.getStatus());

        assertTrue(jsonTest.getJSONArray("waitlist").isEmpty());
    }

    @Test
    void testToJsonTwoSizeWaitlistEmptyTables() {
        restaurantTest.addCustomerToWaitlist(new Customer("KAI","111"));
        restaurantTest.addCustomerToWaitlist(new Customer("Joy","222"));
        JSONObject jsonTest = restaurantTest.toJson();

        assertEquals(jsonTest.getString("name"), restaurantTest.getBossName());
        assertTrue(jsonTest.getJSONArray("tables").isEmpty());

        List<Customer> waitlist = restaurantTest.getWaitlist();
        JSONArray jsonWaitlist = jsonTest.getJSONArray("waitlist");

        JSONObject jsonCustomer0 =  (JSONObject) jsonWaitlist.get(0);
        Customer customer0 = waitlist.get(0);
        assertEquals(jsonCustomer0.getString("name"), customer0.getName());
        assertEquals(jsonCustomer0.getString("phoneNumber"), customer0.getPhoneNumber());

        JSONObject jsonCustomer1 =  (JSONObject) jsonWaitlist.get(1);
        Customer customer1 = waitlist.get(1);
        assertEquals(jsonCustomer1.getString("name"), customer1.getName());
        assertEquals(jsonCustomer1.getString("phoneNumber"), customer1.getPhoneNumber());
    }

}
