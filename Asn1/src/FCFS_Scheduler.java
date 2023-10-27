import java.util.*;
public class FCFS_Scheduler {

    Queue<Process> readyQueue = new LinkedList<Process>(); 
    Queue<Process> waitingQueue = new LinkedList<Process>(); 
    Queue<Process> doneQueue = new LinkedList<Process>(); 

    int turnaroundAvg, responseAvg, waitingAvg;


    FCFS_Scheduler() {

    }

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
        
        while (!readyQueue.isEmpty()) {

            //poll(): Removes and returns the element at the front of the readyQueue.  
            Process currentProcess = readyQueue.poll();
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
