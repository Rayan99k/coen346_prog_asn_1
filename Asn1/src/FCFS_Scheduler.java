import java.util.*;
public class FCFS_Scheduler {

    Queue<Process> readyQueue = new LinkedList<Process>(); 
    Queue<Process> waitingQueue = new LinkedList<Process>(); 
    Queue<Process> doneQueue = new LinkedList<>();


    int turnaroundAvg, responseAvg, waitingAvg;
        
    void addProcess(Process process) {
        //Add process to appropriate queue. Waiting as long as it has children
        if(process.getChildren()>0){
            process.setState("Waiting");
            waitingQueue.add(process);
        }
        else{
            process.setState("Ready");
            readyQueue.add(process);
        }   
    } 

    void runScheduler() {
        System.out.println("\n\n    First Come First Serve Scheduling\n\n");
        
        while (!readyQueue.isEmpty() || !waitingQueue.isEmpty()) {

            System.out.println("\nReady Queue:");
            printQueue(readyQueue);

            System.out.println("\nWaiting Queue:");
            printQueue(waitingQueue);

            Process currentProcess;

            if (!readyQueue.isEmpty()) {
                currentProcess = readyQueue.poll();
            } else if (!waitingQueue.isEmpty()) {
                //If readyQueue is empty, but there are processes in waitingQueue, process them first
                currentProcess = waitingQueue.poll();
                //Check if the process has children
                if (currentProcess.getChildren() > 0) {
                    //If it has children, handle them and move the parent to the ready queue afterward
                    FCFS(currentProcess);
                    readyQueue.add(currentProcess);
                }
            } else {
                //Both readyQueue and waitingQueue are empty
                break;
            }

            //poll(): Removes and returns the element at the front of the readyQueue.  
            currentProcess = readyQueue.poll();
            currentProcess.setState("Running");

            //Set the first contact time
            currentProcess.setFirstResponse(true);
            currentProcess.setFirstResponseTime(Time.get()-currentProcess.getArrivalTime());

            //Update waitingTime: currentTime - arrivalTime. 
            currentProcess.setWaitingTime((Time.get()-currentProcess.getArrivalTime()));
            
            Time.inc(currentProcess.getBurstTime());
            currentProcess.setBurstTime(0);            
            currentProcess.setState("Done");
            currentProcess.setCompletionTime(Time.get()-currentProcess.getArrivalTime());
            doneQueue.add(currentProcess);
        

            System.out.println("Current Time: " + Time.get() + 
                               ", Process: " + currentProcess.getName() + 
                               ", Remaining CPU: " + currentProcess.getBurstTime() 
                              );
        }

        //At the end of the run, calculate performance
        avgTimes();

    }

    private void printQueue(Queue<Process> queue) {
        for (Process process : queue) {
            System.out.println("Process in Queue: " + process.getName());
        }
    }

    private void avgTimes(){
        //Calculates the performance of the scheduler.
        //Only looks at the done queue. Make sure the ready and waiting queues are empty.
        
        int processesTotal = 0;
        int waitingTotal = 0;
        int turnaroundTotal = 0;
        int responseTotal = 0;
        
            while (!doneQueue.isEmpty()) {

            Process currentProcess = doneQueue.poll();

            processesTotal++;
            waitingTotal += currentProcess.getWaitingTime();
            turnaroundTotal += currentProcess.getCompletionTime();
            responseTotal += currentProcess.getFirstResponseTime();        
            }

        turnaroundAvg = turnaroundTotal / processesTotal;
        waitingAvg = waitingTotal / processesTotal;
        responseAvg = responseTotal / processesTotal;

        System.out.println("\nTurnaround Avg: " + turnaroundAvg + 
        ", Waiting Avg: " + waitingAvg + 
        ", Response Avg: " + responseAvg + 
        "\n"
        );

    }

}
