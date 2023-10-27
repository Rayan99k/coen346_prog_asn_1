public class Process{
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