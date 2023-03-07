package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// Represents a restaurant with a Boss name, a fixed number of tables, and a waitlist of customers
public class Restaurant {
    private String name;
    private List<Table> tables;
    private List<Customer> waitlist = new LinkedList();

    /*
     * REQUIRES: tableNum must be greater than 0
     * EFFECTS: create a restaurant with given boss name, a given number of tables
     *          , and an empty waitlist
     */
    public Restaurant(String bossName, int tableNum) {
        this.name = bossName;

        this.tables = new ArrayList<>();
        for (int i = 0; i < tableNum; i++) {
            Table newTable = new Table();
            tables.add(newTable);
        }
    }

    public String getBossName() {
        return name;
    }

    public List<Table> getTables() {
        return tables;
    }

    public List<Customer> getWaitlist() {
        return waitlist;
    }


}
