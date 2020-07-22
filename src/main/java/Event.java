import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class Event {
    private Day day;
    private String name;
    private String description;
    private String faction;

    /**Event Constructor
     * Takes in a
     * @param day
     * @param name
     * @param description
     * @param faction
     */
    public Event(Day day, String name, String description, String faction) {
        this.day = day;
        this.name = name;
        this.description = description;
        this.faction = faction;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFaction() {
        return faction;
    }

    public void setFaction(String faction) {
        this.faction = faction;
    }

    /**The toString method used for storing the information into a file.
     *
     * @return the String
     */
    @Override
    public String toString() {
        String output = name + "\n" + day.toString() + "\n" + description + "\n" + faction;
        return output;
    }
    public String toStringConsole() {
        String output = "Event: "+ name + "\n" + "On: " + day.getDateHuman()+ " of " + day.nameOfMonth() + "\n" + description + "\n" + "Faction: " + faction;
        return output;
    }

    /** The pair to toString, reads the toString file format and turns it into an event.
     *
     * @param sc Scanner that will parse file/String
     * @return An event
     * @throws FileNotFoundException
     */
    public static Event Scanner(Scanner sc) throws FileNotFoundException {
        String name = sc.nextLine();
        String date = sc.nextLine();
        String description = sc.nextLine();
        String faction = sc.nextLine();
        Day day = Day.dateToDay(date);
        Event scanned = new Event(day,name,description,faction);
        return scanned;
    }
    public boolean onSameDay(Day day1){
        return day.equals(day1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return getDay().equals(event.getDay()) &&
                getName().equals(event.getName()) &&
                getDescription().equals(event.getDescription()) &&
                getFaction().equals(event.getFaction());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDay(), getName(), getDescription(), getFaction());
    }
}
