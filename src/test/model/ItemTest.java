package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ItemTest{

    private Item itemTest;

    @BeforeEach
    void runBefore() {
        itemTest = new Item("ItemTest",20.5);

    }

    @Test
    void testConstructor(){
        assertEquals(itemTest.getName(),"ItemTest");
        assertEquals(itemTest.getPrice(),20.5);
    }


    @Test
    void testSetName() {
        itemTest.setName("Butter Chicken");
        assertEquals(itemTest.getName(),"Butter Chicken");
    }

    @Test
    void testSetPrice() {
        itemTest.setPrice(30.1);
        assertEquals(itemTest.getPrice(),30.1);
    }


    @Test
    void testToJson() {
        JSONObject jsonTest = itemTest.toJson();
        assertEquals(jsonTest.getString("name"),itemTest.getName());
        assertEquals(jsonTest.getDouble("price"),itemTest.getPrice());
    }
}
