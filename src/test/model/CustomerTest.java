package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerTest {

    private Customer customerTest;

    @BeforeEach
    void runBefore() {
        customerTest = new Customer("CustomerTest", 1234567890);
    }

    @Test
    void testConstructor() {
        assertEquals(customerTest.getName(),"CustomerTest");
        assertEquals(customerTest.getPhoneNumber(),1234567890);
    }

    @Test
    void testMessageToCustomer() {
        assertEquals(customerTest.messageToCustomer(),
                "[Dear " + customerTest.getName() + ", the table for you is ready! " +
                        "Please show up on the waiting " + "area]" + " to phone number: "
                        + customerTest.getPhoneNumber());
    }
}