import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MemoryControlsTest {
 Day day1;
 Day day2;
 Event event1;
 Event event2;
 String string1;
 String string2;
 ArrayList<Event> list;


    @BeforeEach
    public void setup() {
        day1 = new Day(1);
        day2 = new Day(2);
        event1 = new Event(day1, "Test1","description", "faction");
        event2 = new Event(day2, "Test2", "description", "faction");
        string1 = event1.toString();
        string2 = event2.toString();
        list = new ArrayList<>();

    }

    @Test
    public void testReadEvents() {
        Scanner sc = new Scanner(string1);
        list.add(event1);
        ArrayList<Event> listTest = MemoryControls.readEvents(sc);
        assertEquals(list, listTest);
    }
    @Test
    public void testReadTwoEvents(){
        Scanner sc = new Scanner(string1 + "\n" + string2);
        list.add(event1);
        list.add(event2);
        ArrayList<Event> listTest = MemoryControls.readEvents(sc);
        assertEquals(list, listTest);
    }

    @Test
    public void testReadCurrentDate() {
        Scanner sc = new Scanner("1/1");
        Day testDay = MemoryControls.readCurrentDate(sc);
        assertEquals(day1,testDay);
    }

    @Test
    public void testWriteCurrentDate() throws IOException {
        BufferedWriter mockWriter = mock(BufferedWriter.class);
        String excpectedString = day1.toString();
        Scanner sc = new Scanner("1/1");
        Day testDay = MemoryControls.readCurrentDate(sc);
        MemoryControls.writeCurrentDate(mockWriter);
        verify(mockWriter).write(excpectedString);

    }

    @Test
    public void testWriteEvents() throws IOException {
        BufferedWriter mockWriter = mock(BufferedWriter.class);
        Scanner sc = new Scanner(string1 + "\n" + string2);

        list.add(event1);
        list.add(event2);

        MemoryControls.readEvents(sc);
        MemoryControls.writeEvents(mockWriter);

        InOrder inOrder = Mockito.inOrder(mockWriter);
        inOrder.verify(mockWriter).write(string1 + "\n");
        inOrder.verify(mockWriter).write(string2 + "\n");

    }

    @Test
    public void searchEventsOnDay() {
        Event event3 = new Event(day1, "Test3", "description", "faction");
        List expected = Arrays.asList(event1,event3);
        Scanner sc = new Scanner(string1 + "\n" + string2 + "\n" + event3.toString());
        MemoryControls.readEvents(sc);
        List result = MemoryControls.searchEventsOnDay(day1);
        assertEquals(expected, result);


    }

    @Test
    public void sortEvents() {
        Event event3 = new Event(day1, "Test3", "description", "faction");
        Event event4 = new Event(day2, "Test4", "description", "faction");

        ArrayList<Event> inOrder = new ArrayList<>();
        inOrder.add(event1);
        inOrder.add(event3);
        inOrder.add(event2);
        inOrder.add(event4);

        ArrayList<Event> outOfOrder = new ArrayList<>();
        outOfOrder.add(event2);
        outOfOrder.add(event1);
        outOfOrder.add(event4);
        outOfOrder.add(event3);

        MemoryControls.setEvents(outOfOrder);
        MemoryControls.sortEvents();
        assertEquals(MemoryControls.getEvents(), outOfOrder);
    }
    @Test
    public void eventOnDayTest(){
        Day day3 = new Day(3);
        Day day4 = new Day(4);
        Event event3 = new Event(day3, "Test3", "description", "faction");
        Event event4 = new Event(day4, "Test4", "description", "faction");

        ArrayList<Event> inOrder = new ArrayList<>();

        inOrder.add(event1);
        inOrder.add(event2);
        inOrder.add(event3);
        inOrder.add(event4);

        MemoryControls.setEvents(inOrder);
        assertTrue(MemoryControls.eventOnDay(day3));
        Day day5 = new Day(5);
        assertFalse(MemoryControls.eventOnDay(day5));
    }
    @Test
    public void removeEventTest(){
        ArrayList<Event> inOrder = new ArrayList<>();
        inOrder.add(event1);
        inOrder.add(event2);
        MemoryControls.setEvents(inOrder);
        MemoryControls.removeEvent(event1);
        assertFalse(MemoryControls.getEvents().contains(event1));
    }
    @Test
    public void advanceCurrentDayTest(){
        MemoryControls.setCurrentDay(day1);
        MemoryControls.advanceCurrentDay(1);
        assertEquals(day2, MemoryControls.getCurrentDay());
    }
    @Test
    public void allEventsInNextDaysTest(){

    }
    @Test
    public void searchEventsOnMonth(){

    }

}