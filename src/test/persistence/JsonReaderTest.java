package persistence;

import model.Customer;
import model.Item;
import model.Restaurant;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/*
 *    Title: JsonSerializationDemo
 *    Author: Paul Carter
 *    Date: 16 Oct 2021
 *    Availability: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonexistentFile() {
        JsonReader reader = new JsonReader("./data/notExistentFile.json");
        try {
            Restaurant myRestaurant = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }


    @Test
    void testReaderNewRestaurant() {
        JsonReader reader = new JsonReader("./data/testReaderNewRestaurant.json");
        try {
            Restaurant newRestaurant = new Restaurant("Kai");
            Restaurant myRestaurant = reader.read();
            assertEquals(myRestaurant.getBossName(), "Kai");
            assertEquals(myRestaurant.getTables().size(), 0);
            assertEquals(myRestaurant.getWaitlist().size(), 0);
        } catch (IOException e) {
            fail("IOException was not expected");
        }
    }

    @Test
    void testReaderRegularRestaurant() {
        Restaurant regularRestaurant = new Restaurant("Kai");
        regularRestaurant.setTables(5);
        regularRestaurant.getTables().get(1).addItem(new Item("Noodle", 10.0));
        regularRestaurant.addCustomerToWaitlist(new Customer("Kai", "111"));
        regularRestaurant.addCustomerToWaitlist(new Customer("Joy", "222222"));

        JsonReader reader = new JsonReader("./data/testReaderRegularRestaurant.json");
        try {
            Restaurant myRestaurant = reader.read();
            assertEquals(myRestaurant.getTables().size(), 5);
            assertEquals(myRestaurant.getWaitlist().size(), 2);
            checkTables(myRestaurant.getTables(), regularRestaurant.getTables());
            checkWaitlist(myRestaurant.getWaitlist(), regularRestaurant.getWaitlist());
        } catch (IOException e) {
            fail("IOException was not expected");
        }
    }


}
