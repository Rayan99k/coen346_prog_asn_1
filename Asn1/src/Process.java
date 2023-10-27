public class Process{
    String name;                            // Name
    int priority;                           // Priority
    int burstTime;                          // CPU burst time
    int arrivalTime;                        // Arrival Time
    int pid;                                // Process ID
    int children;                           // Number of children

    static int totalprocesses = 0;

    Process(int pid, String name, int priority, int burstTime, int arrivalTime, int children) {
        this.pid = pid;
        this.name = name;
        this.priority = priority;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.children = children;

        totalprocesses++; //Increment the total number everytime a process is created
    }
}