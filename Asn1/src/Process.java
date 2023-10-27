public class Process{
    private String name;                    // Name
    private int priority;                   // Priority
    private int burstTime;                  // CPU burst time
    private int arrivalTime;                // Arrival Time
    private int waitingTime;                // Time spent in ready or waiting state
    private int completionTime;             // Time until it got into done state
    private int lastServed;                 // The last time in Running state
    private int firstResponseTime;          // Time it took to go from Ready to Running. First time
    private boolean firstResponse = false;  // Has it gone through the scheduler already?
    private int pid;                        // Process ID
    private int children;                   // Number of children
    private String state;                   // Waiting, Ready, Running


    static int totalprocesses = 0;

    Process(int pid, String name, int priority, int burstTime, int arrivalTime, int children) {
        this.pid = pid;
        this.name = name;
        this.priority = priority;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.lastServed = arrivalTime;
        this.children = children;
        

        totalprocesses++; //Increment the total number everytime a process is created

        if(this.children == 0){state = "Ready";}
        else{state = "Waiting";}
    }


    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getPid() {
        return pid;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    public static int getTotalprocesses() {
        return totalprocesses;
    }
 
    static public int incTotalProcesses(){
        return ++totalprocesses;
    }
 
    public int getLastServed() {
        return lastServed;
    }

    public void setLastServed(int lastServed) {
        this.lastServed = lastServed;
    }

    public int getFirstResponseTime() {
        return firstResponseTime;
    }

    public void setFirstResponseTime(int firstResponseTime) {
        this.firstResponseTime = firstResponseTime;
    }
 
    public boolean isFirstResponse() {
        return firstResponse;
    }

    public void setFirstResponse(boolean firstResponse) {
        this.firstResponse = firstResponse;
    }


}