package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.*;

public class TableTest {
    
    private Table tableTest;

    @BeforeEach
    void runBefore() {
        tableTest = new Table();
    }


    @Test
    void testConstructor() {
        assertTrue(tableTest.getStatus());
        assertEquals(tableTest.getOrderList().size(),0);
        assertEquals(tableTest.getTotalAmount(), 0.0);
        assertTrue(tableTest.getTableNumber() > 0);
    }

    @Test
    void testAddOneItem(){
        Item i1 = new Item("鱼香肉丝",35);
        ArrayList<Item> myList = new ArrayList<>();
        myList.add(i1);
        tableTest.addItem(i1);
        ArrayList<Item> result = tableTest.getOrderList();
        assertEquals(result, myList);
    }

    @Test
    void testAddThreeItem() {
        Item i1 = new Item("鱼香肉丝",35);
        Item i2 = new Item("宫保鸡丁",40);
        Item i3 = new Item("兰州牛肉面",20);

        ArrayList<Item> myList = new ArrayList<>();
        myList.add(i1);
        myList.add(i2);
        myList.add(i3);

        tableTest.addItem(i1);
        tableTest.addItem(i2);
        tableTest.addItem(i3);

        ArrayList<Item> result = tableTest.getOrderList();
        assertEquals(result, myList);
    }

    @Test
    void testAddDuplicateItem() {
        Item i1 = new Item("鱼香肉丝",35);
        Item i2 = new Item("宫保鸡丁",40);

        ArrayList<Item> myList = new ArrayList<>();
        myList.add(i1);
        myList.add(i2);
        myList.add(i1);

        tableTest.addItem(i1);
        tableTest.addItem(i2);
        tableTest.addItem(i1);

        ArrayList<Item> result = tableTest.getOrderList();
        assertEquals(result, myList);
    }

    @Test
    void testRemoveOneItem() {

        Item i1 = new Item("鱼香肉丝",35);
        Item i2 = new Item("宫保鸡丁",40);

        ArrayList<Item> myList = new ArrayList<>();
        myList.add(i2);

        tableTest.addItem(i1);
        tableTest.addItem(i2);

        tableTest.removeItem(i1);

        ArrayList<Item> result = tableTest.getOrderList();
        assertEquals(result, myList);

    }

    int countOccurrenceHelper(Item item, ArrayList<Item> itemArrayList) {
        int result = 0;
        for (Item i : itemArrayList) {
            if (i==item) {
                result++;
            }
        }
        return  result;
    }

    @Test
    void testRemoveDuplicateItem() {
        Item i1 = new Item("鱼香肉丝",35);
        Item i2 = new Item("宫保鸡丁",40);

        tableTest.addItem(i1);
        tableTest.addItem(i2);
        tableTest.addItem(i1);
        tableTest.addItem(i1);

        int size1 =  tableTest.getOrderList().size();

        tableTest.removeItem(i1);

        int size2 =  tableTest.getOrderList().size();
        ArrayList<Item> resultList = tableTest.getOrderList();

        assertEquals(countOccurrenceHelper(i2, resultList), 1 );
        assertEquals(size1-1, size2 );
    }

    @Test
    void testSetStatusTrueToFalse() {
        assertTrue(tableTest.getStatus());
        tableTest.setStatus(false);
        assertFalse(tableTest.getStatus());
    }

    @Test
    void testSetStatusFasleToTrue() {
        assertTrue(tableTest.getStatus());
        tableTest.setStatus(false);
        assertFalse(tableTest.getStatus());
        tableTest.setStatus(true);
        assertTrue(tableTest.getStatus());
    }

    @Test
    void testGetBillOneItemOrdered() {
        Item i1 = new Item("鱼香肉丝",35);

        tableTest.addItem(i1);
        String result = tableTest.getBill();

        assertEquals(result, "\n Item Name: " + i1.getName() + " ｜ Item Price :" + i1.getPrice()
                + "\n The total amount of the bill is " + tableTest.getTotalAmount());
    }

    @Test
    void testGetBillThreeItemOrdered() {
        Item i1 = new Item("鱼香肉丝",35);
        Item i2 = new Item("宫保鸡丁",40);
        Item i3 = new Item("兰州牛肉面",20);

        tableTest.addItem(i1);
        tableTest.addItem(i2);
        tableTest.addItem(i3);

        String result = tableTest.getBill();

        assertEquals(result, "\n Item Name: " + i1.getName() + " ｜ Item Price :" + i1.getPrice()
                + "\n Item Name: " + i2.getName() + " ｜ Item Price :" + i2.getPrice()
                + "\n Item Name: " + i3.getName() + " ｜ Item Price :" + i3.getPrice()
                + "\n The total amount of the bill is " + tableTest.getTotalAmount());
    }

    @Test
    void testSeparateBillByOne() {
        Item i1 = new Item("鱼香肉丝",35);
        Item i2 = new Item("宫保鸡丁",40);
        Item i3 = new Item("兰州牛肉面",20);

        tableTest.addItem(i1);
        tableTest.addItem(i2);
        tableTest.addItem(i3);

        String result = tableTest.separateBill(1);

        assertEquals(result, "The amount of a separate bill :" + tableTest.getTotalAmount()/1);


    }

    @Test
    void testSeparateBillByThree() {
        Item i1 = new Item("鱼香肉丝",35);
        Item i2 = new Item("宫保鸡丁",40);
        Item i3 = new Item("兰州牛肉面",20);

        tableTest.addItem(i1);
        tableTest.addItem(i2);
        tableTest.addItem(i3);

        String result = tableTest.separateBill(3);

        assertEquals(result, "The amount of a separate bill :" + tableTest.getTotalAmount()/3);

    }

    @Test
    void testCleanUpTable() {
        Item i1 = new Item("鱼香肉丝",35);
        Item i2 = new Item("宫保鸡丁",40);
        Item i3 = new Item("兰州牛肉面",20);

        tableTest.addItem(i1);
        tableTest.addItem(i2);
        tableTest.addItem(i3);

        tableTest.cleanUpTable();
        assertEquals(tableTest.getTotalAmount(), 0.0);
        assertEquals(tableTest.getOrderList(), new ArrayList<Item>());
        assertTrue(tableTest.getStatus());
    }

    }

