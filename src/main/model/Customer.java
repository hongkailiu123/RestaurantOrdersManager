package model;

public class Customer {
    private String name;
    private int phoneNumber;

    /*
     * REQUIRES: phoneNumber must be a positive and 10-digit number
     * EFFECTS: create a customer with given name and phone number
     */
    public Customer(String name, int phoneNumber) {
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
     * EFFECTS: return the phone number of this customer
     */
    public int getPhoneNumber() {
        return phoneNumber;
    }


    /*
     * EFFECTS: returns a string of a message for the next customer
     */
    public String messageToCustomer() {
        return "[Dear " + getName() + ", the table for you is ready! Please show up on the waiting area]"
                + " to phone number: " + this.getPhoneNumber();

    }

}
