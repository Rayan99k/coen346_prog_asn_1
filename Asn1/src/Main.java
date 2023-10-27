import java.util.*;

public class Main{
    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();


        // Create some processes
        Process process1 = new Process("T1", 1, 20, 0, 3);
        Process process2 = new Process("T1.1", 2, 5, 4, 0);
        Process process3 = new Process("T1.2", 3, 10, 5, 0);
        Process process4 = new Process("T1.3", 3, 15, 6, 0);
        Process process5 = new Process("T2", 5, 10, 0, 0);

        // Add processes to the ready queue
        scheduler.addProcess(process1);
        scheduler.addProcess(process2);
        scheduler.addProcess(process3);
        scheduler.addProcess(process4);
        scheduler.addProcess(process5);
        // Run the scheduler
        scheduler.runScheduler();
    }
}