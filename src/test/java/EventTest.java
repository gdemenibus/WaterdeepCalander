import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.Assert.*;

public class EventTest {
    Event testEvent;
    String expected;
    Day day;

    @Before
    public void setup(){
         day = new Day(2);
         testEvent = new Event(day,"Test","It's a test", "testFaction");
         expected = "Test\n2/1\nIt's a test\ntestFaction";
    }

    @Test
    public void testToString() {
        assertEquals(expected,testEvent.toString());
    }
    @Test
    public void testScanner() throws FileNotFoundException {
        Scanner sc = new Scanner(expected);
        Event beingTested = Event.Scanner(sc);
        assertEquals(testEvent,beingTested);

    }
    @Test
    public void getterNsetterTest(){
        assertEquals(testEvent.getDay(), day);
        assertEquals(testEvent.getDescription(), "It's a test");
        assertEquals(testEvent.getFaction(), "testFaction");
        assertEquals(testEvent.getName(), "Test");
    }
}