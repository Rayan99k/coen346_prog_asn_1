import java.util.*;
public class Scheduler {
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
