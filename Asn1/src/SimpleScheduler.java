import java.util.*;

class Process{
    String name;                            // Name
    int priority;                           // Priority
    int burstTime;                          // CPU burst time
    int arrivalTime;                        // Arrival Time
    int n;                                  // Number of child processes

    Process(String name, int priority, int burstTime, int arrivalTime, int n) {
        this.name = name;
        this.priority = priority;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.n = n;
    }
}

class Scheduler {
    Queue<Process> readyQueue;

    Scheduler() {
        this.readyQueue = new LinkedList<>();
    }

    void addProcess(Process process) {
        readyQueue.add(process);
    }

    void runScheduler() {
        while (!readyQueue.isEmpty()) {
            Process currentProcess = readyQueue.poll();
            System.out.println("Running process: " + currentProcess.name);

            // Simulate process execution
            for (int i = 0; i < currentProcess.burstTime; i++) {
                System.out.println("Executing time slice " + i + " of process " + currentProcess.name);
            }

            System.out.println("Process " + currentProcess.name + " completed.");
        }

        System.out.println("All processes have been executed.");
    }
}

public class SimpleScheduler{
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