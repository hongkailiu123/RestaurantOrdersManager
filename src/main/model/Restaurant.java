package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// Represents a restaurant with a Boss name, a fixed number of tables, and a waitlist of customers
public class Restaurant implements Writable {
    private String name;
    private List<Table> tables = new ArrayList<>();
    private List<Customer> waitlist = new LinkedList();

    /*
     * EFFECTS: creates a restaurant with given boss name, an empty list of tables
     *          , and an empty waitlist
     */
    public Restaurant(String bossName) {
        this.name = bossName;
    }

    /*
     * REQUIRES: tableNum must be greater than 0
     * EFFECTS: adds a given number of tables to tables
     */
    public void setTables(int tableNum) {
        for (int i = 0; i < tableNum; i++) {
            Table newTable = new Table();
            tables.add(newTable);
        }
    }

    // EFFECTS: returns the name
    public String getBossName() {
        return name;
    }

    // EFFECTS: returns the list of tables
    public List<Table> getTables() {
        return tables;
    }

    // EFFECTS: returns the waitlist of customers
    public List<Customer> getWaitlist() {
        return waitlist;
    }

    // MODIFIES: this
    // EFFECTS: add c to the waitlist
    public void addCustomerToWaitlist(Customer c) {
        waitlist.add(c);
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonR = new JSONObject();
        jsonR.put("name", name);
        jsonR.put("tables", tablesToJson());
        jsonR.put("waitlist", waitlistToJson());
        return jsonR;
    }

    // EFFECTS: returns tables in this restaurant as a JSON array
    public JSONArray tablesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Table t : tables) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns  waitlist in this restaurant as a JSON array
    public JSONArray waitlistToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Customer c : waitlist) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }


}
