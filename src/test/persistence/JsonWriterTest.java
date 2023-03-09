package persistence;

import model.Customer;
import model.Item;
import model.Restaurant;
import model.Table;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/*
 *    Title: JsonSerializationDemo
 *    Author: Paul Carter
 *    Date: 16 Oct 2021
 *    Availability: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */

public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterNonexistentFile() {
        try {
            Restaurant myRestaurant = new Restaurant("Kai");
            JsonWriter writer = new JsonWriter("./data/\0myNonexistentFile:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterNewRestaurant() {
        try {
            Restaurant myRestaurant = new Restaurant("Kai");
            JsonWriter writer = new JsonWriter("./data/testWriterNewRestaurant.json");
            writer.open();
            writer.write(myRestaurant);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNewRestaurant.json");
            Restaurant restaurantWriter = reader.read();
            assertEquals(myRestaurant.getBossName(), restaurantWriter.getBossName());
            assertEquals(0, restaurantWriter.getTables().size());
            assertEquals(0, restaurantWriter.getWaitlist().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterRegularRestaurant() {
        try {
            Restaurant myRestaurant = new Restaurant("Kai");
            myRestaurant.setTables(5);
            myRestaurant.getTables().get(1).addItem(new Item("Noodle",10.0));
            myRestaurant.addCustomerToWaitlist(new Customer("Kai","111"));
            myRestaurant.addCustomerToWaitlist(new Customer("Joy","222222"));

            JsonWriter writer = new JsonWriter("./data/testWriterRegularRestaurant.json");
            writer.open();
            writer.write(myRestaurant);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterRegularRestaurant.json");
            Restaurant restaurantWriter = reader.read();
            assertEquals(myRestaurant.getBossName(), restaurantWriter.getBossName());

            List<Table> tablesWriter = restaurantWriter.getTables();
            assertEquals(myRestaurant.getTables().size(), tablesWriter.size());
            checkTables(tablesWriter, myRestaurant.getTables());

            List<Customer> waitlistWriter = restaurantWriter.getWaitlist();
            assertEquals(waitlistWriter.size(),myRestaurant.getWaitlist().size());
            checkWaitlist(waitlistWriter, myRestaurant.getWaitlist());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
