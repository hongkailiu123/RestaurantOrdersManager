package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a customer (waiting for a table) having a String name and a String phone number
public class Customer implements Writable {
    private String name;
    private String phoneNumber;

    /*
     * EFFECTS: create a customer with given name and phone number
     */
    public Customer(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    /*
     * EFFECTS: return the name of this customer
     */
    public String getName() {
        return name;
    }

    /*
     * EFFECTS: return the phone number string representation of this customer
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }


    /*
     * EFFECTS: returns a string of a message for the next customer
     */
    public String messageToCustomer() {
        return "[Dear " + getName() + ", the table for you is ready! Please show up on the waiting area]"
                + " to phone number: " + this.getPhoneNumber();

    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("phoneNumber", phoneNumber);
        return json;
    }
}
