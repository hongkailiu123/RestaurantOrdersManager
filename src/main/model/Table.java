package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;
import java.util.List;


// Represents a table (in a restaurant) having a number, status, orderList, and total amount.
public class Table implements Writable {
    private static int nextTableNum = 1; // Initial value of NextTableNum when no table exists
    private int tableNum;                // Table number
    private boolean status;              // Status (available or not)
    private List<Item> orderList;        // The order list that records the items ordered
    private double totalAmount;          // The current value of the total amount


    /*
     * EFFECTS: constructs a new table with a unique table number, available status (= true),
     *          empty order list, and 0 total amount.
     */
    public Table() {
        tableNum = nextTableNum++;
        status = true;
        orderList = new LinkedList<>();
        totalAmount = 0;
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds item to this table's order list
     *          , and the item's price is added to this table's total amount
     */
    public void addItem(Item item) {
        orderList.add(item);
        this.totalAmount += item.getPrice();
    }


    /*
     * REQUIRES: the order list must not be empty
     * MODIFIES: this
     * EFFECTS: removes item from this table's order list
     *          , and its price is subtracted from this table's total amount
     */
    public void removeItem(Item item) {
        orderList.remove(item);
        this.totalAmount -= item.getPrice();
    }

    /*
     * EFFECTS: returns this table's status
     */
    public boolean getStatus() {
        return status;
    }

    /*
     * EFFECTS: returns this table's table number
     */
    public int getTableNumber() {
        return tableNum;
    }

    /*
     * EFFECTS: returns this table's order list
     */
    public List<Item> getOrderList() {
        return orderList;
    }

    /*
     * EFFECTS: returns this table's total amount
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets this table's status
     */
    public void setStatus(boolean status) {
        this.status = status;
    }


    /*
     * MODIFIES: this
     * EFFECTS: sets this table's table number
     */
    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }


    /*
     * EFFECTS: returns the string representation of the bill that lists
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
     * EFFECTS: replaces this table's order list with an empty order list
     *          , replaces this table's total amount with 0.0
     *          , and sets this table's status as available (true)
     */
    public void cleanUpTable() {
        this.orderList.clear();
        this.totalAmount = 0.0;
        this.status = true;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("tableNum", tableNum);
        json.put("status",status);
        json.put("orderList",itemsToJson());
        json.put("totalAmount",totalAmount);
        return json;
    }

    // EFFECTS: returns orderList in this table as a JSON array
    private JSONArray itemsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Item i : orderList) {
            jsonArray.put(i.toJson());
        }

        return jsonArray;
    }
}
