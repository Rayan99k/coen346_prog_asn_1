//Global timer class. Can be used to get the current time
//Must be incremented after every "CPU burst"
//Can use Time.inc() or Time.get() anywhere in the code

public class Time {
    private static int time = 0;

    public static int get() { //Returns global time
        return time;
    }

    public static int inc(){ //Increment global time
        return ++time;
    }    
}
