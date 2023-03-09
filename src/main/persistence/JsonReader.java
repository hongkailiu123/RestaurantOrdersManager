package persistence;

import model.Customer;
import model.Item;
import model.Restaurant;
import model.Table;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/*
 *    Title: JsonSerializationDemo
 *    Author: Paul Carter
 *    Date: 16 Oct 2021
 *    Availability: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */

// Represents a reader that reads restaurant from JSON data stored in file
public class JsonReader {
    private String sourceFile;

    // EFFECTS: creates a reader to read from the source file
    public JsonReader(String targetFile) {
        this.sourceFile = targetFile;
    }

    //EFFECTS: reads a Restaurant from file and returns it
    //         throws IOException if reading causes an error
    public Restaurant read() throws IOException {
        String jsonData = readFile(sourceFile);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseRestaurant(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String sourceFile) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(sourceFile), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }


    // EFFECTS: parses workroom from JSON object and returns it
    private Restaurant parseRestaurant(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Restaurant restaurant = new Restaurant(name);
        addTables(restaurant, jsonObject);
        parseWaitlist(restaurant, jsonObject);
        return restaurant;
    }

    // MODIFIES: restaurant
    // EFFECTS: parses tables from JSON object and adds them to restaurant
    private void addTables(Restaurant restaurant, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("tables");
        for (Object json : jsonArray) {
            JSONObject nextTable = (JSONObject) json;
            addTable(restaurant, nextTable);
        }
    }

    // MODIFIES: restaurant
    // EFFECTS: parses table from JSON object and adds it to restaurant
    private void addTable(Restaurant restaurant, JSONObject jsonObject) {
        int tableNum = jsonObject.getInt("tableNum");
        boolean status = jsonObject.getBoolean("status");
//        List<Item> orderList = new LinkedList<>();
//        double totalAmount = jsonObject.getDouble("totalAmount");
        Table table = new Table();
        table.setTableNum(tableNum);
        table.setStatus(status);
        addItems(table, jsonObject);
        restaurant.getTables().add(table);
    }

    // MODIFIES: table
    // EFFECTS: parses orderList from JSON and adds items to table
    private void addItems(Table table, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("orderList");
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            addItem(table, nextItem);
        }
    }

    // MODIFIES: table
    // EFFECTS: parses item from JSON and adds it to table
    private void addItem(Table table, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double price = jsonObject.getDouble("price");
        Item item = new Item(name, price);
        table.addItem(item);
    }

    // MODIFIES: restaurant
    // EFFECTS: parses waitlist from JSON and adds it to restaurant
    private void parseWaitlist(Restaurant restaurant, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("waitlist");
        for (Object json : jsonArray) {
            JSONObject nextCustomer = (JSONObject) json;
            addCustomer(restaurant, nextCustomer);
        }
    }

    // MODIFIES: restaurant
    // EFFECTS: parses customer from JSON and adds it to restaurant
    private void addCustomer(Restaurant restaurant, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int phoneNumber = jsonObject.getInt("phoneNumber");
        Customer customer = new Customer(name, phoneNumber);
        restaurant.addCustomerToWaitlist(customer);
    }


}
