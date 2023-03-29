package ui;

import model.Customer;
import model.Item;
import model.Restaurant;
import model.Table;
import persistence.JsonReader;
import persistence.JsonWriter;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class GraphicalUIFeastApp extends JFrame {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    private Restaurant myRestaurant;
    private int tableNum;
    private static final String jsonDestination = "./data/aStateOfRestaurant.json";
    private JsonReader reader;
    private JsonWriter writer;
    private JPanel bgPanel;
    private JPanel infoPanel;
    private JScrollPane scrollItemsTablePane;
    private JScrollPane scrollWaitlistTablePane;
    private JMenuBar menuBar;

    private Table currentTable;

    private JTextField bossNameField;
    private JTextField tableNumField;

    // EFFECTS: runs GUI version feast app
    public GraphicalUIFeastApp() {
        setSize(550, 300);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListenerToThis();
        addMenuBar();
        setup();
        setResizable(false);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds WindowListener with a specific implementation of windowClosing to this
    private void addWindowListenerToThis() {
        addWindowListener(new WindowAdapter() {

            // EFFECTS: display a JOptionPane that remind the user to save the current restaurant state if
            //          the user want
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(null, " Do you want save the current"
                                + "FeastApp state? ", "Save or not", JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (result == JOptionPane.YES_OPTION) {
                    saveEntireState();
                    dispose();
                } else if (result == JOptionPane.NO_OPTION) {
                    dispose();
                } else {
                    // user choose "Cancel", do nothing
                }
            }
        });
    }


    // MODIFIES: this
    // EFFECTS: prompts the user entry the number of tables (the size of that ArrayList<Table>) or load a state
    //          sets up myRestaurant according to user's choice
    //          initials and set up all elements of the JFrame window
    private void setup() {
        bgPanel = new JPanel();
        bgPanel.setBackground(Color.white);
        bgPanel.setPreferredSize(new Dimension(550, 300));
        add(bgPanel);

        bgPanel.setLayout(null);

        addFeastAppImage();
        askAndGetBossName();
        askAndGetTableNum();
        addSetUpButtons();
    }

    // MODIFIES: this
    // EFFECTS: adds the revised FeastAppImage that is read from file
    private void addFeastAppImage() {
        try {
            BufferedImage originalFeastAppImage = ImageIO.read(new File("./data/feastAppImage.png"));
            BufferedImage resizedFeastAppImage = resize(originalFeastAppImage, 100, 40);
            ImageIcon feastAppImage = new ImageIcon(resizedFeastAppImage);
            JLabel imageLabel = new JLabel(feastAppImage);
            imageLabel.setBounds(230, 10, 100, 40);
            bgPanel.add(imageLabel);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to find the FeastApp image " + jsonDestination,
                    "Lost Image", JOptionPane.ERROR_MESSAGE);
        }
    }

    // EFFECTS: a helper to help resize an BufferedImage, and returns the resized image.
    public static BufferedImage resize(BufferedImage image, int newWidth, int newHeight) {
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, image.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, newWidth, newHeight, null);
        g.dispose();
        return resizedImage;
    }

    // MODIFIES: this
    // EFFECTS: Creates and adds JButton that enables the user to load a previous state from file
    //          and another JButton that enables the user to confirm their inputs for BossName
    //          and Table number
    private void addSetUpButtons() {
        JLabel requestChoice = new JLabel("Press Load if you want to load "
                + "the previous state of restaurant from file.");
        requestChoice.setBounds(20, 150, 550, 30);
        bgPanel.add(requestChoice);

        JButton loadButton = new JButton("Load");
        loadButton.setBounds(160, 190, 100, 40);
        addListenerToLoadButton(loadButton);

        bgPanel.add(loadButton);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setBounds(280, 190, 100, 40);
        addListenerToConfirmButton(confirmButton);
        bgPanel.add(confirmButton);
    }

    // MODIFIES: loadButton
    // EFFECTS: adds ActionListener with specific implementation of actionPerformed to loadButton
    private void addListenerToLoadButton(JButton loadButton) {
        loadButton.addActionListener(new ActionListener() {

            // EFFECTS: loads the previous restaurant state from file, removes BgPanel,
            //          assigns myRestaurant.getTables().get(0) to currentTable as the default table,
            //          initials the main window, and refreshes InfoPanel
            //          called by the framework when LoadButton is clicked
            @Override
            public void actionPerformed(ActionEvent e) {
                loadAState();
                currentTable = myRestaurant.getTables().get(0);
                removeBgPanel();
                runRestaurant();
                updateInfoPanel();
            }

            // MODIFIES: this
            // EFFECTS: loads restaurant from file
            private void loadAState() {
                try {
                    reader = new JsonReader(jsonDestination);
                    myRestaurant = reader.read();
                    tableNum = myRestaurant.getTables().size();
                } catch (IOException e) {
                    // System.out.println("Unable to load from file " + jsonDestination);
                    JOptionPane.showMessageDialog(null,
                            "Unable to load from file " + jsonDestination,
                            "Fail to load", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }


    // MODIFIES: confirmButton
    // EFFECTS: adds ActionListener with specific implementation of actionPerformed to confirmButton
    private void addListenerToConfirmButton(JButton confirmButton) {
        confirmButton.addActionListener(new ActionListener() {

            // EFFECTS: sets the parsed int to tableNum, creates a new restaurant according to user's inputs,
            //          removes BgPanel, assigns myRestaurant.getTables().get(0) to currentTable as the default table,
            //          initials the main window, and refreshes InfoPanel
            //          called by the framework when LoadButton is clicked
            @Override
            public void actionPerformed(ActionEvent e) {
                tableNum = Integer.parseInt(tableNumField.getText());
                myRestaurant = new Restaurant(bossNameField.getText());
                myRestaurant.setTables(tableNum);
                currentTable = myRestaurant.getTables().get(0);
                removeBgPanel();
                runRestaurant();
                updateInfoPanel();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates JLabel request to prompt the user entry the table number
    //          and JTextField to receive user's input
    //          assigns JTextField to tableNumField
    private void askAndGetTableNum() {
        JLabel requestTableNum = new JLabel("Entry the number of tables that "
                + "are ready for guests in your restaurant (Integer): ");
        requestTableNum.setBounds(20, 90, 550, 30);
        bgPanel.add(requestTableNum);

        tableNumField = new JTextField();
        tableNumField.setBounds(255, 120, 40, 30);
        bgPanel.add(tableNumField);
    }

    // MODIFIES: this
    // EFFECTS: creates JLabel request to prompt the user entry the boss name
    //          and JTextField to receive user's input
    //          assigns JTextField to bossNameField
    private void askAndGetBossName() {
        JLabel requestBossName = new JLabel("Entry the boss name: ");
        requestBossName.setBounds(20, 60, 150, 30);
        bgPanel.add(requestBossName);

        bossNameField = new JTextField();
        bossNameField.setBounds(255, 70, 40, 30);
        bgPanel.add(bossNameField);
    }

    // MODIFIES: this
    // EFFECTS: removes bgPanel from this JFrame
    private void removeBgPanel() {
        remove(bgPanel);
    }

    // MODIFIES: this
    // EFFECTS: adjusts this JFrame window to user's use
    //          initials tablesPanel and InfoPanel
    private void runRestaurant() {
        setSize(WIDTH, HEIGHT);
        setTitle(myRestaurant.getBossName() + "'s Restaurant");
        getJMenuBar().setVisible(true);

        bgPanel = new JPanel();
        bgPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        bgPanel.setLayout(new GridLayout(2, 1));

        add(bgPanel);

        initialTablesPanel();

        initialInfoPanel();

    }


    // MODIFIES: this
    // EFFECTS: adds an infoPanel with no data to this JFrame
    private void initialInfoPanel() {
        infoPanel = new JPanel();
        infoPanel.setPreferredSize(new Dimension(WIDTH, 300));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));

        String[][] emptyArray = {};
        addOrderListPanel(infoPanel, emptyArray);
        addWaitlistPanel(infoPanel, emptyArray);

        bgPanel.add(infoPanel);
    }

    // MODIFIES: infoPanel
    // EFFECTS: creates a waitlistPanel given String[][] array of waitlist data
    //          adds the waitlistPanel to infoPanel
    private void addWaitlistPanel(JPanel infoPanel, String[][] array) {
        String[] waitlistColumn = {"Customer Name", "Phone Number"};

        String[][] waitlistData = array;
        JTable waitlistTable = new JTable(waitlistData, waitlistColumn);
        waitlistTable.setEnabled(false);
        scrollWaitlistTablePane = new JScrollPane(waitlistTable);
        scrollWaitlistTablePane.setPreferredSize(new Dimension(300, 300));
        scrollWaitlistTablePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        infoPanel.add(scrollWaitlistTablePane);
    }

    // EFFECTS: returns String[][] waitlistData according to the current state of myRestaurant
    private String[][] getWaitlistData() {
        ArrayList<String[]> resultArrayList = new ArrayList<>();
        for (Customer customer : myRestaurant.getWaitlist()) {
            String[] customerData = {customer.getName(), customer.getPhoneNumber()};
            resultArrayList.add(customerData);
        }

        String[][] waitlistData = new String[resultArrayList.size()][];
        for (int i = 0; i < resultArrayList.size(); i++) {
            waitlistData[i] = resultArrayList.get(i);
        }
        return waitlistData;
    }

    // MODIFIES: infoPanel
    // EFFECTS: creates a orderListPanel given String[][] array of orderlist data
    //          adds the orderListPanel to infoPanel
    private void addOrderListPanel(JPanel infoPanel, String[][] array) {
        String[] itemsColumn = {"Item Name", "Price"};

        String[][] orderData = array;
        JTable itemsTable = new JTable(orderData, itemsColumn);
        itemsTable.setEnabled(false);
        scrollItemsTablePane = new JScrollPane(itemsTable);
        scrollItemsTablePane.setPreferredSize(new Dimension(700, 300));
        scrollItemsTablePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        infoPanel.add(scrollItemsTablePane);
    }

    // EFFECTS: returns String[][] orderListData according to the current state of myRestaurant
    private String[][] getOrderListData(List<Item> orderList) {
        ArrayList<String[]> resultArrayList = new ArrayList<>();
        for (Item item : orderList) {
            String itemName = item.getName();
            double itemPrice = item.getPrice();
            String[] customerData = {itemName, String.valueOf(itemPrice)};
            resultArrayList.add(customerData);
        }
        String[][] orderData = new String[resultArrayList.size()][];

        for (int i = 0; i < resultArrayList.size(); i++) {
            orderData[i] = resultArrayList.get(i);
        }

        return orderData;
    }

    // MODIFIES: this
    // EFFECTS: sets up the tablesPanel that consists of the selection of a given number tables combo box
    private void initialTablesPanel() {
        JPanel tablesPanel = new JPanel();
        tablesPanel.setPreferredSize(new Dimension(1000, 400));
        tablesPanel.setBackground(Color.white);
        tablesPanel.setLayout(null);
        JLabel chosenTable = new JLabel("Please choose a Table");
        chosenTable.setBounds(400, 100, 400, 30);
        JLabel defaultTable = new JLabel("(Default Table 1 is chosen)");
        defaultTable.setBounds(390, 130, 400, 30);
        tablesPanel.add(chosenTable);
        tablesPanel.add(defaultTable);

        Vector tableNames = getTableNamesForComboBox();
        JComboBox comboBox = new JComboBox<>(tableNames);
        comboBox.setBounds(350, 200, 250, 30);
        addListenerToComboBox(chosenTable, comboBox, defaultTable);
        tablesPanel.add(comboBox);
        bgPanel.add(tablesPanel);
    }

    // MODIFIES: comboBox
    // EFFECTS: adds ActionListener with specific implementation of actionPerformed to comboBox
    private void addListenerToComboBox(JLabel chosenTable, JComboBox comboBox, JLabel defaultTable) {
        comboBox.addActionListener(new ActionListener() {

            // MODIFIES: chosenTable, defaultTable
            // EFFECTS: sets text for JLabel chosenTable, sets JLabel defaultTable invisible,
            //          and updates InfoPanel given the table chosen by the user
            @Override
            public void actionPerformed(ActionEvent e) {
                int indexOfChosenTable = comboBox.getSelectedIndex();
                String valueChosenTable = "You have chosen " + comboBox.getItemAt(indexOfChosenTable);
                defaultTable.setVisible(false);
                chosenTable.setText(valueChosenTable);
                currentTable = myRestaurant.getTables().get(indexOfChosenTable);
                updateInfoPanel();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: updates InfoPanel given indexOfChosenTable and the current state of myRestaurant
    //          updates currentTable with the table chosen by user
    private void updateInfoPanel() {
        infoPanel.remove(scrollItemsTablePane);
        infoPanel.remove(scrollWaitlistTablePane);

        List<Item> itemList = currentTable.getOrderList();
        String[][] orderData = getOrderListData(itemList);
        addOrderListPanel(infoPanel, orderData);

        String[][] waitlistData = getWaitlistData();
        addWaitlistPanel(infoPanel, waitlistData);

        infoPanel.revalidate();

    }

    // EFFECTS：returns Vector of all table names
    private Vector getTableNamesForComboBox() {
        Vector resultVector = new Vector();
        List<Table> tables = myRestaurant.getTables();
        for (Table table : tables) {
            String tableString = "Table " + table.getTableNumber();
            resultVector.add(tableString);
        }
        return resultVector;
    }

    // MODIFIES: this
    // EFFECTS: creates a JMenuBar with JMenus related to order, waitlist
    //          adds JMenuBar to this JFrame
    private void addMenuBar() {
        menuBar = new JMenuBar();
        addOrdersMenu();
        addWaitlistMenu();

        menuBar.setVisible(false);
        setJMenuBar(menuBar);
    }

    // MODIFIES: this
    // EFFECTS: creates waitlistMenu with few waitlist MenuItems ,and adds it to this
    private void addWaitlistMenu() {
        JMenu waitlist = new JMenu("Waitlist");

        JMenuItem addCustomer = new JMenuItem("Add customer");
        addListenerToAddCustomer(addCustomer);

        JMenuItem removeCustomer = new JMenuItem("Remove customer");
        addListenerToRemoveCustomer(removeCustomer);

        waitlist.add(addCustomer);
        waitlist.add(removeCustomer);

        menuBar.add(waitlist);
    }

    // MODIFIES: removeCustomer
    // EFFECTS: adds ActionListener with specific implementation of actionPerformed to removeCustomer
    private void addListenerToRemoveCustomer(JMenuItem removeCustomer) {
        removeCustomer.addActionListener(new ActionListener() {

            // MODIFIES: this
            // EFFECTS: display a JOptionPane.showConfirmDialog that enables the user to move the first
            //          customer in the waitlist, and refreshes InfoPanel
            @Override
            public void actionPerformed(ActionEvent e) {
                if (myRestaurant.getWaitlist().size() == 0) {
                    // No customer in the waitlist, show an error message
                    JOptionPane.showMessageDialog(null, " NO customer in the waitlist! ",
                            "Empty waitlist", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Request customer name input
                    JPanel inputsPanel = new JPanel();
                    JLabel stringLabel = new JLabel(" Do you want to remove the first customer in the waitlist? ");
                    inputsPanel.add(stringLabel);

                    int result = JOptionPane.showConfirmDialog(null, inputsPanel,
                            "", JOptionPane.OK_CANCEL_OPTION);

                    if (result == JOptionPane.OK_OPTION) {
                        myRestaurant.getWaitlist().remove(0);
                    }
                    updateInfoPanel();
                }
            }
        });
    }


    // MODIFIES: addCustomer
    // EFFECTS: adds ActionListener with specific implementation of actionPerformed to addCustomer
    private void addListenerToAddCustomer(JMenuItem addCustomer) {
        addCustomer.addActionListener(new ActionListener() {

            // MODIFIES: this
            // EFFECTS: display a JOptionPane.showConfirmDialog that enables the user to add customer to the waitlist,
            //          and refreshes InfoPanel
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel inputsPanel = new JPanel();
                inputsPanel.setLayout(new GridLayout(2, 2));

                JLabel stringLabel = new JLabel("Customer name: ");
                JTextField stringField = new JTextField(20);
                inputsPanel.add(stringLabel);
                inputsPanel.add(stringField);

                JLabel phoneNumberLabel = new JLabel("Phone number: ");
                JTextField phoneNumberField = new JTextField(20);
                inputsPanel.add(phoneNumberLabel);
                inputsPanel.add(phoneNumberField);

                int result = JOptionPane.showConfirmDialog(null, inputsPanel,
                        "Enter name and phone number", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    String cname = stringField.getText();
                    String phNumber = phoneNumberField.getText();
                    myRestaurant.getWaitlist().add(new Customer(cname, phNumber));
                }
                updateInfoPanel();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates ordersMenu with few ordersMenuItems, and adds it to this
    private void addOrdersMenu() {
        JMenu orders = new JMenu("Orders");
        JMenuItem takeOrder = new JMenuItem("Take order");
        addListenerToTakeOrder(takeOrder);

        JMenuItem removeItem = new JMenuItem("Remove item");
        addListenerToRemoveItem(removeItem);

        JMenuItem checkOut = new JMenuItem("Check out");
        addListenerToCheckOut(checkOut);

        orders.add(takeOrder);
        orders.add(removeItem);
        orders.add(checkOut);

        menuBar.add(orders);
    }

    // MODIFIES: checkOut
    // EFFECTS: adds ActionListener with specific implementation of actionPerformed to check out
    private void addListenerToCheckOut(JMenuItem checkOut) {
        checkOut.addActionListener(new ActionListener() {

            // MODIFIES: this
            // EFFECTS: display a JOptionPane.showConfirmDialog that enables the user to check out for the currentTable,
            //          and refreshes InfoPanel
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel checkOutPanel = new JPanel();

                JTextArea bill = new JTextArea();
                bill.setText(currentTable.getBill());
                bill.setEnabled(false);
                checkOutPanel.add(bill);

                JTextField paymentReminder = new JTextField(" Is the Customer ready to pay the bill? ");
                paymentReminder.setEnabled(false);
                checkOutPanel.add(paymentReminder);

                int result = JOptionPane.showConfirmDialog(null, checkOutPanel,
                        "Check out", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    currentTable.cleanUpTable();
                }
                updateInfoPanel();
            }
        });
    }

    // MODIFIES: removeItem
    // EFFECTS: adds ActionListener with specific implementation of actionPerformed to removeItem
    private void addListenerToRemoveItem(JMenuItem removeItem) {
        removeItem.addActionListener(new ActionListener() {

            // MODIFIES: this
            // EFFECTS: display a JOptionPane.showConfirmDialog that enables the user to remove item
            //          for the currentTable, and refreshes InfoPanel
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel inputsPanel = new JPanel();

                JLabel stringLabel = new JLabel("Item name: ");
                JTextField stringField = new JTextField(30);
                inputsPanel.add(stringLabel);
                inputsPanel.add(stringField);

                int result = JOptionPane.showConfirmDialog(null, inputsPanel,
                        "Enter Item name", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    String itemName = stringField.getText();
                    Item itemSearched = lookForItem(itemName, currentTable.getOrderList());
                    if (itemSearched == null) {
                        // No given item in the currentTable's orderlist, show an error message
                        JOptionPane.showMessageDialog(null, " Did not find the Item " + itemName,
                                "Fail to find item", JOptionPane.ERROR_MESSAGE);
                    } else {
                        currentTable.removeItem(itemSearched);
                    }
                }
                updateInfoPanel();
            }
        });
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
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to write to file " + jsonDestination,
                    "Fail to save", JOptionPane.ERROR_MESSAGE);
        }

    }

    // MODIFIES: takeOrder
    // EFFECTS: adds ActionListener with specific implementation of actionPerformed to takeOrder
    private void addListenerToTakeOrder(JMenuItem takeOrder) {
        takeOrder.addActionListener(new ActionListener() {

            // MODIFIES: this
            // EFFECTS: display a JOptionPane.showConfirmDialog that enables the user to add item for the currentTable,
            //          and refreshes InfoPanel
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel inputsPanel = new JPanel();
                inputsPanel.setLayout(new GridLayout(2, 2));

                JLabel stringLabel = new JLabel("Item name: ");
                JTextField stringField = new JTextField();
                inputsPanel.add(stringLabel);
                inputsPanel.add(stringField);

                JLabel doubleLabel = new JLabel("Price: ");
                JTextField doubleField = new JTextField();
                inputsPanel.add(doubleLabel);
                inputsPanel.add(doubleField);

                int result = JOptionPane.showConfirmDialog(null, inputsPanel,
                        "Enter Item name and price", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    String itemName = stringField.getText();
                    double itemPrice = Double.parseDouble(doubleField.getText());
                    currentTable.addItem(new Item(itemName, itemPrice));
                }
                updateInfoPanel();
            }

        });
    }

    public static void main(String[] args) {
        new GraphicalUIFeastApp();
    }
}
