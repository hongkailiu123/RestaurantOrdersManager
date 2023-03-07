package model;

import java.util.ArrayList;

// Represents a table (in a restaurant) having a number, status, orderList, and total amount.
public class Table {
    private static int nextTableNum = 1;

    private int tableNum;                // Table number
    private boolean status;             // Status (available or not)
    private ArrayList<Item> orderList;  // the order list that records the items ordered
    private double totalAmount;         // the current value of the total amount


    /*
     * EFFECTS: constructs a new table with a unique table number, available status (= true),
     *          empty order list, and 0 total amount.
     */
    public Table() {
        tableNum = nextTableNum++;
        status = true;
        orderList = new ArrayList<>();
        totalAmount = 0;
    }

    /*
     * MODIFIES: this
     * EFFECTS: an item is added to this table's order list
     *          its price is added to this table's total amount
     */
    public void addItem(Item item) {
        orderList.add(item);
        this.totalAmount += item.getPrice();
    }


    /*
     * REQUIRES: the order list must not be empty
     * MODIFIES: this
     * EFFECTS: an item is removed from this table's order list
     *          its price is subtracted from this table's total amount
     */
    public void removeItem(Item item) {
        orderList.remove(item);
        this.totalAmount -= item.getPrice();
    }

    /*
     * EFFECTS: return this table's status
     */
    public boolean getStatus() {
        return status;
    }

    /*
     * EFFECTS: return this table's table number
     */
    public int getTableNumber() {
        return tableNum;
    }

    /*
     * EFFECTS: return this table's order list
     */
    public ArrayList<Item> getOrderList() {
        return orderList;
    }

    /*
     * EFFECTS: return this table's total amount
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /*
     * MODIFIES: this
     * EFFECTS: set this table's status
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /*
     * EFFECTS: return the string representation of the bill that lists
     *          all the items ordered with item name, item price, and
     *          the total amount of the bill
     */
    public String getBill() {
        String result = "";
        for (Item item: orderList) {
            result = result.concat("\n Item Name: " + item.getName() + " ｜ Item Price :" + item.getPrice());
        }

        return result.concat("\n The total amount of the bill is " + this.totalAmount);
    }


    /*
     * REQUIRES: num >= 1
     * EFFECTS: return the string representation of amount of a separate bill
     */
    public String separateBill(int num) {
        double result;
        result = this.totalAmount / num;
        return "The amount of a separate bill :" + result;
    }

    /*
     * MODIFIES: this
     * EFFECTS: replace this table's order list with an empty order list
     *          replace this table's total amount with 0.0
     */
    public void cleanUpTable() {
        this.orderList.clear();
        this.totalAmount = 0.0;
        this.status = true;
    }


}
