import java.util.Objects;
import java.util.Scanner;

public class Day {
    private int day;

    public int getDate() {
        return day;
    }

    public int getDateHuman(){
        int output = day % 30;
        return output;
    }

    public Day(int day) {
        this.day = day;
        assert day <= 360:"Not a valid day" ;
    }
    public void advanceDay(int i){
        day = day + i;
    }

    /**Converts from the current day to the month.
     * Very useful for when storing dates and when
     *
     * @return
     */
    public  int monthOfDay(){
        int month = (int) Math.ceil(day/30.0);
        return month;
    }

    public String nameOfMonth(){
        String output = "Error";
        int month = this.monthOfDay();
        switch (month){
            case 1: output = "Hammer";
                break;
            case 2: output = "Alturiak";
                break;
            case 3: output = "Ches";
                break;
            case 4: output = "Tarsahk";
                break;
            case 5: output = "Mirtul";
                break;
            case 6: output = "Kythorn";
                break;
            case 7: output = "Famerule";
                break;
            case 8: output = "Eleasis";
                break;
            case 9: output = "Elient";
                break;
            case 10: output = "Marpenoth";
                break;
            case 11: output = "Uktar";
                break;
            case 12: output = "Nightal";
                break;
        }
        return output;
    }

    @Override
    public String toString() {
        String output =  this.getDateHuman() + "/" + this.monthOfDay();
        return output;
    }

    /**
     * Returns a day upon being given a string represeted by it's ToString method.
     * @param input
     * @return
     */
    public static Day dateToDay(String input){
        Scanner sc = new Scanner(input);
        sc.useDelimiter("/");
        int day = Integer.parseInt(sc.next());
        sc.skip("/");
        sc.reset();
        int month = Integer.parseInt(sc.next());
        int dayTotal = (month -1)*30 + day;
        Day output = new Day(dayTotal);
        return output;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Day day1 = (Day) o;
        return day == day1.day;
    }
}
