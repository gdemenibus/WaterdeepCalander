import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


public class DayTest {

    @Test
    public void monthofDay() {
        Day day = new Day(30);
        int month = 1;
        assertEquals( month, day.monthOfDay());
    }
    @Test
    public void monthofDayTest() {
        Day day = new Day(1);
        int month = 1;
        assertEquals(month, day.monthOfDay());
    }

    @Test
    public void testToString() {
    }
    @Test
    public void TestdateToDay(){
        int day = 32;
        int month = 2;
        String toBeRead = "2/2";
        Day expected = new Day(32);
        Day scanned = Day.dateToDay(toBeRead);
        assertEquals(expected, scanned);
    }
}
