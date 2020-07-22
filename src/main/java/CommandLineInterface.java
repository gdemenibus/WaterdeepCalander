import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class CommandLineInterface {

    public static void main(String[] args) throws FileNotFoundException {
        File currentDay = new File("currentDay.txt");
        File events = new File("Events.txt");

        Scanner dayScanner = new Scanner(currentDay);
        Scanner eventScanner = new Scanner(events);

        MemoryControls.readCurrentDate(dayScanner);
        MemoryControls.readEvents(eventScanner);

        Scanner inputScanner = new Scanner(System.in);
        boolean chooseExit = false;
        while(!chooseExit) {
            System.out.println(
                    "Welcome to the Waterdeep Calender Manager. Today is: " + MemoryControls.getCurrentDay().getDateHuman() + " of " + MemoryControls.getCurrentDay().nameOfMonth() + "\n" +
                            "Which of the following would you like to do: " +
                            "\n 1) Move current date " +
                            "\n 2) Add an event to database " +
                            "\n 3) Check events this month" +
                            "\n 4) Terminate app and log all changes"
            );
            int scanned = inputScanner.nextInt();
            switch (scanned){
                case 1: CommandLineInterface.moveCurrentDate();
                    break;
                case 2: CommandLineInterface.addEvent();
                    break;
                case 3: CommandLineInterface.CheckEventsMonth();
                    break;
                case 4: WriteToMemory(currentDay,events);
                    chooseExit = true;
                    break;
            }

        }



    }
    public static void moveCurrentDate(){
        Scanner userInput = new Scanner(System.in);
        System.out.println("By how many days do you want to advance time?");
        int scanned = userInput.nextInt();

        List<Event> allEvents = MemoryControls.allEventsInNextDays(scanned);
        String descriptionEnvelope = "Here are all the events that happen with this advance \n";
        System.out.println(descriptionEnvelope + eventsToConsoleString(allEvents));

        MemoryControls.advanceCurrentDay(scanned);


    }

    private static String eventsToConsoleString(List<Event> eventsList){
        Iterator iterator = eventsList.iterator();
        String output = "";
        while(iterator.hasNext()){
            Event toBePrinted = (Event) iterator.next();
            output = toBePrinted.toStringConsole() + "\n";
        }
        return output;
    }

    public static void addEvent() {
        Scanner userInout = new Scanner(System.in);
        System.out.println("When day of the month does this event take place? \n");
        int dayOfMonth = userInout.nextInt();

        System.out.println("What Month does this event take place in (Number)? \n");
        int month = userInout.nextInt();

        int dayTotal = month * 30 + dayOfMonth;
        Day day = new Day(dayTotal);

        System.out.println("What is the name of the event? \n");
        userInout.nextLine();
        String name = userInout.nextLine();

        System.out.println("Description of the event? \n" );
        String description = userInout.nextLine();

        System.out.println("What is the faction tied to this event? \n");
        String faction = userInout.nextLine();

        Event added = new Event(day, name, description, faction);
        MemoryControls.addEvent(added);

    }
    public static void CheckEventsMonth(){
        System.out.println("Events in the Month of " + MemoryControls.getCurrentDay().nameOfMonth() + " : ");
        List<Event> eventsInMonth = MemoryControls.searchEventsInMonth(MemoryControls.getCurrentDay());
        System.out.println(eventsToConsoleString(eventsInMonth));
    }
    public static void WriteToMemory(File currentDay, File events){
        try {
            FileWriter writerDay = new FileWriter(currentDay);
            FileWriter writerEvent = new FileWriter(events);

            MemoryControls.writeEvents(writerEvent);
            MemoryControls.writeCurrentDate(writerDay);

            writerDay.close();
            writerEvent.close();

        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
