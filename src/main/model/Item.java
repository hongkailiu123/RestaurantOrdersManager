package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an item (in a restaurant) having a String name and a Double price
public class Item implements Writable {
    private String name;
    private double price;


    /*
     * REQUIRES: price must be non-negative
     * EFFECTS: creates an item with given item name and price
     */
    public Item(String itemName, double price) {
        this.name = itemName;
        this.price = price;
    }

    /*
     * EFFECTS: returns the name of this item
     */
    public String getName() {
        return name;
    }

    /*
     * EFFECTS: returns the price of this item
     */
    public double getPrice() {
        return price;
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets name as this item's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
     * REQUIRES: price >= 0
     * MODIFIES: this
     * EFFECTS: sets price as this item's name
     */
    public void setPrice(double price) {
        this.price = price;
    }


    // EFFECTS: returns this as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("price", price);
        return json;
    }
}

