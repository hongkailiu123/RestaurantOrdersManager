package ui;

import model.Customer;
import model.Item;
import model.Restaurant;
import model.Table;
import persistence.JsonReader;
import persistence.JsonWriter;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// Restaurant operations application - Feast
public class FeastApp {
    private Restaurant myRestaurant;
    private Scanner input;
    private int tableNum;
    private String bossName;
    private static final String jsonDestination = "./data/aStateOfRestaurant.json";
    private JsonReader reader;
    private JsonWriter writer;


    // EFFECTS: runs the feast app
    public FeastApp() {
        runFeast();
    }


    // MODIFIES: this
    // EFFECTS: processes user's inputs
    private void runFeast() {
        boolean stop = false;
        String nextAction;

        askTableNum();
        if (tableNum == -1) {
            loadAState();
        } else {
            askBossName();
            myRestaurant = new Restaurant(bossName);
            myRestaurant.setTables(tableNum);
        }

        while (!stop) {
            possibleNextStep();
            nextAction = input.next();
            if (nextAction.equals("s")) {
                reminderToSave();
                stop = true;
            } else {
                processNextStep(nextAction);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts the user entry the number of tables (the size of that ArrayList<Table>) or load a state
    //          of restaurant from file
    private void askTableNum() {
        System.out.println("Entry the number of tables that are ready for guests in your restaurant (Integer).");
        System.out.println("Or");
        System.out.println("Entry -1 if you want to load a state of restaurant from file");
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        tableNum = input.nextInt();
    }

    // MODIFIES: this
    // EFFECTS: prompts the user entry the boss name
    private void askBossName() {
        System.out.println("Entry the boss name (String).");
        bossName = input.next();
    }


    // EFFECTS: displays the menu of next steps to the user
    private void possibleNextStep() {
        System.out.print("\nPossible options");
        System.out.print("\nSelect from:");
        System.out.print("\n    TO -> Take the order");
        System.out.print("\n    RI -> Remove an item");
        System.out.print("\n    CO -> Check out");
        System.out.print("\n    CSO -> Check the status for one table");
        System.out.print("\n    CSA -> Check the statuses for all tables");
        System.out.print("\n    AC -> Add a customer to the waitlist");
        System.out.print("\n    RC -> Remove a customer from the waitlist");
        System.out.print("\n    SF -> Save current state of FeastApp entirely to a file");
        System.out.print("\n    LF -> Load a state of FeastApp from file");
        System.out.println("\n    S-> stop");
    }

    // EFFECTS: processes the user's selection
    private void processNextStep(String nextAction) {
        if (nextAction.equalsIgnoreCase("TO")) {
            takeOrder();
        } else if (nextAction.equalsIgnoreCase("RI")) {
            removeItem();
        } else if (nextAction.equalsIgnoreCase("CO")) {
            checkOut();
        } else if (nextAction.equalsIgnoreCase("CSO")) {
            checkStatusForOneTable();
        } else if (nextAction.equalsIgnoreCase("AC")) {
            addCustomerToWaitlist();
        } else if (nextAction.equalsIgnoreCase("RC")) {
            removeCustomerFromWaitlist();
        } else if (nextAction.equalsIgnoreCase("CSA")) {
            checkStatusForAllTable();
        } else if (nextAction.equalsIgnoreCase("SF")) {
            saveEntireState();
        } else if (nextAction.equalsIgnoreCase("LF")) {
            loadAState();
        } else {
            System.out.println("\n Not valid option! Select one again");
        }

    }

    // MODIFIES: this
    // EFFECTS: takes order of food for a specific table
    private void takeOrder() {
        Table selectedTable = select();
        Item newItem;

        boolean keepGoing = true;
        System.out.println("You have chosen Table: " + selectedTable.getTableNumber());

        while (keepGoing) {
            System.out.println("What item do the customers want to order (Item Name)?");
            System.out.println("Entry T if no more items ");
            String itemName = input.next();
            if ((itemName.equalsIgnoreCase("T"))) {
                keepGoing = false;
            } else {
                selectedTable.setStatus(false);
                System.out.println("What is the price (double)？");
                double itemPrice = input.nextDouble();
                newItem = new Item(itemName, itemPrice);
                selectedTable.addItem(newItem);
                System.out.println("This Item has been added!");
            }
        }
        System.out.println("Completed the operation successfully!");
    }

    // MODIFIES: this
    // EFFECTS: removes item from a specific table's order list
    private void removeItem() {
        Table selectedTable = select();
        Item removeItem;
        boolean keepGoing = true;

        System.out.println("You have chosen Table: " + selectedTable.getTableNumber());

        while (keepGoing) {
            System.out.println("What item do the customer want to remove (Item Name)?");
            System.out.println("Entry T if no more items");
            String itemName = input.next();
            if ((itemName.equalsIgnoreCase("T"))) {
                keepGoing = false;
            } else {
                removeItem = lookForItem(itemName, selectedTable.getOrderList());
                if (!(removeItem == null)) {
                    selectedTable.removeItem(removeItem);
                    System.out.println("This item has been removed!");
                } else {
                    System.out.println("Did not find this item!");
                }
            }
        }
        System.out.println("Completed the operation successfully!");

    }

    // MODIFIES: this
    // EFFECTS: helps guests of a specific table to check out
    private void checkOut() {
        Table selectedTable = select();

        System.out.println(selectedTable.getBill());
        System.out.println("\nDo you want to split the bill (yes or no)?");
        String split = input.next();
        if (split.equalsIgnoreCase("yes")) {
            System.out.println("\nEntry the number of customers (Integer)");
            int num = input.nextInt();
            System.out.println("\n" + selectedTable.separateBill(num));
        }
        System.out.println("\nHave the customers paid the bill (yes or no)?");
        String paid = input.next();
        if (paid.equalsIgnoreCase("yes")) {
            selectedTable.cleanUpTable();
            System.out.println("The table has been cleaned up");
        }
    }

    // EFFECTS: displays the status of the chosen table
    private void checkStatusForOneTable() {
        Table selectedTable = select();

        if (selectedTable.getStatus()) {
            System.out.println("Table " + selectedTable.getTableNumber() + " is available.");
        } else {
            System.out.println("Table " + selectedTable.getTableNumber() + " is unavailable.");
        }

    }

    // EFFECTS: displays all available tables. If no available table, print "No available table"
    private void checkStatusForAllTable() {
        String status = "Available tables: ";
        List<Table> tables = myRestaurant.getTables();

        for (Table table : tables) {
            if (table.getStatus()) {
                status = status.concat("\nTable " + table.getTableNumber() + " is available.");
            }
        }
        if (status.contentEquals("Available tables: ")) {
            System.out.println("No available table");
        } else {
            System.out.println(status);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds customers to the waitlist
    private void addCustomerToWaitlist() {
        boolean keepGoing = true;
        Customer newCustomer;

        while (keepGoing) {
            System.out.println("Entry the customer's name (Customer name)");
            System.out.println("Entry T if no more customers");
            String customerName = input.next();
            if ((customerName.equalsIgnoreCase("T"))) {
                keepGoing = false;
            } else {
                System.out.println("\nEntry the customer's phone number (String representation)");
                String phoneNumber = input.next();
                newCustomer = new Customer(customerName, phoneNumber);
                myRestaurant.addCustomerToWaitlist(newCustomer);
                System.out.println("\nThis customer has been added!");

            }

        }

        System.out.println("Completed the operation successfully!");

    }

    // MODIFIES: this
    // EFFECTS: removes customers from the waitlist and print a message for removed customers;
    //          print "There is no customer in the waitlist" if the waitlist is empty
    private void removeCustomerFromWaitlist() {
        boolean keepGoing = true;
        List<Customer> waitlist = myRestaurant.getWaitlist();

        while (keepGoing) {
            System.out.println("Do you want to get the first customer off the waitlist (yes or no)?");
            System.out.println("Entry T if no more customers");
            String first = input.next();
            if ((first.equalsIgnoreCase("T"))) {
                keepGoing = false;
            } else if (first.equalsIgnoreCase("Yes")) {
                if (waitlist.size() > 0) {
                    Customer nextCustomer = waitlist.get(0);
                    waitlist.remove(0);
                    System.out.println("\nThis customer has been removed!");
                    System.out.println(nextCustomer.messageToCustomer());
                } else {
                    System.out.println("There is no customer in the waitlist!");
                }
            }
            System.out.println("Completed the operation successfully!");

        }
    }

    // EFFECTS: promotes the user entry a table number, and returns the table the user selected.
    private Table select() {
        int selectedNum = -1;
        List<Table> tables = myRestaurant.getTables();

        while (!(0 < selectedNum && selectedNum <= tableNum)) {
            System.out.println("Entry the table Number (Integer in [1, table number])");
            selectedNum = input.nextInt();
        }
        int searchNum = selectedNum - 1;
        return tables.get(searchNum);
    }

    // EFFECTS: looks for the item in the order list according to the item name.
    //          returns that item if found, otherwise null;
    private Item lookForItem(String itemName, List<Item> orderList) {
        for (Item targetItem : orderList) {
            String name = targetItem.getName();
            if (name.contentEquals(itemName)) {
                return targetItem;
            }
        }
        return null;
    }


    // EFFECTS: saves restaurant to file
    private void saveEntireState() {
        try {
            writer = new JsonWriter(jsonDestination);
            writer.open();
            writer.write(myRestaurant);
            writer.close();
            System.out.println("Successfully write to file " + jsonDestination);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file " + jsonDestination);
        }

    }

    // MODIFIES: this
    // EFFECTS: loads restaurant from file
    private void loadAState() {
        try {
            reader = new JsonReader(jsonDestination);
            myRestaurant = reader.read();
            this.tableNum = myRestaurant.getTables().size();
            System.out.println("Successfully load from file " + jsonDestination);
        } catch (IOException e) {
            System.out.println("Unable to load from file " + jsonDestination);
        }

    }

    // EFFECTS: reminds the user to save restaurant to file before the user stops
    //          processes the saving option if the user want to do it
    private void reminderToSave() {
        System.out.println("Do you want to save the current state of restaurant to file? (yes or no)");
        String saveWord = input.next();
        if (saveWord.equalsIgnoreCase("yes")) {
            saveEntireState();
        }
    }

}
