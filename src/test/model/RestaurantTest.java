package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestaurantTest {
    private final String bossNameTest = "Kai";
    private final int tableNumTest = 10;
    private Restaurant restaurantTest;

    @BeforeEach
    void runBefore() {
        restaurantTest = new Restaurant(bossNameTest,tableNumTest);
    }

    @Test
    void testConstructor() {
        assertEquals(restaurantTest.getBossName(),bossNameTest);
        assertEquals(restaurantTest.getWaitlist().size(),0);
        assertEquals(restaurantTest.getTables().size(),10);
    }
}
