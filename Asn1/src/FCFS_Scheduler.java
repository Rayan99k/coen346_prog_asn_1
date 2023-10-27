import java.util.*;
public class FCFS_Scheduler {
    Queue<Process> queue = new LinkedList<Process>() ;

    FCFS_Scheduler() {

    }

    void addProcess(Process process) {
        queue.add(process);
    }

    void runScheduler() {
        while (!queue.isEmpty()) {
            Process currentProcess = queue.poll();
            System.out.println("Running process: " + currentProcess.getName());

            // Simulate process execution
            for (int i = 0; i < currentProcess.getBurstTime(); i++) {
                System.out.println("Executing time slice " + i + " of process " + currentProcess.getName());
            }

            System.out.println("Process " + currentProcess.getName() + " completed.");
        }

        System.out.println("All processes have been executed.");
    }
}
