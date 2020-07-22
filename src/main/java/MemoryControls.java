import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


//Need to decide how Events will be stored internally.
//Whenever days are advanced, this data structure will need to be accessed and compared
//Hence we need something easy to access, and where we can compare every element, and we can have multiple events per
//day
//HashSet might work if we take it's iterator.
//Could use an array and QuickSearch, but that also requires storing the array.

public class MemoryControls {
    private static ArrayList<Event> events;
    //TO CONSIDER: Using a sorted ArrayList, sorting events in chronological order. Makes it easier to find if an event
    // is on the same day as the current day!
    private static Day currentDay;

    public static ArrayList<Event> getEvents() {
        return events;
    }

    public static void setEvents(ArrayList<Event> events) {
        MemoryControls.events = events;
    }

    public static void setCurrentDay(Day currentDay) {
        MemoryControls.currentDay = currentDay;
    }

    public static Day getCurrentDay() {

        return currentDay;
    }

    /**
     * Turns the file that is read into a HashSet with all events, an easy way to check if we already a certain event
     *
     * @param sc
     * @return
     */
    public static ArrayList<Event> readEvents(Scanner sc) {
        events = new ArrayList<Event>();
        try {
            while (sc.hasNext()) {
                Event read = Event.Scanner(sc);
                events.add(read);
            }
            return events;
        } catch (FileNotFoundException e) {
            System.out.println("File not found, check for typos");
        }
        return events;
    }

    /**
     * Reads the current date from scanner which is fed (It's up to the main class/boot method to feed the proper file,
     * Makes for easier adaptability
     *
     * @param sc
     * @return
     */
    public static Day readCurrentDate(Scanner sc) {
        String day = sc.nextLine();
        currentDay = Day.dateToDay(day);
        return currentDay;
    }

    /**
     * Writes new current day to memory. Needs a writer, which will be fed when the application exits.
     * Writing to memory should only happen when quiting the application. (If this was a multi user application,
     * it would write with every transaction)
     *
     * @param wr
     */
    public static void writeCurrentDate(Writer wr) {
        try {
            wr.write(currentDay.toString());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO Problem found, check online for fix");
        }
    }

    /**
     * Writes the set of all events into the memory.
     *
     * @param wr
     */
    public static void writeEvents(Writer wr) {
        Iterator<Event> iterator = events.iterator();
        while (iterator.hasNext()) {
            try {
                wr.write(iterator.next().toString() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*public static void addEvent(Event e) {
        events.add(e);
    }*/

    public static void removeEvent(Event e) {
        events.remove(e);
    }

    public static void advanceCurrentDay(int i) {
        currentDay.advanceDay(i);
    }

    /**Searches weather of not there is an event on that specific day;
     * @param day
     * @return
     */
    public static boolean eventOnDay(Day day){
        Event[] eventsArray = new Event[events.size()];
        return SearchHelp(day, events.toArray(eventsArray));

    }

    /**Recursive Function, takes care of the search.
     *
     * @param day
     * @param events1
     * @return
     */
    private static boolean SearchHelp(Day day, Event[] events1){
        int middle = events1.length/2;

        if(events1[middle].onSameDay(day)){
            return true;
        }
        else if(events1.length == 1 ){
            return false;
        }
        else if(events1[middle].getDay().getDate() > day.getDate()){
            Event[] lowerHalf = new Event[middle];
            for(int i = 0; i < middle; i++){
                lowerHalf[i] = events1[i];
            }
            return SearchHelp(day, lowerHalf);

        }
        else if(events1[middle].getDay().getDate() < day.getDate()){
            Event[] upperHalf = new Event[middle];
            for(int i = 0; i < middle; i++){
                upperHalf[i] = events1[middle + i];
            }
            return SearchHelp(day, upperHalf);
        }
        return false;

    }

    /**Realised previous function doesn't take care of multiple events with the same day,
     * Made a lamba function to take care of that and return a list of all events on a certain day
     *
     * @param day
     * @return
     */
    public static List<Event> searchEventsOnDay(Day day){
        List<Event> output = events.parallelStream()
                .filter(t -> t.getDay().equals(day)).collect(Collectors.toList());
        return output;
    }

    public static List<Event> searchEventsInMonth(Day day){
        List<Event> output = events.parallelStream()
                .filter(t -> t.getDay().monthOfDay() == day.monthOfDay()).collect(Collectors.toList());
        return output;
    }


    /**Returns a list of all events that took place in the following days
     *
     * @param i
     * @return
     */
    public static List<Event> allEventsInNextDays(int i){
        List<Event> output = new ArrayList<>();
        for(int j = 1; j <= i; j++){
            Day followingDay = new Day(currentDay.getDate() + j);
            output.addAll(searchEventsOnDay(followingDay));
        }
        return output;
    }

    /**Sorts the events with Insertion Sort. Very good for  nearly sorted lists.
     *
     */
    public static void sortEvents(){
        for(int i = 0; i < events.size(); i++){
            for(int j = i; j > 0; j--){
                if(events.get(j).getDay().getDate() < events.get(j-i).getDay().getDate()){
                    swap(j, j-1);
                }
                else{
                    break;
                }
            }

        }

    }

    /**Auxilary function for Sorting for insertion sort
     *
     * @param a
     * @param b
     */
    private static void swap(int a, int b){
        Event temp = events.get(a);
        events.set(a, events.get(b));
        events.set(b, temp);
    }

    public static void addEvent(Event e){
        events.add(e);
        sortEvents();
    }


}
