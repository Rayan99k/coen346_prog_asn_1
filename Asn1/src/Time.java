//Global timer class. Can be used to get the current time
//Must be incremented after every "CPU burst"
//Can use Time.inc() or Time.get() anywhere in the code

public class Time {
    private static int time = 0;

    public static int get() { //Returns global time
        return time;
    }

    public static int inc(){ //Increment global time by 1
        return ++time;
    }    
    
        public static int inc(int x){ //Increment global time by specific amount 
            time += x;
        return time;
    }

        public static void reset() {
            time = 0;
        } 
}
