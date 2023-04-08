package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Event class
 * It is copied and adapted to FeastApp from AlarmSystem project
 */
public class EventTest {

    //IMPORTANT: Please read this! ! !
    //NOTE: tests for Event might fail if times, at which lines creating events to compare
    //      with each other are executed, are slightly different.
    //      these pairs of lines run in same millisecond for this test to make sense and pass.
    //      if some of them failed, you can try to quit IntelliJ, restart , and then re-run them
    //      , which may work.


    @Test
    public void testEvent() {
        Date d = Calendar.getInstance().getTime();
        Event eventTest = new Event("EventTest");

        assertEquals("EventTest", eventTest.getDescription());
        assertEquals(d.toString(), eventTest.getDate().toString());
    }

    @Test
    public void testToString() {
        Date d = Calendar.getInstance().getTime();
        Event eventTest = new Event("EventTest");

        assertEquals(d.toString() + "\n" + "EventTest", eventTest.toString());
    }

    @Test
    public void testEqualsNullAndDifferentClassFalse() {
        Event event1 = new Event("EventTest");
        assertFalse(event1.equals(null));
        assertFalse(event1.equals(new Date()));
    }

    @Test
    public void testEqualsTrue() {
        Event event1 = new Event("EventTest");
        Event event2 = new Event("EventTest");

        assertTrue(event1.equals(event1));
        assertTrue(event1.equals(event2));
    }

    @Test
    public void testHashCode() {
        Event event1 = new Event("EventTest");
        Event event2 = new Event("EventTest");

        assertTrue(event1.hashCode() == event1.hashCode());
        assertTrue(event1.hashCode() == event2.hashCode());

    }





}
