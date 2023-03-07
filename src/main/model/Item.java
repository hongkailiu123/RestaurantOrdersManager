package model;

// Represents an item (in a restaurant) having a String name and a Double price
public class Item {
    private String name;
    private double price;


    /*
     * REQUIRES: price must be non-negative
     * EFFECTS: create an item with given item name and price
     */
    public Item(String itemName, double price) {
        this.name = itemName;
        this.price = price;
    }

    /*
     * EFFECTS: return the name of this item
     */
    public String getName() {
        return name;
    }

    /*
     * EFFECTS: return the price of this item
     */
    public double getPrice() {
        return price;
    }

    /*
     * MODIFIES: this
     * EFFECTS: set name as this item's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
     * REQUIRES: price >= 0
     * MODIFIES: this
     * EFFECTS: set price as this item's name
     */
    public void setPrice(double price) {
        this.price = price;
    }



}

