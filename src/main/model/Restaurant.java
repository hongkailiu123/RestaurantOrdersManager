package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// Represents a restaurant with a String Boss name, a fixed number of tables, and a waitlist of customers
public class Restaurant implements Writable {
    private String name;
    private List<Table> tables = new ArrayList<>();
    private List<Customer> waitlist = new LinkedList();

    /*
     * EFFECTS: creates a restaurant with a given boss name, an empty list of tables
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
    // EFFECTS: adds c to the waitlist
    //          logs this event to the Singleton EventLog
    public void addCustomerToWaitlist(Customer c) {
        waitlist.add(c);
        EventLog.getInstance().logEvent(new Event(c.getName() + "(" + c.getPhoneNumber() + ")"
                + " has been added to the waitlist."));
    }


    // MODIFIES: this
    // EFFECTS: loads (adds) c to the waitlist
    //          calls loadCustomerToWaitlist when loading a state from file, avoiding to log inappropriate events
    public void loadCustomerToWaitlist(Customer c) {
        waitlist.add(c);
    }

    // MODIFIES: this
    // EFFECTS: remove the first Customer in the waitlist
    //          logs this event to the Singleton EventLog
    public void removeFirstCustomerFromWaitlist() {
        Customer customerToRemove = waitlist.get(0);
        waitlist.remove(0);
        EventLog.getInstance().logEvent(new Event(customerToRemove.getName()
                + "(" + customerToRemove.getPhoneNumber() + ")" + " has been removed from the waitlist."));
    }

    // EFFECTS: returns this as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject jsonR = new JSONObject();
        jsonR.put("name", name);
        jsonR.put("tables", tablesToJson());
        jsonR.put("waitlist", waitlistToJson());
        return jsonR;
    }

    // EFFECTS: returns tables in this restaurant as a JSON array
    private JSONArray tablesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Table t : tables) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns waitlist in this restaurant as a JSON array
    private JSONArray waitlistToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Customer c : waitlist) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }

}
